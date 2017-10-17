package cn.rongcapital.mkt.vo.out;

import java.util.List;

public class HomePageCalendarPopOut {

    private String date;

    private List<HomePageCalendarPopData> content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<HomePageCalendarPopData> getContent() {
        return content;
    }

    public void setContent(List<HomePageCalendarPopData> content) {
        this.content = content;
    }

}
