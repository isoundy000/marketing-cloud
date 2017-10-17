package cn.rongcapital.mkt.service;

/**
 * Created by Yunfeng on 2016-5-31.
 */
public interface WechatAssetListService {
    Object getWechatAssetListByType(Integer assetType,Integer index,Integer size);
    
    Object getWechatAssetList(Integer index,Integer size);
}
