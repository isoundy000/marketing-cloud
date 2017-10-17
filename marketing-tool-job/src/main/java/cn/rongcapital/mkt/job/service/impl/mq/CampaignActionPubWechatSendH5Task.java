package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.tagsin.wechat_sdk.App;

import cn.rongcapital.mkt.biz.MessageSendBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignActionSendPubDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionSendPub;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.WebchatAuthInfo;

import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignActionPubWechatSendH5Task extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignActionSendPubDao campaignActionSendPubDao;
	@Autowired
	private DataPartyDao dataPartyDao;
	
	@Autowired
	private WechatRegisterDao wechatRegisterDao;
	
	@Autowired
	private WebchatAuthInfoDao webchatAuthInfoDao;
	
	@Autowired
	private MessageSendBiz messageSendBiz;
	
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		
		CampaignActionSendPub campaignActionSendPubT = new CampaignActionSendPub();
		campaignActionSendPubT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSendPubT.setCampaignHeadId(campaignHeadId);
		campaignActionSendPubT.setItemId(itemId);
		List<CampaignActionSendPub> campaignActionSendPubList = campaignActionSendPubDao.selectList(campaignActionSendPubT);
		if(CollectionUtils.isEmpty(campaignActionSendPubList) ||
		   StringUtils.isBlank(campaignActionSendPubList.get(0).getPubId()) || 
		   null == campaignActionSendPubList.get(0).getMaterialId()) {
			logger.error("没有配置公众号和图文属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignActionSendPub campaignActionSendPub = campaignActionSendPubList.get(0);
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
											 itemId,campaignEndsList,campaignActionSendPub);
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
								  CampaignActionSendPub campaignActionSendPub) {
		//获取app
		App app = getApp(campaignActionSendPub);
		
		List<Segment> segmentListToNext = new ArrayList<Segment>();//要传递给下面节点的数据(执行了发送微信操作的数据)
		String queueKey = campaignHeadId+"-"+itemId;
		List<String> fansWeixinIds = new ArrayList<String>();
		String pubId = campaignActionSendPub.getPubId();
		String materialId = campaignActionSendPub.getMaterialId();
        Set<Integer> dataPartyIds = new HashSet<>();
		for(Segment segment:segmentList) {
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				insertNodeAudience(campaignHeadId, itemId, segment);
				Integer dataId = segment.getDataId();
				//从mongo的主数据表中查询该条id对应的主数据详细信息
				DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(dataId)), DataParty.class);
				if(null!=dp && null !=dp.getMdType() &&
				   dp.getMdType() == ApiConstant.DATA_PARTY_MD_TYPE_POPULATION) {
					boolean isFans = isPubWechatFans(dp, pubId, null);
					logger.info("是否是微信公众号粉丝标识------------------------------------>："+isFans);
					if(isFans) {
						Integer mid = dp.getMid();
						cn.rongcapital.mkt.po.DataParty dataParty = new cn.rongcapital.mkt.po.DataParty();
						dataParty.setId(mid);
						List<cn.rongcapital.mkt.po.DataParty> dataPartyList = dataPartyDao.selectList(dataParty);
						fansWeixinIds.add(dataPartyList.get(0).getWxCode());
						segmentListToNext.add(segment);//数据放入向后面节点传递的list里
                        dataPartyIds.add(dp.getMid());
					} else {
						logger.info("不是公众号粉丝,无法发送,"+JSON.toJSONString(segment));
					}
				}
			}
		}
		if(fansWeixinIds.size() > 0) {
			//boolean isPubSent = sendPubWechatByH5Interface(pubId,materialId,fansWeixinIds,campaignHeadId,itemId);
			for(String wxCode : fansWeixinIds){
				
				boolean isPubSent = false;
				if(null != app){
					isPubSent = messageSendBiz.sendMpnews(app.getAuthAppId(), app.getAuthRefreshToken(), wxCode, materialId);
				}
				logger.info("向受众人群粉丝发送微信图文成功标识------------------------------------>："+isPubSent);
				
				if(!isPubSent) {//公众号执行发送动作失败
					segmentListToNext = null;
				} else {
					//更新mongo里node_audience表的发送状态
					updateSentStatus(segmentList,campaignHeadId,itemId,ApiConstant.MONGO_NODEAUDIENCE_SENTSTATUS_H5_SENT);
	                if (!CollectionUtils.isEmpty(dataPartyIds)) {
	                    Query query = new Query(Criteria.where("mid").in(dataPartyIds));
	                    Update update = new Update();
	                    update.inc("receiveCount", Integer.valueOf(1));
	                    mongoTemplate.updateMulti(query, update, DataParty.class);
	                }
				}
			}
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList) && CollectionUtils.isNotEmpty(segmentListToNext)) {
			for(CampaignSwitch cs:campaignEndsList) {
				//发送segment数据到后面的节点
				sendDynamicQueue(segmentListToNext, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
				logicDeleteNodeAudience(campaignHeadId,itemId,segmentListToNext);
				logger.info(queueKey+"-out:"+JSON.toJSONString(segmentListToNext));
			}
		}
	}
	

	/**
	 * @Title: getApp   
	 * @Description: 封装APP 
	 * @param: @param campaignActionSendPub	动作属性对象
	 * @param: @return      
	 * @return: App      
	 * @throws
	 */
	private App getApp(CampaignActionSendPub campaignActionSendPub) {
		App app = new App("","");
		try {
			String pubId = campaignActionSendPub.getPubId();
			WechatRegister wechatRegister = new WechatRegister();
			wechatRegister.setType(0);
			wechatRegister.setWxAcct(pubId);
			WechatRegister register = wechatRegisterDao.selectList(wechatRegister).get(0);
			//公众号不存在
			if(register == null){
				return null;
			}
			// 获取appId
			String appId =  register.getAppId();
			// 获取authorizerRefreshToken
			WebchatAuthInfo webchatAuthInfo = new WebchatAuthInfo();
			webchatAuthInfo.setAuthorizerAppid(appId);
			String authorizerRefreshToken = webchatAuthInfoDao.selectList(webchatAuthInfo).get(0)
					.getAuthorizerRefreshToken();
			// 封装公众号信息
			app.setAuthAppId(appId);
			app.setAuthRefreshToken(authorizerRefreshToken);
		} catch (Exception e) {
			logger.info("获取参数方法出现异常：" + e.getMessage());
		}
		return app;
	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelCampaignInnerTask(taskSchedule);
	}
	
	@Override
	public void task(Integer taskId) {

	}
		
		
}