package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignActionWaitDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionWait;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignActionWaitTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CampaignActionWaitDao campaignActionWaitDao;
	@Autowired
    private ConcurrentTaskScheduler taskSchedule;
	private static ConcurrentHashMap<String, ScheduledFuture<?>> waitTaskMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();
	
	public void task (TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		
		CampaignActionWait campaignActionWaitT = new CampaignActionWait();
		campaignActionWaitT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionWaitT.setCampaignHeadId(campaignHeadId);
		campaignActionWaitT.setItemId(itemId);
		List<CampaignActionWait> campaignActionWaitList =  campaignActionWaitDao.selectList(campaignActionWaitT);
		if(CollectionUtils.isEmpty(campaignActionWaitList) ||
		   ((null == campaignActionWaitList.get(0).getRelativeType() || 
		   null == campaignActionWaitList.get(0).getRelativeValue()) &&
		   (null == campaignActionWaitList.get(0).getSpecificTime()))
		   ) {
			  logger.error("没有配置等待动作的属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			  return;
		}
		Byte realativeType = campaignActionWaitList.get(0).getRelativeType();
		Integer relativeValue = campaignActionWaitList.get(0).getRelativeValue();
		Date specificTime = campaignActionWaitList.get(0).getSpecificTime();
		
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
											 itemId,campaignEndsList,realativeType,relativeValue,specificTime);
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
								  Integer campaignHeadId,String itemId,List<CampaignSwitch> campaignEndsList,
								  Byte realativeType,Integer relativeValue,Date specificTime) {
		String queueKey = campaignHeadId+"-"+itemId;
		List<Segment> segmentListToNext = new ArrayList<Segment>();
		for(Segment segment:segmentList) {
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				insertNodeAudience(campaignHeadId, itemId, segment);
				segmentListToNext.add(segment);
			}
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList)) {
			for(CampaignSwitch cs:campaignEndsList) {
				Runnable task = new Runnable() {
					public void run() {
						sendDynamicQueue(segmentListToNext, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
						logicDeleteNodeAudience(campaignHeadId,itemId,segmentListToNext);
						logger.info(queueKey+"-out:"+JSON.toJSONString(segmentListToNext));
					}
				};
				ScheduledFuture<?> scheduledFuture = null;
				if(null != realativeType && null != relativeValue) {
					DateTime now = new DateTime();
					DateTime execTime = null;
					switch (realativeType) {
					case 0://小时
						execTime = now.plusHours(relativeValue);
						scheduledFuture = taskSchedule.schedule(task, execTime.toDate());
						break;
					case 1://天
						execTime = now.plusDays(relativeValue);
						scheduledFuture = taskSchedule.schedule(task, execTime.toDate());
						break;
					case 2://周
						execTime = now.plusWeeks(relativeValue);
						scheduledFuture = taskSchedule.schedule(task, execTime.toDate());
						break;
					case 3://月
						execTime = now.plusMonths(relativeValue);
						scheduledFuture = taskSchedule.schedule(task, execTime.toDate());
						break;
					}
				} else if(null != specificTime) {
					scheduledFuture = taskSchedule.schedule(task, specificTime);
				}
				if(null != scheduledFuture) {
					waitTaskMap.put(cs.getCampaignHeadId()+"-"+cs.getNextItemId()+"-"+System.currentTimeMillis()+"-"+segmentListToNext.hashCode(), scheduledFuture);
				}
			}
		}
	}	
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		String prefixKey = campaignHeadId+"-"+itemId;
		waitTaskMap.forEach((key,scheduledFuture)->{
			if(StringUtils.isNotBlank(key) && key.startsWith(prefixKey))
			scheduledFuture.cancel(true);
		});
		super.cancelCampaignInnerTask(taskSchedule);
	}
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
