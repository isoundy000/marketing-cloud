/*************************************************
 * @功能简述: MaterialCouponGetSystemTimeService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponGetSystemTimeService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponGetSystemTimeServiceImpl implements MaterialCouponGetSystemTimeService {

    /**
     * 获取当前系统时间
     * 
     * @author zhuxuelong
     * @return BaseOutput
     * @date: 2016/12/7
     */
    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput getSystemTime() {
        BaseOutput result =
                        new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                                        ApiConstant.INT_ONE, null);
        result.setTotalCount(ApiConstant.INT_ONE);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("time", System.currentTimeMillis());
        result.getData().add(map);
        return result;
    }

}
