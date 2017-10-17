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
public class TagRelatedImporterTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private TagRelatedImporter tagRelatedImporter;
    
    
    @Mock
    private TagRecommendDao tagRecommendDao;
    
    @Mock
    private TagDao tagDao;
    
    @Mock
    private TagGroupMapDao tagGroupMapDao;
    
    @Mock
    private TaggroupDao taggroupDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：TagRelatedImporter 准备---------------------");
        
        
        tagRelatedImporter = new TagRelatedImporterImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(tagRelatedImporter, "tagRecommendDao", tagRecommendDao);
        ReflectionTestUtils.setField(tagRelatedImporter, "tagDao", tagDao);
        ReflectionTestUtils.setField(tagRelatedImporter, "tagGroupMapDao", tagGroupMapDao);
        ReflectionTestUtils.setField(tagRelatedImporter, "taggroupDao", taggroupDao);
        
    }
    
    @Test
    public void testImportData() {
        logger.info("测试方法: importData ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：TagRelatedImporter 结束---------------------");
    }

}


