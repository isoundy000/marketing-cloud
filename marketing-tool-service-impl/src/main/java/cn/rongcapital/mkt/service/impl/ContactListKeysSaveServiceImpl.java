package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactListKeysSaveService;
import cn.rongcapital.mkt.service.ImportContactsDataToMDataService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ContactListKeyAttribute;
import cn.rongcapital.mkt.vo.in.SaveContactListKeysIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-8-19.
 */
@Service
public class ContactListKeysSaveServiceImpl implements ContactListKeysSaveService {

    @Autowired
    private ImportContactsDataToMDataService importContactsDataToMDataService;

    @Autowired
    private ContactTemplateDao contactTemplateDao;

    @Override
    public BaseOutput saveContactListKeys(SaveContactListKeysIn saveContactListKeysIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        Long contactId = saveContactListKeysIn.getContactId();
        String keyList = getContactTemplateKeyList(saveContactListKeysIn.getFieldNameList());

        ContactTemplate contactTemplate = new ContactTemplate();
        contactTemplate.setContactId(contactId);
        List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);
        if(CollectionUtils.isEmpty(contactTemplateList)){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
        }


        for(ContactTemplate upContactTemplate : contactTemplateList){
            upContactTemplate.setKeyList(keyList);
            upContactTemplate.setIsRememberImportKey(saveContactListKeysIn.getSaveFlag().byteValue());
            contactTemplateDao.updateById(upContactTemplate);
        }

        importContactsDataToMDataService.importContactsDataToMData(contactId);
        return baseOutput;
    }

    private String getContactTemplateKeyList(ArrayList<ContactListKeyAttribute> fieldNameList) {
        StringBuffer stringBuffer = new StringBuffer();
        for(ContactListKeyAttribute contactListKeyAttribute : fieldNameList){
            stringBuffer.append(contactListKeyAttribute.getFieldName() + ",");
        }
        return stringBuffer.substring(0,stringBuffer.length()-1).toString();
    }

}
