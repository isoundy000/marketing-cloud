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

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import org.apache.ibatis.annotations.Param;

public interface ContactTemplateDao extends BaseDao<ContactTemplate>{
	
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
	 * 通过id更新浏览次数
	 * @param t
	 * @return
	 */
	int updatePageViewsById(ContactTemplate t);
	
	/***
	 * 根据c_id删除
	 * @param t
	 * @return
	 */
	int deleteByCId(ContactTemplate t);
	
	/***
	 * 根据ContactTemplate的contactId选取必填项 且 在DefaultContactTemplate表中为主键 根据field_name 和field_code 关联
	 * @param t
	 * @return id
	 */
	List<ContactTemplate> selectIdByContactId(ContactTemplate t);
	
	/**
	 * 基类自动生成的updateById有问题，这个和基类的功能相同
	 * @param t
	 * @return
	 */
	int updateById1(ContactTemplate t);
	
	List<ContactTemplate> selectListAll(ContactTemplate t);
	
	List<ContactTemplate> selectListGroupByCId(ContactTemplate t);

	/***
	 * 根据ContactTemplate的contactId选取必填项 且 在DefaultContactTemplate表中为主键 根据field_name 和field_code 关联
	 * @param contactTemplateKeys
	 * @return ArrayList<String>
	 */
	ArrayList<String> selectFieldCodeListByFieldNameList(@Param("filedNameList") ArrayList<String> contactTemplateKeys);

	/***
	 * 获取联系人模板的总数
	 * @param contactTemplate
	 * @return ArrayList<String>
	 */
	Integer selectRealContactTemplateListCount(ContactTemplate contactTemplate);

	/***
	 * 更改联系人表单
	 * @param updateContactTemplate
	 * @return ArrayList<String>
	 */
	void updateKeyListById(ContactTemplate updateContactTemplate);
}