package cn.rongcapital.mkt.service;

public interface DataDeleteMainService {

    /**
     * mkt.data.main.delete
     * 
     * @功能简述 : 删除某条主数据
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @param dataId
     * @return
     */
    public Object deleteMain(String method, String userToken, String ver, Integer dataId);
}
