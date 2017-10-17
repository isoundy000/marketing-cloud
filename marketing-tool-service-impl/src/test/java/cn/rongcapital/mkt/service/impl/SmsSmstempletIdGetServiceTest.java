package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dao.SmsTempletMaterialMapDao;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.po.SmsTempletMaterialMap;
import cn.rongcapital.mkt.service.SmsSmstempletIdGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsSmstempletIdGetOut;

@RunWith(MockitoJUnitRunner.class)
public class SmsSmstempletIdGetServiceTest {

    private SmsSmstempletIdGetService smsSmstempletIdGetService;

    @Mock
    private SmsTempletDao smsTempletDao;
    
    @Mock
    private SmsMaterialDao smsMaterialDao;

    @Mock
    private SmsTempletMaterialMapDao smsTempletMaterialMapDao;
    
    private SmsTemplet smsTemplet;

    private List<SmsTempletMaterialMap> smsTempletMaterialMapList;
    
    private List<SmsTemplet> smsTempletList;
    private int materialSize = 0;
    
    @Before
    public void setUp() throws Exception {
//    	//固定模板
//        smsTemplet = new SmsTemplet(Integer.valueOf(1), "", (byte) 0, (byte) 0, (byte) 1, "sms",
//                        (byte) 0, "sms", null, "user1", null, "无", "审核", new Date(),
//                        "【融数金服】大伟牌模板，专注短信模板20年，质量信得过！");
//    	materialSize = 0;
//    	
//        smsTempletList = new ArrayList<SmsTemplet>();
//        smsTempletList.add(smsTemplet);
    	
    	//变量模板
        smsTemplet = new SmsTemplet(Integer.valueOf(11), "", (byte) 0, (byte) 1, (byte) 1, "sms",
                (byte) 0, "sms", null, "user1", null, "无", "审核", new Date(),
                "【融数金服】大伟牌模板，专注短信模板20年，质量信得过！");
        
        smsTempletList = new ArrayList<SmsTemplet>();
        smsTempletList.add(smsTemplet);

        smsTempletMaterialMapList = new ArrayList<SmsTempletMaterialMap>();
        
        SmsTempletMaterialMap smsTempletMaterialMap1= new SmsTempletMaterialMap();
        
        smsTempletMaterialMap1.setId(100l);
        smsTempletMaterialMap1.setMaterialId(2);
        smsTempletMaterialMap1.setSmsTempletId(11l);
        smsTempletMaterialMap1.setMaterialName("优惠券");
        smsTempletMaterialMap1.setMaterialType("coupon");
        smsTempletMaterialMap1.setMaterialPropertyCode("start_time");
        smsTempletMaterialMap1.setMaterialPropertyName("开始时间");
        smsTempletMaterialMap1.setMaterialPropertyId(10);

        smsTempletMaterialMapList.add(smsTempletMaterialMap1);
        
        SmsTempletMaterialMap smsTempletMaterialMap2= new SmsTempletMaterialMap();
        
        smsTempletMaterialMap2.setId(101l);
        smsTempletMaterialMap2.setSmsTempletId(11l);
        smsTempletMaterialMap2.setMaterialId(2);
        smsTempletMaterialMap2.setMaterialName("优惠券");
        smsTempletMaterialMap2.setMaterialType("coupon");
        smsTempletMaterialMap2.setMaterialPropertyCode("end_time");
        smsTempletMaterialMap2.setMaterialPropertyName("结束时间");
        smsTempletMaterialMap2.setMaterialPropertyId(11);

        smsTempletMaterialMapList.add(smsTempletMaterialMap1);
        materialSize = 2;
        
        
        smsSmstempletIdGetService = new SmsSmstempletIdGetServiceImpl();

        Mockito.when(smsTempletDao.selectList(any())).thenReturn(smsTempletList);
        Mockito.when(smsMaterialDao.selectList(any())).thenReturn(null);
        Mockito.when(smsTempletMaterialMapDao.selectList(any())).thenReturn(smsTempletMaterialMapList);
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smsSmstempletIdGetService, "smsTempletDao", smsTempletDao);
        ReflectionTestUtils.setField(smsSmstempletIdGetService, "smsMaterialDao", smsMaterialDao);
        ReflectionTestUtils.setField(smsSmstempletIdGetService, "smsTempletMaterialMapDao", smsTempletMaterialMapDao);
    }

    /**
     * 接口：mkt.sms.smstemplet.id.get 实习方法测试
     * 
     * @param id
     * @return
     * @Date 2016-11-11
     * @author shuiyangyang
     */
    @Test
    public void testGetSmsSmstempletById() {
        BaseOutput result = smsSmstempletIdGetService.getSmsSmstempletById(smsTemplet.getId());

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(1, result.getTotal());

        SmsSmstempletIdGetOut smsSmstempletIdGetOut = new SmsSmstempletIdGetOut(smsTemplet.getId(),
                        smsTemplet.getChannelType(), smsTemplet.getType(),
                        smsTemplet.getAuditStatus(), smsTemplet.getName(),
                        smsTemplet.getAuditReason(), null, smsTemplet.getContent(), true, true);
        smsSmstempletIdGetOut.setAuditTime(DateUtil.getStringFromDate(smsTemplet.getAuditTime(),
                        "yyyy-MM-dd HH:mm:ss"));
        result.getData().add(smsSmstempletIdGetOut);

        Assert.assertEquals(smsSmstempletIdGetOut, result.getData().get(0));
        
        SmsSmstempletIdGetOut smsSmstempletIdGet = (SmsSmstempletIdGetOut)result.getData().get(0);
        
        Assert.assertEquals(materialSize,smsSmstempletIdGet.getMaterialList().size());
        
        
    }

    @After
    public void tearDown() throws Exception {}

}
