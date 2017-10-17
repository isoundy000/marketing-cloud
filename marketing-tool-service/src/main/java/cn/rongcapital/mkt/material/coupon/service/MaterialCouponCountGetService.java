/*************************************************
 * @功能简述: 接口mkt.material.coupon.counts的service接口类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/6
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponCountGetService {

    /**
     * 获取指定条件的优惠券的数量
     * 
     * @param chanelCode
     * @param keyword
     * @return BaseOutput
     * @author zhuxuelong
     * @Date 2016-12-06
     */
    public BaseOutput getMaterialCouponCount(String chanelCode, String keyword);
}
