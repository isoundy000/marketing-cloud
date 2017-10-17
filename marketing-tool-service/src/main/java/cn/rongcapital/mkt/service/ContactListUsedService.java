package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactStatusUpdateIn;

/*
 * created by chengjincheng 2016-08-15
 */
public interface ContactListUsedService {
	BaseOutput contactStatusUpdate(ContactStatusUpdateIn body);
}
