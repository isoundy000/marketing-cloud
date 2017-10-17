/*************************************************
 * @功能简述: API接口 活动管理
 * @项目名称: marketing cloud
 * @see:
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.campaign.api;

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
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.CampaignBodyCreateService;
import cn.rongcapital.mkt.service.CampaignBodyGetService;
import cn.rongcapital.mkt.service.CampaignBodyItemAudienceSearchService;
import cn.rongcapital.mkt.service.CampaignDeleteService;
import cn.rongcapital.mkt.service.CampaignHeaderCreateService;
import cn.rongcapital.mkt.service.CampaignHeaderGetService;
import cn.rongcapital.mkt.service.CampaignHeaderUpdateService;
import cn.rongcapital.mkt.service.CampaignNodeItemListGetService;
import cn.rongcapital.mkt.service.CampaignProgressStatusCountService;
import cn.rongcapital.mkt.service.CampaignProgressStatusListService;
import cn.rongcapital.mkt.service.CampaignSummaryGetService;
import cn.rongcapital.mkt.service.GetCampaignConvertChartListService;
import cn.rongcapital.mkt.service.GetCampaignCustomerSourceListService;
import cn.rongcapital.mkt.service.GroupTagsSearchService;
import cn.rongcapital.mkt.service.SaveCampaignAudienceService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.Audience;
import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.in.CampaignDeleteIn;
import cn.rongcapital.mkt.vo.in.CampaignHeadCreateIn;
import cn.rongcapital.mkt.vo.in.CampaignHeadUpdateIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyCreateOut;
import cn.rongcapital.mkt.vo.out.CampaignBodyGetOut;
import cn.rongcapital.mkt.vo.out.CampaignBodyItemAudienceSearchOut;
import cn.rongcapital.mkt.vo.out.CampaignConvertChartListOut;
import cn.rongcapital.mkt.vo.out.CampaignCustomSourceListOut;
import cn.rongcapital.mkt.vo.out.CampaignHeaderGetOut;
import cn.rongcapital.mkt.vo.out.CampaignNodeItemListOut;
import cn.rongcapital.mkt.vo.out.CampaignProfileOut;
import cn.rongcapital.mkt.vo.out.CampaignProgressStatusCountOut;
import cn.rongcapital.mkt.vo.out.SerarchTagGroupTagsOut;

@Component
@Path(ApiConstant.API_PATH)
@Produces({MediaType.APPLICATION_JSON})
@ValidateRequest
public class MktCampaignApi {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CampaignHeaderCreateService campaignHeaderCreateService;

    @Autowired
    private CampaignHeaderUpdateService campaignHeaderUpdateService;

    @Autowired
    private GroupTagsSearchService groupTagsSearchService;

    @Autowired
    private CampaignBodyCreateService campaignBodyCreateService;

    @Autowired
    private CampaignProgressStatusListService campaignProgressStatusListService;

    @Autowired
    private CampaignProgressStatusCountService campaignProgressStatusCountService;

    @Autowired
    private CampaignDeleteService campaignDeleteService;

    @Autowired
    private GetCampaignConvertChartListService getCampaignConvertChartListService;

    @Autowired
    private CampaignHeaderGetService campaignHeaderGetService;

    @Autowired
    private GetCampaignCustomerSourceListService getCampaignCustomerSourceListService;

    @Autowired
    private CampaignNodeItemListGetService campaignNodeItemListGetService;

    @Autowired
    private CampaignBodyGetService campaignBodyGetService;
    
    @Autowired
    private SaveCampaignAudienceService saveCampaignAudienceService;
    
    @Autowired
    private CampaignSummaryGetService campaignSummaryGetService;
    
    @Autowired
    private CampaignBodyItemAudienceSearchService campaignBodyItemAudienceSearchService;
    
    

    /**
     * @功能简述: 编辑campaign header
     * @param: CampaignHeadUpdateIn body, SecurityContext securityContext
     * @return: BaseOutput
     */
    @POST
    @Path("/mkt.campaign.header.update")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput campaignHeaderUpdate(@Valid CampaignHeadUpdateIn body, @Context SecurityContext securityContext) {
        return campaignHeaderUpdateService.campaignHeaderUpdate(body, securityContext);
    }

    /**
     * @功能简述: 创建campaign header
     * @param: CampaignHeadCreateIn body, SecurityContext securityContext
     * @return: BaseOutput
     */
    @POST
    @Path("/mkt.campaign.header.create")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput campaignHeaderCreate(@Valid CampaignHeadCreateIn body, @Context SecurityContext securityContext) {
        return campaignHeaderCreateService.campaignHeaderCreate(body, securityContext);
    }

    /*
     * @功能简述: 根据关键字查询出系统最末级标签组名称和所关联的标签列表
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
    @Path("/mkt.tag.search.grouptags.get")
    public SerarchTagGroupTagsOut getGroupTagsByKey(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @QueryParam("tag_group_name") String tagGroupName) {
        return groupTagsSearchService.groupTagsSearch(method, userToken, tagGroupName);
    }

    /**
     * @功能简述: 创建/编辑campaign body
     * @param: SegmentHeadIn body, SecurityContext securityContext,Integer campaignHeadId
     * @return: Object
     */
    @POST
    @Path("/mkt.campaign.body.update")
    @Consumes({MediaType.APPLICATION_JSON})
    public CampaignBodyCreateOut campaignBodyCreate(@Valid CampaignBodyCreateIn body,
            @Context SecurityContext securityContext) {
        return campaignBodyCreateService.campaignBodyCreate(body, securityContext);
    }

    /**
     * @功能简述: 获取不同状态下的campaign列表
     * @param: publish_status 0:未发布,1:已发布,2:活动中,3:已结束,4:全部 campaign_name 活动名称摘要，如果有代表作前端界面的模糊查询
     *         index 开始页索引，默认为1 size 分页大小，默认为10，最大值为100
     * @return: Object
     */
    @GET
    @Path("/mkt.campaign.progressstatus.list.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput campaignProgressStatusListGet(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotNull @QueryParam("publish_status") Byte publishStatus, @QueryParam("campaign_name") String campaignName,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
        return campaignProgressStatusListService.campaignProgressStatusList(publishStatus, campaignName, index, size);
    }

    /**
     * @功能简述: 获取不同状态下的campaign数量
     * @param:
     * @return: Object
     */
    @GET
    @Path("/mkt.campaign.progressstatus.count.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public CampaignProgressStatusCountOut campaignProgressStatusCount(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return campaignProgressStatusCountService.campaignProgressStatusCountGet();
    }

    /**
     * @功能简述: 删除campaign
     * @param: campaign_head_id 营销活动id
     * @return: Object
     */
    @POST
    @Path("/mkt.campaign.delete")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput campaignDelete(@Valid CampaignDeleteIn campaignDeleteIn,
            @Context SecurityContext securityContext) {
        return campaignDeleteService.campaignDelete(campaignDeleteIn.getCampaignId());
    }

    /**
     * 查询活动转化图表的相关数据
     * 
     * @param userToken
     * @param ver
     * @param campaignHeadId
     * @return
     */
    @GET
    @Path("/mkt.campaign.conversion.list")
    public CampaignConvertChartListOut getCompaignConversionList(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
        return getCampaignConvertChartListService.getCompaignConvertChartList(campaignHeadId);
    }

    @GET
    @Path("/mkt.campaign.analysis.list")
    public BaseOutput campaignAnalysisList() {
        BaseOutput out =
                new BaseOutput(ApiConstant.INT_ZERO, ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        out.getData().add(campaignHeaderGetService.campaignAnalysisList(null, null, null, null));
        return out;
    }

    /**
     * @功能简述: 根据id获取segment header
     * @param: SegmentHeadIn body, SecurityContext securityContext,Integer campaignHeadId
     * @return: Object
     */
    @GET
    @Path("/mkt.campaign.header.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public CampaignHeaderGetOut campaignHeaderGet(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
        return campaignHeaderGetService.campaignHeaderGet(userToken, ver, campaignHeadId);
    }

    /**
     * 活动概要查询
     * 
     * @param userToken
     * @param ver
     * @param campaignHeadId
     * @return
     */
    @GET
    @Path("/mkt.campaign.profile.list")
    @Consumes({MediaType.APPLICATION_JSON})
    public CampaignProfileOut campaignProfileList(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
        return campaignHeaderGetService.campaignProfileList(userToken, ver, campaignHeadId);
    }

    /**
     * 查询活动转化图表的相关数据
     * 
     * @param userToken
     * @param ver
     * @param campaignHeadId
     * @return
     */
    @GET
    @Path("/mkt.campaign.customer.source.list")
    public CampaignCustomSourceListOut getCampaignCustomerSourceList(
            @NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
            @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
        return getCampaignCustomerSourceListService.getCampaignCustomSourceInfo(campaignHeadId);
    }

    /**
     * @功能简述: 获取活动编排页面左侧的节点和子节点列表
     * @param: SegmentHeadIn body, SecurityContext securityContext
     * @return: Object
     */
    @GET
    @Path("/mkt.campaign.nodeitem.list.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public CampaignNodeItemListOut campaignBodyGet(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver) {
        return campaignNodeItemListGetService.campaignNodeItemListGet(userToken, ver);
    }

    /**
     * @功能简述: 根据campaign_head_id 获取campaign body
     * @param: SegmentHeadIn body, SecurityContext securityContext,Integer campaignHeadId
     * @return: Object
     */
    @GET
    @Path("/mkt.campaign.body.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public CampaignBodyGetOut campaignBodyGet(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
        return campaignBodyGetService.campaignBodyGet(userToken, ver, campaignHeadId);
    }
    
    /**
     * @功能描述:活动编排，保存人群
     * @Param: String user_token, String ver, String audience_name
     * @return: Object
     */
    @POST
    @Path("/mkt.campaign.node.audience.save")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Object saveCampaignAudience(@Valid Audience audience, @Context SecurityContext securityContext) {
        return saveCampaignAudienceService.saveCampaignAudience(audience, securityContext);
    }
    
    /**
     * @功能简述: 查询营销活动个数和触达人数
     * @param:
     * @return: Object
     */
    @GET
    @Path("/mkt.campaign.summary.get")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput campaignSummaryGet(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return campaignSummaryGetService.campaignSummaryGet();
    }
    
    /**
     * 搜索活动节点上的人
     * 
     * @param userToken
     * @param ver
     * @param name
     * @param campaignHeadId
     * @param itemId
     * @return
     */
    @GET
    @Path("/mkt.campaign.body.item.audience.search")
    public CampaignBodyItemAudienceSearchOut campaignBodyItemAudienceSearch(
            @NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
            @QueryParam("search_field") String name, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId,
            @NotNull @QueryParam("item_id") String itemId) {
        return campaignBodyItemAudienceSearchService.campaignBodyItemAudienceSearch(name, campaignHeadId, itemId);
    }

}
