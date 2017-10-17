package cn.rongcapital.mkt.material.coupon.service;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface CouponFileUploadService {

    BaseOutput uploadFile(MultipartFormDataInput input, String userId);

    Object getCouponFileUploadUrlGet(String userId);

}
