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
import cn.rongcapital.mkt.dao.ImgTextAssetDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class ImgTextAssetDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ImgTextAssetDao imgTextAssetDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: ImgTextAssetDao 开始---------------------");
    }
    
	@Test
	public void testSelectListByType() {
	    logger.info("测试方法: selectListByType ");    
	}
	
	@Test
	public void testSelectListByName() {
	    logger.info("测试方法: selectListByName ");    
	}
	
	@Test
	public void testBatchInsertTuwen() {
	    logger.info("测试方法: batchInsertTuwen ");    
	}
	
	@Test
	public void testSelectMenuList() {
	    logger.info("测试方法: selectMenuList ");    
	}
	
	@Test
	public void testInsertWithDate() {
	    logger.info("测试方法: insertWithDate ");    
	}
	
	@Test
	public void testInsertHostImg() {
	    logger.info("测试方法: insertHostImg ");    
	}
	
	@Test
	public void testSelectListAll() {
	    logger.info("测试方法: selectListAll ");    
	}
	
	@Test
	public void testLogicDeleteAssetById() {
	    logger.info("测试方法: logicDeleteAssetById ");    
	}
	
	@Test
	public void testSelectImgtextAssetCountByType() {
	    logger.info("测试方法: selectImgtextAssetCountByType ");    
	}
	
	@Test
	public void testSelectListByNameAndType() {
	    logger.info("测试方法: selectListByNameAndType ");    
	}
	
	@Test
	public void testSelectImgtextIdByMaterialId() {
	    logger.info("测试方法: selectImgtextIdByMaterialId ");    
	}
	
	@Test
	public void testUpdateByIdWithDate() {
	    logger.info("测试方法: updateByIdWithDate ");    
	}
	
	@Test
	public void testBatchUpdateWechatStatusByPubId() {
	    logger.info("测试方法: batchUpdateWechatStatusByPubId ");    
	}
	
	@Test
	public void testBatchDeleteWechatStatusByPubId() {
	    logger.info("测试方法: batchDeleteWechatStatusByPubId ");    
	}
	
	@Test
	public void testSelectListBySearchKeyLike() {
	    logger.info("测试方法: selectListBySearchKeyLike ");    
	}
	
	@Test
	public void testSelectListBySearchKeyLikeCount() {
	    logger.info("测试方法: selectListBySearchKeyLikeCount ");    
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
        logger.info("测试: ImgTextAssetDao 结束---------------------");
    }
}