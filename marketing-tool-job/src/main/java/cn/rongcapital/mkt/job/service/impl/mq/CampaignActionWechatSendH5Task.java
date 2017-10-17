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

import cn.rongcapital.mkt.dao.DataPartyDao;
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
import cn.rongcapital.mkt.dao.CampaignActionSendH5Dao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionSendH5;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

/**
 * 发送H5活动节点,由于大连接口不支持个人号发送H5,修改为个人号发送H5的链接
 * change log:
 * 2016-07-01:跟闫俊确认:该节点上个人号发送H5功能暂时去掉
 */
@Service
public class CampaignActionWechatSendH5Task extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignActionSendH5Dao campaignActionSendH5Dao;
	@Autowired
	private DataPartyDao dataPartyDao;
	
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		
		CampaignActionSendH5 campaignActionSendH5T = new CampaignActionSendH5();
		campaignActionSendH5T.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSendH5T.setCampaignHeadId(campaignHeadId);
		campaignActionSendH5T.setItemId(itemId);
		List<CampaignActionSendH5> campaignActionSendH5List = campaignActionSendH5Dao.selectList(campaignActionSendH5T);
		if(CollectionUtils.isEmpty(campaignActionSendH5List) ||
		   StringUtils.isBlank(campaignActionSendH5List.get(0).getPubId()) || 
		   null == campaignActionSendH5List.get(0).getMaterialId()) {
			logger.error("没有配置公众号属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignActionSendH5 campaignActionSendH5 = campaignActionSendH5List.get(0);
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
											 itemId,campaignEndsList,campaignActionSendH5);
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
								  CampaignActionSendH5 campaignActionSendH5) {
		String queueKey = campaignHeadId+"-"+itemId;
		List<Segment> segmentListToNext = new ArrayList<Segment>();//要传递给下面节点的数据(执行了发送微信操作的数据)
		List<String> fansWeixinIds = new ArrayList<String>();
		String pubId = campaignActionSendH5.getPubId();
		Integer materialId = campaignActionSendH5.getMaterialId();
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
					if(isFans) {
//						String h5MobileUrl = getH5MobileUrl(campaignActionSendH5.getImgTextAssetId());
//						segment.setH5MobileUrl(h5MobileUrl);
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
			boolean isPubSent = sendPubWechatByH5Interface(pubId,materialId,fansWeixinIds,campaignHeadId,itemId);
			if(!isPubSent) {//公众号执行发送动作失败
				segmentListToNext = null;
			}else {
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
		if(CollectionUtils.isNotEmpty(campaignEndsList) && CollectionUtils.isNotEmpty(segmentListToNext)) {
			for(CampaignSwitch cs:campaignEndsList) {
				//发送segment数据到后面的节点
				sendDynamicQueue(segmentListToNext, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
				logicDeleteNodeAudience(campaignHeadId,itemId,segmentListToNext);
				logger.info(queueKey+"-out:"+JSON.toJSONString(segmentListToNext));
			}
		}
	}
	
//	private String getH5MobileUrl(Integer imgTextAssetId){
//		String h5Url = null;
//		ImgTextAsset imgTextAssetT = new ImgTextAsset();
//		imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//		imgTextAssetT.setId(imgTextAssetId);
//		List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
//		if(CollectionUtils.isNotEmpty(imgTextAssetList)) {
//			h5Url = imgTextAssetList.get(0).getMobilePreviewUrl();
//		}
//		return h5Url;
//	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelCampaignInnerTask(taskSchedule);
	}
	
	@Override
	public void task(Integer taskId) {

	}

}