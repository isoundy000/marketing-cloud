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

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.WechatPersonalUuid;

import java.util.List;
import java.util.Map;

public interface WechatPersonalUuidDao extends BaseDao<WechatPersonalUuid>{

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
	 * 插入个人号登录时的uuid
	 * @param paramMap
	 * @return list
	 */
	void insertUuidAndUin(Map<String, Object> paramMap);

	/**
	 * 选择当前数据库下status为0的uuid
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,String>> selectEffectiveUuids();

	/**
	 * 将该条uuid跟新为无效数据
	 * @param paramMap
	 * @return list
	 */
	void updateStatus(Map<String,Object> paramMap);

	/**
	 * 根据uin来获取uuid
	 * @param paramMap
	 * @return list
	 */
	Map<String,Object> selectUuidByUin(Map<String,Object> paramMap);
}