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
public class DataGetMainListServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private DataGetMainListService dataGetMainListService;
    
    
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
        logger.info("测试：DataGetMainListService 准备---------------------");
        
        
        dataGetMainListService = new DataGetMainListServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(dataGetMainListService, "importTemplateDao", importTemplateDao);
        ReflectionTestUtils.setField(dataGetMainListService, "dataPartyDao", dataPartyDao);
        ReflectionTestUtils.setField(dataGetMainListService, "dataPopulationDao", dataPopulationDao);
        ReflectionTestUtils.setField(dataGetMainListService, "dataCustomerTagsDao", dataCustomerTagsDao);
        ReflectionTestUtils.setField(dataGetMainListService, "dataArchPointDao", dataArchPointDao);
        ReflectionTestUtils.setField(dataGetMainListService, "dataMemberDao", dataMemberDao);
        ReflectionTestUtils.setField(dataGetMainListService, "dataLoginDao", dataLoginDao);
        ReflectionTestUtils.setField(dataGetMainListService, "dataPaymentDao", dataPaymentDao);
        ReflectionTestUtils.setField(dataGetMainListService, "dataShoppingDao", dataShoppingDao);
        
    }
    
    @Test
    public void testGetMainList() {
        logger.info("测试方法: getMainList ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：DataGetMainListService 结束---------------------");
    }

}


