/*************************************************
 * @功能简述: MaterialCouponGeneralGetService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponGeneralGetService;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponGeneralGetOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponGeneralGetServiceImpl implements MaterialCouponGeneralGetService {

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput getMaterialCouponGeneral(Long id) {
        BaseOutput result =
                        new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                                        ApiConstant.INT_ZERO, null);
        result.setTotal(ApiConstant.INT_ZERO);
        result.setTotalCount(ApiConstant.INT_ZERO);
        MaterialCoupon po = new MaterialCoupon();
        po.setId(id);
        po.setStatus((byte) 0);
        List<MaterialCoupon> dataList = materialCouponDao.selectList(po);
        if (CollectionUtils.isNotEmpty(dataList)) {
            result.setTotal(ApiConstant.INT_ONE);
            result.setTotalCount(ApiConstant.INT_ONE);
            MaterialCouponGeneralGetOut output = new MaterialCouponGeneralGetOut();
            BeanUtils.copyProperties(dataList.get(0), output);
            result.getData().add(output);
        }
        return result;
    }
}
