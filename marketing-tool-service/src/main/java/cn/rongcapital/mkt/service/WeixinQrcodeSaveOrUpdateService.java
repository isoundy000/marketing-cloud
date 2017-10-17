package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeInData;

public interface WeixinQrcodeSaveOrUpdateService {
	
	/**
	 * 创建微信二维码信息，如果已存在则更新，未存在则直接保存 wechat_qrcode
	 * 接口：mkt.weixin.qrcode.saveorupdate
	 * @param WechatQrcodeInData
	 * @return BaseOutput
	 * @author lihaiguang
	 */
	BaseOutput weixinSaveOrUpdate(WechatQrcodeInData body);

}
