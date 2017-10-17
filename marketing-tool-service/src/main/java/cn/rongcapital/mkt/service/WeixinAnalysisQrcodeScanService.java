/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeScanIn;

/**
 * @author shuiyangyang
 *
 */
public interface WeixinAnalysisQrcodeScanService {

	/**
	 * 保存扫描微信二维码次数和人数 
	 * 接口：mkt.weixin.analysis.qrcode.scan
	 * 
	 * @param userId
	 * @param userHost
	 * @param qrcodeId
	 * @return
	 * @author shuiyangyang
	 * @date 2016.09.01
	 */
	BaseOutput instertToWechatQrcodeScan(WechatQrcodeScanIn body);
	
	/**
	 * 保存扫描微信二维码次数和人数 
	 * @param openId
	 * @param wechatQrcode
	 */
	void instertToWechatQrcodeScan(String openId,WechatQrcode wechatQrcode);
}
