/*************************************************
 * @功能及特点的描述简述: 优惠券管理页面->优惠券列表查询（分页）包含模糊查询 该类被编译测试过
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6 @date(创建、开发日期)：2016-12月-07 最后修改日期：2016-12月-07 @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponListOut;

public interface MaterialCouponPageListService {

    /**
     * @功能描述: 优惠券管理页面->优惠券列表查询（分页）
     * @param channelCode 渠道编码
     * @param couponStatus 优惠券状态
     * @param keyword 模糊查询关键字
     * @param index 当前页
     * @param size 当前页多少条数据
     * @param filterOverdue 是否过滤已经过期的数据
     * @return BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月7日
     */
    MaterialCouponListOut getMaterialCouponListByKeyword(String channelCode, String couponStatus,
                                                         String keyword, Integer index, Integer size, boolean filterOverdue);
}
