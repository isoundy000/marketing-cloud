package cn.rongcapital.mkt.job.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.CustomTagMap;

@Service
public class CustomTagAudienceCountTask implements TaskService {

	@Autowired
	private CustomTagDao customTagDao;
	@Autowired
	private CustomTagMapDao customTagMapDao;

	private static final int pageSize = 100;

	@Override
	public void task(Integer taskId) {
//		CustomTag customTagT = new CustomTag();
//		customTagT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//		int totalRecord = customTagDao.selectListCount(customTagT);
//		int totalPage = (totalRecord + pageSize -1) / pageSize;
//		for(int index = 1;index <= totalPage; index++) {
//			customTagT = new CustomTag(index,pageSize);
//			customTagT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//			List<CustomTag> customTagList = customTagDao.selectList(customTagT);
//			if(CollectionUtils.isEmpty(customTagList)){
//				break;
//			}
//			for(CustomTag customTag:customTagList) {
//				Set<Integer> dataPartyIdSet = new HashSet<Integer>();
//				int cutomTagId = customTag.getId();
//				dataPartyIdSet = getCampaginTagDataPartyIdList(dataPartyIdSet,cutomTagId);
//				dataPartyIdSet = getContactTagDataPartyIdList(dataPartyIdSet,cutomTagId);
//				//TO DO:上传文件来源的tag关联的人群，由于使用的是origin_*表的id，
//				//并且没有设置md_ype,无法获取到data_party表的id
//				customTag.setCoverAudienceCount(dataPartyIdSet.size());
//				customTagDao.updateById(customTag);
//			}
//		}
	}

	/**
	 * 获取营销活动中设置的自定义标签覆盖的人群:data_party表的id list
	 */
	private Set<Integer> getCampaginTagDataPartyIdList(Set<Integer> dataPartyIdSet,int customTagId) {
//		CustomTagMap customTagMapT = new CustomTagMap();
//		customTagMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//		customTagMapT.setTagId(String.valueOf(customTagId));
//		customTagMapT.setTagSource(TagSourceEnum.CAMPAIGN_SOURCE_ACCESS.getTagSourceId());
//		int totalRecord = customTagMapDao.selectListCount(customTagMapT);
//		int totalPage = (totalRecord + pageSize -1) / pageSize;
//		for(int index = 1;index <= totalPage; index++) {
//			customTagMapT = new CustomTagMap(index,pageSize);
//			customTagMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//			customTagMapT.setTagId(String.valueOf(customTagId));
//			customTagMapT.setTagSource(TagSourceEnum.CAMPAIGN_SOURCE_ACCESS.getTagSourceId());
//			List<CustomTagMap> customTagMapList = customTagMapDao.selectList(customTagMapT);
//			if(CollectionUtils.isEmpty(customTagMapList)) {
//				break;
//			}
//			for(CustomTagMap customTagMap:customTagMapList) {
//				dataPartyIdSet.add(Integer.valueOf(customTagMap.getMapId()));
//			}
//		}
		return dataPartyIdSet;
	}
	/**
	 *
	 * @param dataPartyIdSet
	 * @param customTagId
	 * @return
	 */
	private Set<Integer> getContactTagDataPartyIdList(Set<Integer> dataPartyIdSet,int customTagId) {
//		CustomTagMap customTagMapT = new CustomTagMap();
//		customTagMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//		customTagMapT.setTagId(String.valueOf(customTagId));
////		customTagMapT.setTagSource(ApiConstant.TAG_TYPE_CONTACT);
//		int totalRecord = customTagMapDao.selectListCount(customTagMapT);
//		int totalPage = (totalRecord + pageSize -1) / pageSize;
//		for(int index = 1;index <= totalPage; index++) {
//			customTagMapT = new CustomTagMap(index,pageSize);
//			customTagMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
////			customTagMapT.setTagId(customTagId);
//			customTagMapT.setType(ApiConstant.TAG_TYPE_CONTACT);
//			List<CustomTagMap> customTagMapList = customTagMapDao.selectList(customTagMapT);
//			if(CollectionUtils.isEmpty(customTagMapList)) {
//				break;
//			}
//			for(CustomTagMap customTagMap:customTagMapList) {
////				dataPartyIdSet.add(customTagMap.getMapId());
//			}
//		}
		return dataPartyIdSet;
	}
}
