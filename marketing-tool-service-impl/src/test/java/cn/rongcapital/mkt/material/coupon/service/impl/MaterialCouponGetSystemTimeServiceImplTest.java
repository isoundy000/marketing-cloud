/*************************************************
 * @功能简述: MaterialCouponGetSystemTimeService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016-12-07
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponGetSystemTimeService;
import cn.rongcapital.mkt.material.coupon.service.impl.MaterialCouponGetSystemTimeServiceImpl;
import cn.rongcapital.mkt.vo.BaseOutput;

public class MaterialCouponGetSystemTimeServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MaterialCouponGetSystemTimeService service;

    @Before
    public void setUp() throws Exception {
        service = new MaterialCouponGetSystemTimeServiceImpl();
        logger.info("测试：MaterialCouponGetSystemTimeService 开始---------------------");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponGetSystemTimeService 结束---------------------");
    }

    @Test
    public void test() {
        BaseOutput output = service.getSystemTime();
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertEquals(1, output.getData().size());
        Map<String, Object> dataConvert = (Map<String, Object>) output.getData().get(0);
        Assert.assertTrue(dataConvert.containsKey("time"));
        Assert.assertTrue(dataConvert.get("time") instanceof Number);
    }

}
