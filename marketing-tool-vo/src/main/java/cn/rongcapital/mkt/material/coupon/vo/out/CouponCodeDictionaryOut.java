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

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponCodeDictionaryOut {

    private List<CouponCodeDictionaryItemOut> receivedStatus = new ArrayList<>();
    private List<CouponCodeDictionaryItemOut> verifyStatus = new ArrayList<>();
    private List<CouponCodeDictionaryItemOut> expiredStatus = new ArrayList<>();
    public List<CouponCodeDictionaryItemOut> getReceivedStatus() {
        return receivedStatus;
    }
    public void setReceivedStatus(List<CouponCodeDictionaryItemOut> receivedStatus) {
        this.receivedStatus = receivedStatus;
    }
    public List<CouponCodeDictionaryItemOut> getVerifyStatus() {
        return verifyStatus;
    }
    public void setVerifyStatus(List<CouponCodeDictionaryItemOut> verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
    public List<CouponCodeDictionaryItemOut> getExpiredStatus() {
        return expiredStatus;
    }
    public void setExpiredStatus(List<CouponCodeDictionaryItemOut> expiredStatus) {
        this.expiredStatus = expiredStatus;
    }
}
