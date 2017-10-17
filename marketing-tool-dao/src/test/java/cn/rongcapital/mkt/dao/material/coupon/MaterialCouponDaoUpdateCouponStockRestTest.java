/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.dao.material.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.MaterialCouponChannelCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponSourceCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponTypeEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;

public class MaterialCouponDaoUpdateCouponStockRestTest extends AbstractUnitTest {

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {}

    /**
     * 
     */
    @Test
    public void testUpdateByIdAndStatus01() {
        MaterialCoupon coupon = new MaterialCoupon();
        coupon.setSourceCode(MaterialCouponSourceCodeEnum.COMMON.getCode());
        coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
        coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
        coupon.setTitle("zhuxuelongtest");
        coupon.setStockRest(100);
        coupon.setStockTotal(100);
        coupon.setAmount(BigDecimal.valueOf(333));
        coupon.setStartTime(new Date());
        coupon.setEndTime(new Date());
        coupon.setCouponStatus(MaterialCouponStatusEnum.RELEASED.getCode());
        coupon.setStatus((byte) 0);
        List<MaterialCoupon> dataList = materialCouponDao.selectList(coupon);
        if (dataList.size() == 0) {
            materialCouponDao.insert(coupon);
        } else {
            coupon.setId(dataList.get(0).getId());
        }
        materialCouponDao.updateCouponStockRest(coupon.getId(), 10);
        MaterialCoupon couponSearch = new MaterialCoupon();
        couponSearch.setId(coupon.getId());
        List<MaterialCoupon> list = materialCouponDao.selectList(couponSearch);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(90, list.get(0).getStockRest().intValue());
    }

}
