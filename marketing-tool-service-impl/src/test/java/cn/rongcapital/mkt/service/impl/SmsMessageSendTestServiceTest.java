package cn.rongcapital.mkt.service.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.SmsMessageSendTestService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsMessageSendTestIn;

@RunWith(MockitoJUnitRunner.class)
public class SmsMessageSendTestServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SmsMessageSendTestService smsMessageSendTestService;
    
    SmsMessageSendTestIn body;
    
    @Before
    public void setUp() throws Exception {
        smsMessageSendTestService = new SmsMessageSendTestServiceImpl();
        body = new SmsMessageSendTestIn();
        body.setReceiveMobiles("13123456789,13123456789,13123456789");
        body.setSendMessage("Hello World!");
    }
    
    @Test
    public void testMessageSendTest() {
        BaseOutput result = smsMessageSendTestService.messageSendTest(body, null);
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), result.getMsg());
    }
    
    
    @After
    public void tearDown() throws Exception {}
}
