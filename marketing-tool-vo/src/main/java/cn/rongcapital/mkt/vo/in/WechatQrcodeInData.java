package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class WechatQrcodeInData extends BaseInput{
	@NotEmpty
	@JsonProperty("wx_acct")
	private String wxAcct;
	
	@JsonProperty("wx_name")
	private String wxName;
	
	@JsonProperty("ch_code")
	private Integer chCode;
	
	@NotEmpty
	@JsonProperty("ch_name")
	private String chName;
	
	@NotEmpty
	@JsonProperty("qrcode_name")
	private String qrcodeName;
	
	@JsonProperty("expiration_time")
	private String expirationTime;
	
	@JsonProperty("is_audience")
	private Integer isAudience;
	
	@JsonProperty("audience_name")
	private String audienceName;
	
	@NotEmpty
    @JsonProperty("user_token")
    private String userToken;

	public String getWxAcct() {
		return wxAcct;
	}

	public void setWxAcct(String wxAcct) {
		this.wxAcct = wxAcct;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public Integer getChCode() {
		return chCode;
	}

	public void setChCode(Integer chCode) {
		this.chCode = chCode;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getQrcodeName() {
		return qrcodeName;
	}

	public void setQrcodeName(String qrcodeName) {
		this.qrcodeName = qrcodeName;
	}



	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Integer getIsAudience() {
		return isAudience;
	}

	public void setIsAudience(Integer isAudience) {
		this.isAudience = isAudience;
	}

	public String getAudienceName() {
		return audienceName;
	}

	public void setAudienceName(String audienceName) {
		this.audienceName = audienceName;
	}
	
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	

}
