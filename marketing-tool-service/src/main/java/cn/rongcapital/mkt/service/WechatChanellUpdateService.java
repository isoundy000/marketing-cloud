/*************************************************
 * @功能简述: 更新渠道信息
 * @see MktApi：
 * @author: zhaogyoing
 * @version: 1.0
 * @date：2016-08-11
 *************************************************/
package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatChanellUpdateIn;

public interface WechatChanellUpdateService {

	BaseOutput wechatChannelUpdate(WechatChanellUpdateIn wechatChanellUpdateIn, SecurityContext securityContext);
}
