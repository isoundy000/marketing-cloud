package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.po.ImportTemplate;

public interface DataUpateViewListService {

    /**
     * mkt.data.view.list.update
     * 
     * @功能简述 : 保存自定义视图字段
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @param importTemplates
     * @return boolean
     */
    public boolean getViewList(String method, String userToken, String ver,
                    List<ImportTemplate> importTemplates);

}
