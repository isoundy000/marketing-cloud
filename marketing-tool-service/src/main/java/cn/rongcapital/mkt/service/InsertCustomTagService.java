package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.base.BaseTag;

/**
 * Created by Yunfeng on 2016-9-21.
 */
public interface InsertCustomTagService {
    BaseTag insertCustomTag(BaseTag baseTag);
    BaseTag cascadingInsertCustomTag(BaseTag baseTag);
    BaseTag insertCustomTagLeafFromSystemIn(String tagName,String tagSource);
}
