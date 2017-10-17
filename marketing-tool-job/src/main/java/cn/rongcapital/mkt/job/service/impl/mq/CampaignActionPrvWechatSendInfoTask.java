package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignActionSendPrivtDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionSendPrivt;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignActionPrvWechatSendInfoTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignActionSendPrivtDao campaignActionSendPrivtDao;
	
	public void task (TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		CampaignActionSendPrivt campaignActionSendPrivtT = new CampaignActionSendPrivt();
		campaignActionSendPrivtT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSendPrivtT.setCampaignHeadId(campaignHeadId);
		campaignActionSendPrivtT.setItemId(itemId);
		List<CampaignActionSendPrivt> campaignActionSendPrivtList = campaignActionSendPrivtDao.selectList(campaignActionSendPrivtT);
		if(CollectionUtils.isEmpty(campaignActionSendPrivtList) ||
		   StringUtils.isBlank(campaignActionSendPrivtList.get(0).getUin()) ||
		   StringUtils.isBlank(campaignActionSendPrivtList.get(0).getTextInfo())) {
			logger.error("没有配置个人号属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignActionSendPrivt campaignActionSendPrivt = campaignActionSendPrivtList.get(0);
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
							processMqMessage(segmentList,campaignHeadId,
											 itemId,campaignEndsList,campaignActionSendPrivt);
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
				consumerMap.put(campaignHeadId+"-"+itemId, consumer);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}     
		}
		
	}

	private void processMqMessage(List<Segment> segmentList,
			  Integer campaignHeadId,String itemId,
			  List<CampaignSwitch> campaignEndsList,
			  CampaignActionSendPrivt campaignActionSendPrivt) {
		List<Segment> segmentListToNext = new ArrayList<Segment>();//要传递给下面节点的数据(执行了发送微信操作的数据)
		String queueKey = campaignHeadId+"-"+itemId;
		for(Segment segment:segmentList) {
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				insertNodeAudience(campaignHeadId, itemId, segment);
				Integer dataId = segment.getDataId();
				//从mongo的主数据表中查询该条id对应的主数据详细信息
                Query query = new Query(Criteria.where("mid").is(dataId));
				DataParty dp = mongoTemplate.findOne(query, DataParty.class);
				if(null!=dp && null !=dp.getMdType() &&
						StringUtils.isNotBlank(dp.getMappingKeyid()) &&
						dp.getMdType() == ApiConstant.DATA_PARTY_MD_TYPE_WECHAT) {
					//调用微信个人号发送消息接口
					String uin = campaignActionSendPrivt.getUin();
					String textInfo = campaignActionSendPrivt.getTextInfo();
					String groupUcode = campaignActionSendPrivt.getUcode();
					boolean isFriend = isPrvWechatFriend(segment, uin, groupUcode);
					if(isFriend) {
						boolean isSent = sendPrvWechatByH5Interface(uin,textInfo,dp.getMappingKeyid());
						if(isSent) {
							segmentListToNext.add(segment);//数据放入向后面节点传递的list里
                            Update update = new Update();
                            update.inc("receiveCount", Integer.valueOf(1));
                            mongoTemplate.updateFirst(query, update, DataParty.class);
						}
					} else {
						logger.info("不是个人号好友,无法发送,"+JSON.toJSONString(segment));
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList)) {
			for(CampaignSwitch cs:campaignEndsList) {
				//发送segment数据到后面的节点
				sendDynamicQueue(segmentListToNext, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
				logicDeleteNodeAudience(campaignHeadId,itemId,segmentListToNext);
				logger.info(queueKey+"-out:"+JSON.toJSONString(segmentListToNext));
			}
		}
	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelCampaignInnerTask(taskSchedule);
	}
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
