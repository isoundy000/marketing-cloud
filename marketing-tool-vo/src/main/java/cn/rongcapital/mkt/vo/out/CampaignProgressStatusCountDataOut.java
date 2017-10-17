package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignProgressStatusCountDataOut {

    private Integer count; 
    
	private Byte publishStatus;

	@JsonProperty("count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@JsonProperty("publish_status")
	public Byte getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Byte publishStatus) {
		this.publishStatus = publishStatus;
	}
	
}
