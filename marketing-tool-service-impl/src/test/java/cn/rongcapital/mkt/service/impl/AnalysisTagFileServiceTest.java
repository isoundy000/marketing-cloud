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
public class AnalysisTagFileServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private AnalysisTagFileService analysisTagFileService;
    
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：AnalysisTagFileService 准备---------------------");
        
        
        analysisTagFileService = new AnalysisTagFileServiceImpl();
        
        // 把mock的dao set进入service
        
    }
    
    @Test
    public void testInsertTagRecommendMongoDB() {
        logger.info("测试方法: insertTagRecommendMongoDB ");
    }
    
    @Test
    public void testGetTagTree() {
        logger.info("测试方法: getTagTree ");
    }
    
    @Test
    public void testFindTagTree() {
        logger.info("测试方法: findTagTree ");
    }
    
    @Test
    public void testFindTagRecommend() {
        logger.info("测试方法: findTagRecommend ");
    }
    
    @Test
    public void testReadXlsx() {
        logger.info("测试方法: readXlsx ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：AnalysisTagFileService 结束---------------------");
    }

}


