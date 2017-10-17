package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
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
import cn.rongcapital.mkt.dao.CampaignNodeItemDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.CampaignTriggerTimerDao;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
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
import cn.rongcapital.mkt.po.CampaignNodeItem;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.CampaignTriggerTimer;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.service.CampaignBodyGetService;
import cn.rongcapital.mkt.vo.out.CampaignActionSaveAudienceOut;
import cn.rongcapital.mkt.vo.out.CampaignActionSendH5Out;
import cn.rongcapital.mkt.vo.out.CampaignActionSendPrivtOut;
import cn.rongcapital.mkt.vo.out.CampaignActionSendPubOut;
import cn.rongcapital.mkt.vo.out.CampaignActionSetTagOut;
import cn.rongcapital.mkt.vo.out.CampaignActionWaitOut;
import cn.rongcapital.mkt.vo.out.CampaignAudienceTargetOut;
import cn.rongcapital.mkt.vo.out.CampaignBodyGetOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionPropCompareOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionPrvtFriendsOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionPubFansOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionTagOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionWechatForwardOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionWechatReadOut;
import cn.rongcapital.mkt.vo.out.CampaignNodeChainOut;
import cn.rongcapital.mkt.vo.out.CampaignSwitchOut;
import cn.rongcapital.mkt.vo.out.CampaignTriggerTimerOut;
import cn.rongcapital.mkt.vo.out.TagOut;

@Service
public class CampaignBodyGetServiceImpl implements CampaignBodyGetService {

	@Autowired
	private CampaignBodyDao campaignBodyDao;
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
	private CampaignAudienceTargetDao campaignAudienceTargetDao;
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
	// @Autowired
	// private CampaignDecisionWechatSentDao campaignDecisionWechatSentDao;
	@Autowired
	private CampaignSwitchDao campaignSwitchDao;
	@Autowired
	private CampaignTriggerTimerDao campaignTriggerTimerDao;
	@Autowired
	private CampaignNodeItemDao campaignNodeItemDao;
	@Autowired
	private WechatAssetDao wechatAssetDao;
	@Autowired
	private WechatAssetGroupDao wechatAssetGroupDao;
	@Autowired
	private ImgTextAssetDao imgTextAssetDao;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public CampaignBodyGetOut campaignBodyGet(String userToken, String ver, int campaignHeadId) {
		CampaignBodyGetOut campaignBodyGetOut = new CampaignBodyGetOut(0, ApiErrorCode.SUCCESS.getMsg(), 0, null);
		CampaignBody campaignBodyQuery = new CampaignBody();
		campaignBodyQuery.setHeadId(campaignHeadId);
		campaignBodyQuery.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignBodyQuery.setPageSize(Integer.MAX_VALUE);
		List<CampaignBody> campaignBodyList = campaignBodyDao.selectList(campaignBodyQuery);

		if (CollectionUtils.isNotEmpty(campaignBodyList)) {
			for (CampaignBody c : campaignBodyList) {
				CampaignNodeChainOut campaignNodeChainOut = new CampaignNodeChainOut();
				campaignNodeChainOut.setItemId(c.getItemId());
				campaignNodeChainOut.setItemType(c.getItemType());
				campaignNodeChainOut.setNodeType(c.getNodeType());
				campaignNodeChainOut.setPosX(c.getPosX());
				campaignNodeChainOut.setPosY(c.getPosY());
				campaignNodeChainOut.setPosZ(c.getPosZ());
				// 获取code、icon值
				CampaignNodeItem campaignNodeItem = new CampaignNodeItem();
				campaignNodeItem.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				campaignNodeItem.setType(c.getItemType());
				campaignNodeItem.setPtype(c.getNodeType());
				List<CampaignNodeItem> campaignNodeItemList = campaignNodeItemDao.selectList(campaignNodeItem);
				if (CollectionUtils.isNotEmpty(campaignNodeItemList)) {
					CampaignNodeItem cni = campaignNodeItemList.get(0);
					campaignNodeChainOut.setCode(cni.getCode());
					campaignNodeChainOut.setIcon(cni.getIcon());
					campaignNodeChainOut.setCodeName(cni.getName());
					// 获取父节点的code值
					if (cni.getPtype() != ApiConstant.CAMPAIGN_PARENT_NODE_PTYPE) {// 不是父节点
						campaignNodeItem = new CampaignNodeItem();
						campaignNodeItem.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
						campaignNodeItem.setType(cni.getPtype());
						campaignNodeItem.setPtype(ApiConstant.CAMPAIGN_PARENT_NODE_PTYPE);
						List<CampaignNodeItem> cnilist = campaignNodeItemDao.selectList(campaignNodeItem);
						campaignNodeChainOut.setParentCode(cnilist.get(0).getCode());
					}
				}
				// 设置desc
				campaignNodeChainOut.setDesc(c.getDescription());
				// 设置节点上的当前人数:查询mongodb获得
				campaignNodeChainOut.setAudienceCount(getAudienceCountInNode(campaignHeadId, c.getItemId()));
				campaignNodeChainOut.setStatisticsUrl(c.getStatisticsUrl());

				List<CampaignSwitchOut> campaignSwitchList = queryCampaignSwitchList(campaignHeadId, c.getItemId());
				List<CampaignSwitchOut> campaignEndsList = queryCampaignEndsList(campaignHeadId, c.getItemId());
				campaignNodeChainOut.setCampaignEndsList(campaignEndsList);
				campaignNodeChainOut.setCampaignSwitchList(campaignSwitchList);

				if (campaignNodeChainOut.getNodeType() == ApiConstant.CAMPAIGN_NODE_TRIGGER) {
					switch (campaignNodeChainOut.getItemType()) {
					case ApiConstant.CAMPAIGN_ITEM_TRIGGER_TIMMER:// 定时触发
						CampaignTriggerTimerOut campaignTriggerTimerOut = queryCampaignTriggerTimer(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignTriggerTimerOut);
						break;
					}
				}
				if (campaignNodeChainOut.getNodeType() == ApiConstant.CAMPAIGN_NODE_AUDIENCE) {
					switch (campaignNodeChainOut.getItemType()) {
					case ApiConstant.CAMPAIGN_ITEM_AUDIENCE_TARGET:// 目标人群
						CampaignAudienceTargetOut campaignAudienceTargetOut = queryCampaignAudienceTarget(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignAudienceTargetOut);
						break;
					}
				}
				if (campaignNodeChainOut.getNodeType() == ApiConstant.CAMPAIGN_NODE_DECISION) {
					switch (campaignNodeChainOut.getItemType()) {
					case ApiConstant.CAMPAIGN_ITEM_DECISION_PROP_COMPARE:// 联系人属性比较
						CampaignDecisionPropCompareOut campaignDecisionPropCompareOut = queryCampaignDecisionPropCompare(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionPropCompareOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_SENT:// 微信图文是否发送
						// CampaignDecisionWechatSentOut
						// campaignDecisionWechatSentOut =
						// queryCampaignDecisionWechatSent(campaignNodeChainOut,campaignHeadId);
						// campaignNodeChainOut.setInfo(campaignDecisionWechatSentOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_READ:// 微信图文是否查看
						CampaignDecisionWechatReadOut campaignDecisionWechatReadOut = queryCampaignDecisionWechatRead(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionWechatReadOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_FORWARD:// 微信图文是否转发
						CampaignDecisionWechatForwardOut campaignDecisionWechatForwardOut = queryCampaignDecisionWechatForward(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionWechatForwardOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_IS_SUBSCRIBE:// 是否订阅公众号
						CampaignDecisionPubFansOut campaignDecisionPubFansOut = queryCampaignDecisionPubFans(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionPubFansOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_IS_PRIVT_FRIEND:// 是否个人号好友
						CampaignDecisionPrvtFriendsOut campaignDecisionPrvtFriendsOut = queryCampaignDecisionPrvtFriends(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionPrvtFriendsOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_TAG:// 标签判断
						CampaignDecisionTagOut campaignDecisionTagOut = queryCampaignDecisionTag(campaignNodeChainOut,
								campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionTagOut);
						break;
					}
				}
				if (campaignNodeChainOut.getNodeType() == ApiConstant.CAMPAIGN_NODE_ACTION) {
					switch (campaignNodeChainOut.getItemType()) {
					case ApiConstant.CAMPAIGN_ITEM_ACTION_WAIT:// 等待
						CampaignActionWaitOut campaignActionWaitOut = queryCampaignActionWait(campaignNodeChainOut,
								campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionWaitOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SAVE_AUDIENCE:// 保存当前人群
						CampaignActionSaveAudienceOut campaignActionSaveAudienceOut = queryCampaignActionSaveAudience(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSaveAudienceOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SET_TAG:// 设置标签
						CampaignActionSetTagOut campaignActionSetTagOut = queryCampaignActionSetTag(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSetTagOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_ADD_CAMPAIGN:// 添加到其它活动
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_MOVE_CAMPAIGN:// 转移到其它活动
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_WECHAT_H5:// 发送微信图文
						CampaignActionSendPubOut campaignActionSendPubOut = queryCampaignActionSendPub(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSendPubOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_H5:// 发送H5图文
						CampaignActionSendH5Out campaignActionSendH5Out = queryCampaignActionSendH5(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSendH5Out);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_PRVT_INFO:// 发送个人号消息
						CampaignActionSendPrivtOut campaignActionSendPrivtOut = queryCampaignActionSendPrivt(
								campaignNodeChainOut, campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSendPrivtOut);
						break;
					}
				}
				campaignBodyGetOut.getData().add(campaignNodeChainOut);
			}
		}
		campaignBodyGetOut.setTotal(campaignBodyGetOut.getData().size());
		return campaignBodyGetOut;
	}

	private int getAudienceCountInNode(Integer campaignHeadId, String itemId) {
		Criteria criteria = Criteria.where("campaignHeadId").is(campaignHeadId).and("itemId").is(itemId).and("status")
				.is(0);
		long count = mongoTemplate.count(new Query(criteria), NodeAudience.class);
		return (int) count;
	}

	private CampaignActionSendPrivtOut queryCampaignActionSendPrivt(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignActionSendPrivt t = new CampaignActionSendPrivt();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSendPrivt> resList = campaignActionSendPrivtDao.selectList(t);
		CampaignActionSendPrivtOut campaignActionSendPrivtOut = new CampaignActionSendPrivtOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSendPrivt campaignActionSendPrivt = resList.get(0);
			campaignActionSendPrivtOut.setName(campaignActionSendPrivt.getName());
			campaignActionSendPrivtOut.setTextInfo(campaignActionSendPrivt.getTextInfo());
			campaignActionSendPrivtOut.setAssetId(campaignActionSendPrivt.getAssetId());
			campaignActionSendPrivtOut.setGroupId(campaignActionSendPrivt.getGroupId());

			if (campaignActionSendPrivt.getAssetId() != null) {
				WechatAsset wechatAssetT = new WechatAsset();
				wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetT.setAssetId(campaignActionSendPrivt.getAssetId());
				List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
				if (CollectionUtils.isNotEmpty(wechatAssetList)) {
					String assetName = wechatAssetList.get(0).getAssetName();
					campaignActionSendPrivtOut.setAssetName(assetName);// 返回微信名
				}
			}
			if (campaignActionSendPrivt.getGroupId() != null) {
				WechatAssetGroup wechatAssetGroupT = new WechatAssetGroup();
				wechatAssetGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetGroupT.setId(campaignActionSendPrivt.getGroupId().longValue());
				List<WechatAssetGroup> wechatAssetGroupList = wechatAssetGroupDao.selectList(wechatAssetGroupT);
				if (CollectionUtils.isNotEmpty(wechatAssetGroupList)) {
					String groupName = wechatAssetGroupList.get(0).getName();
					campaignActionSendPrivtOut.setGroupName(groupName);// 返回群组名
				}
			}
		}
		return campaignActionSendPrivtOut;
	}

	private CampaignActionSendH5Out queryCampaignActionSendH5(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignActionSendH5 t = new CampaignActionSendH5();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSendH5> resList = campaignActionSendH5Dao.selectList(t);
		CampaignActionSendH5Out campaignActionSendH5Out = new CampaignActionSendH5Out();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSendH5 campaignActionSendH5 = resList.get(0);
			campaignActionSendH5Out.setName(campaignActionSendH5.getName());
			campaignActionSendH5Out.setPubAssetId(campaignActionSendH5.getPubAssetId());
			campaignActionSendH5Out.setPrvAssetId(campaignActionSendH5.getPrvAssetId());
			campaignActionSendH5Out.setImgTextAssetId(campaignActionSendH5.getImgTextAssetId());
			campaignActionSendH5Out.setGroupId(campaignActionSendH5.getGroupId());
			if (null != campaignActionSendH5.getPubAssetId()) {
				WechatAsset wechatAssetT = new WechatAsset();
				wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetT.setAssetId(campaignActionSendH5.getPubAssetId());
				List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
				if (CollectionUtils.isNotEmpty(wechatAssetList)) {
					campaignActionSendH5Out.setPubAssetName(wechatAssetList.get(0).getAssetName());
				}
			}
			if (null != campaignActionSendH5.getImgTextAssetId()) {
				ImgTextAsset imgTextAssetT = new ImgTextAsset();
				imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				imgTextAssetT.setId(campaignActionSendH5.getImgTextAssetId());
				List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
				if (CollectionUtils.isNotEmpty(imgTextAssetList)) {
					campaignActionSendH5Out.setImgTextAssetName(imgTextAssetList.get(0).getName());
				}
			}
			if (campaignActionSendH5.getPrvAssetId() != null) {
				WechatAsset wechatAssetT = new WechatAsset();
				wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetT.setAssetId(campaignActionSendH5.getPrvAssetId());
				List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
				if (CollectionUtils.isNotEmpty(wechatAssetList)) {
					String assetName = wechatAssetList.get(0).getAssetName();
					campaignActionSendH5Out.setPrvAssetName(assetName);// 返回个人号微信名
				}
			}
			if (campaignActionSendH5.getGroupId() != null) {
				WechatAssetGroup wechatAssetGroupT = new WechatAssetGroup();
				wechatAssetGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetGroupT.setId(campaignActionSendH5.getGroupId().longValue());
				List<WechatAssetGroup> wechatAssetGroupList = wechatAssetGroupDao.selectList(wechatAssetGroupT);
				if (CollectionUtils.isNotEmpty(wechatAssetGroupList)) {
					String groupName = wechatAssetGroupList.get(0).getName();
					campaignActionSendH5Out.setGroupName(groupName);// 返回群组名
				}
			}
		}
		return campaignActionSendH5Out;
	}

	private CampaignActionSendPubOut queryCampaignActionSendPub(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignActionSendPub t = new CampaignActionSendPub();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSendPub> resList = campaignActionSendPubDao.selectList(t);
		CampaignActionSendPubOut campaignActionSendPubOut = new CampaignActionSendPubOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSendPub campaignActionSendPub = resList.get(0);
			campaignActionSendPubOut.setName(campaignActionSendPub.getName());
			campaignActionSendPubOut.setAssetId(campaignActionSendPub.getAssetId());
			campaignActionSendPubOut.setImgTextAssetId(campaignActionSendPub.getImgTextAssetId());
			if (null != campaignActionSendPub.getAssetId()) {
				WechatAsset wechatAssetT = new WechatAsset();
				wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetT.setAssetId(campaignActionSendPub.getAssetId());
				List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
				if (CollectionUtils.isNotEmpty(wechatAssetList)) {
					campaignActionSendPubOut.setAssetName(wechatAssetList.get(0).getAssetName());
				}
			}
			if (null != campaignActionSendPub.getImgTextAssetId()) {
				ImgTextAsset imgTextAssetT = new ImgTextAsset();
				imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				imgTextAssetT.setId(campaignActionSendPub.getImgTextAssetId());
				List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
				if (CollectionUtils.isNotEmpty(imgTextAssetList)) {
					campaignActionSendPubOut.setImgTextAssetName(imgTextAssetList.get(0).getName());
				}
			}
		}
		return campaignActionSendPubOut;
	}

	private CampaignActionSetTagOut queryCampaignActionSetTag(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignActionSetTag t = new CampaignActionSetTag();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSetTag> resList = campaignActionSetTagDao.selectList(t);
		CampaignActionSetTagOut campaignActionSetTagOut = new CampaignActionSetTagOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSetTag campaignActionSetTag = resList.get(0);
			campaignActionSetTagOut.setName(campaignActionSetTag.getName());
			campaignActionSetTagOut
					.setTagNames(Arrays.asList(StringUtils.split(campaignActionSetTag.getTagNames(), ',')));
		}
		return campaignActionSetTagOut;
	}

	private CampaignActionSaveAudienceOut queryCampaignActionSaveAudience(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignActionSaveAudience t = new CampaignActionSaveAudience();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSaveAudience> resList = campaignActionSaveAudienceDao.selectList(t);
		CampaignActionSaveAudienceOut campaignActionSaveAudienceOut = new CampaignActionSaveAudienceOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSaveAudience campaignActionSaveAudience = resList.get(0);
			campaignActionSaveAudienceOut.setName(campaignActionSaveAudience.getName());
			campaignActionSaveAudienceOut.setAudienceId(campaignActionSaveAudience.getAudienceId());
			campaignActionSaveAudienceOut.setAudienceName(campaignActionSaveAudience.getAudienceName());
		}
		return campaignActionSaveAudienceOut;
	}

	private CampaignActionWaitOut queryCampaignActionWait(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignActionWait t = new CampaignActionWait();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionWait> resList = campaignActionWaitDao.selectList(t);
		CampaignActionWaitOut campaignActionWaitOut = new CampaignActionWaitOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignActionWait campaignActionWait = resList.get(0);
			campaignActionWaitOut.setName(campaignActionWait.getName());
			campaignActionWaitOut.setRelativeType(campaignActionWait.getRelativeType());
			campaignActionWaitOut.setRelativeValue(campaignActionWait.getRelativeValue());
			campaignActionWaitOut.setSpecificTime(DateUtil.getStringFromDate(campaignActionWait.getSpecificTime(),
					ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
			campaignActionWaitOut.setType(campaignActionWait.getType());
		}
		return campaignActionWaitOut;
	}

	private CampaignDecisionTagOut queryCampaignDecisionTag(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignDecisionTag t = new CampaignDecisionTag();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionTag> resList = campaignDecisionTagDao.selectList(t);
		CampaignDecisionTagOut campaignDecisionTagOut = new CampaignDecisionTagOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionTag campaignDecisionTag = resList.get(0);
			campaignDecisionTagOut.setName(campaignDecisionTag.getName());
			campaignDecisionTagOut.setRule(campaignDecisionTag.getRule());
			String tagIds = campaignDecisionTag.getTagIds();
			if (StringUtils.isNotBlank(tagIds)) {
				List<TagOut> tags = new ArrayList<TagOut>();
				List<String> tagIdsStrList = Arrays.asList(StringUtils.split(tagIds, ','));
				for (String tagIdStr : tagIdsStrList) {
					TagOut tagOut = new TagOut();
			        Query query = new Query(Criteria.where("tag_id").is(tagIdStr));
			        BaseTag targetTag = mongoTemplate.findOne(query,BaseTag.class);
					Log.info("--------------" + targetTag.getTagName());
					tagOut.setTagId(tagIdStr);
					tagOut.setTagName(targetTag.getTagName());
					tags.add(tagOut);
				}
				campaignDecisionTagOut.setTags(tags);
			}
		}
		return campaignDecisionTagOut;
	}

	private CampaignDecisionPrvtFriendsOut queryCampaignDecisionPrvtFriends(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignDecisionPrvtFriends t = new CampaignDecisionPrvtFriends();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionPrvtFriends> resList = campaignDecisionPrvtFriendsDao.selectList(t);
		CampaignDecisionPrvtFriendsOut campaignDecisionPrvtFriendsOut = new CampaignDecisionPrvtFriendsOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = resList.get(0);
			campaignDecisionPrvtFriendsOut.setName(campaignDecisionPrvtFriends.getName());
			campaignDecisionPrvtFriendsOut.setAssetId(campaignDecisionPrvtFriends.getAssetId());
			campaignDecisionPrvtFriendsOut.setGroupId(campaignDecisionPrvtFriends.getGroupId());
			campaignDecisionPrvtFriendsOut.setRefreshInterval(campaignDecisionPrvtFriends.getRefreshInterval());
			campaignDecisionPrvtFriendsOut.setRefreshIntervalType(campaignDecisionPrvtFriends.getRefreshIntervalType());
			if (null != campaignDecisionPrvtFriends.getAssetId()) {
				WechatAsset wechatAssetT = new WechatAsset();
				wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetT.setAssetId(campaignDecisionPrvtFriends.getAssetId());
				List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
				if (CollectionUtils.isNotEmpty(wechatAssetList)) {
					String assetName = wechatAssetList.get(0).getAssetName();
					campaignDecisionPrvtFriendsOut.setAssetName(assetName);// 返回微信名
				}
			}
			if (null != campaignDecisionPrvtFriends.getGroupId()) {
				WechatAssetGroup wechatAssetGroupT = new WechatAssetGroup();
				wechatAssetGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetGroupT.setId(campaignDecisionPrvtFriends.getGroupId().longValue());
				List<WechatAssetGroup> wechatAssetGroupList = wechatAssetGroupDao.selectList(wechatAssetGroupT);
				if (CollectionUtils.isNotEmpty(wechatAssetGroupList)) {
					String groupName = wechatAssetGroupList.get(0).getName();
					campaignDecisionPrvtFriendsOut.setGroupName(groupName);// 返回群组名
				}
			}
		}
		return campaignDecisionPrvtFriendsOut;
	}

	private CampaignDecisionPubFansOut queryCampaignDecisionPubFans(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignDecisionPubFans t = new CampaignDecisionPubFans();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionPubFans> resList = campaignDecisionPubFansDao.selectList(t);
		CampaignDecisionPubFansOut campaignDecisionPubFansOut = new CampaignDecisionPubFansOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionPubFans campaignDecisionPubFans = resList.get(0);
			campaignDecisionPubFansOut.setName(campaignDecisionPubFans.getName());
			campaignDecisionPubFansOut.setAssetId(campaignDecisionPubFans.getAssetId());
			campaignDecisionPubFansOut.setRefreshInterval(campaignDecisionPubFans.getRefreshInterval());
			campaignDecisionPubFansOut.setRefreshIntervalType(campaignDecisionPubFans.getRefreshIntervalType());
			campaignDecisionPubFansOut.setSubscribeTime(campaignDecisionPubFans.getSubscribeTime());
			if (null != campaignDecisionPubFans.getAssetId()) {
				WechatAsset wechatAssetT = new WechatAsset();
				wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetT.setAssetId(campaignDecisionPubFans.getAssetId());
				List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
				if (CollectionUtils.isNotEmpty(wechatAssetList)) {
					campaignDecisionPubFansOut.setAssetName(wechatAssetList.get(0).getAssetName());
				}
			}
		}
		return campaignDecisionPubFansOut;
	}

	private CampaignDecisionWechatForwardOut queryCampaignDecisionWechatForward(
			CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignDecisionWechatForward t = new CampaignDecisionWechatForward();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionWechatForward> resList = campaignDecisionWechatForwardDao.selectList(t);
		CampaignDecisionWechatForwardOut campaignDecisionWechatForwardOut = new CampaignDecisionWechatForwardOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionWechatForward campaignDecisionWechatForward = resList.get(0);
			campaignDecisionWechatForwardOut.setName(campaignDecisionWechatForward.getName());
			campaignDecisionWechatForwardOut.setAssetId(campaignDecisionWechatForward.getAssetId());
			campaignDecisionWechatForwardOut.setRefreshInterval(campaignDecisionWechatForward.getRefreshInterval());
			campaignDecisionWechatForwardOut
					.setRefreshIntervalType(campaignDecisionWechatForward.getRefreshIntervalType());
			campaignDecisionWechatForwardOut.setImgTextAssetId(campaignDecisionWechatForward.getImgTextAssetId());
			campaignDecisionWechatForwardOut.setForwardTimes(campaignDecisionWechatForward.getForwardTimes());
			if (null != campaignDecisionWechatForward.getAssetId()) {
				WechatAsset wechatAssetT = new WechatAsset();
				wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetT.setAssetId(campaignDecisionWechatForward.getAssetId());
				List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
				if (CollectionUtils.isNotEmpty(wechatAssetList)) {
					campaignDecisionWechatForwardOut.setAssetName(wechatAssetList.get(0).getAssetName());
				}
			}
			if (null != campaignDecisionWechatForward.getImgTextAssetId()) {
				ImgTextAsset imgTextAssetT = new ImgTextAsset();
				imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				imgTextAssetT.setId(campaignDecisionWechatForward.getImgTextAssetId());
				List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
				if (CollectionUtils.isNotEmpty(imgTextAssetList)) {
					campaignDecisionWechatForwardOut.setImgTextAssetName(imgTextAssetList.get(0).getName());
				}
			}
		}
		return campaignDecisionWechatForwardOut;
	}

	private CampaignDecisionWechatReadOut queryCampaignDecisionWechatRead(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignDecisionWechatRead t = new CampaignDecisionWechatRead();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionWechatRead> resList = campaignDecisionWechatReadDao.selectList(t);
		CampaignDecisionWechatReadOut campaignDecisionWechatReadOut = new CampaignDecisionWechatReadOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionWechatRead campaignDecisionWechatRead = resList.get(0);
			campaignDecisionWechatReadOut.setName(campaignDecisionWechatRead.getName());
			campaignDecisionWechatReadOut.setAssetId(campaignDecisionWechatRead.getAssetId());
			campaignDecisionWechatReadOut.setRefreshInterval(campaignDecisionWechatRead.getRefreshInterval());
			campaignDecisionWechatReadOut.setRefreshIntervalType(campaignDecisionWechatRead.getRefreshIntervalType());
			campaignDecisionWechatReadOut.setImgTextAssetId(campaignDecisionWechatRead.getImgTextAssetId());
			campaignDecisionWechatReadOut.setReadTime(campaignDecisionWechatRead.getReadTime());
			campaignDecisionWechatReadOut.setReadPercent(campaignDecisionWechatRead.getReadPercent());
			if (null != campaignDecisionWechatRead.getAssetId()) {
				WechatAsset wechatAssetT = new WechatAsset();
				wechatAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAssetT.setAssetId(campaignDecisionWechatRead.getAssetId());
				List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetT);
				if (CollectionUtils.isNotEmpty(wechatAssetList)) {
					campaignDecisionWechatReadOut.setAssetName(wechatAssetList.get(0).getAssetName());
				}
			}
			if (null != campaignDecisionWechatRead.getImgTextAssetId()) {
				ImgTextAsset imgTextAssetT = new ImgTextAsset();
				imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				imgTextAssetT.setId(campaignDecisionWechatRead.getImgTextAssetId());
				List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
				if (CollectionUtils.isNotEmpty(imgTextAssetList)) {
					campaignDecisionWechatReadOut.setImgTextAssetName(imgTextAssetList.get(0).getName());
				}
			}
		}
		return campaignDecisionWechatReadOut;
	}

	// @SuppressWarnings("unused")
	// private CampaignDecisionWechatSentOut
	// queryCampaignDecisionWechatSent(CampaignNodeChainOut
	// campaignNodeChainOut, int campaignHeadId) {
	// CampaignDecisionWechatSent t = new CampaignDecisionWechatSent();
	// t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
	// t.setCampaignHeadId(campaignHeadId);
	// t.setItemId(campaignNodeChainOut.getItemId());
	// List<CampaignDecisionWechatSent> resList =
	// campaignDecisionWechatSentDao.selectList(t);
	// CampaignDecisionWechatSentOut campaignDecisionWechatSentOut = new
	// CampaignDecisionWechatSentOut();
	// if(CollectionUtils.isNotEmpty(resList)) {
	// CampaignDecisionWechatSent campaignDecisionWechatSent = resList.get(0);
	// campaignDecisionWechatSentOut.setName(campaignDecisionWechatSent.getName());
	// campaignDecisionWechatSentOut.setPubId(campaignDecisionWechatSent.getPubId());
	// campaignDecisionWechatSentOut.setPubName(campaignDecisionWechatSent.getPubName());
	// campaignDecisionWechatSentOut.setRefreshInterval(campaignDecisionWechatSent.getRefreshInterval());
	// campaignDecisionWechatSentOut.setRefreshIntervalType(campaignDecisionWechatSent.getRefreshIntervalType());
	// campaignDecisionWechatSentOut.setWechatH5Id(campaignDecisionWechatSent.getWechatH5Id());
	// campaignDecisionWechatSentOut.setWechatH5Name(campaignDecisionWechatSent.getWechatH5Name());
	// }
	// return campaignDecisionWechatSentOut;
	// }

	private CampaignDecisionPropCompareOut queryCampaignDecisionPropCompare(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignDecisionPropCompare t = new CampaignDecisionPropCompare();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionPropCompare> resList = campaignDecisionPropCompareDao.selectList(t);
		CampaignDecisionPropCompareOut campaignDecisionPropCompareOut = new CampaignDecisionPropCompareOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionPropCompare campaignDecisionPropCompare = resList.get(0);
			campaignDecisionPropCompareOut.setName(campaignDecisionPropCompare.getName());
			campaignDecisionPropCompareOut.setPropType(campaignDecisionPropCompare.getPropType());
			campaignDecisionPropCompareOut.setRule(campaignDecisionPropCompare.getRule());
			campaignDecisionPropCompareOut.setRuleValue(campaignDecisionPropCompare.getValue());
			campaignDecisionPropCompareOut.setExclude(campaignDecisionPropCompare.getExclude());
		}
		return campaignDecisionPropCompareOut;
	}

	private CampaignAudienceTargetOut queryCampaignAudienceTarget(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignAudienceTarget t = new CampaignAudienceTarget();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignAudienceTarget> resList = campaignAudienceTargetDao.selectList(t);
		CampaignAudienceTargetOut campaignAudienceTargetOut = new CampaignAudienceTargetOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignAudienceTarget campaignAudienceTarget = resList.get(0);
			campaignAudienceTargetOut.setName(campaignAudienceTarget.getName());
			campaignAudienceTargetOut.setAllowedNew(campaignAudienceTarget.getAllowedNew());
			campaignAudienceTargetOut.setRefreshInterval(campaignAudienceTarget.getRefreshInterval());
			campaignAudienceTargetOut.setRefreshIntervalType(campaignAudienceTarget.getRefreshIntervalType());
			campaignAudienceTargetOut.setSegmentationId(campaignAudienceTarget.getSegmentationId());
			campaignAudienceTargetOut.setSegmentationName(campaignAudienceTarget.getSegmentationName());

		}
		return campaignAudienceTargetOut;
	}

	private CampaignTriggerTimerOut queryCampaignTriggerTimer(CampaignNodeChainOut campaignNodeChainOut,
			int campaignHeadId) {
		CampaignTriggerTimer t = new CampaignTriggerTimer();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignTriggerTimer> resList = campaignTriggerTimerDao.selectList(t);
		CampaignTriggerTimerOut campaignTriggerTimerOut = new CampaignTriggerTimerOut();
		if (CollectionUtils.isNotEmpty(resList)) {
			CampaignTriggerTimer campaignTriggerTimer = resList.get(0);
			campaignTriggerTimerOut.setName(campaignTriggerTimer.getName());
			campaignTriggerTimerOut.setStartTime(DateUtil.getStringFromDate(campaignTriggerTimer.getStartTime(),
					ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
			campaignTriggerTimerOut.setEndTime(DateUtil.getStringFromDate(campaignTriggerTimer.getEndTime(),
					ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));

		}
		return campaignTriggerTimerOut;
	}

	private List<CampaignSwitchOut> queryCampaignSwitchList(int campaignHeadId, String itemId) {
		List<CampaignSwitchOut> campaignSwitchOutList = new ArrayList<CampaignSwitchOut>();
		CampaignSwitch campaignSwitch = new CampaignSwitch();
		campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_SWITCH_YES);
		campaignSwitch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignSwitch.setCampaignHeadId(campaignHeadId);
		campaignSwitch.setItemId(itemId);
		List<CampaignSwitch> campaignSwitchListYes = campaignSwitchDao.selectList(campaignSwitch);
		CampaignSwitchOut csoY = null;
		if (CollectionUtils.isNotEmpty(campaignSwitchListYes)) {
			CampaignSwitch cs = campaignSwitchListYes.get(0);
			csoY = new CampaignSwitchOut();
			csoY.setColor(cs.getColor());
			csoY.setDrawType(cs.getDrawType());
			csoY.setNextItemId(cs.getNextItemId());
		}
		campaignSwitchOutList.add(csoY);

		campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_SWITCH_NO);
		List<CampaignSwitch> campaignSwitchListNo = campaignSwitchDao.selectList(campaignSwitch);
		CampaignSwitchOut csoN = null;
		if (CollectionUtils.isNotEmpty(campaignSwitchListNo)) {
			CampaignSwitch cs = campaignSwitchListNo.get(0);
			csoN = new CampaignSwitchOut();
			csoN.setColor(cs.getColor());
			csoN.setDrawType(cs.getDrawType());
			csoN.setNextItemId(cs.getNextItemId());
		}
		campaignSwitchOutList.add(csoN);
		return campaignSwitchOutList;
	}

	private List<CampaignSwitchOut> queryCampaignEndsList(int campaignHeadId, String itemId) {
		CampaignSwitch campaignSwitch = new CampaignSwitch();
		campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_ENDS);
		campaignSwitch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignSwitch.setCampaignHeadId(campaignHeadId);
		campaignSwitch.setItemId(itemId);
		List<CampaignSwitch> campaignEndsList = campaignSwitchDao.selectList(campaignSwitch);
		List<CampaignSwitchOut> campaignSwitchOutList = new ArrayList<CampaignSwitchOut>();
		if (CollectionUtils.isNotEmpty(campaignEndsList)) {
			for (CampaignSwitch cs : campaignEndsList) {
				CampaignSwitchOut cso = new CampaignSwitchOut();
				cso.setColor(cs.getColor());
				cso.setDrawType(cs.getDrawType());
				cso.setNextItemId(cs.getNextItemId());
				campaignSwitchOutList.add(cso);
			}
		}
		return campaignSwitchOutList;
	}

}
