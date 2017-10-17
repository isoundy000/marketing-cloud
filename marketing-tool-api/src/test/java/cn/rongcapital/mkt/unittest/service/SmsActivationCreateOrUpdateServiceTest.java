package cn.rongcapital.mkt.unittest.service;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsActivationCreateOrUpdateService;
import cn.rongcapital.mkt.service.impl.SmsActivationCreateOrUpdateServiceImpl;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.jms.JMSException;

/**
 * Created by byf on 10/20/16.
 */
@RunWith(MockitoJUnitRunner.class)

public class SmsActivationCreateOrUpdateServiceTest {

    private SmsActivationCreateOrUpdateService smsActivationCreateOrUpdateService;

    @Mock
    private SmsTaskHeadDao smsTaskHeadDao;

    @Mock
    private SmsTaskBodyDao smsTaskBodyDao;

    @Mock
    private MQTopicService mqTopicService;

    @Before
    public void setUp() throws Exception {
        smsActivationCreateOrUpdateService = new SmsActivationCreateOrUpdateServiceImpl();
        ReflectionTestUtils.setField(smsActivationCreateOrUpdateService,"smsTaskHeadDao",smsTaskHeadDao);
        ReflectionTestUtils.setField(smsActivationCreateOrUpdateService,"smsTaskBodyDao",smsTaskBodyDao);
        ReflectionTestUtils.setField(smsActivationCreateOrUpdateService,"mqTopicService",mqTopicService);
    }

    @Test
    public void testWeChatQrocdeActivate() throws JMSException {
        //定义service方法参数
        SmsActivationCreateIn smsActivationCreateIn = new SmsActivationCreateIn();
        smsActivationCreateIn.setTaskName("短信活动单元测试");
        smsActivationCreateIn.setTaskSignatureId(Long.valueOf(1));
        smsActivationCreateIn.setTaskMaterialId(Long.valueOf(1));
        smsActivationCreateIn.setTaskMaterialContent("单元测试发送短信");
        smsActivationCreateIn.setTaskSendType(0);

        //执行待测的service方法
        BaseOutput baseOutPut=smsActivationCreateOrUpdateService.createOrUpdateSmsActivation(smsActivationCreateIn);

        //获取service方法执行结果
        int succ=baseOutPut.getCode();

        //断言
        Assert.assertEquals(succ, ApiErrorCode.SUCCESS.getCode());


    }
}
