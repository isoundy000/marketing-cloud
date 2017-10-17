 /*************************************************
 * @功能及特点的描述简述: 返回获取发放统计数据
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC
 * @author: liuhaizhan
 * @version: 版本
 * @date(创建、开发日期)：2016年12月7日
 * 最后修改日期：2016年12月7日
 * @复审人:
 *************************************************/ 
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponPutInGeneralService {
    
    /**
     * @author liuhaizhan
     * @功能简述: 返回获取发放统计数据
     * @param 优惠券ID
     * @return 
     */
   BaseOutput putInGeneral(long id);


}
