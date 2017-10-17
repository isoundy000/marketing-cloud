/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeInId;

/**
 * @author shuiyangyang
 * @Date 2016.08.25
 */
public interface WeixinQrcodeDelService {
	/**
	 * 删除二维码接口 （逻辑删除，状态改为2）
	 * 接口：mkt.weixin.qrcode.del
	 * 
	 * @param id
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	BaseOutput weixinQrocdeDel(WechatQrcodeInId body);

	/**
	 * 删除二维码接口 （微信记录物理删除）
	 * 接口：mkt.weixin.qrcode.records.del
	 * 
	 * @param id
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	BaseOutput weixinQrcodeRecordsDel(WechatQrcodeInId body);
	
	
}
