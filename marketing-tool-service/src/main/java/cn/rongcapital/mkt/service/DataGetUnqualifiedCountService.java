package cn.rongcapital.mkt.service;

public interface DataGetUnqualifiedCountService {

    /**
     * mkt.data.unqualified.count.get
     * 
     * @功能简述 : 获取非法数据条数
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @param batchId
     * @return
     */
    public Object getQualityCount(String method, String userToken, String ver, Long batchId);
}
