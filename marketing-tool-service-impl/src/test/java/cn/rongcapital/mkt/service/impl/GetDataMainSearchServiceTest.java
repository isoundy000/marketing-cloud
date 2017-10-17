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
public class GetDataMainSearchServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private GetDataMainSearchService getDataMainSearchService;
    
    
    @Mock
    private SegmentationHeadDao segmentationHeadDao;
    
    @Mock
    private CampaignHeadDao campaignHeadDao;
    
    @Mock
    private AudienceListDao audienceListDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：GetDataMainSearchService 准备---------------------");
        
        
        getDataMainSearchService = new GetDataMainSearchServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(getDataMainSearchService, "segmentationHeadDao", segmentationHeadDao);
        ReflectionTestUtils.setField(getDataMainSearchService, "campaignHeadDao", campaignHeadDao);
        ReflectionTestUtils.setField(getDataMainSearchService, "audienceListDao", audienceListDao);
        
    }
    
    @Test
    public void testSearchDataMain() {
        logger.info("测试方法: searchDataMain ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：GetDataMainSearchService 结束---------------------");
    }

}


