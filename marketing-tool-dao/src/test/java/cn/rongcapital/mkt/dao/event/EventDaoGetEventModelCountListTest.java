/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 2017.01.09
 * @复审人:
 *************************************************/

package cn.rongcapital.mkt.dao.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.common.enums.event.EventChannelEnum;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.po.EventPlatform;
import cn.rongcapital.mkt.event.po.EventSource;
import cn.rongcapital.mkt.event.vo.EventModelCount;

@RunWith(SpringJUnit4ClassRunner.class)
public class EventDaoGetEventModelCountListTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private EventDao eventDao;
    
    @Autowired
    private EventSourceDao eventSourceDao;
    
    @Autowired
    private EventPlatformDao eventPlatformDao;
    
    private List<Event> eList = new ArrayList<>();
    private List<EventPlatform> epList = new ArrayList<>();
    private List<EventSource> esList = new ArrayList<>();
    
    private List<EventPlatform> originaleEPList = new ArrayList<>();
    
    @Before
    public void setUp() throws Exception {
    	logger.info("测试: EventDao getEventModelCountList 开始---------------------");
    	logger.info("测试: EventDao 消除旧数据影响");
    	EventPlatform temp = new EventPlatform();
    	temp.setStatus((byte)0);
    	originaleEPList = eventPlatformDao.selectList(temp);
    	for (EventPlatform e : originaleEPList) {
			e.setStatus((byte)1);
			eventPlatformDao.updateById(e);
		}
    	
        logger.info("测试: EventDao 插入测试数据");
        EventSource eventSource = new EventSource();
        EventPlatform eventPlatform1 = new EventPlatform();
        Event event = new Event();
        
        eventPlatform1.setChannel(EventChannelEnum.CHANNEL1.getCode());
        eventPlatform1.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 20));
        eventPlatform1.setName("getEventModelCountList_测试数据");
        eventPlatform1.setStatus((byte) 0);
        eventPlatform1.setCreateTime(new Date());
        eventPlatformDao.insert(eventPlatform1);
        epList.add(eventPlatform1);
        
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 20));
        eventSource.setName("getEventModelCountList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelCountList_测试数据");
        event.setSourceId(eventSource.getId());
        event.setObjectId(1l);
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelCountList_测试数据");
        event.setSourceId(eventSource.getId());
        event.setObjectId(1l);
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelCountList_测试数据");
        event.setSourceId(eventSource.getId());
        event.setObjectId(1l);
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);

        eventPlatform1 = new EventPlatform();
        eventPlatform1.setChannel(EventChannelEnum.CHANNEL2.getCode());
        eventPlatform1.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 21));
        eventPlatform1.setName("getEventModelCountList_测试数据");
        eventPlatform1.setStatus((byte) 0);
        eventPlatform1.setCreateTime(new Date());
        eventPlatformDao.insert(eventPlatform1);
        epList.add(eventPlatform1);
        
        eventSource = new EventSource();
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 21));
        eventSource.setName("getEventModelCountList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelCountList_测试数据");
        event.setSourceId(eventSource.getId());
        event.setObjectId(1l);
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        
        eventSource = new EventSource();
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 22));
        eventSource.setName("getEventModelCountList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelCountList_测试数据");
        event.setSourceId(eventSource.getId());
        event.setObjectId(1l);
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        eventPlatform1 = new EventPlatform();
        eventPlatform1.setChannel(EventChannelEnum.CHANNEL3.getCode());
        eventPlatform1.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 22));
        eventPlatform1.setName("getEventModelCountList_测试数据");
        eventPlatform1.setStatus((byte) 0);
        eventPlatform1.setCreateTime(new Date());
        eventPlatformDao.insert(eventPlatform1);
        epList.add(eventPlatform1);
        
        eventSource = new EventSource();
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 23));
        eventSource.setName("getEventModelCountList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelCountList_测试数据");
        event.setSourceId(eventSource.getId());
        event.setObjectId(1l);
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
    }
    
    @Test
    public void getEventModelCountList01() {
    	
    	List<EventModelCount> list = eventDao.getEventModelCountList();
    	Long c1 = 0l;
    	for(EventModelCount e : list){
    		switch (EventChannelEnum.getByCode(e.getChannel())){
    			case CHANNEL1: 
    				c1=c1+e.getCount();
    				Assert.assertEquals(3, e.getCount().intValue());
    			break;
    			case CHANNEL2: 
    				c1=c1+e.getCount();
    				Assert.assertEquals(2, e.getCount().intValue());
    			break;
    			case CHANNEL3: 
    				c1=c1+e.getCount();
    				Assert.assertEquals(1, e.getCount().intValue());
    			break;
    		}
    	}
    }
    
    @After
    public void tearDown() throws Exception {
    	logger.info("测试: EventDao 还原旧数据 ");
    	for (EventPlatform e : originaleEPList) {
			e.setStatus((byte)0);
			eventPlatformDao.updateById(e);
		}
    	
        logger.info("测试: EventDao 删除测试数据 ");
        for (Event e: eList){
        	e.setStatus((byte) 1);
        	eventDao.updateById(e);
        }
        
        
        logger.info("测试: EventPlatformDao 删除测试数据 ");
        for (EventPlatform e: epList){
        	e.setStatus((byte) 1);
        	eventPlatformDao.updateById(e);
        }
        
        logger.info("测试: EventSourceDao 删除测试数据 ");
        for (EventSource e: esList){
        	e.setStatus((byte) 1);
        	eventSourceDao.updateById(e);
        }
          
        logger.info("测试: EventDao getEventModelCountList 结束---------------------");
    }
    
}
