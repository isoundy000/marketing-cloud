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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.po.DataShopping;
import cn.rongcapital.mkt.po.OrderCount;
import cn.rongcapital.mkt.po.ShoppingWechat;

public interface DataShoppingDao extends BaseDao<DataShopping>, BaseDataFilterDao<DataShopping> {
	
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
	void cleanAndUpdateByOriginal(List<DataShopping> dataShoppings);

	/**
	 * 批量更新数据状态
	 * @param idList
	 * @return
	 */
	int updateStatusByIds(@Param("list") List<Integer> idList, @Param("status") Integer status);

	List<OrderCount> selectListByWxData(DataShopping dataShopping);
	
    /**
     * @功能简述 : 根据购物记录中单个微信用户（公众号标识＋openId）
     * 最后一次购买（取订单号的消费时间记最后一次购买时间）的时间获取数据
     * @return List<ShoppingWechat>
     */
    List<ShoppingWechat> selectListByLastTransTimeandWeChatInfo();
    
    /**
     * @功能简述 : 根据购物记录中单个微信用户（公众号标识＋openid）购买的总次数(看订单号)获取数据量
     * @return ShoppingWechat
     */
    List<ShoppingWechat> selectTotalShoppingCountByWeChatInfo();
    
    
    /**
     * @功能简述 : 根据购物记录中单个微信用户（公众号标识＋openid）单月购买次数（订单号）获取数据量
     * @return ShoppingWechat
     */
    List<ShoppingWechat> selectSingleMonthShoppingCountByWeChatInfo();
    
    /**
     * @功能简述 : 获取所有微盟的数据
     * @return ShoppingWechat
     */
    List<ShoppingWechat> selectAllDataByWeimob();
    
    /**
     * @功能简述 : 获取所有含有微信信息的数据
     * @return ShoppingWechat
     */
    List<ShoppingWechat> selectAllDataByWechatInfo();
    
    /**
     * @Title: selectLastTransTimeandByKeyid   
     * @Description: 根据keyId获取单个微信用户最后一次购买的时间  
     * @param: @param keyId
     * @param: @return      
     * @return: Date      
     * @throws
     */
    Date selectLastTransTimeByKeyid(@Param("keyId")Integer keyId);
    
    /**
     * @Title: selectTotalShoppingCountByKeyid   
     * @Description: 根据keyId获取单个微信用户购买的总次数
     * @param: @param keyId
     * @param: @return      
     * @return: Integer      
     * @throws
     */
    Integer selectTotalShoppingCountByKeyid(@Param("keyId")Integer keyId);
    
    /**
     * @Title: selectWeimobByKeyid   
     * @Description: 根据keyId获取单个微信用户购买渠道偏好
     * @param: @param keyId
     * @param: @return      
     * @return: String      
     * @throws
     */
    List<ShoppingWechat> selectWeimobByKeyid(@Param("keyId")Integer keyId);
    
    /**
     * @Title: selectSingleMonthShoppingCountByKeyid   
     * @Description: 查询单个微信用户单月购买数量  
     * @param: @param keyId
     * @param: @return      
     * @return: Integer      
     * @throws
     */
    Integer selectSingleMonthShoppingCountByKeyid(@Param("keyId")Integer keyId);
    
    /**
     * @Title: selectOrderStatusByKeyid   
     * @Description: 通过keyId查询单个微信用户订单状态   
     * @param: @param keyId
     * @param: @return      
     * @return: String      
     * @throws
     */
    List<ShoppingWechat> selectOrderStatusByKeyid(@Param("keyId")Integer keyId);
    
	/**
	 * 更新Keyid
	 * newkeyId-5
	 * oldkeyId-6
	 */
    void updateDataShoppingKeyid(Map<String,Object> map);
}