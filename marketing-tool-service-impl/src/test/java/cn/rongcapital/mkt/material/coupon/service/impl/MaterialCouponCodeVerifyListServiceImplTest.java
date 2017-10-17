/*************************************************
 * @功能简述: MaterialCouponCodeVerifyListService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016-12-07
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeVerifyListService;
import cn.rongcapital.mkt.material.coupon.service.impl.MaterialCouponCodeVerifyListServiceImpl;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponCodeVerifyListOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponCodeVerifyListServiceImplTest {

    private MaterialCouponCodeVerifyListService service;

    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Before
    public void setUp() throws Exception {
        service = new MaterialCouponCodeVerifyListServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    /**
     * 无结果
     */
    @Test
    public void test01() {
        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Map<String, Object> param = (Map<String, Object>) args[0];
                Assert.assertEquals(Long.valueOf("1"), param.get("id"));
                Assert.assertEquals("long", param.get("user"));
                Assert.assertEquals("received", param.get("releaseStatus"));
                Assert.assertEquals("verified", param.get("verifyStatus"));
                Assert.assertEquals("expired", param.get("expireStatus"));
                Assert.assertNotNull(param.get("expireTime"));
                Assert.assertEquals(Integer.valueOf(10), param.get("index"));
                Assert.assertEquals(Integer.valueOf(10), param.get("size"));
                return 0;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyListCnt(Mockito.any());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyList(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        BaseOutput output = service.listMaterialCouponCodeVerfy(1L, "long", "received", "verified", "expired", 2, 10);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
        Assert.assertEquals(0, output.getData().size());
    }
    
    /**
     * 页码为空
     */
    @Test
    public void test01_01() {
        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Map<String, Object> param = (Map<String, Object>) args[0];
                Assert.assertEquals(Integer.valueOf(0), param.get("index"));
                Assert.assertEquals(Integer.valueOf(10), param.get("size"));
                return 0;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyListCnt(Mockito.any());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyList(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        BaseOutput output =
                service.listMaterialCouponCodeVerfy(1L, "long", "received", "verified", "expired", null, null);
    }

    /**
     * 页码为0
     */
    @Test
    public void test01_02() {
        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Map<String, Object> param = (Map<String, Object>) args[0];
                Assert.assertEquals(Integer.valueOf(0), param.get("index"));
                Assert.assertEquals(Integer.valueOf(10), param.get("size"));
                return 0;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyListCnt(Mockito.any());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyList(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        BaseOutput output = service.listMaterialCouponCodeVerfy(1L, "long", "received", "verified", "expired", 0, 0);
    }

    
    /**
     * 页码数据为空
     */
    @Test
    public void test01_03() {
        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Map<String, Object> param = (Map<String, Object>) args[0];
                Assert.assertEquals(Long.valueOf("1"), param.get("id"));
                Assert.assertEquals("long", param.get("user"));
                Assert.assertEquals("received", param.get("releaseStatus"));
                Assert.assertEquals("verified", param.get("verifyStatus"));
                Assert.assertEquals("expired", param.get("expireStatus"));
                Assert.assertNotNull(param.get("expireTime"));
                Assert.assertEquals(Integer.valueOf(10), param.get("index"));
                Assert.assertEquals(Integer.valueOf(10), param.get("size"));
                return 2;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyListCnt(Mockito.any());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyList(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        BaseOutput output = service.listMaterialCouponCodeVerfy(1L, "long", "received", "verified", "expired", 2, 10);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(2, output.getTotalCount());
        Assert.assertEquals(0, output.getData().size());
    }
    
    /**
     * 有结果
     */
    @Test
    public void test02() {
        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Map<String, Object> param = (Map<String, Object>) args[0];
                Assert.assertEquals(Long.valueOf("1"), param.get("id"));
                Assert.assertEquals("long", param.get("user"));
                Assert.assertEquals("received", param.get("releaseStatus"));
                Assert.assertEquals("verified", param.get("verifyStatus"));
                Assert.assertNull(param.get("expireStatus"));
                Assert.assertNull(param.get("expireTime"));
                Assert.assertEquals(Integer.valueOf(10), param.get("index"));
                Assert.assertEquals(Integer.valueOf(10), param.get("size"));
                return 20;
            }
        }).when(materialCouponCodeDao).getCouponCodeVerifyListCnt(Mockito.any());


        Date value = new Date();
        value.setTime(1234566L);
        List<MaterialCouponCodeVerifyListOut> mockData = new ArrayList<MaterialCouponCodeVerifyListOut>();
        MaterialCouponCodeVerifyListOut mockDataItem = new MaterialCouponCodeVerifyListOut();
        mockDataItem.setId(3L);
        mockDataItem.setCode("1");
        mockDataItem.setUser("12");
        mockDataItem.setAmount(BigDecimal.valueOf(121));
        mockDataItem.setStatus("verified");
        mockDataItem.setVerifyTimeDate(value);
        mockDataItem.setChannelCode("sms");
        mockData.add(mockDataItem);

        mockDataItem = new MaterialCouponCodeVerifyListOut();
        mockDataItem.setId(4L);
        mockDataItem.setCode("21");
        mockDataItem.setUser("22");
        mockDataItem.setAmount(BigDecimal.valueOf(221));
        mockDataItem.setStatus("verified");
        mockDataItem.setVerifyTimeDate(value);
        mockDataItem.setChannelCode("wechat");
        mockData.add(mockDataItem);
        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyList(Mockito.anyMap())).thenReturn(mockData);
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        BaseOutput output = service.listMaterialCouponCodeVerfy(1L, "long", "received", "verified", "", 2, 10);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(2, output.getTotal());
        Assert.assertEquals(20, output.getTotalCount());
        Assert.assertNotNull(output.getData());
        Assert.assertEquals(2, output.getData().size());


        MaterialCouponCodeVerifyListOut dataConvert1 = (MaterialCouponCodeVerifyListOut) output.getData().get(0);
        Assert.assertEquals(3, dataConvert1.getId().intValue());
        Assert.assertEquals("1", dataConvert1.getCode());
        Assert.assertEquals("12", dataConvert1.getUser());
        Assert.assertEquals(BigDecimal.valueOf(121), dataConvert1.getAmount());
        Assert.assertEquals("verified", dataConvert1.getStatus());
        Assert.assertEquals(1234566, dataConvert1.getVerifyTime().intValue());
        Assert.assertEquals("sms", dataConvert1.getChannelCode());

        dataConvert1 = (MaterialCouponCodeVerifyListOut) output.getData().get(1);
        Assert.assertEquals(4, dataConvert1.getId().intValue());
        Assert.assertEquals("21", dataConvert1.getCode());
        Assert.assertEquals("22", dataConvert1.getUser());
        Assert.assertEquals(BigDecimal.valueOf(221), dataConvert1.getAmount());
        Assert.assertEquals("verified", dataConvert1.getStatus());
        Assert.assertEquals(1234566, dataConvert1.getVerifyTime().intValue());
        Assert.assertEquals("wechat", dataConvert1.getChannelCode());
    }

}
