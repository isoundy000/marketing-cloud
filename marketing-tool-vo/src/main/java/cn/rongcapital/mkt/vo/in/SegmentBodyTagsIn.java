/*************************************************
 * @功能简述: VO:SegmentBodyTagsIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentBodyTagsIn {

	@NotNull
	private String tagId;

	@NotNull
	private String tagGroupId;

	@NotNull
	private Integer exclude;

	@NotNull
	private Integer groupSeq;

	@JsonProperty("tag_id")
	public String getTagId() {
		return tagId;
	}

	@JsonProperty("tag_id")
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	@JsonProperty("tag_group_id")
	public String getTagGroupId() {
		return tagGroupId;
	}

	@JsonProperty("tag_group_id")
	public void setTagGroupId(String tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	@JsonProperty("exclude")
	public Integer getExclude() {
		return exclude;
	}

	@JsonProperty("exclude")
	public void setExclude(Integer exclude) {
		this.exclude = exclude;
	}

	@JsonProperty("group_seq")
	public Integer getGroupSeq() {
		return groupSeq;
	}

	@JsonProperty("group_seq")
	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

}
