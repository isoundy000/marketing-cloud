/*************************************************
 * @功能简述: MaterialCouponReleaseGeneralService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author:
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/

package cn.rongcapital.mkt.material.coupon.service.impl;

import static org.mockito.Matchers.any;

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
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MeterialCouponCodeCountByStatus;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponReleaseGeneralService;
import cn.rongcapital.mkt.vo.BaseOutput;


@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponReleaseGeneralServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    MaterialCouponReleaseGeneralService materialCouponReleaseGeneralService;

    @Mock
    private MaterialCouponDao materialCouponDao;

    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;

    private List<Object> data = new ArrayList<Object>();
    private List<MeterialCouponCodeCountByStatus> reValue = new ArrayList<MeterialCouponCodeCountByStatus>();
    private List<MeterialCouponCodeCountByStatus> veValue = new ArrayList<MeterialCouponCodeCountByStatus>();

    @Before
    public void setUp() throws Exception {
        logger.info("测试：MaterialCouponReleaseGeneralService 准备---------------------");

        materialCouponReleaseGeneralService = new MaterialCouponReleaseGeneralServiceImpl();

        MeterialCouponCodeCountByStatus receivedVer = new MeterialCouponCodeCountByStatus(12l, "received", 6l);
        MeterialCouponCodeCountByStatus reunreceived = new MeterialCouponCodeCountByStatus(12l, "unreceived", 2l);
        reValue.add(receivedVer);
        reValue.add(reunreceived);
        MeterialCouponCodeCountByStatus ververified = new MeterialCouponCodeCountByStatus(12l, "verified", 3l);
        veValue.add(ververified);

        // 把mock的dao set进入service
        ReflectionTestUtils.setField(materialCouponReleaseGeneralService, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(materialCouponReleaseGeneralService, "materialCouponCodeDao",
                materialCouponCodeDao);

        Map<String, Object> map = new HashMap<>();
        map.put("expect_release", 20000l);
        map.put("actual_release", 8l);
        map.put("actual_reached", 6l);
        map.put("actual_verify", 3l);
        data.add(map);
    }

    @Test
    public void testReleaseGeneralById() {
        logger.info("测试方法: releaseGeneralById start");

        Mockito.when(materialCouponDao.selectStockTotalByCouponId(any())).thenReturn(20000l);
        Mockito.when(materialCouponCodeDao.selectCouponTotalByCouponIdAndReleStatus(any())).thenReturn(reValue);
        Mockito.when(materialCouponCodeDao.selectCouponTotalByCouponIdAndVeriStatus(any())).thenReturn(veValue);

        BaseOutput result0 = materialCouponReleaseGeneralService.releaseGeneralById(12l, "", "");

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result0.getCode());
        Map map0 = (Map) data.get(0);
        Map resultmap0 = (Map) result0.getData().get(0);
        Assert.assertEquals(map0.get("expect_release"), resultmap0.get("expect_release"));
        Assert.assertEquals(map0.get("actual_release"), resultmap0.get("actual_release"));
        Assert.assertEquals(map0.get("actual_reached"), resultmap0.get("actual_reached"));
        Assert.assertEquals(map0.get("actual_verify"), resultmap0.get("actual_verify"));
        Assert.assertEquals(result0.getData().size(), 1);

        logger.info("测试方法: releaseGeneralById end");
    }

    @Test
    public void testReleaseGeneralById02() {
        logger.info("测试方法: releaseGeneralById start");

        Mockito.when(materialCouponDao.selectStockTotalByCouponId(any())).thenReturn(null);
        Mockito.when(materialCouponCodeDao.selectCouponTotalByCouponIdAndReleStatus(any())).thenReturn(null);
        Mockito.when(materialCouponCodeDao.selectCouponTotalByCouponIdAndVeriStatus(any())).thenReturn(null);

        BaseOutput result0 = materialCouponReleaseGeneralService.releaseGeneralById(12l, "", "");

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result0.getCode());
        Map map0 = (Map) data.get(0);
        Map resultmap0 = (Map) result0.getData().get(0);
        Assert.assertEquals(0l, resultmap0.get("expect_release"));
        Assert.assertEquals(0l, resultmap0.get("actual_release"));
        Assert.assertEquals(0l, resultmap0.get("actual_reached"));
        Assert.assertEquals(0l, resultmap0.get("actual_verify"));
        Assert.assertEquals(result0.getData().size(), 1);

        logger.info("测试方法: releaseGeneralById end");
    }
    
    @Test
    public void testReleaseGeneralById03() {
        logger.info("测试方法: releaseGeneralById start");

        List<MeterialCouponCodeCountByStatus> list = new ArrayList<MeterialCouponCodeCountByStatus>();
        MeterialCouponCodeCountByStatus temp= new MeterialCouponCodeCountByStatus(null, null, null);
        list.add(temp);
        
        Mockito.when(materialCouponDao.selectStockTotalByCouponId(any())).thenReturn(null);
        Mockito.when(materialCouponCodeDao.selectCouponTotalByCouponIdAndReleStatus(any())).thenReturn(list);
        Mockito.when(materialCouponCodeDao.selectCouponTotalByCouponIdAndVeriStatus(any())).thenReturn(list);

        BaseOutput result0 = materialCouponReleaseGeneralService.releaseGeneralById(12l, "", "");

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result0.getCode());
        Map map0 = (Map) data.get(0);
        Map resultmap0 = (Map) result0.getData().get(0);
        Assert.assertEquals(0l, resultmap0.get("expect_release"));
        Assert.assertEquals(0l, resultmap0.get("actual_release"));
        Assert.assertEquals(0l, resultmap0.get("actual_reached"));
        Assert.assertEquals(0l, resultmap0.get("actual_verify"));
        Assert.assertEquals(result0.getData().size(), 1);

        logger.info("测试方法: releaseGeneralById end");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponReleaseGeneralService 结束---------------------");
    }

}
