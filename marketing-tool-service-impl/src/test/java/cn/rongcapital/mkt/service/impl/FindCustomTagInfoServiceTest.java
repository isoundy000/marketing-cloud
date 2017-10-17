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
public class FindCustomTagInfoServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private FindCustomTagInfoService findCustomTagInfoService;
    
    
    @Mock
    private MongoBaseTagDaoImpl mongoBaseTagDaoImpl;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：FindCustomTagInfoService 准备---------------------");
        
        
        findCustomTagInfoService = new FindCustomTagInfoServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(findCustomTagInfoService, "mongoBaseTagDao", mongoBaseTagDaoImpl);
        
    }
    
    @Test
    public void testFindCustomTagByTag() {
        logger.info("测试方法: findCustomTagByTag ");
    }
    
    @Test
    public void testFindCustomTagByTagNameAndTagPath() {
        logger.info("测试方法: findCustomTagByTagNameAndTagPath ");
    }
    
    @Test
    public void testFindCustomTagInfoByTagId() {
        logger.info("测试方法: findCustomTagInfoByTagId ");
    }
    
    @Test
    public void testQueryMDataCountByTagId() {
        logger.info("测试方法: queryMDataCountByTagId ");
    }
    
    @Test
    public void testFindAllCustomTagLeaf() {
        logger.info("测试方法: findAllCustomTagLeaf ");
    }
    
    @Test
    public void testFindMDataByTagId() {
        logger.info("测试方法: findMDataByTagId ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：FindCustomTagInfoService 结束---------------------");
    }

}


