package cn.rongcapital.mkt.service;

public interface DataGetMainListService {

    /**
     * mkt.data.main.count.get
     * 
     * @author nianjun
     * @功能简述 : 获取主数据列表
     * @param method
     * @param userToken
     * @param dataType
     * @param index
     * @param size
     * @param ver
     * @return
     */
    public Object getMainList(String method, String userToken, Integer dataType, Integer index,
                    Integer size, String ver);
}
