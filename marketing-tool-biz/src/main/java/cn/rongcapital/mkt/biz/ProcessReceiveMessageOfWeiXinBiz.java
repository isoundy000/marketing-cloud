package cn.rongcapital.mkt.biz;

import javax.xml.bind.JAXBException;

import com.qq.weixin.mp.aes.AesException;

public interface ProcessReceiveMessageOfWeiXinBiz {

	/**
	 * 获取微信事件监听消息
	 * @param textXml
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @param openid
	 * @param authAppId
	 * @throws JAXBException
	 * @throws AesException
	 */
	public void getMsgLog(String textXml,String msg_signature,String timestamp,String nonce,String signature,String openid,String authAppId) throws JAXBException, AesException;
	
	/**
	 * 全网发布使用的发送API文本消息，客服文本消息，事件消息
	 * @param textXml
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @param openid
	 * @param authAppId
	 * @return
	 * @throws JAXBException
	 * @throws AesException
	 */
	public boolean validateMsgSendState(String textXml,String msg_signature,String timestamp,String nonce,String signature,String openid,String authAppId) throws JAXBException, AesException;

}
