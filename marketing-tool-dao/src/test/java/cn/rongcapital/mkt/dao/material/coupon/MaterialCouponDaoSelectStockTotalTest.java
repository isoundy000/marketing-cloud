/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/
package cn.rongcapital.mkt.dao.material.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.common.enums.MaterialCouponChannelCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponSourceCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponTypeEnum;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponDaoSelectStockTotalTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaterialCouponDao materialCouponDao;

    private static final Date now = new Date();
    
    private Long couponId;
    
	@Before
	public void setUp() throws Exception {
		logger.info("测试: MaterialCouponDao 开始---------------------");
	}

	@Test
	public void testSelectStockTotalByCouponId() {
		logger.info("测试方法: selectStockTotalByCouponId ");
		
		MaterialCoupon mc = new MaterialCoupon();
		mc.setTitle("方法selectStockTotalByCouponId测试用数据" + Math.random() * 1000);
		mc.setSourceCode(MaterialCouponSourceCodeEnum.GENERATE.getCode());
		mc.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
		mc.setCouponStatus(MaterialCouponStatusEnum.RELEASED.getCode());
		mc.setStockRest(2000);
		mc.setAmount(new BigDecimal("10.1"));
		mc.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
	    mc.setStartTime(now);
	    mc.setEndTime(new Date());
		mc.setStatus((byte) 0);
		mc.setStockTotal(314159265);
		
        List<MaterialCoupon> mcl = materialCouponDao.selectList(mc);
        if (mcl.size() == 0) {
            materialCouponDao.insert(mc);
        } else {
            mc.setId(mcl.get(0).getId());
        }
        couponId = mc.getId();
        
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("id",  couponId);
		Long result = materialCouponDao.selectStockTotalByCouponId(paramMap);
		Assert.assertEquals(314159265, result.longValue());
		
		mc.setStatus((byte) 1);
		materialCouponDao.updateById(mc);
		
		logger.info("测试方法: selectStockTotalByCouponId ");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试: MaterialCouponDao 结束---------------------");
	}
}
