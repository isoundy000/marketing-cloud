package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface DataDownloadQualityLogService {

    /**
     * mkt.data.quality.log.download
     * 
     * @功能简述 : 下载数据质量报告日志
     * @author nianjun
     * @param int
     * @return
     * 
     */
    public BaseOutput downloadQualityLog(Long importDataId);

}
