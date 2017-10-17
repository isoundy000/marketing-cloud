/*************************************************
 * @功能简述: VO:MaterialCouponCodeVerifyListOut
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/8
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.vo.out;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


public class MaterialCouponCodeVerifyListOut {

    private Long id;

    private String code;

    private String user;

    private BigDecimal amount;

    private String status;

    @JsonIgnore
    private Date verifyTimeDate;

    @JsonProperty("verify_time")
    private Long verifyTime;

    @JsonProperty("channel_code")
    private String channelCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getVerifyTimeDate() {
        return verifyTimeDate;
    }

    public void setVerifyTimeDate(Date verifyTimeDate) {
        this.verifyTimeDate = verifyTimeDate;
    }

    public Long getVerifyTime() {
        if (this.verifyTimeDate != null) {
            return this.verifyTimeDate.getTime();
        } else {
            return null;
        }
    }

    public void setVerifyTime(Long verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Override
    public String toString() {
        return "MaterialCouponCodeVerifyListOut [id=" + id + ", code=" + code + ", user=" + user + ", amount=" + amount
                        + ", status=" + status + ", verifyTime=" + verifyTime + ", channelCode=" + channelCode + "]";
    }

}
