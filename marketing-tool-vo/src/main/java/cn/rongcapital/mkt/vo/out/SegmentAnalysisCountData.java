package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentAnalysisCountData {

	private String tagvalueId;
	
	private String tagvalueName;
	
	private Integer tagvalueCount;

	@JsonProperty("tagvalue_id")
	public String getTagvalueId() {
		return tagvalueId;
	}

	public void setTagvalueId(String tagvalueId) {
		this.tagvalueId = tagvalueId;
	}

	@JsonProperty("tagvalue_name")
	public String getTagvalueName() {
		return tagvalueName;
	}

	public void setTagvalueName(String tagvalueName) {
		this.tagvalueName = tagvalueName;
	}

	@JsonProperty("tagvalue_count")
	public Integer getTagvalueCount() {
		return tagvalueCount;
	}

	public void setTagvalueCount(Integer tagvalueCount) {
		this.tagvalueCount = tagvalueCount;
	}
	
}
