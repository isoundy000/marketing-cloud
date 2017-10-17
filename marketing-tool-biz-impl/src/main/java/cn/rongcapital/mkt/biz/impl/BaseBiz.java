package cn.rongcapital.mkt.biz.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;
import com.tagsin.wechat_sdk.token.Token;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.dao.WebchatComponentVerifyTicketDao;
import cn.rongcapital.mkt.po.WebchatComponentVerifyTicket;
import cn.rongcapital.mkt.service.WechatInterfaceLogService;

@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class BaseBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WechatInterfaceLogService wechatInterfaceLogService;
	
	@Autowired
	WebchatComponentVerifyTicketDao webchatComponentVerifyTicketDao;
	
	@Autowired
	Environment env;
	
	public App getApp(){
		    String weixin_appid = env.getProperty("weixin.appid");
		    String weixin_secret = env.getProperty("weixin.secret");
		 	App app = new App(weixin_appid,weixin_secret);
	        WebchatComponentVerifyTicket webchatComponentVerifyTicketq = new WebchatComponentVerifyTicket();
	        webchatComponentVerifyTicketq.setOrderField("id");
	        webchatComponentVerifyTicketq.setOrderFieldType("desc");
	        webchatComponentVerifyTicketq.setStartIndex(0);
	        webchatComponentVerifyTicketq.setPageSize(1);
	        List<WebchatComponentVerifyTicket> list = webchatComponentVerifyTicketDao.selectList(webchatComponentVerifyTicketq);
	        if(list!=null&&list.size()>0){
	        	WebchatComponentVerifyTicket webchatComponentVerifyTicket = list.get(0);
	        	String componentTicket = webchatComponentVerifyTicket.getComponentVerifyTicket();
	        	app.setComponentTicket(componentTicket);
	        }
	        
	        try {	        	
	        	String tokenStrBack = JedisClient.get(app.getId());	        	
	        	if(StringUtils.isEmpty(tokenStrBack)){
	        		Token token = WxComponentServerApi.accessToken(app);
		        	String tokenStr = JSONObject.toJSONString(token);
		        	/**
		        	 * 失效时间1小时50分钟：6600秒
		        	 */
					JedisClient.set(app.getId(), tokenStr,6600);
	        	}
			} catch (JedisException e) {
				logger.info(e.getMessage());
			}
		return app;		
	}
	
	public App getAppAddComponentTicket(){
		    String weixin_appid = env.getProperty("weixin.appid");
		    String weixin_secret = env.getProperty("weixin.secret");
		 	App app = new App(weixin_appid,weixin_secret);
	        WebchatComponentVerifyTicket webchatComponentVerifyTicketq = new WebchatComponentVerifyTicket();
	        webchatComponentVerifyTicketq.setOrderField("id");
	        webchatComponentVerifyTicketq.setOrderFieldType("desc");
	        webchatComponentVerifyTicketq.setStartIndex(0);
	        webchatComponentVerifyTicketq.setPageSize(1);
	        List<WebchatComponentVerifyTicket> list = webchatComponentVerifyTicketDao.selectList(webchatComponentVerifyTicketq);
	        if(list!=null&&list.size()>0){
	        	WebchatComponentVerifyTicket webchatComponentVerifyTicket = list.get(0);
	        	String componentTicket = webchatComponentVerifyTicket.getComponentVerifyTicket();
	        	app.setComponentTicket(componentTicket);
	        }	       
		return app;		
	}
	
	public void setWechatInterfaceLogService(WechatInterfaceLogService wechatInterfaceLogService) {
		this.wechatInterfaceLogService = wechatInterfaceLogService;
	}
	
	
	public String replaceAllUTF8mb4(String text){    	
    	try {
			if(StringUtils.isNotEmpty(text)){    		
				byte[] b_text = text.getBytes();
				for (int i = 0; i < b_text.length; i++){
			        if((b_text[i] & 0xF8)== 0xF0){
			            for (int j = 0; j < 4; j++) {						
			            	b_text[i+j]=0x30;					
			            }
			            i+=3;
			        }
			    }
				String textBack = new String(b_text, "UTF-8");
				return textBack;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;   	
    }
}
