package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignActionSaveAudienceOut {
	
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
