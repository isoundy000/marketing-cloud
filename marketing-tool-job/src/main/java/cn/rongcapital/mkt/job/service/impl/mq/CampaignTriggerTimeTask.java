package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.Calendar;
import java.util.List;

import javax.jms.MessageConsumer;

import cn.rongcapital.mkt.service.CampaignHeaderUpdateService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.vo.out.CampaignManualStartOut;

@Service
public class CampaignTriggerTimeTask extends BaseMQService implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(CampaignTriggerTimeTask.class);
	
    @Autowired
    private TaskScheduleDao taskScheduleDao;
    MessageConsumer consumer = null;
	@Autowired
	CampaignHeadDao campaignHeadDao;

    @Autowired
    CampaignHeaderUpdateService campaignHeaderUpdateService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		CampaignManualStartOut ur = checkPublishStatus(campaignHeadId);
		if(null != ur) {
			logger.error("活动不是发布状态，活动触发启动失败,campaignHeadId:"+campaignHeadId);
			return;//只有发布状态的活动才能被时间触发节点自动开启
		}	
		//检查细分状态,如果细分不存在或是未发布状态，则不能开启活动,如果检查通过，则设置用到的细分状态为:活动中
		CampaignManualStartOut urSegment = checkAndSetSegmentStatus(campaignHeadId);
		if(null != urSegment) {
			logger.error("活动使用的细分未发布或者不存在，活动触发启动失败,campaignHeadId:"+campaignHeadId);
			return;
		}
		//设置活动状态为:活动中
		CampaignHead t = new CampaignHead();
		t.setId(campaignHeadId);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS);
		campaignHeadDao.updateById(t);
		//激活该活动对应的全部任务
		taskScheduleDao.activateTaskByCampaignHeadId(campaignHeadId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		boolean isNeedCancel = false;//double check,查询数据库的任务表，看是否真的需要停止任务
		TaskSchedule taskScheduleT = new TaskSchedule();
//		taskScheduleT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		taskScheduleT.setCampaignHeadId(campaignHeadId);
		taskScheduleT.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_TRUGGER_TIME);
		List<TaskSchedule> taskScheduleList = taskScheduleDao.selectList(taskScheduleT);
		if(CollectionUtils.isNotEmpty(taskScheduleList)){
			TaskSchedule v = taskScheduleList.get(0);
			if(v.getStatus().byteValue() == ApiConstant.TABLE_DATA_STATUS_INVALID ||
//			   v.getTaskStatus().byteValue() == ApiConstant.TASK_STATUS_INVALID
//			   (v.getStartTime() != null && v.getStartTime().after(Calendar.getInstance().getTime())) ||
			   (v.getEndTime() != null && v.getEndTime().before(Calendar.getInstance().getTime()))
					) {
				isNeedCancel = true;
			}
		}
		if(false == isNeedCancel) {
			return;
		}
		//停止该活动对应的全部任务
		taskScheduleDao.deActivateTaskByCampaignHeadId(campaignHeadId);
		//如果活动状态为运行中，则设置活动状态为:已结束
		 CampaignHead t = new CampaignHead(); 
		 t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		 t.setId(campaignHeadId);
		 List<CampaignHead> camList = campaignHeadDao.selectList(t);
		 if(CollectionUtils.isNotEmpty(camList)) {
			 CampaignHead ch = camList.get(0);
			 campaignHeaderUpdateService.decreaseSegmentReferCampaignCount(campaignHeadId);
			 if(ch.getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS) {
				 t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH);
				 campaignHeadDao.updateById(t);
			 }
		 }
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}
	
}
