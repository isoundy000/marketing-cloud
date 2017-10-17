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
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.CustomTag;

public interface CustomTagDao extends BaseDao<CustomTag>{
	
	/**
	 * 通过tag_id删除tag
	 * @param paramMap
	 * @return list
	 */
	int logicDeleteTagById(Map<String, Object> paramMap);
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
     * @功能简述: 查询对象list并锁表,只要不为NULL与空则为条件,属性值之间and连接
     * @param: CustomTag t
     * @return: List<CustomTag>
     */
    List<CustomTag> selectListforUpdate(CustomTag t);
    
    /**
     * @功能简述: 跟心cover audience count
     * @param: CustomTag t
     * @return: List<CustomTag>
     */
    int increaseCoverAudienceCount(CustomTag t);
    
    /**
     * @功能简述: 根据contactId获取用户所有的自定义标签
     * @param: Map<String , Integer>
     * @return: List<String>
     */
    List<String> selectTagsByContactId(Map<String, Integer> paramMap);

	/**
	 * @功能简述: 批量插入标签
	 * @param: Map<String , Integer>
	 * @return: List<String>
	 */
	void batchInsert(List<Map<String, Object>> insertCustomTagList);

	/**
	 * @功能简述: 根据名称获取Id
	 * @param: Map<String , Integer>
	 * @return: List<String>
	 */
	List<Long> selectIdsByCustomTags(ArrayList<String> tag_names);
	
	/**
     * @功能简述: 获取未删除的CustomTag
     * @param: CustomTag t
     * @return: List<CustomTag>
     */
	List<CustomTag> selectListUndeleteTags(CustomTag t);
	
	List<CustomTag> selectByNameFuzzy(CustomTag param);
	
	int delecteCustomTagByContactId(@Param("contactId")Integer contactId);
	
	/**
	 * @Title: selectCustomTagsByIds   
	 * @Description: 根据id查询标自定义签列表
	 * @param: @param ids
	 * @param: @return      
	 * @return: List<Tag>      
	 * @throws
	 */
	List<CustomTag> selectCustomTagsByIds(String[]ids);
	
	/**
	 * 通过名称获取id
	 * @param name
	 * @return
	 */
	Integer selectIdByName(@Param("name") String name);
	
	/**
	 * 插入返回主键
	 * @param customTag
	 * @return
	 */
	Integer insertCustomTagResId(CustomTag customTag);
	
}