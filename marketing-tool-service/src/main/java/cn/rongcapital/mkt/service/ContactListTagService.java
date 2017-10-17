package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactListTagIn;

/*
 * created by chengjincheng 2016-08-19
 */
public interface ContactListTagService {
	BaseOutput contactListTag(ContactListTagIn body);
}
