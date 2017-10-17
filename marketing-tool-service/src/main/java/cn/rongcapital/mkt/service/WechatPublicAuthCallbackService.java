package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatPublicAuthCallbackIn;

/**
 * Created by Yunfeng on 2016-6-17.
 */
public interface WechatPublicAuthCallbackService {
    BaseOutput authWechatPublicCallback(WechatPublicAuthCallbackIn wechatPublicAuthCallbackIn);
}
