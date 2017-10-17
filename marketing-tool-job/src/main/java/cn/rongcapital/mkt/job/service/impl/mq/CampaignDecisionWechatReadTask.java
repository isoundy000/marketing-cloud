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
import cn.rongcapital.mkt.dao.CampaignDecisionWechatReadDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionWechatRead;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignDecisionWechatReadTask extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CampaignDecisionWechatReadDao campaignDecisionWechatReadDao;
	
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
			logger.info("没有后续节点,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignDecisionWechatRead campaignDecisionWechatReadT = new CampaignDecisionWechatRead();
		campaignDecisionWechatReadT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionWechatReadT.setCampaignHeadId(campaignHeadId);
		campaignDecisionWechatReadT.setItemId(itemId);
		List<CampaignDecisionWechatRead> campaignDecisionWechatReadList = campaignDecisionWechatReadDao.selectList(campaignDecisionWechatReadT);
		if(CollectionUtils.isEmpty(campaignDecisionWechatReadList)) {
			logger.info("没有设置决策条件，return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
		    return;
		}
		CampaignDecisionWechatRead campaignDecisionWechatRead = campaignDecisionWechatReadList.get(0);
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
		List<Segment> segmentList = getQueueData(queue);//获取MQ数据
		if(CollectionUtils.isNotEmpty(segmentList)) {
			List<Segment> segmentListToNextYes = new ArrayList<Segment>();//要传递给下面节点的数据:已读的
			List<Segment> segmentListToNextNo = new ArrayList<Segment>();//要传递给下面节点的数据:未读的
			for(Segment segment:segmentList) {
				String pubId= campaignDecisionWechatRead.getPubId();
				String openId = segment.getFansFriendsOpenId();
				Integer materialId = campaignDecisionWechatRead.getMaterialId();
//				String h5MobileUrl = segment.getH5MobileUrl();//mobile端的url
				//有pubId,openId,h5MobileUrl的人,校验是否已读
				if(StringUtils.isNotBlank(pubId) && 
				   StringUtils.isNotBlank(openId) && 
				   null!=materialId) {
					boolean isRead = checkWechatReadByH5Interface(pubId,openId,campaignDecisionWechatRead);
					if(true == isRead) {
						segmentListToNextYes.add(segment);
					} else {
						segmentListToNextNo.add(segment);
					}
				}
			}
			if(CollectionUtils.isNotEmpty(campaignSwitchYesList)) {
				CampaignSwitch csYes = campaignSwitchYesList.get(0);
				sendDynamicQueue(segmentListToNextYes, csYes.getCampaignHeadId() +"-"+csYes.getNextItemId());
				logger.info(queueKey+"-out-to-yes:"+JSON.toJSONString(segmentListToNextYes));
			}
			if(CollectionUtils.isNotEmpty(campaignSwitchNoList)) {
				CampaignSwitch csNo = campaignSwitchNoList.get(0);
				sendDynamicQueue(segmentListToNextNo, csNo.getCampaignHeadId() +"-"+csNo.getNextItemId());
				logger.info(queueKey+"-out-to-no:"+JSON.toJSONString(segmentListToNextNo));
			} else {
				//如果没有非分支，则MQ数据发送给本节点，供本节点下一次刷新的时候再次检测是否已读
				sendDynamicQueue(segmentListToNextNo, campaignHeadId +"-"+itemId);
				logger.info(queueKey+"-out-to-self:"+JSON.toJSONString(segmentListToNextNo));
			}
		}
	
	}
	
	private boolean checkWechatReadByH5Interface(String pubId,String fansOpenId,
												 CampaignDecisionWechatRead campaignDecisionWechatRead) {
		boolean isRead = true;
		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(h5BaseUrl);
		httpUrl.setPath(ApiConstant.DL_PUB_ISREAD_API_PATH+getPid());
		HashMap<Object , Object> params = new HashMap<Object , Object>();
		params.put("pub_id", pubId);
		params.put("fans_open_id", fansOpenId);
		Integer materialId = campaignDecisionWechatRead.getMaterialId();
		params.put("material_id", materialId);
		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
		httpUrl.setRequetsBody(JSON.toJSONString(params));
		try {
			PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
			String postResStr = postResult.getResponseBodyAsString();
			JSONObject resJson = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_wtuwen_viewed_response");
			String status = resJson.getString("status");
			Integer duration = resJson.getInteger("duration");//阅读时长
			String scroll_to_bottom = resJson.getString("scroll_to_bottom");//是否滑到底部
			if(StringUtils.isNotBlank(status) && status.equals("true")) {
				if(campaignDecisionWechatRead.getReadTime() != null && 
				   campaignDecisionWechatRead.getReadTime() != 0) {
					byte readTime = campaignDecisionWechatRead.getReadTime();
					int durtionStrict = -1;
					if(readTime == 1) {
						 durtionStrict = 60*1000;//1分钟
					}
					if(readTime == 2) {
						 durtionStrict = 3*60*1000;//3分钟
						
					}
					if(readTime == 3) {
						 durtionStrict = 5*60*1000;//4分钟
						
					}
					if(readTime == 4) {
						 durtionStrict = 10*60*1000;//10分钟
						
					}
					isRead = duration < durtionStrict?false:true;
				}
				if(isRead == true && StringUtils.isNotBlank(scroll_to_bottom) && scroll_to_bottom.equals("false")) {
					byte readPercent = campaignDecisionWechatRead.getReadPercent();
					if(readPercent == 2) {//要求全部阅读
						isRead = false;
					}
				}
			} else {
				isRead = false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			isRead = false;
		}
		return isRead;
	}
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}

}
