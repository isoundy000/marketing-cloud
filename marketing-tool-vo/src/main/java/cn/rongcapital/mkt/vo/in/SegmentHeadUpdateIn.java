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

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class SegmentHeadUpdateIn extends BaseInput {
	
	@NotNull
	private Integer segmentId;

	@NotNull
    private Integer publishStatus = null;
  
    @NotEmpty
    private String segmentName = null;
  
    @NotEmpty
    private String userToken = null;
  
    @JsonProperty("publish_status")
    public Integer getPublishStatus() {
	    return publishStatus;
  	}
  
  	public void setPublishStatus(Integer publishStatus) {
  		this.publishStatus = publishStatus;
  	}
  
	@JsonProperty("segment_name")
  	public String getSegmentName() {
	  	return segmentName;
  	}
  
    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }
  
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

