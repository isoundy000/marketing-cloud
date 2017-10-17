package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentFilterCondition {

	@NotEmpty
	private String tagId;

	@NotEmpty
	private String tagName;

	@NotEmpty
	private String exclude;

	@NotNull
	private String tagGroupId;

	@JsonProperty("tag_id")
	public String getTagId() {
		return tagId;
	}

	public void setTag_id(String tagId) {
		this.tagId = tagId;
	}

	@JsonProperty("exclude")
	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	@JsonProperty("tag_name")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@JsonProperty("tag_group_id")
	public String getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(String tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

}
