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
import cn.rongcapital.mkt.po.CustomTagOriginalDataMap;

import java.util.List;

public interface CustomTagOriginalDataMapDao extends BaseDao<CustomTagOriginalDataMap>{

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
	 * 获取尚未同步完毕的自定义标签id
	 * @param paramCustomTagOriginalDataMap
	 * @return list
	 */
	List<Integer> selectDintinctUnhandleIdList(CustomTagOriginalDataMap paramCustomTagOriginalDataMap);

	/**
	 * 根据标签获取该标签下尚未被同步的数据数量
	 * @param customTagOriginalDataMap
	 * @return list
	 */
	Integer selectUnhandledDataCountByTagId(CustomTagOriginalDataMap customTagOriginalDataMap);

	/**
	 * 根据标签获取该标签下已经被同步的数据数量
	 * @param customTagOriginalDataMap
	 * @return list
	 */
	Integer selectHandledDataCountByTagId(CustomTagOriginalDataMap customTagOriginalDataMap);
}