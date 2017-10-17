package cn.rongcapital.mkt.vo.in;


import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

import cn.rongcapital.mkt.vo.BaseInput;

/**
 * 短信模板删除
 * 
 * 接口：mkt.sms.smstemplet.del
 * @param body
 * @param securityContext
 * @return
 * @author shuiyangyang
 * @Date 2016-11-14
 */
public class SmsSmstempletDelIn extends BaseInput {
    @NotEmpty
    private String userToken = null;
    
    @NotNull
    private Integer id ;

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   
    
    

}
