package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-6-28.
 */
public interface WechatAssetMemberSearchService {
    BaseOutput searchWechatAssetMember(String groupIds,String searchField);
}
