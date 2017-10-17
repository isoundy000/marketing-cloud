package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by ethan on 16/7/26.
 */
public class CampaignProfileOut extends BaseOutput {

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("campaign_audience_target")
    private List<CampaignAudienceTargetProfileOut> audienceTargetList;

    @JsonProperty("campaign_content")
    private List<CampaignContentOut> contentList;
    
    @JsonProperty("campaign_name")
    private String campaignName;

//    @JsonProperty("campaign_touch_population")
//    private List<CampaignTouchPopulationOut> touchPopulationList;

    private String sendChannel = "微信";

    public CampaignProfileOut() {}
    public CampaignProfileOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<CampaignAudienceTargetProfileOut> getAudienceTargetList() {
        return audienceTargetList;
    }

    public void setAudienceTargetList(List<CampaignAudienceTargetProfileOut> audienceTargetList) {
        this.audienceTargetList = audienceTargetList;
    }

    public List<CampaignContentOut> getContentList() {
        return contentList;
    }

    public void setContentList(List<CampaignContentOut> contentList) {
        this.contentList = contentList;
    }
    
//    public List<CampaignTouchPopulationOut> getTouchPopulationList() {
//        return touchPopulationList;
//    }
//
//    public void setTouchPopulationList(List<CampaignTouchPopulationOut> touchPopulationList) {
//        this.touchPopulationList = touchPopulationList;
//    }

    public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getSendChannel() {
        return sendChannel;
    }

    public void setSendChannel(String sendChannel) {
        this.sendChannel = sendChannel;
    }
}
