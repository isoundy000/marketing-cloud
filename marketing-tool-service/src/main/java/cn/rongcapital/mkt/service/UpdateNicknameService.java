package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.UpdateNicknameIn;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-6-1.
 */
public interface UpdateNicknameService {
    Object updateNickname(UpdateNicknameIn updateNicknameIn,SecurityContext securityContext);
}
