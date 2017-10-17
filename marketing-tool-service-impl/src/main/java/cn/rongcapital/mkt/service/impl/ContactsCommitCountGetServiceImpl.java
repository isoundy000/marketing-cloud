package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactListDao;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.po.ContactList;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.ContactsCommitCountGetService;
import cn.rongcapital.mkt.vo.out.ContactsCommitCountListOutput;
import cn.rongcapital.mkt.vo.out.ContactsCommitCountOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Yunfeng on 2016-8-16.
 */
@Service
public class ContactsCommitCountGetServiceImpl implements ContactsCommitCountGetService{

    @Autowired
    private ContactTemplateDao contactTemplateDao;

    @Autowired
    private ContactListDao contactListDao;

    @Autowired
    private DataPopulationDao dataPopulationDao;

    @Override
    public ContactsCommitCountListOutput getContactsCommitCount(Long contactId) {
        ContactsCommitCountListOutput contactsCommitCountListOutput = new ContactsCommitCountListOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO);
        ContactsCommitCountOutput contactsCommitCountOutput = new ContactsCommitCountOutput();

        //1.获取该表单的浏览次数
        ContactTemplate contactTemplate = new ContactTemplate();
        contactTemplate.setContactId(contactId);
        List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);
        if(!CollectionUtils.isEmpty(contactTemplateList)){
            contactTemplate = contactTemplateList.get(0);
            if(contactTemplate.getPageViews() != null){
                contactsCommitCountOutput.setPageViews(contactTemplate.getPageViews());
            }else{
                contactsCommitCountOutput.setPageViews(0);
            }
        }else{
            contactsCommitCountOutput.setPageViews(0);
        }

        ContactList contactList = new ContactList();
        contactList.setContactTemplId(contactId.intValue());
        //2.获取总的提交数据
        Integer totalCount = contactListDao.selectListCount(contactList);
        contactsCommitCountOutput.setCommitCount(totalCount);

        //3.获取今日提交数据
        Date startTime = getStartTime();
        Date endTime = getEndTime();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("startTime",startTime);
        paramMap.put("endTime",endTime);
        paramMap.put("contactTemplId", contactId);
        Integer todayCount = contactListDao.selectTodayCommitCount(paramMap);
        contactsCommitCountOutput.setTodayCount(todayCount);
        //4.获取产生的主数据条数
        contactList = new ContactList();
        contactList.setStatus(2);
        contactList.setContactTemplId(contactId.intValue());
        List<Integer> distinctKeyidList = contactListDao.selectDistinctKeyidList(contactList);
        if(distinctKeyidList.contains(null)){
            distinctKeyidList.remove(null);
        }
        if(distinctKeyidList.size() > 0){
            Integer mainDataCount = dataPopulationDao.selectCountFromContactList(distinctKeyidList);
            contactsCommitCountOutput.setMdCount(mainDataCount);
        }else{
            contactsCommitCountOutput.setMdCount(ApiConstant.INT_ZERO);
        }

        //5.获取未导入的主数据条数
        contactList = new ContactList();
        contactList.setStatus(0);
        contactList.setContactTemplId(contactId.intValue());
        Integer nonMdCount = contactListDao.selectListCount(contactList);
        contactsCommitCountOutput.setNonmdCount(nonMdCount);

        contactsCommitCountListOutput.getData().add(contactsCommitCountOutput);
        return contactsCommitCountListOutput;
    }

    private Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return new Date(todayStart.getTime().getTime());
    }

    private Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return new Date(todayEnd.getTime().getTime());
    }
}
