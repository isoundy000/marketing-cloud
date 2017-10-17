/*************************************************
 * @功能简述: EventGeneralGetService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016-12-07
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.service.EventGeneralGetService;
import cn.rongcapital.mkt.event.vo.out.EventGeneralOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class EventGeneralGetServiceImplTest {

    @Mock
    private EventDao eventDao;

    @Mock
    private EventObjectDao eventObjectDao;

    private EventGeneralGetService service;

    @Before
    public void setUp() throws Exception {
        service = new EventGeneralGetServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    /**
     * 完整数据
     */
    @Test
    public void testGetEventGeneral01() {
        List<Event> eventList = new ArrayList<Event>();
        Event event = new Event();
        event.setId(11L);
        event.setCode("AAA");
        event.setName("BBB");
        event.setSourceId(22L);
        event.setObjectId(33L);
        eventList.add(event);
        Mockito.when(eventDao.selectList(Mockito.any(Event.class))).thenReturn(eventList);
        ReflectionTestUtils.setField(service, "eventDao", eventDao);

        List<EventObject> eventObjList = new ArrayList<EventObject>();
        EventObject eventObj = new EventObject();
        eventObj.setId(33L);
        eventObj.setCode("CCC");
        eventObj.setName("DDD");
        eventObjList.add(eventObj);
        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(eventObjList);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        BaseOutput output = service.getEventGeneral(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));
        Assert.assertEquals(output.getData().size(), 1);
        EventGeneralOut outputData = (EventGeneralOut) output.getData().get(0);

        Assert.assertEquals(outputData.getId().intValue(), 11);
        Assert.assertEquals(outputData.getCode(), "AAA");
        Assert.assertEquals(outputData.getName(), "BBB");
        Assert.assertEquals(outputData.getSourceId().intValue(), 22);
        Assert.assertEquals(outputData.getObjectId().intValue(), 33);
        Assert.assertEquals(outputData.getObjectName(), "DDD");
    }

    /**
     * Event 有 EventObject 无
     */
    @Test
    public void testGetEventGeneral02() {
        List<Event> eventList = new ArrayList<Event>();
        Event event = new Event();
        event.setId(11L);
        event.setCode("AAA");
        event.setName("BBB");
        event.setSourceId(22L);
        event.setObjectId(33L);
        eventList.add(event);
        Mockito.when(eventDao.selectList(Mockito.any(Event.class))).thenReturn(eventList);
        ReflectionTestUtils.setField(service, "eventDao", eventDao);

        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        BaseOutput output = service.getEventGeneral(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));
        Assert.assertEquals(output.getData().size(), 1);
        EventGeneralOut outputData = (EventGeneralOut) output.getData().get(0);

        Assert.assertEquals(outputData.getId().intValue(), 11);
        Assert.assertEquals(outputData.getCode(), "AAA");
        Assert.assertEquals(outputData.getName(), "BBB");
        Assert.assertEquals(outputData.getSourceId().intValue(), 22);
        Assert.assertEquals(outputData.getObjectId().intValue(), 33);
        Assert.assertNull(outputData.getObjectName());
    }


    /**
     * Event 有 EventObject 无
     */
    @Test
    public void testGetEventGeneral03() {
        List<Event> eventList = new ArrayList<Event>();
        Event event = new Event();
        event.setId(11L);
        event.setCode("AAA");
        event.setName("BBB");
        event.setSourceId(22L);
        eventList.add(event);
        Mockito.when(eventDao.selectList(Mockito.any(Event.class))).thenReturn(eventList);
        ReflectionTestUtils.setField(service, "eventDao", eventDao);

        BaseOutput output = service.getEventGeneral(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));
        Assert.assertEquals(output.getData().size(), 1);
        EventGeneralOut outputData = (EventGeneralOut) output.getData().get(0);

        Assert.assertEquals(outputData.getId().intValue(), 11);
        Assert.assertEquals(outputData.getCode(), "AAA");
        Assert.assertEquals(outputData.getName(), "BBB");
        Assert.assertEquals(outputData.getSourceId().intValue(), 22);
        Assert.assertNull(outputData.getObjectId());
        Assert.assertNull(outputData.getObjectName());
    }

    /**
     * Event 无 EventObject 无
     */
    @Test
    public void testGetEventGeneral04() {
        Mockito.when(eventDao.selectList(Mockito.any(Event.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventDao", eventDao);

        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        BaseOutput output = service.getEventGeneral(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isEmpty(output.getData()));
    }


}
