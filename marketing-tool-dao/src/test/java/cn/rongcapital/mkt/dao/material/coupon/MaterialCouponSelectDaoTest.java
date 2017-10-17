/*************************************************
 * @功能及特点的描述简述: 测试获取代金券列表 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6 @date(创建、开发日期)：2016-12月-06 最后修改日期：2016-12月-06 @复审人：
 *************************************************/
package cn.rongcapital.mkt.dao.material.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.rongcapital.mkt.common.enums.MaterialCouponReadyStatusType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponSelectDaoTest extends AbstractUnitTest{


    @Autowired
    private MaterialCouponDao meterialCouponDao;

    MaterialCoupon paramMaterialCoupon= null;

    /**
     * Test method for {@link cn.rongcapital.mkt.dao.material.coupon.MaterialCouponSelectDaoTest#setUp()}.
     */
    @Before
    public void testSetUp() {
        paramMaterialCoupon = new MaterialCoupon();
        paramMaterialCoupon.setAmount(new BigDecimal(8));
        paramMaterialCoupon.setChannelCode("sms");
        paramMaterialCoupon.setCouponStatus("used");
        paramMaterialCoupon.setCreateTime(new Date());
        paramMaterialCoupon.setEndTime(new Date());
        paramMaterialCoupon.setSourceCode("common");
        paramMaterialCoupon.setRule("");
        paramMaterialCoupon.setStartTime(new Date());
        paramMaterialCoupon.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        paramMaterialCoupon.setStockRest(10000);
        paramMaterialCoupon.setStockTotal(20000);
        paramMaterialCoupon.setTitle("贝贝熊TESTByXiexl短信引流优惠码活动");
        paramMaterialCoupon.setType("voucher");
        paramMaterialCoupon.setUpdateTime(new Date());
        paramMaterialCoupon.setReadyStatus(MaterialCouponReadyStatusType.READY.getCode());
        meterialCouponDao.insert(paramMaterialCoupon);
    }

    /**
     * Test method for
     * {@link cn.rongcapital.mkt.dao.material.coupon.MaterialCouponSelectDaoTest#selectListByKeyword()}.
     * 
     * @throws Exception
     */
    @Test
    public void testSelectListCountByKeyword() throws Exception {

        MaterialCoupon queryMaterialCoupon = new MaterialCoupon();
        queryMaterialCoupon.setTitle("贝贝熊TESTByXiexl");
        queryMaterialCoupon.setStartIndex(0);
        queryMaterialCoupon.setPageSize(10);
        queryMaterialCoupon.setChannelCode("sms");
        queryMaterialCoupon.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        int resultCount = meterialCouponDao.selectListByKeywordCount(queryMaterialCoupon);
        Assert.assertEquals(1, resultCount);
    }
    
    
    /**
     * Test method for
     * {@link cn.rongcapital.mkt.dao.material.coupon.MaterialCouponSelectDaoTest#selectListByKeyword()}.
     * 
     * @throws Exception
     */
    @Test
    public void testSelectListByKeyword() throws Exception {

        MaterialCoupon queryMaterialCoupon = new MaterialCoupon();
        queryMaterialCoupon.setTitle("贝贝熊TESTByXiexl");
        queryMaterialCoupon.setStartIndex(0);
        queryMaterialCoupon.setPageSize(10);
        queryMaterialCoupon.setChannelCode("sms");
        queryMaterialCoupon.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        List<MaterialCoupon> resultList = meterialCouponDao.selectListByKeyword(queryMaterialCoupon);
        if (CollectionUtils.isEmpty(resultList)) {
            throw new Exception("数据选取失败，单元测试不通过");
        }

        for (MaterialCoupon materialCoupon : resultList) {
            if (materialCoupon.getTitle().contains("短信引流")) {
                Assert.assertEquals(paramMaterialCoupon.getTitle().equals(materialCoupon.getTitle()), true);
            }
        }
    }

    /**
     * Test method for {@link cn.rongcapital.mkt.dao.material.coupon.MaterialCouponSelectDaoTest#tearDown()}.
     */
    @After
    public void testTearDown() {
        paramMaterialCoupon.setStatus(NumUtil.int2OneByte(StatusEnum.DELETED.getStatusCode()));
        meterialCouponDao.updateById(paramMaterialCoupon);
    }

}
