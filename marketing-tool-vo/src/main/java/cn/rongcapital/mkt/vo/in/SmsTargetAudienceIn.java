package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 10/18/16.
 */
public class SmsTargetAudienceIn {
    private Long targetAudienceId;
    private Integer targetAudienceType;
    private String targetAudienceName;
    private String targetAudienceOriginalName;

    @JsonProperty("target_audience_id")
    public Long getTargetAudienceId() {
        return targetAudienceId;
    }

    public void setTargetAudienceId(Long targetAudienceId) {
        this.targetAudienceId = targetAudienceId;
    }

    @JsonProperty("target_audience_type")
    public Integer getTargetAudienceType() {
        return targetAudienceType;
    }

    public void setTargetAudienceType(Integer targetAudienceType) {
        this.targetAudienceType = targetAudienceType;
    }

    @JsonProperty("target_audience_name")
    public String getTargetAudienceName() {
        return targetAudienceName;
    }

    public void setTargetAudienceName(String targetAudienceName) {
        this.targetAudienceName = targetAudienceName;
    }

    @JsonProperty("target_audience_original_name")
    public String getTargetAudienceOriginalName() {
        return targetAudienceOriginalName;
    }

    public void setTargetAudienceOriginalName(String targetAudienceOriginalName) {
        this.targetAudienceOriginalName = targetAudienceOriginalName;
    }
}
