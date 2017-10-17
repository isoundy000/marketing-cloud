/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author zhouqi
 * @Date 2016.08.29
 */
public interface WechatQrcodeActivateService {
	/**
	 * 生效微信二维码
	 * 接口：mkt.weixin.qrcode.activate
	 * 修改wechat_qrcode.status状态为1，标示微信二维码已用
	 * @param id
	 * @return
	 * @author zhouqi
	 * @Date 2016.08.29
	 */
	BaseOutput weChatQrocdeActivate(int id);

		
}
