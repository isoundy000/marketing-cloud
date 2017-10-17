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
import cn.rongcapital.mkt.dao.WechatRegisterDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class WechatRegisterDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatRegisterDao wechatRegisterDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: WechatRegisterDao 开始---------------------");
    }
    
	@Test
	public void testSelectWxacctList() {
	    logger.info("测试方法: selectWxacctList ");    
	}
	
	@Test
	public void testSelectPersonalId() {
	    logger.info("测试方法: selectPersonalId ");    
	}
	
	@Test
	public void testInsertAuthPublic() {
	    logger.info("测试方法: insertAuthPublic ");    
	}
	
	@Test
	public void testSelectStatus() {
	    logger.info("测试方法: selectStatus ");    
	}
	
	@Test
	public void testUpdatePubInfo() {
	    logger.info("测试方法: updatePubInfo ");    
	}
	
	@Test
	public void testSelectNewWxAssetListWhenFirstImported() {
	    logger.info("测试方法: selectNewWxAssetListWhenFirstImported ");    
	}
	
	@Test
	public void testUpdateInforByWxAcct() {
	    logger.info("测试方法: updateInforByWxAcct ");    
	}
	
	@Test
	public void testBatchUpdateStatusList() {
	    logger.info("测试方法: batchUpdateStatusList ");    
	}
	
	@Test
	public void testUpdateConsignationTimeByWxacct() {
	    logger.info("测试方法: updateConsignationTimeByWxacct ");    
	}
	
	@Test
	public void testBatchInsertPersonList() {
	    logger.info("测试方法: batchInsertPersonList ");    
	}
	
	@Test
	public void testSelectNewWxAssetList() {
	    logger.info("测试方法: selectNewWxAssetList ");    
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
        logger.info("测试: WechatRegisterDao 结束---------------------");
    }
}