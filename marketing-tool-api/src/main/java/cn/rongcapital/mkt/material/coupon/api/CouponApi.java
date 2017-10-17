/*************************************************
 * @功能简述: API接口优惠码主类
 * @项目名称: marketing cloud
 * @see:
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/12/06
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.api;

import javax.jms.JMSException;
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
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeDictionaryService;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeListService;
import cn.rongcapital.mkt.material.coupon.service.CouponFileUploadService;
import cn.rongcapital.mkt.material.coupon.service.CouponSaveService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponAudienceCreateService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeCheckService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeVerifyListService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCountGetService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponDeleteService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponEditDetailService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponGeneralGetService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponGetSystemTimeService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponPageListService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponPutInGeneralService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponReleaseGeneralService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponVerifyGeneralService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCreateAudienceVO;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponDeleteIn;
import cn.rongcapital.mkt.material.coupon.vo.in.MaterialCouponCodeVerifyIn;
import cn.rongcapital.mkt.material.coupon.vo.in.MaterialCouponInfoIn;
import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeDictionaryListOut;
import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeMaxCountOut;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponListOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class CouponApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponCountGetService materialCouponCountGetService;

    @Autowired
    private MaterialCouponReleaseGeneralService materialCouponReleaseGeneralService;

    @Autowired
    private CouponCodeListService couponCodeListService;
	
    @Autowired
    private MaterialCouponPageListService couponPageListService;
    
    @Autowired
    private MaterialCouponGetSystemTimeService materialCouponGetSystemTimeService;

    @Autowired
    private MaterialCouponCodeVerifyListService materialCouponCodeVerifyListService;

    @Autowired
    private MaterialCouponGeneralGetService materialCouponGeneralGetService;

    @Autowired
    private MaterialCouponPutInGeneralService materialCouponPutInGeneralService;

    @Autowired
    private MaterialCouponDeleteService materialCouponDeleteService;
    
    @Autowired
    private MaterialCouponCodeCheckService materialCouponCodeCheckService;//优惠码检查Service

    @Autowired
    private CouponFileUploadService couponFileUploadService;
    
    @Autowired
    private CouponSaveService couponSaveService;
    
	@Autowired
	private MaterialCouponVerifyGeneralService materialCouponVerifyGeneralService;
	
	@Autowired
    private CouponCodeDictionaryService dictionaryService; //获取核销页面数据字典
	
	@Autowired
    private MaterialCouponEditDetailService materialCouponEditDetailService;
	    
	@Autowired
	private MaterialCouponAudienceCreateService materialCouponAudienceCreateService;
    /**
     * 获取指定条件的优惠券的数量
     * 
     * 接口：mkt.material.coupon.counts
     * 
     * @param user_token
     * @param ver
     * @param chanel_code
     * @param keyword
     * @return BaseOutput
     * @author zhuxuelong
     * @Date 2016-12-06
     */
    @GET
    @Path("/mkt.material.coupon.counts")
    public BaseOutput getMaterialCouponCount(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @QueryParam("channel_code") String chanelCode,
            @QueryParam("keyword") String keyword) throws Exception {
        return materialCouponCountGetService.getMaterialCouponCount(chanelCode, keyword);
    }

    /**
     * @author guozhenchao
     * @功能简述: 获取优惠码列表接口
     * @param fileTagUpdateIn
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.materiel.coupon.code.list")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput couponCodeList(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id,
            @QueryParam("index") Integer index, @QueryParam("size") Integer size) {
        return couponCodeListService.couponCodeList(id, index, size);
    }

    /**
     * @author guozhenchao
     * @功能简述: 获取已发放优惠码列表接口
     * @param fileTagUpdateIn
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.materiel.coupon.issued.code.list")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput couponIssuedCodeList(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id,
            @QueryParam("index") Integer index, @QueryParam("size") Integer size) {
        return couponCodeListService.couponIssuedCodeList(id, index, size);
    }
    
    
    /**
     * @author guozhenchao
     * @功能简述: 获取文件上传url
     * @param: String
     *      userToken, String ver,String user_id
     * @return: Object
     */
    @GET
    @Path("/mkt.materiel.coupon.file.upload.get")
    public Object getMigrationFileUploadUrl(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("user_id") String userId) throws Exception {
        return couponFileUploadService.getCouponFileUploadUrlGet(userId);
    }
    
    
    /**
     * @author guozhenchao
     * @功能简述:优惠券文件上传接口
     * @param fileUnique
     * @param input
     * @return
     */
    @POST
    @Path("/mkt.materiel.coupon.file.upload")
    @Consumes("multipart/form-data")
    public BaseOutput fileUpload(@NotEmpty @QueryParam("user_id") String userId, MultipartFormDataInput input){
        return couponFileUploadService.uploadFile(input, userId);
    }
    /**
     * 获取券码投放流失概览数据
     * 
     * 接口：mkt.material.coupon.releaseGeneral
     * 
     * @param user_token
     * @param ver
     * @param id
     * @return BaseOutput
     * @author shanjingqi
     * @Date 2016-12-06
     */
    @GET
    @Path("/mkt.material.coupon.releaseGeneral")
    public BaseOutput releaseGeneral(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String version, @NotNull @QueryParam("id") Long id) {

        return materialCouponReleaseGeneralService.releaseGeneralById(id, userToken, version);
    }

    /**
     * @author liuhaizhan
     * @功能简述 优惠券删除
     * @param：入参请求
     * @return：message2
     */
    @POST
    @Path("/mkt.material.coupon.delete")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput delete(@Valid MaterialCouponDeleteIn mcdi) {
        return materialCouponDeleteService.delete(mcdi.getId());
    }

    /**
     * @author liuhaizhan
     * @功能简述 获取发放统计数据
     * @param：id 优惠券Id
     * @return：message2
     */
    @GET
    @Path("/mkt.material.coupon.putInGeneral")
    public BaseOutput getPutInGeneral(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id) {
        return materialCouponPutInGeneralService.putInGeneral(id);
    }

   
  


    /**
     * @功能描述: message
     * @param userToken
     * @param ver
     * @param searchWord
     * @param channelType
     * @param index
     * @param size
     * @return
     * @throws Exception BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月7日
     */
    @GET
    @Path("/mkt.material.coupon.list")
    public MaterialCouponListOut getMaterialCouponListByKeyword(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("channel_code") String channelCode,
            @QueryParam("keyword") String keyword, @QueryParam("coupon_status") String couponStatus,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size,
            @QueryParam("filter_overdue") boolean filterOverdue) throws Exception {
    
        return couponPageListService.getMaterialCouponListByKeyword(channelCode, couponStatus, keyword, index, size, filterOverdue);
    }
    
    /**
     * @功能简述: 获取当前系统时间
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.material.coupon.systemtime")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput getSystemTime(@NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver) {
        return materialCouponGetSystemTimeService.getSystemTime();
    }
    
    /**
     * @功能简述: 获取优惠券概要信息
     * @param id 优惠券主键
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.material.coupon.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput getMaterialCouponGeneral(@NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id) {
        return materialCouponGeneralGetService.getMaterialCouponGeneral(id);
    }

    /**
     * @功能简述: 获取核销对账数据列表
     * 
     * @param id 优惠券主键
     * @param blur_search 查询关键字
     * @param receive_status 收到状态
     * @param verify_status 使用状态
     * @param expire_status 过期状态
     * @param index 页码
     * @param size 单页最大数量
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.material.coupon.verify.list")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput listMaterialCouponCodeVerfy(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id,
            @QueryParam("blur_search") String blurSearch, @QueryParam("receive_status") String receiveStatus,
            @QueryParam("verify_status") String verifyStatus, @QueryParam("expire_status") String expireStatus,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
        return materialCouponCodeVerifyListService.listMaterialCouponCodeVerfy(id, blurSearch, receiveStatus,
                verifyStatus, expireStatus, index, size);
    }

    /**
     * @功能描述: 优惠码校验接口
     * @param userToken
     * @param ver
     * @param id
     * @param couponCode 优惠码
     * @param user 用户ID
     * @return
     * @throws Exception BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月9日
     */
    @GET
    @Path("/mkt.material.coupon.check")
    public BaseOutput materialCouponCodeCheck(
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, 
            @NotNull  @QueryParam("id") Long id,
            @NotEmpty @QueryParam("coupon_code") String couponCode,
            @NotEmpty @QueryParam("user") String user) throws Exception {

        return materialCouponCodeCheckService.materialCouponCodeCheck(id, couponCode, user);
    }
    
    /**
     * @author guozhenchao
     * @功能简述:优惠券文件上传接口
     * @param fileUnique
     * @param input
     * @return
     */
    @POST
    @Path("/mkt.materiel.coupon.save")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput couponSave(@Valid MaterialCouponInfoIn couponInfo){
        return couponSaveService.save(couponInfo);
    }
    
    /**
     * @功能描述: 优惠码核销接口
     * @param userToken
     * @param ver
     * @param id
     * @param couponCode 优惠码
     * @param user 用户ID
     * @return
     * @throws Exception BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月9日
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path("/mkt.material.coupon.verify")
    public BaseOutput materialCouponCodeVerify(@Valid MaterialCouponCodeVerifyIn in) throws Exception {
        return materialCouponCodeCheckService.materialCouponCodeVerify(in.getId(), in.getCoupon_code(), in.getUser());
    }
    
	/**
	 * 券码核销流失概览
	 * 
	 * 接口：mkt.material.coupon.verifyGeneral
	 * 
	 * @param user_token
	 * @param ver
	 * @param id
	 * @author shanjingqi
	 * @Date 2016-12-09
	 */
	@GET
	@Path("/mkt.material.coupon.verifyGeneral")
	public BaseOutput verifyeGeneral(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String version, @NotNull @QueryParam("id") Long id) {
		return materialCouponVerifyGeneralService.verifyGeneralById(id, userToken, version);
	}    

    /**
     * @功能描述: 获取优惠码最大可用数量
     * @param userToken
     * @param ver
     * @param id
     * @return
     * @throws Exception BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月12日
     */
    @GET
    @Path("/mkt.material.coupon.max.count")
    public CouponCodeMaxCountOut materialCouponCodeCheck(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("type_code") String typeCode,
            @DefaultValue("5") @Min(5) @Max(20) @QueryParam("length") int length) throws Exception {
        return materialCouponCodeCheckService.materialCouponCodeMaxCount(typeCode, length);
    }
    
    /**
     * @功能描述: 核销对账页面》获取数据字典
     * @param userToken
     * @param ver
     * @return
     * @throws Exception CouponCodeDictionaryListOut
     * @author xie.xiaoliang
     * @since 2016-12-14
     */
    @GET
    @Path("/mkt.material.coupon.dictionary")
    public CouponCodeDictionaryListOut couponCodeDictionaryService(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver) throws Exception {

        return this.dictionaryService.materialCouponDictionary();
    }
    
    
    /**
     * @author liuhaizhan
     * @功能简述:返回编辑页
     * @param
     * @return
     */
    @GET
    @Path("/mkt.material.coupon.editdetail")
    public BaseOutput getEditDetail(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id) {
        return materialCouponEditDetailService.getCouponEditdes(id);
    }
    
    
	
    /**
     * 根据筛选条件新建固定人群
     * 
     * 接口：mkt.material.coupon.audience.create
     * 
     * @param MaterialCouponCreateAudienceVO
     * @author shanjingqi
     * @throws JMSException 
     * @Date 2016-12-13
     */ 
	@POST
    @Path("/mkt.material.coupon.audience.create")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput createTargetAudienceGroup(@Valid MaterialCouponCreateAudienceVO mcca) throws JMSException {
        return materialCouponAudienceCreateService.createTargetAudienceGroup(mcca);
    }
}
