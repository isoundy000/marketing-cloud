package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.common.jedis.JedisException;

/**
 * Created by Yunfeng on 2016-6-1.
 */
public interface WechatAssetListGetService {
    Object getWechatAssetList(Integer assetId)  throws JedisException;
}
