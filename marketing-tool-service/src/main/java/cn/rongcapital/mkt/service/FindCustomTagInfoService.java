package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.DataParty;

import java.util.List;

/**
 * Created by Yunfeng on 2016-9-23.
 */
public interface FindCustomTagInfoService {
    BaseTag findCustomTagByTagNameAndTagPath(String tagName,String tagPath,String tagSource);
    BaseTag findCustomTagByTag(BaseTag baseTag);
    List<BaseTag> findAllCustomTagLeaf();
    BaseTag findCustomTagInfoByTagId(String tagId);
    List<DataParty> findMDataByTagId(String tagId,Integer pagNum,Integer pageSize);
    Integer queryMDataCountByTagId(String tagId);
}
