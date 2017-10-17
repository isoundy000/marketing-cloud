package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.service.ImportContactsDataToMDataService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ImportContactsDataIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-8-22.
 */
@Service
public class ImportContactsDataToMDataServiceImpl implements ImportContactsDataToMDataService{

    //Todo:不通过定时任务导入主数据，通过现场计算将数据导入主数据

    private static final Integer NOT_UPDATE_BITMAP_MARK = 0;
    private static final Integer UPDATED_BITMAP_MAP = 1;
    private static final Integer SYNCED_TO_DATA_PARTY = 2;
    private static final Integer TEMPLATE_PAGE_SIZE = 50;
    private static final Integer TEMPLATE_LIST_DATA_PAGE_SIZE = 500;
    private static final Integer DATA_PARTY_VALID_VALUE = 0;

    private static final String DATA_SOURCE = "联系人表单";

    @Autowired
    private ContactTemplateDao contactTemplateDao;

    @Autowired
    private ContactListDao contactListDao;

    @Autowired
    private KeyidMapBlockDao keyidMapBlockDao;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private DataPopulationDao dataPopulationDao;

    @Override
    public BaseOutput importContactsDataToMData(Long contactId) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        if(contactId == null){
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
            return baseOutput;
        }

        //Todo:1先从Template里选出keyList并且转化为列名形式
        ContactTemplate contactTemplate = new ContactTemplate();
        contactTemplate.setContactId(contactId);
        contactTemplate.setPageSize(TEMPLATE_PAGE_SIZE);
        List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);
        if(!CollectionUtils.isEmpty(contactTemplateList)){
            contactTemplate = contactTemplateList.get(0);
            String keyList = contactTemplate.getKeyList();
            ArrayList<String> contactTemplateKeys = transferStringFormatToArrayListFormat(keyList);
            ArrayList<String> contactTemplateKeyColumns = contactTemplateDao.selectFieldCodeListByFieldNameList(contactTemplateKeys);
            //Todo:2然后从keyblock表中构造出bitmap
            List<KeyidMapBlock> keyidMapBlockList = keyidMapBlockDao.selectListByCodeList(contactTemplateKeyColumns);
            ArrayList<String> templateKeyColumnList = getKeyListColumn(keyidMapBlockList);
            String bitmap = calculateBitmapByKeyidMapBlockList(keyidMapBlockList);
            if(bitmap == null || templateKeyColumnList == null){
                baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
                baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
                return baseOutput;
            }
            //Todo:3更新这批数据的bitmap以及相应的状态
            ContactList contactList = new ContactList();
            contactList.setPageSize(Integer.MAX_VALUE);
            contactList.setStatus(NOT_UPDATE_BITMAP_MARK);
            contactList.setContactTemplId(contactId.intValue());
            List<ContactList> contactListList = contactListDao.selectList(contactList);
            if(!CollectionUtils.isEmpty(contactListList)){
                for(ContactList upContactList : contactListList){
                    //Todo:4将数据导入主数据，并且获得主数据的id，然后设置到contactList中
//                    Integer keyId = syncContactDataToMData(upContactList,templateKeyColumnList,bitmap);
                    Integer keyId = syncContactDataToPopulationData(upContactList,templateKeyColumnList,bitmap);
                    upContactList.setKeyid(keyId);
                    upContactList.setStatus(SYNCED_TO_DATA_PARTY);
                    upContactList.setBitmap(bitmap);
                    contactListDao.updateById(upContactList);
                }
            }
        }else{
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
            return baseOutput;
        }
        return baseOutput;
    }

    private Integer syncContactDataToPopulationData(ContactList upContactList, ArrayList<String> templateKeyColumnList, String bitmap) {
        Integer keyId = null;
        DataPopulation dataPopulation = new DataPopulation();
        dataPopulation.setBitmap(bitmap);
        dataPopulation.setMobile(upContactList.getMobile());
        dataPopulation.setName(upContactList.getName());
        dataPopulation.setGender(upContactList.getGender());
        dataPopulation.setBirthday(upContactList.getBirthday());
        dataPopulation.setProvice(upContactList.getProvice());
        dataPopulation.setCity(upContactList.getCity());
        dataPopulation.setMonthlyIncome(upContactList.getMonthlyIncome());
        dataPopulation.setMonthlyConsume(upContactList.getMonthlyConsume());
        dataPopulation.setMaritalStatus(upContactList.getMaritalStatus());
        dataPopulation.setEducation(upContactList.getEducation());
        dataPopulation.setEmployment(upContactList.getEmployment());
        dataPopulation.setNationality(upContactList.getNationality());
        dataPopulation.setBloodType(upContactList.getBloodType());
        dataPopulation.setCitizenship(upContactList.getCitizenship());
        dataPopulation.setIq(upContactList.getIq());
        dataPopulation.setIdentifyNo(upContactList.getIdentifyNo());
        dataPopulation.setDrivingLicense(upContactList.getDrivingLicense());
        dataPopulation.setEmail(upContactList.getEmail());
        dataPopulation.setQq(upContactList.getQq());
        dataPopulation.setAcctType(upContactList.getAcctType());
        dataPopulation.setAcctNo(upContactList.getAcctNo());
        dataPopulation.setIdfa(upContactList.getIdfa());
        dataPopulation.setImei(upContactList.getImei());
        dataPopulation.setUdid(upContactList.getUdid());
        dataPopulation.setPhoneMac(upContactList.getPhoneMac());
        dataPopulation.setSource(DATA_SOURCE);
        dataPopulation.setWxmpId(upContactList.getWxmpId());
        dataPopulation.setWxCode(upContactList.getWxCode());
        dataPopulation.setNickname(upContactList.getNickname());
        dataPopulation.setHeadImgUrl(upContactList.getHeadImgUrl());
        dataPopulation.setSubscribeTime(upContactList.getSubscribeTime());
        dataPopulation.setLanguage(upContactList.getLanguage());
        dataPopulation.setUnionid(upContactList.getUnionid());
        dataPopulation.setRemark(upContactList.getRemark());
        dataPopulationDao.insert(dataPopulation);
        keyId = dataPopulation.getId();
        return keyId;
    }

    private Integer syncContactDataToMData(ContactList upContactList, ArrayList<String> templateKeyColumnList, String bitmap) {
        Integer keyId = null;
        //Todo:1先看主数据里面有没有这条数据
        DataParty dataParty = new DataParty();
        copyKeyListFromUpContactListToDataParty(upContactList,dataParty,templateKeyColumnList);
        dataParty.setBitmap(bitmap);
        List<DataParty> dataPartyList = dataPartyDao.selectList(dataParty);
        if(!CollectionUtils.isEmpty(dataPartyList)){
            //Todo:2如果有，获取他的id，并且将Id返回
            keyId = dataPartyList.get(0).getId();
        }else{
            //Todo:3如果没有，将本条数据插入主数据，然后获取Id返回
            dataParty.setMobile(upContactList.getMobile());
            dataParty.setName(upContactList.getName());
            dataParty.setGender(upContactList.getGender());
            dataParty.setBirthday(upContactList.getBirthday());
            dataParty.setCitizenship(upContactList.getCitizenship());
            dataParty.setProvice(upContactList.getProvice());
            dataParty.setCity(upContactList.getCity());
            dataParty.setJob(upContactList.getJob());
            dataParty.setMonthlyIncome(upContactList.getMonthlyIncome());
            dataParty.setSource(upContactList.getSource());
            dataParty.setMonthlyConsume(upContactList.getMonthlyConsume());
            dataParty.setStatus(DATA_PARTY_VALID_VALUE.byteValue());
            dataPartyDao.insert(dataParty);
            keyId = dataParty.getId();
        }

        return keyId;
    }

    private void copyKeyListFromUpContactListToDataParty(ContactList upContactList, DataParty dataParty, ArrayList<String> templateKeyColumnList) {
        for(String keyidColumn : templateKeyColumnList){
            try {
                Field originalField = upContactList.getClass().getDeclaredField(toCamelUpperCode(keyidColumn));
                originalField.setAccessible(true);
                Object obj = originalField.get(upContactList);
                Field dataField = dataParty.getClass().getDeclaredField(toCamelUpperCode(keyidColumn));
                dataField.setAccessible(true);
                dataField.set(dataParty,obj);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private String toCamelUpperCode(String field) {
        if(!field.contains("_")) return field;
        String[] splitWord = field.split("_");
        StringBuffer resultBuffer = new StringBuffer();
        int index = 0;
        for(String word : splitWord){
            if(index == 0){
                resultBuffer.append(word);
                index++;
                continue;
            }
            word = word.replace(word.charAt(0), (char) (word.charAt(0) + ('A'-'a')));
            resultBuffer.append(word);
        }
        return resultBuffer.toString();
    }

    private ArrayList<String> getKeyListColumn(List<KeyidMapBlock> keyidMapBlockList) {
        if(CollectionUtils.isEmpty(keyidMapBlockList)) return null;
        ArrayList<String> keyColumnList = new ArrayList<String>();
        for(KeyidMapBlock keyidMapBlock : keyidMapBlockList){
            keyColumnList.add(keyidMapBlock.getField());
        }
        return keyColumnList;
    }

    private String calculateBitmapByKeyidMapBlockList(List<KeyidMapBlock> keyidMapBlockList) {
        if(CollectionUtils.isEmpty(keyidMapBlockList)){
            return null;
        }
        char[] bitmapChars = new char[17];
        for(int index = 0; index < bitmapChars.length; index++ ){
            bitmapChars[index] = '0';
        }
        for(KeyidMapBlock keyidMapBlock : keyidMapBlockList){
            bitmapChars[keyidMapBlock.getSeq() - 1] = '1';
        }
        return new String(bitmapChars);
    }

    private ArrayList<String> transferStringFormatToArrayListFormat(String keyList) {
        ArrayList<String> keysInChinese = new ArrayList<String>();
        if(keyList.contains(",")){
            String[] keyArray = keyList.split(",");
            for(String key : keyArray){
                keysInChinese.add(key);
            }
        }else {
            keysInChinese.add(keyList);
        }
        return keysInChinese;
    }

}
