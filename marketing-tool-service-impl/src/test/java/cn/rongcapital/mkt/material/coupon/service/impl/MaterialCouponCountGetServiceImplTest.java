/*************************************************
 * @功能简述: MaterialCouponCountGetService实现测试类
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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCountGetService;
import cn.rongcapital.mkt.material.coupon.service.impl.MaterialCouponCountGetServiceImpl;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponCountGetServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());


    private MaterialCouponCountGetService service;

    @Mock
    private MaterialCouponDao materialCouponDao;

    @Before
    public void setUp() throws Exception {
        service = new MaterialCouponCountGetServiceImpl();

        logger.info("测试：MaterialCouponCountGetService 开始---------------------");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponCountGetService 结束---------------------");
    }



    @Test
    public void testGetMaterialCouponCount() {
        Mockito.when(materialCouponDao.getMaterialCouponCount(Mockito.anyMapOf(String.class, Object.class)))
                        .thenReturn(1L);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        BaseOutput output = service.getMaterialCouponCount("sms", "123");
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertEquals(1, output.getData().size());
        Map<String, Object> dataConvert = (Map<String, Object>) output.getData().get(0);
        Assert.assertEquals(1L, dataConvert.get("unrelease_count"));
        Assert.assertEquals(1L, dataConvert.get("occupy_count"));
        Assert.assertEquals(1L, dataConvert.get("releasing_count"));
        Assert.assertEquals(1L, dataConvert.get("released_count"));
        Assert.assertEquals(4L, dataConvert.get("total"));
    }

}
