package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.ContactsCommitDatapartyDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;


@Service
public class ContactsCommitDatapartyDownloadServiceImpl implements ContactsCommitDatapartyDownloadService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImportTemplateDao importTemplateDao;
    
    @Autowired
    private DataPartyDao dataPartyDao;
    
    @SuppressWarnings("rawtypes")
	public BaseOutput contactsCommitDatapartyDownload(Integer contact_id) {
    	
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
    	
        Map<String, Object> map = new HashMap<String, Object>();
        String fileName = generateDownloadFile(contact_id);
        map.put("download_url", fileName);
        result.getData().add(map);

        return result;
	}
    
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private String generateDownloadFile(Integer contact_id) {
    	
        List<DataParty> dataList = dataPartyDao.selectListByContactId(contact_id);
        ImportTemplate paramImportTemplate = new ImportTemplate();
        paramImportTemplate.setTemplType(DataTypeEnum.PARTY.getCode());
        paramImportTemplate.setPageSize(0);
        List<ImportTemplate> importTemplates = importTemplateDao.selectList(paramImportTemplate);
        List<Map<String, String>> columnsMap = transferNameListtoMap(importTemplates);

        File file = FileUtil.generateFileforDownload(columnsMap, dataList, FileNameEnum.getNameByCode(DataTypeEnum.PARTY.getCode()));

        return file.getName();
    }

    // 用于转换ImportTemplate,转换后map的key为字段名,value为对应的中文名
    public static List<Map<String, String>> transferNameListtoMap(List<ImportTemplate> importTemplates) {
        List<Map<String, String>> columnNamesMap = new ArrayList<>();
        if (CollectionUtils.isEmpty(importTemplates)) {
            Map<String, String> emptyMap = new HashMap<>();
            emptyMap.put("", "");
            columnNamesMap.add(emptyMap);
        } else {
            Map<String, String> idMap = new HashMap<>();
            Map<String, String> batchIdMap = new HashMap<>();
            batchIdMap.put("batch_id", "批次号");
            idMap.put("id", "id");
            columnNamesMap.add(idMap);
            columnNamesMap.add(batchIdMap);

            for (ImportTemplate importTemplate : importTemplates) {
                Map<String, String> map = new HashMap<>();
                map.put(importTemplate.getFieldCode(), importTemplate.getFieldName());
                columnNamesMap.add(map);
            }
        }
        return columnNamesMap;
    }
}
