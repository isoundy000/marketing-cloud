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
public class WechatPersonalAuthServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private WechatPersonalAuthService wechatPersonalAuthService;
    
    
    @Mock
    private TenementDao tenementDao;
    
    @Mock
    private WechatPersonalUuidDao wechatPersonalUuidDao;
    
    @Mock
    private WechatMemberDao wechatMemberDao;
    
    @Mock
    private WechatGroupDao wechatGroupDao;
    
    @Mock
    private WechatAssetDao wechatAssetDao;
    
    @Mock
    private WechatAssetGroupDao wechatAssetGroupDao;
    
    @Mock
    private WechatRegisterDao wechatRegisterDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：WechatPersonalAuthService 准备---------------------");
        
        
        wechatPersonalAuthService = new WechatPersonalAuthServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(wechatPersonalAuthService, "tenementDao", tenementDao);
        ReflectionTestUtils.setField(wechatPersonalAuthService, "wechatPersonalUuidDao", wechatPersonalUuidDao);
        ReflectionTestUtils.setField(wechatPersonalAuthService, "wechatMemberDao", wechatMemberDao);
        ReflectionTestUtils.setField(wechatPersonalAuthService, "wechatGroupDao", wechatGroupDao);
        ReflectionTestUtils.setField(wechatPersonalAuthService, "wechatAssetDao", wechatAssetDao);
        ReflectionTestUtils.setField(wechatPersonalAuthService, "wechatAssetGroupDao", wechatAssetGroupDao);
        ReflectionTestUtils.setField(wechatPersonalAuthService, "wechatRegisterDao", wechatRegisterDao);
        
    }
    
    @Test
    public void testAuthPersonWechat() {
        logger.info("测试方法: authPersonWechat ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：WechatPersonalAuthService 结束---------------------");
    }

}


