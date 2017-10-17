package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class SegmentPublishstatusListOut extends BaseOutput {

	
	List<SegmentPublishstatusListDataOut> dataCustom = new ArrayList<SegmentPublishstatusListDataOut>();
	
	public SegmentPublishstatusListOut(int code, String msg, int total){
		super(code, msg, total, null);
	}

	@JsonProperty("data")
	public List<SegmentPublishstatusListDataOut> getDataCustom() {
		return dataCustom;
	}

	public void setDataCustom(List<SegmentPublishstatusListDataOut> dataCustom) {
		this.dataCustom = dataCustom;
	}
	
	
}
