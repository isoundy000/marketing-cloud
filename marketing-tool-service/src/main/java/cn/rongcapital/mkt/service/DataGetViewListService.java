package cn.rongcapital.mkt.service;

public interface DataGetViewListService {

    /**
     * mkt.data.view.list.get
     * 
     * @功能简述 : 查询自定义视图字段列表
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @param mdType
     * @return
     */
    public Object getViewList(String method, String userToken, String ver, Integer mdType);
}
