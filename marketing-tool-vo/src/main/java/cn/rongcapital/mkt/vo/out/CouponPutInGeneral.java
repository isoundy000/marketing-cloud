/*************************************************
* @功能及特点的描述简述: 优惠券投放统计
* 该类被编译测试过
* @see （与该类关联的类）：
* @对应项目名称：MC
* @author: liuhaizhan
* @version: 版本
* @date(创建、开发日期)：2016年12月8日
* 最后修改日期：2016年12月8日
* @复审人:
*************************************************/
package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class CouponPutInGeneral {

    @JsonProperty("release_success_count")
    private Integer releasesuccessCount=0;
    @JsonProperty("release_fail_count")
    private Integer releaseFailCount = 0;
    @JsonProperty("rest_count")
    private Integer restCount = 0;
    @JsonProperty("verify_amount")
    private Double verifyAmount = 0.00;
    @JsonProperty("unverify_amount")
    private Double unverifyAmount = 0.00;

    public Double getVerifyAmount() {
        return verifyAmount;
    }

    public void setVerifyAmount(Double verifyAmount) {
        this.verifyAmount = verifyAmount;
    }

    public Double getUnverifyAmount() {
        return unverifyAmount;
    }

    public void setUnverifyAmount(Double unverifyAmount) {
        this.unverifyAmount = unverifyAmount;
    }

    public Integer getReleasesuccessCount() {
        return releasesuccessCount;
    }

    public void setReleasesuccessCount(Integer releasesuccessCount) {
        this.releasesuccessCount = releasesuccessCount;
    }

    public Integer getReleaseFailCount() {
        return releaseFailCount;
    }

    public void setReleaseFailCount(Integer releaseFailCount) {
        this.releaseFailCount = releaseFailCount;
    }

    public Integer getRestCount() {
        return restCount;
    }

    public void setRestCount(Integer restCount) {
        this.restCount = restCount;
    }

}
