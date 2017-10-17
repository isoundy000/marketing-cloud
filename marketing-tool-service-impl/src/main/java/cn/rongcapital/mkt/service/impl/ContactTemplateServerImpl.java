/*************************************************
 * @功能简述: ContactTemplateServer实现类
 * @see: MktApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/8/12 -
*************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.common.util.QrCreater;
import cn.rongcapital.mkt.dao.ContactListDao;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactList;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactTemplateServer;
import cn.rongcapital.mkt.service.ImportContactsDataToMDataService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactTemplateIn;
import cn.rongcapital.mkt.vo.in.Field_list;

@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class ContactTemplateServerImpl implements ContactTemplateServer {

	private static final Integer NO_CHOICE_FOR_IMPORTED_DATA = 0;
	private static final Integer UN_IMPORTED_DATA = 1;
	private static final Integer IMPORTED_DATA = 2;
	private static final Integer NO_CHOICE_FOR_SHOWN = 0;
	private static final Integer SHOWN_NEW = 1;
	private static final Integer SHOWN_ORIGIN = 2;
	private static final Integer UN_REMEMBER_IMPORTLIST = 0;
	private static final Integer SHOWN_IN_FEEDBACK = 0;
	private static final Integer NO_SHOWN_IN_FEEDBACK = 1;

	private static final String EMPTY_KEY_LIST = "";

	@Autowired
	Environment env;
	
	@Autowired
	private ContactTemplateDao contactTemplateDao;

	@Autowired
	private ContactListDao contactListDao;

	@Autowired
	private ImportContactsDataToMDataService importContactsDataToMDataService;

	@Override
	public BaseOutput ContactListCreate(ContactTemplateIn ctIn) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Integer keyModifyStatus = ctIn.getKeyModifyStatus();
		Integer columnShownStatus = ctIn.getColumnShownStatus();

		// 计数器
		int chang_count = 0;
		// 新增
		if (ctIn.getContact_id() == null) {
			long cont_id = (int) (System.currentTimeMillis() / 1000);
			if (CollectionUtils.isNotEmpty(ctIn.getField_list())) {

				for (Field_list field_list : ctIn.getField_list()) {
					if (field_list.getSelected().equals("1")) {
						ContactTemplate param = new ContactTemplate();
						param.setContactName(ctIn.getContact_name());
						param.setContactDescript(ctIn.getContact_descript());
						param.setContactTitle(ctIn.getContact_title());

						param.setContactId(cont_id);
						param.setFieldName(field_list.getField_name());
						param.setFieldCode(field_list.getField_code());
						param.setFieldType(field_list.getField_type());
						param.setSelected(field_list.getSelected());
						param.setRequired(field_list.getRequired());
						param.setIschecked(field_list.getIschecked());
						param.setPageViews(0);
						param.setFieldIndex(field_list.getIndex());
						param.setIsShownInFeedback(SHOWN_IN_FEEDBACK.byteValue());
						param.setDelStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
						
						// 二维码链接
						String qr = env.getProperty("contact.pc.url") + "?contact_id=" + cont_id;
						// 生成短链
						String qrShort = GenerateUUid.generateShortUuid();
						
						param.setQrcodeUrl(qr);
						param.setQrcodeShorturl(qrShort);
						
						// 生成二维码
						boolean qrCreaterSuccess = QrCreater.generateQr( env.getProperty("contact.short.url") + qrShort, "/rc/data/downloads/contactlist/" + param.getContactId() + ".jpg");
						if(qrCreaterSuccess) {
							// 设置二维码名
							param.setQrcodePic(cont_id + ".jpg");
						}

						// 插入
						contactTemplateDao.insert(param);
						chang_count++;
					}
				}
				        // 影响行数
						result.setTotal(chang_count);
						Map<String, Object> map_list = new HashMap<String, Object>();
						map_list.put("contact_id", cont_id);
						map_list.put("createtime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						result.getData().add(map_list);

			} else {
				result.setTotal(0);
			}

		}// 修改 先删除再新增
		else{
				if (CollectionUtils.isNotEmpty(ctIn.getField_list())) {
					//1.先处理数据导入
					if(keyModifyStatus != null && keyModifyStatus != NO_CHOICE_FOR_IMPORTED_DATA){
						if(keyModifyStatus == UN_IMPORTED_DATA){
							ContactList contactList = new ContactList();
							contactList.setContactTemplId(ctIn.getContact_id().intValue());
							contactList.setStatus(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
							List<ContactList> contactLists = contactListDao.selectList(contactList);
							if(!CollectionUtils.isEmpty(contactLists)){
								for(ContactList validContactList : contactLists){
									validContactList.setStatus(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_INVALID));
									contactListDao.updateById(validContactList);
								}
							}
						}else if(keyModifyStatus == IMPORTED_DATA){
							//导入数据
							importContactsDataToMDataService.importContactsDataToMData(ctIn.getContact_id());
						}
						//更新相应模板表中的keyList和isRemember字段
						ContactTemplate contactTemplate = new ContactTemplate();
						contactTemplate.setContactId(ctIn.getContact_id());
						List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);
						if(!CollectionUtils.isEmpty(contactTemplateList)){
							for(ContactTemplate updateContactTemplate : contactTemplateList){
								updateContactTemplate.setKeyList(null);
								updateContactTemplate.setIsRememberImportKey(UN_REMEMBER_IMPORTLIST.byteValue());
								contactTemplateDao.updateKeyListById(updateContactTemplate);
							}
						}
					}

					//2.处理显示逻辑
					if(columnShownStatus != null && columnShownStatus != NO_CHOICE_FOR_SHOWN){
						if(columnShownStatus == SHOWN_NEW){
							ContactTemplate contactTemplate = new ContactTemplate();
							contactTemplate.setContactId(ctIn.getContact_id());
							List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);
							if(!CollectionUtils.isEmpty(contactTemplateList)){
								for(ContactTemplate updateContactTemplate : contactTemplateList){
									updateContactTemplate.setIsShownInFeedback(NO_SHOWN_IN_FEEDBACK.byteValue());
									contactTemplateDao.updateById(updateContactTemplate);
								}
							}
						}else if(columnShownStatus == SHOWN_ORIGIN){
							//不对数据库进行任何更新
						}
					}

					// 再新增
					ContactTemplate contactTemplate = new ContactTemplate();
					contactTemplate.setContactId(ctIn.getContact_id());
					contactTemplate.setPageSize(Integer.MAX_VALUE);
					List<ContactTemplate> oriContactTemplateList = contactTemplateDao.selectList(contactTemplate);
					if(!CollectionUtils.isEmpty(oriContactTemplateList)){
						for(ContactTemplate oriContactTemplate : oriContactTemplateList){
							oriContactTemplate.setDelStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
							contactTemplateDao.updateById(oriContactTemplate);
						}
					}
					for (Field_list field_list : ctIn.getField_list()) {
						if (field_list.getSelected().equals("1")) {
							ContactTemplate param = new ContactTemplate();
							param.setContactName(ctIn.getContact_name());
							param.setContactDescript(ctIn.getContact_descript());
							param.setContactTitle(ctIn.getContact_title());

							param.setContactId(ctIn.getContact_id());
							param.setFieldName(field_list.getField_name());
							param.setFieldCode(field_list.getField_code());
							param.setSelected(field_list.getSelected());
							param.setRequired(field_list.getRequired());
							param.setIschecked(field_list.getIschecked());
							param.setPageViews(0);
							param.setFieldIndex(field_list.getIndex());
							param.setUpdateTime(new Date());
							
							
							// 二维码链接
							String qr = env.getProperty("contact.pc.url") + "?contact_id=" + param.getContactId();
							// 生成短链
							String qrShort = GenerateUUid.generateShortUuid();
							
							
							param.setQrcodeUrl(qr);
							param.setQrcodeShorturl(qrShort);
							
							// 生成二维码
							boolean qrCreaterSuccess = QrCreater.generateQr( env.getProperty("contact.short.url") + qrShort, "/rc/data/downloads/contactlist/" + param.getContactId() + ".jpg");
							
							if(qrCreaterSuccess) {
								// 设置二维码名
								param.setQrcodePic(param.getContactId() + ".jpg");
							}
							

							if(isInOldTemplate(param,oriContactTemplateList)){
								param.setIsShownInFeedback(SHOWN_IN_FEEDBACK.byteValue());
								param.setDelStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
								contactTemplateDao.updateById(param);
							}else {
								param.setDelStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
								param.setIsShownInFeedback(SHOWN_IN_FEEDBACK.byteValue());
								contactTemplateDao.insert(param);
							}
						}
					}
					        // 影响行数
							result.setTotal(chang_count);
							Map<String, Object> map_list = new HashMap<String, Object>();
							map_list.put("contact_id", ctIn.getContact_id());
							map_list.put("updatetime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
							result.getData().add(map_list);
				}
			}

		return result;
	}

	private boolean isInOldTemplate(ContactTemplate param, List<ContactTemplate> oriContactTemplateList) {
		boolean flag = false;
		for(ContactTemplate contactTemplate : oriContactTemplateList){
			if(contactTemplate.getFieldName().equals(param.getFieldName())){
				param.setId(contactTemplate.getId());
				flag = true;
				break;
			}
		}
		return flag;
	}

	@Override
	public BaseOutput updateContextTempById(Long id) {
		ContactTemplate param = new ContactTemplate();
		param.setContactId(id);

		int update_count = contactTemplateDao.deleteByCId(param);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Map<String, Object> map_r = new HashMap<String, Object>();
		map_r.put("id", id);
		map_r.put("updatetime", DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));

		result.setTotal(update_count);
		result.getData().add(map_r);

		return result;
	}

}
