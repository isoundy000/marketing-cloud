/*************************************************
 * @功能简述: VO:SegmentTagGetOut
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/7
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentTagGetOut {

	private String tagId;

	private String tagName;

	@JsonProperty("tag_id")
	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	@JsonProperty("tag_name")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
