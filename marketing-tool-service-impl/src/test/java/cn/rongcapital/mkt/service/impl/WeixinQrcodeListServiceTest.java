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
public class WeixinQrcodeListServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private WeixinQrcodeListService weixinQrcodeListService;
    
    
    @Mock
    private WechatQrcodeDao wechatQrcodeDao;
    
    @Mock
    private WechatChannelDao wechatChannelDao;
    
    @Mock
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;
    
    @Mock
    private TagDao tagDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：WeixinQrcodeListService 准备---------------------");
        
        
        weixinQrcodeListService = new WeixinQrcodeListServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(weixinQrcodeListService, "wechatQrcodeDao", wechatQrcodeDao);
        ReflectionTestUtils.setField(weixinQrcodeListService, "wechatChannelDao", wechatChannelDao);
        ReflectionTestUtils.setField(weixinQrcodeListService, "wechatQrcodeFocusDao", wechatQrcodeFocusDao);
        ReflectionTestUtils.setField(weixinQrcodeListService, "tagDao", tagDao);
        
    }
    
    @Test
    public void testUpdateStatusByExpirationTime() {
        logger.info("测试方法: updateStatusByExpirationTime ");
    }
    
    @Test
    public void testGetWeixinQrcodeListQrname() {
        logger.info("测试方法: getWeixinQrcodeListQrname ");
    }
    
    @Test
    public void testGetWeixinQrcodeList() {
        logger.info("测试方法: getWeixinQrcodeList ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：WeixinQrcodeListService 结束---------------------");
    }

}


