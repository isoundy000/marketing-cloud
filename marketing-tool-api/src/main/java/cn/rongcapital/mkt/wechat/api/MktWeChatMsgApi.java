package cn.rongcapital.mkt.wechat.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qq.weixin.mp.aes.AesException;

import cn.rongcapital.mkt.biz.ProcessReceiveMessageOfWeiXinBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktWeChatMsgApi {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProcessReceiveMessageOfWeiXinBiz processReceiveMessageOfWeiXinBiz;
	
	
	/**
	 * @param componentVerifyTicketIn
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * 测试
	 * @return
	 * @throws AesException 
	 * @throws JAXBException 
	 */
	@POST
	@Path("/mkt.weixin.qrcode.getMsgEvent/{appId}")
	@Consumes({MediaType.TEXT_XML})
	public String getMsgEvent(String textxml,@QueryParam("msg_signature") String msg_signature,@QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce, @QueryParam("signature") String signature, @QueryParam("openid") String openid, @PathParam("appId") String appId){		
//		logger.info("获取监听事件的消息     textxml:"+textxml);
//		logger.info("获取监听事件的消息参数:   msg_signature："+msg_signature+";timestamp:"+timestamp+";nonce:"+nonce+";signature:"+signature+";openid:"+openid+";appId:"+appId);
		try {
			if(ApiConstant.WEIXIN_TEST_APPID.equals(appId)){
				boolean isAuthCode = processReceiveMessageOfWeiXinBiz.validateMsgSendState(textxml, msg_signature, timestamp, nonce, signature, openid, appId);
			}else{
				processReceiveMessageOfWeiXinBiz.getMsgLog(textxml, msg_signature, timestamp, nonce, signature, openid,appId);
			}			
		} catch (Exception e) {
//			logger.info(e.getMessage());
//			logger.info("事件出现异常，给微信返回成功，使公众号可用。");
		}
		return "success";		
	}

}
