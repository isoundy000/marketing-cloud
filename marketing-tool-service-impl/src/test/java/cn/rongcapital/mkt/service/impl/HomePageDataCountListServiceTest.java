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
public class HomePageDataCountListServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private HomePageDataCountListService homePageDataCountListService;
    
    
    @Mock
    private DataPartyDao dataPartyDao;
    
    @Mock
    private CustomTagDao customTagDao;
    
    @Mock
    private TaggroupDao taggroupDao;
    
    @Mock
    private SegmentationHeadDao segmentationHeadDao;
    
    @Mock
    private CampaignHeadDao campaignHeadDao;
    
    @Mock
    private ImportDataHistoryDao importDataHistoryDao;
    
    @Mock
    private WechatMemberDao wechatMemberDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：HomePageDataCountListService 准备---------------------");
        
        
        homePageDataCountListService = new HomePageDataCountListServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(homePageDataCountListService, "dataPartyDao", dataPartyDao);
        ReflectionTestUtils.setField(homePageDataCountListService, "customeTagDao", customTagDao);
        ReflectionTestUtils.setField(homePageDataCountListService, "taggroupDao", taggroupDao);
        ReflectionTestUtils.setField(homePageDataCountListService, "segmentationHeadDao", segmentationHeadDao);
        ReflectionTestUtils.setField(homePageDataCountListService, "campaignHeadDao", campaignHeadDao);
        ReflectionTestUtils.setField(homePageDataCountListService, "importDataHistoryDao", importDataHistoryDao);
        ReflectionTestUtils.setField(homePageDataCountListService, "wechatMemberDao", wechatMemberDao);
        
    }
    
    @Test
    public void testGetDataCountList() {
        logger.info("测试方法: getDataCountList ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：HomePageDataCountListService 结束---------------------");
    }

}


