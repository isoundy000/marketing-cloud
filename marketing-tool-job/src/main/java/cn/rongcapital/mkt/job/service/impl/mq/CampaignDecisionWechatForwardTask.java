package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpClientUtil;
import cn.rongcapital.mkt.common.util.HttpUrl;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatForwardDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionWechatForward;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignDecisionWechatForwardTask extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CampaignDecisionWechatForwardDao campaignDecisionWechatForwardDao;
//	@Value("${runxue.h5.api.base.url}")
//	private String h5BaseUrl;
	
	public void task (TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		String queueKey = campaignHeadId+"-"+itemId;
		List<CampaignSwitch> campaignSwitchYesList = queryCampaignSwitchYesList(campaignHeadId, itemId);
		List<CampaignSwitch> campaignSwitchNoList = queryCampaignSwitchNoList(campaignHeadId, itemId);
		if(CollectionUtils.isEmpty(campaignSwitchYesList) && 
		   CollectionUtils.isEmpty(campaignSwitchNoList)) {
			logger.info("没有后续节点，该活动线路终止,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignDecisionWechatForward campaignDecisionWechatForwardT = new CampaignDecisionWechatForward();
		campaignDecisionWechatForwardT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionWechatForwardT.setCampaignHeadId(campaignHeadId);
		campaignDecisionWechatForwardT.setItemId(itemId);
		List<CampaignDecisionWechatForward> campaignDecisionWechatForwardList = campaignDecisionWechatForwardDao.selectList(campaignDecisionWechatForwardT);
		if(CollectionUtils.isEmpty(campaignDecisionWechatForwardList)) {
			logger.info("没有设置决策条件，该活动线路终止,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
		    return;
		}
		CampaignDecisionWechatForward campaignDecisionWechatForward = campaignDecisionWechatForwardList.get(0);
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
		List<Segment> segmentList = getQueueData(queue);//获取MQ数据
		if(CollectionUtils.isNotEmpty(segmentList)) {
			List<Segment> segmentListToNextYes = new ArrayList<Segment>();//要传递给下面节点的数据:转发微信的
			List<Segment> segmentListToNextNo = new ArrayList<Segment>();//要传递给下面节点的数据:没转发微信的
			for(Segment segment:segmentList) {
				String pubId= campaignDecisionWechatForward.getPubId();
				String openId = segment.getFansFriendsOpenId();
				Integer materialId = campaignDecisionWechatForward.getMaterialId();
				//有pubId,openId,materialId的人,校验是否转发
				if(StringUtils.isNotBlank(pubId) && StringUtils.isNotBlank(openId) && null != materialId) {
					boolean isForward = checkWechatForwardByH5Interface(pubId,openId,materialId,campaignDecisionWechatForward);
					if(true == isForward) {
						segmentListToNextYes.add(segment);
					} else {
						segmentListToNextNo.add(segment);
					}
				}
			}
			if(CollectionUtils.isNotEmpty(campaignSwitchYesList)) {
				CampaignSwitch csYes = campaignSwitchYesList.get(0);
				sendDynamicQueue(segmentListToNextYes, csYes.getCampaignHeadId() +"-"+csYes.getNextItemId());
				logger.info(queueKey+"-out-yes:"+JSON.toJSONString(segmentListToNextYes));
			}
			if(CollectionUtils.isNotEmpty(campaignSwitchNoList)) {
				CampaignSwitch csNo = campaignSwitchNoList.get(0);
				sendDynamicQueue(segmentListToNextNo, csNo.getCampaignHeadId() +"-"+csNo.getNextItemId());
				logger.info(queueKey+"-out-no:"+JSON.toJSONString(segmentListToNextNo));
			} else {
				//如果没有非分支，则MQ数据发送给本节点，供本节点下一次刷新的时候再次检测
				sendDynamicQueue(segmentListToNextNo, campaignHeadId +"-"+itemId);
				logger.info(queueKey+"-out-to-self:"+JSON.toJSONString(segmentListToNextNo));
			}
		}
	
	}
	
	private boolean checkWechatForwardByH5Interface(String pubId,String fansOpenId,Integer materialId,
												    CampaignDecisionWechatForward campaignDecisionWechatForward) {
		boolean isForward = false;
		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(h5BaseUrl);
		httpUrl.setPath(ApiConstant.DL_PUB_ISFORWARD_API_PATH+getPid());
		HashMap<Object , Object> params = new HashMap<Object , Object>();
		params.put("pub_id", pubId);
		params.put("fans_open_id", fansOpenId);
		params.put("material_id", materialId);
		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
		httpUrl.setRequetsBody(JSON.toJSONString(params));
		try {
			PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
			String postResStr = postResult.getResponseBodyAsString();
			JSONObject resJson = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_wtuwen_shared_response");
			Integer share_count = resJson.getInteger("share_count");//转发次数
			Byte forwardTimesStrict = campaignDecisionWechatForward.getForwardTimes();
			if(null != share_count && share_count > 0) {
				if(null != forwardTimesStrict) {
					switch (forwardTimesStrict) {
					case ApiConstant.CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_NOLIMIT:
						isForward = true;
						break;
					case ApiConstant.CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_ONE:
						if(share_count > 1) {
							isForward = true;
						}
						break;		
					case ApiConstant.CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_TEN:
						if(share_count > 10) {
							isForward = true;
						}
						break;	
					case ApiConstant.CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_ONE_HUNDRED:
						if(share_count > 100) {
							isForward = true;
						}
						break;
					case ApiConstant.CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_FIVE_HUNDRED:
						if(share_count > 500) {
							isForward = true;
						}
						break;
					}
				} else {
					isForward = true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return isForward;
	}
	
	@Override
	public void task(Integer taskId) {		
		
	}

}
