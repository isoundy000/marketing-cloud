/*************************************************
 * @功能简述: API接口 主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.LoginInput;
import cn.rongcapital.mkt.vo.ModifyInput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class MktApi {
	@Autowired
	private LoginService loginService;

	@Autowired
	private ModifyPasswdService modifyPasswdService;

	@Autowired
	private TaskGetListService taskGetListService;

	@Autowired
	private HomePageCalendarListService homePageCalendarListService;

	@Autowired
	private HomePageCalendarPopService homePageCalendarPopService;

	private Logger logger = LoggerFactory.getLogger(getClass());
   
	/**
	 * @功能简述: For testing, will remove later
	 * @param:String userToken,String
	 *                   ver
	 * @return: Object
	 */
	@GET
	@Path("/test.demo")
	public Object testGet(@NotEmpty @QueryParam("user_token") String userToken, @QueryParam("ver") String ver)
			throws Exception {
		BaseOutput ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), userToken, 0, null);
		return Response.ok().entity(ur).build();
	}



	/**
	 * @功能描述: 登录接口
	 * @Param: LoginIn loginIn, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.user.login")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object login(@Valid LoginInput input, @Context SecurityContext securityContext) {
		return loginService.validateLoginPassword(input, securityContext);
	}

	/**
	 * @功能描述: 修改密码
	 * @Param: ModifyIn modifyIn, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.user.modifypasswd")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object modifyPasswd(@Valid ModifyInput input, @Context SecurityContext securityContext) {
		return modifyPasswdService.modifyPasswd(input, securityContext);
	}

	/**
	 * @功能简述: 获取后台任务列表
	 * @author nianjun
	 * @param:
	 * @return: Object
	 */
	@GET
	@Path("/mkt.task.list.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput taskListGet(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken) {
		return taskGetListService.getTaskList();
	}

	/**
     * @功能简述: 检查后台任务列表的状态
     * @author lihaiguang
     * @param:
     * @return: Object
     */
	@GET
    @Path("/mkt.task.list.check")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput taskListCheck(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return taskGetListService.checkTaskList();
    }
	
	/**
     * @功能简述: 更改后台任务列表的状态把未查看的全部更新为已查看
     * @author lihaiguang
     * @param:
     * @return: Object
     */
	@GET
    @Path("/mkt.task.list.check.update")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput taskListCheckUpdate(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return taskGetListService.updateTaskListStatus();
    }
	
	/**
	 * 统计出当月日历日被客户标记当月定时的活动，按启动时间算
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.calendar.list")
	public BaseOutput homePageCalendarList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver,@NotEmpty @QueryParam("date") String date) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageCalendarListService.getCalendarList(date));

		return result;
	}

	/**
	 * 当月日历日被客户标记当日定时的活动的弹窗
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.calendar.pop")
	public BaseOutput homePageCalendarPop(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("date") String date) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageCalendarPopService.getCalendarPop(date));

		return result;
	}


}
