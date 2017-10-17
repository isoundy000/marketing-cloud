package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SegmentFilterSumIn extends BaseInput{

    @NotEmpty
    private String userToken;

    @NotNull
    private List<SegmentFilterSumCondition> groups;
          
    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<SegmentFilterSumCondition> getGroups() {
        return groups;
    }

    public void setGroups(List<SegmentFilterSumCondition> groups) {
        this.groups = groups;
    }
}
