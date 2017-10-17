/*************************************************
 * @功能简述: 获取优惠券概要信息
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/7
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;



public interface MaterialCouponGeneralGetService {

    /**
     * 获取优惠券概要信息
     * 
     * @param id 优惠券主键
     * @return BaseOutput
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    public BaseOutput getMaterialCouponGeneral(Long id);

}
