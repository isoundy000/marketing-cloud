package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

public class CampaignNodeListOut {
	
	private byte type;

	private int index;
	
	private String name;
	
	private String code;
	
	private String icon;
	
	private String url;
	
	private String color;
	
	private List<CampaignItemListOut> children = new ArrayList<CampaignItemListOut>();

	public List<CampaignItemListOut> getChildren() {
		return children;
	}

	public void setChildren(List<CampaignItemListOut> children) {
		this.children = children;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

}
