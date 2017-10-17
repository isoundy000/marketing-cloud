package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 10/28/16.
 */
public class YestodayOut {

    private String starDate;
    private String Yestoday;
    private String EndDate;


    @JsonProperty("StarDate")
    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    @JsonProperty("Yestoday")
    public String getYestoday() {
        return Yestoday;
    }

    public void setYestoday(String yestoday) {
        Yestoday = yestoday;
    }

    @JsonProperty("EndDate")
    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
