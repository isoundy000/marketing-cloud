/*************************************************
 * @功能及特点的描述简述: 核销对账页面,获取数据字典
 * 该类被编译测试过
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016-12-13 
 * @date(最后修改日期)：2016-12-13 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeDictionaryListOut;

public interface CouponCodeDictionaryService {

    /**
     * @功能描述: 获取数据字典
     * @return BaseOutput
     * @author xie.xiaoliang
     * @since 2016-12-13
     */
    CouponCodeDictionaryListOut materialCouponDictionary();

}
