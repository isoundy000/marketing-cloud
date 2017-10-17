package cn.rongcapital.mkt.biz.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.tagsin.tutils.http.HttpResult;
import com.tagsin.tutils.http.Requester;
import com.tagsin.tutils.http.Requester.Method;
import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.tutils.okhttp.OkHttpUtil;
import com.tagsin.tutils.okhttp.OkHttpUtil.RequestMediaType;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WXServerApiException;
import com.tagsin.wechat_sdk.WxComponentServerApi;
import com.tagsin.wechat_sdk.token.TokenType;
import com.tagsin.wechat_sdk.user.UserInfo;
import com.tagsin.wechat_sdk.vo.AuthInfo;

import cn.rongcapital.mkt.biz.ProcessReceiveMessageOfWeiXinBiz;
import cn.rongcapital.mkt.biz.WechatMemberBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.Xml2JsonUtil;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeTicketDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeTicket;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.service.QrcodeFocusInsertService;
import cn.rongcapital.mkt.service.WechatAssetService;
import cn.rongcapital.mkt.service.WeixinAnalysisQrcodeScanService;
import cn.rongcapital.mkt.vo.in.ComponentVerifyTicketIn;
import cn.rongcapital.mkt.vo.in.WechatQrcodeScanIn;
import cn.rongcapital.mkt.vo.weixin.WXMsgVO;
import okhttp3.Response;


@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class ProcessReceiveMessageOfWeiXinImpl extends BaseBiz implements ProcessReceiveMessageOfWeiXinBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WechatMemberDao wechatMemberDao;
	
	@Autowired
	private WebchatAuthInfoDao webchatAuthInfoDao;
	
	@Autowired
	private QrcodeFocusInsertService qrcodeFocusInsertService;
	
	@Autowired
	private WechatAssetService wechatAssetService;
	
	@Autowired
	private WechatRegisterDao wechatRegisterDao;
    
    @Autowired
    private WechatQrcodeTicketDao wechatQrcodeTicketDao;
    
    @Autowired
    private WeixinAnalysisQrcodeScanService weixinAnalysisQrcodeScanService;
    
    @Autowired
    private WechatQrcodeDao wechatQrcodeDao;
    
    @Autowired
    private WechatMemberBiz wechatMemberBiz;
    
    private String QUERY_AUTH_CODE_TEXT="";
    
    private String TESTCOMPONENT_MSG_TYPE_TEXT = "TESTCOMPONENT_MSG_TYPE_TEXT";

    private String LOCATION = "LOCATION";
    
    private String QUERY_AUTH_CODE_FROM_API = "_from_api";
    
    private String TESTCOMPONENT_MSG_TYPE_TEXT_CALLBACK = "_callback";
    
    private String LOCATION_FROM_CALLBACK="from_callback";	
    
    private String QUERY_AUTH_CODE = "QUERY_AUTH_CODE";
	
	private ComponentVerifyTicketIn getComponentVerifyTicketInFromTextXml(String textXml) throws JAXBException{
			JAXBContext context = JAXBContext.newInstance(ComponentVerifyTicketIn.class);  
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream textxmlis = new ByteArrayInputStream(textXml.getBytes());   			
			ComponentVerifyTicketIn componentVerifyTicketIn = (ComponentVerifyTicketIn)unmarshaller.unmarshal(textxmlis);
			return componentVerifyTicketIn;		
	}
	
	/**
	 * @param resultXml
	 *            <xml>
	 *            <ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 *            <FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 *            <CreateTime>1473663078</CreateTime>
	 *            <MsgType><![CDATA[event]]></MsgType>
	 *            <Event><![CDATA[SCAN]]></Event>
	 *            <EventKey><![CDATA[196]]></EventKey>
	 *            <Ticket><![CDATA[gQHK8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2JqdEE2MEhsQmh0LW5CZWVaeGNXAAIEDL62VwMEAAAAAA==]]></Ticket>
	 *            </xml>
	 * @return
	 */
	private Map<String,String> parserMsgToMap(String resultXml){	
		Map<String,String> mapBack = new HashMap<String,String>();
		String webchatComponentVerifyTicketJson = Xml2JsonUtil.xml2JSON(resultXml);			
		JSONObject myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
		webchatComponentVerifyTicketJson = myJsonObject.get("xml").toString();
		/*webchatComponentVerifyTicketJson:{"CreateTime":["1473071665"],"Event":["subscribe"],"ToUserName":["gh_4685d4eef135"],"FromUserName":["o0gycwIqxXT7DDkwqY-NyqmLb8Pg"],"MsgType":["event"]}*/
		myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
		String createTime = myJsonObject.getString("CreateTime");
		if(StringUtils.isNotEmpty(createTime)){
			createTime = createTime.substring(2, createTime.length()-2);			
		}
		mapBack.put("createTime", createTime);
		String event = myJsonObject.getString("Event");
		if(StringUtils.isNotEmpty(event)){
			event = event.substring(2, event.length()-2);			
		}
		mapBack.put("event", event);		
		String toUserName = myJsonObject.getString("ToUserName");
		if(StringUtils.isNotEmpty(toUserName)){
			toUserName = toUserName.substring(2, toUserName.length()-2);
		}		
		mapBack.put("toUserName", toUserName);		
		String fromUserName = myJsonObject.getString("FromUserName");
		if(StringUtils.isNotEmpty(fromUserName)){
			fromUserName = fromUserName.substring(2, fromUserName.length()-2);
		}		
		mapBack.put("fromUserName", fromUserName);
		String msgType = myJsonObject.getString("MsgType");
		if(StringUtils.isNotEmpty(msgType)){
			msgType = msgType.substring(2, msgType.length()-2);
		}
		mapBack.put("msgType", msgType);
		String ticket = myJsonObject.getString("Ticket");
		if(StringUtils.isNotEmpty(ticket)){
			ticket = ticket.substring(2, ticket.length()-2);
		}
		mapBack.put("ticket", ticket);
		String eventKey = myJsonObject.getString("EventKey");
		if(StringUtils.isNotEmpty(eventKey)){
			eventKey = eventKey.substring(2, eventKey.length()-2);
		}		
		mapBack.put("eventKey", eventKey);
		String content = myJsonObject.getString("Content");
		if(StringUtils.isNotEmpty(content)){
			content = content.substring(2, content.length()-2);
		}		
		mapBack.put("content", content);

		String msgId = myJsonObject.getString("MsgId");
		if(StringUtils.isNotEmpty(msgId)){
			msgId = msgId.substring(2, msgId.length()-2);
		}		
		mapBack.put("msgId", msgId);

		String latitude = myJsonObject.getString("Latitude");
		if(StringUtils.isNotEmpty(latitude)){
			latitude = latitude.substring(2, latitude.length()-2);
		}		
		mapBack.put("latitude", latitude);

		String longitude = myJsonObject.getString("Longitude");
		if(StringUtils.isNotEmpty(longitude)){
			longitude = longitude.substring(2, longitude.length()-2);
		}		
		mapBack.put("longitude", longitude);
		
		String precision = myJsonObject.getString("Precision");
		if(StringUtils.isNotEmpty(precision)){
			precision = precision.substring(2, precision.length()-2);
		}		
		mapBack.put("precision", precision);
		
		return mapBack;		
	}
	
	private WechatMember getWechatMemberFromUserInfo(UserInfo userInfo,WechatMember wechatMember){
		wechatMember.setNickname(userInfo.getNickname());
		wechatMember.setSex(userInfo.getSex());
		wechatMember.setCity(userInfo.getCity());
		wechatMember.setCountry(userInfo.getCountry());
		wechatMember.setProvince(userInfo.getProvince());
		wechatMember.setHeadImageUrl(userInfo.getHeadimgurl());
		//关注时间
		wechatMember.setSubscribeTime(userInfo.getSubscribe_time());
		// unionid
		wechatMember.setRemark(userInfo.getRemark());
		return wechatMember;		
	}

	private WebchatAuthInfo getWebchatAuthInfoByAuthAppId(String authAppId){
		WebchatAuthInfo webchatAuthInfoBack = null;
		WebchatAuthInfo webchatAuthInfo = new WebchatAuthInfo();
		webchatAuthInfo.setAuthorizerAppid(authAppId);
		List<WebchatAuthInfo> webchatAuthInfoes = webchatAuthInfoDao.selectList(webchatAuthInfo);
		if(webchatAuthInfoes!=null&&webchatAuthInfoes.size()>0){
			webchatAuthInfoBack = webchatAuthInfoes.get(0);
		}
		return webchatAuthInfoBack;		
	}
	
	private WechatRegister getWechatRegisterByAuthAppId(String authAppId){
		WechatRegister wechatRegisterTemp = new WechatRegister();
		wechatRegisterTemp.setAppId(authAppId);
		List<WechatRegister> wechatRegisters = wechatRegisterDao.selectList(wechatRegisterTemp);
		if(wechatRegisters!=null&&wechatRegisters.size()>0){
			WechatRegister wechatRegisterBack =  wechatRegisters.get(0);
			return wechatRegisterBack;
		}
		return null;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * cn.rongcapital.mkt.biz.ProcessReceiveMessageOfWeiXinBiz#getMsgLog(java.
	 * lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String) 
	 * 以前不在关注状态，这次关注成功的扫描二维码接收消息的xml： 
	 * <xml>
	 * 		<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 * 		<FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 * 		<CreateTime>1473747992</CreateTime>
	 * 		<MsgType><![CDATA[event]]></MsgType>
	 * 		<Event><![CDATA[subscribe]]></Event>
	 * 		<EventKey><![CDATA[qrscene_230]]></EventKey>
	 * 		<Ticket><![CDATA[gQGu8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL05EdGZXcjNsQnh0X2kwMHhlQmNXAAIEEb62VwMEAAAAAA==]]></Ticket>
	 * </xml>
	 * 已经在关注状态的前提下，扫描二维码的xml： 
	 * <xml>
	 * 		<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 * 		<FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 * 		<CreateTime>1473748010</CreateTime> 
	 * 		<MsgType><![CDATA[event]]></MsgType>
	 * 		<Event><![CDATA[SCAN]]></Event> 
	 * 		<EventKey><![CDATA[230]]></EventKey>
	 * 		<Ticket><![CDATA[gQGu8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL05EdGZXcjNsQnh0X2kwMHhlQmNXAAIEEb62VwMEAAAAAA==]]></Ticket>
	 * </xml>
	 * 取消关注 
	 * <xml>
	 * 		<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 * 		<FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 * 		<CreateTime>1473748255</CreateTime> 
	 * 		<MsgType><![CDATA[event]]></MsgType>
	 * 		<Event><![CDATA[unsubscribe]]></Event> 
	 * 		<EventKey><![CDATA[]]></EventKey>
	 * </xml> 
	 * 非扫描二维码关注的xml 
	 * <xml>
	 * 		<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 * 		<FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 * 		<CreateTime>1473748532</CreateTime> 
	 * 		<MsgType><![CDATA[event]]></MsgType>
	 * 		<Event><![CDATA[subscribe]]></Event> 
	 * 		<EventKey><![CDATA[]]></EventKey>
	 * </xml>
	 *
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void getMsgLog(String textXml,String msg_signature,String timestamp,String nonce,String signature,String openid,String authAppId) throws JAXBException, AesException {		
	    	/**
	    	 * 记入接口日志到数据库
	    	 */
		    WXMsgVO wxMsgVO = new WXMsgVO(textXml,msg_signature,timestamp,nonce,signature,openid,authAppId);
			WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("ProcessReceiveMessageOfWeiXinImpl","getMsgLog",wxMsgVO.toString(),new Date());
			wechatInterfaceLogService.insert(wechatInterfaceLog);
			/**
			 * 解析xml
			 */
			String weixin_appid = env.getProperty("weixin.appid");
		    String weixin_encoding_aes_key = env.getProperty("weixin.encoding.aes.key");
		    String weixin_token = env.getProperty("weixin.token");		    
			WXBizMsgCrypt pc = new WXBizMsgCrypt(weixin_token, weixin_encoding_aes_key, weixin_appid); 
			String resultXml = pc.decryptMsg(msg_signature, timestamp, nonce, textXml);		
			logger.info("解密后明文: " + resultXml);
			Map<String, String> msgMap = this.parserMsgToMap(resultXml);
			App app = this.getApp();
			WebchatAuthInfo webchatAuthInfo = this.getWebchatAuthInfoByAuthAppId(authAppId);
			app.setAuthAppId(webchatAuthInfo.getAuthorizerAppid());
			app.setAuthRefreshToken(webchatAuthInfo.getAuthorizerRefreshToken());
			WechatRegister wechatRegister = this.getWechatRegisterByAuthAppId(authAppId);
			int status = 0;
			String event = msgMap.get("event");
			String createTime = msgMap.get("createTime");
			String qrCodeTicket = msgMap.get("ticket");
			Date date = null;
			if(StringUtils.isNotEmpty(createTime)){
				date = new Date(Long.parseLong(createTime)*1000);
			}
			
			if(StringUtils.isNotEmpty(event)){
				switch(event){
				  case "SCAN":{
				      this.insertWechatQrcodeScan(qrCodeTicket, openid);
					  break; 
				  }
				  case "subscribe":{
					  UserInfo userInfo = wechatMemberBiz.getUserInfoeByOpenid(app, openid);
					  if(wechatRegister!=null){
						  wechatAssetService.follow(userInfo, wechatRegister.getWxAcct());
					  }
					  status = 0;
					  qrcodeFocusInsertService.insertQrcodeFoucsInfo(qrCodeTicket, openid, date, status, wechatRegister.getWxAcct());
					  this.insertWechatQrcodeScan(qrCodeTicket, openid);
					  break;
				  }
				  case "unsubscribe":{
					  if(wechatRegister!=null){
						  wechatAssetService.unfollow(openid, wechatRegister.getWxAcct());
					  }
					  status = 1;
					  qrcodeFocusInsertService.insertQrcodeFoucsInfo(qrCodeTicket, openid, date, status, wechatRegister.getWxAcct());				  
					  break;
				  }
				  default :{
					  break;
				  }
				}
			}
	}
	
	private WechatQrcode getWechatQrcodeScanInForSCAN(String qrCodeTicket,String openid){
	    if(StringUtils.isNotEmpty(qrCodeTicket)&&StringUtils.isNotEmpty(openid)){
	        WechatQrcodeTicket wechatQrcodeTicketTemp = new WechatQrcodeTicket();
            wechatQrcodeTicketTemp.setTicket(qrCodeTicket);
            List<WechatQrcodeTicket> wechatQrcodeTicketes = wechatQrcodeTicketDao.selectList(wechatQrcodeTicketTemp);
            if(CollectionUtils.isNotEmpty(wechatQrcodeTicketes)){
                WechatQrcodeTicket wechatQrcodeTicket = wechatQrcodeTicketes.get(0);
                if(wechatQrcodeTicket!=null){
                    WechatQrcode wechatQrcodeTemp = new WechatQrcode();
                    wechatQrcodeTemp.setTicket(String.valueOf(wechatQrcodeTicket.getId()));
                    List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcodeTemp);
                    if(CollectionUtils.isNotEmpty(wechatQrcodeList)){
                        WechatQrcode wechatQrcode = wechatQrcodeList.get(0);
                        return wechatQrcode;      
                    }
                }
            }
	    }
        return null;	    
	}
	
	private void insertWechatQrcodeScan(String qrCodeTicket,String openid){
	    WechatQrcode wechatQrcode = this.getWechatQrcodeScanInForSCAN(qrCodeTicket, openid);
	    if(wechatQrcode!=null){
	        weixinAnalysisQrcodeScanService.instertToWechatQrcodeScan(openid,wechatQrcode);
	    }	    
	}

	@Override
	public boolean validateMsgSendState(String textXml, String msg_signature, String timestamp, String nonce,
			String signature, String openid, String authAppId) throws JAXBException, AesException {
		boolean isAuthCode = false;
    	/**
    	 * 记入接口日志到数据库
    	 */
	    WXMsgVO wxMsgVO = new WXMsgVO(textXml,msg_signature,timestamp,nonce,signature,openid,authAppId);
		WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("ProcessReceiveMessageOfWeiXinImpl","validateMsgSendState",wxMsgVO.toString(),new Date());
		wechatInterfaceLogService.insert(wechatInterfaceLog);
		/**
		 * 解析xml
		 */
		String weixin_appid = env.getProperty("weixin.appid");
	    String weixin_encoding_aes_key = env.getProperty("weixin.encoding.aes.key");
	    String weixin_token = env.getProperty("weixin.token");
		WXBizMsgCrypt pc = new WXBizMsgCrypt(weixin_token, weixin_encoding_aes_key, weixin_appid); 
		String resultXml = pc.decryptMsg(msg_signature, timestamp, nonce, textXml);		
		
		logger.info("解密后明文: " + resultXml);
		Map<String, String> msgMap = this.parserMsgToMap(resultXml);
		App app = this.getApp();
		String event = msgMap.get("event");
		String content = msgMap.get("content");
		String msg = "";
		if(StringUtils.isNotEmpty(content)){
			if(content.contains(this.QUERY_AUTH_CODE)){
				isAuthCode = true;
				msg = this.getQueryAuthCodeBackResultXml(msgMap,timestamp,nonce,pc);				
			}else if(content.contains(this.TESTCOMPONENT_MSG_TYPE_TEXT)){				
				msg = this.getComponentMsgTypeTextBackResultXml(msgMap,timestamp,nonce,pc);
			}
		}else if(event.equals(this.LOCATION)){			
			msg = this.getLocationBackResultXml(msgMap,timestamp,nonce,pc);
		}	
		logger.info("msg:"+msg);
		logger.info("QUERY_AUTH_CODE_TEXT"+this.QUERY_AUTH_CODE_TEXT);
		Boolean isSended = WxComponentServerApi.send(app, this.QUERY_AUTH_CODE_TEXT, msg);
		logger.info("Has Send Msg Success "+ isSended);
		return isAuthCode;
	}
		
	/**
	 * 返回Api文本消息全网发布文本
	 * @param msgMap
	 * @param timestamp
	 * @param nonce
	 * @param pc
	 * @return
	 */
	private String getQueryAuthCodeBackResultXml(Map<String, String> msgMap,String timestamp, String nonce,WXBizMsgCrypt pc){
		String query_auth_code = msgMap.get("content");
		String[] query_auth_codes = query_auth_code.split(":");
		String queryAuthCode = query_auth_codes[1];
		this.QUERY_AUTH_CODE_TEXT= queryAuthCode;
		String content = queryAuthCode+this.QUERY_AUTH_CODE_FROM_API;
		String backResultJson = this.getBackResultJson(msgMap.get("fromUserName"), content);
		return backResultJson;		
	}
	

	/**
	 * 返回普通文本消息全网发布文本
	 * @param msgMap
	 * @param timestamp
	 * @param nonce
	 * @param pc
	 * @return
	 */
	private String getComponentMsgTypeTextBackResultXml(Map<String, String> msgMap,String timestamp, String nonce,WXBizMsgCrypt pc){		
		String content = msgMap.get("content")+this.TESTCOMPONENT_MSG_TYPE_TEXT_CALLBACK;
		String backResultJson = this.getBackResultJson(msgMap.get("fromUserName"), content);
		return backResultJson;		
	}
	
	/**
	 * 发送事件消息全网发布文本
	 * @param msgMap
	 * @param timestamp
	 * @param nonce
	 * @param pc
	 * @return
	 */
	private String getLocationBackResultXml(Map<String, String> msgMap,String timestamp, String nonce,WXBizMsgCrypt pc){		
		String content = msgMap.get("event")+this.LOCATION_FROM_CALLBACK;
		String backResultJson = this.getBackResultJson(msgMap.get("fromUserName"), content);
		return backResultJson;		
	}
	
	/**
	 * 组装消息体
	 * @param touser
	 * @param content
	 * @return
	 */
	private String getBackResultJson(String touser,String content){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":\"").append(touser).append("\",");
		sb.append("\"msgtype\":\"text\",");
		sb.append("\"text\":");
		sb.append("{");
		sb.append("\"content\":\"").append(content).append("\"");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
	
}
