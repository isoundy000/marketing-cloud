package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.DataParty;

/**
 * Created by Yunfeng on 2016-9-23.
 */
public interface TagCustomTagToDataPartyService {
    boolean tagCustomTagToDataParty(DataParty dataParty, BaseTag baseTag);
    boolean tagCustomTagToDataPartyById(String tagId,Integer dataPartyId);
}
