package cn.rongcapital.mkt.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-5-25.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginInput extends BaseInput{
    public String user_id = null;
    public String password = null;

    @JsonProperty("user_id")
    public String getUserId(){
        return user_id;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    @JsonProperty("password")
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
