package cn.rongcapital.mkt.vo.in;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TagBodyUpdateIn extends BaseInput {
	@NotNull
	private Integer tag_id;
	@NotEmpty
	private String tag_name = null;
	@NotEmpty
	private String tag_group_id = null;
	
	@JsonProperty("tag_id")
	public Integer getTag_id() {
		return tag_id;
	}

	public void setTag_id(Integer tag_id) {
		this.tag_id = tag_id;
	}

	@JsonProperty("tag_name")
	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	@JsonProperty("tag_group_id")
	public String getTag_group_id() {
		return tag_group_id;
	}

	public void setTag_group_id(String tag_group_id) {
		this.tag_group_id = tag_group_id;
	}

	
}
