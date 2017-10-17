package cn.rongcapital.mkt.vo.out;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class HomePageCalendarListOut {

    private String today;
    
    private String currentMonth;

    private List<HomePageCalendarData> calendarData;

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    @JsonProperty("calendar_data")
    public List<HomePageCalendarData> getCalendarData() {
        return calendarData;
    }

    public void setCalendarData(List<HomePageCalendarData> calendarData) {
        this.calendarData = calendarData;
    }
    
    @JsonProperty("current_month")
	public String getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}
    
    

}
