package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentPublishstatusListDataOut {
	
	private String segmentName;

	private Integer referCampaignCount;
	
	private Byte publishStatus;

	private Integer segmentHeadId;
	
	private List<String> tagNames = new ArrayList<String>();

	private Integer coverCount;
	
	private Byte compileStatus;
	
	@JsonProperty("segment_name")
	public String getSegmentName() {
		return segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

    @JsonProperty("refer_campaign_count")
    public Integer getReferCampaignCount() {
        return referCampaignCount;
    }

    public void setReferCampaignCount(Integer referCampaignCount) {
        this.referCampaignCount = referCampaignCount;
    }

    @JsonProperty("segment_head_id")
	public Integer getSegmentHeadId() {
		return segmentHeadId;
	}

	public void setSegmentHeadId(Integer segmentHeadId) {
		this.segmentHeadId = segmentHeadId;
	}

	@JsonProperty("publish_status")
	public Byte getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Byte publishStatus) {
		this.publishStatus = publishStatus;
	}

    @JsonProperty("tag_names")
	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

    @JsonProperty("cover_count")
	public Integer getCoverCount() {
		return coverCount;
	}

	public void setCoverCount(Integer coverCount) {
		this.coverCount = coverCount;
	}
	@JsonProperty("compile_status")
	public Byte getCompileStatus() {
		return compileStatus;
	}

	public void setCompileStatus(Byte compileStatus) {
		this.compileStatus = compileStatus;
	}

}
