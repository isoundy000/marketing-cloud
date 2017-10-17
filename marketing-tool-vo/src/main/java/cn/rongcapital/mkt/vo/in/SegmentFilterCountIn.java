package cn.rongcapital.mkt.vo.in;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class SegmentFilterCountIn extends BaseInput{

    @NotEmpty
    private String userToken;

    @NotNull
    private List<SegmentFilterCondition> conditions;
          
    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    
    
    
    @JsonProperty("conditions")
    public List<SegmentFilterCondition> getConditions() {
        return conditions;
    }

    
    public void setConditions(List<SegmentFilterCondition> conditions) {
        this.conditions = conditions;
    }
		
	
}
