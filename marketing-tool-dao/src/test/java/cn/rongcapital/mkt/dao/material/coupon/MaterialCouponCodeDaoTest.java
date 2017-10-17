/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: guozhenchao
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/
package cn.rongcapital.mkt.dao.material.coupon;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponCodeDaoTest  extends AbstractUnitTest{

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: MaterialCouponCodeDao 开始---------------------");
        MaterialCouponCode code = new MaterialCouponCode();
        code.setCouponId(100L);
        code.setCode("1211111");
        code.setReleaseStatus("unreleased");
        materialCouponCodeDao.insert(code);
    }
    
    @Test
    public void testSelectList() {
        logger.info("测试方法: testSelectList ");
        MaterialCouponCode code = new MaterialCouponCode();
        code.setCouponId(100L);
        code.setCode("1211111");
        List<MaterialCouponCode> list = materialCouponCodeDao.selectList(code);
        Assert.assertEquals(true, list.size() > 0);
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: MaterialCouponCodeDao 结束---------------------");
    }
}
