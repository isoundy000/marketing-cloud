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
import cn.rongcapital.mkt.po.AudienceListPartyMap;

import java.util.List;
import java.util.Map;

public interface AudienceListPartyMapDao extends BaseDao<AudienceListPartyMap>{
	
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
	
	/***
	 * 根据人群id更新表
	 * @param t
	 * @return 影响条目
	 */
	int updateByListId(AudienceListPartyMap t);

	/***
	 * 根据人群id更新表
	 * @param Integer
	 * @return 影响条目
	 */
	List<Map<String,Object>> searchPartyList(Map<String,Object> paramMap);

	/***
	 * 批量插入数据
	 * @param Integer
	 * @return 影响条目
	 */
	int batchInsert(List<Map<String, Object>> paramInsertLists);
	
	
	List<String> selectPartyIdList(List<Integer> audience_ids);

	/***
	 * 根据audience_id来搜索partyId
	 * @param Integer
	 * @return 影响条目
	 */
	List<String> selectPartyIdLIistByAudienceId(String audience_list_id);
}