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
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class WechatAssetGroupDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: WechatAssetGroupDao 开始---------------------");
    }
    
	@Test
	public void testSelectGroupById() {
	    logger.info("测试方法: selectGroupById ");    
	}
	
	@Test
	public void testSelectFriendCount() {
	    logger.info("测试方法: selectFriendCount ");    
	}
	
	@Test
	public void testSelectCountByGroupId() {
	    logger.info("测试方法: selectCountByGroupId ");    
	}
	
	@Test
	public void testSelectGroupIdsByWxAcct() {
	    logger.info("测试方法: selectGroupIdsByWxAcct ");    
	}
	
	@Test
	public void testSelectMemberCountByWxAcct() {
	    logger.info("测试方法: selectMemberCountByWxAcct ");    
	}
	
	@Test
	public void testUpdateByWxacctIGroupId() {
	    logger.info("测试方法: updateByWxacctIGroupId ");    
	}
	
	@Test
	public void testDeleteRecordByUin() {
	    logger.info("测试方法: deleteRecordByUin ");    
	}
	
	@Test
	public void testSumGroupMemberCount() {
	    logger.info("测试方法: sumGroupMemberCount ");    
	}
	
	@Test
	public void testSelectImportGroupIdsByIds() {
	    logger.info("测试方法: selectImportGroupIdsByIds ");    
	}
	
	@Test
	public void testSelectImportGroupIds() {
	    logger.info("测试方法: selectImportGroupIds ");    
	}
	
	@Test
	public void testInsertNewGroupList() {
	    logger.info("测试方法: insertNewGroupList ");    
	}
	
	@Test
	public void testUpdateGroupCountById() {
	    logger.info("测试方法: updateGroupCountById ");    
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
        logger.info("测试: WechatAssetGroupDao 结束---------------------");
    }
}