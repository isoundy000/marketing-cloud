/*************************************************
 * @功能简述: DAO接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import java.util.List;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.po.DefaultContactTemplate;

public interface DefaultContactTemplateDao extends BaseDao<DefaultContactTemplate>{
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象list;
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListBycustomMap(Map<String,Object> paramMap);
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象总数
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListCountBycustomMap(Map<String,Object> paramMap);
	
	/**
	 * 根据ContactTemplate的contactId选取必填项 且 在DefaultContactTemplate表中为主键 根据field_name 和field_code 关联
	 * @param T
	 * @return field_name field_code isSelected
	 * @version: 0.0.1
	 * @author shuiyangyang
	 * @Data 2016.08.18
	 */
	List<DefaultContactTemplate> selectKeyByContactId(ContactTemplate T);
	
	
}