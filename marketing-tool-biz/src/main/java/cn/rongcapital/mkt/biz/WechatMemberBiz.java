package cn.rongcapital.mkt.biz;

import java.util.List;

import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.user.UserInfo;

//import com.tagsin.wechat_sdk.App;

import cn.rongcapital.mkt.po.WechatMember;

public interface WechatMemberBiz {

	/**
	 * 获取公众号下的粉丝列表：1：获取粉丝openID集合，2：轮询获取粉丝详细信息
	 * @param authorizer_appid
	 * @param authRefreshToken
	 * @return
	 */
	public List<WechatMember> getUserList(String authorizer_appid, String authRefreshToken);
	
	/**
	 * 提供给粉丝扫描关注公众号的获取粉丝信息接口
	 * @param app
	 * @param openid
	 * @return
	 */
	public UserInfo getUserInfoeByOpenid(App app, String openid);

}
