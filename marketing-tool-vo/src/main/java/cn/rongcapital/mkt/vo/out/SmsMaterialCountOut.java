package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 12/9/16.
 */
public class SmsMaterialCountOut {

    private Integer smsType;
    private Integer smsCount;

    @JsonProperty("sms_type")
    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    @JsonProperty("sms_count")
    public Integer getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(Integer smsCount) {
        this.smsCount = smsCount;
    }
}
