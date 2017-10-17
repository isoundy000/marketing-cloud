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
public class CampaignBodyGetServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CampaignBodyGetService campaignBodyGetService;
    
    
    @Mock
    private CampaignBodyDao campaignBodyDao;
    
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
    private CampaignSwitchDao campaignSwitchDao;
    
    @Mock
    private CampaignTriggerTimerDao campaignTriggerTimerDao;
    
    @Mock
    private CampaignNodeItemDao campaignNodeItemDao;
    
    @Mock
    private TagDao tagDao;
    
    @Mock
    private WechatAssetDao wechatAssetDao;
    
    @Mock
    private WechatAssetGroupDao wechatAssetGroupDao;
    
    @Mock
    private ImgTextAssetDao imgTextAssetDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：CampaignBodyGetService 准备---------------------");
        
        
        campaignBodyGetService = new CampaignBodyGetServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignBodyDao", campaignBodyDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignActionSaveAudienceDao", campaignActionSaveAudienceDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignActionSendH5Dao", campaignActionSendH5Dao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignActionSendPrivtDao", campaignActionSendPrivtDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignActionSendPubDao", campaignActionSendPubDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignActionSetTagDao", campaignActionSetTagDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignActionWaitDao", campaignActionWaitDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignAudienceTargetDao", campaignAudienceTargetDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignDecisionPropCompareDao", campaignDecisionPropCompareDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignDecisionPrvtFriendsDao", campaignDecisionPrvtFriendsDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignDecisionPubFansDao", campaignDecisionPubFansDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignDecisionTagDao", campaignDecisionTagDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignDecisionWechatForwardDao", campaignDecisionWechatForwardDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignDecisionWechatReadDao", campaignDecisionWechatReadDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignSwitchDao", campaignSwitchDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignTriggerTimerDao", campaignTriggerTimerDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignNodeItemDao", campaignNodeItemDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "tagDao", tagDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "wechatAssetDao", wechatAssetDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "wechatAssetGroupDao", wechatAssetGroupDao);
        ReflectionTestUtils.setField(campaignBodyGetService, "imgTextAssetDao", imgTextAssetDao);
        
    }
    
    @Test
    public void testCampaignBodyGet() {
        logger.info("测试方法: campaignBodyGet ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：CampaignBodyGetService 结束---------------------");
    }

}


