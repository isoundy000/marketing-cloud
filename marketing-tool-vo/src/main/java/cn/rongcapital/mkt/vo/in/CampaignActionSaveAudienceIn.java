package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignActionSaveAudienceIn {
	
	private Integer campaignHeadId;
	
    private String itemId;

    private String name;

    private Integer audienceId;

    private String audienceName;

//    private Byte type;// 0:关联已有的audience_list; 1:新增audience_list
    
    
//    public Byte getType() {
//		return type;
//	}
//
//	public void setType(Byte type) {
//		this.type = type;
//	}

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

    @JsonProperty("audience_id")
    public Integer getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(Integer audienceId) {
		this.audienceId = audienceId;
	}

	@JsonProperty("audience_name")
	public String getAudienceName() {
		return audienceName;
	}

	public void setAudienceName(String audienceName) {
		this.audienceName = audienceName;
	}

}
