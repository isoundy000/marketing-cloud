/*************************************************
 * @功能简述: API接口 受众细分
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 
 * @date: 
 * @复审人: 
*************************************************/
package cn.rongcapital.mkt.segment.api;

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
import cn.rongcapital.mkt.service.CreupdateSegmentService;
import cn.rongcapital.mkt.service.SegmentAudienctAnalysisService;
import cn.rongcapital.mkt.service.SegmentBodyGetService;
import cn.rongcapital.mkt.service.SegmentBodyUpdateService;
import cn.rongcapital.mkt.service.SegmentDetailGetService;
import cn.rongcapital.mkt.service.SegmentFilterGetService;
import cn.rongcapital.mkt.service.SegmentHeaderCreateService;
import cn.rongcapital.mkt.service.SegmentHeaderGetService;
import cn.rongcapital.mkt.service.SegmentHeaderUpdateService;
import cn.rongcapital.mkt.service.SegmentPublishStatusCountService;
import cn.rongcapital.mkt.service.SegmentPublishstatusListService;
import cn.rongcapital.mkt.service.SegmentSearchDownloadService;
import cn.rongcapital.mkt.service.SegmentSearchGetService;
import cn.rongcapital.mkt.service.SegmentSecondaryTaglistSearchService;
import cn.rongcapital.mkt.service.SegmentTagGetService;
import cn.rongcapital.mkt.service.SegmentTagUpdateService;
import cn.rongcapital.mkt.service.SegmentTagkeyTagListService;
import cn.rongcapital.mkt.service.SegmentTagnameTagCountService;
import cn.rongcapital.mkt.service.SegmentTagnameTagListService;
import cn.rongcapital.mkt.service.SegmentTagnameTagValueService;
import cn.rongcapital.mkt.service.TagGroupLimitService;
import cn.rongcapital.mkt.service.TagSystemFlagListGetService;
import cn.rongcapital.mkt.service.TagSystemFuzzyListGetService;
import cn.rongcapital.mkt.service.TagSystemTreeListGetService;
import cn.rongcapital.mkt.service.TagSystemValueListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentBodyUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentCountFilterIn;
import cn.rongcapital.mkt.vo.in.SegmentCreUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentFilterSumIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadCreateIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadDeleteIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentTagUpdateIn;
import cn.rongcapital.mkt.vo.in.TagGroupsListIn;
import cn.rongcapital.mkt.vo.in.TagListSecondarySearchIn;
import cn.rongcapital.mkt.vo.out.SegmentPublishstatusListOut;

@Component
@Path(ApiConstant.API_PATH)
@Produces({MediaType.APPLICATION_JSON})
@ValidateRequest
public class MktSegmentApi {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TagGroupLimitService tagGroupLimitService;

    @Autowired
    private SegmentAudienctAnalysisService segmentAudienctAnalysisService;

    @Autowired
    private TagSystemTreeListGetService tagSystemTreeListGetService;

    @Autowired
    private TagSystemFlagListGetService tagSystemFlagListGetService;

    @Autowired
    private TagSystemFuzzyListGetService tagSystemFuzzyListGetService;

    @Autowired
    private SegmentTagnameTagCountService segmentTagnameTagCountService;

    @Autowired
    private TagSystemValueListGetService tagSystemValueListGetService;

    @Autowired
    private SegmentFilterGetService segmentFilterGetService;

    @Autowired
    private CreupdateSegmentService creupdateSegmentService;

    @Autowired
    private SegmentTagGetService segmentTagGetService;

    @Autowired
    private SegmentTagUpdateService segmentTagUpdateService;

    @Autowired
    private SegmentHeaderUpdateService segmentHeaderUpdateService;

    @Autowired
    private SegmentPublishstatusListService segmentPublishstatusListService;

    @Autowired
    private SegmentSearchGetService segmentSearchGetServer;

    @Autowired
    private SegmentSearchDownloadService segmentSearchDownloadService;
    
    @Autowired
    private SegmentHeaderCreateService segmentHeaderService;
    
    @Autowired
    private SegmentPublishStatusCountService segmentPublishStatusCountService;
    
    @Autowired
    private SegmentHeaderGetService segmentHeaderGetService;

    @Autowired
    private SegmentBodyUpdateService segmentBodyUpdateService;
    
    @Autowired
    private SegmentTagnameTagListService segmentTagnameTagListService;

    @Autowired
    private SegmentTagkeyTagListService segmentTagkeyTagListService;

    @Autowired
    private SegmentTagnameTagValueService segmentTagnameTagValueService;
    
    @Autowired
    private SegmentBodyGetService segmentBodyGetService;
    
    @Autowired
    private SegmentDetailGetService segmentDetailGetService;

    @Autowired
    private SegmentSecondaryTaglistSearchService segmentSecondaryTaglistSearchService;
    

    /**
     * 获取创建联系人表单界面中，右侧的显示列表
     * 
     * @param userToken
     * @param var
     * @param contactId
     * @return
     */
    @GET
    @Path("mkt.taggroup.limit.get")
    public BaseOutput getContactKeyList(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @QueryParam("source") String source) {
        return tagGroupLimitService.getTagGroupLimit(source);
    }

    /**
     * 细分管理分析
     * 
     * @param userToken
     * @param var
     * @param contactId
     * @return
     */
    @GET
    @Path("mkt.segment.audienct.analysis.get")
    public BaseOutput getSegmentAnalysis(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("tag_id") String tagId,
            @NotNull @QueryParam("segment_head_id") Integer segmentHeadId) {
        return segmentAudienctAnalysisService.getSegmentAudienctAnalysis(tagId, segmentHeadId);
    }

    /**
     * 系统标签（树形）结构接口
     * 
     * @return
     * @author shuiyangyang
     * @Date 2016-11-09
     */
    @GET
    @Path("/mkt.tag.system.tree.list.get")
    public BaseOutput tagSystemTreeListGet(@NotEmpty @QueryParam("user_token") String userToken,
            @QueryParam("ver") String ver) {
        return tagSystemTreeListGetService.getTagSystemTreeList();
    }

    /**
     * 获取推荐标签
     * 
     * @return
     * @author shuiyangyang
     * @Date 2016-11-09
     */
    @GET
    @Path("/mkt.tag.system.flag.list.get")
    public BaseOutput tagSystemFlagListGet(@NotEmpty @QueryParam("user_token") String userToken,
            @QueryParam("ver") String ver) {
        return tagSystemFlagListGetService.getTagSystemFlagList();
    }

    /**
     * 根据页面输入值模糊查询标签，返回标签或者标签值 （全路径的标签或者标签值《带有标签类型：标签值，标签》分页
     * 
     * @param tagName
     * @param index
     * @param size
     * @return
     * @author shuiyangyang
     * @date 2016-11-11
     */
    @GET
    @Path("/mkt.tag.system.fuzzy.list.get")
    public BaseOutput tagSystemFuzzyListGet(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @QueryParam("tag_name") String tagName,
            @QueryParam("choice_show") String choiceShow,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
        return tagSystemFuzzyListGetService.getTagSystemFuzzyList(tagName,choiceShow, index, size);
    }

    /**
     * @功能简述: 获取标签的柱状图数据
     * @param method
     * @param userToken
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.tagname.tagcount.get")
    public BaseOutput getTagCountByGroupId(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("tag_ids") String tagIds) {
        // segmentTagnameTagCountService.getTagCountById(tagIds);
        return segmentTagnameTagCountService.getMongoTagCountByTagIdList(tagIds);
    }

    /**
     * 获取标签值
     * 
     * 接口：mkt.tag.system.value.list.get
     * 
     * @param tagId
     * @return
     * @author shuiyangyang
     * @Date 2016-11-14
     */
    @GET
    @Path("/mkt.tag.system.value.list.get")
    public BaseOutput tagSystemValueListGet(@NotEmpty @QueryParam("user_token") String userToken,
            @QueryParam("ver") String ver, @NotEmpty @QueryParam("tag_id") String tagId) {
        return tagSystemValueListGetService.getTagSystemValueList(tagId);
    }

    /**
     * @功能简述: 细分漏斗过滤
     * @param:
     * @return: Object
     */
    @POST
    @Path("/mkt.segment.filter.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput segmentFilterGet(TagGroupsListIn tagGroupsListIn, @Context SecurityContext securityContext) {
        return segmentFilterGetService.getSegmentFilterResult(tagGroupsListIn);
    }

    /**
     * @功能简述: 编辑segment body
     * @param: SegmentBodyUpdateIn body, SecurityContext securityContext
     * @return: Object
     */
    @POST
    @Path("/mkt.segment.creupdate")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput createOrUpdateSegment(SegmentCreUpdateIn segmentCreUpdateIn,
            @Context SecurityContext securityContext) {
        return creupdateSegmentService.creupdateSegment(segmentCreUpdateIn);
    }

    /**
     * @功能简述: 获取受众细分关联的tag
     * @param userToken
     * @param segmentHeadId
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.tag.get")
    public BaseOutput getSegmentHeaderTag(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("segment_head_id") String segmentHeadId) {
        return segmentTagGetService.getSegmentTag(userToken, segmentHeadId);
    }

    /**
     * @功能简述: 打标签，增加或修改受众细分关联的tag
     * @param: SegmentTagUpdateIn body, SecurityContext securityContext
     * @return: Object
     */
    @POST
    @Path("/mkt.segment.tag.update")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput segmentBodyUpdate(@Valid SegmentTagUpdateIn body, @Context SecurityContext securityContext) {
        return segmentTagUpdateService.updateSegmentTag(body, securityContext);
    }

    /**
     * @功能简述: 编辑segment header
     * @param: SegmentHeadIn body, SecurityContext securityContext
     * @return: Object
     */
    @POST
    @Path("/mkt.segment.header.update")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput segmentHeaderUpdate(@Valid SegmentHeadUpdateIn body, @Context SecurityContext securityContext) {
        return segmentHeaderUpdateService.segmentHeaderUpdate(body, securityContext);
    }

    /**
     * @功能简述: 创建segment header
     * @param: SegmentHeadIn body, SecurityContext securityContext
     * @return: Object
     */
    @POST
    @Path("/mkt.segment.header.delete")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput segmentHeaderDelete(@Valid SegmentHeadDeleteIn body, @Context SecurityContext securityContext) {
        return segmentHeaderUpdateService.deleteSegmentHeader(body, securityContext);
    }

    @POST
    @Path("/mkt.segment.gendercount.list")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput segmentGenderCountList(@Valid SegmentCountFilterIn input) {
        return segmentFilterGetService.segmentGenderCountList(input);
    }

    @POST
    @Path("/mkt.segment.provincecount.list")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput segmentProvinceCountList(@Valid SegmentCountFilterIn input) {
        return segmentFilterGetService.segmentProvinceCountList(input);
    }

    @POST
    @Path("/mkt.segment.receivecount.list")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput segmentReceiveCountList(@Valid SegmentCountFilterIn input) {
        return segmentFilterGetService.segmentReceiveCountList(input);
    }

    // /**
    // * @功能简述: 获取受众细分漏斗计算结果
    // * @param body
    // * @param securityContext
    // * @return BaseOutput
    // */
    // @POST
    // @Path("/mkt.segment.filter.get")
    // @Consumes({ MediaType.APPLICATION_JSON })
    // public BaseOutput getSegmentFilterCount(@Valid SegmentFilterCountIn body,
    // @Context SecurityContext securityContext) {
    // return segmentFilterGetService.getSegmentFilterCount(body, securityContext);
    // }

    @POST
    @Path("/mkt.segment.filter.sum.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput getSegmentFilterSum(@Valid SegmentFilterSumIn body, @Context SecurityContext securityContext) {
        return segmentFilterGetService.getSegmentFilterSum(body);
    }

    /**
     * @功能简述: 获取某个发布状态下的segemnt列表
     * @param: String userToken, String ver, String publishStatus
     * @return: Object
     */
    @GET
    @Path("/mkt.segment.publishstatus.list.get")
    public SegmentPublishstatusListOut segmentPublishstatusList(@NotEmpty @QueryParam("user_token") String userToken,
            @NotNull @QueryParam("publish_status") Integer publishStatus,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size,
            @NotEmpty @QueryParam("ver") String ver, @QueryParam("keyword") String keyword) throws Exception {
        return segmentPublishstatusListService.segmentPublishstatusList(userToken, publishStatus, index, size, ver,
                keyword);
    }

    /**
     * 根据输入名字模糊查询都有哪些人在人群中
     * 
     * @param head_id
     * @param query_name
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.search.get")
    public BaseOutput segmentSearch(@NotNull @QueryParam("head_id") Integer head_id,
            @NotEmpty @QueryParam("query_name") String query_name) {

        return segmentSearchGetServer.SegmentSearch(head_id, query_name);
    }

    /**
     * 根据主键id下载相应人群数
     * 
     * @param head_id
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.search.download")
    public BaseOutput getSegmentSearchDownload(@NotEmpty @QueryParam("user_token") String user_token,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("head_id") Integer head_id) {
        return segmentSearchDownloadService.getSegmentSearchDownload(head_id);
    }
    
    /**
     * @功能简述: 根据id获取segment header
     * @param: SegmentHeadIn
     *             body, SecurityContext securityContext, String segmentId
     * @return: Object
     */
    @GET
    @Path("/mkt.segment.header.get")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput segmentHeaderGet(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("segment_head_id") String segmentId) {
        return segmentHeaderGetService.segmentHeaderGet(userToken, ver, segmentId);
    }

    /**
     * @功能简述: 创建segment header
     * @param: SegmentHeadIn
     *             body, SecurityContext securityContext
     * @return: Object
     */
    @POST
    @Path("/mkt.segment.header.create")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput segmentHeaderCreate(@Valid SegmentHeadCreateIn body, @Context SecurityContext securityContext) {
        return segmentHeaderService.segmentHeaderCreate(body, securityContext);
    }

    /**
     * @功能简述: 查询不同发布状态下的segment数量
     * @param: String
     *             userToken, String ver
     * @return: Object
     */
    @GET
    @Path("/mkt.segment.publishstatus.count.get")
    public Object segmentPublishstatusCount(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver) throws Exception {
        return segmentPublishStatusCountService.segmentPublishstatusCount(userToken, ver);
    }
    
    /**
     * @功能描述:查询系统推荐标签列表
     * @Param: String method, String userToken
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.tagname.taglist.get")
    public BaseOutput getSysRecommendedTagList(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        // segmentTagnameTagListService.getSysRecommendedTagList();
        return segmentTagnameTagListService.getMongoTagRecommendList(method, userToken);
    }

    /**
     * @功能简述: 编辑segment body
     * @param: SegmentBodyUpdateIn
     *             body, SecurityContext securityContext
     * @return: Object
     */
    @POST
    @Path("/mkt.segment.body.update")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Object segmentBodyUpdate(@Valid SegmentBodyUpdateIn body, @Context SecurityContext securityContext) {
        return segmentBodyUpdateService.segmentBodyUpdate(body, securityContext);
    }


    /**
     * @功能描述:查询细分的详细信息
     * @Param: String method, String userToken
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.detail.get")
    public BaseOutput getSegmentDetail(@NotEmpty @QueryParam("method") String method,
                                       @NotEmpty @QueryParam("user_token") String userToken,
                                       @NotEmpty @QueryParam("ver") String ver,
                                       @NotNull  @QueryParam("id") Long id) {
        return segmentDetailGetService.getSegmentDetail(id);
    }

    /**
     * @功能简述: 模糊搜索受众细分标签值的列表(二级搜索)
     * @param:
     * @return: Object
     */
    @POST
    @Path("/mkt.segment.secondary.taglist.search")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput searchSegmentSecondaryTaglist(TagListSecondarySearchIn tagListSecondarySearchIn, @Context SecurityContext securityContext) {
        return segmentSecondaryTaglistSearchService.searchSegmentSecondaryTaglist(tagListSecondarySearchIn);
    }
    
    /*
     * @功能简述: 根据关键字查询出系统最末级标签名称列表
     * 
     * @param method
     * 
     * @param userToken
     * 
     * @param tagGroupName
     * 
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.tagkey.taglist.get")
    public BaseOutput getLastTagByKey(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @QueryParam("tag_group_name") String tagGroupName) {
        //segmentTagkeyTagListService.getLastTagByKey(tagGroupName);
        return segmentTagkeyTagListService.getMongoTagRecommendByLike(tagGroupName);
    }

    /**
     * @功能简述: 根据系统最末级标签组ID查询出标签内容列表
     * @param method
     * @param userToken
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.tagname.tagvalue.get")
    public BaseOutput getTagValueByGroupId(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("tag_group_id") String tagGroupId) {
        //segmentTagnameTagValueService.getTagValueByGroupId(tagGroupId);
        return segmentTagnameTagValueService.getMongoTagValueByTagId(tagGroupId);
    }


    /**
     * @功能简述: 获取受众细分body信息
     * @param userToken
     * @param segmentHeadId
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.segment.body.get")
    public BaseOutput getSegmentBody(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("segment_head_id") String segmentHeadId) {
        return segmentBodyGetService.getSegmentBody(userToken, segmentHeadId);
    }

}
