/*************************************************
 * @功能简述: VO:SegmentBodyUpdateIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.in;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class SegmentBodyUpdateIn extends BaseInput{

	@NotNull
	private Integer segmentHeadId;

//	@NotEmpty
//	@Valid
	private List<SegmentBodyFilterGroupIn> filterGroups;

	@NotEmpty
	private String userToken = null;

	@JsonProperty("segment_head_id")
	public Integer getSegmentHeadId() {
		return segmentHeadId;
	}

	@JsonProperty("segment_head_id")
	public void setSegmentHeadId(Integer segmentHeadId) {
		this.segmentHeadId = segmentHeadId;
	}

	@JsonProperty("filter_groups")
	public List<SegmentBodyFilterGroupIn> getFilterGroups() {
		return filterGroups;
	}

	@JsonProperty("filter_groups")
	public void setFilterGroups(List<SegmentBodyFilterGroupIn> filterGroups) {
		this.filterGroups = filterGroups;
	}
	
	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	@JsonProperty("user_token")
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

}
