package cn.rongcapital.mkt.vo.in;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactTemplateIn2 {

	@NotEmpty
	@JsonProperty("user_token")
	private String user_token;

	@JsonProperty("contact_id")
	private long contact_id;

	@NotEmpty
	@JsonProperty("contact_name")
	private String contact_name;

	@NotEmpty
	@JsonProperty("contact_title")
	private String contact_title;

	@NotEmpty
	@JsonProperty("contact_descript")
	private String contact_descript;

	@JsonProperty("field_list")
	private List<String> field_list;

	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}

	public String getUser_token() {
		return this.user_token;
	}

	public void setContact_id(long contact_id) {
		this.contact_id = contact_id;
	}

	public long getContact_id() {
		return this.contact_id;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_name() {
		return this.contact_name;
	}

	public void setContact_title(String contact_title) {
		this.contact_title = contact_title;
	}

	public String getContact_title() {
		return this.contact_title;
	}

	public void setContact_descript(String contact_descript) {
		this.contact_descript = contact_descript;
	}

	public String getContact_descript() {
		return this.contact_descript;
	}

//	public void setField_list(List<Field_list> field_list) {
//		this.field_list = field_list;
//	}
//
//	public List<Field_list> getField_list() {
//		return this.field_list;
//	}

}
