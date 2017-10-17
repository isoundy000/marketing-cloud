/*************************************************
* @功能及特点的描述简述: 返回优惠券编辑回显
* 该类被编译测试过
* @see （与该类关联的类）：
* @对应项目名称：MC
* @author: liuhaizhan
* @version: 版本
* @date(创建、开发日期)：2016年12月9日
* 最后修改日期：2016年12月9日
* @复审人:
*************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.dao.MaterialCouponDao;
import cn.rongcapital.mkt.po.MaterialCoupon;
import cn.rongcapital.mkt.service.MaterialCouponEditDetailService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CouPonEditInfo;
@Service
public class MaterialCouponEditDetailServiceImpl implements MaterialCouponEditDetailService {

    /*
     * (non-Javadoc)
     * 
     * @see cn.rongcapital.mkt.service.MaterialCouponEditdetail#getCouponEditdes(long)
     */

    @Autowired
    private MaterialCouponDao materialCouponDao;

    /**
     * @功能简述 编辑时优惠券回显
     * @param 优惠券id
     * @return 优惠券实体
     */
    @Override
    public BaseOutput getCouponEditdes(long id) {
        // TODO Auto-generated method stub

        BaseOutput base = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        MaterialCoupon mcp = materialCouponDao.selectOneCoupon(id);

        if (mcp != null && MaterialCouponStatusEnum.UNUSED.getCode().equals(mcp.getCouponStatus())) {
            List data = new ArrayList();
            CouPonEditInfo cpdi = new CouPonEditInfo();
            BeanUtils.copyProperties(mcp,cpdi);
            cpdi.setStartTime(mcp.getStartTime()==null?0:mcp.getStartTime().getTime());
            cpdi.setEndTime(mcp.getEndTime()==null?0:mcp.getEndTime().getTime());
            data.add(cpdi);
            base.setTotal(ApiConstant.INT_ONE);
            base.setData(data);
        } else {
            base.setCode(ApiErrorCode.COUPON_VALIDATE_ERROR.getCode());
            base.setMsg(ApiErrorCode.COUPON_VALIDATE_ERROR.getMsg());
        }
        return base;
    }

}
