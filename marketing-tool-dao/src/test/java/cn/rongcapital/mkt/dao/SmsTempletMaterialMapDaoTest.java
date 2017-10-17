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
import cn.rongcapital.mkt.dao.DataShoppingDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class SmsTempletMaterialMapDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SmsTempletMaterialMapDao smsTempletMaterialMapDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: DataShoppingDao 开始---------------------");
    }
    
	@Test
	public void testDeleteByTempletId() {
	    logger.info("测试方法: deleteByTempletId ");  
	    
	    smsTempletMaterialMapDao.deleteByTempletId(56);
	    
	}
	
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: DataShoppingDao 结束---------------------");
    }
}