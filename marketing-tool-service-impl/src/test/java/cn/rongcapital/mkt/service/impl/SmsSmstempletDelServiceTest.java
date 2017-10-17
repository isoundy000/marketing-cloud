package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
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
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dao.SmsTempletMaterialMapDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsSmstempletDelService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsSmstempletDelIn;
@RunWith(MockitoJUnitRunner.class)
public class SmsSmstempletDelServiceTest {
    
    private SmsSmstempletDelService smsSmstempletDelService;
    
    @Mock
    private SmsMaterialDao smsMaterialDao;
    
    @Mock
    private SmsTempletDao smsTempletDao;
    
    @Mock
    private SmsTempletMaterialMapDao smsTempletMaterialMapDao;
    
    private List<SmsMaterial> smsMaterialLists;
    
    private List<SmsTemplet> smsTempletLists;
    
    private int delCount = 1;
    
    private SmsSmstempletDelIn body;
    
    @Before
    public void setUp() throws Exception {
        
        smsMaterialLists = new ArrayList<SmsMaterial>();
        
        smsTempletLists = new ArrayList<SmsTemplet>();
        
        SmsTemplet smsTemplet =  new SmsTemplet();
        
        smsTemplet.setType((byte) 1);
        smsTempletLists.add(smsTemplet);
        
        body = new SmsSmstempletDelIn();
        body.setId(1);
        
        smsSmstempletDelService = new SmsSmstempletDelServiceImpl();
        
        Mockito.when(smsMaterialDao.selectList(any())).thenReturn(smsMaterialLists);
        Mockito.when(smsTempletDao.selectList(any())).thenReturn(smsTempletLists);
        Mockito.when(smsTempletDao.updateById(any())).thenReturn(delCount);
        

    	Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				return null;
			}
		}).when(smsTempletMaterialMapDao).deleteByTempletId(any());
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smsSmstempletDelService, "smsMaterialDao", smsMaterialDao);
        ReflectionTestUtils.setField(smsSmstempletDelService, "smsTempletDao", smsTempletDao);
        ReflectionTestUtils.setField(smsSmstempletDelService, "smsTempletMaterialMapDao", smsTempletMaterialMapDao);
    }
    
    
    @Test
    public void testDelSmsTemple() throws Exception {
        BaseOutput result = smsSmstempletDelService.delSmsTemple(body, null);
        // 测试正常情况
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), result.getMsg());
        
        // 测试数据不存在
        smsTempletLists.clear();
        result = smsSmstempletDelService.delSmsTemple(body, null);
        Assert.assertEquals(2, result.getCode());
        Assert.assertEquals("数据不存在", result.getMsg());
        
        // 测试数据使用中
        smsMaterialLists.add(new SmsMaterial());
        result = smsSmstempletDelService.delSmsTemple(body, null);
        Assert.assertEquals(1, result.getCode());
        Assert.assertEquals("当前内容被使用中 不能删除请稍后再试", result.getMsg());
        
        
    }

    @After
    public void tearDown() throws Exception {}

}
