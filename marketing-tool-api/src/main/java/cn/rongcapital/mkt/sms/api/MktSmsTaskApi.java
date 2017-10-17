package cn.rongcapital.mkt.sms.api;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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
import cn.rongcapital.mkt.service.SmsTaskHeadService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktSmsTaskApi {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SmsTaskHeadService smsTaskHeadService;
	
	/**
	 * 任务中心根据任务名称模糊查询
	 * @param userToken
	 * @param userId
	 * @param ver
	 * @param index
	 * @param size
	 * @param smsTaskAppType
	 * @param smsTaskStatus
	 * @param smsTaskName
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/mkt.sms.smstaskhead.list.get")
	public BaseOutput smsTaskHeadList(@NotEmpty @QueryParam("user_token") String userToken,@NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("ver") String ver,			
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size,
			@QueryParam("sms_task_app_type") String smsTaskAppType,
			@QueryParam("sms_task_status") String smsTaskStatus,
			@QueryParam("sms_task_name") String smsTaskName) throws Exception {		
		return smsTaskHeadService.smsTaskHeadList(userId, index, size, smsTaskAppType, smsTaskStatus, smsTaskName);
	}
		
	/**
	 * 任务发布
	 * @param userToken
	 * @param userId
	 * @param ver
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/mkt.sms.smstaskhead.publish")
	public BaseOutput smsTaskHeadPublish(@NotEmpty @QueryParam("user_token") String userToken,@NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("ver") String ver,
			@NotNull @QueryParam("sms_task_head_id") Integer id) throws Exception {		
		return smsTaskHeadService.smsTaskHeadPublish(userId, id);
	}
	
	@GET
	@Path("/mkt.sms.smstaskhead.get")
	public BaseOutput deleteSmsMaterial(@NotEmpty @QueryParam("user_token") String userToken,@NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("ver") String ver,
			@NotNull @QueryParam("sms_task_head_id") Integer id) throws Exception {		
		return smsTaskHeadService.getSmsTaskHeadById(id);
	}
	
}
