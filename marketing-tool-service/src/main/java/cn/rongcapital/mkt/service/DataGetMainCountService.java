package cn.rongcapital.mkt.service;

public interface DataGetMainCountService {

    /**
     * mkt.data.main.count.get
     * 
     * @功能简述 : 获取主数据条数
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @return
     */
    public Object getMainCount(String method, String userToken, String ver);
}
