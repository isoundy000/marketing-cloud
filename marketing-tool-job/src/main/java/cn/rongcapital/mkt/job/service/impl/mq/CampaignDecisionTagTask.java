package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionTag;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignDecisionTagTask extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
			
	@Autowired
	private CampaignDecisionTagDao campaignDecisionTagDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		String queueKey = campaignHeadId+"-"+itemId;
		List<CampaignSwitch> campaignSwitchYesList = queryCampaignSwitchYesList(campaignHeadId, itemId);
		List<CampaignSwitch> campaignSwitchNoList = queryCampaignSwitchNoList(campaignHeadId, itemId);
		if(CollectionUtils.isEmpty(campaignSwitchYesList) && 
		   CollectionUtils.isEmpty(campaignSwitchNoList)) {
			return;//标签判断节点下面如果没有分支，则该条活动线路截止
		}
		CampaignDecisionTag campaignDecisionTagT = new CampaignDecisionTag();
		campaignDecisionTagT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionTagT.setCampaignHeadId(campaignHeadId);
		campaignDecisionTagT.setItemId(itemId);
		List<CampaignDecisionTag> campaignDecisionTagList = campaignDecisionTagDao.selectList(campaignDecisionTagT);
		if(CollectionUtils.isEmpty(campaignDecisionTagList) || 
		   null == campaignDecisionTagList.get(0).getRule() ||
		   null == campaignDecisionTagList.get(0).getTagIds()
		  ){
			logger.info("标签判断规则为空,campagin_head_id:"+campaignHeadId+",itemId:"+itemId);
			return;//该条活动线路截止
		}
		CampaignDecisionTag campaignDecisionTag = campaignDecisionTagList.get(0);
		Byte rule = campaignDecisionTag.getRule();//标签判断规则
		//查询该规则对应的标签list
		String tagIdsStr =  campaignDecisionTagList.get(0).getTagIds();
		List<String> tagIdsStrList = Arrays.asList(tagIdsStr);

		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
		MessageConsumer consumer = getQueueConsumer(queue);//获取queue的消费者对象
		//监听MQ的listener
		MessageListener listener = new MessageListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onMessage(Message message) {
				if(message!=null) {
					try {
						//获取segment list数据对象
						List<Segment> segmentList = (List<Segment>)((ObjectMessage)message).getObject();
						if(CollectionUtils.isNotEmpty(segmentList)) {
							processMqMessage(message,segmentList,tagIdsStrList,campaignSwitchYesList,campaignSwitchNoList,rule,queueKey);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		};
		if(null != consumer){
			try {
				//设置监听器
				consumer.setMessageListener(listener);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}     
		}
	}
	
	//处理listener接收到的数据
	private void processMqMessage(Message message,List<Segment> segmentList,
								  List<String> tagIdList,
								  List<CampaignSwitch> campaignSwitchYesList,
								  List<CampaignSwitch> campaignSwitchNoList,
								  byte rule,String queueKey) throws Exception{
		List<Segment> segmentListToMqYes = new ArrayList<Segment>();//初始化"是"分支的数据对象list
		List<Segment> segmentListToMqNo = new ArrayList<Segment>();//初始化"非"分支的数据对象list
		for(Segment s:segmentList) {
			switch (rule) {
			//全部匹配
			case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_ALL:
				boolean isAllMatch = true;
				for(int i=0;i<tagIdList.size();i++){
					Integer dataId = s.getDataId();
					Criteria criteria = Criteria.where("mid").is(dataId);
					criteria = criteria.andOperator(Criteria.where("custom_tag_list").is(tagIdList.get(i)));
					List<DataParty> dpListM1 = mongoTemplate.find(new Query(criteria), DataParty.class);
					if(CollectionUtils.isEmpty(dpListM1)) {
						isAllMatch = false;
						break;
					}
				}
				if(isAllMatch) {
					segmentListToMqYes.add(s);
				}else{
					segmentListToMqNo.add(s);
				}
				break;
			//匹配其一
			case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_ONE:
				boolean isMatchOne = false;
				for(int i=0;i<tagIdList.size();i++){
					Integer dataId = s.getDataId();
					Criteria criteria = Criteria.where("mid").is(dataId);
					criteria = criteria.andOperator(Criteria.where("custom_tag_list").is(tagIdList.get(i)));
					List<DataParty> dpListM1 = mongoTemplate.find(new Query(criteria), DataParty.class);
					if(CollectionUtils.isNotEmpty(dpListM1)) {
						isMatchOne = true;
						break;
					}
				}
				if(isMatchOne) {
					segmentListToMqYes.add(s);
				}else{
					segmentListToMqNo.add(s);
				}
				break;
			//匹配2个及以上
			case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_TWO_MORE:
				int matchCount = 0;
				boolean matchTwoMore = false;
				for(int i=0;i<tagIdList.size();i++){
					Integer dataIdStr3 = s.getDataId();
					Criteria criteria3 = Criteria.where("mid").is(dataIdStr3);
					criteria3 = criteria3.andOperator(Criteria.where("custom_tag_list").is(tagIdList.get(i)));
					List<DataParty> dpListM1 = mongoTemplate.find(new Query(criteria3), DataParty.class);
					if(CollectionUtils.isNotEmpty(dpListM1)) {
						++matchCount;
					}
					if(matchCount >= 2) {
						matchTwoMore = true;
						break;
					}
				}
				if(matchTwoMore) {
					segmentListToMqYes.add(s);
				}else{
					segmentListToMqNo.add(s);
				}
				break;
			}
		}
		if(CollectionUtils.isNotEmpty(campaignSwitchYesList)) {
			CampaignSwitch csYes = campaignSwitchYesList.get(0);
			sendDynamicQueue(segmentListToMqYes, csYes.getCampaignHeadId() +"-"+csYes.getNextItemId());
			logger.info(queueKey+"-out-yes:"+JSON.toJSONString(segmentListToMqYes));
		}
		if(CollectionUtils.isNotEmpty(campaignSwitchNoList)) {
			CampaignSwitch csNo = campaignSwitchNoList.get(0);
			sendDynamicQueue(segmentListToMqNo, csNo.getCampaignHeadId() +"-"+csNo.getNextItemId());
			logger.info(queueKey+"-out-no:"+JSON.toJSONString(segmentListToMqNo));
		}
	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelCampaignInnerTask(taskSchedule);
	}

	@Override
	public void task(Integer taskId) {
	}
	
}
