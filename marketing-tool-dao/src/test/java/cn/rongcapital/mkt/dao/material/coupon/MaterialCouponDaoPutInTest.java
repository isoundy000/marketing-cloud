/*************************************************
 * @功能及特点的描述简述: 返回投放优惠券统计 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC
 * @author: liuhaizhan
 * @version: 版本
 * @date(创建、开发日期)：2016年12月9日 最后修改日期：2016年12月9日 @复审人:
 *************************************************/
package cn.rongcapital.mkt.dao.material.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponDaoPutInTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    MaterialCoupon mc = null;

    MaterialCouponCode mcc = null;

    // 先插入数据然后再测试
    @Before
    public void insertData() {
        mc = new MaterialCoupon();
        mc.setTitle("LiuHaizhanPutIntest");
        mc.setSourceCode("common");
        mc.setType("voucher");
        mc.setCouponStatus(MaterialCouponStatusEnum.USED.getCode());
        mc.setChannelCode("sms");
        mc.setStatus((byte) 0);
        mc.setStockTotal(1500);
        mc.setAmount(new BigDecimal(10));// 金额;
        mc.setStockRest(200);;// 库存数量
        mc.setStartTime(new Date());
        mc.setReadyStatus("ready");
        mc.setEndTime(new Date());
        materialCouponDao.insert(mc);

        mcc = new MaterialCouponCode();

        mcc.setCouponId(mc.getId());
        mcc.setCode("1333");
        mcc.setUser("13842821032");
        mcc.setVerifyStatus("unverify");
        mcc.setStatus((byte)0);
        mcc.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode());
        // 未收到
        materialCouponCodeDao.insert(mcc);

        mcc.setCouponId(mc.getId());
        mcc.setCode("aaaauuu");
        mcc.setStatus((byte)0);
        mcc.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mcc.setVerifyStatus("verified");

        materialCouponCodeDao.insert(mcc);

    }

    @Test
    public void test() {

        MaterialCoupon m = materialCouponDao.selectOneCoupon(mc.getId());
        Assert.assertEquals(mc.getStatus().toString(), m.getStatus().toString());
        List<Map> map = materialCouponCodeDao.getCouponPutInCount(mc.getId());// 投放统计
        for (Map data : map) {
            // Assert.assertEquals(mcc, data.get("status").toString());
            logger.info(data.get("status").toString());
            logger.info(data.get("cnt").toString());
        }

        map = materialCouponCodeDao.getCouponVerifyCount(mc.getId());// 投放统计

        for (Map data : map) {
            // Assert.assertEquals(mcc, data.get("status").toString());
            logger.info(data.get("status").toString());
            logger.info(data.get("cnt").toString());
        }

    }
}
