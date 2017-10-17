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
import cn.rongcapital.mkt.dao.TaggroupDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class TaggroupDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private TaggroupDao taggroupDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: TaggroupDao 开始---------------------");
    }
    
	@Test
	public void testSelectNameById() {
	    logger.info("测试方法: selectNameById ");    
	}
	
	@Test
	public void testSelectTaggroupSystemMenulist() {
	    logger.info("测试方法: selectTaggroupSystemMenulist ");    
	}
	
	@Test
	public void testSelectSubNodesByGroupName() {
	    logger.info("测试方法: selectSubNodesByGroupName ");    
	}
	
	@Test
	public void testSelectSystemTagCount() {
	    logger.info("测试方法: selectSystemTagCount ");    
	}
	
	@Test
	public void testSelectByNameFuzzy() {
	    logger.info("测试方法: selectByNameFuzzy ");    
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
        logger.info("测试: TaggroupDao 结束---------------------");
    }
}