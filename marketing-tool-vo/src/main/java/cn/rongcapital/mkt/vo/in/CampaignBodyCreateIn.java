package cn.rongcapital.mkt.vo.in;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignBodyCreateIn extends BaseInput {

	@NotNull
	private Integer campaignHeadId;
	@NotEmpty
	private String userToken = null;
	
	private List<CampaignNodeChainIn> campaignNodeChain = new ArrayList<CampaignNodeChainIn>();

	@JsonProperty("campaign_node_chain")
	public List<CampaignNodeChainIn> getCampaignNodeChain() {
		return campaignNodeChain;
	}

	public void setCampaignNodeChain(List<CampaignNodeChainIn> campaignNodeChain) {
		this.campaignNodeChain = campaignNodeChain;
	}

	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	@JsonProperty("campaign_head_id")
	public Integer getCampaignHeadId() {
		return campaignHeadId;
	}

	public void setCampaignHeadId(Integer campaignHeadId) {
		this.campaignHeadId = campaignHeadId;
	}
	
}
