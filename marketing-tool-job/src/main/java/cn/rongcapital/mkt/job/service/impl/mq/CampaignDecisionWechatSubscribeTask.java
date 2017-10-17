package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignDecisionPubFansDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionPubFans;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignDecisionWechatSubscribeTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CampaignDecisionPubFansDao campaignDecisionPubFansDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
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
		CampaignDecisionPubFans campaignDecisionPubFansT = new CampaignDecisionPubFans();
		campaignDecisionPubFansT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionPubFansT.setCampaignHeadId(campaignHeadId);
		campaignDecisionPubFansT.setItemId(itemId);
		List<CampaignDecisionPubFans> campaignDecisionPubFansList = campaignDecisionPubFansDao.selectList(campaignDecisionPubFansT);
		if(CollectionUtils.isEmpty(campaignDecisionPubFansList) ||
		   StringUtils.isBlank(campaignDecisionPubFansList.get(0).getPubId())) {
			logger.error("没有设置决策条件,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignDecisionPubFans campaignDecisionPubFans = campaignDecisionPubFansList.get(0);
		String pubId = campaignDecisionPubFans.getPubId();
		Byte subscribeTimeType = campaignDecisionPubFans.getSubscribeTime();
		String currentQueueName = campaignHeadId + "-" +itemId;
		Queue queue = getDynamicQueue(currentQueueName);
		List<Segment> segmentList = getQueueData(queue);
		if(CollectionUtils.isNotEmpty(segmentList)) {
			List<Segment> segmentListToNextYes = new ArrayList<Segment>();//要传递给下面节点的数据:是粉丝
			List<Segment> segmentListToNextNo = new ArrayList<Segment>();//要传递给下面节点的数据:不是粉丝
			for(Segment segment:segmentList) {
				//从mongo的主数据表中查询该条id对应的主数据详细信息
				DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(segment.getDataId())), DataParty.class);
				boolean isFans = isPubWechatFans(dp, pubId, subscribeTimeType);
				if(isFans) {
					segmentListToNextYes.add(segment);
				}else{
					segmentListToNextNo.add(segment);
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
				//如果没有非分支，则MQ数据发送给本节点，供本节点下一次刷新的时候再次检测
				sendDynamicQueue(segmentListToNextNo, campaignHeadId +"-"+itemId);
				logger.info(queueKey+"-out-to-self:"+JSON.toJSONString(segmentListToNextNo));
			}
		}
		
	}
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
