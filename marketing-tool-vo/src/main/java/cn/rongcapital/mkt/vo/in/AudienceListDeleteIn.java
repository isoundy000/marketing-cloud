package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class AudienceListDeleteIn extends BaseInput{
	
	@NotEmpty
	@JsonProperty("user_token")
	private String userToken;
	
	@NotNull
	@JsonProperty("audience_list_id")
	private Integer audienceListId;
	
	public String getUserToken() {
		return userToken;
	}
	
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	public Integer getAudienceListId() {
		return audienceListId;
	}
	
	public void setAudienceListId(Integer audienceListId) {
		this.audienceListId = audienceListId;
	}
}
