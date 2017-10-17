package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ContactListTagOut {
	private String contactId;
	private List<ContactTags> contactTags = new ArrayList<>();

	@JsonProperty("contact_id")
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	@JsonProperty("contact_tags")
	public List<ContactTags> getContactTags() {
		return contactTags;
	}

	public void setContactTags(List<ContactTags> contactTags) {
		this.contactTags = contactTags;
	}
}