/**
 * 
 */
package cn.rongcapital.mkt.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.po.SmsTaskDetailState;
import cn.rongcapital.mkt.vo.out.MessageSendRecordGetOut;

/**
 * @author shuiyangyang
 * @Date 2016.10.18
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SmsTaskDetailDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsTaskDetailDao smsTaskDetailDao;

    @Autowired
    private SmsTaskDetailStateDao smsTaskDetailStateDao;

    SmsTaskDetail smsTaskDetailFirst;
    SmsTaskDetail smsTaskDetailSecond;
    SmsTaskDetailState smsTaskDetailStateFirst;
    SmsTaskDetailState smsTaskDetailStateSecond;

    @Before
    public void setUp() throws Exception {
        // 设置参数
        long smsTaskHeadId = (long) 1000;
        Byte status = 0;

        String receiveMobileFirst = "13123456789";
        String sendMessageFirst = "Hello World!";
        String sendMobileFirst = "1000000342423";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 10, 19, 5, 23);
        Date sendTimeFirst = calendar.getTime();
        int smsTaskSendStatusFirst = 0;

        String receiveMobileSecond = "13123456790";
        String sendMessageSecond = "Hello";
        String sendMobileSecond = "1000000342423";
        calendar.add(Calendar.SECOND, 20);
        Date sendTimeSecond = calendar.getTime();
        int smsTaskSendStatusSecond = 1;
        // 设置对象
        smsTaskDetailFirst = new SmsTaskDetail((long) -1, smsTaskHeadId, receiveMobileFirst, sendMessageFirst, status,
                sendMobileFirst, sendTimeFirst);
        smsTaskDetailSecond = new SmsTaskDetail((long) -1, smsTaskHeadId, receiveMobileSecond, sendMessageSecond,
                status, sendMobileSecond, sendTimeSecond);

        smsTaskDetailDao.insert(smsTaskDetailFirst);
        smsTaskDetailDao.insert(smsTaskDetailSecond);


        smsTaskDetailStateFirst = new SmsTaskDetailState(smsTaskDetailFirst.getId(), smsTaskSendStatusFirst, status);
        smsTaskDetailStateSecond = new SmsTaskDetailState(smsTaskDetailSecond.getId(), smsTaskSendStatusSecond, status);
        // 写到数据库
        smsTaskDetailStateDao.insert(smsTaskDetailStateFirst);
        smsTaskDetailStateDao.insert(smsTaskDetailStateSecond);

    }

    @Test
    public void testMessageSendRecordGet() {

        // 设置返回list
        List<MessageSendRecordGetOut> messageSendRecordGetOutLists = new ArrayList<MessageSendRecordGetOut>();

        // 设置预测返回结果
        MessageSendRecordGetOut messageSendRecordGetOutTestFirst =
                new MessageSendRecordGetOut(smsTaskDetailFirst.getId(), smsTaskDetailFirst.getSmsTaskHeadId(),
                        smsTaskDetailFirst.getReceiveMobile(), smsTaskDetailFirst.getSendMessage(),
                        smsTaskDetailFirst.getSendMobile(), smsTaskDetailFirst.getSendTime(),
                        smsTaskDetailStateFirst.getSmsTaskSendStatus());
        MessageSendRecordGetOut messageSendRecordGetOutTestSecond =
                new MessageSendRecordGetOut(smsTaskDetailSecond.getId(), smsTaskDetailSecond.getSmsTaskHeadId(),
                        smsTaskDetailSecond.getReceiveMobile(), smsTaskDetailSecond.getSendMessage(),
                        smsTaskDetailSecond.getSendMobile(), smsTaskDetailSecond.getSendTime(),
                        smsTaskDetailStateSecond.getSmsTaskSendStatus());

        // 测试没有receiveMobile
        SmsTaskDetail smsTaskDetailTest = new SmsTaskDetail();
        smsTaskDetailTest.setSmsTaskHeadId(smsTaskDetailFirst.getSmsTaskHeadId());
        smsTaskDetailTest.setStatus(smsTaskDetailFirst.getStatus());
        messageSendRecordGetOutLists = smsTaskDetailDao.messageSendRecordGet(smsTaskDetailTest);

        // 断言
        Assert.assertEquals(2, messageSendRecordGetOutLists.size());
        Assert.assertEquals(messageSendRecordGetOutTestFirst, messageSendRecordGetOutLists.get(0));
        Assert.assertEquals(messageSendRecordGetOutTestSecond, messageSendRecordGetOutLists.get(1));



        // 测试带有receiveMobile
        smsTaskDetailTest.setReceiveMobile(smsTaskDetailFirst.getReceiveMobile());
        messageSendRecordGetOutLists = smsTaskDetailDao.messageSendRecordGet(smsTaskDetailTest);

        // 断言
        Assert.assertEquals(1, messageSendRecordGetOutLists.size());
        Assert.assertEquals(messageSendRecordGetOutTestFirst, messageSendRecordGetOutLists.get(0));
    }

    @After
    public void tearDown() throws Exception {
        // 逻辑删除数据
        Byte statusDel = 1;
        smsTaskDetailFirst.setStatus(statusDel);
        smsTaskDetailSecond.setStatus(statusDel);
        smsTaskDetailStateFirst.setStatus(statusDel);
        smsTaskDetailStateSecond.setStatus(statusDel);
        smsTaskDetailDao.updateById(smsTaskDetailFirst);
        smsTaskDetailDao.updateById(smsTaskDetailSecond);
        smsTaskDetailStateDao.updateById(smsTaskDetailStateFirst);
        smsTaskDetailStateDao.updateById(smsTaskDetailStateSecond);
    }

}
