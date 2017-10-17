package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.dao.WechatGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-22.
 */
@Service
public class GetGroupIdServiceImpl {

    @Autowired
    private WechatGroupDao wechatGroupDao;

    public Integer getGroupIdByOwnerIdAndGroupname(String ownerId , String groupName){
        //1.查询是否存在这个组
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("wx_acct",ownerId);
        paramMap.put("group_name",groupName);
        Integer groupId = wechatGroupDao.selectGroupId(paramMap);
        //2.存在返回groupId，不存在插入这个组然后返回groupId
        if(groupId == null){
            wechatGroupDao.insertWechatGroup(paramMap);
            groupId = wechatGroupDao.selectGroupId(paramMap);
        }

        return groupId;
    }

    public Long getGroupIdByOwnerIdAndGroupId(String uin, String ucode) {
        //1.查询是否存在这个组
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("wx_acct",uin);
        paramMap.put("group_id",ucode);
        Long groupId = wechatGroupDao.selectGroupIdByUcode(paramMap);
        //2.存在返回groupId，不存在插入这个组然后返回groupId
        if(groupId == null){
            wechatGroupDao.insertWechatGroupByUcode(paramMap);
            groupId = wechatGroupDao.selectGroupIdByUcode(paramMap);
        }

        return groupId;
    }
}
