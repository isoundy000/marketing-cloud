package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.util.ReflectionUtil;
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
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.DataShopping;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.service.DataGetMainListService;
import cn.rongcapital.mkt.vo.out.DataGetMainListOut;

@Service
public class DataGetMainListServiceImpl implements DataGetMainListService {

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
    public Object getMainList(String method, String userToken, Integer dataType, Integer index, Integer size,
                    String ver) {

        DataGetMainListOut result = new DataGetMainListOut(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        List<Map<String, Object>> resultList = new ArrayList<>();
        ImportTemplate paramImportTemplate = new ImportTemplate(index, size);
        paramImportTemplate.setSelected(Boolean.TRUE);
        paramImportTemplate.setTemplType(dataType);
        List<ImportTemplate> importTemplateList = importTemplateDao.selectSelectedTemplateList(paramImportTemplate);
        List<Map<String, Object>> columnList = new ArrayList<>();
        if (importTemplateList != null && !importTemplateList.isEmpty()) {
            for (ImportTemplate importTemplate : importTemplateList) {
                Map<String, Object> map = new HashMap<>();
                map.put("col_name", importTemplate.getFieldName());
                map.put("col_code", importTemplate.getFieldCode());
                columnList.add(map);
                result.setMdType(importTemplate.getTemplType());
            }
        }

        if (dataType == DataTypeEnum.PARTY.getCode()) {
            DataParty paramObj = new DataParty(index, size);
            resultList = getData(dataType, paramObj, dataPartyDao, importTemplateList, result);
        } else if (dataType == DataTypeEnum.POPULATION.getCode()) {
            DataPopulation paramObj = new DataPopulation(index, size);
            resultList = getData(dataType, paramObj, dataPopulationDao, importTemplateList, result);
        } else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
            DataCustomerTags paramObj = new DataCustomerTags(index, size);
            resultList = getData(dataType, paramObj, dataCustomerTagsDao, importTemplateList, result);
        } else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
            DataArchPoint paramObj = new DataArchPoint(index, size);
            resultList = getData(dataType, paramObj, dataArchPointDao, importTemplateList, result);
        } else if (dataType == DataTypeEnum.MEMBER.getCode()) {
            DataMember paramObj = new DataMember(index, size);
            resultList = getData(dataType, paramObj, dataMemberDao, importTemplateList, result);
        } else if (dataType == DataTypeEnum.LOGIN.getCode()) {
            DataLogin paramObj = new DataLogin(index, size);
            resultList = getData(dataType, paramObj, dataLoginDao, importTemplateList, result);
        } else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
            DataPayment paramObj = new DataPayment(index, size);
            resultList = getData(dataType, paramObj, dataPaymentDao, importTemplateList, result);
        } else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
            DataShopping paramObj = new DataShopping(index, size);
            resultList = getData(dataType, paramObj, dataShoppingDao, importTemplateList, result);
        } else {
            logger.error("传入错误的data type : {}", dataType);
        }


        result.getData().addAll(resultList);
        result.setTotal(result.getData().size());
        result.getColNames().addAll(columnList);
        return Response.ok().entity(result).build();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T extends BaseQuery, D extends BaseDao> List<Map<String, Object>> getData(Integer mdType, T paramObj,
                    D dao, List<ImportTemplate> importTemplateList, DataGetMainListOut result) {

        int total = dao.selectListCount(null);
        result.setTotalCount(total);
        List<T> dataList = dao.selectList(paramObj);
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (dataList != null && !dataList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            for (T tempT : dataList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", ReflectionUtil.getObjectPropertyByName(tempT, "id"));
                for (ImportTemplate importTemplate : importTemplateList) {
                    Object value = ReflectionUtil.getObjectPropertyByName(tempT,
                                    ReflectionUtil.recoverFieldName(importTemplate.getFieldCode()));
                    if (value != null && value.getClass().getSimpleName().equals(Date.class.getSimpleName())) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        value = simpleDateFormat.format((Date) value);
                    }
                    map.put(importTemplate.getFieldCode(), value);
                }

                resultList.add(map);
            }
        }

        return resultList;
    }

}
