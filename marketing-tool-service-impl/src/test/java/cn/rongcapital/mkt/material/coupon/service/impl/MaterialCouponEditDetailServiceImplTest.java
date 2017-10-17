/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统) @author:liuhaizhan
 * @version: 版本v1.6 @date(创建、开发日期)：2016年12月12日 @date(最后修改日期)：2016年12月12日 @复审人：
 *************************************************/

package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponEditDetailService;
import cn.rongcapital.mkt.vo.BaseOutput;
import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponEditDetailServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MaterialCouponEditDetailService materialCouponEditDetailService;

    @Mock
    private MaterialCouponDao materialCouponDao;

    MaterialCoupon mcp = null;
    long id = 1l;

    @Before
    public void set() {
        materialCouponEditDetailService = new MaterialCouponEditDetailServiceImpl();
        ReflectionTestUtils.setField(materialCouponEditDetailService, "materialCouponDao", materialCouponDao);
    }

    @Test
    public void test() {

        // 非使用状态
        mcp = new MaterialCoupon();
        mcp.setId(123l);
        mcp.setTitle("test");
        mcp.setRule("{\"coupon_code\": 48294}");
        mcp.setAmount(new BigDecimal(100));
        mcp.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
        mcp.setChannelCode("sms");
        mcp.setEndTime(new Date());
        mcp.setSourceCode("common");
        mcp.setStockTotal(100);
        mcp.setStartTime(new Date());
        BaseOutput base = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), 1, null);
        Mockito.when(materialCouponDao.selectOneCoupon(Mockito.anyLong())).thenReturn(mcp);
        List list = new ArrayList();
        list.add(mcp);
        base.setData(list);
        BaseOutput bo = materialCouponEditDetailService.getCouponEditdes(id);
        Assert.assertEquals(base.getCode(), bo.getCode());
        logger.info(JSON.toJSONString(bo));
  
        
        //规则为null，金额，时间为null
        mcp = new MaterialCoupon();
        mcp.setId(123l);
        mcp.setTitle("test");
        mcp.setSourceCode("common");
        mcp.setStockTotal(100);
        mcp.setChannelCode("sms");
        mcp.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
        Mockito.when(materialCouponDao.selectOneCoupon(Mockito.anyLong())).thenReturn(mcp);
        List list1 = new ArrayList();
        list1.add(mcp);
        base.setData(list1);
        bo = materialCouponEditDetailService.getCouponEditdes(id);
        Assert.assertEquals(base.getCode(), bo.getCode());
        logger.info(JSON.toJSONString(bo));



        // 使用状态
        mcp = new MaterialCoupon();
        mcp.setId(123l);
        mcp.setTitle("test");
        mcp.setRule("{\"coupon_code\": 48294}");
        mcp.setCouponStatus(MaterialCouponStatusEnum.USED.getCode());
        BaseOutput fbase = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                ApiErrorCode.SUCCESS.getMsg(), 1, null);
        Mockito.when(materialCouponDao.selectOneCoupon(Mockito.anyLong())).thenReturn(mcp);

        BaseOutput bo2 = materialCouponEditDetailService.getCouponEditdes(id);
        Assert.assertEquals(fbase.getCode(), bo2.getCode());

        // 当返回为null时
        Mockito.when(materialCouponDao.selectOneCoupon(Mockito.anyLong())).thenReturn(null);
        fbase = new BaseOutput(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getCode(),
            ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getMsg(), 1, null);
        bo2 = materialCouponEditDetailService.getCouponEditdes(id);
        Assert.assertEquals(fbase.getCode(), bo2.getCode());



    }
}
