/*************************************************
 * @功能简述: MaterialCouponVerifyGeneralService实现测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import static org.mockito.Matchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponReleaseGeneralService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponVerifyGeneralService;
import cn.rongcapital.mkt.vo.BaseOutput;


@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponVerifyGeneralServiceTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private MaterialCouponVerifyGeneralService materialCouponVerifyGeneralService;
	
	@Mock
	private MaterialCouponReleaseGeneralService materialCouponReleaseGeneralService;
	
	@Mock
	private MaterialCouponDao materialCouponDao;
	private List<Object> data = new ArrayList<Object>();
	
	private Map<String, Long> map = new HashMap<String, Long>();
	
	@Before
	public void setUp() throws Exception {
		logger.info("测试：MaterialCouponVerifyGeneralService 准备---------------------");

		materialCouponVerifyGeneralService = new MaterialCouponVerifyGeneralServiceImpl();

		map.put("couponCount", 10l); // 券码总量
		map.put("received", 5l); // 投放成功 
		map.put("unreceived", 2l); // 投放失败
		map.put("verified", 3l); // 已核销

		// 把mock的dao set进入service

		
		Map<String, Double> map = new HashMap<String, Double>();
        map.put("expect_release_amount", (double)80.1);
        map.put("actual_release_amount", (double)56.07);
        map.put("actual_reached_amount", (double)40.05);
        map.put("actual_verify_amount", (double)24.03);
        data.add(map);
        
		ReflectionTestUtils.setField(materialCouponVerifyGeneralService, "materialCouponDao", materialCouponDao);
		ReflectionTestUtils.setField(materialCouponVerifyGeneralService, "mcReleaseServera", materialCouponReleaseGeneralService);
	}
	
	@Test
	public void testVerifyGeneralById() {
		logger.info("测试方法: verifyGeneralById start");
		
		MaterialCoupon mc = new MaterialCoupon();
		mc.setAmount(new BigDecimal("8.01"));
		Mockito.when(materialCouponDao.selecCouponAmountByCouponId(any())).thenReturn(mc);
		Mockito.when(materialCouponReleaseGeneralService.getReleaseAndVerifyCouponCount(any())).thenReturn(map);

		BaseOutput result0 = materialCouponVerifyGeneralService.verifyGeneralById(0l, "", "");
		
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result0.getCode());
        Map<String, Double> map0 = (Map<String, Double>)data.get(0);
        Map<String, Double> resultmap0 = (Map<String, Double>)result0.getData().get(0);
        Assert.assertEquals(map0.get("expect_release_amount"),resultmap0.get("expect_release_amount"));
        Assert.assertEquals(map0.get("actual_release_amount"),resultmap0.get("actual_release_amount"));
        Assert.assertEquals(map0.get("actual_reached_amount"),resultmap0.get("actual_reached_amount"));
        Assert.assertEquals(map0.get("actual_verify_amount"),resultmap0.get("actual_verify_amount"));
        Assert.assertEquals(result0.getData().size(),1);
		
		logger.info("测试方法: verifyGeneralById end");
	}

	   @Test
    public void testVerifyGeneralById02() {
        logger.info("测试方法: verifyGeneralById start");
        
        MaterialCoupon mc = null;

        Mockito.when(materialCouponDao.selecCouponAmountByCouponId(any())).thenReturn(mc);
        Mockito.when(materialCouponReleaseGeneralService.getReleaseAndVerifyCouponCount(any())).thenReturn(map);

        BaseOutput result0 = materialCouponVerifyGeneralService.verifyGeneralById(0l, "", "");
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result0.getCode());
        
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("expect_release_amount", (double)0);
        map.put("actual_release_amount", (double)0);
        map.put("actual_reached_amount", (double)0);
        map.put("actual_verify_amount", (double)0);
        
        Map<String, Double> resultmap0 = (Map<String, Double>)result0.getData().get(0);
        Assert.assertEquals(map.get("expect_release_amount"),resultmap0.get("expect_release_amount"));
        Assert.assertEquals(map.get("actual_release_amount"),resultmap0.get("actual_release_amount"));
        Assert.assertEquals(map.get("actual_reached_amount"),resultmap0.get("actual_reached_amount"));
        Assert.assertEquals(map.get("actual_verify_amount"),resultmap0.get("actual_verify_amount"));
        Assert.assertEquals(result0.getData().size(),1);
        
        logger.info("测试方法: verifyGeneralById end");
	}
	
	@After
	public void tearDown() throws Exception {
		logger.info("测试：MaterialCouponVerifyGeneralService 结束---------------------");
	}
	
	
}
