/*************************************************
 * @功能及特点的描述简述: 优惠码投放统计 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统) @author:liuhaizhan
 * @version: 版本v1.6 @date(创建、开发日期)：2016年12月16日 @date(最后修改日期)：2016年12月16日 @复审人：
 *************************************************/

package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponPutInGeneralService;
import cn.rongcapital.mkt.vo.BaseOutput;
import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponPutInGeneralServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MaterialCouponPutInGeneralService materialCouponPutInGeneralService;

    @Mock
    private MaterialCouponDao materialCouponDao;
    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;
    long id = 12l;

    @Before
    public void set() {
        materialCouponPutInGeneralService = new MaterialCouponPutInGeneralServiceImpl();
        ReflectionTestUtils.setField(materialCouponPutInGeneralService, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(materialCouponPutInGeneralService, "materialCouponCodeDao", materialCouponCodeDao);
    }

    @Test
    public void test() {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VERIFYDATE_ERROR.getCode(),
                ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VERIFYDATE_ERROR.getMsg(), ApiConstant.INT_ONE, null);
        Mockito.when(materialCouponDao.selectOneCoupon(id)).thenReturn(null);
        BaseOutput actual = materialCouponPutInGeneralService.putInGeneral(id);
        Assert.assertEquals(baseOutput.getMsg(), actual.getMsg()); // 测试没有数据

        MaterialCoupon m = new MaterialCoupon();
        m.setStatus((byte) 1);
        Mockito.when(materialCouponDao.selectOneCoupon(id)).thenReturn(m);
        actual = materialCouponPutInGeneralService.putInGeneral(id);
        Assert.assertEquals(baseOutput.getMsg(), actual.getMsg()); // 已删除状态数据



        MaterialCoupon mc = new MaterialCoupon();
        mc.setStatus((byte) 0);
        mc.setAmount(new BigDecimal(7.00));
        Mockito.when(materialCouponDao.selectOneCoupon(id)).thenReturn(mc);

        Mockito.when(materialCouponCodeDao.getCouponPutInCount(Mockito.anyLong())).thenReturn(null); // 投放数量为空

        Mockito.when(materialCouponCodeDao.getCouponVerifyCount(Mockito.anyLong())).thenReturn(null); // 核销为空
        baseOutput.setCode(0);
        actual = materialCouponPutInGeneralService.putInGeneral(id);
        Assert.assertEquals(baseOutput.getCode(), actual.getCode());

        logger.info(JSON.toJSONString(actual));

        Map data = new HashMap();
        data.put("status", "received");
        data.put("cnt", 6);
        Map data1=new HashMap();
        data1.put("status", "unreceived");
        data1.put("cnt", 7);
        List<Map> putIncnt = new ArrayList<Map>();
        putIncnt.add(data);
        putIncnt.add(data1);
        Mockito.when(materialCouponCodeDao.getCouponPutInCount(Mockito.anyLong())).thenReturn(putIncnt);
        List<Map> couponVerifyCnt = new ArrayList<Map>();
        Map data2 = new HashMap();
        data2.put("status", "unverify");
        data2.put("cnt", 2);
        Map data3 = new HashMap();
        data3.put("status", "verified");
        data3.put("cnt", "4");
        couponVerifyCnt.add(data3);
        couponVerifyCnt.add(data2);
        Mockito.when(materialCouponCodeDao.getCouponVerifyCount(Mockito.anyLong())).thenReturn(couponVerifyCnt);
        baseOutput.setCode(0);
        actual = materialCouponPutInGeneralService.putInGeneral(id);
        Assert.assertEquals(baseOutput.getCode(), actual.getCode());
        logger.info(JSON.toJSONString(actual));

        // 无核销数据时
        Mockito.when(materialCouponCodeDao.getCouponVerifyCount(Mockito.anyLong())).thenReturn(null);
        actual = materialCouponPutInGeneralService.putInGeneral(id);
        Assert.assertEquals(baseOutput.getCode(), actual.getCode());
        logger.info(JSON.toJSONString(actual));

    }


}
