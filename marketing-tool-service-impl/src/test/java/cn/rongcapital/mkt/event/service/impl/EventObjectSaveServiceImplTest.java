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
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.service.EventObjectSaveService;
import cn.rongcapital.mkt.event.vo.in.EventObjecAttribure;
import cn.rongcapital.mkt.event.vo.in.EventObjectVo;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class EventObjectSaveServiceImplTest {

    @Mock
    private EventObjectDao eventObjectDao;

    private EventObjectSaveService service;

    @Before
    public void setUp() throws Exception {
        service = new EventObjectSaveServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    /**
     * 客体对象标识重复
     */
    @Test
    public void testSaveEventObj01() {
        EventObjectVo event = new EventObjectVo();
        event.setCode("AAA");
        event.setName("BBB");
        event.setInstanceNameLabel("CCC");
        event.setInstanceNameProp("DDD");

        List<EventObject> eventObjList = new ArrayList<EventObject>();
        EventObject eventObj = new EventObject();
        eventObj.setId(33L);
        eventObj.setCode("AAA");
        eventObj.setName("BBB");
        eventObjList.add(eventObj);
        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(eventObjList);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
        BaseOutput output = service.saveEventObj(event);
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_CODE_ALREADY_EXIST.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_CODE_ALREADY_EXIST.getMsg(), output.getMsg());
    }

    /**
     * 客体对象属性重复
     */
    @Test
    public void testSaveEventObj02() {
        EventObjectVo event = new EventObjectVo();
        event.setCode("AAA");
        event.setName("BBB");
        event.setInstanceNameLabel("CCC");
        event.setInstanceNameProp("DDD");
        List<EventObjecAttribure> attributes = new ArrayList<EventObjecAttribure>();
        EventObjecAttribure item = new EventObjecAttribure();
        item.setLabel("label1");
        item.setName("name1");
        attributes.add(item);

        item = new EventObjecAttribure();
        item.setLabel("label2");
        item.setName("name2");
        attributes.add(item);

        item = new EventObjecAttribure();
        item.setLabel("label3");
        item.setName("name2");
        attributes.add(item);
        event.setAttributes(attributes);

        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        BaseOutput output = service.saveEventObj(event);
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_ATTRIBUTE_DUPLICATED.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_ATTRIBUTE_DUPLICATED.getMsg(), output.getMsg());
    }

    /**
     * 正常（含有属性）
     */
    @Test
    public void testSaveEventObj03() {
        EventObjectVo event = new EventObjectVo();
        event.setCode("AAA");
        event.setName("BBB");
        event.setInstanceNameLabel("CCC");
        event.setInstanceNameProp("DDD");
        List<EventObjecAttribure> attributes = new ArrayList<EventObjecAttribure>();
        EventObjecAttribure item = new EventObjecAttribure();
        item.setLabel("label1");
        item.setName("name1");
        attributes.add(item);

        item = new EventObjecAttribure();
        item.setLabel("label2");
        item.setName("name2");
        attributes.add(item);
        event.setAttributes(attributes);

        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(null);

        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                EventObject data = (EventObject) args[0];
                Assert.assertEquals("AAA", data.getCode());
                Assert.assertEquals("BBB", data.getName());
                Assert.assertEquals("CCC", data.getInstanceNameLabel());
                Assert.assertEquals("DDD", data.getInstanceNameProp());
                Assert.assertEquals(false, data.getSystemObject());
                Assert.assertEquals(
                        "[{\"label\":\"label1\",\"name\":\"name1\"},{\"label\":\"label2\",\"name\":\"name2\"}]",
                        data.getAttributes());
                return null;
            }
        }).when(eventObjectDao).insert(Mockito.any());

        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        BaseOutput output = service.saveEventObj(event);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
    }

    /**
     * 正常（不含有属性）
     */
    @Test
    public void testSaveEventObj04() {
        EventObjectVo event = new EventObjectVo();
        event.setCode("AAA");
        event.setName("BBB");
        event.setInstanceNameLabel("CCC");
        event.setInstanceNameProp("DDD");

        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(null);

        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                EventObject data = (EventObject) args[0];
                Assert.assertEquals("AAA", data.getCode());
                Assert.assertEquals("BBB", data.getName());
                Assert.assertEquals("CCC", data.getInstanceNameLabel());
                Assert.assertEquals("DDD", data.getInstanceNameProp());
                Assert.assertEquals(false, data.getSystemObject());
                Assert.assertNull(data.getAttributes());
                return null;
            }
        }).when(eventObjectDao).insert(Mockito.any());

        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        BaseOutput output = service.saveEventObj(event);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
    }

}
