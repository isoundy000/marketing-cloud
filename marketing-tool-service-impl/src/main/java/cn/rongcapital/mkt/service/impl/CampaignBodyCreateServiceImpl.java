package cn.rongcapital.mkt.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CampaignActionSaveAudienceDao;
import cn.rongcapital.mkt.dao.CampaignActionSendH5Dao;
import cn.rongcapital.mkt.dao.CampaignActionSendPrivtDao;
import cn.rongcapital.mkt.dao.CampaignActionSendPubDao;
import cn.rongcapital.mkt.dao.CampaignActionSetTagDao;
import cn.rongcapital.mkt.dao.CampaignActionWaitDao;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPropCompareDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPrvtFriendsDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPubFansDao;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatForwardDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatReadDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatSentDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignNodeItemDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.CampaignTriggerTimerDao;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.po.CampaignActionSaveAudience;
import cn.rongcapital.mkt.po.CampaignActionSendH5;
import cn.rongcapital.mkt.po.CampaignActionSendPrivt;
import cn.rongcapital.mkt.po.CampaignActionSendPub;
import cn.rongcapital.mkt.po.CampaignActionSetTag;
import cn.rongcapital.mkt.po.CampaignActionWait;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.CampaignDecisionPropCompare;
import cn.rongcapital.mkt.po.CampaignDecisionPrvtFriends;
import cn.rongcapital.mkt.po.CampaignDecisionPubFans;
import cn.rongcapital.mkt.po.CampaignDecisionTag;
import cn.rongcapital.mkt.po.CampaignDecisionWechatForward;
import cn.rongcapital.mkt.po.CampaignDecisionWechatRead;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CampaignNodeItem;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.CampaignTriggerTimer;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.WechatGroup;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.service.CampaignBodyCreateService;
import cn.rongcapital.mkt.service.InsertCustomTagService;
import cn.rongcapital.mkt.vo.in.CampaignActionSaveAudienceIn;
import cn.rongcapital.mkt.vo.in.CampaignActionSendH5In;
import cn.rongcapital.mkt.vo.in.CampaignActionSendPrivtIn;
import cn.rongcapital.mkt.vo.in.CampaignActionSendPubIn;
import cn.rongcapital.mkt.vo.in.CampaignActionSetTagIn;
import cn.rongcapital.mkt.vo.in.CampaignActionWaitIn;
import cn.rongcapital.mkt.vo.in.CampaignAudienceTargetIn;
import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionPropCompareIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionPrvtFriendsIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionPubFansIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionTagIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionWechatForwardIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionWechatReadIn;
import cn.rongcapital.mkt.vo.in.CampaignNodeChainIn;
import cn.rongcapital.mkt.vo.in.CampaignSwitchIn;
import cn.rongcapital.mkt.vo.in.CampaignTriggerTimerIn;
import cn.rongcapital.mkt.vo.in.TagIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyCreateOut;

@Service
public class CampaignBodyCreateServiceImpl implements CampaignBodyCreateService {

	@Autowired
	private CampaignHeadDao campaignHeadDao;
	@Autowired
	private CampaignBodyDao campaignBodyDao;
	@Autowired
	private CampaignTriggerTimerDao campaignTriggerTimerDao;
	@Autowired
	private CampaignActionSaveAudienceDao campaignActionSaveAudienceDao;
	@Autowired
	private CampaignActionSendH5Dao campaignActionSendH5Dao;
	@Autowired
	private CampaignActionSendPrivtDao campaignActionSendPrivtDao;
	@Autowired
	private CampaignActionSendPubDao campaignActionSendPubDao;
	@Autowired
	private CampaignActionSetTagDao campaignActionSetTagDao;
	@Autowired
	private CampaignActionWaitDao campaignActionWaitDao;
	@Autowired
	private CampaignAudienceTargetDao CampaignAudienceTargetDao;
	@Autowired
	private CampaignDecisionPropCompareDao campaignDecisionPropCompareDao;
	@Autowired
	private CampaignDecisionPrvtFriendsDao campaignDecisionPrvtFriendsDao;
	@Autowired
	private CampaignDecisionPubFansDao campaignDecisionPubFansDao;
	@Autowired
	private CampaignDecisionTagDao campaignDecisionTagDao;
	@Autowired
	private CampaignDecisionWechatForwardDao campaignDecisionWechatForwardDao;
	@Autowired
	private CampaignDecisionWechatReadDao campaignDecisionWechatReadDao;
	@Autowired
	private CampaignDecisionWechatSentDao campaignDecisionWechatSentDao;
	@Autowired
	private CampaignSwitchDao campaignSwitchDao;
	@Autowired
	private CampaignNodeItemDao campaignNodeItemDao;
	@Autowired
	private TaskScheduleDao taskScheduleDao;
	@Autowired
	private WechatAssetDao wechatAssetDao;
	@Autowired
	private WechatAssetGroupDao wechatAssetGroupDao;
	@Autowired
	private WechatGroupDao wechatGroupDao;
	@Autowired
	private ImgTextAssetDao imgTextAssetDao;
	@Autowired
	private CustomTagDao customTagDao;
	//mongo要用到的dao
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private InsertCustomTagService insertCustomTagServiceImpl;
	
	private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CampaignBodyCreateOut campaignBodyCreate(CampaignBodyCreateIn body, SecurityContext securityContext) {
		DateFormat formatter = new SimpleDateFormat(ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
		jacksonObjectMapper.setDateFormat(formatter);
		int campaignHeadId = body.getCampaignHeadId();
		
		
		CampaignBodyCreateOut out = checkCampaignBiz(campaignHeadId);
		if(null != out) {
			return out;
		}
		deleteOldCampaignTask(campaignHeadId);//删除旧任务
		deleteOldCampaignData(campaignHeadId);//删除旧数据 
		for(CampaignNodeChainIn campaignNodeChainIn:body.getCampaignNodeChain()){
			Integer taskId= null;//定时任务id
			List<CampaignSwitch> campaignSwitchList = initCampaignSwitchList(campaignNodeChainIn,campaignHeadId);
			List<CampaignSwitch> campaignEndsList = initCampaignEndsList(campaignNodeChainIn,campaignHeadId);
			if(CollectionUtils.isNotEmpty(campaignSwitchList)) {
				for(CampaignSwitch c:campaignSwitchList) {
					campaignSwitchDao.insert(c);
				}
			} else if(CollectionUtils.isNotEmpty(campaignEndsList)) {
				for(CampaignSwitch c:campaignEndsList) {
					campaignSwitchDao.insert(c);
				}
			}
			if(campaignNodeChainIn.getNodeType() == ApiConstant.CAMPAIGN_NODE_TRIGGER){
				switch (campaignNodeChainIn.getItemType()) {
				case ApiConstant.CAMPAIGN_ITEM_TRIGGER_TIMMER://定时触发
					TaskSchedule taskSchedule = initTaskTimeTrigger(campaignNodeChainIn,campaignHeadId);
					if(null != taskSchedule) {
						taskScheduleDao.insert(taskSchedule);
						taskId = taskSchedule.getId();
					}
					CampaignTriggerTimer campaignTriggerTimer = initCampaignTriggerTimer(campaignNodeChainIn,campaignHeadId);
					if(null != campaignTriggerTimer) {
						campaignTriggerTimerDao.insert(campaignTriggerTimer);
					}
					break;
				}
			}
			if(campaignNodeChainIn.getNodeType() == ApiConstant.CAMPAIGN_NODE_AUDIENCE){
				switch (campaignNodeChainIn.getItemType()) {
				case ApiConstant.CAMPAIGN_ITEM_AUDIENCE_TARGET://目标人群
					TaskSchedule taskSchedule = initTaskAudienceTarget(campaignNodeChainIn,campaignHeadId);
					if(null != taskSchedule) {
						taskScheduleDao.insert(taskSchedule);
						
						taskId = taskSchedule.getId();
					}
					CampaignAudienceTarget campaignAudienceTarget = initCampaignAudienceTarget(campaignNodeChainIn,campaignHeadId);
					if(null != campaignAudienceTarget) {
						CampaignAudienceTargetDao.insert(campaignAudienceTarget);
					}
					break;
				}
			}
			if(campaignNodeChainIn.getNodeType() == ApiConstant.CAMPAIGN_NODE_DECISION){
				TaskSchedule taskSchedule = null;
				switch (campaignNodeChainIn.getItemType()) {
				case ApiConstant.CAMPAIGN_ITEM_DECISION_PROP_COMPARE://联系人属性比较
					taskSchedule = initTaskPropCompare(campaignNodeChainIn,campaignHeadId);
					taskScheduleDao.insert(taskSchedule);
					taskId = taskSchedule.getId();
					CampaignDecisionPropCompare campaignDecisionPropCompare = initCampaignDecisionPropCompare(campaignNodeChainIn,campaignHeadId);
					if(null != campaignDecisionPropCompare) {
						campaignDecisionPropCompareDao.insert(campaignDecisionPropCompare);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_SENT://微信图文是否发送(节点已去掉)
//					taskSchedule = initTaskWechatSent(campaignNodeChainIn,campaignHeadId);
//					taskScheduleDao.insert(taskSchedule);
//					taskId = taskSchedule.getId();
//					CampaignDecisionWechatSent campaignDecisionWechatSent = initCampaignDecisionWechatSent(campaignNodeChainIn,campaignHeadId);
//					campaignDecisionWechatSentDao.insert(campaignDecisionWechatSent);
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_READ://微信图文是否查看
					taskSchedule = initTaskWechatRead(campaignNodeChainIn,campaignHeadId);
					if(null != taskSchedule) {
						taskScheduleDao.insert(taskSchedule);
						taskId = taskSchedule.getId();
					}
					CampaignDecisionWechatRead campaignDecisionWechatRead = initCampaignDecisionWechatRead(campaignNodeChainIn,campaignHeadId);
					if(null != campaignDecisionWechatRead) {
						campaignDecisionWechatReadDao.insert(campaignDecisionWechatRead);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_FORWARD://微信图文是否转发
					taskSchedule = initTaskWechatForward(campaignNodeChainIn,campaignHeadId);
					if(null != taskSchedule) {
						taskScheduleDao.insert(taskSchedule);
						taskId = taskSchedule.getId();
					}
					CampaignDecisionWechatForward campaignDecisionWechatForward = initCampaignDecisionWechatForward(campaignNodeChainIn,campaignHeadId);
					if(null != campaignDecisionWechatForward) {
						campaignDecisionWechatForwardDao.insert(campaignDecisionWechatForward);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_IS_SUBSCRIBE://是否订阅公众号
					taskSchedule = initTaskWechatSubscribe(campaignNodeChainIn,campaignHeadId);
					if(null != taskSchedule) {
						taskScheduleDao.insert(taskSchedule);
						taskId = taskSchedule.getId();
					}
					CampaignDecisionPubFans campaignDecisionPubFans = initCampaignDecisionPubFans(campaignNodeChainIn,campaignHeadId);
					if(null != campaignDecisionPubFans) {
						campaignDecisionPubFansDao.insert(campaignDecisionPubFans);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_IS_PRIVT_FRIEND://是否个人号好友
					taskSchedule = initTaskWechatPrivFriend(campaignNodeChainIn,campaignHeadId);
					if(null != taskSchedule) {
						taskScheduleDao.insert(taskSchedule);
						taskId = taskSchedule.getId();
					}
					CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = initCampaignDecisionPrvtFriends(campaignNodeChainIn,campaignHeadId);
					if(null != campaignDecisionPrvtFriends) {
						campaignDecisionPrvtFriendsDao.insert(campaignDecisionPrvtFriends);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_TAG://标签判断
					taskSchedule = initTaskDecisionTag(campaignNodeChainIn,campaignHeadId);
					taskScheduleDao.insert(taskSchedule);
					taskId = taskSchedule.getId();
					CampaignDecisionTag campaignDecisionTag = initCampaignDecisionTag(campaignNodeChainIn,campaignHeadId);
					if(null != campaignDecisionTag) {
						campaignDecisionTagDao.insert(campaignDecisionTag);
					}
					break;
				}
			}
			if(campaignNodeChainIn.getNodeType() == ApiConstant.CAMPAIGN_NODE_ACTION){
				TaskSchedule taskSchedule = null;
				switch (campaignNodeChainIn.getItemType()) {
				case ApiConstant.CAMPAIGN_ITEM_ACTION_WAIT://等待
					taskSchedule = initTaskActionWait(campaignNodeChainIn,campaignHeadId);
					taskScheduleDao.insert(taskSchedule);
					taskId = taskSchedule.getId();
					CampaignActionWait campaignActionWait = initCampaignActionWait(campaignNodeChainIn,campaignHeadId);
					if(null != campaignActionWait) {
						campaignActionWaitDao.insert(campaignActionWait);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SAVE_AUDIENCE://保存当前人群
					CampaignActionSaveAudience campaignActionSaveAudience = initCampaignActionSaveAudience(campaignNodeChainIn,campaignHeadId);
					//如果audience_id参数为空,则在audience_list表增加1条记录
//					if(campaignActionSaveAudience.getAudienceId() == null) {
//						AudienceList audienceList = new AudienceList();
//						audienceList.setAudienceName(campaignActionSaveAudience.getName());
//						audienceListDao.insert(audienceList);
//						campaignActionSaveAudience.setAudienceId(audienceList.getId());
//					} else {
//						//TO DO:检查audience_list表里是否存在audience_id的记录?
//					}
					taskSchedule = initTaskActionSaveAudience(campaignNodeChainIn,campaignHeadId);
					taskScheduleDao.insert(taskSchedule);
					taskId = taskSchedule.getId();
					if(null != campaignActionSaveAudience) {
						campaignActionSaveAudienceDao.insert(campaignActionSaveAudience);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SET_TAG://设置标签
					CampaignActionSetTag campaignActionSetTag = initCampaignActionSetTag(campaignNodeChainIn,campaignHeadId);
					taskSchedule = initTaskActionSetTag(campaignNodeChainIn,campaignHeadId);
					taskScheduleDao.insert(taskSchedule);
					taskId = taskSchedule.getId();
					if(null != campaignActionSetTag) {
						campaignActionSetTagDao.insert(campaignActionSetTag);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_ADD_CAMPAIGN://添加到其它活动
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_MOVE_CAMPAIGN://转移到其它活动
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_WECHAT_H5://发送微信图文
					CampaignActionSendPub campaignActionSendPub = initCampaignActionSendPub(campaignNodeChainIn,campaignHeadId);
					taskSchedule = initTaskActionSendWechatH5(campaignNodeChainIn,campaignHeadId);
					taskScheduleDao.insert(taskSchedule);
					taskId = taskSchedule.getId();
					if(null != campaignActionSendPub) {
						campaignActionSendPubDao.insert(campaignActionSendPub);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_H5://发送H5图文
					CampaignActionSendH5 campaignActionSendH5 = initCampaignActionSendH5(campaignNodeChainIn,campaignHeadId);
					taskSchedule = initTaskActionSendH5(campaignNodeChainIn,campaignHeadId);
					taskScheduleDao.insert(taskSchedule);
					taskId = taskSchedule.getId();
					if(null != campaignActionSendH5) {
						campaignActionSendH5Dao.insert(campaignActionSendH5);
					}
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_PRVT_INFO://发送个人号消息
					taskSchedule = initTaskActionSendPrvInfo(campaignNodeChainIn,campaignHeadId);
					taskScheduleDao.insert(taskSchedule);
					taskId = taskSchedule.getId();
					CampaignActionSendPrivt campaignActionSendPrivt = initCampaignActionSendPrivt(campaignNodeChainIn,campaignHeadId);
					if(null != campaignActionSendPrivt) {
						campaignActionSendPrivtDao.insert(campaignActionSendPrivt);
					}
					break;
				}
			}
			//更新campaign_node_item表
			CampaignNodeItem campaignNodeItem = initCampaignNodeItem(campaignNodeChainIn, campaignHeadId);
			if(null != campaignNodeItem) {
				campaignNodeItemDao.updateById(campaignNodeItem);
			}
			CampaignBody campaignBody = initCampaignBody(campaignNodeChainIn,campaignHeadId,taskId);
			if(null != campaignBody) {
				campaignBodyDao.insert(campaignBody);
			}
		}
		out = new CampaignBodyCreateOut(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		return out;
	}

	/**
	 * 校验业务逻辑:
	 * 1.活动头必须存在且只存在1个
	 * 2.只有未发布和已发布状态的活动才能编辑/创建 body
	 * @return
	 */
	private CampaignBodyCreateOut checkCampaignBiz(int campaignHeadId) {
		CampaignBodyCreateOut out = null;
		CampaignHead ch = new CampaignHead();
		ch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		ch.setId(campaignHeadId);
		List<CampaignHead> campaignHeadList = campaignHeadDao.selectList(ch);
		
		if(CollectionUtils.isNotEmpty(campaignHeadList)){
			CampaignHead campaignHead = campaignHeadList.get(0);
			if(campaignHead.getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS) {
				out = new CampaignBodyCreateOut(ApiErrorCode.BIZ_ERROR_CANPAIGN_IN_PROGRESS.getCode(),
												ApiErrorCode.BIZ_ERROR_CANPAIGN_IN_PROGRESS.getMsg(),
												ApiConstant.INT_ZERO,null);
			} else if(campaignHead.getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH) {
				out = new CampaignBodyCreateOut(ApiErrorCode.BIZ_ERROR_CANPAIGN_FINISH.getCode(),
													   ApiErrorCode.BIZ_ERROR_CANPAIGN_FINISH.getMsg(),
													   ApiConstant.INT_ZERO,null);
			}

		}else{
			out = new CampaignBodyCreateOut(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
								 			ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(),
								 			ApiConstant.INT_ZERO,null);
		 
		}
		return out;
	}
	
	private void deleteOldCampaignData (int campaignHeadId) {
		campaignSwitchDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionWechatSentDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionWechatReadDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionWechatForwardDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionTagDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionPubFansDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionPrvtFriendsDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionPropCompareDao.deleteByCampaignHeadId(campaignHeadId);
		CampaignAudienceTargetDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionWaitDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSetTagDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSendPubDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSendPrivtDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSendH5Dao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSaveAudienceDao.deleteByCampaignHeadId(campaignHeadId);
		campaignTriggerTimerDao.deleteByCampaignHeadId(campaignHeadId);
		campaignBodyDao.deleteByCampaignHeadId(campaignHeadId);
	}
	
	private void deleteOldCampaignTask (int campaignHeadId) {
		CampaignBody campaignBody = new CampaignBody();
		campaignBody.setHeadId(campaignHeadId);
		List<CampaignBody> campaignBodyList = campaignBodyDao.selectList(campaignBody);
		for(CampaignBody cb:campaignBodyList) {
			if(null != cb.getTaskId()) {
				taskScheduleDao.physicalDeleteById(cb.getTaskId());
			}
		}
	}
	
	private TaskSchedule initTaskTimeTrigger(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		CampaignTriggerTimerIn campaignTriggerTimerIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignTriggerTimerIn.class);
		if(null == campaignTriggerTimerIn) return null;
		Date startTime = DateUtil.getDateFromString(campaignTriggerTimerIn.getStartTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
		Date endTime = DateUtil.getDateFromString(campaignTriggerTimerIn.getEndTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
		taskSchedule.setStartTime(startTime);
		taskSchedule.setEndTime(endTime);
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_TRUGGER_TIME);
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		CampaignHead ch =  new CampaignHead();
		ch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		ch.setId(campaignHeadId);
		List<CampaignHead> chList = campaignHeadDao.selectList(ch);
		if(CollectionUtils.isNotEmpty(chList)){
			if(chList.get(0).getPublishStatus()==ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH) {
				taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_VALID);//如果活动是已发布状态,设置触发任务为可运行状态
			}else {
				taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//如果活动不是已发布状态,设置触发任务为不可运行状态
			}
		} else {
			taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//如果没有查询到活动head,设置触发任务为不可运行状态
		}
		return taskSchedule;
	}
	
	private Float tranlateToMinutes(Integer refreshIntv,Byte refreshType) {
		if(null == refreshIntv || refreshType == null) {
			return null;
		}
		Float intervalMinutes = null;
		switch (refreshType) {
		case 0://小时
			intervalMinutes = 60f * refreshIntv;
			break;
		case 1://天
			intervalMinutes = 60 * 24f * refreshIntv;
			break;
		case 2://周
			intervalMinutes = 60 * 24 * 7f * refreshIntv;
			break;
		case 3://月
			intervalMinutes = 60 * 24 * 30f * refreshIntv;
			break;
		case 4://分
			intervalMinutes = 1f * refreshIntv;
			break;
		default:
			break;
		}
		return intervalMinutes;
	}
	
	private TaskSchedule initTaskAudienceTarget(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignAudienceTargetIn campaignAudienceTargetIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignAudienceTargetIn.class);
		if(null != campaignAudienceTargetIn) {
			Byte allowedNew = campaignAudienceTargetIn.getAllowedNew();
			if(null!=allowedNew) {
				if(allowedNew==0) {
					TaskSchedule taskSchedule = new TaskSchedule();
					Float intervalMinutes = tranlateToMinutes(campaignAudienceTargetIn.getRefreshInterval(),
							campaignAudienceTargetIn.getRefreshIntervalType());
					taskSchedule.setIntervalMinutes(intervalMinutes);
					taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_AUDIENCE_TARGET);
					taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
					taskSchedule.setCampaignHeadId(campaignHeadId);
					taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
					return taskSchedule;
				}
				if(allowedNew==1) {
					TaskSchedule taskSchedule = new TaskSchedule();
					taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_AUDIENCE_TARGET);
					taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
					taskSchedule.setCampaignHeadId(campaignHeadId);
					taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
					return taskSchedule;
				}
			}
		}
		return null;
	}
	
	private TaskSchedule initTaskPropCompare(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_DECISION_PROP_COMPARE);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	
//	private TaskSchedule initTaskWechatSent(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
//		CampaignDecisionWechatSentIn campaignDecisionWechatSentIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatSentIn.class);
//		TaskSchedule taskSchedule = new TaskSchedule();
//		Integer intervalMinutes = tranlateToMinutes(campaignDecisionWechatSentIn.getRefreshInterval(), 
//													campaignDecisionWechatSentIn.getRefreshIntervalType());
//		taskSchedule.setIntervalMinutes(intervalMinutes);
//		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_DECISION_WECHAT_SENT);
//		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
//		taskSchedule.setCampaignHeadId(campaignHeadId);
//		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
//		return taskSchedule;
//	}
	
	private TaskSchedule initTaskWechatRead(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionWechatReadIn campaignDecisionWechatReadIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatReadIn.class);
		if(null == campaignDecisionWechatReadIn) return null;
		TaskSchedule taskSchedule = new TaskSchedule();
		Float intervalMinutes = tranlateToMinutes(campaignDecisionWechatReadIn.getRefreshInterval(), 
													campaignDecisionWechatReadIn.getRefreshIntervalType());
		taskSchedule.setIntervalMinutes(intervalMinutes);
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_DECISION_WECHAT_READ);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	
	private TaskSchedule initTaskWechatForward(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionWechatForwardIn campaignDecisionWechatForwardIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatForwardIn.class);
		if(null == campaignDecisionWechatForwardIn) return null;
		TaskSchedule taskSchedule = new TaskSchedule();
		Float intervalMinutes = tranlateToMinutes(campaignDecisionWechatForwardIn.getRefreshInterval(), 
													campaignDecisionWechatForwardIn.getRefreshIntervalType());
		taskSchedule.setIntervalMinutes(intervalMinutes);
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_DECISION_WECHAT_FORWARD);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	
	private TaskSchedule initTaskWechatSubscribe(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionPubFansIn campaignDecisionPubFansIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionPubFansIn.class);
		if(null == campaignDecisionPubFansIn) return null;
		TaskSchedule taskSchedule = new TaskSchedule();
		Float intervalMinutes = tranlateToMinutes(campaignDecisionPubFansIn.getRefreshInterval(), 
													campaignDecisionPubFansIn.getRefreshIntervalType());
		taskSchedule.setIntervalMinutes(intervalMinutes);
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_DECISION_WECHAT_SUBSCRIBE);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	
	private TaskSchedule initTaskWechatPrivFriend(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionPrvtFriendsIn campaignDecisionPrvtFriendsIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionPrvtFriendsIn.class);
		if(null == campaignDecisionPrvtFriendsIn) return null;
		TaskSchedule taskSchedule = new TaskSchedule();
		Float intervalMinutes = tranlateToMinutes(campaignDecisionPrvtFriendsIn.getRefreshInterval(), 
													campaignDecisionPrvtFriendsIn.getRefreshIntervalType());
		taskSchedule.setIntervalMinutes(intervalMinutes);
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_DECISION_WECHAT_PRV_FRIEND);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	
	private TaskSchedule initTaskDecisionTag(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_DECISION_TAG);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	private TaskSchedule initTaskActionWait(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_ACTION_WAIT);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	private TaskSchedule initTaskActionSaveAudience(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_ACTION_SAVE_AUDIENCE);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	private TaskSchedule initTaskActionSetTag(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_ACTION_SET_TAG);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	private TaskSchedule initTaskActionSendWechatH5(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setTaskName(ApiConstant.TASK_NAME_CAMPAIGN_ACTION_PUBWECHAT_SEND_H5);
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_ACTION_PUBWECHAT_SEND_H5);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	private TaskSchedule initTaskActionSendH5(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_ACTION_WECHAT_SEND_H5);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	private TaskSchedule initTaskActionSendPrvInfo(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_ACTION_WECHAT_PRV_SEND_INFO);
		taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);//新增的任务,默认设置为不可运行
		taskSchedule.setCampaignHeadId(campaignHeadId);
		taskSchedule.setCampaignItemId(campaignNodeChainIn.getItemId());
		return taskSchedule;
	}
	private List<CampaignSwitch> initCampaignSwitchList(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		List<CampaignSwitchIn> campaignSwitchInList = campaignNodeChainIn.getCampaignSwitchList();
		List<CampaignSwitch> campaignSwitchList = new ArrayList<CampaignSwitch>();
		if(CollectionUtils.isNotEmpty(campaignSwitchInList)) {
			for(int i=0;i<campaignSwitchInList.size();i++) {
				CampaignSwitchIn campaignSwitchIn = campaignSwitchInList.get(i);
				if(null == campaignSwitchIn) {
					continue;//为null表示该是非分支上没有连线
				}
				CampaignSwitch campaignSwitch = new CampaignSwitch();
				campaignSwitch.setCampaignHeadId(campaignHeadId);
				campaignSwitch.setItemId(campaignNodeChainIn.getItemId());
				campaignSwitch.setColor(campaignSwitchIn.getColor());
				campaignSwitch.setNextItemId(campaignSwitchIn.getNextItemId());
				if(i == 0) {
					campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_SWITCH_YES);
				}else {
					campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_SWITCH_NO);
				}
				campaignSwitch.setDrawType(campaignSwitchIn.getDrawType());
				campaignSwitchList.add(campaignSwitch);
			}
		}
		return campaignSwitchList;
	}
	
	private List<CampaignSwitch> initCampaignEndsList(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		List<CampaignSwitchIn> campaignEndsInList = campaignNodeChainIn.getCampaignEndsList();
		List<CampaignSwitch> campaignSwitchList = new ArrayList<CampaignSwitch>();
		if(CollectionUtils.isNotEmpty(campaignEndsInList)) {
			for(CampaignSwitchIn campaignSwitchIn:campaignEndsInList){
				CampaignSwitch campaignSwitch = new CampaignSwitch();
				campaignSwitch.setCampaignHeadId(campaignHeadId);
				campaignSwitch.setItemId(campaignNodeChainIn.getItemId());
				campaignSwitch.setColor(campaignSwitchIn.getColor());
				campaignSwitch.setNextItemId(campaignSwitchIn.getNextItemId());
				campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_ENDS);
				campaignSwitch.setDrawType(campaignSwitchIn.getDrawType());
				campaignSwitchList.add(campaignSwitch);
			}
		}
		return campaignSwitchList;
	}
	
	private CampaignActionSendPrivt initCampaignActionSendPrivt(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSendPrivt campaignActionSendPrivt = new CampaignActionSendPrivt();
		CampaignActionSendPrivtIn campaignActionSendPrivtIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSendPrivtIn.class);
		if(null == campaignActionSendPrivtIn) return null;
		campaignActionSendPrivt.setName(campaignActionSendPrivtIn.getName());
		campaignActionSendPrivt.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSendPrivt.setCampaignHeadId(campaignHeadId);
		campaignActionSendPrivt.setAssetId(campaignActionSendPrivtIn.getAssetId());
		campaignActionSendPrivt.setGroupId(campaignActionSendPrivtIn.getGroupId());
		campaignActionSendPrivt.setTextInfo(campaignActionSendPrivtIn.getTextInfo());
		
		if(null != campaignActionSendPrivtIn.getAssetId()) {
			WechatAsset wechatAssetT = new WechatAsset();
			wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetT.setAssetId(campaignActionSendPrivtIn.getAssetId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
			if(CollectionUtils.isNotEmpty(wechatAssetList)) {
				WechatAsset wechatAsset = wechatAssetList.get(0);
				String uin = wechatAsset.getWxAcct();
				campaignActionSendPrivt.setUin(uin);//写入uin
			}
		}
		if(null != campaignActionSendPrivtIn.getGroupId()) {
			WechatAssetGroup wechatAssetGroupT = new WechatAssetGroup();
			wechatAssetGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetGroupT.setId(campaignActionSendPrivtIn.getGroupId().longValue());
			List<WechatAssetGroup> wechatAssetGroupList =  wechatAssetGroupDao.selectList(wechatAssetGroupT);
			if(CollectionUtils.isNotEmpty(wechatAssetGroupList)) {
				Long importId = wechatAssetGroupList.get(0).getImportGroupId();
				if(null != importId) {
					WechatGroup wechatGroupT = new WechatGroup();
					wechatGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					wechatGroupT.setId(importId.intValue());
					List<WechatGroup> wechatGroupList = wechatGroupDao.selectList(wechatGroupT);
					if(CollectionUtils.isNotEmpty(wechatGroupList)) {
						String ucode = wechatGroupList.get(0).getGroupId();
						campaignActionSendPrivt.setUcode(ucode);//写入ucode
					}
				}
			}
		}
		return campaignActionSendPrivt;
	}
	
	private CampaignActionSendH5 initCampaignActionSendH5(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSendH5 campaignActionSendH5 = new CampaignActionSendH5();
		CampaignActionSendH5In campaignActionSendH5In = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSendH5In.class);
		if(null == campaignActionSendH5In) return null;
		campaignActionSendH5.setName(campaignActionSendH5In.getName());
		campaignActionSendH5.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSendH5.setCampaignHeadId(campaignHeadId);
		campaignActionSendH5.setPubAssetId(campaignActionSendH5In.getPubAssetId());
		campaignActionSendH5.setImgTextAssetId(campaignActionSendH5In.getImgTextAssetId());
		if(null != campaignActionSendH5In.getPubAssetId()) {
			WechatAsset wechatAssetT = new WechatAsset();
			wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetT.setAssetId(campaignActionSendH5In.getPubAssetId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
			if(CollectionUtils.isNotEmpty(wechatAssetList)) {
				WechatAsset wechatAsset = wechatAssetList.get(0);
				campaignActionSendH5.setPubId(wechatAsset.getWxAcct());
			}
		}
		campaignActionSendH5.setPrvAssetId(campaignActionSendH5In.getPrvAssetId());
		campaignActionSendH5.setGroupId(campaignActionSendH5In.getGroupId());
		if(null != campaignActionSendH5In.getPrvAssetId()) {
			WechatAsset wechatAssetT = new WechatAsset();
			wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetT.setAssetId(campaignActionSendH5In.getPrvAssetId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
			if(CollectionUtils.isNotEmpty(wechatAssetList)) {
				WechatAsset wechatAsset = wechatAssetList.get(0);
				String uin = wechatAsset.getWxAcct();
				campaignActionSendH5.setUin(uin);//写入uin
			}
		}
		if(null != campaignActionSendH5In.getGroupId()) {
			WechatAssetGroup wechatAssetGroupT = new WechatAssetGroup();
			wechatAssetGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetGroupT.setId(campaignActionSendH5In.getGroupId().longValue());
			List<WechatAssetGroup> wechatAssetGroupList =  wechatAssetGroupDao.selectList(wechatAssetGroupT);
			if(CollectionUtils.isNotEmpty(wechatAssetGroupList)) {
				Long importId = wechatAssetGroupList.get(0).getImportGroupId();
				if(null != importId) {
					WechatGroup wechatGroupT = new WechatGroup();
					wechatGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					wechatGroupT.setId(importId.intValue());
					List<WechatGroup> wechatGroupList = wechatGroupDao.selectList(wechatGroupT);
					if(CollectionUtils.isNotEmpty(wechatGroupList)) {
						String ucode = wechatGroupList.get(0).getGroupId();
						campaignActionSendH5.setUcode(ucode);//写入ucode
					}
				}
			}
		}
		return campaignActionSendH5;
	}
	
	private CampaignActionSendPub initCampaignActionSendPub(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSendPub campaignActionSendPub = new CampaignActionSendPub();
		CampaignActionSendPubIn campaignActionSendPubIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSendPubIn.class);
		if(null == campaignActionSendPubIn) return null;
		campaignActionSendPub.setName(campaignActionSendPubIn.getName());
		campaignActionSendPub.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSendPub.setCampaignHeadId(campaignHeadId);
		campaignActionSendPub.setAssetId(campaignActionSendPubIn.getAssetId());
		campaignActionSendPub.setImgTextAssetId(campaignActionSendPubIn.getImgTextAssetId());
		if(null != campaignActionSendPubIn.getImgTextAssetId()) {
			ImgTextAsset imgTextAssetT = new ImgTextAsset();
			imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			imgTextAssetT.setId(campaignActionSendPubIn.getImgTextAssetId());
			List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
			if(CollectionUtils.isNotEmpty(imgTextAssetList)) {
				ImgTextAsset imgTextAsset = imgTextAssetList.get(0);
				//TODO  需要修改字段类型
				if(StringUtils.isNotBlank(imgTextAsset.getMaterialId())) {
					//campaignActionSendPub.setMaterialId(Integer.parseInt(imgTextAsset.getMaterialId()));
					campaignActionSendPub.setMaterialId(imgTextAsset.getMaterialId());
				}
			}
		}
		if(null != campaignActionSendPubIn.getAssetId()) {
			WechatAsset wechatAssetT = new WechatAsset();
			wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetT.setAssetId(campaignActionSendPubIn.getAssetId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
			if(CollectionUtils.isNotEmpty(wechatAssetList)) {
				WechatAsset wechatAsset = wechatAssetList.get(0);
				campaignActionSendPub.setPubId(wechatAsset.getWxAcct());
			}
		}
		return campaignActionSendPub;
	}
	
	private CampaignActionSetTag initCampaignActionSetTag(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSetTag campaignActionSetTag = new CampaignActionSetTag();
		CampaignActionSetTagIn campaignActionSetTagIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSetTagIn.class);
		if(null == campaignActionSetTagIn) return null;
		campaignActionSetTag.setName(campaignActionSetTagIn.getName());
		campaignActionSetTag.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSetTag.setCampaignHeadId(campaignHeadId);
		List<String> tagNameList = campaignActionSetTagIn.getTagNames();
		if(tagNameList == null || tagNameList.isEmpty()) {
		    campaignActionSetTag.setTagNames("");
		}
		if(CollectionUtils.isNotEmpty(tagNameList)) {
			List<String> tagIdList = new ArrayList<String>();
			for(String tagName:tagNameList) {
			    //将这个逻辑存到mongo
			    BaseTag baseTag =  new CustomTagLeaf();
			    baseTag.setStatus((int)ApiConstant.TABLE_DATA_STATUS_VALID);//数据正常
			    baseTag.setTagName(tagName);
			    String tagSource = TagSourceEnum.CAMPAIGN_SOURCE_ACCESS.getTagSourceName();
			    baseTag.setSource(tagSource);
			    Query query = new Query(Criteria.where("tag_name").is(baseTag.getTagName()).and("status").is(baseTag.getStatus()).and("source").is(tagSource));
			    BaseTag baseTagTemp = mongoTemplate.findOne(query,BaseTag.class);
			    String cutomTagId = null;
			    if(baseTagTemp != null){
			        cutomTagId = baseTagTemp.getTagId();
			    }else {
			       // mongoTemplate.insert(objectToSave);
			        BaseTag baseTagResult = insertCustomTagServiceImpl.insertCustomTagLeafFromSystemIn(tagName, tagSource);
			        cutomTagId = baseTagResult.getTagId();
			    }
			    if(null != cutomTagId) {
                    tagIdList.add(cutomTagId);
                }
			/*	CustomTag customTagT = new CustomTag();
				customTagT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				customTagT.setName(tagName);
				List<CustomTag> customTagList = customTagDao.selectList(customTagT);
				Integer cutomTagId = null;
				if(CollectionUtils.isNotEmpty(customTagList)) {
					cutomTagId = customTagList.get(0).getId();
				} else {
					//插入该tag到custom_tag表
					customTagT = new CustomTag();
					customTagT.setName(tagName);
					customTagDao.insert(customTagT);
					cutomTagId = customTagT.getId();
				}
				if(null != cutomTagId) {
					tagIdList.add(cutomTagId);
				}*/
			}
			if(tagIdList.size() > 0){
			    campaignActionSetTag.setTagIds(StringUtils.join(tagIdList,","));
			}else {
			    campaignActionSetTag.setTagIds("");
			}
			
			campaignActionSetTag.setTagNames(StringUtils.join(tagNameList,","));
		}
		return campaignActionSetTag;
	}
	
	private CampaignActionSaveAudience initCampaignActionSaveAudience(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSaveAudience campaignActionSaveAudience = new CampaignActionSaveAudience();
		CampaignActionSaveAudienceIn campaignActionSaveAudienceIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSaveAudienceIn.class);
		if(null == campaignActionSaveAudienceIn) return null;
		campaignActionSaveAudience.setName(campaignActionSaveAudienceIn.getName());
		campaignActionSaveAudience.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSaveAudience.setCampaignHeadId(campaignHeadId);
		campaignActionSaveAudience.setAudienceId(campaignActionSaveAudienceIn.getAudienceId());
		campaignActionSaveAudience.setAudienceName(campaignActionSaveAudienceIn.getAudienceName());
		return campaignActionSaveAudience;
	}
	
	private CampaignActionWait initCampaignActionWait(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionWait campaignActionWait = new CampaignActionWait();
		CampaignActionWaitIn campaignActionWaitIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionWaitIn.class);
		if(null == campaignActionWaitIn) return null;
		campaignActionWait.setName(campaignActionWaitIn.getName());
		campaignActionWait.setItemId(campaignNodeChainIn.getItemId());
		campaignActionWait.setCampaignHeadId(campaignHeadId);
		campaignActionWait.setType(campaignActionWaitIn.getType());
		campaignActionWait.setRelativeValue(campaignActionWaitIn.getRelativeValue());
		campaignActionWait.setRelativeType(campaignActionWaitIn.getRelativeType());
		campaignActionWait.setSpecificTime(campaignActionWaitIn.getSpecificTime());
		return campaignActionWait;
	}
	
	private CampaignDecisionTag initCampaignDecisionTag(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionTag campaignDecisionTag = new CampaignDecisionTag();
		CampaignDecisionTagIn campaignDecisionTagIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionTagIn.class);
		if(null == campaignDecisionTagIn) return null;
		campaignDecisionTag.setName(campaignDecisionTagIn.getName());
		campaignDecisionTag.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionTag.setCampaignHeadId(campaignHeadId);
		campaignDecisionTag.setRule(campaignDecisionTagIn.getRule());
		List<TagIn> tagList =  campaignDecisionTagIn.getTags();
		if(CollectionUtils.isNotEmpty(tagList)) {//TO DO:优化,前端参数直接传递1个逗号分隔的tag_id的字符串
			List<String> tagIdList = new ArrayList<String>();
			for(TagIn tagIn:tagList) {
				tagIdList.add(tagIn.getTag_id());
			}
			String tagIds = StringUtils.join(tagIdList, ",");
			campaignDecisionTag.setTagIds(tagIds);
		}
		return campaignDecisionTag;
	}
	
	private CampaignDecisionPrvtFriends initCampaignDecisionPrvtFriends(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = new CampaignDecisionPrvtFriends();
		CampaignDecisionPrvtFriendsIn campaignDecisionPrvtFriendsIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionPrvtFriendsIn.class);
		if(null == campaignDecisionPrvtFriendsIn) return null;
		campaignDecisionPrvtFriends.setName(campaignDecisionPrvtFriendsIn.getName());
		campaignDecisionPrvtFriends.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionPrvtFriends.setCampaignHeadId(campaignHeadId);
		campaignDecisionPrvtFriends.setAssetId(campaignDecisionPrvtFriendsIn.getAssetId());
		campaignDecisionPrvtFriends.setGroupId(campaignDecisionPrvtFriendsIn.getGroupId());
		campaignDecisionPrvtFriends.setRefreshInterval(campaignDecisionPrvtFriendsIn.getRefreshInterval());
		campaignDecisionPrvtFriends.setRefreshIntervalType(campaignDecisionPrvtFriendsIn.getRefreshIntervalType());
		if(null != campaignDecisionPrvtFriendsIn.getAssetId()) {
			WechatAsset wechatAssetT = new WechatAsset();
			wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetT.setAssetId(campaignDecisionPrvtFriendsIn.getAssetId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
			if(CollectionUtils.isNotEmpty(wechatAssetList)) {
				WechatAsset wechatAsset = wechatAssetList.get(0);
				String uin = wechatAsset.getWxAcct();
				campaignDecisionPrvtFriends.setUin(uin);//写入uin
			}
		}
		if(null != campaignDecisionPrvtFriendsIn.getGroupId()) {
			WechatAssetGroup wechatAssetGroupT = new WechatAssetGroup();
			wechatAssetGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetGroupT.setId(campaignDecisionPrvtFriendsIn.getGroupId().longValue());
			List<WechatAssetGroup> wechatAssetGroupList =  wechatAssetGroupDao.selectList(wechatAssetGroupT);
			if(CollectionUtils.isNotEmpty(wechatAssetGroupList)) {
				Long importId = wechatAssetGroupList.get(0).getImportGroupId();
				if(null != importId) {
					WechatGroup wechatGroupT = new WechatGroup();
					wechatGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					wechatGroupT.setId(importId.intValue());
					List<WechatGroup> wechatGroupList = wechatGroupDao.selectList(wechatGroupT);
					if(CollectionUtils.isNotEmpty(wechatGroupList)) {
						String ucode = wechatGroupList.get(0).getGroupId();
						campaignDecisionPrvtFriends.setUcode(ucode);//写入ucode
					}
				}
			}
		}
		return campaignDecisionPrvtFriends;
	}
	
	private CampaignDecisionPubFans initCampaignDecisionPubFans(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionPubFans campaignDecisionPubFans = new CampaignDecisionPubFans();
		CampaignDecisionPubFansIn campaignDecisionPubFansIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionPubFansIn.class);
		if(null == campaignDecisionPubFansIn) return null;
		campaignDecisionPubFans.setName(campaignDecisionPubFansIn.getName());
		campaignDecisionPubFans.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionPubFans.setCampaignHeadId(campaignHeadId);
		campaignDecisionPubFans.setAssetId(campaignDecisionPubFansIn.getAssetId());
		campaignDecisionPubFans.setSubscribeTime(campaignDecisionPubFansIn.getSubscribeTime());
		campaignDecisionPubFans.setRefreshInterval(campaignDecisionPubFansIn.getRefreshInterval());
		campaignDecisionPubFans.setRefreshIntervalType(campaignDecisionPubFansIn.getRefreshIntervalType());
		if(null != campaignDecisionPubFansIn.getAssetId()) {
			WechatAsset wechatAssetT = new WechatAsset();
			wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetT.setAssetId(campaignDecisionPubFansIn.getAssetId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
			if(CollectionUtils.isNotEmpty(wechatAssetList)) {
				WechatAsset wechatAsset = wechatAssetList.get(0);
				campaignDecisionPubFans.setPubId(wechatAsset.getWxAcct());
			}
		}
		return campaignDecisionPubFans;
	}
	
	private CampaignDecisionWechatForward initCampaignDecisionWechatForward(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionWechatForward campaignDecisionWechatForward = new CampaignDecisionWechatForward();
		CampaignDecisionWechatForwardIn campaignDecisionWechatForwardIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatForwardIn.class);
		if(null == campaignDecisionWechatForwardIn) return null;
		campaignDecisionWechatForward.setName(campaignDecisionWechatForwardIn.getName());
		campaignDecisionWechatForward.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionWechatForward.setCampaignHeadId(campaignHeadId);
		campaignDecisionWechatForward.setAssetId(campaignDecisionWechatForwardIn.getAssetId());
		campaignDecisionWechatForward.setImgTextAssetId(campaignDecisionWechatForwardIn.getImgTextAssetId());
		campaignDecisionWechatForward.setRefreshInterval(campaignDecisionWechatForwardIn.getRefreshInterval());
		campaignDecisionWechatForward.setRefreshIntervalType(campaignDecisionWechatForwardIn.getRefreshIntervalType());
		campaignDecisionWechatForward.setForwardTimes(campaignDecisionWechatForwardIn.getForwardTimes());
		if(null != campaignDecisionWechatForwardIn.getAssetId()) {
			WechatAsset wechatAssetT = new WechatAsset();
			wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetT.setAssetId(campaignDecisionWechatForwardIn.getAssetId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
			if(CollectionUtils.isNotEmpty(wechatAssetList)) {
				WechatAsset wechatAsset = wechatAssetList.get(0);
				campaignDecisionWechatForward.setPubId(wechatAsset.getWxAcct());
			}
		}
		if(null !=campaignDecisionWechatForwardIn.getImgTextAssetId()) {
			ImgTextAsset imgTextAssetT = new ImgTextAsset();
			imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			imgTextAssetT.setId(campaignDecisionWechatForwardIn.getImgTextAssetId());
			List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
			if(CollectionUtils.isNotEmpty(imgTextAssetList)) {
				ImgTextAsset imgTextAsset = imgTextAssetList.get(0);
				if(StringUtils.isNotBlank(imgTextAsset.getMaterialId())) {
					campaignDecisionWechatForward.setMaterialId(Integer.parseInt(imgTextAsset.getMaterialId()));
				}
			}
		}
		return campaignDecisionWechatForward;
	}
	
	private CampaignDecisionWechatRead initCampaignDecisionWechatRead(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionWechatRead campaignDecisionWechatRead = new  CampaignDecisionWechatRead();
		CampaignDecisionWechatReadIn campaignDecisionWechatReadIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatReadIn.class);
		if(null == campaignDecisionWechatReadIn) return null;
		campaignDecisionWechatRead.setName(campaignDecisionWechatReadIn.getName());
		campaignDecisionWechatRead.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionWechatRead.setCampaignHeadId(campaignHeadId);
		campaignDecisionWechatRead.setAssetId(campaignDecisionWechatReadIn.getAssetId());
		campaignDecisionWechatRead.setRefreshInterval(campaignDecisionWechatReadIn.getRefreshInterval());
		campaignDecisionWechatRead.setRefreshIntervalType(campaignDecisionWechatReadIn.getRefreshIntervalType());
		campaignDecisionWechatRead.setImgTextAssetId(campaignDecisionWechatReadIn.getImgTextAssetId());
		campaignDecisionWechatRead.setReadTime(campaignDecisionWechatReadIn.getReadTime());
		campaignDecisionWechatRead.setReadPercent(campaignDecisionWechatReadIn.getReadPercent());
		if(null !=campaignDecisionWechatReadIn.getAssetId()) {
			WechatAsset wechatAssetT = new WechatAsset();
			wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatAssetT.setAssetId(campaignDecisionWechatReadIn.getAssetId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
			if(CollectionUtils.isNotEmpty(wechatAssetList)) {
				WechatAsset wechatAsset = wechatAssetList.get(0);
				campaignDecisionWechatRead.setPubId(wechatAsset.getWxAcct());
			}
		}
		if(null !=campaignDecisionWechatReadIn.getImgTextAssetId()) {
			ImgTextAsset imgTextAssetT = new ImgTextAsset();
			imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			imgTextAssetT.setId(campaignDecisionWechatReadIn.getImgTextAssetId());
			List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
			if(CollectionUtils.isNotEmpty(imgTextAssetList)) {
				ImgTextAsset imgTextAsset = imgTextAssetList.get(0);
				if(StringUtils.isNotBlank(imgTextAsset.getMaterialId())) {
					campaignDecisionWechatRead.setMaterialId(Integer.parseInt(imgTextAsset.getMaterialId()));
				}
			}
		}
		return campaignDecisionWechatRead;
	}
	
//	private CampaignDecisionWechatSent initCampaignDecisionWechatSent(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
//		CampaignDecisionWechatSent campaignDecisionWechatSent = new  CampaignDecisionWechatSent();
//		CampaignDecisionWechatSentIn campaignDecisionWechatSentIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatSentIn.class);
//		campaignDecisionWechatSent.setName(campaignDecisionWechatSentIn.getName());
//		campaignDecisionWechatSent.setItemId(campaignNodeChainIn.getItemId());
//		campaignDecisionWechatSent.setCampaignHeadId(campaignHeadId);
//		campaignDecisionWechatSent.setPubId(campaignDecisionWechatSentIn.getPubId());
//		campaignDecisionWechatSent.setPubName(campaignDecisionWechatSentIn.getPubName());
//		campaignDecisionWechatSent.setRefreshInterval(campaignDecisionWechatSentIn.getRefreshInterval());
//		campaignDecisionWechatSent.setRefreshIntervalType(campaignDecisionWechatSentIn.getRefreshIntervalType());
//		campaignDecisionWechatSent.setWechatH5Id(campaignDecisionWechatSentIn.getWechatH5Id());
//		campaignDecisionWechatSent.setWechatH5Name(campaignDecisionWechatSent.getWechatH5Name());
//		return campaignDecisionWechatSent;
//	}
	
	private CampaignDecisionPropCompare initCampaignDecisionPropCompare(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionPropCompare campaignDecisionPropCompare = new  CampaignDecisionPropCompare();
		CampaignDecisionPropCompareIn campaignDecisionPropCompareIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionPropCompareIn.class);
		if(null == campaignDecisionPropCompareIn) return null;
		campaignDecisionPropCompare.setName(campaignDecisionPropCompareIn.getName());
		campaignDecisionPropCompare.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionPropCompare.setCampaignHeadId(campaignHeadId);
		campaignDecisionPropCompare.setPropType(campaignDecisionPropCompareIn.getPropType());
		campaignDecisionPropCompare.setRule(campaignDecisionPropCompareIn.getRule());
		campaignDecisionPropCompare.setValue(campaignDecisionPropCompareIn.getRuleValue());
		campaignDecisionPropCompare.setExclude(campaignDecisionPropCompareIn.getExlude());
		return campaignDecisionPropCompare;
	}
	
	private CampaignBody initCampaignBody(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId,Integer taskId) {
		CampaignBody campaignBody = new CampaignBody();
		campaignBody.setHeadId(campaignHeadId);
		campaignBody.setItemType(campaignNodeChainIn.getItemType());
		campaignBody.setNodeType(campaignNodeChainIn.getNodeType());
		campaignBody.setItemId(campaignNodeChainIn.getItemId());
		campaignBody.setPosX(campaignNodeChainIn.getPosX());
		campaignBody.setPosY(campaignNodeChainIn.getPosY());
		campaignBody.setPosZ(campaignNodeChainIn.getPosZ());
		campaignBody.setDescription(campaignNodeChainIn.getDesc());
		campaignBody.setTaskId(taskId);
		return campaignBody;
	}
	/**
	 * 初始化campaign_node_item表的数据:更新icon
	 * @param campaignNodeChainIn
	 * @param campaignHeadId
	 * @return
	 */
	private CampaignNodeItem initCampaignNodeItem(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		if(campaignNodeChainIn.getCode() == null) {
			return null;
		}
		CampaignNodeItem campaignNodeItemTmp = new CampaignNodeItem();
		campaignNodeItemTmp.setCode(campaignNodeChainIn.getCode());
		campaignNodeItemTmp.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<CampaignNodeItem> campaignNodeItemList = campaignNodeItemDao.selectList(campaignNodeItemTmp);
		if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(campaignNodeItemList) &&
		   campaignNodeItemList.size() == 1) {
			String icon = campaignNodeChainIn.getIcon();
			if(StringUtils.isNotBlank(icon)){
				CampaignNodeItem campaignNodeItem = campaignNodeItemList.get(0);
				campaignNodeItem.setIcon(icon);
				return campaignNodeItem;
			}
		}
		return null;
	}
	
	private CampaignTriggerTimer initCampaignTriggerTimer(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignTriggerTimer campaignTriggerTimer = new CampaignTriggerTimer();
		CampaignTriggerTimerIn campaignTriggerTimerIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignTriggerTimerIn.class);
		if(null == campaignTriggerTimerIn) return null;
		campaignTriggerTimer.setCampaignHeadId(campaignHeadId);
		campaignTriggerTimer.setItemId(campaignNodeChainIn.getItemId());
		campaignTriggerTimer.setName(campaignTriggerTimerIn.getName());
		campaignTriggerTimer.setStartTime(DateUtil.getDateFromString(campaignTriggerTimerIn.getStartTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		campaignTriggerTimer.setEndTime(DateUtil.getDateFromString(campaignTriggerTimerIn.getEndTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		return campaignTriggerTimer;
	}
	
	private CampaignAudienceTarget initCampaignAudienceTarget(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
		CampaignAudienceTargetIn campaignAudienceTargetIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignAudienceTargetIn.class);
		if(null == campaignAudienceTargetIn) return null;
		campaignAudienceTarget.setName(campaignAudienceTargetIn.getName());
		campaignAudienceTarget.setCampaignHeadId(campaignHeadId);
		campaignAudienceTarget.setItemId(campaignNodeChainIn.getItemId());
		campaignAudienceTarget.setSegmentationId(campaignAudienceTargetIn.getSegmentationId());
		campaignAudienceTarget.setSegmentationName(campaignAudienceTargetIn.getSegmentationName());
		campaignAudienceTarget.setAllowedNew(campaignAudienceTargetIn.getAllowedNew());
		campaignAudienceTarget.setRefreshInterval(campaignAudienceTargetIn.getRefreshInterval());
		campaignAudienceTarget.setRefreshIntervalType(campaignAudienceTargetIn.getRefreshIntervalType());
		return campaignAudienceTarget;
	}
}
