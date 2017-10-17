/*************************************************
 * @功能简述: 获取优惠券核销流失概览
 * @see CouponApi：
 * @author: 单璟琦
 * @version: 1.0 @date：2016-12-09
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponVerifyGeneralService {
	
	public BaseOutput verifyGeneralById(Long id,String userToken,String version);

}
