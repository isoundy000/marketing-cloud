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
public class DataDownloadMainListServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private DataDownloadMainListService dataDownloadMainListService;
    
    
    @Mock
    private ImportTemplateDao importTemplateDao;
    
    @Mock
    private DataPartyDao dataPartyDao;
    
    @Mock
    private DataPopulationDao dataPopulationDao;
    
    @Mock
    private DataCustomerTagsDao dataCustomerTagsDao;
    
    @Mock
    private DataArchPointDao dataArchPointDao;
    
    @Mock
    private DataMemberDao dataMemberDao;
    
    @Mock
    private DataLoginDao dataLoginDao;
    
    @Mock
    private DataPaymentDao dataPaymentDao;
    
    @Mock
    private DataShoppingDao dataShoppingDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：DataDownloadMainListService 准备---------------------");
        
        
        dataDownloadMainListService = new DataDownloadMainListServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(dataDownloadMainListService, "importTemplateDao", importTemplateDao);
        ReflectionTestUtils.setField(dataDownloadMainListService, "dataPartyDao", dataPartyDao);
        ReflectionTestUtils.setField(dataDownloadMainListService, "dataPopulationDao", dataPopulationDao);
        ReflectionTestUtils.setField(dataDownloadMainListService, "dataCustomerTagsDao", dataCustomerTagsDao);
        ReflectionTestUtils.setField(dataDownloadMainListService, "dataArchPointDao", dataArchPointDao);
        ReflectionTestUtils.setField(dataDownloadMainListService, "dataMemberDao", dataMemberDao);
        ReflectionTestUtils.setField(dataDownloadMainListService, "dataLoginDao", dataLoginDao);
        ReflectionTestUtils.setField(dataDownloadMainListService, "dataPaymentDao", dataPaymentDao);
        ReflectionTestUtils.setField(dataDownloadMainListService, "dataShoppingDao", dataShoppingDao);
        
    }
    
    @Test
    public void testTransferNameListtoMap() {
        logger.info("测试方法: transferNameListtoMap ");
    }
    
    @Test
    public void testDownloadMainList() {
        logger.info("测试方法: downloadMainList ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：DataDownloadMainListService 结束---------------------");
    }

}


