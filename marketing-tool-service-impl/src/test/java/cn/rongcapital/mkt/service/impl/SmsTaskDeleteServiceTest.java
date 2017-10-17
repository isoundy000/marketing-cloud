/**
 * 
 */
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
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.SmsTaskDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsTaskDeleteIn;

/**
 * @author shuiyangyang
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsTaskDeleteServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    SmsTaskDeleteService smsTaskDeleteService;
    
    @Mock
    private SmsTaskHeadDao smsTaskHeadDao;
    
    @Mock
    private SmsTaskBodyDao smsTaskBodyDao;
    
    private List<SmsTaskHead> smsTaskHeadLists;
    
    private List<SmsTaskBody> smsTaskBodyLists;
    
    SmsTaskHead smsTaskHead;
    
    @Before
    public void setUp() throws Exception {
        
        smsTaskHeadLists = new ArrayList<SmsTaskHead>();
        smsTaskHead = new SmsTaskHead();
        smsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        smsTaskHead.setSmsTaskStatus(0);
        smsTaskHeadLists.add(smsTaskHead);

        smsTaskBodyLists = new ArrayList<SmsTaskBody>();
        smsTaskBodyLists.add(new SmsTaskBody());
        smsTaskBodyLists.add(new SmsTaskBody());

        Mockito.when(smsTaskHeadDao.updateById(any())).thenReturn(1);
        Mockito.when(smsTaskHeadDao.selectList(any())).thenReturn(smsTaskHeadLists);
        Mockito.when(smsTaskBodyDao.selectList(any())).thenReturn(smsTaskBodyLists);
        
        smsTaskDeleteService = new SmsTaskDeleteServiceImpl();

        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smsTaskDeleteService, "smsTaskHeadDao", smsTaskHeadDao);
        ReflectionTestUtils.setField(smsTaskDeleteService, "smsTaskBodyDao", smsTaskBodyDao);
    }

    @Test
    public void testmessageSendRecordGet() {

        // 测试成功
        BaseOutput result = smsTaskDeleteService.smsTaskDelete(new SmsTaskDeleteIn(), null);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), result.getMsg());
        
        // 测试任务执行中
        smsTaskHeadLists.get(0).setSmsTaskStatus(2);
        result = smsTaskDeleteService.smsTaskDelete(new SmsTaskDeleteIn(), null);
        Assert.assertEquals(1, result.getCode());
        Assert.assertEquals("任务执行中 不能删除", result.getMsg());
        
        // 测试重复删除
        smsTaskHeadLists.get(0).setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
        result = smsTaskDeleteService.smsTaskDelete(new SmsTaskDeleteIn(), null);
        Assert.assertEquals(2, result.getCode());
        Assert.assertEquals("重复删除", result.getMsg());
        
        // 测试数据在数据库中不存在
        smsTaskHeadLists.clear();
        result = smsTaskDeleteService.smsTaskDelete(new SmsTaskDeleteIn(), null);
        Assert.assertEquals(3, result.getCode());
        Assert.assertEquals("数据不存在", result.getMsg());
    }


    @After
    public void tearDown() throws Exception {}

}
