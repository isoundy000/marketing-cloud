package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.ModifyInput;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-5-26.
 */
public interface ModifyPasswdService {

    public Object modifyPasswd(ModifyInput input, SecurityContext securityContext);

}
