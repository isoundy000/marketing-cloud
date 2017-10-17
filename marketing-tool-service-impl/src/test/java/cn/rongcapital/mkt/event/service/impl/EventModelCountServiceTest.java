/*************************************************
 * @功能简述: EventModelCountService 实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:	2017.01.10
 * @复审人:
 *************************************************/
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
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.event.EventChannelEnum;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.service.EventModelCountService;
import cn.rongcapital.mkt.event.vo.EventModelCount;
import cn.rongcapital.mkt.event.vo.out.EventModelCountOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class EventModelCountServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	EventModelCountService emcService;
	
    @Mock
    private EventDao eventDao;
    
    @Before
    public void setUp() throws Exception {
    	logger.info("测试：EventModelCountService 开始---------------------");
    	emcService = new EventModelCountServiceImpl();
    	
    	ReflectionTestUtils.setField(emcService, "eventDao", eventDao);

    }
    
    @Test
    public void testGetEventModelCountList01() {
    	logger.info("测试方法: getEventModelCountList start");
    	List<EventModelCount> list = new ArrayList<EventModelCount>();
    	Mockito.when(eventDao.getEventModelCountList()).thenReturn(list);
    	BaseOutput result = emcService.getEventModelCountList();
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
    	EventModelCountOut emCount = (EventModelCountOut) result.getData().get(0);
        Assert.assertEquals(0l, emCount.getTotalCount().longValue());
        Assert.assertEquals(0l, emCount.getFirstChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getSecondChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getThirdChannelCount().longValue());
        logger.info("测试方法: getEventModelCountList end");
    }
    
    @Test
    public void testGetEventModelCountList02() {
    	logger.info("测试方法: getEventModelCountList start");
    	Mockito.when( eventDao.getEventModelCountList()).thenReturn(null);

    	BaseOutput result = emcService.getEventModelCountList();
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
    	EventModelCountOut emCount = (EventModelCountOut) result.getData().get(0);
        Assert.assertEquals(0l, emCount.getTotalCount().longValue());
        Assert.assertEquals(0l, emCount.getFirstChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getSecondChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getThirdChannelCount().longValue());
        logger.info("测试方法: getEventModelCountList end");
    }
    
    @Test
    public void testGetEventModelCountList03() {
    	logger.info("测试方法: getEventModelCountList start");
    	List<EventModelCount> list = new ArrayList<EventModelCount>();
    	list.add(null);
    	list.add(new EventModelCount());
    	Mockito.when(eventDao.getEventModelCountList()).thenReturn(list);

    	BaseOutput result = emcService.getEventModelCountList();
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
    	EventModelCountOut emCount = (EventModelCountOut) result.getData().get(0);
        Assert.assertEquals(0l, emCount.getTotalCount().longValue());
        Assert.assertEquals(0l, emCount.getFirstChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getSecondChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getThirdChannelCount().longValue());
        logger.info("测试方法: getEventModelCountList end");
    }
    
    @Test
    public void testGetEventModelCountList04() {
    	logger.info("测试方法: getEventModelCountList start");
    	EventModelCount emc = new EventModelCount();
    	emc.setChannel(null);
    	emc.setCount(null);
    	List<EventModelCount> list = new ArrayList<EventModelCount>();
    	list.add(null);
    	list.add(new EventModelCount());
    	list.add(emc);
    	Mockito.when(eventDao.getEventModelCountList()).thenReturn(list);

    	BaseOutput result = emcService.getEventModelCountList();
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
    	EventModelCountOut emCount = (EventModelCountOut) result.getData().get(0);
        Assert.assertEquals(0l, emCount.getTotalCount().longValue());
        Assert.assertEquals(0l, emCount.getFirstChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getSecondChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getThirdChannelCount().longValue());
        logger.info("测试方法: getEventModelCountList end");
    }
    
    @Test
    public void testGetEventModelCountList05() {
    	logger.info("测试方法: getEventModelCountList start");
    	EventModelCount emc = new EventModelCount();
    	emc.setChannel("sdf");
    	emc.setCount(null);
    	EventModelCount emc2 = new EventModelCount();
    	emc2.setChannel(EventChannelEnum.CHANNEL1.getCode());
    	emc2.setCount(null);
    	EventModelCount emc3 = new EventModelCount();
    	emc3.setChannel(EventChannelEnum.CHANNEL2.getCode());
    	emc3.setCount(0l);
    	EventModelCount emc4 = new EventModelCount();
    	emc4.setChannel(EventChannelEnum.CHANNEL3.getCode());
    	emc4.setCount(3l);
    	List<EventModelCount> list = new ArrayList<EventModelCount>();
    	list.add(emc);
    	list.add(emc2);
    	list.add(emc3);
    	list.add(emc4);
    	Mockito.when(eventDao.getEventModelCountList()).thenReturn(list);

    	BaseOutput result = emcService.getEventModelCountList();
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
    	EventModelCountOut emCount = (EventModelCountOut) result.getData().get(0);
        Assert.assertEquals(3l, emCount.getTotalCount().longValue());
        Assert.assertEquals(0l, emCount.getFirstChannelCount().longValue());
        Assert.assertEquals(0l, emCount.getSecondChannelCount().longValue());
        Assert.assertEquals(3l, emCount.getThirdChannelCount().longValue());
        logger.info("测试方法: getEventModelCountList end");
    }
    
    
    @Test
    public void testGetEventModelCountList06() {
    	logger.info("测试方法: getEventModelCountList start");
    	EventModelCount emc = new EventModelCount();
    	emc.setChannel("sdf");
    	emc.setCount(null);
    	EventModelCount emc2 = new EventModelCount();
    	emc2.setChannel(EventChannelEnum.CHANNEL1.getCode());
    	emc2.setCount(1l);
    	EventModelCount emc3 = new EventModelCount();
    	emc3.setChannel(EventChannelEnum.CHANNEL2.getCode());
    	emc3.setCount(2l);
    	EventModelCount emc4 = new EventModelCount();
    	emc4.setChannel(EventChannelEnum.CHANNEL3.getCode());
    	emc4.setCount(3l);
    	List<EventModelCount> list = new ArrayList<EventModelCount>();
    	list.add(emc);
    	list.add(emc2);
    	list.add(emc3);
    	list.add(emc4);
    	Mockito.when( eventDao.getEventModelCountList()).thenReturn(list);

    	BaseOutput result = emcService.getEventModelCountList();
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
    	EventModelCountOut emCount = (EventModelCountOut) result.getData().get(0);
        Assert.assertEquals(6l, emCount.getTotalCount().longValue());
        Assert.assertEquals(1l, emCount.getFirstChannelCount().longValue());
        Assert.assertEquals(2l, emCount.getSecondChannelCount().longValue());
        Assert.assertEquals(3l, emCount.getThirdChannelCount().longValue());
        logger.info("测试方法: getEventModelCountList end");
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：EventModelCountService 结束---------------------");
    }
}
