/*************************************************
 * @功能简述: MaterialCouponReleaseGeneralService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: 单璟琦
 * @version: 0.0.1
 * @date: 2016/12/07
 * @复审人:

 *************************************************/

package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MeterialCouponCodeCountByStatus;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponReleaseGeneralService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class MaterialCouponReleaseGeneralServiceImpl implements MaterialCouponReleaseGeneralService {
	@Autowired
	MaterialCouponDao materialCouponDao;

	@Autowired
	MaterialCouponCodeDao materialCouponCodeDao;
	
	private static final String EXPECT_RELEASE = "expect_release";//预期投放
	private static final String ACTUAL_RELEASE = "actual_release";//真实投放 
	private static final String ACTUAL_REACHED = "actual_reached";//真实触达 
	private static final String ACTUAL_VERIFY = "actual_verify";//真实核销 
	
	
	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput releaseGeneralById(Long id, String userToken, String version) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Map<String, Long> map = getReleaseAndVerifyCouponCount(id);
		
		Long couponCount = map.get("couponCount");
		Long received = map.get("received"); 
		Long unreceived = map.get("unreceived");
		Long verified = map.get("verified");
		map.clear();
		map.put(EXPECT_RELEASE, couponCount); // 预期投放 = 券码总量
		map.put(ACTUAL_RELEASE, received + unreceived); // 真实投放 = 投放成功 + 投放失败
		map.put(ACTUAL_REACHED, received); // 真实触达 = 投放成功
		map.put(ACTUAL_VERIFY, verified); // 真实核销 = 已核销
		
		result.getData().add(map);

		return result;
	}

	
	public Map<String, Long> getReleaseAndVerifyCouponCount(Long id) {
		Long received = 0l, unreceived = 0l, couponCount = 0l, verified = 0l;

		Map<String, Object> paramMap = new HashMap();
		paramMap.put("id", id);
		couponCount = materialCouponDao.selectStockTotalByCouponId(paramMap);
		paramMap.clear();

		List<String> list = new ArrayList();
		list.add(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
		list.add(MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode());
		paramMap.put("releaseStatusList", list);
		paramMap.put("id", id);
		List<MeterialCouponCodeCountByStatus> relResult = materialCouponCodeDao
				.selectCouponTotalByCouponIdAndReleStatus(paramMap);

		if (!CollectionUtils.isEmpty(relResult)) {
			for (MeterialCouponCodeCountByStatus mcs : relResult) {
				if (MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode().equals(mcs.getStatus())) {
					received = mcs.getCount();
				} else if (MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode().equals(mcs.getStatus())) {
					unreceived = mcs.getCount();
				}
			}
		}
		list.clear();
		paramMap.clear();

		list.add(MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode());
		paramMap.put("verifyStatusList", list);
		paramMap.put("id", id);
		List<MeterialCouponCodeCountByStatus> verRresult = materialCouponCodeDao
				.selectCouponTotalByCouponIdAndVeriStatus(paramMap);

		if (!CollectionUtils.isEmpty(verRresult)) {
			for (MeterialCouponCodeCountByStatus mcs : verRresult) {
				if (MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode().equals(mcs.getStatus())) {
					verified = mcs.getCount();
				}
			}
		}

		Map<String, Long> map = new HashMap<String, Long>();
		map.put("couponCount", couponCount !=null ? couponCount : 0l); // 券码总量
		map.put("received", received); // 投放成功 
		map.put("unreceived", unreceived); // 投放失败
		map.put("verified", verified); // 已核销
		return map;
	}

}
