package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class HomePageCalendarData {

    private String activeDay;

    private int status;
    
    private String statusDescription;

    @JsonProperty("active_day")
    public String getActiveDay() {
        return activeDay;
    }

    public void setActiveDay(String activeDay) {
        this.activeDay = activeDay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty("status_description")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
    
}
