/*************************************************
 * @功能简述: API接口通用主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.contact.api;

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

import cn.rongcapital.mkt.service.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactListTagIn;
import cn.rongcapital.mkt.vo.in.ContactStatusUpdateIn;
import cn.rongcapital.mkt.vo.in.ContactTempDIn;
import cn.rongcapital.mkt.vo.in.ContactTemplateIn;
import cn.rongcapital.mkt.vo.in.ContactsCommitDelIn;
import cn.rongcapital.mkt.vo.in.ContactsCommitSaveIn;
import cn.rongcapital.mkt.vo.in.ImportContactsDataIn;
import cn.rongcapital.mkt.vo.in.SaveContactListKeysIn;
import cn.rongcapital.mkt.vo.out.ContactsCommitCountListOutput;
import cn.rongcapital.mkt.vo.out.GetContactKeyListOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktContactApi {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ContactTemplateServer contactTemplateServer;

	@Autowired
	private ContactTemplateService contactTemplateService;

	@Autowired
	private ContactKeyListGetService contactKeyListGetService;

	@Autowired
	private ContactListUsedService contactListUsedService;

	@Autowired
	private ContactListTagGetService contactListTagGetService;

	@Autowired
	private ContactListPvService contactListPvService;

	@Autowired
	private ContactListQrcodeDownloadService contactListQrcodeDownloadService;

	@Autowired
	private ContactListKeyListService contactListKeyListService;

	@Autowired
	private ContacsCommitSaveService contactsCommitSaveService;

	@Autowired
	private ContactListTagService contactListTagService;

	@Autowired
	private ContactListKeysSaveService contactListKeysSaveService;

	@Autowired
	private ImportContactsDataToMDataService importContactsDataToMDataService;

	@Autowired
	private ContactsCommitCountGetService contactsCommitCountGetService;

	@Autowired
	private ContactListInfoGetService contactListInfoGetService;
	
	@Autowired
	private ContactListGetByStatusService contactListGetByStatusService;

	@Autowired
	private ContactImportkeyListGetService contactImportkeyListGetService;
	
	@Autowired
	private ContactsLongurlGetService contactsLongurlGetService;

	@Autowired
	private ContactsCommitDatapartyDownloadService contactsCommitDatapartyDownloadService;
	
	/***
	 * 新建联系人表单
	 * 
	 * @param body
	 * @param securityContext
	 * @return baseOutput
	 */
	@POST
	@Path("/mkt.contact.list.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput ContactListCreate(@Valid ContactTemplateIn body, @Context SecurityContext securityContext) {
		return contactTemplateServer.ContactListCreate(body);
	}

	/**
	 * 获取创建联系人表单界面中，右侧的显示列表
	 * 
	 * @param userToken
	 * @param var
	 * @param contactId
	 * @return
	 */
	@GET
	@Path("mkt.contact.keylist.get")
	public GetContactKeyListOutput getContactKeyList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @QueryParam("contact_id") Long contactId) {
		return contactKeyListGetService.getContactKeyList(contactId);
	}

	/**
	 * 启用联系人表单
	 *
	 * @param
	 * @param ver
	 * @author chengjincheng
	 */
	@POST
	@Path("/mkt.contact.list.used")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput contactStatusUpdate(@Valid ContactStatusUpdateIn body) {

		return contactListUsedService.contactStatusUpdate(body);
	}

	/**
	 * 联系人表单打标签
	 *
	 * @param
	 * @param ver
	 * @author chengjincheng
	 */
	@POST
	@Path("/mkt.contact.list.tag")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput contactListTag(@Valid ContactListTagIn body) {

		return contactListTagService.contactListTag(body);
	}

	/**
	 * 根据表单编号查询出表单数据(表单预览、表单编辑).
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.contact.list.tag.get")
	public BaseOutput getContactListTag(@NotEmpty @QueryParam("user_token") String user_token,
			@NotNull @QueryParam("contact_id") Integer contact_id) {
		return contactListTagGetService.getContactListTag(contact_id);
	}

	/**
	 * 复制联系人表单
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@POST
	@Path("/mkt.contact.list.duplicate")
	public BaseOutput copyContact(@Valid ContactTempDIn body, @Context SecurityContext securityContext) {
		return contactTemplateService.copyContactTemplate(body, securityContext);
	}

	/**
	 * 删除联系人表单
	 *
	 * @param userToken
	 * @param ver
	 * @author yyl
	 */
	@POST
	@Path("/mkt.contact.list.del")
	public BaseOutput updateContextTempById(@Valid ContactTempDIn body) {
		return contactTemplateServer.updateContextTempById(body.getContact_id());
	}

	/**
	 * 统计联系人表单浏览次数
	 * 
	 * @param userToken
	 * @param var
	 * @param contactId
	 * @return
	 */
	@GET
	@Path("mkt.contact.list.pv")
	public BaseOutput countPageViews(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String var, @NotEmpty @QueryParam("contact_id") String contactId) {
		return contactListPvService.countPageViews(contactId);
	}

	/**
	 * 下载联系人表单的二维码图片
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.contact.list.qrcode.download")
	public BaseOutput getContactListQrcode(@NotEmpty @QueryParam("user_token") String user_token,
			@NotNull @QueryParam("contact_id") Integer contact_id) {
		return contactListQrcodeDownloadService.getContactListQrcode(contact_id);
	}

	/**
	 * 将联系人表单导入数据时选择的数据主键保存到数据库中
	 * 
	 * @param userToken
	 * @param ver
	 * @param contactId
	 * @return
	 * @author shuiyangyang
	 */
	@GET
	@Path("mkt.contact.list.key.list")
	public BaseOutput getContactListKeyList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("contact_id") Integer contactId) {

		return contactListKeyListService.getContactListKeyList(contactId);
	}

	/**
	 * 存储联系人表单所选择的主键
	 * 
	 * @param
	 * @param saveContactListKeysIn
	 * @author baiyunfeng
	 */
	@POST
	@Path("/mkt.contact.list.keys.save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput saveContactListKeys(@Valid SaveContactListKeysIn saveContactListKeysIn) {
		return contactListKeysSaveService.saveContactListKeys(saveContactListKeysIn);
	}

	/**
	 * 根据表单编号查询出表单数据(表单预览、表单编辑).
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.contact.list.info.get")
	public BaseOutput getContactListInfo(@NotEmpty @QueryParam("user_token") String user_token,
			@NotNull @QueryParam("contact_id") String contact_id) {
		return contactListInfoGetService.getContactListInfo(contact_id);
	}

	/**
	 * 保存用户反馈数据
	 *
	 * @param
	 * @param ver
	 * @author chengjincheng
	 */
	@POST
	@Path("/mkt.contacts.commit.save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput contactsCommitSave(@Valid ContactsCommitSaveIn body) {

		return contactsCommitSaveService.contactsCommitSave(body);
	}

	/**
	 * 查询用户反馈数据
	 *
	 * @param
	 * @param ver
	 * @author chengjincheng
	 */
	@GET
	@Path("/mkt.contacts.commit.get")
	public BaseOutput contactsCommitGet(@NotNull @QueryParam("contact_id") Integer contact_id,
			@NotNull @QueryParam("commit_time") Integer commit_time,
			 @DefaultValue("1") @Min(1) @QueryParam("index") int index,
			 @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size) {
		return contactsCommitSaveService.contactsCommitGet(contact_id, commit_time, index, size);
	}

	/**
	 * 统计用户反馈数据
	 * 
	 * @param userToken
	 * @param ver
	 * @param contactId
	 * @return
	 */
	@GET
	@Path("mkt.contacts.commit.count")
	public ContactsCommitCountListOutput getContactsCommitCount(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("contact_id") Long contactId) {
		return contactsCommitCountGetService.getContactsCommitCount(contactId);
	}

	/**
	 * 将联系人表单的数据导入主数据
	 * 
	 * @param
	 * @param importContactsDataIn
	 * @author baiyunfeng
	 */
//	@POST
//	@Path("/mkt.contacts.mdata.import")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	public BaseOutput importContactsDataToMData(@Valid ImportContactsDataIn importContactsDataIn) {
//		return importContactsDataToMDataService.importContactsDataToMData(importContactsDataIn);
//	}

	/**
	 * 保存用户反馈数据
	 *
	 * @param
	 * @param ver
	 * @author chengjincheng
	 */
	@POST
	@Path("/mkt.contacts.commit.del")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput contactsCommitDel(@Valid ContactsCommitDelIn body) {

		return contactsCommitSaveService.contactsCommitDel(body);
	}

	/**
	 * 下载用户反馈详情
	 *
	 * @param
	 * @param ver
	 * @author chengjincheng
	 */
	@GET
	@Path("/mkt.contacts.commit.download")
	public BaseOutput contactsCommitDownload(@NotNull @QueryParam("contact_id") Integer contact_id,
			@NotNull @QueryParam("commit_time") Integer commit_time) {
		return contactsCommitSaveService.contactsCommitDownload(contact_id, commit_time);
	}
	
	/**
	 * 下载反馈详情生成主数据
	 *
	 * @param
	 * @param ver
	 * @author lijinkai
	 */
	@GET
	@Path("/mkt.contacts.commit.dataparty.download")
	public BaseOutput contactsCommitDatapartyDownload(@NotNull @QueryParam("contact_id") Integer contact_id) {
		return contactsCommitDatapartyDownloadService.contactsCommitDatapartyDownload(contact_id);
	}
	
	/**
	 * 根据状态查询联系人表单
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.contact.list.get")
	public BaseOutput getContactList(@NotNull @QueryParam("contact_status") Integer contact_status,
			 @QueryParam("contact_id") String contact_id, @QueryParam("contact_name") String contact_name,
			 @DefaultValue("1") @Min(1) @QueryParam("index") int index,
			 @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size) {
		return contactListGetByStatusService.getContactList(contact_status, contact_id, contact_name, index, size);
	}

	/**
	 * 获取导入数据的主键
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.contact.importkeylist.get")
	public BaseOutput getContactListInfo(@NotEmpty @QueryParam("user_token") String user_token,
										 @NotEmpty @QueryParam("ver") String ver,
										 @NotNull @QueryParam("contact_id") Long contact_id) {
		return contactImportkeyListGetService.getContactImportkeyList(contact_id);
	}
	
	/*
	 * 获取联系人表单对应长链接URL
	 * @author shuiyangyang
	 * @Date 2016.08.31
	 */
	@GET
	@Path("mkt.contacts.longurl.get")
	public BaseOutput getLongurl(
			@NotEmpty @QueryParam("short_url") String shortUrl,
			@QueryParam("device") String device) {
		return contactsLongurlGetService.getLongurl(shortUrl, device);
	}
}
