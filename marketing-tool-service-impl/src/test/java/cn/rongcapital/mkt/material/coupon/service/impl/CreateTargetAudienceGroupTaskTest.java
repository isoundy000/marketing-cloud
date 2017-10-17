/*************************************************
 * @功能简述: CreateTargetAudienceGroupTask测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

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

import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCreateAudienceVO;
import cn.rongcapital.mkt.service.AudienceListService;

@RunWith(MockitoJUnitRunner.class)
public class CreateTargetAudienceGroupTaskTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private CreateTargetAudienceGroupTask ctagTask;

    @Mock
    MaterialCouponDao materialCouponDao;

    @Mock
    MaterialCouponCodeDao materialCouponCodeDao;

    @Mock
    AudienceListService audienceListService;

    List<String> mobileList = new ArrayList<>();

    List<MaterialCoupon> mc = new ArrayList<MaterialCoupon>();
    
    MaterialCouponCreateAudienceVO parmVO;
    
    String jsonString ="";
    
    @Before
    public void setUp() throws Exception {
        logger.info("测试：CreateTargetAudienceGroupTask 准备---------------------");

        ctagTask = new CreateTargetAudienceGroupTask();
        parmVO = new MaterialCouponCreateAudienceVO(282l,"test",null,null,null,null);
        jsonString = JSONObject.toJSONString(parmVO);
        
        String tempMobile = "";
        for (int a = 0; a < 6; a++) {
            tempMobile = "1860000000" + a;
            mobileList.add(tempMobile);
        }

        MaterialCoupon tempMc = new MaterialCoupon();
        tempMc.setTaskId(10l);
        mc.add(tempMc);

        ReflectionTestUtils.setField(ctagTask, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(ctagTask, "materialCouponCodeDao", materialCouponCodeDao);
        ReflectionTestUtils.setField(ctagTask, "audienceListService", audienceListService);
    }
    
    @Test
    public void testCreateTargetAudienceGroup01() throws JMSException {
        logger.info("测试方法: testCreateTargetAudienceGroup01 start");

        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(mobileList);
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(mc);
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(true);

        ctagTask.task(jsonString);
        
        logger.info("测试方法: testCreateTargetAudienceGroup01 end");
    }

    @Test
    public void testCreateTargetAudienceGroup02() throws JMSException {
        logger.info("测试方法: testCreateTargetAudienceGroup02 start");

        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(null);
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(mc);
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(false);

        ctagTask.task(jsonString);
        
        logger.info("测试方法: testCreateTargetAudienceGroup02 end");
    }

    @Test
    public void testCreateTargetAudienceGroup03() throws JMSException {
        logger.info("测试方法: testCreateTargetAudienceGroup03 start");

        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(new ArrayList<>());
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(null);
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(true);

        ctagTask.task(jsonString);
        
        logger.info("测试方法: testCreateTargetAudienceGroup03 end");
    }
    
    @Test
    public void testCreateTargetAudienceGroup04() throws JMSException {
        logger.info("测试方法: testCreateTargetAudienceGroup04 start");

        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(new ArrayList<>());
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(new ArrayList<MaterialCoupon>());
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(true);

        ctagTask.task(jsonString);
        
        logger.info("测试方法: testCreateTargetAudienceGroup04 end");
    }
    
    @Test
    public void testCreateTargetAudienceGroup05() throws JMSException {
        logger.info("测试方法: testCreateTargetAudienceGroup05 start");

        MaterialCoupon tempMc = new MaterialCoupon();
        mc.clear();
        mc.add(tempMc);
        
        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(new ArrayList<>());
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(mc);
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(true);

        parmVO = new MaterialCouponCreateAudienceVO(282l,"test","137","received","verified","expired");
        jsonString = JSONObject.toJSONString(parmVO);
        
        ctagTask.task(jsonString);
        
        logger.info("测试方法: testCreateTargetAudienceGroup05 end");
    }
    
    @Test
    public void testCreateTargetAudienceGroup06() throws JMSException {
        logger.info("测试方法: testCreateTargetAudienceGroup06 start");
        
        ctagTask.task(1);
        
        logger.info("测试方法: testCreateTargetAudienceGroup06 end");
    }


    @After
    public void tearDown() throws Exception {
        logger.info("测试：CreateTargetAudienceGroupTask 结束---------------------");
    }

}
