package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.HomePageCalendarPopOut;

public interface HomePageCalendarPopService {

    /**
     * @author nianjun
     * 
     * mkt.homepage.datacount.pop
     * 
     * 获取当月日历日被客户标记当日定时的活动的弹窗
     * 
     * @return HomePageCalendarPopOut
     */
    public HomePageCalendarPopOut getCalendarPop(String date);

}
