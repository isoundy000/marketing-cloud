/*************************************************
 * @功能简述: MaterialCouponStatusUpdateService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016-12-07
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.service.impl.MaterialCouponStatusUpdateServiceImpl;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponStatusUpdateVO;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponStatusUpdateServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MaterialCouponStatusUpdateService service;

    @Mock
    private MaterialCouponDao materialCouponDao;

    @Before
    public void setUp() throws Exception {
        service = new MaterialCouponStatusUpdateServiceImpl();
        logger.info("测试：MaterialCouponStatusUpdateService 开始---------------------");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponStatusUpdateService 结束---------------------");
    }

    /**
     * TaskName = null
     * 
     */
    @Test
    public void testUpdateMaterialCouponStatus01() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                MaterialCoupon data = (MaterialCoupon) args[0];
                Assert.assertEquals(6, data.getId().intValue());
                Assert.assertEquals("1", data.getCouponStatus());
                Assert.assertNull(data.getTaskId());
                Assert.assertNull(data.getTaskName());
                return null;
            }
        }).when(materialCouponDao).updateByIdAndStatus(Mockito.any(MaterialCoupon.class));
        List<MaterialCoupon> list = new ArrayList<MaterialCoupon>();
        MaterialCoupon dto = new MaterialCoupon();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 2);
        dto.setEndTime(date.getTime());
        list.add(dto);
        Mockito.when(materialCouponDao.selectList(Mockito.any())).thenReturn(list);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        MaterialCouponStatusUpdateVO vo = new MaterialCouponStatusUpdateVO();
        vo.setId(6L);
        vo.setStatus("1");
        service.updateMaterialCouponStatus(vo);
    }
    
    /**
     * status = null
     * 
     */
    @Test
    public void testUpdateMaterialCouponStatus01_01() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                MaterialCoupon data = (MaterialCoupon) args[0];
                Assert.assertEquals(6, data.getId().intValue());
                Assert.assertNull(data.getCouponStatus());
                Assert.assertNull(data.getTaskId());
                Assert.assertNull(data.getTaskName());
                return null;
            }
        }).when(materialCouponDao).updateByIdAndStatus(Mockito.any(MaterialCoupon.class));
        List<MaterialCoupon> list = new ArrayList<MaterialCoupon>();
        MaterialCoupon dto = new MaterialCoupon();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 2);
        dto.setEndTime(date.getTime());
        list.add(dto);
        Mockito.when(materialCouponDao.selectList(Mockito.any())).thenReturn(list);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        MaterialCouponStatusUpdateVO vo = new MaterialCouponStatusUpdateVO();
        vo.setId(6L);
        service.updateMaterialCouponStatus(vo);
    }


    /**
     * TaskName = ""
     * 
     */
    @Test
    public void testUpdateMaterialCouponStatus02() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                MaterialCoupon data = (MaterialCoupon) args[0];
                Assert.assertEquals(6, data.getId().intValue());
                Assert.assertEquals("1", data.getCouponStatus());
                Assert.assertNull(data.getTaskId());
                Assert.assertNull(data.getTaskName());
                return null;
            }
        }).when(materialCouponDao).updateByIdAndStatus(Mockito.any(MaterialCoupon.class));
        List<MaterialCoupon> list = new ArrayList<MaterialCoupon>();
        MaterialCoupon dto = new MaterialCoupon();
        list.add(dto);
        Mockito.when(materialCouponDao.selectList(Mockito.any())).thenReturn(list);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        MaterialCouponStatusUpdateVO vo = new MaterialCouponStatusUpdateVO();
        vo.setId(6L);
        vo.setStatus("1");
        vo.setTaskName("");
        service.updateMaterialCouponStatus(vo);
    }


    /**
     * 有TaskName/TaskId
     * 
     */
    @Test
    public void testUpdateMaterialCouponStatus03() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                MaterialCoupon data = (MaterialCoupon) args[0];
                Assert.assertEquals(6, data.getId().intValue());
                Assert.assertEquals("1", data.getCouponStatus());
                Assert.assertEquals(23, data.getTaskId().intValue());
                Assert.assertEquals("task1", data.getTaskName());
                return null;
            }
        }).when(materialCouponDao).updateByIdAndStatus(Mockito.any(MaterialCoupon.class));
        List<MaterialCoupon> list = new ArrayList<MaterialCoupon>();
        MaterialCoupon dto = new MaterialCoupon();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 2);
        dto.setEndTime(date.getTime());
        list.add(dto);
        Mockito.when(materialCouponDao.selectList(Mockito.any())).thenReturn(list);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        MaterialCouponStatusUpdateVO vo = new MaterialCouponStatusUpdateVO();
        vo.setId(6L);
        vo.setStatus("1");
        vo.setTaskId(23L);
        vo.setTaskName("task1");
        service.updateMaterialCouponStatus(vo);
    }

    /**
     * 参数异常
     * 
     */
    @Test
    public void testUpdateMaterialCouponStatus04() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponDao).updateByIdAndStatus(Mockito.any(MaterialCoupon.class));

        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        MaterialCouponStatusUpdateVO vo = new MaterialCouponStatusUpdateVO();
        vo.setStatus("1");
        vo.setTaskId(23L);
        vo.setTaskName("task1");
        try {
            service.updateMaterialCouponStatus(vo);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("param id can not be null", e.getMessage());
        }
    }

    /**
     * 数据不存在
     * 
     */
    @Test
    public void testUpdateMaterialCouponStatus05() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponDao).updateByIdAndStatus(Mockito.any(MaterialCoupon.class));
        List<MaterialCoupon> list = new ArrayList<MaterialCoupon>();
        Mockito.when(materialCouponDao.selectList(Mockito.any())).thenReturn(list);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        MaterialCouponStatusUpdateVO vo = new MaterialCouponStatusUpdateVO();
        vo.setId(9L);
        vo.setStatus("1");
        vo.setTaskId(23L);
        vo.setTaskName("task1");
        try {
            service.updateMaterialCouponStatus(vo);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("coupon id=[9] is not exist.", e.getMessage());
        }
    }

    /**
     * 数据过期
     * 
     */
    @Test
    public void testUpdateMaterialCouponStatus06() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponDao).updateByIdAndStatus(Mockito.any(MaterialCoupon.class));
        List<MaterialCoupon> list = new ArrayList<MaterialCoupon>();
        MaterialCoupon dto = new MaterialCoupon();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -2);
        dto.setEndTime(date.getTime());
        list.add(dto);
        Mockito.when(materialCouponDao.selectList(Mockito.any())).thenReturn(list);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        MaterialCouponStatusUpdateVO vo = new MaterialCouponStatusUpdateVO();
        vo.setId(9L);
        vo.setStatus("1");
        vo.setTaskId(23L);
        vo.setTaskName("task1");
        try {
            service.updateMaterialCouponStatus(vo);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("coupon id=[9] is expired.", e.getMessage());
        }
    }

}
