package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by ethan on 16/7/25.
 */
public class SegmentDimensionCountOut {

    @JsonProperty("dimension_name")
    private String dimensionName;

    @JsonProperty("population_count")
    private Integer populationCount;

    public SegmentDimensionCountOut() {}

    public SegmentDimensionCountOut(String dimensionName, Integer populationCount) {
        this.dimensionName = dimensionName;
        this.populationCount = populationCount;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public Integer getPopulationCount() {
        return populationCount;
    }

    public void setPopulationCount(Integer populationCount) {
        this.populationCount = populationCount;
    }
}
