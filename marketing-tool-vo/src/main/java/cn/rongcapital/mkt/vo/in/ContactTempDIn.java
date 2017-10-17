package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class ContactTempDIn extends BaseInput{
	
	
	@NotEmpty
	@JsonProperty("user_token")
	private String userToken;
	
	@NotNull
	@JsonProperty("contact_id")
	private Long contact_id;
	
	public Long getContact_id() {
		return contact_id;
	}

	public void setContact_id(Long contact_id) {
		this.contact_id = contact_id;
	}

	public String getUserToken() {
		return userToken;
	}
	
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	

}
