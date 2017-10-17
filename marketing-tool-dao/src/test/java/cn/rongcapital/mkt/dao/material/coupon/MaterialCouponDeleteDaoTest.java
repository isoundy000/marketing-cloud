/*************************************************
* @功能及特点的描述简述: message（例如该类是用来做什么的）
* 该类被编译测试过
* @see （与该类关联的类）：
* @对应项目名称：MC
* @author: liuhaizhan
* @version: 版本
* @date(创建、开发日期)：2016年12月7日
* 最后修改日期：2016年12月7日
* @复审人:
*************************************************/
package cn.rongcapital.mkt.dao.material.coupon;

import java.math.BigDecimal;
import java.util.Date;

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
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponDeleteDaoTest extends AbstractUnitTest {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    MaterialCoupon mc = null;

    MaterialCouponCode mcc = null;

    @Before
    // 先插入
    public void setUp() {
        mc = new MaterialCoupon();
        mc.setTitle("LiuHaizhantest");
        mc.setSourceCode("common");
        mc.setType("voucher");
        mc.setChannelCode("sms");
        mc.setStatus((byte) 0);
        mc.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
        mc.setStockTotal(1500);
        mc.setAmount(new BigDecimal(10));// 金额;
        mc.setStockRest(200);;// 库存数量
        mc.setStartTime(new Date());
        mc.setEndTime(new Date());
        mc.setReadyStatus("ready");
        materialCouponDao.insert(mc);

        // 插入优惠码
        mcc = new MaterialCouponCode();
        mcc.setCode("123456AAAAAABBBB");
        mcc.setCouponId(mc.getId());
        mcc.setStatus((byte) 0);
        mcc.setUser("13842821032");
        mcc.setVerifyStatus("unverify");
        mcc.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());    
        materialCouponCodeDao.insert(mcc);
    }

    @Test
    public void test() {
        Long id = 1l; // 是否存在
        Assert.assertEquals(mc.getTitle(), materialCouponDao.selectOneCoupon(mc.getId()).getTitle());
    }

    @Test
    public void update() {

        mc.setStatus((byte) 1);
        materialCouponDao.updateById(mc); // 更新优惠券id
        // 查询更新是否成功
        Assert.assertEquals(mc.getStatus(), materialCouponDao.selectOneCoupon(mc.getId()).getStatus());
        materialCouponCodeDao.updateByCouponId(mc.getId()); // 更新优惠券码
         
        MaterialCouponCode mc =new MaterialCouponCode();
        mc.setId(mcc.getId());
       // materialCouponCodeDao.selectList(mc);
        Assert.assertEquals("1",materialCouponCodeDao.selectList(mc).get(0).getStatus().toString() );

       

    }


}
