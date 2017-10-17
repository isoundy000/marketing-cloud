package cn.rongcapital.mkt.service.impl;

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
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.service.SmstempletCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
@RunWith(MockitoJUnitRunner.class)
public class SmstempletCountGetServiceTest {
    
    private SmstempletCountGetService smstempletCountGetService;

    @Mock
    private SmsTempletDao smsTempletDao;
    
    private List<Map<String,Object>> mapList;
    
    @Before
    public void setUp() throws Exception {
        
    	mapList = new ArrayList<Map<String,Object>>();
    	
    	Map<String,Object> map1 = new HashMap<String,Object>();
    	map1.put("0", 20);
    	
    	Map<String,Object> map2 = new HashMap<String,Object>();
    	map2.put("1", 60);
    	
    	mapList.add(map1);
    	mapList.add(map2);
    	
    	smstempletCountGetService = new SmstempletCountGetServiceImpl();
        Mockito.when(smsTempletDao.getTempletCountByType(any())).thenReturn(mapList);
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smstempletCountGetService, "smsTempletDao", smsTempletDao);
    }
    
    
    @Test
    public void testGetSmsTempletCount() throws Exception {
        BaseOutput result = smstempletCountGetService.getSmsTempletCount("1");
        
        // 测试正常情况
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), result.getMsg());
        
        Assert.assertEquals(2, result.getData().size());
        
    }

    @After
    public void tearDown() throws Exception {}

}
