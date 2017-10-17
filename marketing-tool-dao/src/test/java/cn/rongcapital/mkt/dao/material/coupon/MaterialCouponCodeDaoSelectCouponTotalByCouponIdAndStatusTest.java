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

import java.util.ArrayList;
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

import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.po.MeterialCouponCodeCountByStatus;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponCodeDaoSelectCouponTotalByCouponIdAndStatusTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaterialCouponCodeDao materialCouponCodeDao;
	
	private List<MaterialCouponCode> mockList = new ArrayList<MaterialCouponCode>();

	@Before
	public void setUp() throws Exception {
		logger.info("测试: MaterialCouponDao 开始---------------------");
		
		MaterialCouponCode mcc1 = new MaterialCouponCode();
		mcc1.setCouponId(99999999999999l);
		mcc1.setCode("UT测试数据" +(int) (Math.random() * 10000000));
		mcc1.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
		mcc1.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode());
		mcc1.setStatus((byte) 0);
		mcc1.setUser("18600000000");
        List<MaterialCouponCode> mcl = materialCouponCodeDao.selectList(mcc1);
        if (mcl.size() == 0) {
            materialCouponCodeDao.insert(mcc1);
        } else {
            mcc1.setId(mcl.get(0).getId());
        }
        mockList.add(mcc1);
        
		MaterialCouponCode mcc2 = new MaterialCouponCode();
		mcc2.setCouponId(99999999999999l);
		mcc2.setCode("UT测试数据" +(int) (Math.random() * 10000000));
		mcc2.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode());
		mcc2.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
		mcc2.setStatus((byte) 0);
		mcc2.setUser("18600000001");
        List<MaterialCouponCode> mc2 = materialCouponCodeDao.selectList(mcc2);
        if (mc2.size() == 0) {
            materialCouponCodeDao.insert(mcc2);
        } else {
            mcc2.setId(mcl.get(0).getId());
        }
		mockList.add(mcc2);
		
		MaterialCouponCode mcc3 = new MaterialCouponCode();
		mcc3.setCouponId(99999999999999l);
		mcc3.setCode("UT测试数据" +(int) (Math.random() * 10000000));
		mcc3.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
		mcc3.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
		mcc3.setStatus((byte) 0);
	    mcc3.setUser("18600000002");
        List<MaterialCouponCode> mc3 = materialCouponCodeDao.selectList(mcc3);
        if (mc3.size() == 0) {
            materialCouponCodeDao.insert(mcc3);
        } else {
            mcc3.setId(mcl.get(0).getId());
        }
	    mockList.add(mcc3);
		
		MaterialCouponCode mcc4 = new MaterialCouponCode();
		mcc4.setCouponId(99999999999999l);
		mcc4.setCode("UT测试数据" +(int) (Math.random() * 10000000));
		mcc4.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
		mcc4.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.FAIL.getCode());
		mcc4.setStatus((byte) 0);
		mcc4.setUser("18600000003");
        List<MaterialCouponCode> mc4 = materialCouponCodeDao.selectList(mcc4);
        if (mc4.size() == 0) {
            materialCouponCodeDao.insert(mcc4);
        } else {
            mcc4.setId(mcl.get(0).getId());
        }
		mockList.add(mcc4);
		
	}

	@Test
	public void testSelectCouponTotalByCouponIdAndReleStatus() {
		logger.info("测试方法: selectCouponTotalByCouponIdAndReleStatus ");
		
		Map<String, Object> paramMap = new HashMap();
		List<String> list = new ArrayList();
		list.add(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
		list.add(MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode());
		paramMap.put("releaseStatusList", list);
		paramMap.put("id", 99999999999999l);

		List<MeterialCouponCodeCountByStatus> result = materialCouponCodeDao
				.selectCouponTotalByCouponIdAndReleStatus(paramMap);
		Long receivedValue = -1l;
		Long unreceivedValue = -1l;
		for (MeterialCouponCodeCountByStatus mcs : result) {
			if (MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode().equals(mcs.getStatus())) {
				receivedValue = mcs.getCount();
			} else if (MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode().equals(mcs.getStatus())) {
				unreceivedValue = mcs.getCount();
			}
		}

		Assert.assertEquals(3, receivedValue.longValue());
		Assert.assertEquals(1, unreceivedValue.longValue());
		

		
		logger.info("测试方法: selectCouponTotalByCouponIdAndReleStatus ");
	}

	@Test
	public void testselectCouponTotalByCouponIdAndVeriStatus() {
		logger.info("测试方法: selectCouponTotalByCouponIdAndVeriStatus ");
		Map<String, Object> paramMap = new HashMap();
		List<String> list = new ArrayList();
		list.add(MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode());
		paramMap.put("verifyStatusList", list);
		paramMap.put("id", 99999999999999l);

		List<MeterialCouponCodeCountByStatus> result = materialCouponCodeDao
				.selectCouponTotalByCouponIdAndVeriStatus(paramMap);
		Long verifiedValue = -1l;
		for (MeterialCouponCodeCountByStatus mcs : result) {
			if (MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode().equals(mcs.getStatus())) {
				verifiedValue = mcs.getCount();
			}
		}

		Assert.assertEquals(1, verifiedValue.longValue());

		logger.info("测试方法: selectCouponTotalByCouponIdAndVeriStatus ");
	}

	@After
	public void tearDown() throws Exception {
		
		for (MaterialCouponCode mcc : mockList) {
			mcc.setStatus((byte) 1);
			materialCouponCodeDao.updateById(mcc);
		}
		logger.info("测试: MaterialCouponDao 结束---------------------");
	}

}