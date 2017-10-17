package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;

import com.alibaba.fastjson.JSONObject;

@RunWith(MockitoJUnitRunner.class)
public class CouponCodeSaveTaskImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CouponCodeSaveTaskImpl couponCodeSaveTaskImpl;
    
    @Mock
    MaterialCouponDao materialCouponDao;

    @Mock
    MaterialCouponCodeDao materialCouponCodeDao;
    
    MaterialCoupon coupon = new MaterialCoupon();
    
    JSONObject json = new JSONObject();
    
    @Before
    public void setUp(){
        logger.info("测试：CouponCodeSaveTaskImplTest 准备---------------------");
        couponCodeSaveTaskImpl = new CouponCodeSaveTaskImpl();
        ReflectionTestUtils.setField(couponCodeSaveTaskImpl, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(couponCodeSaveTaskImpl, "materialCouponCodeDao", materialCouponCodeDao);
        coupon.setId(426L);
        coupon.setAmount(new BigDecimal(5));
        coupon.setRule("{\"type_code\":\"mixed\",\"length\":5}");
        coupon.setTitle("新MC优惠券");
        coupon.setSourceCode("2");
        coupon.setStockTotal(1);
        Date now = new Date();
        coupon.setStartTime(now);
        coupon.setEndTime(now);
        coupon.setChannelCode("sms");
        
        json.put("edit", false);
        json.put("stock_total", 5);
        json.put("user_token", "1");
        json.put("coupon_id", coupon.getId());
    }
    
    @Test
    public void testCreateMixedCode(){
        Mockito.when(materialCouponDao.selectOneCoupon(426L)).thenReturn(coupon);
        json.put("edit", false);
        json.put("source_code", "2");
        json.put("rule", "{\"type_code\":\"mixed\",\"length\":5}");
        couponCodeSaveTaskImpl.task(json.toString());
    }
    
    @Test
    public void testCreateCommonCode(){
        Mockito.when(materialCouponDao.selectOneCoupon(426L)).thenReturn(coupon);
        json.put("edit", false);
        json.put("source_code", "1");
        json.put("rule", "{\"coupon_code\":\"aaa\"}");
        couponCodeSaveTaskImpl.task(json.toString());
    }
    
    
    @Test
    public void testCreateOwnCode(){
        Mockito.when(materialCouponDao.selectOneCoupon(426L)).thenReturn(coupon);
        json.put("edit", false);
        json.put("rule", "[{\"file_name\":\"1.xlsx\"},{\"file_name\":\"2.xlsx\"}]");
        json.put("source_code", "3");
        couponCodeSaveTaskImpl.task(json.toString());
    }
    
    @Test
    public void testUpdateMixedCode(){
        Mockito.when(materialCouponDao.selectOneCoupon(426L)).thenReturn(coupon);
        json.put("edit", true);
        json.put("source_code", "2");
        json.put("rule", "{\"type_code\":\"mixed\",\"length\":5}");
        couponCodeSaveTaskImpl.task(json.toString());
    }
    
    @Test
    public void testUpdateCommonCode(){
        Mockito.when(materialCouponDao.selectOneCoupon(426L)).thenReturn(coupon);
        json.put("edit", true);
        json.put("source_code", "1");
        json.put("rule", "{\"coupon_code\":\"aaa\"}");
        couponCodeSaveTaskImpl.task(json.toString());
    }
    
    
    @Test
    public void testUpdateOwnCode(){
        Mockito.when(materialCouponDao.selectOneCoupon(426L)).thenReturn(coupon);
        json.put("edit", true);
        json.put("rule", "[{\"file_name\":\"1.xlsx\"},{\"file_name\":\"2.xlsx\"}]");
        json.put("source_code", "3");
        couponCodeSaveTaskImpl.task(json.toString());
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：CouponCodeSaveTaskImplTest 结束---------------------");
    }
}
