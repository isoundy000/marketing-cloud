/*************************************************
 * @功能及特点的描述简述: 返回优惠券编辑回显 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC
 * @author: liuhaizhan
 * @version: 版本
 * @date(创建、开发日期)：2016年12月9日 最后修改日期：2016年12月9日 @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponSourceCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponEditDetailService;
import cn.rongcapital.mkt.material.coupon.vo.out.CouPonEditInfoOut;
import cn.rongcapital.mkt.vo.BaseOutput;

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

        BaseOutput base = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        MaterialCoupon mcp = materialCouponDao.selectOneCoupon(id);
       //20161222 删除掉非used 情况 
        if (mcp != null) {
            List data = new ArrayList();
            CouPonEditInfoOut cpdi = new CouPonEditInfoOut();
            BeanUtils.copyProperties(mcp, cpdi);
            // 20161222 适配文件上传own为自由码时 返回则是{}
            if (MaterialCouponSourceCodeEnum.OWN.getCode().equals(mcp.getSourceCode())) {
                cpdi.setRlObject(JSONUtils.parse("[]"));
            } else {
                cpdi.setRlObject(JSONUtils.parse(mcp.getRule() == null ? "{}" : mcp.getRule())); //json 转换 
            }
          
            cpdi.setAmount(mcp.getAmount()==null?0:mcp.getAmount().doubleValue());
            cpdi.setStartTime(mcp.getStartTime() == null ? 0 : mcp.getStartTime().getTime());
            cpdi.setEndTime(mcp.getEndTime() == null ? 0 : mcp.getEndTime().getTime());
            data.add(cpdi);
            base.setTotal(ApiConstant.INT_ONE);
            base.setData(data);
        } else {
            base.setCode(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getCode());
            base.setMsg(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR.getMsg());
        }
        return base;
    }

}
