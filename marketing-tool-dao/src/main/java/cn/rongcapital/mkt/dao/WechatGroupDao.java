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
import java.util.Map;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.WechatGroup;

public interface WechatGroupDao extends BaseDao<WechatGroup>{

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
	 * 根据公众号和组名唯一选取GroupId
	 * @param paramMap
	 * @return Integer
	 */
	Integer selectGroupId(Map<String, Object> paramGroup);

	/**
	 * 插入一条新的数据
	 * @param paramMap
	 * @return Integer
	 */
	void insertWechatGroup(Map<String, Object> paramGroup);

	/**
	 * 根据个人号群组的ucode获取唯一标识id
	 * @param paramMap
	 * @return Integer
	 */
	Long selectGroupIdByUcode(Map<String, Object> paramMap);

	/**
	 * 插入一条新的数据
	 * @param paramMap
	 * @return Integer
	 */
	void insertWechatGroupByUcode(Map<String, Object> paramMap);

	/**
	 * 更新群组的相关信息
	 * @param paramMap
	 * @return Integer
	 */
	void updateInfoById(Map<String, Object> updateMap);

	/**
	 * 获取尚没有导入的IdList
	 * @param paramMap
	 * @return Integer
	 */
	List<Map<String,Object>> selectNewGroupList(List<Long> alreadyImportedIdList);

	/**
	 * 获取第一次导入的List
	 * @param paramMap
	 * @return Integer
	 */
	List<Map<String,Object>> selectFirstImportGroupList();

	/**
	 * 删除与该uin相关的所有数据
	 * @param paramMap
	 * @return Integer
	 */
	void deleteRecordByUin(String uin);
	
	/**
	 * 更新微信未分组人数
	 * @param wechatGroup
	 * @return 
	 */
	void updateInfoByGroupWxCode(WechatGroup wechatGroup);
	
	/**
	 * 根据微信账号更新微信公众号状态
	 * @param WechatGroupList
	 */
	void updateStatusByWxAcct(WechatGroup wechatGroup);
}