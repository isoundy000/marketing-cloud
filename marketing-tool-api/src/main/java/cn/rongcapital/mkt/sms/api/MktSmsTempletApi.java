package cn.rongcapital.mkt.sms.api;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.SmsTempletService;
import cn.rongcapital.mkt.service.SmstempletCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsTempletOut;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktSmsTempletApi {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SmsTempletService smsTempletService;
	
	@Autowired
	private SmstempletCountGetService smstempletCountGetService;
	
	
	/**
	 * 根据模板名称模糊查询模板列表
	 * @param userToken
	 * @param userId
	 * @param ver
	 * @param index
	 * @param size
	 * @param channelType
	 * @param type
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/mkt.sms.smstemplet.list.get")
	public SmsTempletOut smsTempletList(@NotEmpty @QueryParam("user_token") String userToken,@NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("ver") String ver,			
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size,
			@QueryParam("channel_type") String channelType,
			@QueryParam("type") String type,
			@QueryParam("name") String name,
			@QueryParam("content") String content) throws Exception {
		return smsTempletService.smsTempletList(userId, index, size, channelType, type, name,content);
	}
	
	/**
	 * 模板的增加或者修改
	 * @param smsTempletIn
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/mkt.sms.smstemplet.saveorupdate")
	public BaseOutput saveOrUpdateSmsTemplet(@Valid SmsTempletIn smsTempletIn) throws Exception {		
		return smsTempletService.saveOrUpdateSmsTemplet(smsTempletIn);
	}
	
	/**
	 * 分类获取模板数量
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/mkt.sms.smstemplet.count.get")
	public BaseOutput smsTempletCountGet(@NotEmpty @QueryParam("user_token") String userToken
			,@NotEmpty @QueryParam("user_id") String userId
			,@NotEmpty @QueryParam("ver") String ver
			,@QueryParam("channel_type") String channelType) throws Exception {		
		return smstempletCountGetService.getSmsTempletCount(channelType);
	}
	
}
