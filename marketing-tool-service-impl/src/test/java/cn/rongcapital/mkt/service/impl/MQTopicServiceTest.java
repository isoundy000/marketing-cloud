/*************************************************
 * @功能简述: Service实现测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;


import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.service.*;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class MQTopicServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private MQTopicService mQTopicService;
    
    
    @Mock
    private TaskRunLogDao taskRunLogDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：MQTopicService 准备---------------------");
        
        
        mQTopicService = new MQTopicServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(mQTopicService, "taskRunLogDao", taskRunLogDao);
        
    }
    
    @Test
    public void testMain() {
        logger.info("测试方法: main ");
    }
    
    @Test
    public void testInitMQService() {
        logger.info("测试方法: initMQService ");
    }
    
    @Test
    public void testInitReceiver() {
        logger.info("测试方法: initReceiver ");
    }
    
    @Test
    public void testSenderMessage() {
        logger.info("测试方法: senderMessage ");
    }
    
    @Test
    public void testStartMqTask() {
        logger.info("测试方法: startMqTask ");
    }
    
    @Test
    public void testSendMessage() {
        logger.info("测试方法: sendMessage ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：MQTopicService 结束---------------------");
    }

}


