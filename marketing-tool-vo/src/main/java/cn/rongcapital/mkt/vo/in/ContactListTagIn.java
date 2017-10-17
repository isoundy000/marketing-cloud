package cn.rongcapital.mkt.vo.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonProperty;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ContactListTagIn extends BaseInput {
	@NotNull
	private Integer contactId;
	private ArrayList<String> tagNames;

	@JsonProperty("tag_names")
	public ArrayList<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(ArrayList<String> tagNames) {
		this.tagNames = tagNames;
	}

	@JsonProperty("contact_id")
	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
}
