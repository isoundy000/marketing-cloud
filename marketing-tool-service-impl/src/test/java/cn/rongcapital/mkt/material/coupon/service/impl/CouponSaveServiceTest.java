package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.CouponSaveService;
import cn.rongcapital.mkt.material.coupon.vo.in.MaterialCouponInfoIn;
import cn.rongcapital.mkt.service.MQTopicService;

@RunWith(MockitoJUnitRunner.class)
public class CouponSaveServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CouponSaveService couponSaveService;
    
    @Mock
    private MaterialCouponDao materialCouponDao;
    
    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    @Mock
    private MQTopicService mqTopicService;
    @Before
    public void setUp() throws Exception {
        logger.info("测试: SegmentCalcServiceTest 开始---------------------");
//        ReflectionTestUtils.setField(couponSaveService, "materialCouponDao", materialCouponDao);
//        ReflectionTestUtils.setField(couponSaveService, "materialCouponCodeDao", materialCouponCodeDao);
        couponSaveService = new CouponSaveServiceImpl();
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentCalcServiceTest 结束---------------------");
    }
    
    /**
     * 正常Case
     */
    @Test
    public void test01() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                MaterialCoupon data = (MaterialCoupon) args[0];
                Assert.assertEquals("2", data.getSourceCode());
                return null;
            }
        }).when(materialCouponDao).insert(Mockito.any(MaterialCoupon.class));
        ReflectionTestUtils.setField(couponSaveService, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(couponSaveService, "materialCouponCodeDao", materialCouponCodeDao);
        ReflectionTestUtils.setField(couponSaveService, "mqTopicService", mqTopicService);
        MaterialCouponInfoIn in =new MaterialCouponInfoIn();
        in.setAmount(new BigDecimal(5));
        in.setRule("{\"type_code\":\"mixed\",\"length\":5}");
        in.setTitle("新MC优惠券");
        in.setSource_code("2");
        in.setStock_total(1);
        Date now = new Date();
        in.setStart_time(now);
        in.setEnd_time(now);
        in.setChannel_code("sms");
        couponSaveService.save(in);
    }

}