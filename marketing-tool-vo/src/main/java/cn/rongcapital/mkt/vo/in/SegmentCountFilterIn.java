package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SegmentCountFilterIn extends BaseInput {

    @NotEmpty
    private String userToken;

    @NotNull
    private List<Integer> segmentHeadIds;
    
    private int type;
          
    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("segment_head_ids")
    public List<Integer> getSegmentHeadIds() {
        return segmentHeadIds;
    }

    public void setSegmentHeadIds(List<Integer> segmentHeadIds) {
        this.segmentHeadIds = segmentHeadIds;
    }

    @JsonProperty("type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
}
