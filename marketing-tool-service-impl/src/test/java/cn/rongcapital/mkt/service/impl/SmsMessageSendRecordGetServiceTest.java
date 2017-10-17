/**
 * @author shuiyangyang
 * @Date 2016.10.18
 */
package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.service.SmsMessageSendRecordGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.MessageSendRecordGetOut;

/**
 * @author shuiyangyang
 * @Date 2016.10.18
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsMessageSendRecordGetServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private SmsMessageSendRecordGetService smsMessageSendRecordGetService;

    @Mock
    private SmsTaskDetailDao smsTaskDetailDao;

    private List<MessageSendRecordGetOut> messageSendRecordGetOutLists;
    
    private int totalCount = 10;

    @Before
    public void setUp() throws Exception {

        messageSendRecordGetOutLists = new ArrayList<MessageSendRecordGetOut>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 10, 19, 14, 23);
        Date tiem = calendar.getTime();
        MessageSendRecordGetOut messageSendRecordGetOut = new MessageSendRecordGetOut((long) 12, (long) 12,
                "13123456789", "Hello World!", "134563323145", tiem, (Integer) 0);
        messageSendRecordGetOutLists.add(messageSendRecordGetOut);

        Mockito.when(smsTaskDetailDao.messageSendRecordGet(any())).thenReturn(messageSendRecordGetOutLists);
        Mockito.when(smsTaskDetailDao.selectListCount(any())).thenReturn(totalCount);
        smsMessageSendRecordGetService = new SmsMessageSendRecordGetServiceImpl();

        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smsMessageSendRecordGetService, "smsTaskDetailDao", smsTaskDetailDao);
    }

    @Test
    public void testmessageSendRecordGet() {

        BaseOutput result = smsMessageSendRecordGetService.messageSendRecordGet((long) 0, "", 1, 10);

        // 断言
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(messageSendRecordGetOutLists.size(), result.getTotal());
        Assert.assertEquals(totalCount, result.getTotalCount());
        Assert.assertEquals(messageSendRecordGetOutLists, result.getData());
    }


    @After
    public void tearDown() throws Exception {}

}
