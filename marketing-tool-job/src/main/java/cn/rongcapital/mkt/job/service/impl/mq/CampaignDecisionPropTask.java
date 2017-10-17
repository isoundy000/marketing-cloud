package cn.rongcapital.mkt.job.service.impl.mq;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CampaignDecisionPropCompareDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionPropCompare;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignDecisionPropTask extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CampaignDecisionPropCompareDao campaignDecisionPropCompareDao;
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
			return;//标签判断节点下面如果没有分支，则该条活动线路截止
		}
		CampaignDecisionPropCompare campaignDecisionPropCompareT = new CampaignDecisionPropCompare();
		campaignDecisionPropCompareT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionPropCompareT.setCampaignHeadId(campaignHeadId);
		campaignDecisionPropCompareT.setItemId(itemId);
		List<CampaignDecisionPropCompare> campaignDecisionPropCompareList = campaignDecisionPropCompareDao.selectList(campaignDecisionPropCompareT);
		if(CollectionUtils.isEmpty(campaignDecisionPropCompareList) || 
		   null == campaignDecisionPropCompareList.get(0).getRule()
		  ){
			logger.info("节点属性为空,return,campagin_head_id:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		
		CampaignDecisionPropCompare campaignDecisionPropCompare = campaignDecisionPropCompareList.get(0);
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
							processMqMessage(message,segmentList,campaignSwitchYesList,campaignSwitchNoList,campaignDecisionPropCompare,queueKey);
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
	//处理listener接收到的数据
	private void processMqMessage(Message message,List<Segment> segmentList,
								  List<CampaignSwitch> campaignSwitchYesList,
								  List<CampaignSwitch> campaignSwitchNoList,
								  CampaignDecisionPropCompare campaignDecisionPropCompare,
								  String queueKey) throws Exception{
		List<Segment> segmentListToMqYes = new ArrayList<Segment>();//初始化"是"分支的数据对象list
		List<Segment> segmentListToMqNo = new ArrayList<Segment>();//初始化"非"分支的数据对象list
		Byte rule = campaignDecisionPropCompare.getRule();
		Byte propType = campaignDecisionPropCompare.getPropType();
		String value = campaignDecisionPropCompare.getValue();
		Byte exclude = campaignDecisionPropCompare.getExclude();
		for(Segment segment:segmentList) {
			boolean checkRes = compareProp(rule, propType, segment, value,exclude);
			if(checkRes) {
				segmentListToMqYes.add(segment);
			} else {
				segmentListToMqNo.add(segment);
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
	
	private boolean compareProp(Byte rule,Byte propType,Segment segment, String value,byte exclude) throws IllegalArgumentException, IllegalAccessException {
		boolean checkRes = false;
		DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(segment.getDataId())), DataParty.class);
		if(dp != null) {
			Field fields[] = DataParty.class.getDeclaredFields();
			switch (rule) {
			case 0://0：等于
				switch (propType) {
				case 0://文本
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(String.class) ){
					    	String valueInData = (String)filed.get(dp);
					    	if(exclude == 0) {
					    		if(StringUtils.equals(value, valueInData)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!StringUtils.equals(value, valueInData)) {
					    			checkRes = true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				case 1://数字
					if(StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Byte.class) ||
					       filed.getType().isAssignableFrom(Integer.class)||
					       filed.getType().isAssignableFrom(Long.class)){
					    	Object valueInData = filed.get(dp);
					    	Long valueInDataLong = Long.parseLong(String.valueOf(valueInData));
					    	Long valueLong = Long.parseLong(value);
					    	if(exclude == 0) {
					    		if(valueInDataLong == valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	} else {
					    		if(valueInDataLong != valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				case 2://日期
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Date.class) ){
					    	Date dateInData = (Date)filed.get(dp);
					    	Date date = DateUtil.getDateFromString(value, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
					    	if(exclude == 0) {
					    		if(dateInData.equals(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!dateInData.equals(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				}
				break;
			case 1://1:包含
				switch (propType) {
				case 0://文本
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(String.class) ){
					    	String valueInData = (String)filed.get(dp);
					    	if(exclude == 0) {
					    		if(StringUtils.contains(value, valueInData)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!StringUtils.contains(value, valueInData)) {
					    			checkRes= true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				case 1://数字
					break;
				case 2://日期
					break;
				}
				break;
			case 2://2:starts_with
				switch (propType) {
				case 0://文本
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(String.class) ){
					    	String valueInData = (String)filed.get(dp);
					    	if(exclude == 0) {
					    		if(StringUtils.startsWith(value, valueInData)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!StringUtils.startsWith(value, valueInData)) {
					    			checkRes= true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				}
				break;
			case 3://3:ends_with
				switch (propType) {
				case 0://文本
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(String.class) ){
					    	String valueInData = (String)filed.get(dp);
					    	if(exclude == 0) {
					    		if(StringUtils.endsWith(value, valueInData)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!StringUtils.endsWith(value, valueInData)) {
					    			checkRes= true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				}
				break;
			case 4://4:empty
				switch (propType) {
				case 0://文本
					if(exclude == 0) {
						if(StringUtils.isBlank(value)) {
							checkRes= true;
						}
					} else {
						if(!StringUtils.isBlank(value)) {
							checkRes= true;
						}
					}
					break;
				}
				break;
			case 5://5:大于
				switch (propType) {
				case 0://文本
					break;
				case 1://数字
					if(StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Byte.class) ||
					       filed.getType().isAssignableFrom(Integer.class)||
					       filed.getType().isAssignableFrom(Long.class)){
					    	Object valueInData = filed.get(dp);
					    	Long valueInDataLong = Long.parseLong(String.valueOf(valueInData));
					    	Long valueLong = Long.parseLong(value);
					    	if(exclude == 0) {
					    		if(valueInDataLong > valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	} else {
					    		if(valueInDataLong <= valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				case 2://日期
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Date.class) ){
					    	Date dateInData = (Date)filed.get(dp);
					    	Date date = DateUtil.getDateFromString(value, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
					    	if(exclude == 0) {
					    		if(dateInData.after(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!dateInData.after(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				}
				break;
			case 6://6:小于
				switch (propType) {
				case 0://文本
					break;
				case 1://数字
					if(StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Byte.class) ||
					       filed.getType().isAssignableFrom(Integer.class)||
					       filed.getType().isAssignableFrom(Long.class)){
					    	Object valueInData = filed.get(dp);
					    	Long valueInDataLong = Long.parseLong(String.valueOf(valueInData));
					    	Long valueLong = Long.parseLong(value);
					    	if(exclude == 0) {
					    		if(valueInDataLong < valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	} else {
					    		if(valueInDataLong >= valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				case 2://日期
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Date.class) ){
					    	Date dateInData = (Date)filed.get(dp);
					    	Date date = DateUtil.getDateFromString(value, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
					    	if(exclude == 0) {
					    		if(dateInData.before(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!dateInData.before(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				}
				break;
			case 7://7:大于等于
				switch (propType) {
				case 0://文本
					break;
				case 1://数字
					if(StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Byte.class) ||
					       filed.getType().isAssignableFrom(Integer.class)||
					       filed.getType().isAssignableFrom(Long.class)){
					    	Object valueInData = filed.get(dp);
					    	Long valueInDataLong = Long.parseLong(String.valueOf(valueInData));
					    	Long valueLong = Long.parseLong(value);
					    	if(exclude == 0) {
					    		if(valueInDataLong >= valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	} else {
					    		if(valueInDataLong < valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				case 2://日期
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Date.class) ){
					    	Date dateInData = (Date)filed.get(dp);
					    	Date date = DateUtil.getDateFromString(value, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
					    	if(exclude == 0) {
					    		if(dateInData.after(date) || dateInData.equals(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!dateInData.after(date) && !dateInData.equals(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				}
				break;
			case 8://8:小于等于
				switch (propType) {
				case 0://文本
					break;
				case 1://数字
					if(StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Byte.class) ||
					       filed.getType().isAssignableFrom(Integer.class)||
					       filed.getType().isAssignableFrom(Long.class)){
					    	Object valueInData = filed.get(dp);
					    	Long valueInDataLong = Long.parseLong(String.valueOf(valueInData));
					    	Long valueLong = Long.parseLong(value);
					    	if(exclude == 0) {
					    		if(valueInDataLong <= valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	} else {
					    		if(valueInDataLong > valueLong) {
					    			checkRes = true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				case 2://日期
					if(StringUtils.isBlank(value)) {
						break;
					}
					for(Field filed:fields){
					    if(filed.getType().isAssignableFrom(Date.class) ){
					    	Date dateInData = (Date)filed.get(dp);
					    	Date date = DateUtil.getDateFromString(value, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
					    	if(exclude == 0) {
					    		if(dateInData.before(date) || dateInData.equals(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	} else {
					    		if(!dateInData.before(date) && !dateInData.equals(date)) {
					    			checkRes= true;
					    			break;
					    		}
					    	}
					    }
					}
					break;
				}
				break;
			}
		}
		return checkRes;
	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelCampaignInnerTask(taskSchedule);
	}
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
