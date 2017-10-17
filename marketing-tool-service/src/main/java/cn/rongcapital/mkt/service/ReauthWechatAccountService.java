package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.in.ReauthWechatAccountIn;

import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-23.
 */
public interface ReauthWechatAccountService {

    Map<String,Object> reauthWechatAccount(String assetId);
}
