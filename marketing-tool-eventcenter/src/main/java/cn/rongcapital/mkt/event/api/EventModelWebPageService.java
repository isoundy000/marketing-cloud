/*************************************************
 * @功能及特点的描述简述: 事件Service

 * @对应项目名称：营销云系统
 * @author: 单璟琦
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-1-11
 * @date(最后修改日期)：
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.vo.BaseOutput;

@Path(ApiConstant.API_PATH)
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public interface EventModelWebPageService {
	/**
	 * 事件库数量统计
	 * 
	 * 接口：mkt.event.eventModel.count
	 * 
	 * @param user_token
	 * @param ver
	 * @return BaseOutput
	 * @author shanjingqi
	 * @Date 2017-01-10
	 */
	@GET
	@Path("/mkt.event.eventModel.count")
	BaseOutput getEventModelCount(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String version);
	
	/**
	 * 根据事件途径获取事件源列表
	 * 
	 * 接口：mkt.event.eventModel.count
	 * 
	 * @param user_token
	 * @param ver
	 * @return BaseOutput
	 * @author shanjingqi
	 * @Date 2017-01-10
	 */
	@GET
	@Path("/mkt.event.source.list")
	BaseOutput getEventSourceListByChannel(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String version,@NotEmpty @QueryParam("channel") String channel);
}
