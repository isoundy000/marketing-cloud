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
import cn.rongcapital.mkt.po.Taggroup;

public interface TaggroupDao extends BaseDao<Taggroup>{
	
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
	
	List<Taggroup> selectByNameFuzzy(Taggroup param);

	/**
	 * 获取系统标签组列表
	 * @param paramMap
	 * @return list
	 */
	List<Map<String, Object>> selectTaggroupSystemMenulist(Taggroup param);
	
	/**
     * 获取系统标签内容列表
     * @param paramMap
     * @return list
     */
	List<String> selectSubNodesByGroupName(Map<String, Object> paramMap);
	
	/**
     * 获取系统标签数量
     * @param paramMap
     * @return list
     */
	//按Jiangyi的说法, 叶子节点以及该节点的parent算是标签, 再往上都不是标签.
	Integer selectSystemTagCount();
	
	/**
	 * @Title: selectNameById   
	 * @Description: 通过id查询name   
	 * @param: @param id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	String selectNameById(@Param("id")Integer id);
}