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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponSourceCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponTypeEnum;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponCodeDaoGetCCVerifyUserInfoList extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    private static final Date now = new Date();

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private Long couponId;

    private List<MaterialCouponCode> mockList = new ArrayList<MaterialCouponCode>();

    private MaterialCoupon coupon = new MaterialCoupon();

    private Map<String, String> userInfoMap = new HashMap<String, String>();

    @Before
    public void setUp() throws Exception {
        logger.info("测试: MaterialCouponCodeDao getCouponCodeVerifyUserInfoList 开始---------------------");
        logger.info("测试: MaterialCouponCodeDao 插入测试数据");
        coupon.setSourceCode(MaterialCouponSourceCodeEnum.COMMON.getCode());
        coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
        coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
        coupon.setTitle("UT方法getCouponCodeVerifyUserInfoList测试数据");
        coupon.setCouponStatus(MaterialCouponStatusEnum.RELEASED.getCode());
        coupon.setStatus(Byte.valueOf("0"));
        coupon.setStartTime(now);
        coupon.setEndTime(now);
        coupon.setAmount(BigDecimal.valueOf(10));
        coupon.setStockTotal(314159265);
        coupon.setStockRest(2000);
        coupon.setReadyStatus("ready");

        List<MaterialCoupon> dataList = materialCouponDao.selectList(coupon);
        if (dataList.size() == 0) {
            materialCouponDao.insert(coupon);
        } else {
            coupon.setId(dataList.get(0).getId());
        }
        couponId = coupon.getId();

        // received verified status(0/1) user
        MaterialCouponCode mcc1 = new MaterialCouponCode();
        mcc1.setCouponId(couponId);
        mcc1.setCode("UT测试数据" + UUID.randomUUID().toString().substring(0, 10));
        mcc1.setUser("1用户信息" + UUID.randomUUID().toString().substring(0, 20));
        mcc1.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mcc1.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode());
        mcc1.setStatus((byte) 0);
        List<MaterialCouponCode> couponCodeList = materialCouponCodeDao.selectList(mcc1);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(mcc1);
        }
        mockList.add(mcc1);
        userInfoMap.put("1用户信息", mcc1.getUser());

        mcc1 = new MaterialCouponCode();
        mcc1.setCouponId(couponId);
        mcc1.setCode("UT测试数据" + UUID.randomUUID().toString().substring(0, 10));
        mcc1.setUser("2用户信息" + UUID.randomUUID().toString().substring(0, 20));
        mcc1.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mcc1.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.FAIL.getCode());
        mcc1.setStatus((byte) 0);
        couponCodeList = materialCouponCodeDao.selectList(mcc1);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(mcc1);
        }
        mockList.add(mcc1);
        userInfoMap.put("2用户信息", mcc1.getUser());

        mcc1 = new MaterialCouponCode();
        mcc1.setCouponId(couponId);
        mcc1.setCode("UT测试数据" + UUID.randomUUID().toString().substring(0, 10));
        mcc1.setUser("3用户信息" + UUID.randomUUID().toString().substring(0, 20));
        mcc1.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mcc1.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mcc1.setStatus((byte) 0);
        couponCodeList = materialCouponCodeDao.selectList(mcc1);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(mcc1);
        }
        mockList.add(mcc1);
        userInfoMap.put("3用户信息", mcc1.getUser());

        mcc1 = new MaterialCouponCode();
        mcc1.setCouponId(couponId);
        mcc1.setCode("UT测试数据" + UUID.randomUUID().toString().substring(0, 10));
        mcc1.setUser("4用户信息" + UUID.randomUUID().toString().substring(0, 20));
        mcc1.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode());
        mcc1.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mcc1.setStatus((byte) 0);
        couponCodeList = materialCouponCodeDao.selectList(mcc1);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(mcc1);
        }
        mockList.add(mcc1);
        userInfoMap.put("4用户信息", mcc1.getUser());

        mcc1 = new MaterialCouponCode();
        mcc1.setCouponId(couponId);
        mcc1.setCode("UT测试数据" + UUID.randomUUID().toString().substring(0, 10));
        mcc1.setUser("5用户信息" + UUID.randomUUID().toString().substring(0, 20));
        mcc1.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
        mcc1.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mcc1.setStatus((byte) 0);
        couponCodeList = materialCouponCodeDao.selectList(mcc1);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(mcc1);
        }
        mockList.add(mcc1);
        userInfoMap.put("5用户信息", mcc1.getUser());

        mcc1 = new MaterialCouponCode();
        mcc1.setCouponId(couponId);
        mcc1.setCode("UT测试数据" + UUID.randomUUID().toString().substring(0, 10));
        mcc1.setUser("6用户信息" + UUID.randomUUID().toString().substring(0, 20));
        mcc1.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
        mcc1.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mcc1.setStatus((byte) 1);
        couponCodeList = materialCouponCodeDao.selectList(mcc1);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(mcc1);
        }
        mockList.add(mcc1);
        userInfoMap.put("6用户信息", mcc1.getUser());
    }

    @Test
    public void testGetCouponCodeVerifyUserInfoList01() {

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id", couponId);
        paramMap.put("user", "1用户信息");
        paramMap.put("verifyStatus", MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode());
        List<String> reList = materialCouponCodeDao.getCouponCodeVerifyUserInfoList(paramMap);
        Assert.assertEquals(1, reList.size());
        Assert.assertEquals(userInfoMap.get("1用户信息"), reList.get(0));

    }
    
    @Test
    public void testGetCouponCodeVerifyUserInfoList02() {

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id", couponId);
        paramMap.put("user", "空");
        paramMap.put("verifyStatus", MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        List<String> reList = materialCouponCodeDao.getCouponCodeVerifyUserInfoList(paramMap);
        Assert.assertEquals(0, reList.size());

    }
    
    @Test
    public void testGetCouponCodeVerifyUserInfoList03() {

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id", couponId);
        paramMap.put("releaseStatus", MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
        List<String> reList = materialCouponCodeDao.getCouponCodeVerifyUserInfoList(paramMap);
        Assert.assertEquals(1, reList.size());
        Assert.assertEquals(userInfoMap.get("5用户信息"), reList.get(0));
    }

    @Test
    public void testGetCouponCodeVerifyUserInfoList04() {

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id", couponId);
        paramMap.put("user", "户信");
        paramMap.put("verifyStatus", MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        List<String> reList = materialCouponCodeDao.getCouponCodeVerifyUserInfoList(paramMap);
        Assert.assertEquals(3, reList.size());
        for (String string : reList) {

            if (userInfoMap.get("3用户信息").equals(string)) {
                Assert.assertEquals(userInfoMap.get("3用户信息"), string);
            } else if (userInfoMap.get("4用户信息").equals(string)) {
                Assert.assertEquals(userInfoMap.get("4用户信息"), string);
            } else if (userInfoMap.get("5用户信息").equals(string)) {
                Assert.assertEquals(userInfoMap.get("5用户信息"), string);
            } else {
                Assert.assertFalse(false);
            }
        }
    }

    @Test
    public void testGetCouponCodeVerifyUserInfoList05() {

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id", couponId);
        paramMap.put("user", "户信");
        paramMap.put("verifyStatus", MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        paramMap.put("expireStatus", "expired");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE, 2);
        paramMap.put("expireTime", date.getTime());
        List<String> reList = materialCouponCodeDao.getCouponCodeVerifyUserInfoList(paramMap);
        Assert.assertEquals(3, reList.size());
        for (String string : reList) {

            if (userInfoMap.get("3用户信息").equals(string)) {
                Assert.assertEquals(userInfoMap.get("3用户信息"), string);
            } else if (userInfoMap.get("4用户信息").equals(string)) {
                Assert.assertEquals(userInfoMap.get("4用户信息"), string);
            } else if (userInfoMap.get("5用户信息").equals(string)) {
                Assert.assertEquals(userInfoMap.get("5用户信息"), string);
            } else {
                Assert.assertFalse(false);
            }
        }
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: MaterialCouponCodeDao 删除测试数据 ");
        coupon.setStatus((byte) 1);
        materialCouponDao.updateById(coupon);
        for (MaterialCouponCode mCC : mockList) {
            mCC.setStatus((byte) 1);
            materialCouponCodeDao.updateById(mCC);
        }
        logger.info("测试: MaterialCouponCodeDao getCouponCodeVerifyUserInfoList 结束---------------------");
    }
}
