package cn.rongcapital.mkt.vo.in;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataGetFilterAudiencesIn extends BaseInput {
    private String userToken = null;

    @NotNull
    private Integer mdType;
    
    private Integer contactWayList;

    private List<Integer> mdTypes;

    private List<Integer> contactIds;

    private Integer timeCondition;

    private List<CustomizeViewCheckboxIn> customizeViews;

    private String method;

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("md_type")
    public Integer getMdType() {
        return mdType;
    }

    public void setMdType(Integer mdType) {
        this.mdType = mdType;
    }
    
    @JsonProperty("contact_wayList")
    public Integer getContactWayList() {
		return contactWayList;
	}

	public void setContactWayList(Integer contactWayList) {
		this.contactWayList = contactWayList;
	}

	@JsonProperty("contact_ids")
    public List<Integer> getContactIds() {
        return contactIds;
    }

    public void setContactIds(List<Integer> contactIds) {
        this.contactIds = contactIds;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @JsonProperty("customize_views")
    public List<CustomizeViewCheckboxIn> getCustomizeViews() {
        return customizeViews;
    }

    public void setCustomizeViews(List<CustomizeViewCheckboxIn> customizeViews) {
        this.customizeViews = customizeViews;
    }

    @JsonProperty("md_types")
    public List<Integer> getMdTypes() {
        return mdTypes;
    }

    public void setMdTypes(List<Integer> mdTypes) {
        this.mdTypes = mdTypes;
    }

    @JsonProperty("time_condition")
    public Integer getTimeCondition() {
        return timeCondition;
    }

    public void setTimeCondition(Integer timeCondition) {
        this.timeCondition = timeCondition;
    }
}
