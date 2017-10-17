package cn.rongcapital.mkt.vo.sms.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class SmsMaterialDeleteIn extends BaseInput{

	@NotNull
	private Integer id;
	@NotEmpty
	private String userToken;
	
	@JsonProperty("id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
