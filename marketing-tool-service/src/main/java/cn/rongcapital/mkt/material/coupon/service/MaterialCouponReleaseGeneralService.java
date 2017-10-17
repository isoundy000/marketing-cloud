/*************************************************
 * @功能简述: 获取优惠券投放流失概览
 * @see CouponApi：
 * @author: 单璟琦
 * @version: 1.0 @date：2016-12-08
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import java.util.Map;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponReleaseGeneralService {

	public BaseOutput releaseGeneralById(Long id,String userToken,String version);
	
	public Map<String, Long> getReleaseAndVerifyCouponCount(Long id);
}
