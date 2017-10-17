/*************************************************
 * @功能简述: MaterialCouponReleaseGeneralService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: 单璟琦
 * @version: 0.0.1
 * @date: 2016/12/09
 * @复审人:

 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponReleaseGeneralService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponVerifyGeneralService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;


@Service
public class MaterialCouponVerifyGeneralServiceImpl implements MaterialCouponVerifyGeneralService {

	@Autowired
	MaterialCouponDao materialCouponDao;
	
	@Autowired
	MaterialCouponReleaseGeneralService mcReleaseServera;
	
	private static final String EXPECT_RELEASE_AMOUNT = "expect_release_amount";// 预期投放金额
	private static final String ACTUAL_RELEASE_AMOUNT = "actual_release_amount";// 真实投放金额
	private static final String ACTUAL_REACHED_AMOUNT = "actual_reached_amount";// 真实触达金额
	private static final String ACTUAL_VERIFY_AMOUNT = "actual_verify_amount";// 真实核销金额

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput verifyGeneralById(Long id, String userToken, String version) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Map<String, Object> paramMap = new HashMap();
		paramMap.put("id", id);
		MaterialCoupon mc = materialCouponDao.selecCouponAmountByCouponId(paramMap);
		Double couponAmount = mc !=null ?  mc.getAmount().doubleValue(): new Double("0.00");
		
		Map<String, Long> map = mcReleaseServera.getReleaseAndVerifyCouponCount(id);
		Long couponCount = map.get("couponCount");
		Long received = map.get("received");
		Long unreceived = map.get("unreceived");
		Long verified = map.get("verified");

		Map<String, Double> rMap = new HashMap<String, Double>();
		// 预期投放金额 = 券码总量 * 券码金额
		rMap.put(EXPECT_RELEASE_AMOUNT, couponCount*couponAmount); 
		// 真实投放金额= （投放成功 +投放失败）* 券码金额
		rMap.put(ACTUAL_RELEASE_AMOUNT, (received + unreceived)*couponAmount); 
		// 真实触达金额 = 投放成功 * 券码金额
		rMap.put(ACTUAL_REACHED_AMOUNT, received*couponAmount); 
		// 真实核销金额= 已核销 * 券码金额
		rMap.put(ACTUAL_VERIFY_AMOUNT, verified*couponAmount); 
		result.getData().add(rMap);

		return result;
	}

}
