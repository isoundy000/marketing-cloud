package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 10/28/16.
 */
public class Day30Out {

    private String starDate;
    private String day30;
    private String endDate;

    @JsonProperty("StarDate")
    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    @JsonProperty("Day30")
    public String getDay30() {
        return day30;
    }

    public void setDay30(String day30) {
        this.day30 = day30;
    }

    @JsonProperty("EndDate")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
