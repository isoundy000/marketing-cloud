/*************************************************
 * @功能及特点的描述简述: 事件Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-1-7
 * @date(最后修改日期)：2017-1-7
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.api;

import java.util.List;

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

import cn.rongcapital.mkt.event.vo.in.EventSubscribeInput;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.vo.in.EventBehavierListIn;
import cn.rongcapital.mkt.event.vo.in.EventObjectVo;
import cn.rongcapital.mkt.event.vo.in.EventSourceVo;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.event.vo.out.EventListOut;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;
import cn.rongcapital.mkt.vo.BaseOutput;

@Path(ApiConstant.API_PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@ValidateRequest
public interface EventWebService {

    @GET
    @Path("/mkt.event.list")
    EventListOut getEventListByKeyword(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @QueryParam("keyword") String keyword,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) throws Exception;

    @GET
    @Path("/mkt.event.behavior.list")
    List<EventBehavior> getEventBehaviorListByKeyword(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @QueryParam("keyword") String keyword,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) throws Exception;

    @GET
    @Path("/mkt.event.object")
    EventObject selectById(@QueryParam("event_object_id") Integer eventObjectId);

    /**
     * @功能描述: 事件订阅与取消订阅
     * @param input
     * @return BaseOutput
     * @author xie.xiaoliang
     * @since 2017-01-10
     */
    @POST
    @Path("/mkt.event.subscribe")
    BaseOutput eventSubscribe(@Valid EventSubscribeInput input);
    
    /**
     * @功能简述: 获取事件概要信息
     * @param eventId
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.event.general.get")
    BaseOutput getEventGeneral(@NotNull @QueryParam("event_id") Long eventId);

    /**
     * @功能简述: 获取客体属性值列表
     * @param eventObjectId
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.event.object.prop.list")
    BaseOutput getEventObjProps(@NotNull @QueryParam("object_id") Long eventObjectId);

    /**
     * @功能简述: 事件客体注册
     * @param event
     * @return BaseOutput
     * @author zhuxuelong
     */
    @POST
    @Path("/mkt.event.object.register")
    BaseOutput saveEventObj(@NotNull @Valid EventObjectVo event);

    /**
     * @功能简述: 事件来源注册
     * @param source
     * @return BaseOutput
     * @author zhuxuelong
     */
    @POST
    @Path("/mkt.event.source.register")
    BaseOutput saveEventSource(@NotNull @Valid EventSourceVo source);
    
    /**
     * @author guozhenchao
     * @功能简述: 事件行为查询列表
     * @param eventBehavierListIn
     * @return
     */
    @POST
    @Path("/mkt.event.behavior.list")
	EventBehaviorOut getEventBehavierList(@Valid EventBehavierListIn eventBehavierListIn);
}
