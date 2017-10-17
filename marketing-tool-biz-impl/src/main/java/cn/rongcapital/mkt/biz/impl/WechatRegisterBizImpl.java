package cn.rongcapital.mkt.biz.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;

import cn.rongcapital.mkt.biz.WechatRegisterBiz;
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.po.WechatRegister;

@Service
public class WechatRegisterBizImpl extends BaseBiz implements WechatRegisterBiz  {

	/* (non-Javadoc)
	 * @see cn.rongcapital.mkt.biz.WechatRegisterBiz#getAuthInfo(com.tagsin.wechat_sdk.App, java.lang.String)
	 * authInfoString 的格式
	 * {
		"authorizer_info": {
		"nick_name": "微信SDK Demo Special", 
		"head_img": "http://wx.qlogo.cn/mmopen/GPyw0pGicibl5Eda4GmSSbTguhjg9LZjumHmVjybjiaQXnE9XrXEts6ny9Uv4Fk6hOScWRDibq1fI0WOkSaAjaecNTict3n6EjJaC/0", 
		"service_type_info": { "id": 2 }, 
		"verify_type_info": { "id": 0 },
		"user_name":"gh_eb5e3a772040",
		"business_info": {"open_store": 0, "open_scan": 0, "open_pay": 0, "open_card": 0, "open_shake": 0},
		"alias":"paytest01"
		}, 
		"qrcode_url":"URL",    
		"authorization_info": {
		"appid": "wxf8b4f85f3a794e77", 
		"func_info": [
		{ "funcscope_category": { "id": 1 } }, 
		{ "funcscope_category": { "id": 2 } }, 
		{ "funcscope_category": { "id": 3 } }
		]
		}
		}*
	 */
	@Override
	public WechatRegister getAuthInfo(String authAppId,String refreshToken) {
		App app =this.getApp();
		app.setAuthAppId(authAppId);
		app.setAuthRefreshToken(refreshToken);
		
		String authInfoString =  WxComponentServerApi.getAuthInfo(app,authAppId);
		/**
    	 * 记入接口日志到数据库
    	 */
		WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("WechatRegisterBizImpl","getAuthInfo",authInfoString,new Date());
		wechatInterfaceLogService.insert(wechatInterfaceLog);	
		/**
		 * 组装授权公众号对象
		 */
		WechatRegister  wechatRegister =  null;
		if(StringUtils.isNoneBlank(authInfoString)){
			wechatRegister =  this.getWechatRegisterFromAuthInfo(authInfoString);	
		}
	
		return wechatRegister;
	}

	/**
	 * @param authInfoString
	 * {
		    "authorizer_info": {
		        "nick_name": "闷骚恒恒",
		        "service_type_info": {
		            "id": 1
		        },
		        "verify_type_info": {
		            "id": -1
		        },
		        "user_name": "gh_0752ccda639c",
		        "alias": "",
		        "qrcode_url": "http://mmbiz.qpic.cn/mmbiz_jpg/Us3ouRjaS79cVluzgKaeRcibQYwNyVB0QbdxibTWZcHRtFuFUYX9At5y0mhlYRw2JwFeUicFftw4e1UhicW9ONUjag/0",
		        "business_info": {
		            "open_pay": 0,
		            "open_shake": 0,
		            "open_scan": 0,
		            "open_card": 0,
		            "open_store": 0
		        },
		        "idc": 1
		    },
		    "authorization_info": {
		        "authorizer_appid": "wxa5fb7dea54673299",
		        "func_info": [
		            {
		                "funcscope_category": {
		                    "id": 1
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 15
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 4
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 7
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 2
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 3
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 11
		                }
		            }
		        ]
		    }
		}
	 * @return
	 */
	public WechatRegister getWechatRegisterFromAuthInfo(String authInfoString){
		JSONObject jsonObject = JSON.parseObject(authInfoString);
		JSONObject authorizerInfo = jsonObject.getJSONObject("authorizer_info");
		if(authorizerInfo != null){
			WechatRegister  wechatRegister = new WechatRegister();

			String name = authorizerInfo.getString("nick_name");
			String alias = authorizerInfo.getString("alias");
			String userName = authorizerInfo.getString("user_name");
			String headImg = authorizerInfo.getString("head_img");
			
			String wechatQrcode = authorizerInfo.getString("qrcode_url");			
			JSONObject serviceTypeInfo = authorizerInfo.getJSONObject("service_type_info");
			JSONObject verifyTypeInfo = authorizerInfo.getJSONObject("verify_type_info");
			Integer service_type_info = serviceTypeInfo.getInteger("id");
			Integer verify_type_info = verifyTypeInfo.getInteger("id");
			
			
			JSONObject authorizationInfo = jsonObject.getJSONObject("authorization_info");
			String appId = authorizationInfo.getString("authorizer_appid");
			
			wechatRegister.setWxAcct(userName);
			wechatRegister.setName(name);
			wechatRegister.setType(service_type_info);
			wechatRegister.setIsAuth(verify_type_info);
			wechatRegister.setNickname(alias);
			wechatRegister.setHeaderImage(headImg);
			wechatRegister.setAppId(appId);
			wechatRegister.setWechatQrcode(wechatQrcode);			
			return wechatRegister;			
		}
		return null;

	}
	
}
