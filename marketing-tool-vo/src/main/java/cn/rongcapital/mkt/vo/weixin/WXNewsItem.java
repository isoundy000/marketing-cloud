package cn.rongcapital.mkt.vo.weixin;

import org.codehaus.jackson.annotate.JsonProperty;

public class WXNewsItem {

	private String title;
	
	private String author;
	
	private String digest;
	
	private String content;
	
	private String content_source_url;
	
	private String thumb_media_id;
	
	private int show_cover_pic;
	
	private String url;
	
	private String thumb_url;

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@JsonProperty("digest")
	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	@JsonProperty("content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty("content_source_url")
	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	@JsonProperty("thumb_media_id")
	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	@JsonProperty("show_cover_pic")
	public int getShow_cover_pic() {
		return show_cover_pic;
	}

	public void setShow_cover_pic(int show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("thumb_url")
	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	
}
