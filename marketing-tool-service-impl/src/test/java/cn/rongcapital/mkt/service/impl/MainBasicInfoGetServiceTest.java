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
public class MainBasicInfoGetServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private MainBasicInfoGetService mainBasicInfoGetService;
    
    
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
    
    @Mock
    private DataPartyDao dataPartyDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：MainBasicInfoGetService 准备---------------------");
        
        
        mainBasicInfoGetService = new MainBasicInfoGetServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(mainBasicInfoGetService, "dataPopulationDao", dataPopulationDao);
        ReflectionTestUtils.setField(mainBasicInfoGetService, "dataCustomerTagsDao", dataCustomerTagsDao);
        ReflectionTestUtils.setField(mainBasicInfoGetService, "dataArchPointDao", dataArchPointDao);
        ReflectionTestUtils.setField(mainBasicInfoGetService, "dataMemberDao", dataMemberDao);
        ReflectionTestUtils.setField(mainBasicInfoGetService, "dataLoginDao", dataLoginDao);
        ReflectionTestUtils.setField(mainBasicInfoGetService, "dataPaymentDao", dataPaymentDao);
        ReflectionTestUtils.setField(mainBasicInfoGetService, "dataShoppingDao", dataShoppingDao);
        ReflectionTestUtils.setField(mainBasicInfoGetService, "dataPartyDao", dataPartyDao);
        
    }
    
    @Test
    public void testGetKeyIdFromContactInfo() {
        logger.info("测试方法: getKeyIdFromContactInfo ");
    }
    
    @Test
    public void testGetMainBasicInfo() {
        logger.info("测试方法: getMainBasicInfo ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：MainBasicInfoGetService 结束---------------------");
    }

}


