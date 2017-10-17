package cn.rongcapital.mkt.service;

public interface DataGetQualityCountService {

    /**
     * mkt.data.quality.count.get
     * 
     * @功能简述 : 获取数据接入条数
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @return
     */
    public Object getQualityCount(String method, String userToken, String ver);
}
