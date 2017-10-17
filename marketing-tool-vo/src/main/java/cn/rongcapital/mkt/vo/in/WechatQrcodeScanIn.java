/**
 * 
 */
package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

/**
 * @author shuiyangyang
 *
 */
public class WechatQrcodeScanIn extends BaseInput{
	
	@NotEmpty
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("user_host")
	private String userHost;
	
	@NotEmpty
	@JsonProperty("qrcode_id")
	private String qrcodeId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserHost() {
		return userHost;
	}

	public void setUserHost(String userHost) {
		this.userHost = userHost;
	}

	public String getQrcodeId() {
		return qrcodeId;
	}

	public void setQrcodeId(String qrcodeId) {
		this.qrcodeId = qrcodeId;
	}
	
	
	

}
