package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class SerarchTagGroupTagsOut extends BaseOutput {
	
	List<SerarchTagGroupTagsDataOut> dataCustom = new ArrayList<SerarchTagGroupTagsDataOut>();
			
	public SerarchTagGroupTagsOut(int code, String msg, int total) {
		super(code, msg, total, null);
	}

	@JsonProperty("data")
	public List<SerarchTagGroupTagsDataOut> getDataCustom() {
		return dataCustom;
	}

	public void setDataCustom(List<SerarchTagGroupTagsDataOut> dataCustom) {
		this.dataCustom = dataCustom;
	}

}
