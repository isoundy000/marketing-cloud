/*************************************************
 * @功能简述: Service实现测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;


import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.service.testbase.AbstractUnitTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

@RunWith(SpringJUnit4ClassRunner.class)
public class SegmentManageCalServiceTest  extends AbstractUnitTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SegmentManageCalService segmentManageCalService;
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：SegmentManageCalService 准备---------------------");
        
        segmentManageCalService = new SegmentManageCalServiceImpl();
        
    }
    
    
    @Test
    public void testSinterstore() {
        logger.info("测试方法: sinterstore ");
        
    }
    
    @Test
    public void testSunionstore() {
        logger.info("测试方法: sunionstore ");
    }
    
    @Test
    public void testScard() {
        logger.info("测试方法: scard ");
    }
    
    @Test
    public void testDeleteTempKey() {
        logger.info("测试方法: deleteTempKey ");
    }

}


