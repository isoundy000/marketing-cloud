package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SerarchTagGroupTagsDataOut {

	private String tagGroupName;
	
	private Integer tagGroupId;
	
	private List<TagOut> tagList = new ArrayList<TagOut>();

	@JsonProperty("tag_group_name")
	public String getTagGroupName() {
		return tagGroupName;
	}

	public void setTagGroupName(String tagGroupName) {
		this.tagGroupName = tagGroupName;
	}

	@JsonProperty("tag_group_id")
	public Integer getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(Integer tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	@JsonProperty("tag_list")
	public List<TagOut> getTagList() {
		return tagList;
	}

	public void setTagList(List<TagOut> tagList) {
		this.tagList = tagList;
	} 
	
	
}
