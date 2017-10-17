package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 根据条件查询联系人表单
 *
 * @see （与该类关联的类): ContactListGetByStatusServiceImpl
 * @对应项目名称: MC系统
 * @author: zhaoguoying
 * @version: v1.5
 * @date(创建、开发日期): 2016-08-12
 * 最后修改日期: 2016-11-08
 * @复审人: 丛树林
  *************************************************/
public interface ContactListGetByStatusService {
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
	BaseOutput getContactList(Integer contactStatus,String contactId, String contactName, int index, int size);
		
}
