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
public class SegmentTagUpdateServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SegmentTagUpdateService segmentTagUpdateService;
    
    
    @Mock
    private SegmentationHeadDao segmentationHeadDao;
    
    @Mock
    private CustomTagMapDao customTagMapDao;
    
    @Mock
    private CustomTagDao customTagDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：SegmentTagUpdateService 准备---------------------");
        
        
        segmentTagUpdateService = new SegmentTagUpdateServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(segmentTagUpdateService, "segmentationHeadDao", segmentationHeadDao);
        ReflectionTestUtils.setField(segmentTagUpdateService, "customTagMapDao", customTagMapDao);
        ReflectionTestUtils.setField(segmentTagUpdateService, "customTagDao", customTagDao);
        
    }
    
    @Test
    public void testUpdateSegmentTag() {
        logger.info("测试方法: updateSegmentTag ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentTagUpdateService 结束---------------------");
    }

}


