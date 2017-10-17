/*************************************************
 * @功能及特点的描述简述: 获取可生成优惠码的最大值item
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016-12-13 
 * @date(最后修改日期)：2016-12-13 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponCodeMaxCountOutItem {
    private Long maxCount;

    public CouponCodeMaxCountOutItem() {
        super();
    }

    /**
     * @param maxCount
     */
    public CouponCodeMaxCountOutItem(Long maxCount) {
        super();
        this.maxCount = maxCount;
    }

    @JsonProperty("max_count")
    public Long getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Long maxCount) {
        this.maxCount = maxCount;
    }
}
