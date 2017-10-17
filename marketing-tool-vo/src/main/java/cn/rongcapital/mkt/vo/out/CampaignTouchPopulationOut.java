package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by ethan on 16/7/26.
 */
public class CampaignTouchPopulationOut {

    @JsonProperty("audience_name")
    private String populationName;

    @JsonProperty("population_count")
    private Integer populationCount;

    public String getPopulationName() {
        return populationName;
    }

    public void setPopulationName(String populationName) {
        this.populationName = populationName;
    }

    public Integer getPopulationCount() {
        return populationCount;
    }

    public void setPopulationCount(Integer populationCount) {
        this.populationCount = populationCount;
    }
}
