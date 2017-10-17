/*************************************************
 * @功能及特点的描述简述: 优惠券详情（编辑优惠券回显） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：XXX系统
 * @author: liuhaizhan
 * @version: 版本
 * @date(创建、开发日期)：2016年12月6日 最后修改日期：2016年12月6日 @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponEditDetailService {

    /**
     * @author liuhaizhan
     * @功能简述 返回编辑的优惠券明细
     * @param：id 优惠券ID
     * @return：message2
     */
    BaseOutput getCouponEditdes(long id);

}
