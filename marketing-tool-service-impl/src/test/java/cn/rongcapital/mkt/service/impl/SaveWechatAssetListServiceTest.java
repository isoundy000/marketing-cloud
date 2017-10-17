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
public class SaveWechatAssetListServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SaveWechatAssetListService saveWechatAssetListService;
    
    
    @Mock
    private AudienceListDao audienceListDao;
    
    @Mock
    private AudienceListPartyMapDao audienceListPartyMapDao;
    
    @Mock
    private DataPartyDao dataPartyDao;
    
    @Mock
    private WechatAssetGroupDao wechatAssetGroupDao;
    
    @Mock
    private WechatGroupDao wechatGroupDao;
    
    @Mock
    private WechatMemberDao wechatMemberDao;
    
    @Mock
    private DataPopulationDao dataPopulationDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：SaveWechatAssetListService 准备---------------------");
        
        
        saveWechatAssetListService = new SaveWechatAssetListServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(saveWechatAssetListService, "audienceListDao", audienceListDao);
        ReflectionTestUtils.setField(saveWechatAssetListService, "audienceListPartyMapDao", audienceListPartyMapDao);
        ReflectionTestUtils.setField(saveWechatAssetListService, "dataPartyDao", dataPartyDao);
        ReflectionTestUtils.setField(saveWechatAssetListService, "wechatAssetGroupDao", wechatAssetGroupDao);
        ReflectionTestUtils.setField(saveWechatAssetListService, "wechatGroupDao", wechatGroupDao);
        ReflectionTestUtils.setField(saveWechatAssetListService, "wechatMemberDao", wechatMemberDao);
        ReflectionTestUtils.setField(saveWechatAssetListService, "dataPopulationDao", dataPopulationDao);
        
    }
    
    @Test
    public void testSaveWechatAssetList() {
        logger.info("测试方法: saveWechatAssetList ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SaveWechatAssetListService 结束---------------------");
    }

}


