package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class CampaignBodyItemAudienceSearchOut extends BaseOutput {
	
	private List<CampaignBodyItemAudienceSearchData> dataCustom = new ArrayList<CampaignBodyItemAudienceSearchData>();

	public CampaignBodyItemAudienceSearchOut(int code, String msg, int total) {
		super(code, msg, total, null);
	}

	@JsonProperty("data")
	public List<CampaignBodyItemAudienceSearchData> getDataCustom() {
		return dataCustom;
	}

	public void setDataCustom(List<CampaignBodyItemAudienceSearchData> dataCustom) {
		this.dataCustom = dataCustom;
	}
	

}
