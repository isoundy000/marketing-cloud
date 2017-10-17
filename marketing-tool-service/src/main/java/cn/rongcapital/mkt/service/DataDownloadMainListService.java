package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface DataDownloadMainListService {

    /**
     * mkt.data.main.list.download
     * 
     * @author nianjun
     * @功能简述 : 下载主数据列表
     * @param method
     * @param userToken
     * @param dataType
     * @return
     */
    public BaseOutput downloadMainList(String userToken, Integer dataType);
}
