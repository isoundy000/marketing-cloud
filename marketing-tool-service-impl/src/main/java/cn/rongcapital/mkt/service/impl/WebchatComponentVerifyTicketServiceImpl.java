package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.Xml2JsonUtil;
import cn.rongcapital.mkt.dao.WebchatComponentVerifyTicketDao;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.WebchatComponentVerifyTicket;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.service.WebchatComponentVerifyTicketService;
import cn.rongcapital.mkt.vo.in.ComponentVerifyTicketIn;

@Service
public class WebchatComponentVerifyTicketServiceImpl implements WebchatComponentVerifyTicketService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebchatComponentVerifyTicketDao webchatComponentVerifyTicketDao;

	@Autowired
	private WechatRegisterDao wechatRegisterDao;

	@Autowired
	private WechatAssetDao wechatAssetDao;
	
	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;

	@Override
	public void insert(String componentVerifyTicketXml) {
		String componentVerifyTicketJson = Xml2JsonUtil.xml2JSON(componentVerifyTicketXml);
		WebchatComponentVerifyTicket webchatComponentVerifyTicket = JSONObject.parseObject(componentVerifyTicketJson,
				WebchatComponentVerifyTicket.class);
		webchatComponentVerifyTicketDao.insert(webchatComponentVerifyTicket);
	}

	@Override
	public void insert(ComponentVerifyTicketIn componentVerifyTicketIn, String msg_signature, String timestamp,
			String nonce) {
		String appId = componentVerifyTicketIn.getAppId();
		String encrypt = componentVerifyTicketIn.getEncrypt();
		String encodingAesKey = "abcdefghijklmnopqrstuvwxyz12345678900987654";
		String token = "ruixuemarketingcloud";
		try {
			encrypt = "<xml>" + "<AppId><![CDATA[" + componentVerifyTicketIn.getAppId() + "]]></AppId>"
					+ "<ToUserName><![CDATA[" + componentVerifyTicketIn.getToUserName() + "]]></ToUserName>"
					+ "<Encrypt><![CDATA[" + componentVerifyTicketIn.getEncrypt() + "]]></Encrypt>" + "</xml>";
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			// 第三方收到公众号平台发送的消息
			String result2 = pc.decryptMsg(msg_signature, timestamp, nonce, encrypt);
			logger.info("----微信请求消息---:{}", result2);
			String webchatComponentVerifyTicketJson = Xml2JsonUtil.xml2JSON(result2);
			JSONObject myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
			webchatComponentVerifyTicketJson = myJsonObject.get("xml").toString();
			myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
			String appIdTemp = myJsonObject.getString("AppId");
			appIdTemp = appIdTemp.substring(2, appIdTemp.length() - 2).trim();
			String createTime = myJsonObject.getString("CreateTime");
			createTime = createTime.substring(2, createTime.length() - 2).trim();
			String infoType = myJsonObject.getString("InfoType");
			infoType = infoType.substring(2, infoType.length() - 2).trim();
			logger.info("----infoType---:{}", infoType);
			// TODO 微信公众号取消授权 congshulin
			/**
			 * 
			 * 微信公众号取消授权 congshulin <xml> <AppId>第三方平台appid</AppId>
			 * <CreateTime>1413192760</CreateTime>
			 * <InfoType>unauthorized</InfoType>
			 * <AuthorizerAppid>公众号appid</AuthorizerAppid> </xml>
			 *
			 */
			switch (infoType){
				case "authorized" :{
					String authorizerAppId = myJsonObject.getString("AuthorizerAppid");
					authorizerAppId = authorizerAppId.substring(2, authorizerAppId.length() - 2).trim();
					logger.info("----authorizerAppId----:{}", authorizerAppId);
					WechatRegister wechatRegister = this.getWechatRegisterByAuthAppId(authorizerAppId);
					logger.info("----wx_acct----:{}", wechatRegister.getWxAcct());
					this.updateStatusForWechat(wechatRegister.getWxAcct(), ApiConstant.TABLE_DATA_STATUS_VALID);
				}
				case "unauthorized" :{
					String authorizerAppId = myJsonObject.getString("AuthorizerAppid");
					authorizerAppId = authorizerAppId.substring(2, authorizerAppId.length() - 2).trim();
					logger.info("----authorizerAppId----:{}", authorizerAppId);
					WechatRegister wechatRegister = this.getWechatRegisterByAuthAppId(authorizerAppId);
					logger.info("----wx_acct----:{}", wechatRegister.getWxAcct());
					this.updateStatusForWechat(wechatRegister.getWxAcct(), ApiConstant.TABLE_DATA_STATUS_INVALID);
				}
				case "component_verify_ticket" :{
					String componentVerifyTicket = myJsonObject.getString("ComponentVerifyTicket");
					componentVerifyTicket = componentVerifyTicket.substring(2, componentVerifyTicket.length() - 2);
					logger.info("WebchatComponentVerifyTicketServiceImpl-------component_verify_ticket： {}", componentVerifyTicket);
					WebchatComponentVerifyTicket webchatComponentVerifyTicket = new WebchatComponentVerifyTicket();
					webchatComponentVerifyTicket.setAppId(appIdTemp);
					webchatComponentVerifyTicket.setCreateTime(Long.parseLong(createTime));
					webchatComponentVerifyTicket.setComponentVerifyTicket(componentVerifyTicket);
					webchatComponentVerifyTicket.setInfoType(infoType);
					webchatComponentVerifyTicketDao.insert(webchatComponentVerifyTicket);
				}
			}
		} catch (Exception e) {
			logger.error("WebchatComponentVerifyTicketServiceImpl------Exception:", e);
		}
	}

	/**
	 * @功能简述: 取消微信公众号授权后设置显示公众号及组
	 * @param: wxAcct
	 * 微信公众号id
	 * @param: status
	 * 状态
	 * @return:
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class,readOnly = false)
	public void updateStatusForWechat(String wxAcct, byte status) {
		WechatRegister wechatRegister = new WechatRegister();
		wechatRegister.setWxAcct(wxAcct);
		wechatRegister.setStatus(status);
		wechatRegisterDao.updateInforByWxAcct(wechatRegister);
		WechatAsset wechatAsset = new WechatAsset();
		wechatAsset.setWxAcct(wxAcct);
		wechatAsset.setStatus(status);
		wechatAssetDao.updateByWxacct(wechatAsset);
		if(status==0){
			wechatQrcodeDao.authorizedByPubId(wxAcct);
		}else if(status==1){
			wechatQrcodeDao.unauthorizedByPubId(wxAcct);
		}		
	}

	/**
	 * @功能简述: 取消微信公众号授权后设置显示公众号及组
	 * 
	 *
	 * @param: wxAcct
	 *             微信公众号id
	 * @param: status
	 *             状态
	 * @return:
	 * 
	 */
	private WechatRegister getWechatRegisterByAuthAppId(String authAppId) {
		WechatRegister wechatRegisterTemp = new WechatRegister();
		wechatRegisterTemp.setAppId(authAppId);
		List<WechatRegister> wechatRegisters = wechatRegisterDao.selectList(wechatRegisterTemp);
		if (wechatRegisters != null && wechatRegisters.size() > 0) {
			WechatRegister wechatRegisterBack = wechatRegisters.get(0);
			return wechatRegisterBack;
		}
		return null;

	}

}
