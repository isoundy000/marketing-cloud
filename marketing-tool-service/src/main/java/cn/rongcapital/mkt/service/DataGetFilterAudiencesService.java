package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.vo.in.CustomizeViewCheckboxIn;

public interface DataGetFilterAudiencesService {

    /**
     * mkt.data.filter.audiences.get
     * 
     * @功能简述 : 根据快捷筛选查询某类型的主数据
     * @author nianjun
     * @param <T>
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @param mdType
     * @param dataTypeList
     * @return
     * 
     */
    public <T extends BaseQuery> Object getFilterAudiences(String method, String userToken, String ver, Integer index,
                    Integer size, Integer mdType, List<Integer> dataTypeList, List<Integer> contactIds,
                    List<CustomizeViewCheckboxIn> customizeViews, Integer timeCondition,Integer contactWayList);
}
