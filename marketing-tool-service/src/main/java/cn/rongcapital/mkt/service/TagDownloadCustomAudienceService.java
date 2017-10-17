package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagDownloadCustomAudienceService {

    /**
     * mkt.tag.user.custom.get
     * 
     * @author nianjun
     * @功能简述 : 下载自定义标签下覆盖的人群
     * @param contactId
     * @return
     */
    public BaseOutput downloadCustomAudience(String tagId);
}
