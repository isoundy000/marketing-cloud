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
public class CampaignBodyCreateServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CampaignBodyCreateService campaignBodyCreateService;
    
    
    @Mock
    private CampaignHeadDao campaignHeadDao;
    
    @Mock
    private CampaignBodyDao campaignBodyDao;
    
    @Mock
    private CampaignTriggerTimerDao campaignTriggerTimerDao;
    
    @Mock
    private CampaignActionSaveAudienceDao campaignActionSaveAudienceDao;
    
    @Mock
    private CampaignActionSendH5Dao campaignActionSendH5Dao;
    
    @Mock
    private CampaignActionSendPrivtDao campaignActionSendPrivtDao;
    
    @Mock
    private CampaignActionSendPubDao campaignActionSendPubDao;
    
    @Mock
    private CampaignActionSetTagDao campaignActionSetTagDao;
    
    @Mock
    private CampaignActionWaitDao campaignActionWaitDao;
    
    @Mock
    private CampaignAudienceTargetDao campaignAudienceTargetDao;
    
    @Mock
    private CampaignDecisionPropCompareDao campaignDecisionPropCompareDao;
    
    @Mock
    private CampaignDecisionPrvtFriendsDao campaignDecisionPrvtFriendsDao;
    
    @Mock
    private CampaignDecisionPubFansDao campaignDecisionPubFansDao;
    
    @Mock
    private CampaignDecisionTagDao campaignDecisionTagDao;
    
    @Mock
    private CampaignDecisionWechatForwardDao campaignDecisionWechatForwardDao;
    
    @Mock
    private CampaignDecisionWechatReadDao campaignDecisionWechatReadDao;
    
    @Mock
    private CampaignDecisionWechatSentDao campaignDecisionWechatSentDao;
    
    @Mock
    private CampaignSwitchDao campaignSwitchDao;
    
    @Mock
    private CampaignNodeItemDao campaignNodeItemDao;
    
    @Mock
    private TaskScheduleDao taskScheduleDao;
    
    @Mock
    private WechatAssetDao wechatAssetDao;
    
    @Mock
    private WechatAssetGroupDao wechatAssetGroupDao;
    
    @Mock
    private WechatGroupDao wechatGroupDao;
    
    @Mock
    private ImgTextAssetDao imgTextAssetDao;
    
    @Mock
    private CustomTagDao customTagDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：CampaignBodyCreateService 准备---------------------");
        
        
        campaignBodyCreateService = new CampaignBodyCreateServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignHeadDao", campaignHeadDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignBodyDao", campaignBodyDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignTriggerTimerDao", campaignTriggerTimerDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSaveAudienceDao", campaignActionSaveAudienceDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSendH5Dao", campaignActionSendH5Dao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSendPrivtDao", campaignActionSendPrivtDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSendPubDao", campaignActionSendPubDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSetTagDao", campaignActionSetTagDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionWaitDao", campaignActionWaitDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "CampaignAudienceTargetDao", campaignAudienceTargetDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionPropCompareDao", campaignDecisionPropCompareDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionPrvtFriendsDao", campaignDecisionPrvtFriendsDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionPubFansDao", campaignDecisionPubFansDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionTagDao", campaignDecisionTagDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionWechatForwardDao", campaignDecisionWechatForwardDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionWechatReadDao", campaignDecisionWechatReadDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionWechatSentDao", campaignDecisionWechatSentDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignSwitchDao", campaignSwitchDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignNodeItemDao", campaignNodeItemDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "taskScheduleDao", taskScheduleDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "wechatAssetDao", wechatAssetDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "wechatAssetGroupDao", wechatAssetGroupDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "wechatGroupDao", wechatGroupDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "imgTextAssetDao", imgTextAssetDao);
        ReflectionTestUtils.setField(campaignBodyCreateService, "customTagDao", customTagDao);
        
    }
    
    @Test
    public void testCampaignBodyCreate() {
        logger.info("测试方法: campaignBodyCreate ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：CampaignBodyCreateService 结束---------------------");
    }

}


