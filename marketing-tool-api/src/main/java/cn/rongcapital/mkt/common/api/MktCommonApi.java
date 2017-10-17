/*************************************************
 * @功能简述: API接口通用主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.service.RegisterListService;
import cn.rongcapital.mkt.service.WechatChanellUpdateService;
import cn.rongcapital.mkt.service.WechatChannelListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatChanellUpdateIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktCommonApi {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegisterListService regListService;

	@Autowired
	private WechatChannelListService wechatChannelListService;

	@Autowired
	private WechatChanellUpdateService wechatChanellUpdateService;

	/**
	 * 获取公众号列表
	 *
	 * @param userToken
	 * @param ver
	 * @author chengjincheng
	 */
	@GET
	@Path("/mkt.weixin.register.list")
	public BaseOutput registerListGet(@NotEmpty @QueryParam("user_token") String userToken) {
		return regListService.getRegisterList();
	}

	/**
	 * 获取微信接入渠道列表
	 *
	 * @param userToken
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.weixin.channel.list")
	public BaseOutput wechannelListGet(@NotEmpty @QueryParam("user_token") String userToken) {
		return wechatChannelListService.channelList();
	}

	/**
	 * 获取微信接入渠道列表
	 *
	 * @param userToken
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.weixin.channel.name.get")
	public BaseOutput wechannelListGet(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ch_name") String ch_name) {
		WechatChannel wechatChannel = new WechatChannel();
		wechatChannel.setChName(ch_name);
		wechatChannel.setStatus(Byte.toString(ApiConstant.TABLE_DATA_STATUS_VALID));
		return wechatChannelListService.chanelExitLike(wechatChannel);
	}

	/**
	 * 更新微信渠道详细信息
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@POST
	@Path("/mkt.weixin.channel.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput wechannelUpdate(@Valid WechatChanellUpdateIn body, @Context SecurityContext securityContext) {
		return wechatChanellUpdateService.wechatChannelUpdate(body, securityContext);
	}
}
