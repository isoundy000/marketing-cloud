/*************************************************
 * @功能简述: DAO接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao.material.coupon;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.po.MeterialCouponCodeCountByStatus;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponCodeVerifyListOut;

public interface MaterialCouponCodeDao extends BaseDao<MaterialCouponCode> {

	// 自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展 查询对象list; 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * 
	 * @param paramMap
	 * @return list
	 */
	// List<T> selectListBycustomMap(Map<String,Object> paramMap);

	// 自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展 查询对象总数 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * 
	 * @param paramMap
	 * @return list
	 */
	// List<T> selectListCountBycustomMap(Map<String,Object> paramMap);

	/**
	 * 根据优惠券ID和发放状态 查询优惠券数量
	 * 
	 * @author: 单璟琦
	 * @param paramMap
	 * @return List<MeterialCouponCodeCountByStatus>
	 */
	List<MeterialCouponCodeCountByStatus> selectCouponTotalByCouponIdAndReleStatus(Map<String, Object> paramMap);

	/**
	 * 根据优惠券ID和核销状态 查询优惠券数量
	 * 
	 * @author: 单璟琦
	 * @param paramMap
	 * @return Map<String,Object>
	 */
	List<MeterialCouponCodeCountByStatus> selectCouponTotalByCouponIdAndVeriStatus(Map<String, Object> paramMap);
    
    /**
     * 通过主键和有效状态更新表
     * 
     * @param po
     * @author zhuxuelong
     */
    void updateByIdAndStatus(MaterialCouponCode po);
    
    /**
     * 批量通过主键和有效状态更新表
     * 
     * @param poList
     * @author zhuxuelong
     */
    void batchUpdateByIdAndStatus(List<MaterialCouponCode> poList);
	
	
	  /**@author liuhaizhan
     * @功能简述 删除优惠码
     * @param：param1 message1
     * @return：message2
     */
    int updateByCouponId(long id);

    /**
     * 获取核销对账数据
     * 
     * @param paramMap
     * @return List<MaterialCouponCodeVerifyListOut>
     * @author zhuxuelong
     */
    List<MaterialCouponCodeVerifyListOut> getCouponCodeVerifyList(Map<String, Object> paramMap);
    
    /**
     * 获取核销对账数据总数
     * 
     * @param paramMap
     * @return long
     * @author zhuxuelong
     */
    int getCouponCodeVerifyListCnt(Map<String, Object> paramMap);

    List<MaterialCouponCode> selectIssuedList(MaterialCouponCode code);
 
    void batchInsert(List<MaterialCouponCode> list);

    int getTotleIssuedListCount(MaterialCouponCode code);

    int getTotleListCount(MaterialCouponCode code);

    void deleteCodeByCouponId(Long id);
    /**
     * 获取核销对账数据用户标识信息列表
     * 
     * @param paramMap
     * @return List<String>
     * @author shanjingqi
     */
    List<String> getCouponCodeVerifyUserInfoList(Map<String, Object> paramMap);
    
    
    
    /**
         * @author liuhaizhan
         * @功能简述: 统计优惠券投放成功，失败数量
         * @param 优惠券ID
         * @return 
         */
    List<Map> getCouponPutInCount(long id);
    
    
    /**
         * @author liuhaizhan
         * @功能简述:统计优惠券已核销,未核销金额 
         * @param 优惠券ID
         * @return 
         */
    List<Map> getCouponVerifyCount(long id);
}