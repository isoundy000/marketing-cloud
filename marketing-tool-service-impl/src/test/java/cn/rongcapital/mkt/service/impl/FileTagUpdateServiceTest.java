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
public class FileTagUpdateServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private FileTagUpdateService fileTagUpdateService;
    
    
    @Mock
    private ImportDataHistoryDao importDataHistoryDao;
    
    @Mock
    private CustomTagDao customTagDao;
    
    @Mock
    private CustomTagMapDao customTagMapDao;
    
    @Mock
    private OriginalDataPopulationDao originalDataPopulationDao;
    
    @Mock
    private OriginalDataCustomerTagsDao originalDataCustomerTagsDao;
    
    @Mock
    private OriginalDataArchPointDao originalDataArchPointDao;
    
    @Mock
    private OriginalDataMemberDao originalDataMemberDao;
    
    @Mock
    private OriginalDataLoginDao originalDataLoginDao;
    
    @Mock
    private OriginalDataPaymentDao originalDataPaymentDao;
    
    @Mock
    private OriginalDataShoppingDao originalDataShoppingDao;
    
    @Mock
    private CustomTagOriginalDataMapDao customTagOriginalDataMapDao;
    
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：FileTagUpdateService 准备---------------------");
        
        
        fileTagUpdateService = new FileTagUpdateServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(fileTagUpdateService, "importDataHistoryDao", importDataHistoryDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "customTagDao", customTagDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "customTagMapDao", customTagMapDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "originalDataPopulationDao", originalDataPopulationDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "originalDataCustomerTagsDao", originalDataCustomerTagsDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "originalDataArchPointDao", originalDataArchPointDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "originalDataMemberDao", originalDataMemberDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "originalDataLoginDao", originalDataLoginDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "originalDataPaymentDao", originalDataPaymentDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "originalDataShoppingDao", originalDataShoppingDao);
        ReflectionTestUtils.setField(fileTagUpdateService, "customTagOriginalDataMapDao", customTagOriginalDataMapDao);
        
    }
    
    @Test
    public void testUpdateFileTag() {
        logger.info("测试方法: updateFileTag ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：FileTagUpdateService 结束---------------------");
    }

}


