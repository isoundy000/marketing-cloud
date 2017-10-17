package cn.rongcapital.mkt.vo.weixin;

import com.alibaba.fastjson.JSONObject;

public class WXMsgVO {

	private String textXml;
	private String msg_signature;
	private String timestamp;
	private String nonce;
	private String signature;
	private String openid;
	private String authAppId;
	
	public WXMsgVO(String textXml,String msg_signature,String timestamp,String nonce,String signature,String openid,String authAppId){
		this.textXml=textXml;
		this.msg_signature=msg_signature;
		this.timestamp=timestamp;
		this.nonce=nonce;
		this.signature=signature;
		this.openid=openid;
		this.authAppId=authAppId;
	}
	
	public String getTextXml() {
		return textXml;
	}
	public void setTextXml(String textXml) {
		this.textXml = textXml;
	}
	public String getMsg_signature() {
		return msg_signature;
	}
	public void setMsg_signature(String msg_signature) {
		this.msg_signature = msg_signature;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAuthAppId() {
		return authAppId;
	}
	public void setAuthAppId(String authAppId) {
		this.authAppId = authAppId;
	}
	
	@Override
	public String toString() {
		String jsonString = JSONObject.toJSONString(this);
		return jsonString;
	}
	
	
}
