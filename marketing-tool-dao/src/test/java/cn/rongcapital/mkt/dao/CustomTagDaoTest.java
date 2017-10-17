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
import cn.rongcapital.mkt.dao.CustomTagDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomTagDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CustomTagDao customTagDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: CustomTagDao 开始---------------------");
    }
    
	@Test
	public void testBatchInsert() {
	    logger.info("测试方法: batchInsert ");    
	}
	
	@Test
	public void testSelectIdByName() {
	    logger.info("测试方法: selectIdByName ");    
	}
	
	@Test
	public void testDelecteCustomTagByContactId() {
	    logger.info("测试方法: delecteCustomTagByContactId ");    
	}
	
	@Test
	public void testSelectIdsByCustomTags() {
	    logger.info("测试方法: selectIdsByCustomTags ");    
	}
	
	@Test
	public void testSelectCustomTagsByIds() {
	    logger.info("测试方法: selectCustomTagsByIds ");    
	}
	
	@Test
	public void testInsertCustomTagResId() {
	    logger.info("测试方法: insertCustomTagResId ");    
	}
	
	@Test
	public void testIncreaseCoverAudienceCount() {
	    logger.info("测试方法: increaseCoverAudienceCount ");    
	}
	
	@Test
	public void testSelectByNameFuzzy() {
	    logger.info("测试方法: selectByNameFuzzy ");    
	}
	
	@Test
	public void testSelectListUndeleteTags() {
	    logger.info("测试方法: selectListUndeleteTags ");    
	}
	
	@Test
	public void testSelectListforUpdate() {
	    logger.info("测试方法: selectListforUpdate ");    
	}
	
	@Test
	public void testSelectTagsByContactId() {
	    logger.info("测试方法: selectTagsByContactId ");    
	}
	
	@Test
	public void testLogicDeleteTagById() {
	    logger.info("测试方法: logicDeleteTagById ");    
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
        logger.info("测试: CustomTagDao 结束---------------------");
    }
}