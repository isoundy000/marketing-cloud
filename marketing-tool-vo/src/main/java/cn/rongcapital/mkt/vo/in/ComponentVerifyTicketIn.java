package cn.rongcapital.mkt.vo.in;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class ComponentVerifyTicketIn {
	

    private String appId;

    private String encrypt;
    
    private String toUserName="gh_4685d4eef135";

	@XmlElement(name="AppId")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	@XmlElement(name="Encrypt")
	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	@XmlElement(name="ToUserName")
	public String getToUserName() {
		return toUserName;
	}


	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	
}
