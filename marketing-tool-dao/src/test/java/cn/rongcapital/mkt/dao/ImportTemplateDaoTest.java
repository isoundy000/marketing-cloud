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
import cn.rongcapital.mkt.dao.ImportTemplateDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class ImportTemplateDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ImportTemplateDao importTemplateDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: ImportTemplateDao 开始---------------------");
    }
    
	@Test
	public void testSelectFileColumn() {
	    logger.info("测试方法: selectFileColumn ");    
	}
	
	@Test
	public void testSelectTemplateList() {
	    logger.info("测试方法: selectTemplateList ");    
	}
	
	@Test
	public void testSelectViewListByTemplType() {
	    logger.info("测试方法: selectViewListByTemplType ");    
	}
	
	@Test
	public void testUpdateByTemplNameandFieldName() {
	    logger.info("测试方法: updateByTemplNameandFieldName ");    
	}
	
	@Test
	public void testSelectTempleNameByType() {
	    logger.info("测试方法: selectTempleNameByType ");    
	}
	
	@Test
	public void testSelectTemplTypePairs() {
	    logger.info("测试方法: selectTemplTypePairs ");    
	}
	
	@Test
	public void testSelectSelectedTemplateList() {
	    logger.info("测试方法: selectSelectedTemplateList ");    
	}
	
	@Test
	public void testUpdateSelectedByTemplType() {
	    logger.info("测试方法: updateSelectedByTemplType ");    
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
        logger.info("测试: ImportTemplateDao 结束---------------------");
    }
}