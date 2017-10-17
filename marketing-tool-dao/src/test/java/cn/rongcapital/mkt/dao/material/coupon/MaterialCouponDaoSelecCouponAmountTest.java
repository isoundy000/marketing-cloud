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
public class MaterialCouponDaoSelecCouponAmountTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaterialCouponDao materialCouponDao;

    private Long couponId;
	
	@Before
	public void setUp() throws Exception {
		logger.info("测试: MaterialCouponDao 开始---------------------");
	}

    private static final Date now = new Date();
	@Test
	public void testSelecCouponAmountByCouponId() {
		logger.info("测试方法: selecCouponAmountByCouponId start");

		MaterialCoupon mc = new MaterialCoupon();
		mc.setTitle("方法selecCouponAmountByCouponId测试用数据" + Math.random() * 1000);
		mc.setSourceCode(MaterialCouponSourceCodeEnum.GENERATE.getCode());
		mc.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
		mc.setCouponStatus(MaterialCouponStatusEnum.RELEASED.getCode());
	    mc.setStockRest(2000);
		mc.setStatus((byte) 0);
		mc.setAmount(new BigDecimal("31415926535.01"));
		mc.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
		mc.setStartTime(now);
        mc.setEndTime(now);
        mc.setStockTotal(314159265);
        
        List<MaterialCoupon> mcl = materialCouponDao.selectList(mc);
        if (mcl.size() == 0) {
            materialCouponDao.insert(mc);
        } else {
            mc.setId(mcl.get(0).getId());
        }
        couponId = mc.getId();
        

		Map<String, Object> paramMap = new HashMap();
		paramMap.put("id", couponId);
		MaterialCoupon re = materialCouponDao.selecCouponAmountByCouponId(paramMap);
		Assert.assertEquals(new BigDecimal("31415926535.01").doubleValue(), re.getAmount().doubleValue(), 0.001);

		mc.setStatus((byte) 1);
		materialCouponDao.updateById(mc);

		logger.info("测试方法: selectCouponTotalByCouponIdAndReleStatus end");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试: MaterialCouponDao 结束---------------------");
	}
}
