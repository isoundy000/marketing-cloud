package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class CampaignHeaderGetOut extends BaseOutput {

	private List<CampaignHeaderGetDataOut> dataCutom = new ArrayList<CampaignHeaderGetDataOut>();
	
	public CampaignHeaderGetOut(int code, String msg, int total) {
		super(code, msg, total, null);
	}

	@JsonProperty("data")
	public List<CampaignHeaderGetDataOut> getDataCutom() {
		return dataCutom;
	}

	public void setDataCutom(List<CampaignHeaderGetDataOut> dataCutom) {
		this.dataCutom = dataCutom;
	}
	
	
}
