package cn.rongcapital.mkt.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactTemplateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactTempDIn;
import javassist.bytecode.stackmap.BasicBlock.Catch;

@Service
public class ContactTemplateServiceImpl implements ContactTemplateService {

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Override
	public BaseOutput copyContactTemplate(ContactTempDIn body, SecurityContext securityContext) {
		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setContactId(body.getContact_id());
		List<ContactTemplate> contactTemplates = contactTemplateDao.selectList(contactTemplate);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (CollectionUtils.isNotEmpty(contactTemplates) && contactTemplates.size() > 0) {
			result.setTotal(contactTemplates.size());
			for (ContactTemplate w : contactTemplates) {
				ContactTemplate copyContactTemplate = (ContactTemplate) deepClone(w);
				copyContactTemplate.setContactId(Long.valueOf(System.currentTimeMillis() / 1000));
				copyContactTemplate.setContactName("[副本]" + w.getContactName());
				copyContactTemplate.setCreateTime(null);
				copyContactTemplate.setUpdateTime(null);
				copyContactTemplate.setStatus(null);
				contactTemplateDao.insert(copyContactTemplate);
				List<ContactTemplate> copyContactTemplates = contactTemplateDao.selectList(copyContactTemplate);
				if (CollectionUtils.isNotEmpty(copyContactTemplates) && copyContactTemplates.size() > 0) {
					Map<String, Object> map = new HashMap<>();
					map.put("contact_id", copyContactTemplates.get(0).getContactId());
					map.put("updatetime", DateUtil.getStringFromDate(copyContactTemplates.get(0).getUpdateTime(),
							"yyyy-MM-dd HH:mm:ss"));
					result.getData().add(map);
				}
			}
		}
		return result;
	}

	private Object deepClone(Object obj) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);// 从流里读出来
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (oi.readObject());
		}

		catch (Exception ex) {
			return null;
		}
	}
}