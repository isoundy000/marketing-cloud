package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class SmsMessageSendTestIn extends BaseInput{
    
    @NotEmpty
    private String userToken = null;
    
    @NotEmpty
    private String receiveMobiles;
    
    @NotEmpty
    private String sendMessage;

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("receive_mobiles")
    public String getReceiveMobiles() {
        return receiveMobiles;
    }

    public void setReceiveMobiles(String receiveMobiles) {
        this.receiveMobiles = receiveMobiles;
    }

    @JsonProperty("send_message")
    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }
    
}
