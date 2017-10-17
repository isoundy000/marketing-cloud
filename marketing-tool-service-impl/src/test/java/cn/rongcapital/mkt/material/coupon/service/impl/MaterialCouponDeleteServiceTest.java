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
package cn.rongcapital.mkt.material.coupon.service.impl;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponDeleteService;
import cn.rongcapital.mkt.material.coupon.service.impl.MaterialCouponDeleteServiceImpl;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponDeleteServiceTest {

private Logger logger = LoggerFactory.getLogger(getClass());
    
    private MaterialCouponDeleteService materialCouponDeleteService;

    @Mock
    private MaterialCouponDao materialCouponDao;

    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;
    private Long id = 1l;
    MaterialCoupon mcp = null;

    @Before
    public void set() {
        mcp = new MaterialCoupon();
        mcp.setCouponStatus(MaterialCouponStatusEnum.RELEASED.getCode());
        materialCouponDeleteService = new MaterialCouponDeleteServiceImpl();
        ReflectionTestUtils.setField(materialCouponDeleteService, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(materialCouponDeleteService, "materialCouponCodeDao", materialCouponCodeDao);

    }

    @Test
    public void del() {
        
        Mockito.when(materialCouponDao.selectOneCoupon(id)).thenReturn(null); //查不到数据时
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
            ApiErrorCode.SUCCESS.getMsg(), 1, null);
    BaseOutput actual = materialCouponDeleteService.delete(id);
    Assert.assertEquals(baseOutput.getCode(), actual.getCode());
        
        
        Mockito.when(materialCouponDao.selectOneCoupon(id)).thenReturn(mcp);
        baseOutput = new BaseOutput(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getCode(),
                ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getMsg(), 1, null);
       actual = materialCouponDeleteService.delete(id);
        Assert.assertEquals(baseOutput.getMsg(), actual.getMsg()); // 测试不是未使用状态

        // 测试未使用状态的删除操作
        mcp.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
        Mockito.when(materialCouponDao.selectOneCoupon(id)).thenReturn(mcp);

        // 增加一个any()
        Mockito.when(materialCouponDao.updateById(Mockito.any())).thenReturn(-1); // 删除失败时

        baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(), 1, null);

        actual = materialCouponDeleteService.delete(id);
        Assert.assertEquals(baseOutput.getCode(), actual.getCode());
    
        //优惠券删除成功，优惠码删除成功
        Mockito.when(materialCouponDao.updateById(Mockito.any())).thenReturn(1); // 删除成功時
        Mockito.when(materialCouponCodeDao.updateByCouponId(Mockito.anyLong())).thenReturn(1);
        baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), 1, null);
        actual=materialCouponDeleteService.delete(id);
        Assert.assertEquals(baseOutput.getCode(), actual.getCode());
        
        //优惠券删除成功,优惠码删除失败时
        Mockito.when(materialCouponDao.updateById(Mockito.any())).thenReturn(1); // 删除成功時
        Mockito.when(materialCouponCodeDao.updateByCouponId(Mockito.anyLong())).thenReturn(-1);
        baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(), 1, null);
        actual=materialCouponDeleteService.delete(id);
        Assert.assertEquals(baseOutput.getCode(), actual.getCode());
        
    }
}
