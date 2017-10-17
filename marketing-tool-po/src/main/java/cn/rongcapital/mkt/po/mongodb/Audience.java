package cn.rongcapital.mkt.po.mongodb;

import org.springframework.data.mongodb.core.mapping.Field;

public class Audience {

	@Field(value = "audience_id")
	private Integer audienceId;
	
	@Field(value = "audience_name")
	private String audienceName;

	public Integer getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(Integer audienceId) {
		this.audienceId = audienceId;
	}

	public String getAudienceName() {
		return audienceName;
	}

	public void setAudienceName(String audienceName) {
		this.audienceName = audienceName;
	}
	
	
}
