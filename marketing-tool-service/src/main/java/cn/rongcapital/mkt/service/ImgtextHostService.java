package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.ImgtextHostIn;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-5-30.
 */
public interface ImgtextHostService {
    Object hostImgtextAsset(ImgtextHostIn imgtextHostIn,SecurityContext securityContext);
}
