package cn.rongcapital.mkt.vo.weixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WXFansListVO {

	private String pubId;
	
	private List<Map<String, Object>> fansList = new ArrayList<Map<String, Object>>();

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public List<Map<String, Object>> getFansList() {
		return fansList;
	}

	public void setFansList(List<Map<String, Object>> fansList) {
		this.fansList = fansList;
	}
	
}
