/*************************************************
 * @功能简述: MaterialCouponGeneralGetService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016-12-07
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponGeneralGetService;
import cn.rongcapital.mkt.material.coupon.service.impl.MaterialCouponGeneralGetServiceImpl;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponGeneralGetOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponGeneralGetServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());


    private MaterialCouponGeneralGetService service;

    @Mock
    private MaterialCouponDao materialCouponDao;

    @Before
    public void setUp() throws Exception {
        service = new MaterialCouponGeneralGetServiceImpl();

        logger.info("测试：MaterialCouponGeneralGetService 开始---------------------");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponGeneralGetService 结束---------------------");
    }

    /**
     * 数据不存在
     */
    @Test
    public void test01() {
        Mockito.when(materialCouponDao.selectList(Mockito.any(MaterialCoupon.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        BaseOutput output = service.getMaterialCouponGeneral(1L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isEmpty(output.getData()));
    }

    /**
     * 数据存在
     */
    @Test
    public void test02() {
        List<MaterialCoupon> couponList = new ArrayList<MaterialCoupon>();
        MaterialCoupon item = new MaterialCoupon();
        item.setId(2L);
        item.setTitle("123");
        item.setType("voucher");
        item.setSourceCode("common");
        item.setChannelCode("sms");
        item.setCouponStatus("unused");
        item.setTaskId(123L);
        item.setTaskName("33333");
        couponList.add(item);
        Mockito.when(materialCouponDao.selectList(Mockito.any(MaterialCoupon.class))).thenReturn(couponList);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        BaseOutput output = service.getMaterialCouponGeneral(1L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));
        Assert.assertEquals(output.getData().size(), 1);
        MaterialCouponGeneralGetOut expectOut = new MaterialCouponGeneralGetOut();
        BeanUtils.copyProperties(item, expectOut);
        Assert.assertEquals(expectOut.toString(), output.getData().get(0).toString());
    }

}
