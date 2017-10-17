package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactImportkeyListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-8-29.
 */
@Service
public class ContactImportkeyListGetServiceImpl implements ContactImportkeyListGetService {

    @Autowired
    private ContactTemplateDao contactTemplateDao;

    @Override
    public BaseOutput getContactImportkeyList(Long contactId) {

        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);

        ContactTemplate contactTemplate = new ContactTemplate();
        contactTemplate.setContactId(contactId);
        contactTemplate.setDelStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);
        if(!CollectionUtils.isEmpty(contactTemplateList)){
            contactTemplate = contactTemplateList.get(0);
            String keyList = contactTemplate.getKeyList();
            ArrayList<String> keyListInArray = transferStringFormatToArrayListFormat(keyList);
            for(String key : keyListInArray){
                for(ContactTemplate templ : contactTemplateList){
                    if(key.equals(templ.getFieldName())){
                        Map<String,String> map = new HashMap<String,String>();
                        map.put("field_name",templ.getFieldName());
                        map.put("field_code",templ.getFieldCode());
                        baseOutput.getData().add(map);
                    }
                }
            }
        }

        baseOutput.setTotal(baseOutput.getData().size());
        baseOutput.setTotalCount(baseOutput.getData().size());
        return baseOutput;
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
