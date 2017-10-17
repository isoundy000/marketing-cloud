/*************************************************
 * @功能简述: DAO基类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao.base;

import java.util.List;

public interface BaseDao<T> {

	/**
	 * @功能简述: 添加对象
	 * @param: T t
	 * @return: int 影响的条数
	 */
	int insert(T t);
	
	/**
	 * @功能简述: 更新对象,条件主键ID
	 * @param: T t
	 * @return: int 影响的条数
	 */
	int updateById(T t);
	
	/**
	 * @功能简述: 查询对象list,只要不为NULL与空则为条件,属性值之间and连接
	 * @param: T t
	 * @return: List<T>
	 */
	List<T> selectList(T t);
	
	/**
	 * @功能简述: 查询对象list,查询条件:where id in(id1,id2...);非null的id才有效
	 * @param: List<Integer> idList
	 * @return: List<T>
	 */
    List<T> selectListByIdList(List<Integer> idList);

	/**
	 * @功能简述: 查询对象总数,只要不为NULL与空则为条件,属性值之间and连接
	 * @param: T t
	 * @return: int
	 */
	int selectListCount(T t);
	
}
