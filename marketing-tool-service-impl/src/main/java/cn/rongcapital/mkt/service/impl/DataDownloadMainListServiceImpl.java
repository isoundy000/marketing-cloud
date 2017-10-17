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
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.service.DataDownloadMainListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.DataGetMainListOut;

@Service
public class DataDownloadMainListServiceImpl implements DataDownloadMainListService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImportTemplateDao importTemplateDao;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private DataPopulationDao dataPopulationDao;

    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;

    @Autowired
    private DataArchPointDao dataArchPointDao;

    @Autowired
    private DataMemberDao dataMemberDao;

    @Autowired
    private DataLoginDao dataLoginDao;

    @Autowired
    private DataPaymentDao dataPaymentDao;

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Override
    public BaseOutput downloadMainList(String userToken, Integer dataType) {

        DataGetMainListOut result = new DataGetMainListOut(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        Map<String, String> resultMap = new HashMap<>();
        String fileName = "";
        if (dataType == DataTypeEnum.PARTY.getCode()) {
            fileName = generateDownloadFile(dataPartyDao, dataType);
        } else if (dataType == DataTypeEnum.POPULATION.getCode()) {
            fileName = generateDownloadFile(dataPopulationDao, dataType);
        } else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
            fileName = generateDownloadFile(dataCustomerTagsDao, dataType);
        } else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
            fileName = generateDownloadFile(dataArchPointDao, dataType);
        } else if (dataType == DataTypeEnum.MEMBER.getCode()) {
            fileName = generateDownloadFile(dataMemberDao, dataType);
        } else if (dataType == DataTypeEnum.LOGIN.getCode()) {
            fileName = generateDownloadFile(dataLoginDao, dataType);
        } else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
            fileName = generateDownloadFile(dataPaymentDao, dataType);
        } else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
            fileName = generateDownloadFile(dataShoppingDao, dataType);
        } else {
            logger.error("传入错误的data type : {}", dataType);
        }

        resultMap.put("download_file_name", fileName);
        result.getData().add(resultMap);

        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T extends BaseQuery, D extends BaseDao> String generateDownloadFile(D dao, Integer dataType) {
        List<T> dataList = dao.selectList(null);
        ImportTemplate paramImportTemplate = new ImportTemplate();
        paramImportTemplate.setTemplType(dataType);
        paramImportTemplate.setPageSize(0);
        List<ImportTemplate> importTemplates = importTemplateDao.selectList(paramImportTemplate);
        List<Map<String, String>> columnsMap = transferNameListtoMap(importTemplates);

        File file = FileUtil.generateFileforDownload(columnsMap, dataList, FileNameEnum.getNameByCode(dataType));

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
