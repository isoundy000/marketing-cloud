package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListUsedService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactStatusUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;
@Service
public class ContactListUsedServiceImpl implements ContactListUsedService{

	@Autowired
	ContactTemplateDao contactDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput contactStatusUpdate(ContactStatusUpdateIn body)
	{	
		ContactTemplate temp = new ContactTemplate();
		temp.setContactId(body.getContactId());
		temp.setStatus(body.getContact_status());
		
		contactDao.updateById(temp);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO,
				null);
		
		//Date now = new Date();
		//SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", body.getContactId());
		map.put("updatetime", DateUtil.getStringFromDate(new Date(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		result.getData().add(map);
		result.setTotal(1);
		
		return result;
	}
}
