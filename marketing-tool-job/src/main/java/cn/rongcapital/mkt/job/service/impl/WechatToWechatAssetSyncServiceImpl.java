package cn.rongcapital.mkt.job.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.WechatGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.WechatRegister;

/*************************************************
 * @功能及特点的描述简述: 同步微信公众号接口
 * 
 * @see （与该类关联的类): TaskService
 * @对应项目名称: MC系统
 * @author: 白云峰
 * @version: v1.2 @date(创建、开发日期): 2016-6-17 最后修改日期: 2016-09-02
 * @复审人: 丛树林
 *************************************************/
@Service
public class WechatToWechatAssetSyncServiceImpl implements TaskService {

	@Autowired
	private WechatGroupDao wechatGroupDao;
	@Autowired
	private WechatAssetGroupDao wechatAssetGroupDao;
	@Autowired
	private WechatMemberDao wechatMemberDao;
	@Autowired
	private WechatRegisterDao wechatRegisterDao;
	@Autowired
	private WechatAssetDao wechatAssetDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void task(Integer taskId) {
		synWAssetInfoMethod();
		synWAssetGroupMethod();
	}

	/**
	 * @功能简述 同步微信公众号信息到wechat_asset
	 * 
	 * @param:
	 * 
	 * @return:
	 * 
	 */
	public void synWAssetInfoMethod() {
		// Todo:获取wechat_wegister表中的状态是0的公众号信息
		WechatRegister wechatRinfo = new WechatRegister();
		wechatRinfo.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<WechatRegister> wechatRegisterList = wechatRegisterDao.selectList(wechatRinfo);
		if (!CollectionUtils.isEmpty(wechatRegisterList)) {

			for (WechatRegister wechatRegister : wechatRegisterList) {
				String wxAcct = wechatRegister.getWxAcct();
				String groupIds = getWechatGroupId(wxAcct);
				WechatAsset wAsset = new WechatAsset();
				wAsset.setWxAcct(wxAcct);
				// wAsset.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID); congshulin 删除
				WechatAsset wechatAsset = new WechatAsset();
				wechatAsset.setAssetId(wechatRegister.getId());
				wechatAsset.setAssetType(wechatRegister.getType());
				wechatAsset.setAssetName(wechatRegister.getName());
				wechatAsset.setNickname(wechatRegister.getNickname());
				wechatAsset.setWxAcct(wxAcct);
				wechatAsset.setConsignationTime(wechatRegister.getCreateTime());
				wechatAsset.setTotalCount(getWechatMemberCount(wxAcct));
				wechatAsset.setGroupIds(groupIds);
				wechatAsset.setImgfileUrl(wechatRegister.getHeaderImage());
				wechatAsset.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wechatAsset.setAppId(wechatRegister.getAppId());
				wechatAsset.setWechatQrcode(wechatRegister.getWechatQrcode());

				int count = wechatAssetDao.selectListCount(wAsset);
				if (count == 0) {
					wechatAssetDao.insert(wechatAsset);
					logger.info("insert into wechat_asset id:" + wechatAsset.getId());
				} else {
					wechatAssetDao.updateByWxacct(wechatAsset);
					logger.info("update wechat_asset wx_acct:" + wxAcct);
				}
			}

		}
	}

	/**
	 * @功能简述 同步微信公众号组信息到wechat_asset_group
	 * 
	 * @param: wxAcct
	 *             公众号id
	 * @return:
	 * 
	 */
	public void synWAssetGroupMethod() {
		WechatAsset wechatAssetInfo = new WechatAsset();
		wechatAssetInfo.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		wechatAssetInfo.setStartIndex(null);
		wechatAssetInfo.setPageSize(null);
		List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAssetInfo);

		if (!CollectionUtils.isEmpty(wechatAssetList)) {
			for (WechatAsset wechatAsset : wechatAssetList) {
				String wxAcct = wechatAsset.getWxAcct();
				WechatGroup wGroup = new WechatGroup();
				wGroup.setWxAcct(wxAcct);
				wGroup.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				wGroup.setStartIndex(null);
				wGroup.setPageSize(null);
				List<WechatGroup> selectList = wechatGroupDao.selectList(wGroup);

				for (WechatGroup wechatGroup : selectList) {
					Long groupId = new Long(wechatGroup.getGroupId());
					WechatAssetGroup wechatAssetGroup = new WechatAssetGroup();
					wechatAssetGroup.setImportGroupId(groupId);
					wechatAssetGroup.setWxAcct(wxAcct);
					List<WechatAssetGroup> wechatAssetGroupsTemp = wechatAssetGroupDao.selectList(wechatAssetGroup);
					if(CollectionUtils.isEmpty(wechatAssetGroupsTemp)){
						wechatAssetGroup.setName(wechatGroup.getGroupName());
						wechatAssetGroup.setMembers(getWechatMemberCountByPubIdAndGroupId(wechatGroup.getWxAcct(),wechatGroup.getGroupId()));
						wechatAssetGroup.setCreateTime(new Date());
						if (groupId >= 100) {
							wechatAssetGroup.setIsSysGroup(ApiConstant.TAG_ITEM_CUSTOM);
						} else {
							wechatAssetGroup.setIsSysGroup(ApiConstant.TAG_ITEM_SYSTEM);
						}
						wechatAssetGroupDao.insert(wechatAssetGroup);
						logger.info("insert into wechat_asset_group id:" + wechatAssetGroup.getId());
					}else{
						
						wechatAssetGroup.setName(wechatGroup.getGroupName());
						wechatAssetGroup.setMembers(getWechatMemberCountByPubIdAndGroupId(wechatGroup.getWxAcct(),wechatGroup.getGroupId()));
						wechatAssetGroup.setCreateTime(new Date());
						wechatAssetGroupDao.updateByWxacctIGroupId(wechatAssetGroup);
						logger.info("update wechat_asset_group id:" + wxAcct);
					}																				
				}
			}
		}
	}

	private void callAssetMethod() {
		// Todo:1.遍历wechat_asset_group表，获取importId的List
		List<Long> alreadyImportedIdList = wechatAssetGroupDao.selectImportGroupIds();
		// Todo:2.根据1获取的listId，在wechat_group表中获取不在importId中的IdList
		List<Map<String, Object>> newGroupList = null;
		if (alreadyImportedIdList != null && alreadyImportedIdList.size() > 0) {
			newGroupList = wechatGroupDao.selectNewGroupList(alreadyImportedIdList);
		} else {
			newGroupList = wechatGroupDao.selectFirstImportGroupList();
		}
		// Todo:3.根据2获取idList，查询wechat_group中的相关数据同步到wechat_asset_group表中
		if (newGroupList != null && newGroupList.size() > 0) {
			wechatAssetGroupDao.insertNewGroupList(newGroupList);
		}

		// Todo:4.即时跟新wechat_asset_group表中的member数
		alreadyImportedIdList = wechatAssetGroupDao.selectImportGroupIds();
		for (Long id : alreadyImportedIdList) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("wx_group_id", id);
			Integer count = wechatMemberDao.selectGroupMemeberCount(paramMap);
			paramMap.put("import_group_id", paramMap.remove("wx_group_id"));
			paramMap.put("members", count);
			wechatAssetGroupDao.updateGroupCountById(paramMap);
		}
		// Todo:group基本同步完毕

		// Todo:获取wechat_asset表中的wx_acct。
		List<String> alreadyImportedWxAcctList = wechatAssetDao.selectWxAssetList();
		// Todo:根据acct获取wx_register表中新注册的公众号或者个人号
		List<Map<String, Object>> newRegisterWxAssetList = null;
		if (alreadyImportedWxAcctList != null && alreadyImportedWxAcctList.size() > 0) {
			newRegisterWxAssetList = wechatRegisterDao.selectNewWxAssetList(alreadyImportedWxAcctList);
		} else {
			newRegisterWxAssetList = wechatRegisterDao.selectNewWxAssetListWhenFirstImported();
		}
		// Todo:将选择出来的相应数据插入wechat_asset表中
		if (newRegisterWxAssetList != null && newRegisterWxAssetList.size() > 0) {
			wechatAssetDao.insertNewRegisterAsset(newRegisterWxAssetList);
		}
		// Todo:更新wechat_asset表中的total数据和groupIds数据
		alreadyImportedWxAcctList = wechatAssetDao.selectWxAssetList();
		for (String wxAcct : alreadyImportedWxAcctList) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("wx_acct", wxAcct);
			List<Long> groupIdList = wechatAssetGroupDao.selectGroupIdsByWxAcct(paramMap);
			String groupIds = generateGroupIds(groupIdList);
			if (groupIds != null) {
				Integer totalCount = wechatAssetGroupDao.selectMemberCountByWxAcct(paramMap);
				paramMap.put("group_ids", groupIds);
				paramMap.put("total_count", totalCount);
				wechatAssetDao.updateGroupIdsAndTotalCount(paramMap);
			}
		}

		// Todo:更新微信资产的状态信息，未来做成PO与上面那个for循环进行合并
		for (String wxAcct : alreadyImportedWxAcctList) {
			WechatRegister wechatRegister = new WechatRegister();
			wechatRegister.setWxAcct(wxAcct);
			wechatRegister.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID); // 0代表有效
			List<WechatRegister> wechatRegisterList = wechatRegisterDao.selectList(wechatRegister);
			if (!CollectionUtils.isEmpty(wechatRegisterList)) {
				wechatRegister = wechatRegisterList.get(0);
				WechatAsset wechatAsset = new WechatAsset();
				wechatAsset.setAssetName(wechatRegister.getName());
				wechatAsset.setConsignationTime(wechatRegister.getCreateTime());
				wechatAsset.setAssetType(wechatRegister.getType());
				wechatAsset.setImgfileUrl(wechatRegister.getHeaderImage());
				wechatAsset.setWxAcct(wechatRegister.getWxAcct());
				wechatAsset.setNickname(wechatRegister.getNickname());
				wechatAssetDao.updateByWxacct(wechatAsset);
			}
		}

	}

	private String getWechatGroupId(String wxAcct) {
		WechatGroup wechatGroup = new WechatGroup();
		wechatGroup.setWxAcct(wxAcct);
		wechatGroup.setStartIndex(null);
		wechatGroup.setPageSize(null);
		List<WechatGroup> selectList = wechatGroupDao.selectList(wechatGroup);
		if (!CollectionUtils.isEmpty(selectList)) {
			StringBuffer sb = new StringBuffer();
			for (WechatGroup info : selectList) {
				sb.append(info.getGroupId());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		return "";
	}

	private int getWechatMemberCount(String wxAcct) {
		WechatMember wechatMember = new WechatMember();
		wechatMember.setPubId(wxAcct);
		wechatMember.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int selectListCount = wechatMemberDao.selectListCount(wechatMember);
		return selectListCount;
	}
	
	private int getWechatMemberCountByPubIdAndGroupId(String pubId,String groupId) {
		WechatMember wechatMember = new WechatMember();
		wechatMember.setPubId(pubId);
		wechatMember.setWxGroupId(groupId+",");
		wechatMember.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int selectListCount = wechatMemberDao.selectListCount(wechatMember);
		return selectListCount;
	}

	private String generateGroupIds(List<Long> groupIdList) {
		StringBuffer sf = new StringBuffer();
		if (groupIdList != null && groupIdList.size() > 0) {
			for (Long id : groupIdList) {
				sf.append(id + ",");
			}
			sf.deleteCharAt(sf.length() - 1);
			return sf.toString();
		}
		return null;
	}
}
