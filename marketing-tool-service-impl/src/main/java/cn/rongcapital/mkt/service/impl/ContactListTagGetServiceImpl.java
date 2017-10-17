package cn.rongcapital.mkt.service.impl;

import java.util.List;

import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.vo.out.ContactTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.service.ContactListTagGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ContactListTagOut;
import org.springframework.util.CollectionUtils;

@Service
public class ContactListTagGetServiceImpl implements ContactListTagGetService {

	@Autowired
	private CustomTagMapDao customTagMapDao;
	@Autowired
	private FindCustomTagInfoService findCustomTagInfoService;

	@Override
	public BaseOutput getContactListTag(Integer contactId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		ContactListTagOut contactListTagOut = new ContactListTagOut();
		CustomTagMap paramCustomTagMap = new CustomTagMap();
		paramCustomTagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		paramCustomTagMap.setMapId(String.valueOf(contactId));
		paramCustomTagMap.setTagSource(TagSourceEnum.CONTACT_DOCUMENT_SOURCE_ACCESS.getTagSourceId());
		List<CustomTagMap> customTagMapList = customTagMapDao.selectList(paramCustomTagMap);
		if(!CollectionUtils.isEmpty(customTagMapList)){
			for(CustomTagMap customTagMap : customTagMapList){
				BaseTag baseTag = findCustomTagInfoService.findCustomTagInfoByTagId(customTagMap.getTagId());
				ContactTags contactTags = new ContactTags();
				contactTags.setTagId(baseTag.getTagId());
				contactTags.setTagName(baseTag.getTagName());
				contactListTagOut.getContactTags().add(contactTags);
			}
		}

		result.getData().add(contactListTagOut);
		return result;
	}

}