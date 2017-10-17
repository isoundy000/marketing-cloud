/*************************************************
 * @功能简述: VO:SegmentBodyGetOut
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.out;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentBodyGetOut {

	private Integer groupIndex;

	private List<SegmentBodyTagsOut> tagList;

	@JsonProperty("group_index")
	public Integer getGroupIndex() {
		return groupIndex;
	}

	@JsonProperty("group_index")
	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}

	@JsonProperty("tag_list")
	public List<SegmentBodyTagsOut> getTagList() {
		return tagList;
	}

	@JsonProperty("tag_list")
	public void setTagList(List<SegmentBodyTagsOut> tagList) {
		this.tagList = tagList;
	}

}
