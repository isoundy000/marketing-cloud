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
import cn.rongcapital.mkt.dao.CustomTagMapDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomTagMapDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CustomTagMapDao customTagMapDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: CustomTagMapDao 开始---------------------");
    }
    
	@Test
	public void testSelectCustomTagMapByTagNameandMapId() {
	    logger.info("测试方法: selectCustomTagMapByTagNameandMapId ");    
	}
	
	@Test
	public void testBatchInsert() {
	    logger.info("测试方法: batchInsert ");    
	}
	
	@Test
	public void testSelectTagIdList() {
	    logger.info("测试方法: selectTagIdList ");    
	}
	
	@Test
	public void testGetTagUseHeadId() {
	    logger.info("测试方法: getTagUseHeadId ");    
	}
	
	@Test
	public void testSelectCustomTagMapByTagName() {
	    logger.info("测试方法: selectCustomTagMapByTagName ");    
	}
	
	@Test
	public void testBatchDeleteUseTagId() {
	    logger.info("测试方法: batchDeleteUseTagId ");    
	}
	
	@Test
	public void testDeleteCustomTagMapByMapId() {
	    logger.info("测试方法: deleteCustomTagMapByMapId ");    
	}
	
	@Test
	public void testBatchDeleteUseHeadId() {
	    logger.info("测试方法: batchDeleteUseHeadId ");    
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
	
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: CustomTagMapDao 结束---------------------");
    }
}