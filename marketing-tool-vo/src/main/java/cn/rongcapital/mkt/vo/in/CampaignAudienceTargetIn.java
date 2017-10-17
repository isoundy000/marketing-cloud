package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignAudienceTargetIn {
	
    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private Integer segmentationId;

    private String segmentationName;

    private Byte allowedNew;

    private Integer refreshInterval;

    private Byte refreshIntervalType;

    @JsonProperty("campaign_head_id")
    public Integer getCampaignHeadId() {
        return campaignHeadId;
    }

    public void setCampaignHeadId(Integer campaignHeadId) {
        this.campaignHeadId = campaignHeadId;
    }

    @JsonProperty("item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("segmentation_id")
    public Integer getSegmentationId() {
        return segmentationId;
    }

    public void setSegmentationId(Integer segmentationId) {
        this.segmentationId = segmentationId;
    }

    @JsonProperty("segmentation_name")
    public String getSegmentationName() {
        return segmentationName;
    }

    public void setSegmentationName(String segmentationName) {
        this.segmentationName = segmentationName == null ? null : segmentationName.trim();
    }

    @JsonProperty("allowed_new")
    public Byte getAllowedNew() {
        return allowedNew;
    }

    public void setAllowedNew(Byte allowedNew) {
        this.allowedNew = allowedNew;
    }

    @JsonProperty("refresh_interval")
    public Integer getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Integer refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    @JsonProperty("refresh_interval_type")
    public Byte getRefreshIntervalType() {
        return refreshIntervalType;
    }

    public void setRefreshIntervalType(Byte refreshIntervalType) {
        this.refreshIntervalType = refreshIntervalType;
    }
}
