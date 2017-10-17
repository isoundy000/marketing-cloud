package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListQrcodeDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class ContactListQrcodeDownloadServiceImpl implements ContactListQrcodeDownloadService {

	@Autowired
	ContactTemplateDao contactTemplateDao;
	
	@Override
	public BaseOutput getContactListQrcode(Integer contactId) {
		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setContactId(Long.valueOf(contactId));
		List<ContactTemplate> contactTemplates = contactTemplateDao.selectList(contactTemplate);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if(CollectionUtils.isNotEmpty(contactTemplates))
			result.setTotal(contactTemplates.size());
		if(contactTemplates.size()>0)
		{
			Map<String, Object> map = new HashMap<>();
			map.put("pic_url", contactTemplates.get(0).getQrcodeUrl());
			result.getData().add(map);
		}
		return result;
	}
}
