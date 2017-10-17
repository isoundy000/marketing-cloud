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
public class DataGetFilterContactwayServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private DataGetFilterContactwayService dataGetFilterContactwayService;
    
    
    @Mock
    private ContactWayDao contactWayDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：DataGetFilterContactwayService 准备---------------------");
        
        
        dataGetFilterContactwayService = new DataGetFilterContactwayServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(dataGetFilterContactwayService, "contactWayDao", contactWayDao);
        
    }
    
    @Test
    public void testGetFilterContactway() {
        logger.info("测试方法: getFilterContactway ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：DataGetFilterContactwayService 结束---------------------");
    }

}


