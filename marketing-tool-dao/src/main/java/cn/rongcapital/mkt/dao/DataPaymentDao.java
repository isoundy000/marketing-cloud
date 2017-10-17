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
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.po.DataPayment;
import cn.rongcapital.mkt.po.ShoppingWechat;

public interface DataPaymentDao extends BaseDao<DataPayment>, BaseDataFilterDao<DataPayment> {
	
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
	 * @功能简述 : 根据原始数据更新DataLogin的数据
	 * @return map
	 */
	void cleanAndUpdateByOriginal(List<DataPayment> dataPayments);

	/**
	 * 批量更新数据状态
	 * @param idList
	 * @return
	 */
	int updateStatusByIds(@Param("list") List<Integer> idList, @Param("status") Integer status);
	
	/**
     * @功能简述 : 根据支付记录中支付状态成功，获取单个微信用户（公众号标识＋openid）收入金额总额
     * @return ShoppingWechat
     */
	List<ShoppingWechat> selectTotalIncomeAmountByWechatInfo();
	
	/**
     * @功能简述 : 根据支付记录中支付状态成功，获取单个微信用户（公众号标识＋openid）收入金额平均额
     * @return ShoppingWechat
     */
    List<ShoppingWechat> selectAverageIncomeAmountByWechatInfo();
	
	/**
     * @功能简述 : 微信状态不为空的数据全部读取出来
     * @return ShoppingWechat
     */
	List<ShoppingWechat> selectAllDataByWechatInfo();
	
	/**
	 * @Title: selectWxCodeIsNullStatus   
	 * @Description: 通过keyId查询wx_code是否为空  
	 * @param: @param keyId
	 * @param: @return      
	 * @return: Integer      
	 * @throws
	 */
	Integer selectWxCodeIsNullStatus(@Param("keyId")Integer keyId);
	
	/**
	 * @Title: selectAverageIncomeAmountByKeyid   
	 * @Description: 通过keyId查询单个微信用户收入金额平均额   
	 * @param: @param keyId
	 * @param: @return      
	 * @return: Float      
	 * @throws
	 */
	Float selectAverageIncomeAmountByKeyid(@Param("keyId")Integer keyId);
	
	/**
	 * 
	 * @Title: selectTotalIncomeAmountByKeyid   
	 * @Description: 通过keyId查询单个微信用户收入金额总额 
	 * @param: @param keyId
	 * @param: @return      
	 * @return: Float      
	 * @throws
	 */
	Float selectTotalIncomeAmountByKeyid(@Param("keyId")Integer keyId);
	
	/**
	 * 更新Keyid
	 * newkeyId-5
	 * oldkeyId-6
	 */
	void updateDataPopulationKeyid(Map<String,Object> map);

}