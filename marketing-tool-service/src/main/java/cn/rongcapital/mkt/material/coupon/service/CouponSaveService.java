package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.material.coupon.vo.in.MaterialCouponInfoIn;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface CouponSaveService {

    BaseOutput save(MaterialCouponInfoIn couponInfo);

}
