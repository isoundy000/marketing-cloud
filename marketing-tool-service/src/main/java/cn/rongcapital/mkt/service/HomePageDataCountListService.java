package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.vo.out.HomePageDataCountListOut;

public interface HomePageDataCountListService {

    /**
     * @author nianjun
     * 
     * mkt.homepage.datacount.list
     * 
     * 统计出当前用户、细分、活动、标签、接入数据个数
     * 
     * @return List<HomePageDataCountListOut>
     */
    public List<HomePageDataCountListOut> getDataCountList();

}
