/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class DataCustomerTagsDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: DataCustomerTagsDao 开始---------------------");
    }
    
	@Test
	public void testCleanAndUpdateByOriginal() {
	    logger.info("测试方法: cleanAndUpdateByOriginal ");    
	}
	
	@Test
	public void testUpdateStatusByIds() {
	    logger.info("测试方法: updateStatusByIds ");    
	}
	
	@Test
	public void testInsert() {
	    logger.info("测试方法: insert ");    
	}
	
	@Test
	public void testSelectListCount() {
	    logger.info("测试方法: selectListCount ");    
	}
	
	@Test
	public void testUpdateById() {
	    logger.info("测试方法: updateById ");    
	}
	
	@Test
	public void testSelectList() {
	    logger.info("测试方法: selectList ");    
	}
	
	@Test
	public void testSelectListByIdList() {
	    logger.info("测试方法: selectListByIdList ");    
	}
	
	@Test
	public void testSelectMobileById() {
	    logger.info("测试方法: selectMobileById ");    
	}
	
	@Test
	public void testSelectByBatchId() {
	    logger.info("测试方法: selectByBatchId ");    
	}
	
	@Test
	public void testSelectColumns() {
	    logger.info("测试方法: selectColumns ");    
	}
	
	@Test
	public void testSelectObjectById() {
	    logger.info("测试方法: selectObjectById ");    
	}
	
	@Test
	public void testSelectMappingKeyIds() {
	    logger.info("测试方法: selectMappingKeyIds ");    
	}
	
	@Test
	public void testSelectByMappingKeyId() {
	    logger.info("测试方法: selectByMappingKeyId ");    
	}
	
	@Test
	public void testSelectCountByBatchId() {
	    logger.info("测试方法: selectCountByBatchId ");    
	}
	
	@Test
	public void testGetAudiencesCount() {
	    logger.info("测试方法: getAudiencesCount ");    
	}
	
	@Test
	public void testSelectMappingKeyId() {
	    logger.info("测试方法: selectMappingKeyId ");    
	}
	
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: DataCustomerTagsDao 结束---------------------");
    }
}