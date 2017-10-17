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
import cn.rongcapital.mkt.po.AudienceCount;
import cn.rongcapital.mkt.po.AudienceList;

import java.util.List;
import java.util.Map;

public interface AudienceListDao extends BaseDao<AudienceList>{
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象list;
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<AudienceList> selectListBycustomMap(Map<String,Object> t);
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象总数
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListCountBycustomMap(Map<String,Object> paramMap);

	//自定义扩展
	/**
	 * 微信资产下保存人群的工作
	 * @param paramMap
	 * @return int
	 */
	Integer insertWechatGroups(Map<String,Object> paramMap);
	
	
	/**
     * 活动编排-保存当前人群
     * @param paramMap
     * @return int
     */
    Integer insertAudience(Map<String,Object> paramMap);

	/**
	 * 主数据查询(主界面搜索栏里面的模糊查询)
	 * @param paramMap
	 * @return int
	 */
	List<Map<String,Object>> searchDataMain(Map<String, Object> paramMap);

	/**
	 * 根据人群名称查找主键Id
	 * @param paramMap
	 * @return int
	 */
	Long selectIdByAudienceName(Map<String, Object> paramMap);


	AudienceCount selectAudienceCount(AudienceList audienceList);

}