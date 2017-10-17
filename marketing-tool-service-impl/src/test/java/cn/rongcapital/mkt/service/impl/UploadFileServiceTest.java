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
public class UploadFileServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private UploadFileService uploadFileService;
    
    
    @Mock
    private ImportDataHistoryDao importDataHistoryDao;
    
    @Mock
    private ImportDataModifyLogDao importDataModifyLogDao;
    
    @Mock
    private IllegalDataDao illegalDataDao;
    
    @Mock
    private WechatQrcodeDao wechatQrcodeDao;
    
    @Mock
    private WechatChannelDao wechatChannelDao;
    
    @Mock
    private WechatRegisterDao wechatRegisterDao;
    
    @Mock
    private WechatQrcodeLogDao wechatQrcodeLogDao;
    
    @Mock
    private WechatQrcodeTicketDao wechatQrcodeTicketDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：UploadFileService 准备---------------------");
        
        
        uploadFileService = new UploadFileServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(uploadFileService, "importDataHistoryDao", importDataHistoryDao);
        ReflectionTestUtils.setField(uploadFileService, "importDataModifyLogDao", importDataModifyLogDao);
        ReflectionTestUtils.setField(uploadFileService, "illegalDataDao", illegalDataDao);
        ReflectionTestUtils.setField(uploadFileService, "wechatQrcodeDao", wechatQrcodeDao);
        ReflectionTestUtils.setField(uploadFileService, "wechatChannelDao", wechatChannelDao);
        ReflectionTestUtils.setField(uploadFileService, "wechatRegisterDao", wechatRegisterDao);
        ReflectionTestUtils.setField(uploadFileService, "wechatQrcodeLogDao", wechatQrcodeLogDao);
        ReflectionTestUtils.setField(uploadFileService, "wechatQrcodeTicketDao", wechatQrcodeTicketDao);
        
    }
    
    @Test
    public void testIsUTF8() {
        logger.info("测试方法: isUTF8 ");
    }
    
    @Test
    public void testUploadFile() {
        logger.info("测试方法: uploadFile ");
    }
    
    @Test
    public void testUploadRepairFile() {
        logger.info("测试方法: uploadRepairFile ");
    }
    
    @Test
    public void testRepairDataLength() {
        logger.info("测试方法: repairDataLength ");
    }
    
    @Test
    public void testUploadFileBatch() {
        logger.info("测试方法: uploadFileBatch ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：UploadFileService 结束---------------------");
    }

}


