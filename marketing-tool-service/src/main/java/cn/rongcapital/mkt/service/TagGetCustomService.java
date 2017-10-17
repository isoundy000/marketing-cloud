package cn.rongcapital.mkt.service;

import java.util.List;

public interface TagGetCustomService {

    /**
     * mkt.tag.user.custom.get
     * 
     * @author nianjun
     * @功能简述 : 获取用户自定义标签
     * @param contactId
     * @return
     */
    public List<String> getCustomizeTagByContactId(String ver, Integer contactId);
}
