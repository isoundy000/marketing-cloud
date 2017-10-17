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

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.WechatChannel;

public interface WechatChannelDao extends BaseDao<WechatChannel>{
	
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
	 * @功能简述: 查询对象总数,只要不为NULL与空则为条件,属性值之间and连接 模糊查询
	 * @param: T t
	 * @return: int
	 */
	int selectListCount_Like(WechatChannel t);
	/**
	 * @Title: getWechatChaCountByName   
	 * @Description: 通过名称查询是否存在  
	 * @param: @param chaName
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int getWechatChaCountByName(@Param("chaName") String chaName);
	
	/**
	 * @功能简述: 删除渠道信息
	 * @param: WechatChannel t
	 * @return: int
	 */
	int delete(WechatChannel t);
}