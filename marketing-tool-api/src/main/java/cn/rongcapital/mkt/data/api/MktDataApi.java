/*************************************************
 * @功能简述: API接口通用主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.data.api;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.ContactWay;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.in.*;
import cn.rongcapital.mkt.vo.out.DataGetFilterContactwayOut;
import cn.rongcapital.mkt.vo.out.DataGetFilterRecentTaskOut;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.vo.BaseOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktDataApi {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MainBasicInfoGetService mainBasicInfoGetService;

	@Autowired
	private MigrationFileGeneralInfoService migrationFileGeneralInfoService;

	@Autowired
	private MigrationFileTemplateService migrationFileTemplateService;

	@Autowired
	private MigrationFileUploadUrlService migrationFileUploadUrlService;

	@Autowired
	private DataGetQualityListService dataGetQualityListService;

	@Autowired
	private DataGetQualityCountService dataGetQualityCountService;

	@Autowired
	private DataGetUnqualifiedCountService dataGetUnqualifiedCountService;

	@Autowired
	private DataGetMainCountService dataGetMainCountService;

	@Autowired
	private DataGetMainListService dataGetMainListService;

	@Autowired
	private DataDeleteMainService dataDeleteMainService;

	@Autowired
	private DataGetViewListService dataGetViewListService;

	@Autowired
	private DataGetFilterContactwayService dataGetFilterContactwayService;

	@Autowired
	private DataGetFilterRecentTaskService dataGetFilterRecentTaskService;

	@Autowired
	private DataGetFilterAudiencesService dataGetFilterAudiencesService;

	@Autowired
	private DataUpateMainSegmenttagService dataUpateMainSegmenttagService;

	@Autowired
	private DataDownloadQualityLogService dataDownloadQualityLogService;

	@Autowired
	private DataDownloadMainListService dataDownloadMainListService;

	@Autowired
	private TagGetCustomService tagGetCustomService;

	@Autowired
	private UploadFileService uploadFileService;

	@Autowired
	private AudienceListService audienceListService;

	@Autowired
	private AudienceNameListService audienceNameListService;

	@Autowired
	private AudienceIdListService audienceIdListService;

	@Autowired
	private AudienceSearchService audienceSearchService;

	@Autowired
	private AudienceListDeleteService audienceListDeleteService;

	@Autowired
	private MainActionInfoGetService mainActionInfoGetService;

	@Autowired
	private DataMainRadarInfoGetService dataMainRadarInfoGetService;

	@Autowired
	private GetDataMainSearchService getDataMainSearchService;

	@Autowired
	private GetDataMainSearchByIdService getDataMainSearchByIdService;

	@Autowired
	private DataMainBasicInfoUpdateService dataMainBasicInfoUpdateService;

	@Autowired
	private FileTemplateDownloadService fileTemplateDownloadService;

	@Autowired
	private FileTagUpdateService fileTagUpdateService;

	@Autowired
	private DataDownloadQualityIllegalDataService dataDownloadQualityIllegalDataService;

	@Autowired
	private HomePageUserCountListService homePageUserCountListService;

	@Autowired
	private HomePageDataCountListService homePageDataCountListService;

	@Autowired
	private HomePageDataSourceListService homePageDataSourceListService;

	@Autowired
	private GetUserInfoService userInfoService;

	@Autowired
	private AudienceSearchDownloadService audienceSearchDownloadService;


	/**
	 * @功能简述: 获取某条主数据详细信息
	 * @param userToken
	 * @param contactId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.basicinfo.get")
	public BaseOutput getPartyBehaviorByCondition(@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("contact_id") Integer contactId, @NotNull @QueryParam("md_type") Integer dataType) {
		return mainBasicInfoGetService.getMainBasicInfo(contactId, dataType, userToken);
	}

	/**
	 * @功能简述: 获取文件接入的总览信息
	 * @param: String
	 *             userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.file.generalinfo.get")
	public Object getMigrationFileGeneralInfo(@NotEmpty @QueryParam("user_token") String userToken,
											  @NotEmpty @QueryParam("ver") String ver) throws Exception {
		return migrationFileGeneralInfoService.getMigrationFileGeneralInfo(null);
	}

	/**
	 * @功能简述: 获取文件模板下载列表
	 * @param: String
	 *             userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.file.template.list.get")
	public Object getMigrationFileTemplateList(@NotEmpty @QueryParam("user_token") String userToken,
											   @NotEmpty @QueryParam("ver") String ver) throws Exception {
		return migrationFileTemplateService.getMigrationFileTemplateList(null);
	}

	/**
	 * @功能简述: 获取文件上传url
	 * @param: String
	 *             userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.file.uploadurl.get")
	public Object getMigrationFileUploadUrl(@NotEmpty @QueryParam("user_token") String userToken,
											@NotEmpty @QueryParam("ver") String ver) throws Exception {
		return migrationFileUploadUrlService.getMigrationFileUploadUrl(null);
	}

	/**
	 * @功能简述: 获取数据质量列表
	 * @author nianjun
	 * @param: String
	 *             method, String userToken, String ver, Integer index, Integer
	 *             size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.quality.list.get")
	public Object getQualityList(@NotEmpty @QueryParam("method") String method,
								 @NotEmpty @QueryParam("user_token") String userToken, @QueryParam("index") Integer index,
								 @QueryParam("size") Integer size, @NotEmpty @QueryParam("ver") String ver) {
		return dataGetQualityListService.getQualityList(method, userToken, index, size, ver);
	}


	/**
	 * @功能简述 : 获取数据接入条数
	 * @param: String
	 *             method, String userToken, String ver
	 * @author nianjun
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.quality.count.get")
	public Object getQualityCount(@NotEmpty @QueryParam("method") String method,
								  @NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver) {

		return dataGetQualityCountService.getQualityCount(method, userToken, ver);
	}

	/**
	 * @功能简述 : 获取非法数据条数
	 * @param: String
	 *             method, String userToken, String ver, Long batchId
	 * @author nianjun
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.unqualified.count.get")
	public Object getUnqualifiedCount(@NotEmpty @QueryParam("method") String method,
									  @NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
									  @NotNull @QueryParam("batch_id") Long batchId) {

		return dataGetUnqualifiedCountService.getQualityCount(method, userToken, ver, batchId);
	}


	/**
	 * @功能简述 : 获取主数据条数
	 * @param: String
	 *             method, String userToken, String ver, Long batchId
	 * @author nianjun
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.count.get")
	public Object getUnqualifiedCount(@NotEmpty @QueryParam("method") String method,
									  @NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver) {

		return dataGetMainCountService.getMainCount(method, userToken, ver);
	}


	/**
	 * @功能简述 : 获取主数据列表
	 * @param: String
	 *             method, String userToken, String ver, Integer index, Integer
	 *             size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.list.get")
	public Object getDataMainList(@NotEmpty @QueryParam("method") String method,
								  @NotEmpty @QueryParam("user_token") String userToken, @NotNull @QueryParam("md_type") Integer dataType,
								  @QueryParam("index") Integer index, @QueryParam("size") Integer size,
								  @NotEmpty @QueryParam("ver") String ver) {
		return dataGetMainListService.getMainList(method, userToken, dataType, index, size, ver);
	}


	/**
	 * @功能简述 : 删除某条主数据
	 * @param: String
	 *             method, String userToken, String ver, dataId
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.delete")
	public Object deleteDataMain(@NotEmpty @QueryParam("method") String method,
								 @NotEmpty @QueryParam("user_token") String userToken, @NotNull @QueryParam("data_id") Integer dataId,
								 @NotEmpty @QueryParam("ver") String ver) {
		return dataDeleteMainService.deleteMain(method, userToken, ver, dataId);
	}



	/**
	 * @功能简述 : 查询自定义视图字段列表
	 * @param: String
	 *             method, String userToken, String ver, Integer mdType
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.view.list.get")
	public Object getViewList(@NotEmpty @QueryParam("method") String method,
							  @NotEmpty @QueryParam("user_token") String userToken, @NotNull @QueryParam("md_type") Integer mdType,
							  @NotEmpty @QueryParam("ver") String ver) {
		return dataGetViewListService.getViewList(method, userToken, ver, mdType);
	}

	/**
	 * @功能简述 : 查询联系方式
	 * @param: String
	 *             method, String userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.filter.contactway.get")
	public Object getFilterContactway(@NotEmpty @QueryParam("method") String method,
									  @NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver) {

		List<ContactWay> contactWayList = dataGetFilterContactwayService.getFilterContactway(method, userToken, ver);
		DataGetFilterContactwayOut result = new DataGetFilterContactwayOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		if (contactWayList != null && !contactWayList.isEmpty()) {
			for (ContactWay contactWay : contactWayList) {
				Map<String, Object> map = new HashMap<>();
				map.put("contact_id", contactWay.getId());
				map.put("contact_way", contactWay.getName());
				result.getData().add(map);
			}
		}

		result.setTotal(result.getData().size());
		return Response.ok().entity(result).build();
	}

	/**
	 * @功能简述 : 查询最近完成的数据接入任务
	 * @param: String
	 *             method, String userToken, String ver, String condition
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.filter.recenttask.get")
	public Object getFilterContactway(@NotEmpty @QueryParam("method") String method,
									  @NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
									  @NotEmpty @QueryParam("condition") String condition) {

		List<TaskRunLog> taskRunLogList = dataGetFilterRecentTaskService.getFilterRecentTask(method, userToken, ver,
				condition);
		DataGetFilterRecentTaskOut result = new DataGetFilterRecentTaskOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		if (taskRunLogList != null && !taskRunLogList.isEmpty()) {
			for (TaskRunLog taskRunLog : taskRunLogList) {
				Map<String, Object> map = new HashMap<>();
				map.put("task_id", taskRunLog.getId());
				map.put("filename", taskRunLog.getTaskName());
				result.getData().add(map);
			}
		}

		result.setTotal(result.getData().size());
		return Response.ok().entity(result).build();
	}

	/**
	 * @功能简述 : 根据快捷筛选查询某类型的主数据
	 * @param: String
	 *             method, String userToken, String ver, String mdType, List
	 *             taskIdList
	 * @return: Object
	 */
	@POST
	@Path("/mkt.data.filter.audiences.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object getFilterAudiences(@Valid DataGetFilterAudiencesIn dataGetFilterAudiencesIn) {
		return dataGetFilterAudiencesService.getFilterAudiences(dataGetFilterAudiencesIn.getMethod(),
				dataGetFilterAudiencesIn.getUserToken(), dataGetFilterAudiencesIn.getVer(),
				dataGetFilterAudiencesIn.getIndex(), dataGetFilterAudiencesIn.getSize(),
				dataGetFilterAudiencesIn.getMdType(), dataGetFilterAudiencesIn.getMdTypes(),
				dataGetFilterAudiencesIn.getContactIds(), dataGetFilterAudiencesIn.getCustomizeViews(),
				dataGetFilterAudiencesIn.getTimeCondition(),dataGetFilterAudiencesIn.getContactWayList());
	}

	/**
	 * @功能简述 : 根据联系人id更新联系人标签
	 */
	@POST
	@Path("/mkt.data.main.segmenttag.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object updateMainSegmenttag(@Valid DataUpdateMainSegmenttagIn dataUpdateMainSegmenttagIn) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		dataUpateMainSegmenttagService.updateMainSegmenttag(dataUpdateMainSegmenttagIn.getMethod(),
				dataUpdateMainSegmenttagIn.getUserToken(), dataUpdateMainSegmenttagIn.getVer(),
				dataUpdateMainSegmenttagIn.getTagName(), dataUpdateMainSegmenttagIn.getContactId());

		return result;
	}


	/**
	 * @功能简述 : 根据联系人id获取联系人标签
	 */
	@GET
	@Path("/mkt.data.main.segmenttag.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object getMainSegmenttagNames(@NotEmpty @QueryParam("user_token") String userToken,
										 @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("map_id") Integer map_id) {
		return dataUpateMainSegmenttagService.getMainSegmenttagNames(map_id);

	}

	@GET
	@Path("/mkt.tag.user.custom.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object getCustomTag(@NotEmpty @QueryParam("user_token") String userToken,
							   @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("contact_id") Integer contactId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		List<String> customTags = tagGetCustomService.getCustomizeTagByContactId(ver, contactId);

		result.getData().addAll(customTags);
		result.setTotal(result.getData().size());
		return result;
	}

	/**
	 * @功能描述:文件上传
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.service.file.upload")
	@Consumes("multipart/form-data")
	public Object fileUpload(@NotEmpty @QueryParam("file_unique") String fileUnique, MultipartFormDataInput input) {
		return uploadFileService.uploadFile(fileUnique, input);
	}

	/**
	 * @功能描述:文件上传
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.service.file.repiar.upload")
	@Consumes("multipart/form-data")
	public Object uploadRepiarFile(@NotEmpty @QueryParam("file_unique") String fileUnique,
								   MultipartFormDataInput input) {
		return uploadFileService.uploadRepairFile(fileUnique, input);
	}

	/**
	 * @功能简述: 数据分析，获取人群名称列表
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.dataanalysis.audname.list.get")
	public BaseOutput audienceNameList(@NotEmpty @QueryParam("user_token") String userToken) {
		return audienceNameListService.audienceNameList(userToken);
	}



	/**
	 * @功能简述: 数据分析，获取联系人ID列表
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.dataanalysis.audiences.get")
	public BaseOutput audienceIdList(@NotEmpty @QueryParam("user_token") String userToken,
									 @NotEmpty @QueryParam("audience_ids") String audience_ids,
									 @NotEmpty @QueryParam("audience_type") String audience_type) {
		return audienceIdListService.audienceIdList(userToken, audience_ids, audience_type);
	}



	/**
	 * @功能简述: 获取人群list列表
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.audience.list.get")
	public BaseOutput audienceList(@NotEmpty @QueryParam("user_token") String userToken,
								   @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
								   @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
		return audienceListService.audienceList(userToken, size, index);
	}


	/**
	 *
	 * @param userToken
	 * @return
	 */
	@GET
	@Path("/mkt.audience.count.get")
	public BaseOutput audienceCount(@NotEmpty @QueryParam("user_token") String userToken) {
		return audienceListService.audienceCount(userToken);
	}



	/**
	 * @功能简述: 在人群中查找
	 * @param: String
	 *             userToken
	 * @param: String
	 *             audience_id 人群ID
	 * @param: String
	 *             audience_name 人群名字
	 * @return: Object
	 */
	@GET
	@Path("/mkt.audience.search.get")
	public BaseOutput audienceByName(@NotEmpty @QueryParam("user_token") String userToken,
									 @QueryParam("audience_type") String audience_type, @QueryParam("audience_id") String audience_id,
									 @QueryParam("search_field") String audience_name,
									 @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
									 @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
		return audienceSearchService.audienceByName(userToken, audience_type, audience_id, audience_name, size, index);
	}


	/**
	 * @功能描述:删除人群list
	 * @Param: body
	 * @param securityContext
	 * @return: BaseOutput
	 */
	@POST
	@Path("/mkt.audience.list.delete")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput audienceListDel(@Valid AudienceListDeleteIn body, @Context SecurityContext securityContext) {
		return audienceListDeleteService.audienceListDel(body.getAudienceListId(), securityContext);
	}

	/**
	 * @功能简述: 获取某联系人行为信息
	 * @param userToken
	 * @param contactId
	 * @param behaviorType
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.actioninfo.get")
	public BaseOutput getPartyBehaviorByCondition(@NotEmpty @QueryParam("user_token") String userToken,
												  @NotEmpty @QueryParam("contact_id") String contactId,
												  @NotEmpty @QueryParam("behavior_type") String behaviorType) {
		return mainActionInfoGetService.getMainActionInfo(contactId, behaviorType);
	}



	/**
	 * @功能简述: 统计联系人行为数量
	 * @param userToken
	 * @param contactId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.action.count.get")
	public BaseOutput getPartyBehaviorCountById(@NotEmpty @QueryParam("user_token") String userToken,
												@NotEmpty @QueryParam("contact_id") String contactId) {
		return mainActionInfoGetService.getPartyBehaviorCountById(contactId);
	}

	/**
	 * @功能简述 : 下载非法数据
	 * @param: String
	 *             userToken, Integer batchId
	 * @return: Object
	 */
	@GET
	@Path("mkt.data.quality.illegaldata.download")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput downloadIllegalData(@NotEmpty @QueryParam("file_type") String fileType,
										  @NotNull @QueryParam("batch_id") Long batchId) {
		return dataDownloadQualityIllegalDataService.downloadIllegalData(batchId, fileType);
	}

	/**
	 * @功能简述: 获取某联系人雷达图数据
	 * @param userToken
	 * @param contactId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.radarinfo.get")
	public BaseOutput getRadarInfoByContactId(@NotEmpty @QueryParam("user_token") String userToken,
											  @NotEmpty @QueryParam("contact_id") String contactId) {
		return dataMainRadarInfoGetService.getRadarInfoByContactId(contactId);
	}


	/**
	 * @功能简述: 编辑某条主数据详细信息
	 * @param body
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.data.main.basicinfo.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput updateBaseInfoByContactId(@Valid DataMainBaseInfoUpdateIn body) {
		return dataMainBasicInfoUpdateService.updateBaseInfoByContactId(body);
	}

	/**
	 * @功能简述: 主界面上的搜索栏模糊查询数据
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.search.get")
	public BaseOutput getDataMainSearch(@NotEmpty @QueryParam("user_token") String userToken,
										@NotEmpty @QueryParam("name") String name, @NotEmpty @QueryParam("ver") String ver) {
		DataMainSearchIn dataMainSearchIn = new DataMainSearchIn();
		dataMainSearchIn.setName(name);
		return getDataMainSearchService.searchDataMain(dataMainSearchIn);
	}

	/**
	 * @功能简述: 根据类型和编号，搜索获取主数据(细分、活动和人群)
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.searchbyid.get")
	public BaseOutput getDataMainSearchById(@NotEmpty @QueryParam("user_token") String userToken,
											@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("type") Integer type,
											@NotNull @QueryParam("id") Integer id) {
		return getDataMainSearchByIdService.searchDataMainById(id, type);
	}

	/**
	 * @功能简述: 获取文件模板下载列表
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.file.template.download")
	public Object fileTemplateDownload(@NotEmpty @QueryParam("user_token") String userToken,
									   @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("template_id_list") String templateIdList) {
		return fileTemplateDownloadService.downloadFileTemplate(templateIdList);
	}

	/**
	 * @功能简述: 文件上传时为上传的人群打标签
	 * @param fileTagUpdateIn
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.data.inbound.file.tag.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput fileTagUpdate(FileTagUpdateIn fileTagUpdateIn) {
		return fileTagUpdateService.updateFileTag(fileTagUpdateIn);
	}

	/**
	 * @功能简述: 下载某条主数据详细信息
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.quality.log.download")
	public Object downloadQualityLog(@NotEmpty @QueryParam("user_token") String userToken,
									 @NotNull @QueryParam("import_data_id") Long importDataId) {
		return dataDownloadQualityLogService.downloadQualityLog(importDataId);
	}

	/**
	 * @功能简述: 下载某个主数据分类的数据
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.list.download")
	public Object downloadMainList(@NotEmpty @QueryParam("user_token") String userToken,
								   @NotNull @QueryParam("md_type") Integer dataType) {
		return dataDownloadMainListService.downloadMainList(userToken, dataType);
	}

	/**
	 * 统计出当前用户、细分、活动、标签、接入数据个数
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.datacount.list")
	public BaseOutput homePageDataCountList(@NotEmpty @QueryParam("user_token") String userToken,
											@NotEmpty @QueryParam("ver") String ver) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageDataCountListService.getDataCountList());

		return result;
	}

	/**
	 * 统计出用户的各类来源，如微信、CRM、POS(以数据文件填写的来源为准)
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.datasource.list")
	public BaseOutput homePageDataSourceList(@NotEmpty @QueryParam("user_token") String userToken,
											 @NotEmpty @QueryParam("ver") String ver) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageDataSourceListService.getHomePageDataSourceList());

		return result;
	}

	/**
	 * @Title: getUserInfo
	 * @Description: 通过userID查询用户信息
	 * @param: @param userToken
	 * @param: @param ver
	 * @param: @param userId
	 * @param: @return
	 * @return: BaseOutput
	 * @throws
	 */
	@GET
	@Path("/mkt.data.userinfo.get")
	public BaseOutput getUserInfo(@NotEmpty @QueryParam("user_token") String userToken,
								  @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("personnel_id") String userId) {
		logger.info(" into mkt.data.userinfo.get");
		return userInfoService.getUserInfo(userId);
	}

	/**
	 * 下载人群管理详情
	 * @param audience_id
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.audience.search.download")
	public BaseOutput searchData(@NotNull @QueryParam("audience_id") Integer audience_id) {

		return audienceSearchDownloadService.searchData(audience_id);
	}
}
