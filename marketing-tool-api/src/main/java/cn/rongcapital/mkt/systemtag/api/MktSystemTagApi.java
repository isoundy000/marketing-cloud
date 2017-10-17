/*************************************************
 * @功能简述: API接口 系统标签
 * @项目名称: marketing cloud
 * @see:
 * @author:
 * @version:
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.systemtag.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import cn.rongcapital.mkt.service.CustomTagDeleteService;
import cn.rongcapital.mkt.service.CustomTagGetService;
import cn.rongcapital.mkt.service.SystemTagService;
import cn.rongcapital.mkt.service.TagCustomTaxonomyDelService;
import cn.rongcapital.mkt.service.TagCustomTaxonomyListGetService;
import cn.rongcapital.mkt.service.TagCustomTaxonomyRootListGetService;
import cn.rongcapital.mkt.service.TagCustomTaxonomyRootSaveService;
import cn.rongcapital.mkt.service.TagCustomTaxonomySaveService;
import cn.rongcapital.mkt.service.TagCustomTaxonomyShowSetService;
import cn.rongcapital.mkt.service.TagDownloadCustomAudienceService;
import cn.rongcapital.mkt.service.TagSystemFlagSetService;
import cn.rongcapital.mkt.service.TagSystemListGetService;
import cn.rongcapital.mkt.service.TagSystemTagcountService;
import cn.rongcapital.mkt.service.TaggroupSystemListGetService;
import cn.rongcapital.mkt.service.TaggroupSystemMenulistGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagDeleteIn;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyDelIn;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyRootSaveIn;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomySaveIn;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyShowSetIn;
import cn.rongcapital.mkt.vo.in.TagSystemFlagSetIn;
import cn.rongcapital.mkt.vo.in.TagValueUpdateIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({MediaType.APPLICATION_JSON})
@ValidateRequest
public class MktSystemTagApi {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaggroupSystemListGetService taggroupSystemListGetService;
    
    @Autowired
    private TagSystemFlagSetService tagSystemFlagSetService;
    
    @Autowired
    private TaggroupSystemMenulistGetService taggroupSystemMenulistGetService;
    
    @Autowired
    private TagSystemTagcountService tagSystemTagcountService;
    
    @Autowired
    private CustomTagGetService customTagGetService;

    @Autowired
    private CustomTagDeleteService customTagDeleteService;
    
    @Autowired
    private TagSystemListGetService tagSystemListGetService;
    
    @Autowired
    private TagDownloadCustomAudienceService tagDownloadCustomAudienceService;
    
    @Autowired
    private SystemTagService systemTagService;
    
    @Autowired
    private TagCustomTaxonomySaveService tagCustomTaxonomySaveService;

    @Autowired
    private TagCustomTaxonomyListGetService tagCustomTaxonomyListGetService;

    @Autowired
    private TagCustomTaxonomyDelService tagCustomTaxonomyDelService;

    @Autowired
    private TagCustomTaxonomyRootListGetService tagCustomTaxonomyRootListGetService;

    @Autowired
    private TagCustomTaxonomyShowSetService tagCustomTaxonomyShowSetService;
    
    @Autowired
    private TagCustomTaxonomyRootSaveService tagCustomTaxonomyRootSaveService;


    /**
     * 获取系统标签组列表
     * 
     * @param method
     * @param userToken
     * @param tagGroupId
     * @param index
     * @param size
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.taggroup.system.list.get")
    public BaseOutput getTagGroupByParentGroupId(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotNull @QueryParam("tag_group_id") String tagGroupId, @QueryParam("index") Integer index,
            @QueryParam("size") Integer size) {
        // taggroupSystemListGetService.getTagGroupByParentGroupId(method, userToken, tagGroupId,
        // index, size);
        return taggroupSystemListGetService.getMongoTagRecommendByTagTreeId(method, userToken, tagGroupId, index, size);
    }
    
    /**
     * 推荐标签（设置）
     *
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016-11-08
     */
    @POST
    @Path("/mkt.tag.system.flag.set")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput tagSystemFlagSet(@Valid TagSystemFlagSetIn body,
                    @Context SecurityContext securityContext) {
        return tagSystemFlagSetService.updateFlag(body, securityContext);
    }
    
    /**
     * @功能简述: 获取系统标签组列表
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.taggroup.system.menulist.get")
    public BaseOutput getTaggroupSystemMenulist(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @QueryParam("index") Integer index,
            @QueryParam("size") Integer size) {
        //taggroupSystemMenulistGetService.getTaggroupSystemMenulist(method, userToken, index, size);
        return taggroupSystemMenulistGetService.getMonggTagTreelist(method, userToken, index, size);
    }
    
    /**
     * @功能简述: 获取系统标签总数量
     * @param method
     * @param userToken
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.tag.system.tagcount.get")
    public BaseOutput getTagcount(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        //tagSystemTagcountService.getTagcount(method, userToken);
        return tagSystemTagcountService.getMonggTagcount(method, userToken);
    }

    /**
     * @功能简述 : 获取自定义标签列表
     * @param: String
     *             method, String userToken, Integer index, Integer size
     * @return: Object
     */
    @GET
    @Path("/mkt.tag.custom.list.get")
    public BaseOutput getCustomTagList(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @QueryParam("index") Integer index,
            @QueryParam("size") Integer size) {
        return customTagGetService.getCustomTagList(method, userToken, index, size);
    }

    /**
     * @功能简述 : 删除某个自定义标签
     * @param: String
     *             method, String userToken, Integer tag_id
     * @return: Object
     */
    @POST
    @Path("mkt.tag.custom.delete")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput deleteCustomTag(@Valid CustomTagDeleteIn body, @Context SecurityContext securityContext) {
        return customTagDeleteService.deleteCustomTag(body);
    }

    /**
     * @功能简述 : 根据自定义标签下载覆盖的人群
     * @param: String
     *             userToken, Integer tag_id
     * @return: Object
     */
    @GET
    @Path("mkt.tag.custom.audience.download")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput downloadCustomAudience(@NotEmpty @QueryParam("method") String method,
            @NotNull @QueryParam("tag_id") String tagId) {
        return tagDownloadCustomAudienceService.downloadCustomAudience(tagId);
    }


    /**
     * 获取系统标签内容列表
     * 
     * @param method
     * @param userToken
     * @param tagGroupId
     * @param index
     * @param size
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.tag.system.list.get")
    public BaseOutput getTagcountByParentGroupId(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotNull @QueryParam("tag_group_id") String tagGroupId, @QueryParam("index") Integer index,
            @QueryParam("size") Integer size) {
        //tagSystemListGetService.getTagcount(method, userToken, tagGroupId, index, size);
        return tagSystemListGetService.getMongoTagList(method, userToken, tagGroupId, index, size);
    }
    
    
    /**
     * 标签根节点列表获取
     *
	 * @param userToken
	 * @param ver
     * @return
     * @author wangweiqiang
     * @Date 2016-12-07
     */
	@GET
    @Path("/mkt.tag.root.node.list.get")
    public BaseOutput getRootNode(@NotEmpty @QueryParam("user_token") String userToken,
            @QueryParam("ver") String ver) {
        return systemTagService.getNativeList();
    }
	
    /**
     * 获取标签列表    
     *
	 * @param userToken
	 * @param ver
	 * @param tag_id
     * @return
     * @author wangweiqiang
     * @Date 2016-12-07
     */
    @GET
    @Path("/mkt.tag.tree.list.get")
    public BaseOutput getSystemtagList(@NotEmpty @QueryParam("user_token") String userToken,
                                       @QueryParam("ver") String ver,
                                       @NotEmpty @QueryParam("tag_id") String navigateIndex,
                                       @QueryParam("page_source_type") Integer pageSourceType) {
        return systemTagService.getSystemTagList(navigateIndex,pageSourceType);
    }
    
    /**
     * 获取标签值列表   
     * @param userToken
     * @param ver
     * @param tagId
     * @param index
     * @param size
     * @return
     * @author wangweiqiang
     * @Date 2016-12-07
     */
    @GET
    @Path("/mkt.tag.value.list.get")
    public BaseOutput getSystemtagList(@NotEmpty @QueryParam("user_token") String userToken,
            @QueryParam("ver") String ver,@NotEmpty @QueryParam("tag_id") String tagId,@NotNull 
    		@QueryParam("index") Integer index,@NotNull @QueryParam("size") Integer size) {
        return systemTagService.getSystemTagValueList(tagId, index, size);
    }

    /**
     * 功能描述：保存自定义分类子分类
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @POST
    @Path("/mkt.tag.custom.taxonomy.save")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput tagCustomTaxonomySave(@Valid TagCustomTaxonomySaveIn body,
            @Context SecurityContext securityContext) {
        return tagCustomTaxonomySaveService.tagCustomTaxonomySave(body, securityContext);
    }

    /**
     * 功能描述：获得自定义分类子分类
     * 
     * @param tagTreeId
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @GET
    @Path("/mkt.tag.custom.taxonomy.list.get")
    public BaseOutput tagCustomTaxonomyListGet(@NotEmpty @QueryParam("tag_tree_id") String tagTreeId,
                                               @QueryParam("page_source_type") Integer pageSourceType) {
        return tagCustomTaxonomyListGetService.tagCustomTaxonomyListGet(tagTreeId,pageSourceType);
    }

    /**
     * 功能描述：删除自定义分类（逻辑删除）
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @POST
    @Path("/mkt.tag.custom.taxonomy.del")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput tagCustomTaxonomyDel(@Valid TagCustomTaxonomyDelIn body,
            @Context SecurityContext securityContext) {
        return tagCustomTaxonomyDelService.tagCustomTaxonomyDel(body, securityContext);
    }

    /**
     * 功能描述：获取自定义标签分类全部一级分类
     * 
     * @param nolyShow
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @GET
    @Path("/mkt.tag.custom.taxonomy.root.list.get")
    public BaseOutput TagCustomTaxonomyRootListGetService(@QueryParam("noly_show") Boolean onlyShow) {
        return tagCustomTaxonomyRootListGetService.tagCustomTaxonomyRootListGet(onlyShow);
    }

    /**
     * 功能描述：设置系统标签一级分类优先显示接口
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @POST
    @Path("/mkt.tag.custom.taxonomy.show.set")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput tagCustomTaxonomyShowSet(@Valid TagCustomTaxonomyShowSetIn body,
            @Context SecurityContext securityContext) {
        return tagCustomTaxonomyShowSetService.tagCustomTaxonomyShowSet(body, securityContext);
    }
    
    
    /**
     * 功能描述：系统标签值修改
     * @param body
     * @param securityContext
     * @author shuiyangyang
     * @Date 2016.12.16
     * @return
     */
    @POST
    @Path("/mkt.system.tag.value.update")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput systemTagValueUpdate(@Valid TagValueUpdateIn tagValueUpdateIn,
            @Context SecurityContext securityContext) {
		return systemTagService.saveUpdateTagValue(tagValueUpdateIn);
    }
    
    /**
     * 功能描述：创建自定义分类跟分类
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.21
     * @author shuiyangyang
     */
    @POST
    @Path("/mkt.tag.custom.taxonomy.root.save")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput tagCustomTaxonomyRootSave(@Valid TagCustomTaxonomyRootSaveIn body,
            @Context SecurityContext securityContext) {
        return tagCustomTaxonomyRootSaveService.tagCustomTaxonomyRootSave(body, securityContext);
    }
}
