/*************************************************
 * @功能简述: VO:SegmentTagUpdateIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.in;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class SegmentTagUpdateIn extends BaseInput{

	@NotEmpty
	private String segmentHeadId;

	private List<String> tagNames;

	@NotEmpty
	private String userToken;

	@JsonProperty("segment_head_id")
	public String getSegmentHeadId() {
		return segmentHeadId;
	}

	public void setSegmentHeadId(String segmentHeadId) {
		this.segmentHeadId = segmentHeadId;
	}

	@JsonProperty("tag_names")
	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
}
