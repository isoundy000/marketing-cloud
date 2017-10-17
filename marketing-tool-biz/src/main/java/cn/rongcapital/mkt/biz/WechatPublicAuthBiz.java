package cn.rongcapital.mkt.biz;

import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface WechatPublicAuthBiz {

	public BaseOutput authWechatPublicAccount()throws JedisException;
	
	public BaseOutput authWechatPublicCodeAccount(String authorizationCode);
	
	public Boolean isPubIdGranted(String authAppid);
	
}
