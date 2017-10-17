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
import cn.rongcapital.mkt.po.WechatAssetGroup;

public interface WechatAssetGroupDao extends BaseDao<WechatAssetGroup>{


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
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象总数
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	Map<String,Object> selectGroupById(String id);

	/**
	 * 获取多个组的总人数
	 * @param paramMap
	 * @return list
	 */
	Long sumGroupMemberCount(ArrayList<Long> groupIds);

	/**
	 * 获取导入时的组的编号
	 * @param paramMap
	 * @return list
	 */
	List<Long> selectImportGroupIdsByIds(ArrayList<Long> groupIds);

	/**
	 * 获取导入时的组的编号
	 * @param paramMap
	 * @return list
	 */
	List<WechatAssetGroup> selectImportGroupsByIds(ArrayList<Long> groupIds);
	
	/**
	 * 获取所有导入的id列表
	 * @param paramMap
	 * @return list
	 */
	List<Long> selectImportGroupIds();

	/**
	 * 插入新的小组列表
	 * @param paramMap
	 * @return list
	 */
	void insertNewGroupList(List<Map<String, Object>> newGroupList);

	/**
	 * 同步小组人数
	 * @param paramMap
	 * @return list
	 */
	void updateGroupCountById(Map<String, Object> paramMap);

	/**
	 * 获取某个微信账号下的人数
	 * @param paramMap
	 * @return list
	 */
	List<Long> selectGroupIdsByWxAcct(Map<String, Object> paramMap);

	/**
	 * 统计某个微信账号下的所有人数
	 * @param paramMap
	 * @return list
	 */
	Integer selectMemberCountByWxAcct(Map<String, Object> paramMap);

	/**
	 * 删除与该微信账号相关的所有数据
	 * @param paramMap
	 * @return list
	 */
	void deleteRecordByUin(String uin);

	/**
	 * 根据GroupIdList获取这些组所包含的粉丝总数
	 * @param groupIdList
	 * @return list
	 */
	Integer selectCountByGroupId(@Param("idList") List<Integer> groupIdList);

	/**
	 * 根据好友组名称获取人数
	 * @param wechatAssetGroup
	 * @return list
	 */
	Integer selectFriendCount(WechatAssetGroup wechatAssetGroup);
	
	/**
	 * 根据微信号和组更新微信资产相关信息
	 * @param wechatAssetGroup
	 * @return int
	 */
	void updateByWxacctIGroupId(WechatAssetGroup wechatAssetGroup);
}