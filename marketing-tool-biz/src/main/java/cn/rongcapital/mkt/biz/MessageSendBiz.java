package cn.rongcapital.mkt.biz;

import com.tagsin.wechat_sdk.App;

public interface MessageSendBiz {

	public Boolean send(App app,String touser,String content,String media_id);

	public Boolean sendMpnews(String authAppId,String authorizerRefreshToken,String touser,String media_id);	
	
	public Boolean sendAll(App app,boolean isToAll,String tagId,String msgType,String media_id);
}
