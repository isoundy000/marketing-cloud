package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.dao.WechatPersonalUuidDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-22.
 */
@Service
public class GetUUidListServiceImpl {

    @Autowired
    private WechatPersonalUuidDao wechatPersonalUuidDao;

    public List<String> getUuidList(){
        List<Map<String,String>> uuidLists = wechatPersonalUuidDao.selectEffectiveUuids();
        List<String> resultList = new ArrayList<String>();
        for(Map<String,String> map : uuidLists){
            resultList.add(map.get("uuid"));
        }
        return resultList;
    }
}
