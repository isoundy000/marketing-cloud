/**
 * 
 */
package cn.rongcapital.mkt.service;


import java.util.Date;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeBatchSaveIn;


public interface WeixinQrcodeBatchSaveService {
	/**
	 * 根据batch_id，在表wechat_qrcode 中查找到对应二维码记录，更新：失效时间、标签、备注信息、状态(status=0)
	 * 接口：mkt.weixin.qrcode.batch.save
	 * 
	 * @author shuiyangyang
	 * @Date 2016.08.26
	 *
	 */
	BaseOutput weixinQrcodeBatchSave(String batchId, String expirationTime, String qrcodeTagIds, Integer qrcodeStatus);
	
	BaseOutput weixinQrcodeBatchSave(WechatQrcodeBatchSaveIn body);
}
