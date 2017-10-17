package cn.rongcapital.mkt.biz;

import java.util.List;

import cn.rongcapital.mkt.po.WechatGroup;

public interface WechatGroupBiz {

	public List<WechatGroup> getTags(String authAppId,String authorizer_refresh_token);
		
}
