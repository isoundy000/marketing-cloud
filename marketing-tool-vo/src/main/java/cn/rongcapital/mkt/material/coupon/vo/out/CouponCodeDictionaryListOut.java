/*************************************************
 * @功能及特点的描述简述: 自定义优惠券码核销页面数据字典类
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

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponCodeDictionaryListOut extends BaseOutput {
    
    private List<CouponCodeDictionaryOut> codeDictionaryOuts = new ArrayList<>();
    
    public CouponCodeDictionaryListOut() {
        super();
    }

    /**
     * @param code
     * @param msg
     * @param total
     */
    public CouponCodeDictionaryListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }
    
    @JsonProperty("data")
    public List<CouponCodeDictionaryOut> getCodeDictionaryOuts() {
        return codeDictionaryOuts;
    }

    public void setCodeDictionaryOuts(List<CouponCodeDictionaryOut> codeDictionaryOuts) {
        this.codeDictionaryOuts = codeDictionaryOuts;
    }
}
