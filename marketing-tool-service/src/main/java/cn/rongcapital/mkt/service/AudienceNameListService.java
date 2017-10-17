package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface AudienceNameListService {

    
    /**
     * mkt.dataanalysis.audname.list.get
     * 
     * 获取数据分析人群list列表
     * 
     * @author xukun
     * @param userToken     * 
     * @return
     */
    public BaseOutput audienceNameList(String userToken);

}
