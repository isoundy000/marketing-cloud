package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 10/28/16.
 */
public class RecordScopeOut {

    private String starDate;
    private String recordScope;
    private String endDate;

    @JsonProperty("StarDate")
    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    @JsonProperty("RecordScope")
    public String getRecordScope() {
        return recordScope;
    }

    public void setRecordScope(String recordScope) {
        this.recordScope = recordScope;
    }

    @JsonProperty("EndDate")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
