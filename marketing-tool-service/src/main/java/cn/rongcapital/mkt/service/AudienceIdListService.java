package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface AudienceIdListService {

    
    /**
     * mkt.dataanalysis.audname.list.get
     * 
     * 获取数据分析联系人Id列表
     * 
     * @author xukun
     * @param userToken     * 
     * @return
     */
    public BaseOutput audienceIdList(String userToken,String audience_ids,String audience_type);

}
