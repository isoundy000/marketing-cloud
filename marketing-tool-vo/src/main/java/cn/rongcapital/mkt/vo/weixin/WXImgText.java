package cn.rongcapital.mkt.vo.weixin;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class WXImgText {

	String media_id;
	
	private WXImgTextContent content;
	
	long update_time;

	@JsonProperty("media_id")
	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	@JsonProperty("update_time")
	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public WXImgTextContent getContent() {
		return content;
	}

	public void setContent(WXImgTextContent content) {
		this.content = content;
	}

	
	
}
