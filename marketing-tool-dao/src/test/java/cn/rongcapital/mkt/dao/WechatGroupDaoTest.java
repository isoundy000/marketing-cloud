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
import cn.rongcapital.mkt.dao.WechatGroupDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class WechatGroupDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatGroupDao wechatGroupDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: WechatGroupDao 开始---------------------");
    }
    
	@Test
	public void testUpdateInfoById() {
	    logger.info("测试方法: updateInfoById ");    
	}
	
	@Test
	public void testSelectGroupId() {
	    logger.info("测试方法: selectGroupId ");    
	}
	
	@Test
	public void testInsertWechatGroup() {
	    logger.info("测试方法: insertWechatGroup ");    
	}
	
	@Test
	public void testSelectFirstImportGroupList() {
	    logger.info("测试方法: selectFirstImportGroupList ");    
	}
	
	@Test
	public void testUpdateStatusByWxAcct() {
	    logger.info("测试方法: updateStatusByWxAcct ");    
	}
	
	@Test
	public void testUpdateInfoByGroupWxCode() {
	    logger.info("测试方法: updateInfoByGroupWxCode ");    
	}
	
	@Test
	public void testSelectNewGroupList() {
	    logger.info("测试方法: selectNewGroupList ");    
	}
	
	@Test
	public void testInsertWechatGroupByUcode() {
	    logger.info("测试方法: insertWechatGroupByUcode ");    
	}
	
	@Test
	public void testSelectGroupIdByUcode() {
	    logger.info("测试方法: selectGroupIdByUcode ");    
	}
	
	@Test
	public void testDeleteRecordByUin() {
	    logger.info("测试方法: deleteRecordByUin ");    
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
        logger.info("测试: WechatGroupDao 结束---------------------");
    }
}