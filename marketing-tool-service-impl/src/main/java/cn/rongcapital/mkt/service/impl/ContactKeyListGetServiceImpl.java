package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.dao.DefaultContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.po.DefaultContactTemplate;
import cn.rongcapital.mkt.service.ContactKeyListGetService;
import cn.rongcapital.mkt.vo.out.ContactKeyOutput;
import cn.rongcapital.mkt.vo.out.GetContactKeyListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Yunfeng on 2016-8-15.2
 */
@Service
public class ContactKeyListGetServiceImpl implements ContactKeyListGetService{

    //Todo: 1.这块需要分两种情况,contactId为null则代表为创建接口
    //Todo: 2.contactId不为null则代表编辑接口。
    private static final Integer PAGE_SIZE = 100;
    private static final Integer NON_SELECTED_VALUE = 0;
    private static final Integer MODIFY_DEFAULT_INDEX = 0;

    @Autowired
    private DefaultContactTemplateDao defaultContactTemplateDao;

    @Autowired
    private ContactTemplateDao contactTemplateDao;

    @Override
    public GetContactKeyListOutput getContactKeyList(Long contactId) {
        GetContactKeyListOutput getContactKeyListOutput = new GetContactKeyListOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO);
        DefaultContactTemplate defaultContactTemplate = new DefaultContactTemplate();
        defaultContactTemplate.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        defaultContactTemplate.setPageSize(PAGE_SIZE);
        List<DefaultContactTemplate> defaultContactTemplateList = defaultContactTemplateDao.selectList(defaultContactTemplate);
        for(DefaultContactTemplate tmpDefaultContactTemplate : defaultContactTemplateList){
            tmpDefaultContactTemplate.setFixedShownSeqInRight(tmpDefaultContactTemplate.getDefaultShownSeq());
        }

        if(contactId != null){
            //将默认模板和现在数据库中已有的模板做对比返回给前端
            ContactTemplate contactTemplate = new ContactTemplate();
            contactTemplate.setContactId(contactId);
            contactTemplate.setDelStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            contactTemplate.setPageSize(Integer.MAX_VALUE);
            List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);

            //获取默认模板
            if(!CollectionUtils.isEmpty(contactTemplateList)){
                getContactKeyListOutput.setKeyList(contactTemplateList.get(0).getKeyList());
                getContactKeyListOutput.setContactName(contactTemplateList.get(0).getContactName());
                getContactKeyListOutput.setContactTitle(contactTemplateList.get(0).getContactTitle());
                getContactKeyListOutput.setContactDescription(contactTemplateList.get(0).getContactDescript());
                getContactKeyListOutput.setStatus(new Integer(contactTemplateList.get(0).getStatus()));
                for(DefaultContactTemplate defaultContactTemplate1 : defaultContactTemplateList){
                    defaultContactTemplate1.setIsSelected(NON_SELECTED_VALUE.byteValue());
                    defaultContactTemplate1.setDefaultShownSeq(MODIFY_DEFAULT_INDEX);
                }
                for(DefaultContactTemplate defaultContactTemplate1 : defaultContactTemplateList){
                    for(ContactTemplate contactTemplate1 : contactTemplateList){
                        if(defaultContactTemplate1.getFieldCode().equals(contactTemplate1.getFieldCode())){
                            defaultContactTemplate1.setIsSelected(Integer.valueOf(contactTemplate1.getSelected()).byteValue());
                            defaultContactTemplate1.setIsChecked(contactTemplate1.getIschecked().byteValue());
                            defaultContactTemplate1.setIsRequired(contactTemplate1.getRequired().byteValue());
                            defaultContactTemplate1.setDefaultShownSeq(contactTemplate1.getFieldIndex());
                            break;
                        }
                    }
                }
            }
        }

        //将默认模板返回给前端
        for (DefaultContactTemplate defaultTemplate : defaultContactTemplateList){
            ContactKeyOutput contactKeyOutput = new ContactKeyOutput();
            contactKeyOutput.setFieldName(defaultTemplate.getFieldName());
            contactKeyOutput.setFieldCode(defaultTemplate.getFieldCode());
            contactKeyOutput.setIsChecked(Integer.valueOf(defaultTemplate.getIsChecked()));
            contactKeyOutput.setSelected(Integer.valueOf(defaultTemplate.getIsSelected()));
            contactKeyOutput.setRequired(Integer.valueOf(defaultTemplate.getIsRequired()));
            contactKeyOutput.setIndex(defaultTemplate.getDefaultShownSeq());
            contactKeyOutput.setFieldType(defaultTemplate.getFieldType());
            contactKeyOutput.setFixedIndex(defaultTemplate.getFixedShownSeqInRight());
            getContactKeyListOutput.getDataKeyList().add(contactKeyOutput);
        }
        getContactKeyListOutput.setTotal(getContactKeyListOutput.getDataKeyList().size());
        return getContactKeyListOutput;
    }

}
