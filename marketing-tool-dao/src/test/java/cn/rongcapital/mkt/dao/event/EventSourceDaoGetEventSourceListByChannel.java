/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 2017.01.10
 * @复审人:
 *************************************************/

package cn.rongcapital.mkt.dao.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import cn.rongcapital.mkt.event.po.EventPlatform;
import cn.rongcapital.mkt.event.po.EventSource;
import cn.rongcapital.mkt.event.vo.out.EventSourceListOut;

@RunWith(SpringJUnit4ClassRunner.class)
public class EventSourceDaoGetEventSourceListByChannel extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EventSourceDao eventSourceDao;

	@Autowired
	private EventPlatformDao eventPlatformDao;

	private List<EventPlatform> epList = new ArrayList<>();
	private List<EventSource> esList = new ArrayList<>();
	private List<EventPlatform> originaleEPList = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		logger.info("测试: EventPlatformDao getEventSourceListByChannel 开始---------------------");
		logger.info("测试: EventSourceDao 消除旧数据影响");
		EventPlatform temp = new EventPlatform();
		temp.setStatus((byte) 0);
		originaleEPList = eventPlatformDao.selectList(temp);
		for (EventPlatform e : originaleEPList) {
			e.setStatus((byte) 1);
			eventPlatformDao.updateById(e);
		}

		logger.info("测试: EventDao 插入测试数据");
		EventPlatform eventPlatform1 = new EventPlatform();
		eventPlatform1.setChannel(EventChannelEnum.CHANNEL1.getCode());
		eventPlatform1.setCode("TEST_" + UUID.randomUUID().toString().substring(0, 20));
		eventPlatform1.setName("getEventModelCountList_测试数据");
		eventPlatform1.setStatus((byte) 0);
		eventPlatform1.setCreateTime(new Date());
		eventPlatformDao.insert(eventPlatform1);
		epList.add(eventPlatform1);
		
		EventSource eventSource = new EventSource();
		eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 20));
        eventSource.setName("getEventModelCountList_测试数据1");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
		eventSource = new EventSource();
		eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 20));
        eventSource.setName("getEventModelCountList_测试数据2");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
		eventSource = new EventSource();
		eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 20));
        eventSource.setName("getEventModelCountList_测试数据3");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
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
        eventSource.setName("getEventModelCountList_测试数据4");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        eventSource = new EventSource();
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 22));
        eventSource.setName("getEventModelCountList_测试数据5");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        
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
        eventSource.setName("getEventModelCountList_测试数据6");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
	}

	@Test
	public void getEventModelCountList01() {
		
	    Map<String, Object> paramMap = new HashMap();
        paramMap.put("channel", EventChannelEnum.CHANNEL1.getCode());
        List<EventSourceListOut> list = eventSourceDao.getEventSourceListByChannel(paramMap);
        Assert.assertEquals(3, list.size());
        int i = 0;
        for (EventSource mockData : esList) {
			for (EventSourceListOut result : list) {
				if(mockData.getId() == result.getId()){
					Assert.assertEquals(mockData.getName(), result.getName());
					i++;
				}
			}
		}
        Assert.assertEquals(3,i);
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试: EventSourceDao 还原旧数据 ");
		for (EventPlatform e : originaleEPList) {
			e.setStatus((byte) 0);
			eventPlatformDao.updateById(e);
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
        
        logger.info("测试: EventPlatformDao getEventSourceListByChannel 结束---------------------");
	}

}
