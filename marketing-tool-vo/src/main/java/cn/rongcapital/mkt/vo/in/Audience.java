package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class Audience extends BaseInput{

    @NotEmpty
    @JsonProperty("audience_name")
    private String audience_name;
	
	@NotEmpty
    @JsonProperty("user_token")
    private String userToken;
	
	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	@JsonProperty("audience_name")
    public String getAudience_name() {
        return audience_name;
    }

    public void setAudience_name(String audience_name) {
        this.audience_name = audience_name;
    }
	
	
}
