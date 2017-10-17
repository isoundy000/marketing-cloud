package cn.rongcapital.mkt.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Yunfeng on 2016-5-26.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ModifyInput extends BaseInput{
    public String user_id = null;
    public String oldPasswd = null;
    public String newPasswd = null;

    @JsonProperty("user_id")
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @JsonProperty("oldPasswd")
    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }

    @JsonProperty("newPasswd")
    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
}
