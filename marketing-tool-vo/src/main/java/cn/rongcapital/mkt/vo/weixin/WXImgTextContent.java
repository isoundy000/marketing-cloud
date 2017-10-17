package cn.rongcapital.mkt.vo.weixin;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class WXImgTextContent {

	private List<WXNewsItem> newsItem;
	
	private long create_time;
	
	private long update_time;

	@JsonProperty("news_item")
	public List<WXNewsItem> getNewsItem() {
		return newsItem;
	}

	public void setNewsItem(List<WXNewsItem> newsItem) {
		this.newsItem = newsItem;
	}

	@JsonProperty("create_time")
	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	@JsonProperty("update_time")
	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}
	
	
}
