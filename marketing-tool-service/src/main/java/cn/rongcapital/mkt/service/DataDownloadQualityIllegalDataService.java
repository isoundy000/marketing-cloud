package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface DataDownloadQualityIllegalDataService {

    /**
     * mkt.data.quality.illegaldata.download    
     * 
     * @功能简述 : 下载非法数据
     * @author nianjun
     * @return
     * 
     */
    BaseOutput downloadIllegalData(Long batchId, String type);

}
