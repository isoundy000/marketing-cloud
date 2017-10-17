package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface AudienceSearchDownloadService {

    
    /**
     * mkt.audience.search.download
     * 
     * 下载人群管理详情
     * 
     * @author chengjincheng
     * @param userToken     * 
     * @return
     */
    public BaseOutput searchData(Integer audience_id);

}