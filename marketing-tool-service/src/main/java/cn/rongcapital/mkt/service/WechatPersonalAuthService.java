package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatPersonalAuthIn;

/**
 * Created by Yunfeng on 2016-6-22.
 */
public interface WechatPersonalAuthService {
    BaseOutput authPersonWechat(WechatPersonalAuthIn wechatPersonalAuthIn);
}
