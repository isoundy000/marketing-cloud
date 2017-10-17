package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface SegmentSearchGetService {

    
    /**
     * mkt.segment.search.get
     * 
     * 下载人群管理详情
     * 
     * @author chengjincheng
     * @param      * 
     * @return
     */
    public BaseOutput SegmentSearch(Integer head_id,String query_name);

}