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
import cn.rongcapital.mkt.po.WechatRegister;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WechatRegisterDao extends BaseDao<WechatRegister>{

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
	 * 插入授权成功的微信公众号
	 * @param paramMap
	 * @return list
	 */
	void insertAuthPublic(Map<String, Object> paramMap);

	/**
	 * 查询这个微信号的授权状态
	 * @param paramMap
	 * @return list
	 */
	Integer selectStatus(String wx_acct);

	/**
	 * 更新微信公众号的信息
	 * @param paramMap
	 * @return list
	 */
	Integer updatePubInfo(Map<String, Object> paramMap);

	/**
	 * 批量插入个人号
	 * @param list
	 */
	void batchInsertPersonList(List<Map<String, Object>> paramPersonals);

	/**
	 * 判断这个个人号是否已经插入
	 * @param list
	 */
	Long selectPersonalId(Map<String, Object> paramMap);

	/**
	 * 获取新注册的微信资产的相关信息
	 * @param list
	 */
	List<Map<String,Object>> selectNewWxAssetList(List<String> alreadyImportedWxAcctList);

	/**
	 * 第一次导入时获取新注册的微信资产的相关信息
	 * @param list
	 */
	List<Map<String,Object>> selectNewWxAssetListWhenFirstImported();

	/**
	 * 根据微信账号跟新个人号相关信息
	 * @param
	 */
	void updateInforByWxAcct(WechatRegister wechatRegister);

	/**
	 * 根据微信账号跟新第二次授权时间
	 * @param wechatRegister
	 */
	void updateConsignationTimeByWxacct(WechatRegister wechatRegister);

	/**
	 * 根据微信账号跟新第二次授权时间
	 * @param wechatRegister
	 */
	List<String> selectWxacctList(WechatRegister wechatRegister);
	
	/**
	 * 根据微信账号批量更新微信状态
	 * @param wxAcctList
	 */
	void batchUpdateStatusList(List<String> wxAcctList);
}