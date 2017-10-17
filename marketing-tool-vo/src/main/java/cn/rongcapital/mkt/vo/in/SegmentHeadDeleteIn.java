/*************************************************
 * @功能简述: VO:SegmentHeadIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class SegmentHeadDeleteIn extends BaseInput {
	
	@NotNull
	private Integer segmentId;

    @NotEmpty
    private String userToken = null;

    @JsonProperty("user_token")
    public String getUserToken() {
	    return userToken;
    }
  
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

	public Integer getSegmentId() {
		return segmentId;
	}

	@JsonProperty("segment_head_id")
	public void setSegmentId(Integer segmentId) {
		this.segmentId = segmentId;
	}
  
}

