package cn.rongcapital.mkt.event.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.service.EventSubscribeService;
import cn.rongcapital.mkt.event.service.impl.EventSubscribeServiceImpl;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EventSubscribeServiceImpl Tester.
 *
 * @author xie.xiaoliang
 * @version 1.0
 * @since 2017-1-9
 */
@RunWith(MockitoJUnitRunner.class)
public class EventSubscribeServiceImplTest {
    @Mock
    private EventDao eventDao;

    private EventSubscribeService eventSubscribeService;

    private Event event;
    private Event eventP1 = new Event();
    private Event eventP2 = new Event();
    private List<Event> eventList = new ArrayList<>();
    private List<Event> eventList2 = new ArrayList<>();

    @Before
    public void before() throws Exception {
        //初始化数据
        eventSubscribeService = new EventSubscribeServiceImpl();
        //打桩埋点
        ReflectionTestUtils.setField(eventSubscribeService, "eventDao", eventDao);


        event = new Event();
        event.setCreateTime(new Date());
        event.setId(1l);
        event.setName("订阅测试");
        event.setSubscribed(true);
        event.setUnsubscribable(false);
        eventP1.setId(1l);
        eventList.add(event);

        Event event1 = new Event();
        event1.setCreateTime(new Date());
        event1.setId(2l);
        event1.setName("订阅测试");
        event1.setSubscribed(true);
        event1.setUnsubscribable(false);
        eventP2.setId(2l);
        eventList2.add(event1);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * 测试成功流程
     * Method: eventSubscribe(long eventId, boolean subscribe)
     */
    @Test
    public void testEventSubscribeSuccess() throws Exception {
       
        Mockito.when(eventDao.selectList(Matchers.any())).thenReturn(eventList);
        event.setSubscribed(false);
        Mockito.when(eventDao.updateById(event)).thenReturn(1);
        BaseOutput actual = this.eventSubscribeService.eventSubscribe(1l, false);
        BaseOutput expired = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                ApiErrorCode.SUCCESS.getMsg(), 1, null);
        Assert.assertEquals(expired.getCode(), actual.getCode());
    }
    /**
     * 测试不可取消订阅状态branch
     * Method: eventSubscribe(long eventId, boolean subscribe)
     */
    @Test
    public void testEventSubscribeUnsubscribable() throws Exception {
        Mockito.when(eventDao.selectList(Matchers.any())).thenReturn(eventList);
        event.setSubscribed(false);
        event.setUnsubscribable(true);
        Mockito.when(eventDao.updateById(event)).thenReturn(1);
        BaseOutput actual = this.eventSubscribeService.eventSubscribe(1l, false);
        BaseOutput expired = new BaseOutput(ApiErrorCode.VALIDATE_ERROR_EVENT_UNSUBSCRIBABLE.getCode(),
                ApiErrorCode.VALIDATE_ERROR_EVENT_UNSUBSCRIBABLE.getMsg(), 1, null);
        Assert.assertEquals(expired.getCode(), actual.getCode());
    }

    /**
     * 测试数据库中午此数据
     * Method: eventSubscribe(long eventId, boolean subscribe)
     */
    @Test
    public void testEventSubscribeNotFoundData() throws Exception {
        
        Mockito.when(eventDao.selectList(Matchers.any())).thenReturn(null);
        BaseOutput actual = this.eventSubscribeService.eventSubscribe(1l, false);
        BaseOutput expired = new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), 1, null);
        Assert.assertEquals(expired.getCode(), actual.getCode());

        Mockito.when(eventDao.selectList(Matchers.any())).thenReturn(new ArrayList<Event>());
        BaseOutput actual1 = this.eventSubscribeService.eventSubscribe(1l, false);
        BaseOutput expired1 = new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), 1, null);
        Assert.assertEquals(expired1.getCode(), actual1.getCode());

    }


    /**
     * 测试数据库中午此数据
     * Method: eventSubscribe(long eventId, boolean subscribe)
     */
    @Test
    public void testEventSubscribe2() throws Exception {
        Mockito.when(eventDao.selectList(eventP2)).thenReturn(eventList2);
        BaseOutput actual2 = this.eventSubscribeService.eventSubscribe(2l, false);
        BaseOutput expired2 = new BaseOutput(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getCode(),
                ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getMsg(), 1, null);
        Assert.assertNotEquals(expired2.getCode(), actual2.getCode());
    }
} 
