/*************************************************
 * @功能及特点的描述简述: 优惠码校验
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016-12月-09 
 * @date(最后修改日期)：2016-12月-09 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeMaxCountOut;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponCodeCheckService {

    /**
     * @功能描述: 优惠码校验
     * @param id
     * @param couponCode
     * @param user 用户ID
     * @return BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月9日
     */
    BaseOutput materialCouponCodeCheck(Long id ,String couponCode,String user);
    
    /**
     * @功能描述: 优惠码核销
     * @param id
     * @param couponCode
     * @param user
     * @return BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月12日
     */
    BaseOutput materialCouponCodeVerify(Long id ,String couponCode,String user);
    
    /**
     * @功能描述: 获取优惠码最大可用数量
     * @return BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月12日
     */
    CouponCodeMaxCountOut materialCouponCodeMaxCount(String typeCode, int length);
}
