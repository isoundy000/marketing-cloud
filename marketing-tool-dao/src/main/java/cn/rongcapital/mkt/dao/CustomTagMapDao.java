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

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.po.CustomTagWithName;

public interface CustomTagMapDao extends BaseDao<CustomTagMap>{
	
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
     * @功能简述 : 根据细分Id逻辑删除细分关联的标签
     * @param headId
     * @author zhuxuelong
     */
	void batchDeleteUseHeadId(String headerId);
	
    /**
     * @功能简述 : 根据细分Id获取细分关联的标签
     * @param headId
     * @author zhuxuelong
     * @return List
     */
	List<CustomTagWithName> getTagUseHeadId(Long headerId);

	/**
	 * @功能简述 : 批量插入数据
	 * @param headId
	 * @author zhuxuelong
	 * @return List
	 */
	void batchInsert(List<Map<String, Object>> insertCustomTagMapList);

	/**
	 * @功能简述 : 搜索partyIdList
	 * @param headId
	 * @author zhuxuelong
	 * @return List
	 */
	List<Integer> selectTagIdList(Integer tag_id);
	
	/**
     * @功能简述 : 根据tagName获取tagMap
     * @param tagName
     * @author nianjun
     * @return List
     */
	List<CustomTagMap> selectCustomTagMapByTagName(String tagName);
	
	/**
     * @功能简述 : 根据mapId获取tagMap
     * @param mapId
     * @author nianjun
     * @return List
     */
    List<CustomTagMap> selectCustomTagMapByTagNameandMapId(Map<String, Object> paramMap);
    
    /**
     * @功能简述 : 根据tagId逻辑删除细分关联的人群
     * @param tagId
     * @author zhuxuelong
     */
	void batchDeleteUseTagId(String tagId);
	
	/**
	 * @Title: deleteCustomTagMapByMapId   
	 * @Description: 通过mapId进行删除 
	 * @param: @param mapId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int deleteCustomTagMapByMapId(@Param("mapId") Integer mapId);
	
}