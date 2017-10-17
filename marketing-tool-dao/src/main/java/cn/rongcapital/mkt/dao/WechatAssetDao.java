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
import cn.rongcapital.mkt.po.WechatAsset;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WechatAssetDao extends BaseDao<WechatAsset>{
	
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
	 * 根据微信资产种类获取相关的资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> getWechatCountByType(Map<String,Object> paramMap);

	/**
	 * 根据资产类型获取资产列表
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectAssetListByType(Map<String, Object> paramMap);

	/**
	 * 根据资产Id获取资产详细信息
	 * @param paramMap
	 * @return list
	 */
	Map<String,Object> selectWechatAssetDetai(Map<String, Object> paramMap);

	/**
	 * 根据资产Id修改资产昵称
	 * @param paramMap
	 * @return int
	 */
	int updateNicknameById(Map<String, Object> paramMap);

	/**
	 * 获取服务号和订阅号列表
	 * @param paramMap
	 * @return int
	 */
	List<Map<String,Object>> selectServerAndBookList(Map<String, Object> paramMap);

	/**
	 * 获取资产类型和微信账号
	 * @param paramMap
	 * @return int
	 */
	Map<String,Object> selectAssetTypeAndWxacct(Map<String, Object> paramMap);

	/**
	 * 获取所有已经被同步的微信号
	 * @param paramMap
	 * @return int
	 */
	List<String> selectWxAssetList();

	/**
	 * 插入新注册的微信号列表
	 * @param paramMap
	 * @return int
	 */
	void insertNewRegisterAsset(List<Map<String, Object>> newRegisterWxAssetList);

	/**
	 * 更新微信账号的组数和人数信息
	 * @param paramMap
	 * @return int
	 */
	void updateGroupIdsAndTotalCount(Map<String,Object> paramMap);

	/**
	 * 删除与该uin相关的信息
	 * @param paramMap
	 * @return int
	 */
	void deleteRecordByUin(@Param("uin") String uin);

	/**
	 * 根据微信号更新微信资产相关信息
	 * @param wechatAsset
	 * @return int
	 */
	void updateByWxacct(WechatAsset wechatAsset);

	/**
	 * 根据微信号类型获取不同类型对应的GroupIdsList
	 * @param wechatAsset
	 * @return List<String>
	 */
	List<String> selectGroupIdsListByType(WechatAsset wechatAsset);

	/**
	 * 根据微信号类型获取该类型对应的总人数
	 * @param wechatAsset
	 * @return List<String>
	 */
	Integer selectMemberCountByType(WechatAsset wechatAsset);

	List<WechatAsset> selectByWechatAccounts(@Param("wechatAccounts") Set<String> wechatAccounts);
}