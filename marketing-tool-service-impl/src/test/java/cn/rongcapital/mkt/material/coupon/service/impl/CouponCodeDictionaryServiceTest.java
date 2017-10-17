/*************************************************
 * @功能及特点的描述简述: CouponCodeDictionaryService 单元测试
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016-12-13 
 * @date(最后修改日期)：2016-12-13 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponExpiredEnum;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeDictionaryService;
import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeDictionaryItemOut;
import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeDictionaryOut;
import cn.rongcapital.mkt.vo.BaseOutput;


public class CouponCodeDictionaryServiceTest {

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    private CouponCodeDictionaryService service; // service实例

    /**
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2016-12-13
     */
    @Before
    public void setUp() throws Exception {
        logger.info("测试：CouponCodeDictionaryServiceTest 准备---------------------");
        service = new CouponCodeDictionaryServiceImpl();
    }

    /**
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2016-12-13
     */
    @After
    public void tearDown() throws Exception {
        logger.info("测试：CouponCodeDictionaryServiceTest 结束---------------------");
    }

    /**
     * Test method for
     * {@link cn.rongcapital.mkt.material.coupon.service.impl.CouponCodeDictionaryServiceImpl#materialCouponDictionary()}.
     */
    @Test
    public void testMaterialCouponCodeCheck() {

        BaseOutput output = this.service.materialCouponDictionary();
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        for (Object object : output.getData()) {
            if (object instanceof CouponCodeDictionaryOut) {
                CouponCodeDictionaryOut data = (CouponCodeDictionaryOut) object;
                List<CouponCodeDictionaryItemOut>  itemList = data.getVerifyStatus();
                 
                for (CouponCodeDictionaryItemOut item : itemList) {
                    Assert.assertEquals(Boolean.TRUE, MaterialCouponCodeVerifyStatusEnum.contains(item.getCode()));
                }
                
                 itemList = data.getExpiredStatus();
                
                for (CouponCodeDictionaryItemOut item : itemList) {
                    Assert.assertEquals(Boolean.TRUE, MaterialCouponExpiredEnum.contains(item.getCode()));
                }
                
                itemList = data.getReceivedStatus();
                
                for (CouponCodeDictionaryItemOut item : itemList) {
                    Assert.assertEquals(Boolean.TRUE, MaterialCouponCodeReleaseStatusEnum.contains(item.getCode()));
                }
                
                
            }
        }

    }

}
