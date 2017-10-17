package cn.rongcapital.mkt.dao;

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.CampaignActionSendPub;
import cn.rongcapital.mkt.po.UserInfo;

public interface UserInfoDao extends BaseDao<CampaignActionSendPub>{
    
    /**
     * @Title: getUserInfo   
     * @Description: 通过userId查询用户信息  
     * @param: @param userId
     * @param: @return      
     * @return: UserInfo      
     * @throws
     */
    public UserInfo getUserInfo(@Param("userId") String userId);
    
}