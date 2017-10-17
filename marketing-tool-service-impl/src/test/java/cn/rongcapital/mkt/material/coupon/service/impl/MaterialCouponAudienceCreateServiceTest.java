/*************************************************
 * @功能简述: MaterialCouponAudienceCreateService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import javax.jms.JMSException;

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

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponAudienceCreateService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCreateAudienceVO;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponAudienceCreateServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    MaterialCouponAudienceCreateService mcacService;

    @Mock
    private MQTopicService mqTopicService;

    @Before
    public void setUp() throws Exception {
        logger.info("测试：MaterialCouponVerifyGeneralService 准备---------------------");

        mcacService = new MaterialCouponAudienceCreateServiceImpl();

        ReflectionTestUtils.setField(mcacService, "mqTopicService", mqTopicService);
    }

    @Test
    public void testCreateTargetAudienceGroup01() throws JMSException {
        logger.info("测试方法: testCreateTargetAudienceGroup01 start");

        Mockito.doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(this.mqTopicService).senderMessage("", new ActiveMqMessageVO());
        
        BaseOutput result0 = mcacService.createTargetAudienceGroup(new MaterialCouponCreateAudienceVO());

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result0.getCode());
        logger.info("测试方法: testCreateTargetAudienceGroup01 end");
    }

    

    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponVerifyGeneralService 结束---------------------");
    }

}
