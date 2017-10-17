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

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponDeleteServiceImpl implements MaterialCouponDeleteService {

    /*
     * (non-Javadoc)
     * 
     * @see cn.rongcapital.mkt.service.MaterialCouponDeleteService#Delete(int)
     */
    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Override
    public BaseOutput delete(long id) {
        BaseOutput baseOutput = null;
        if (findIsUse(id)) {
            MaterialCoupon mc = new MaterialCoupon();
            mc.setStatus((byte) 1);
            mc.setId(id);
            if (materialCouponDao.updateById(mc) >= 0) {

                // 删除优惠券码信息
                if (materialCouponCodeDao.updateByCouponId(id) >= 0) {
                    baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
                } else {

                    baseOutput =
                            new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO, null);
                }

            } else {
                baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO, null);
            }

        } else {
            baseOutput = new BaseOutput(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getCode(),ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getMsg(), ApiConstant.INT_ZERO, null);
        }

        return baseOutput;
    }

    /**
     * @功能简述 查询出未使用的优惠券
     * @param 优惠券id
     * @return 优惠券实体
     */
    private boolean findIsUse(long id) {

        MaterialCoupon mcp = materialCouponDao.selectOneCoupon(id);
        boolean flag = true;
        if (mcp != null) {
            if (!MaterialCouponStatusEnum.UNUSED.getCode().equals(mcp.getCouponStatus())) {
                flag = false;
            }
        }

        return flag;

    }

}
