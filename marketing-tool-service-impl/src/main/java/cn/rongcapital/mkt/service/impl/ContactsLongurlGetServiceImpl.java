/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactsLongurlGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class ContactsLongurlGetServiceImpl implements ContactsLongurlGetService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	Environment env;
	
	@Autowired
	ContactTemplateDao contactTemplateDao;
	
	
	/*
	 * 获取联系人表单对应长链接URL
	 * 接口：mkt.contacts.longurl.get
	 * @author shuiyangyang
	 * @Date 2016.08.31
	 */
	@Override
	public BaseOutput getLongurl(String shortUrl, String device) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ONE, null);
		
		ContactTemplate contactTemplate = new ContactTemplate();
		
		// 需要去除短链的头
		//contactTemplate.setQrcodeShorturl(shortUrl.substring(shortUrl.lastIndexOf('/') + 1));
		contactTemplate.setQrcodeShorturl(shortUrl);
		
		List<ContactTemplate> contactTemplateLists = contactTemplateDao.selectList(contactTemplate);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(contactTemplateLists== null || contactTemplateLists.size()<=0) {
			result.setTotal(0);
			logger.info("{} 在数据库中不存在", shortUrl);
		} else {
			
			if("mobile".equals(device)) {
				map.put("long_url", env.getProperty("contact.mobile.url") + "?contact_id=" + contactTemplateLists.get(0).getContactId());
			} else {
				map.put("long_url", env.getProperty("contact.pc.url") + "?contact_id=" + contactTemplateLists.get(0).getContactId());
			}
			logger.info("根据短链：{}, 总共查出{}条数据", shortUrl, contactTemplateLists.size());
		}
		result.getData().add(map);
		return result;
	}

}
