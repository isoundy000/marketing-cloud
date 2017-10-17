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
import cn.rongcapital.mkt.dao.TaskScheduleDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class TaskScheduleDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private TaskScheduleDao taskScheduleDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: TaskScheduleDao 开始---------------------");
    }
    
	@Test
	public void testDeActivateTaskByCampaignHeadId() {
	    logger.info("测试方法: deActivateTaskByCampaignHeadId ");    
	}
	
	@Test
	public void testPhysicalDeleteById() {
	    logger.info("测试方法: physicalDeleteById ");    
	}
	
	@Test
	public void testActivateTaskByCampaignHeadId() {
	    logger.info("测试方法: activateTaskByCampaignHeadId ");    
	}
	
	@Test
	public void testSelectIdByServiceName() {
	    logger.info("测试方法: selectIdByServiceName ");    
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
        logger.info("测试: TaskScheduleDao 结束---------------------");
    }
}