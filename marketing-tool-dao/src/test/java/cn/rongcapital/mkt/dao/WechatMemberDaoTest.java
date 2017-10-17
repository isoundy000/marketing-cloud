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
import cn.rongcapital.mkt.dao.WechatMemberDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class WechatMemberDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatMemberDao wechatMemberDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: WechatMemberDao 开始---------------------");
    }
    
	@Test
	public void testDeleteFansByVO() {
	    logger.info("测试方法: deleteFansByVO ");    
	}
	
	@Test
	public void testSelectSearchInfo() {
	    logger.info("测试方法: selectSearchInfo ");    
	}
	
	@Test
	public void testBatchInsertFans() {
	    logger.info("测试方法: batchInsertFans ");    
	}
	
	@Test
	public void testBatchInsertGroupMember() {
	    logger.info("测试方法: batchInsertGroupMember ");    
	}
	
	@Test
	public void testSelectIdListByGroupId() {
	    logger.info("测试方法: selectIdListByGroupId ");    
	}
	
	@Test
	public void testSelectPeopleDetails() {
	    logger.info("测试方法: selectPeopleDetails ");    
	}
	
	@Test
	public void testUpdateSyncDataMark() {
	    logger.info("测试方法: updateSyncDataMark ");    
	}
	
	@Test
	public void testSelectNotSyncWechatMemberList() {
	    logger.info("测试方法: selectNotSyncWechatMemberList ");    
	}
	
	@Test
	public void testDeleteFansByWxcode() {
	    logger.info("测试方法: deleteFansByWxcode ");    
	}
	
	@Test
	public void testSelectedNotSyncCount() {
	    logger.info("测试方法: selectedNotSyncCount ");    
	}
	
	@Test
	public void testSelectGroupMemeberCount() {
	    logger.info("测试方法: selectGroupMemeberCount ");    
	}
	
	@Test
	public void testSelectIdByPubIdAndOpenId() {
	    logger.info("测试方法: selectIdByPubIdAndOpenId ");    
	}
	
	@Test
	public void testSelectIdByGroupIdAndWxAcct() {
	    logger.info("测试方法: selectIdByGroupIdAndWxAcct ");    
	}
	
	@Test
	public void testBatchInsertContacts() {
	    logger.info("测试方法: batchInsertContacts ");    
	}
	
	@Test
	public void testDeleteRecordByUin() {
	    logger.info("测试方法: deleteRecordByUin ");    
	}
	
	@Test
	public void testSelectKeyidListByIdList() {
	    logger.info("测试方法: selectKeyidListByIdList ");    
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
        logger.info("测试: WechatMemberDao 结束---------------------");
    }
}