package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.po.ContactWay;

public interface DataGetFilterContactwayService {

    /**
     * mkt.data.filter.contactway.get
     * 
     * @功能简述 : 查询联系方式
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @return
     */
    public List<ContactWay> getFilterContactway(String method, String userToken, String ver);

}
