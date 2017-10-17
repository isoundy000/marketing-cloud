package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactTemplateIn;

public interface ContactTemplateServer {
	
	/***
	 * 根据查询的选中关系，保持到对应表
	 * @param ctIn
	 * @return
	 */
	public BaseOutput ContactListCreate(ContactTemplateIn ctIn);
	
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	public BaseOutput updateContextTempById(Long id);
}
