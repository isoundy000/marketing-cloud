/**
 * 接口 ：mkt.contact.list.key.list
 * 功能描述： 将联系人表单导入数据时选择的数据主键保存到数据库中
 * @author shuiyangyang
 * @Data 2016.08.18
 * 
 */
package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.KeyidMapBlockDao;
import cn.rongcapital.mkt.po.KeyidMapBlock;
import cn.rongcapital.mkt.service.ImportContactsDataToMDataService;
import cn.rongcapital.mkt.vo.out.GetContactListKeyListOut;
import cn.rongcapital.mkt.vo.out.ImportContactKeyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListKeyListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactListKeyListServiceImpl implements ContactListKeyListService{

	private static final Integer REQUIRED = 1;
	private static final Integer SELECTED = 1;
	private static final Integer UN_SELECTED = 0;
	private static final Integer SHOWN_KEYWINDOW_STATUS = 1;
	private static final Integer UN_SHOWN_KEYWINDOW_STATUS = 0;
	private static final Integer REMEMBERED_IMPORT_KEY = 1;

	@Autowired
	ContactTemplateDao contactTemplateDao;

	@Autowired
	KeyidMapBlockDao keyidMapBlockDao;

	@Autowired
	private ImportContactsDataToMDataService importContactsDataToMDataService;
	
	@Override
	public BaseOutput getContactListKeyList(Integer contactId) {
		GetContactListKeyListOut getContactListKeyListOut = new GetContactListKeyListOut(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO);
		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setContactId(Long.valueOf(contactId));
		contactTemplate.setDelStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		contactTemplate.setPageSize(Integer.MAX_VALUE);
		contactTemplate.setRequired(REQUIRED);
		List<ContactTemplate> requiredContactTemplateList = contactTemplateDao.selectList(contactTemplate);
		if(!CollectionUtils.isEmpty(requiredContactTemplateList)){
			if(requiredContactTemplateList.get(0).getIsRememberImportKey() != null && requiredContactTemplateList.get(0).getIsRememberImportKey() == REMEMBERED_IMPORT_KEY.byteValue()){
				//Todo:执行导入数据的方法
                importContactsDataToMDataService.importContactsDataToMData(Long.valueOf(contactId));
				getContactListKeyListOut.setShowKeylistWindowStatus(UN_SHOWN_KEYWINDOW_STATUS);
				return getContactListKeyListOut;
			}
			if(requiredContactTemplateList.size() == 1){
				ImportContactKeyInfo importContactKeyInfo = new ImportContactKeyInfo();
				importContactKeyInfo.setFieldName(requiredContactTemplateList.get(0).getFieldName());
				importContactKeyInfo.setFieldCode(requiredContactTemplateList.get(0).getFieldCode());
				importContactKeyInfo.setIsSelected(SELECTED);
				getContactListKeyListOut.getKeyInfoList().add(importContactKeyInfo);
				getContactListKeyListOut.setShowKeylistWindowStatus(SHOWN_KEYWINDOW_STATUS);
				return getContactListKeyListOut;
			}else{
				String lastKeyidList = requiredContactTemplateList.get(0).getKeyList();
				ArrayList<String> lastKeyids = null;
				if(lastKeyidList != null){
					lastKeyids = transferStringFormatToArrayListFormat(lastKeyidList);
				}

				for(ContactTemplate keyContactTemplate : requiredContactTemplateList){
					KeyidMapBlock keyidMapBlock = new KeyidMapBlock();
					keyidMapBlock.setField(keyContactTemplate.getFieldCode());
					List<KeyidMapBlock> keyidMapBlocks = keyidMapBlockDao.selectList(keyidMapBlock);
					if(CollectionUtils.isEmpty(keyidMapBlocks)) continue;
					ImportContactKeyInfo importContactKeyInfo = new ImportContactKeyInfo();
					importContactKeyInfo.setFieldName(keyContactTemplate.getFieldName());
					importContactKeyInfo.setFieldCode(keyContactTemplate.getFieldCode());
					if(lastKeyidList != null && lastKeyids.contains(keyContactTemplate.getFieldName())){
						importContactKeyInfo.setIsSelected(SELECTED);
					}else{
						importContactKeyInfo.setIsSelected(UN_SELECTED);
					}
					getContactListKeyListOut.getKeyInfoList().add(importContactKeyInfo);
				}
				getContactListKeyListOut.setShowKeylistWindowStatus(SHOWN_KEYWINDOW_STATUS);
			}
		}else{
			getContactListKeyListOut.setCode(ApiErrorCode.BIZ_ERROR.getCode());
			getContactListKeyListOut.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
			return getContactListKeyListOut;
		}

			// 设置keylist
//			List<ContactTemplate> contactTemplateLists = contactTemplateDao.selectIdByContactId(contactTemplate);
//			if(!contactTemplateLists.isEmpty()) {
//				for(ContactTemplate contactTemplateList : contactTemplateLists){
//					contactTemplateList.setKeyList(new JSONArray(keyLists).toString());
//					contactTemplateList.setIsRememberImportKey((byte)1);
//					contactTemplateDao.updateById1(contactTemplateList);// 这个是用自己写的sql
//				}
//			}
		
		return getContactListKeyListOut;
	}

	private ArrayList<String> transferStringFormatToArrayListFormat(String keyList) {
		ArrayList<String> keys = new ArrayList<String>();
		if(keyList.contains(",")){
			String[] keyArray = keyList.split(",");
			for(String key : keyArray){
				keys.add(key);
			}
		}else {
			keys.add(keyList);
		}
		return keys;
	}
}
