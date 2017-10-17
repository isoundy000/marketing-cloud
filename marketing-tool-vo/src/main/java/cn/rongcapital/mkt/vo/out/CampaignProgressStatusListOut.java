package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignProgressStatusListOut {

	private String campaignName;
	
	private int campaignId;
	
	private int publishStatus;
	
	private String createTime;
	
	private String startTime;
	
	private String endTime;
	
	private String segmentationName;

	@JsonProperty("campaign_name")
	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	@JsonProperty("campaign_head_id")
	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	@JsonProperty("publish_status")
	public int getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(int publishStatus) {
		this.publishStatus = publishStatus;
	}

	@JsonProperty("create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@JsonProperty("start_time")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@JsonProperty("end_time")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@JsonProperty("segmentation_name")
	public String getSegmentationName() {
		return segmentationName;
	}

	public void setSegmentationName(String segmentationName) {
		this.segmentationName = segmentationName;
	}
	
	
}
