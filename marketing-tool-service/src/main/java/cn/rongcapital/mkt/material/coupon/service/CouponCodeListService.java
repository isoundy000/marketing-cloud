package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author guozhenchao
 *
 */
public interface CouponCodeListService {
    BaseOutput couponCodeList(Long id, Integer index, Integer size);

    BaseOutput couponIssuedCodeList(Long id, Integer index, Integer size);
}
