package cn.rongcapital.mkt.event.service.impl;

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
import cn.rongcapital.mkt.dao.event.EventPlatformDao;
import cn.rongcapital.mkt.dao.event.EventSourceDao;
import cn.rongcapital.mkt.event.po.EventPlatform;
import cn.rongcapital.mkt.event.po.EventSource;
import cn.rongcapital.mkt.event.service.EventSourceSaveService;
import cn.rongcapital.mkt.event.vo.in.EventSourceVo;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class EventSourceSaveServiceImplTest {

    @Mock
    private EventSourceDao eventSourceDao;

    @Mock
    private EventPlatformDao eventPlatformDao;

    private EventSourceSaveService service;

    @Before
    public void setUp() throws Exception {
        service = new EventSourceSaveServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    /**
     * Event source is not Exist
     */
    @Test
    public void testSaveEventSource01() {
        EventSourceVo source = new EventSourceVo();
        source.setCode("WX");
        source.setName("微信");
        source.setPlatformCode("WX");

        List<EventSource> sourceList = new ArrayList<EventSource>();
        EventSource sourceItem = new EventSource();
        sourceList.add(sourceItem);
        Mockito.when(eventSourceDao.selectList(Mockito.any(EventSource.class))).thenReturn(sourceList);
        ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);

        BaseOutput output = service.saveEventSource(source);
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_EVENT_SOURCE_CODE_ALREADY_EXIST.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_EVENT_SOURCE_CODE_ALREADY_EXIST.getMsg(), output.getMsg());
    }

    /**
     * Event source PF code is not Exist
     */
    @Test
    public void testSaveEventSource02() {
        EventSourceVo source = new EventSourceVo();
        source.setCode("WX");
        source.setName("微信");
        source.setPlatformCode("WX");

        Mockito.when(eventSourceDao.selectList(Mockito.any(EventSource.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);

        Mockito.when(eventPlatformDao.selectList(Mockito.any(EventPlatform.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventPlatformDao", eventPlatformDao);

        BaseOutput output = service.saveEventSource(source);
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_EVENT_SOURCE_PF_NOT_EXIST.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_EVENT_SOURCE_PF_NOT_EXIST.getMsg(), output.getMsg());
    }

    /**
     * Normal
     */
    @Test
    public void testSaveEventSource03() {
        EventSourceVo source = new EventSourceVo();
        source.setCode("WX");
        source.setName("微信");
        source.setPlatformCode("WX");

        Mockito.when(eventSourceDao.selectList(Mockito.any(EventSource.class))).thenReturn(null);

        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                EventSource data = (EventSource) args[0];
                Assert.assertEquals("WX", data.getCode());
                Assert.assertEquals("微信", data.getName());
                Assert.assertEquals(66, data.getPlatformId().intValue());
                return null;
            }
        }).when(eventSourceDao).insert(Mockito.any());

        ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);


        List<EventPlatform> platFormList = new ArrayList<EventPlatform>();
        EventPlatform platForm = new EventPlatform();
        platForm.setId(66L);
        platFormList.add(platForm);
        Mockito.when(eventPlatformDao.selectList(Mockito.any(EventPlatform.class))).thenReturn(platFormList);
        ReflectionTestUtils.setField(service, "eventPlatformDao", eventPlatformDao);
        BaseOutput output = service.saveEventSource(source);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
    }

}
