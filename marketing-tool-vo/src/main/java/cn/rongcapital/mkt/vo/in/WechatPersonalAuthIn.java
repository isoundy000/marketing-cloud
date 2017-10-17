package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Yunfeng on 2016-6-22.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class WechatPersonalAuthIn extends BaseInput{
    @NotEmpty
    private String uuid;
    @NotEmpty
    private String uin;

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("uin")
    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }
}
