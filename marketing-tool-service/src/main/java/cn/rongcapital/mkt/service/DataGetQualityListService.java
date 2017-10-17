package cn.rongcapital.mkt.service;

public interface DataGetQualityListService {

    /**
     * mkt.data.quality.list.get
     * 
     * @功能简述 : 数据质量报告
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @return
     */
    public Object getQualityList(String method, String userToken, Integer index, Integer size,
                    String ver);
}
