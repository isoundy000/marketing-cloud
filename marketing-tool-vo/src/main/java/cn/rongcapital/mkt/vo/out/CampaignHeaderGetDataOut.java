package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignHeaderGetDataOut {

	private String campaignName;
	
	private Byte publishStatus;
	
	private Integer id;
	
	private String oper;
	
	private String updatetime;

	@JsonProperty("campaign_name")
	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	@JsonProperty("publish_status")
	public Byte getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Byte publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
