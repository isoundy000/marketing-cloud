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
import cn.rongcapital.mkt.dao.event.EventSourceDao;
import cn.rongcapital.mkt.event.service.EventSourceListService;
import cn.rongcapital.mkt.event.vo.out.EventSourceListOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class EventSourceListServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	EventSourceListService eslService;
	
    @Mock
    private EventSourceDao eventSourceDao;
    
    @Before
    public void setUp() throws Exception {
    	logger.info("测试：EventSourceListService 开始---------------------");
    	eslService = new EventSourceListServiceImpl();
    	
    	ReflectionTestUtils.setField(eslService, "eventSourceDao", eventSourceDao);
    }
    
    @Test
    public void testGetEventSourceListByChannel01() {
    	logger.info("测试方法: getEventSourceListByChannel start");
    	List<EventSourceListOut> list = new ArrayList<EventSourceListOut>();
    	Mockito.when(eventSourceDao.getEventSourceListByChannel(Mockito.any())).thenReturn(list);
    	BaseOutput result = eslService.getEventSourceListByChannel("channel1");
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(0, result.getData().size());
        logger.info("测试方法: getEventSourceListByChannel end");
    }
    
    @Test
    public void testGetEventSourceListByChannel02() {
    	logger.info("测试方法: getEventSourceListByChannel start");
    	List<EventSourceListOut> list = new ArrayList<EventSourceListOut>();
    	Mockito.when(eventSourceDao.getEventSourceListByChannel(Mockito.any())).thenReturn(null);
    	BaseOutput result = eslService.getEventSourceListByChannel("channel1");
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(0, result.getData().size());
        logger.info("测试方法: getEventSourceListByChannel end");
    }
    
    @Test
    public void testGetEventSourceListByChannel03() {
    	logger.info("测试方法: getEventSourceListByChannel start");
    	List<EventSourceListOut> list = new ArrayList<EventSourceListOut>();
    	EventSourceListOut e = new EventSourceListOut();
    	e.setId(1l);
    	e.setName("test");
    	list.add(e);
    	e = new EventSourceListOut();
    	e.setId(2l);
    	e.setName("111");
    	list.add(e);
    	Mockito.when(eventSourceDao.getEventSourceListByChannel(Mockito.any())).thenReturn(list);
    	BaseOutput result = eslService.getEventSourceListByChannel("channel1");
    	
    	EventSourceListOut r1 = (EventSourceListOut)result.getData().get(0);
    	EventSourceListOut r2 = (EventSourceListOut)result.getData().get(1);
    	
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(2, result.getData().size());
        Assert.assertEquals("test", r1.getName());
        Assert.assertEquals("111", r2.getName());
        logger.info("测试方法: getEventSourceListByChannel end");
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：EventSourceListService 结束---------------------");
    }
    
}
