/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
public interface ContactsLongurlGetService {
	/*
	 * 获取联系人表单对应长链接URL
	 * 接口：mkt.contacts.longurl.get
	 * @author shuiyangyang
	 * @Date 2016.08.31
	 */
	BaseOutput getLongurl(String shortUrl, String device);
}
