package cn.rongcapital.mkt.service.impl;

import java.util.*;

import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.service.DeleteCustomTagService;
import cn.rongcapital.mkt.service.InsertCustomTagService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.service.ContactListTagService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactListTagIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class ContactListTagServiceImpl implements ContactListTagService {

	@Autowired
	private DeleteCustomTagService deleteCustomTagService;

	@Autowired
	private InsertCustomTagService insertCustomTagService;

	@Autowired
	CustomTagMapDao customTagMapDao;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput contactListTag(ContactListTagIn body) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		//对自定义标签进行删除
		deleteTagsByContactId(body.getContactId());

		//获取标签名称
		if(CollectionUtils.isEmpty(body.getTagNames())) return result;
		for(String tagName : body.getTagNames()){
			BaseTag insertedTag = insertCustomTagService.insertCustomTagLeafFromSystemIn(tagName,TagSourceEnum.CONTACT_DOCUMENT_SOURCE_ACCESS.getTagSourceName());
		    CustomTagMap customTagMap = new CustomTagMap();
			customTagMap.setTagId(insertedTag.getTagId());
			customTagMap.setTagSource(TagSourceEnum.CONTACT_DOCUMENT_SOURCE_ACCESS.getTagSourceId());
			customTagMap.setMapId(String.valueOf(body.getContactId()));
			customTagMapDao.insert(customTagMap);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", body.getContactId());
		map.put("updatetime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.getData().add(map);
		result.setTotal(1);

		return result;
	}

	/**
	 * @Title: deleteTagsByContactId
	 * @Description: 添加之前进行自定义标签删除
	 * @param: @param contactId
	 * @return: void
	 * @throws
	 */
	private void deleteTagsByContactId(Integer contactId){
		//先筛选出TagId，然后用TagId和来源对标签进行删除。
		if(null != contactId){
			CustomTagMap paramCustomTagMap = new CustomTagMap();
			paramCustomTagMap.setMapId(String.valueOf(contactId));
			paramCustomTagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			paramCustomTagMap.setTagSource(TagSourceEnum.CONTACT_DOCUMENT_SOURCE_ACCESS.getTagSourceId());
			List<CustomTagMap> tagMaps = customTagMapDao.selectList(paramCustomTagMap);
			if(CollectionUtils.isEmpty(tagMaps)) return;
			for(CustomTagMap customTagMap : tagMaps){
				customTagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
				customTagMapDao.updateById(customTagMap);
			}
		}
	}


}
