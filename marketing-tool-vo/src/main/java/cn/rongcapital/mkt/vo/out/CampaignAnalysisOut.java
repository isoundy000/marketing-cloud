package cn.rongcapital.mkt.vo.out;

import java.util.List;

/**
 * Created by ethan on 16/7/26.
 */
public class CampaignAnalysisOut {

    private List<CampaignAnalysisDimensionSpanOut> dayList;

    private List<CampaignAnalysisDimensionSpanOut> weekList;

    private List<CampaignAnalysisDimensionSpanOut> monthList;

    public List<CampaignAnalysisDimensionSpanOut> getDayList() {
        return dayList;
    }

    public void setDayList(List<CampaignAnalysisDimensionSpanOut> dayList) {
        this.dayList = dayList;
    }

    public List<CampaignAnalysisDimensionSpanOut> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<CampaignAnalysisDimensionSpanOut> monthList) {
        this.monthList = monthList;
    }

    public List<CampaignAnalysisDimensionSpanOut> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<CampaignAnalysisDimensionSpanOut> weekList) {
        this.weekList = weekList;
    }

}
