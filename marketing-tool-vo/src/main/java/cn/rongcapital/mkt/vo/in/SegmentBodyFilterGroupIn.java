/*************************************************
 * @功能简述: VO:SegmentBodyFilterGroupIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.in;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SegmentBodyFilterGroupIn {

	@NotNull
	private Integer groupIndex;

	@NotEmpty
	@Valid
	private List<SegmentBodyTagsIn> tagList;

	@JsonProperty("group_index")
	public Integer getGroupIndex() {
		return groupIndex;
	}

	@JsonProperty("group_index")
	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}

	@JsonProperty("tag_list")
	public List<SegmentBodyTagsIn> getTagList() {
		return tagList;
	}

	@JsonProperty("tag_list")
	public void setTagList(List<SegmentBodyTagsIn> tagList) {
		this.tagList = tagList;
	}

}
