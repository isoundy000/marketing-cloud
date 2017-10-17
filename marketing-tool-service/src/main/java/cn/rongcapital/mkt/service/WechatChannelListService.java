/*************************************************
 * @功能简述: 获取渠道列表
 * @see MktApi：
 * @author: zhaogyoing
 * @version: 1.0
 * @date：2016-08-11
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.vo.BaseOutput;


public interface WechatChannelListService {
	
	public BaseOutput channelList();
	
	public BaseOutput chanelExitLike(WechatChannel param);
}
