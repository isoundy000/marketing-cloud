package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 10/28/16.
 */
public class TodayOut {

    private String starDate;
    private String today;
    private String endDate;

    @JsonProperty("StarDate")
    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    @JsonProperty("Today")
    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    @JsonProperty("EndDate")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
