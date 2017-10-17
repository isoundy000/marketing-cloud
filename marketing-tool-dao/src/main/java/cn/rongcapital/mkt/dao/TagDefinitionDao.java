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

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.SystemTagResult;
import cn.rongcapital.mkt.po.TagDefinition;

public interface TagDefinitionDao extends BaseDao<TagDefinition>{
	
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
	 * 通过标签名称查询拼接好的Sql
	 * @param tagName	标签名称
	 * @return
	 */
	String selectDefinitionSqlByTagName(@Param("tagName") String tagName);
	/**
	 * 执行sql
	 * @param targetSql	目标执行sql
	 * @return
	 */
	List<SystemTagResult> executeSql(@Param("targetSql") String targetSql);
	
	/**
	 * 通过标签名称设置更新状态
	 * @param tagName	标签名称
	 * @return
	 */
	int updateIsUpdateByTagName(@Param("tagName") String tagName);
	
	
}