package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by zhaoguoying on 2016-08-11.
 */
public class WechatChanellUpdateOut {
	
	private Integer id;
	private String updatetime;
	
	@JsonProperty("id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonProperty("updatetime")
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
}
