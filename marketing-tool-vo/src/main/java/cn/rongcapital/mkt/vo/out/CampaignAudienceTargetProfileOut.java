package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by ethan on 16/7/26.
 */
public class CampaignAudienceTargetProfileOut {

    @JsonProperty("audience_name")
    private String audienceName;

    @JsonProperty("population_count")
    private Long populationCount;

    public String getAudienceName() {
        return audienceName;
    }

    public void setAudienceName(String audienceName) {
        this.audienceName = audienceName;
    }

    public Long getPopulationCount() {
        return populationCount;
    }

    public void setPopulationCount(Long populationCount) {
        this.populationCount = populationCount;
    }
}
