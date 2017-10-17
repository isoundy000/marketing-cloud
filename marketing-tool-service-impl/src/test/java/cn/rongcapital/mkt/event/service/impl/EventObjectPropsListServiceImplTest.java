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
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.dao.event.EventObjectPropValueDao;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.po.EventObjectPropValue;
import cn.rongcapital.mkt.event.service.EventObjectPropsListService;
import cn.rongcapital.mkt.event.vo.in.EventObjecAttribure;
import cn.rongcapital.mkt.vo.BaseOutput;

import com.alibaba.fastjson.JSON;

@RunWith(MockitoJUnitRunner.class)
public class EventObjectPropsListServiceImplTest {

    @Mock
    private EventObjectDao eventObjectDao;

    @Mock
    private EventObjectPropValueDao eventObjectPropValueDao;

    private EventObjectPropsListService service;

    @Before
    public void setUp() throws Exception {
        service = new EventObjectPropsListServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    /**
     * 不存在EventObject
     */
    @Test
    public void testGetEventObjProps01() {
        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        BaseOutput output = service.getEventObjProps(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isEmpty(output.getData()));
    }

    /**
     * 不存在EventObjectProps
     */
    @Test
    public void testGetEventObjProps02() {
        List<EventObject> eventObjList = new ArrayList<EventObject>();
        EventObject eventObj = new EventObject();
        eventObj.setId(33L);
        eventObj.setCode("CCC");
        eventObj.setName("DDD");
        eventObj.setInstanceNameLabel("饮品名称");
        eventObj.setInstanceNameProp("drinkName");

        List<EventObjecAttribure> attriList = new ArrayList<EventObjecAttribure>();
        EventObjecAttribure attr = new EventObjecAttribure();
        attr.setLabel("口味");
        attr.setName("taste");
        attriList.add(attr);
        attr = new EventObjecAttribure();
        attr.setLabel("颜色");
        attr.setName("color");
        attriList.add(attr);
        eventObj.setAttributes(JSON.toJSONString(attriList));
        eventObjList.add(eventObj);
        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(eventObjList);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        Mockito.when(eventObjectPropValueDao.selectList(Mockito.any(EventObjectPropValue.class))).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventObjectPropValueDao", eventObjectPropValueDao);

        BaseOutput output = service.getEventObjProps(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(3, output.getTotal());
        Assert.assertEquals(3, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));
        Assert.assertEquals(output.getData().size(), 3);
        Assert.assertEquals(
                output.getData().toString(),
                "[EventObjectPropsOut [name=drinkName, label=饮品名称, values=[]], EventObjectPropsOut [name=taste, label=口味, values=[]], EventObjectPropsOut [name=color, label=颜色, values=[]]]");
    }

    /**
     * 存在EventObjectProps（属性比定义属性多）
     */
    @Test
    public void testGetEventObjProps03() {
        List<EventObject> eventObjList = new ArrayList<EventObject>();
        EventObject eventObj = new EventObject();
        eventObj.setId(33L);
        eventObj.setCode("CCC");
        eventObj.setName("DDD");
        eventObj.setInstanceNameLabel("饮品名称");
        eventObj.setInstanceNameProp("drinkName");

        List<EventObjecAttribure> attriList = new ArrayList<EventObjecAttribure>();
        EventObjecAttribure attr = new EventObjecAttribure();
        attr.setLabel("口味");
        attr.setName("taste");
        attriList.add(attr);
        attr = new EventObjecAttribure();
        attr.setLabel("颜色");
        attr.setName("color");
        attriList.add(attr);
        eventObj.setAttributes(JSON.toJSONString(attriList));
        eventObjList.add(eventObj);
        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(eventObjList);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        List<EventObjectPropValue> eventPropList = new ArrayList<EventObjectPropValue>();
        EventObjectPropValue prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("taste");
        prop.setPropValue("香");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("drinkName");
        prop.setPropValue("可乐");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("taste");
        prop.setPropValue("甜");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("price");
        prop.setPropValue("30");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("drinkName");
        prop.setPropValue("雪碧");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("color");
        prop.setPropValue("红");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("price");
        prop.setPropValue("20");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("color");
        prop.setPropValue("黄");
        eventPropList.add(prop);

        Mockito.when(eventObjectPropValueDao.selectList(Mockito.any(EventObjectPropValue.class))).thenReturn(
                eventPropList);
        ReflectionTestUtils.setField(service, "eventObjectPropValueDao", eventObjectPropValueDao);

        BaseOutput output = service.getEventObjProps(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(3, output.getTotal());
        Assert.assertEquals(3, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));
        Assert.assertEquals(output.getData().size(), 3);
        Assert.assertEquals(
                "[EventObjectPropsOut [name=drinkName, label=饮品名称, values=[可乐, 雪碧]], EventObjectPropsOut [name=taste, label=口味, values=[香, 甜]], EventObjectPropsOut [name=color, label=颜色, values=[红, 黄]]]",
                output.getData().toString());

    }

    /**
     * 存在EventObjectProps（属性比定义属性少）
     */
    @Test
    public void testGetEventObjProps04() {
        List<EventObject> eventObjList = new ArrayList<EventObject>();
        EventObject eventObj = new EventObject();
        eventObj.setId(33L);
        eventObj.setCode("CCC");
        eventObj.setName("DDD");
        eventObj.setInstanceNameLabel("饮品名称");
        eventObj.setInstanceNameProp("drinkName");

        List<EventObjecAttribure> attriList = new ArrayList<EventObjecAttribure>();
        EventObjecAttribure attr = new EventObjecAttribure();
        attr.setLabel("口味");
        attr.setName("taste");
        attriList.add(attr);
        attr = new EventObjecAttribure();
        attr.setLabel("颜色");
        attr.setName("color");
        attriList.add(attr);
        eventObj.setAttributes(JSON.toJSONString(attriList));
        eventObjList.add(eventObj);
        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(eventObjList);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        List<EventObjectPropValue> eventPropList = new ArrayList<EventObjectPropValue>();
        EventObjectPropValue prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("taste");
        prop.setPropValue("香");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("drinkName");
        prop.setPropValue("可乐");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("taste");
        prop.setPropValue("甜");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("drinkName");
        prop.setPropValue("雪碧");
        eventPropList.add(prop);

        Mockito.when(eventObjectPropValueDao.selectList(Mockito.any(EventObjectPropValue.class))).thenReturn(
                eventPropList);
        ReflectionTestUtils.setField(service, "eventObjectPropValueDao", eventObjectPropValueDao);

        BaseOutput output = service.getEventObjProps(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(3, output.getTotal());
        Assert.assertEquals(3, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));
        Assert.assertEquals(output.getData().size(), 3);
        Assert.assertEquals(
                "[EventObjectPropsOut [name=drinkName, label=饮品名称, values=[可乐, 雪碧]], EventObjectPropsOut [name=taste, label=口味, values=[香, 甜]], EventObjectPropsOut [name=color, label=颜色, values=[]]]",
                output.getData().toString());

    }

    /**
     * 存在EventObjectProps（无扩展属性）
     */
    @Test
    public void testGetEventObjProps05() {
        List<EventObject> eventObjList = new ArrayList<EventObject>();
        EventObject eventObj = new EventObject();
        eventObj.setId(33L);
        eventObj.setCode("CCC");
        eventObj.setName("DDD");
        eventObj.setInstanceNameLabel("饮品名称");
        eventObj.setInstanceNameProp("drinkName");
        eventObjList.add(eventObj);
        Mockito.when(eventObjectDao.selectList(Mockito.any(EventObject.class))).thenReturn(eventObjList);
        ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);

        List<EventObjectPropValue> eventPropList = new ArrayList<EventObjectPropValue>();
        EventObjectPropValue prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("drinkName");
        prop.setPropValue("可乐");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("drinkName");
        prop.setPropValue("雪碧");
        eventPropList.add(prop);

        prop = new EventObjectPropValue();
        prop.setId(33L);
        prop.setPropName("price");
        prop.setPropValue("20");
        eventPropList.add(prop);

        Mockito.when(eventObjectPropValueDao.selectList(Mockito.any(EventObjectPropValue.class))).thenReturn(
                eventPropList);
        ReflectionTestUtils.setField(service, "eventObjectPropValueDao", eventObjectPropValueDao);

        BaseOutput output = service.getEventObjProps(11L);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));
        Assert.assertEquals(output.getData().size(), 1);
        Assert.assertEquals("[EventObjectPropsOut [name=drinkName, label=饮品名称, values=[可乐, 雪碧]]]", output.getData()
                .toString());

    }

}
