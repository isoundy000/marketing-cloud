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

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;

public interface MaterialCouponDao extends BaseDao<MaterialCoupon> {

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
	 * 根据优惠券ID查询优惠券生成码数量
	 * 
	 * @author: 单璟琦
	 * @param paramMap
	 * @return List<Map<String,Object>>
	 */
	Long selectStockTotalByCouponId(Map<String, Object> paramMap);

    
    /**
     * 通过主键和有效状态更新表
     * 
     * @param po
     * @author zhuxuelong
     */
    void updateByIdAndStatus(MaterialCoupon po);
    
    /**
     * 获取指定条件的优惠券的数量
     * 
     * @param paramMap
     * @return long
     * @author zhuxuelong
     * @Date 2016-12-06
     */
    long getMaterialCouponCount(Map<String,Object> paramMap);
    

    
    /**@author liuhaizhan
     * @功能简述 查询单条优惠券信息
     * @param：id 优惠券唯一主键
     * @return：优惠券实体
     */
    MaterialCoupon selectOneCoupon(long id);
    

    /**
     * 
     * @功能描述: 模糊查询代金券
     * @param paramMaterialCoupon
     * @return List<MaterialCoupon>
     * @author xie.xiaoliang
     * @since 2016年12月6日
     */
    List<MaterialCoupon> selectListByKeyword(MaterialCoupon paramMaterialCoupon);
    
    /**
     * @功能描述: 模糊查询代金券数量
     * @param paramMaterialCoupon
     * @return int
     * @author xie.xiaoliang
     * @since 2016年12月6日
     */
    int selectListByKeywordCount(MaterialCoupon paramMaterialCoupon);

	/**
	 * 根据优惠券ID查询优惠券生成码数量
	 * 
	 * @author: 单璟琦
	 * @param paramMap
	 * @return MaterialCoupon
	 */
    MaterialCoupon selecCouponAmountByCouponId(Map<String, Object> paramMap);
    
    /**
     * 更新优惠券剩余库存
     * 
     * @param id 优惠券主键
     * @param count 消费数量
     * @author zhuxuelong
     */
    void updateCouponStockRest(@Param("id") Long id, @Param("count") int count);
}
