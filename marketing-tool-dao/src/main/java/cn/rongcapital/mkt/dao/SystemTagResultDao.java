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

import cn.rongcapital.mkt.po.SystemTagResult;

public interface SystemTagResultDao {

	/**
	 * @功能简述: 查询对象list,只要不为NULL与空则为条件,属性值之间and连接
	 * @param: T
	 *             t
	 * @return: List<T>
	 */
	List<SystemTagResult> selectListByMap(@Param("tagView") String tagView);

}