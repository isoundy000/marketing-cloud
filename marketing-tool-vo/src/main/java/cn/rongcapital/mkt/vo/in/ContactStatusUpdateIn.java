package cn.rongcapital.mkt.vo.in;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ContactStatusUpdateIn extends BaseInput {
	@NotNull
	private Long contactId;
	@NotNull
	private Byte contact_status;

	public Long getContactId() {
		return contactId;
	}
	
	public void setContact_id(Long contactId) {
		this.contactId = contactId;
	}

	public Byte getContact_status() {
		return contact_status;
	}

	public void setContact_status(Byte contact_status) {
		this.contact_status = contact_status;
	}
}
