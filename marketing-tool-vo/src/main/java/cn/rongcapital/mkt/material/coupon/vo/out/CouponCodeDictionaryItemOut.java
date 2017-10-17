/*************************************************
 * @功能及特点的描述简述: 核销对账页面,获取数据字典输出对象
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
public class CouponCodeDictionaryItemOut {

    private String code; // 状态值枚举
    private String desc; // 状态的实际描述

    public CouponCodeDictionaryItemOut() {
        super();
    }

    /**
     * @param code
     * @param desc
     */
    public CouponCodeDictionaryItemOut(String code, String desc) {
        super();
        this.code = code;
        this.desc = desc;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
