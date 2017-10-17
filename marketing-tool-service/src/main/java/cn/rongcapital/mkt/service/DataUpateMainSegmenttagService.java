package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface DataUpateMainSegmenttagService {

    /**
     * mkt.data.main.segmenttag.update
     * 
     * @功能简述 : 给细分人群打标签
     * @author nianjun
     * @param method
     * @param userToken
     * @param ver
     * @param tagName
     * @param contactId
     * @return
     */
    public boolean updateMainSegmenttag(String method, String userToken, String ver, String tagName, Integer contactId);
    
    
    BaseOutput getMainSegmenttagNames(Integer map_id);
    
    
}
