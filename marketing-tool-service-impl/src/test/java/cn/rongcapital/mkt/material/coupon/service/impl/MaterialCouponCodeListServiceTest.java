/*************************************************
 * @功能简述: Service接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: guozhenchao
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeListService;
import cn.rongcapital.mkt.material.coupon.service.impl.CouponCodeListServiceImpl;
import cn.rongcapital.mkt.service.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponCodeListServiceTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private CouponCodeListService couponCodeListService;

    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Before
    public void setUp() throws Exception {
        logger.info("测试: SegmentCalcServiceTest 开始---------------------");
        couponCodeListService = new CouponCodeListServiceImpl();
        ReflectionTestUtils.setField(couponCodeListService, "materialCouponCodeDao", materialCouponCodeDao);
    }

    @Test
    public void testCouponCodeList() {
        logger.info("测试方法: couponCodeList ");
        List<MaterialCouponCode> resultList = new ArrayList<>();
        MaterialCouponCode code = new MaterialCouponCode();
        code.setCouponId(12L);
        Mockito.when(materialCouponCodeDao.selectList(Mockito.argThat(new MaterialCouponCodeListServiceTest.MaterialCouponCodeMatcherMockOne(code)))).thenReturn(resultList);
        BaseOutput baseOutput = couponCodeListService.couponCodeList(12L, 0, 10);
        Assert.assertEquals(baseOutput.getCode(), ApiErrorCode.SUCCESS.getCode());
    }

    class MaterialCouponCodeMatcherMockOne extends ArgumentMatcher<MaterialCouponCode> {

        MaterialCouponCode materialCouponCode = null;

        public MaterialCouponCodeMatcherMockOne(MaterialCouponCode materialCouponCode) {
            this.materialCouponCode = materialCouponCode;
        }

        public boolean matches(Object obj) {
            Long couponId = Long.valueOf(materialCouponCode.getCouponId());
            Long objId = Long.valueOf(((MaterialCouponCode) obj).getCouponId());
            if (couponId.longValue() == objId.longValue()) return true;
            return false;
        }
    }



}
