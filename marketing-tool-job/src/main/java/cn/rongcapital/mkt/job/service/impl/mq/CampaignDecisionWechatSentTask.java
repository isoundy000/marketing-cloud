//package cn.rongcapital.mkt.job.service.impl.mq;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.jms.Queue;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//
//import cn.rongcapital.mkt.common.constant.ApiConstant;
//import cn.rongcapital.mkt.common.util.HttpClientUtil;
//import cn.rongcapital.mkt.common.util.HttpUrl;
//import cn.rongcapital.mkt.job.service.base.TaskService;
//import cn.rongcapital.mkt.po.CampaignSwitch;
//import cn.rongcapital.mkt.po.TaskSchedule;
//import cn.rongcapital.mkt.po.mongodb.Segment;
//
//@Service
//public class CampaignDecisionWechatSentTask extends BaseMQService implements TaskService {
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired  
//    private MQUtil mqUtil;
//	
//	@Value("${runxue.h5.api.base.url}")
//	private String h5BaseUrl;
//	
//	public void task (TaskSchedule taskSchedule){
//		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
//		String itemId = taskSchedule.getCampaignItemId();
//		List<CampaignSwitch> campaignSwitchYesList = queryCampaignSwitchYesList(campaignHeadId, itemId);
//		List<CampaignSwitch> campaignSwitchNoList = queryCampaignSwitchNoList(campaignHeadId, itemId);
//		if(CollectionUtils.isEmpty(campaignSwitchYesList) && 
//		   CollectionUtils.isEmpty(campaignSwitchNoList)) {
//			return;//标签判断节点下面如果没有分支，则该条活动线路截止
//		}
//		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
//		List<Segment> segmentList = getQueueData(queue);//获取MQ数据
//		if(CollectionUtils.isNotEmpty(segmentList)) {
//			List<Segment> segmentListToNextYes = new ArrayList<Segment>();//要传递给下面节点的数据:发送成功的
//			List<Segment> segmentListToNextNo = new ArrayList<Segment>();//要传递给下面节点的数据:发送失败的
//			for(Segment segment:segmentList) {
//				String pubId= ;
//				Integer taskId = segment.getTaskId();
//				if(StringUtils.isNotBlank(pubId) && null != taskId) {//有pubId和taskId的人,校验是否发送
//					boolean isSent = checkWechatSentByH5Interface(pubId, taskId);
//					if(true == isSent) {
//						segmentListToNextYes.add(segment);
//					} else {
//						segmentListToNextNo.add(segment);
//					}
//				}
//			}
//			if(CollectionUtils.isNotEmpty(campaignSwitchYesList)) {
//				CampaignSwitch csYes = campaignSwitchYesList.get(0);
//				mqUtil.sendDynamicQueue(segmentListToNextYes, csYes.getCampaignHeadId() +"-"+csYes.getNextItemId());
//			}
//			if(CollectionUtils.isNotEmpty(campaignSwitchNoList)) {
//				CampaignSwitch csNo = campaignSwitchNoList.get(0);
//				mqUtil.sendDynamicQueue(segmentListToNextNo, csNo.getCampaignHeadId() +"-"+csNo.getNextItemId());
//			} else {
//				//如果没有非分支，则MQ数据发送给本节点，供本节点下一次刷新的时候再次检测是否发送
//				mqUtil.sendDynamicQueue(segmentListToNextNo, campaignHeadId +"-"+itemId);
//			}
//		}
//	}
//	
//	private boolean checkWechatSentByH5Interface(String pubId,int taskId) {
//		boolean isSuccess = false;
//		HttpUrl httpUrl = new HttpUrl();
//		httpUrl.setHost(h5BaseUrl);
//		httpUrl.setPath(ApiConstant.DL_PUB_ISSENT_API_PATH);
//		HashMap<Object , Object> params = new HashMap<Object , Object>();
//		params.put("pid",getPid());
//		params.put("pub_id", pubId);
//		params.put("task_id", taskId);
//		httpUrl.setParams(params);
//		try {
//			PostMethod postResult = HttpClientUtil.getInstance().post(httpUrl);
//			String postResStr = postResult.getResponseBodyAsString();
//			String status = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_wtuwen_sent_response").getString("status");
//			if(StringUtils.isNotBlank(status) && status.equals("true")) {
//				isSuccess = true;
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//		}
//		return isSuccess;
//	}
//
//	@Override
//	public void task(Integer taskId) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
