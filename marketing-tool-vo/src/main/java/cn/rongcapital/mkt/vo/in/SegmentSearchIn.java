package cn.rongcapital.mkt.vo.in;

import java.util.List;


public class SegmentSearchIn {

	private List<Integer> headidList;
	
    private String queryName;

	public List<Integer> getHeadidList() {
		return headidList;
	}

	public void setHeadidList(List<Integer> headidList) {
		this.headidList = headidList;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
}