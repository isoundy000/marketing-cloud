package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.vo.out.TagAudienceDownloadOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.TagDownloadCustomAudienceService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.util.CollectionUtils;

@Service
public class TagDownloadCustomAudienceServiceImpl implements TagDownloadCustomAudienceService {

    @Autowired
    private FindCustomTagInfoService findCustomTagInfoService;

    @Autowired
    private DataPopulationDao dataPopulationDao;
    
    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public BaseOutput downloadCustomAudience(String tagId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        //Todo:1根据标签Id从mongo中将这个标签对应的人选出来
        List<DataParty> dataPartyList = findCustomTagInfoService.findMDataByTagId(tagId,null,null);
        if(!CollectionUtils.isEmpty(dataPartyList)){
            List<Integer> idList = new ArrayList<>();
            for(DataParty dataParty : dataPartyList){
                idList.add(dataParty.getMid());
            }
//            List<TagAudienceDownloadOut> tagAudienceDownloadOutList = dataPopulationDao.getTagAudienceDownloadList(idList);
            
            List<TagAudienceDownloadOut> tagAudienceDownloadOutList = dataPartyDao.getTagAudienceDownloadList(idList);
            
            Map<String, String> resultMap = new HashMap<>();
            List<Map<String, String>> columnsMapList = new ArrayList<>();
            String[][] excelTitles = { { "name", "姓名" }, { "mobile", "手机号" }, { "gender", "性别" },
                    { "birthday", "出生年月日" }, { "provice", "省" }, { "city", "市" }, { "email", "邮箱" },
                    { "identifyNo", "身份证号" }, { "drivingLicense", "驾驶证号" }, { "wxCode", "微信号" }, { "qq", "qq号" } };
            for (String[] a : excelTitles) {
                Map<String, String> map = new HashMap<>();
                map.put(a[0], a[1]);
                columnsMapList.add(map);
            }
            File file = FileUtil.generateFileforDownload(columnsMapList, tagAudienceDownloadOutList,
                    FileNameEnum.CUSTOM_AUDIENCE.getDetailName());
            resultMap.put("download_file_name", file.getName());
            result.getData().add(resultMap);
        }

//        Map<String, String> resultMap = new HashMap<>();
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("tagId", tagId);
//        List<DataParty> dataList = dataPartyDao.selectCustomAudiencesByTagId(paramMap);
//        ImportTemplate paramImportTemplate = new ImportTemplate();
//        paramImportTemplate.setTemplType(FileNameEnum.PARTY.getCode());
//        paramImportTemplate.setPageSize(0);
//        List<ImportTemplate> importTemplates = importTemplateDao.selectList(paramImportTemplate);
//        List<Map<String, String>> columnsMap = DataDownloadMainListServiceImpl.transferNameListtoMap(importTemplates);
//
//        File file = FileUtil.generateFileforDownload(columnsMap, dataList,
//                        FileNameEnum.CUSTOM_AUDIENCE.getDetailName());

//        resultMap.put("download_file_name", file.getName());
//        result.getData().add(resultMap);

        return result;
    }

}
