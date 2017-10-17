/**
 * 
 */
package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

/**
 * 接口：mkt.sms.task.delete 的post输入
 * 
 * @author shuiyangyang
 * @Date 2016.10.19
 */
public class SmsTaskDeleteIn extends BaseInput{
    
    @NotNull
    private Long id;
    
    @NotEmpty
    private String userToken = null;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    
    
    

}
