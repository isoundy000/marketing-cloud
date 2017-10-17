package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface GetUserInfoService {
    
    /**
     * @Title: getUserInfo   
     * @Description: 通过userId查询用户信息
     * @param: @param userId
     * @param: @return      
     * @return: UserInfo      
     * @throws
     */
     BaseOutput getUserInfo(String userId);

}
