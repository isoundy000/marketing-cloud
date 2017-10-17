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
public class GroupTagsSearchServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private GroupTagsSearchService groupTagsSearchService;
    
    
    @Mock
    private TagDao tagDao;
    
    @Mock
    private TaggroupDao taggroupDao;
    
    @Mock
    private TagGroupMapDao tagGroupMapDao;
    
    @Mock
    private CustomTagDao customTagDao;
    
    @Mock
    private MongoBaseTagDaoImpl mongoBaseTagDaoImpl;
    
    @Mock
    private CustomTagMapDao customTagMapDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：GroupTagsSearchService 准备---------------------");
        
        
        groupTagsSearchService = new GroupTagsSearchServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(groupTagsSearchService, "tagDao", tagDao);
        ReflectionTestUtils.setField(groupTagsSearchService, "taggroupDao", taggroupDao);
        ReflectionTestUtils.setField(groupTagsSearchService, "tagGroupMapDao", tagGroupMapDao);
        ReflectionTestUtils.setField(groupTagsSearchService, "customTagDao", customTagDao);
        ReflectionTestUtils.setField(groupTagsSearchService, "mongoBaseTagDao", mongoBaseTagDaoImpl);
        ReflectionTestUtils.setField(groupTagsSearchService, "customTagMapDao", customTagMapDao);
        
    }
    
    @Test
    public void testGroupTagsSearch() {
        logger.info("测试方法: groupTagsSearch ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：GroupTagsSearchService 结束---------------------");
    }

}


