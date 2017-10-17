/*************************************************
 * @功能简述: 获取当前系统时间
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/7
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;



public interface MaterialCouponGetSystemTimeService {

    /**
     * 获取当前系统时间
     * 
     * @author zhuxuelong
     * @return BaseOutput
     * @date: 2016/12/7
     */
    public BaseOutput getSystemTime();

}
