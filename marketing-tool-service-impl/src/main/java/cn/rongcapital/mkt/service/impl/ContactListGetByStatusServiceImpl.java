package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactListDao;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactList;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListGetByStatusService;
import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 根据条件查询联系人表单
 *
 * @see （与该类关联的类): ContactListGetByStatusService
 * @对应项目名称: MC系统
 * @author: zhaoguoying
 * @version: v1.5
 * @date(创建、开发日期): 2016-08-12
 * 最后修改日期: 2016-11-08
 * @复审人: 丛树林
  *************************************************/
@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class ContactListGetByStatusServiceImpl implements ContactListGetByStatusService {

	@Autowired
	Environment env;

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Autowired
	ContactListDao contactListDao;

	/**
	 * @功能简述: 根据条件查询联系人表单
	 * 
	 * @param: contactStatus
	 *             表单状态
	 * @param: contactId
	 *             表单编号
	 * @param: contactName
	 *             表单名称
	 * @param: index
	 *             条数索引
	 * @param: size
	 *             条数
	 * @return: BaseOutput
	 */
	@Override
	public BaseOutput getContactList(Integer contactStatus, String contactId, String contactName, int index, int size) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		ContactTemplate contactTemplate = new ContactTemplate();

		contactTemplate.setStatus(contactStatus.byteValue());
		if (contactStatus == 3) {
			contactTemplate.setStatus(null);
		}
		if (contactId != null && contactId.length() > 0) {
			contactTemplate.setContactId(Long.valueOf(contactId));
		}
		if (contactName != null && contactName.length() > 0) {
			contactTemplate.setContactName(contactName);
		}

		Integer totalCount = contactTemplateDao.selectRealContactTemplateListCount(contactTemplate);
		result.setTotal(totalCount);
		// set index and size
		contactTemplate.setPageSize(size);
		contactTemplate.setStartIndex((index - 1) * size);
		// wangweiqiang update 2016-09-19(添加创建时间倒叙)
		contactTemplate.setOrderField("create_time");
		contactTemplate.setOrderFieldType("DESC");

		List<ContactTemplate> contactTemplateList = contactTemplateDao.selectListGroupByCId(contactTemplate);

		ContactList contactList = null;
		if (CollectionUtils.isNotEmpty(contactTemplateList)) {
			List<Object> resultData = result.getData();
			for (ContactTemplate contactTem : contactTemplateList) {

				Map<String, Object> contactListMap = new HashMap<String, Object>();
				contactListMap.put("contact_id", contactTem.getContactId());
				contactListMap.put("contact_name", contactTem.getContactName());
				contactListMap.put("qrcode_shorturl",
						env.getProperty("contact.short.url") + contactTem.getQrcodeShorturl());
				contactListMap.put("qrcode_pic", "contactlist/" + contactTem.getQrcodePic());
				contactList = new ContactList();
				contactList.setStartIndex(null);
				contactList.setPageSize(null);
				contactList.setContactTemplId(contactTem.getContactId().intValue());
				List<ContactList> selectContactList = contactListDao.selectList(contactList);
				contactListMap.put("user_count", selectContactList.size());
				contactListMap.put("contact_status", contactTem.getStatus());
				resultData.add(contactListMap);
			}
		}
		return result;
	}

}
