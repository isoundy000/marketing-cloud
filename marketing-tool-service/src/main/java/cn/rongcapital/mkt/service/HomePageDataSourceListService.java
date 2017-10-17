package cn.rongcapital.mkt.service;
import java.util.List;

import cn.rongcapital.mkt.vo.out.HomePageDataSourceListOut;

public interface HomePageDataSourceListService {

    /**
     * @author nianjun
     * 
     * mkt.homepage.datasource.list
     * 
     * 统计出用户的各类来源，如微信、CRM、POS(以数据文件填写的来源为准)
     * 
     * @return List<HomePageUserCountListOut>
     */
    public List<HomePageDataSourceListOut> getHomePageDataSourceList();

}
