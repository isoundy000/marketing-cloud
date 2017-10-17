package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Yunfeng on 2016-6-7.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataMainSearchIn{
    @NotEmpty
    private String userToken;
    @NotEmpty
    private String name;
    @NotEmpty
    private String ver;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("ver")
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}
