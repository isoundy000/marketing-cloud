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
import cn.rongcapital.mkt.po.SegmentBodyWithName;
import cn.rongcapital.mkt.po.SegmentationBody;

public interface SegmentationBodyDao extends BaseDao<SegmentationBody>{
	
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
     * @功能简述 : 根据细分Id逻辑删除受众细分body信息
     * @param headId
     * @author zhuxuelong
     */
	void batchDeleteUseHeaderId(Integer headId);
	
    /**
     * @功能简述 : 根据细分Id获取受众细分body信息
     * @param headId
     * @author zhuxuelong
     * @return List
     */
	List<SegmentBodyWithName> getSegBodyUseHeaderId(Integer headId);
	
	/**
	 * 查询细分包含标签
	 * @param headId
	 * @return
	 */
	List<String> getContainTagsByHeadId(Integer headId);
	 /**
     * @功能简述 : 通过标签ID查询记录条数
     * @param  tagId  标签ID
     * @author wangweiqiang
     * @return Integer
     */
	Integer getCountByTagId(@Param("tagId") String tagId);
	
}