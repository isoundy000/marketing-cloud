package cn.rongcapital.mkt.biz;

import cn.rongcapital.mkt.po.WechatRegister;

public interface WechatRegisterBiz {

	public WechatRegister getAuthInfo(String authAppId, String refreshToken);
}
