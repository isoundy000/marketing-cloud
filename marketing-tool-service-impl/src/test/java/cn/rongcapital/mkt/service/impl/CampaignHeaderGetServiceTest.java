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
public class CampaignHeaderGetServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CampaignHeaderGetService campaignHeaderGetService;
    
    
    @Mock
    private CampaignHeadDao campaignHeadDao;
    
    @Mock
    private CampaignTriggerTimerDao campaignTriggerTimerDao;
    
    @Mock
    private CampaignAudienceTargetDao campaignAudienceTargetDao;
    
    @Mock
    private CampaignActionSendH5Dao campaignActionSendH5Dao;
    
    @Mock
    private CampaignActionSendPrivtDao campaignActionSendPrivtDao;
    
    @Mock
    private CampaignActionSendPubDao campaignActionSendPubDao;
    
    @Mock
    private WechatAssetDao wechatAssetDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：CampaignHeaderGetService 准备---------------------");
        
        
        campaignHeaderGetService = new CampaignHeaderGetServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(campaignHeaderGetService, "campaignHeadDao", campaignHeadDao);
        ReflectionTestUtils.setField(campaignHeaderGetService, "campaignTriggerTimerDao", campaignTriggerTimerDao);
        ReflectionTestUtils.setField(campaignHeaderGetService, "campaignAudienceTargetDao", campaignAudienceTargetDao);
        ReflectionTestUtils.setField(campaignHeaderGetService, "campaignActionSendH5Dao", campaignActionSendH5Dao);
        ReflectionTestUtils.setField(campaignHeaderGetService, "campaignActionSendPrivtDao", campaignActionSendPrivtDao);
        ReflectionTestUtils.setField(campaignHeaderGetService, "campaignActionSendPubDao", campaignActionSendPubDao);
        ReflectionTestUtils.setField(campaignHeaderGetService, "wechatAssetDao", wechatAssetDao);
        
    }
    
    @Test
    public void testCampaignHeaderGet() {
        logger.info("测试方法: campaignHeaderGet ");
    }
    
    @Test
    public void testCampaignProfileList() {
        logger.info("测试方法: campaignProfileList ");
    }
    
    @Test
    public void testCampaignAnalysisList() {
        logger.info("测试方法: campaignAnalysisList ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：CampaignHeaderGetService 结束---------------------");
    }

}


