package cn.rongcapital.mkt.service;


import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.in.Audience;

/**
 * Created by Xukun on 2016-6-28.
 */
public interface SaveCampaignAudienceService {
    Object saveCampaignAudience(Audience audience,SecurityContext securityContext);
}
