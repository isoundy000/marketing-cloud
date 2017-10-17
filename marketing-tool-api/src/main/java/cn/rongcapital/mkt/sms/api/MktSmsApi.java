package cn.rongcapital.mkt.sms.api;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.in.SmsMessageSendTestIn;
import cn.rongcapital.mkt.vo.in.SmsSmstempletDelIn;
import cn.rongcapital.mkt.vo.in.SmsTaskDeleteIn;
import cn.rongcapital.mkt.vo.out.SmsSignatureListOut;
import cn.rongcapital.mkt.vo.out.SmsTargetAudienceListOut;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by byf on 10/18/16.
 */

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class MktSmsApi {

    @Autowired
    private SmsSignatureListGetService smsSignatureListGetService;

    @Autowired
    private SmsTargetAudienceListGetService smsTargetAudienceListGetService;

    @Autowired
    private SmsActivationCreateOrUpdateService smsActivationCreateOrUpdateService;
    
    @Autowired
    private SmsMessageSendRecordGetService smsMessageSendRecordGetService;
    
    @Autowired
    private SmsTaskDeleteService smsTaskDeleteService;
    
    @Autowired
    private SmsMessageSendTestService smsMessageSendTestService;

    @Autowired
    private SmsMaterialGetService smsMaterialGetService;
    
    @Autowired
    private SmsSmstempletIdGetService smsSmstempletIdGetService;
    
    @Autowired
    private SmsSmstempletDelService smsSmstempletDelService;

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
     * @功能简述: 获取短信签名列表
     * @param:String userToken,String
     *                   ver  ,String
     * @return: Signature
     */
    @GET
    @Path("/mkt.sms.signature.get")
    public SmsSignatureListOut smsSignatureListGet(@NotEmpty @QueryParam("user_token") String userToken,
                                                   @QueryParam("ver") String ver) throws Exception {
        return smsSignatureListGetService.getSmsSignatureList();
    }


    /**
     * @功能简述: 获取短信目标人群列表
     * @param:String userToken,
     *        String ver
     * @return: Object
     */
    @GET
    @Path("/mkt.sms.audienct.get")
    public SmsTargetAudienceListOut smsAudienceListGet(@NotEmpty @QueryParam("user_token") String userToken,
                                                       @QueryParam("ver") String ver) throws Exception {
        return smsTargetAudienceListGetService.getSmsTargetAudienceList();
    }

    /**
     * @功能简述: 保存短信活动
     * @param:String userToken,
     *        String ver
     * @return: Object
     */
    @POST
    @Path("/mkt.sms.message.createorupdate")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput createOrUpdateSmsMessage (@Valid SmsActivationCreateIn smsActivationCreateIn,
                                          @Context SecurityContext securityContext) throws JMSException {
        return smsActivationCreateOrUpdateService.createOrUpdateSmsActivation(smsActivationCreateIn);
    }
    
    
    /**
     * 返回当前任务的发送记录列表（可以按照手机号查询）
     * 
     * @param smsTaskHeadId
     * @param receiveMobile
     * @param index
     * @param size
     * @return
     * @author shuiyangyang
     * @Date 2016.10.18
     */
    @GET
    @Path("/mkt.sms.message.send.record.get")
    public BaseOutput messageSendRecordGet(@NotNull @QueryParam("sms_task_head_id") Long smsTaskHeadId,
                                        @QueryParam("receive_mobile") String receiveMobile,
                                        @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
                                        @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
        return smsMessageSendRecordGetService.messageSendRecordGet(smsTaskHeadId, receiveMobile, index, size);
    }
    
    
    
    /**
     * 任务删除接口
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016.10.19
     */
    @POST
    @Path("/mkt.sms.task.delete")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput smsTaskDelete(@Valid SmsTaskDeleteIn body,
            @Context SecurityContext securityContext) {
        return smsTaskDeleteService.smsTaskDelete(body, securityContext);
    }
    
    /**
     * 短信白名单测试发送
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016.10.20
     */
    @POST
    @Path("/mkt.sms.message.send.test")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput smsTaskDelete(@Valid SmsMessageSendTestIn body,
            @Context SecurityContext securityContext) {
        return smsMessageSendTestService.messageSendTest(body, securityContext);
    }

    /**
     * @功能简述: For testing, will remove later
     * @param:String userToken,String
     *                   ver
     * @return: Object
     */
    @GET
    @Path("/mkt.sms.smsmaterial.count.get")
    public BaseOutput getSmsMaterialCount(@NotEmpty @QueryParam("user_token") String userToken,
                                     @QueryParam("ver") String ver,
                                     @NotNull @QueryParam("channel_type") Integer channelType) throws Exception {
        return smsMaterialGetService.getSmsMaterialCount(channelType);
    }

    /**
     * @功能简述: For testing, will remove later
     * @param:String userToken,String
     *                   ver
     * @return: Object
     */
    @GET
    @Path("/mkt.sms.smsmaterial.get")
    public BaseOutput getSmsMaterial(@NotEmpty @QueryParam("user_token") String userToken,
                                     @QueryParam("ver") String ver,
                                     @NotNull @QueryParam("id") Long id) throws Exception {
        return smsMaterialGetService.getSmsMaterialById(id);
    }

    /**
     * @功能简述: For testing, will remove later
     * @param:String userToken,String
     *                   ver
     * @return: Object
     */
    @GET
    @Path("/mkt.sms.smsmaterial.getlist")
    public BaseOutput getSmsMaterial(@NotEmpty @QueryParam("user_token") String userToken,
                                     @QueryParam("ver") String ver,
                                     @QueryParam("search_word") String searchWord,
                                     @QueryParam("channel_type") Integer channelType,
                                     @NotNull @QueryParam("sms_type") Integer smsType,
                                     @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
                                     @DefaultValue("10") @Min(1) @Max(100) @QueryParam("page_size") Integer size) throws Exception {
        return smsMaterialGetService.getSmsMaterialListByKeyword(searchWord,channelType,smsType,index,size);
    }
    
    
    /**
     * 短信模板id查询模板
     * 
     * 接口：mkt.sms.smstemplet.id.get
     * 
     * @param id
     * @return
     * @Date 2016-11-11
     * @author shuiyangyang
     */
    @GET
    @Path("/mkt.sms.smstemplet.id.get")
    public BaseOutput smsSmstempletIdGet(@NotEmpty @QueryParam("user_token") String userToken,
                    @QueryParam("ver") String ver, @NotNull @QueryParam("id") Integer id)
                    throws Exception {
        return smsSmstempletIdGetService.getSmsSmstempletById(id);
    }
    
    /**
     * 短信模板删除
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016-11-14
     */
    @POST
    @Path("/mkt.sms.smstemplet.del")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput smsSmstempletDel(@Valid SmsSmstempletDelIn body,
                    @Context SecurityContext securityContext) {
        return smsSmstempletDelService.delSmsTemple(body, securityContext);
        
    }
}
