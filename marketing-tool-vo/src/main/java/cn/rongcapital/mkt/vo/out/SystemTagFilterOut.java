package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SystemTagFilterOut {
	
	private String tagId;
	
	private String tagName;
	
	private Long tagCount;
	
	public SystemTagFilterOut(){}

	public SystemTagFilterOut(String tagId, String tagName, Long tagCount) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagCount = tagCount;
	}

	@JsonProperty("tag_id")
	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	@JsonProperty("tag_name")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@JsonProperty("tag_count")
	public Long getTagCount() {
		return tagCount;
	}

	public void setTagCount(Long tagCount) {
		this.tagCount = tagCount;
	}
	
	
	

}
