package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.SaveWechatAssetListIn;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-6-1.
 */
public interface SaveWechatAssetListService {
    Object saveWechatAssetList(SaveWechatAssetListIn saveWechatAssetListIn,SecurityContext securityContext);
}
