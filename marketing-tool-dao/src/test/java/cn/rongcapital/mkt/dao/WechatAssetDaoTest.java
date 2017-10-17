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
import cn.rongcapital.mkt.dao.WechatAssetDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class WechatAssetDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatAssetDao wechatAssetDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: WechatAssetDao 开始---------------------");
    }
    
	@Test
	public void testUpdateByWxacct() {
	    logger.info("测试方法: updateByWxacct ");    
	}
	
	@Test
	public void testUpdateNicknameById() {
	    logger.info("测试方法: updateNicknameById ");    
	}
	
	@Test
	public void testSelectServerAndBookList() {
	    logger.info("测试方法: selectServerAndBookList ");    
	}
	
	@Test
	public void testGetWechatCountByType() {
	    logger.info("测试方法: getWechatCountByType ");    
	}
	
	@Test
	public void testInsertNewRegisterAsset() {
	    logger.info("测试方法: insertNewRegisterAsset ");    
	}
	
	@Test
	public void testDeleteRecordByUin() {
	    logger.info("测试方法: deleteRecordByUin ");    
	}
	
	@Test
	public void testSelectWxAssetList() {
	    logger.info("测试方法: selectWxAssetList ");    
	}
	
	@Test
	public void testSelectMemberCountByType() {
	    logger.info("测试方法: selectMemberCountByType ");    
	}
	
	@Test
	public void testSelectByWechatAccounts() {
	    logger.info("测试方法: selectByWechatAccounts ");    
	}
	
	@Test
	public void testSelectGroupIdsListByType() {
	    logger.info("测试方法: selectGroupIdsListByType ");    
	}
	
	@Test
	public void testSelectWechatAssetDetai() {
	    logger.info("测试方法: selectWechatAssetDetai ");    
	}
	
	@Test
	public void testSelectAssetListByType() {
	    logger.info("测试方法: selectAssetListByType ");    
	}
	
	@Test
	public void testSelectAssetTypeAndWxacct() {
	    logger.info("测试方法: selectAssetTypeAndWxacct ");    
	}
	
	@Test
	public void testUpdateGroupIdsAndTotalCount() {
	    logger.info("测试方法: updateGroupIdsAndTotalCount ");    
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
        logger.info("测试: WechatAssetDao 结束---------------------");
    }
}