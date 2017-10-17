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
public class WeixinAnalysisChdataListServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private WeixinAnalysisChdataListService weixinAnalysisChdataListService;
    
    
    @Mock
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;
    
    @Mock
    private WechatChannelDao wechatChannelDao;
    
    @Mock
    private WechatQrcodeDao wechatQrcodeDao;
    
    @Mock
    private WechatQrcodeScanDao wechatQrcodeScanDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：WeixinAnalysisChdataListService 准备---------------------");
        
        
        weixinAnalysisChdataListService = new WeixinAnalysisChdataListServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(weixinAnalysisChdataListService, "wechatQrcodeFocusDao", wechatQrcodeFocusDao);
        ReflectionTestUtils.setField(weixinAnalysisChdataListService, "wechatChannelDao", wechatChannelDao);
        ReflectionTestUtils.setField(weixinAnalysisChdataListService, "wechatQrcodeDao", wechatQrcodeDao);
        ReflectionTestUtils.setField(weixinAnalysisChdataListService, "wechatQrcodeScanDao", wechatQrcodeScanDao);
        
    }
    
    @Test
    public void testGetAnalysisChdata() {
        logger.info("测试方法: getAnalysisChdata ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：WeixinAnalysisChdataListService 结束---------------------");
    }

}


