package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.ContactwayEnum;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.ImportTemplTypeEnum;
import cn.rongcapital.mkt.common.enums.TaskConditionEnum;
import cn.rongcapital.mkt.dao.ContactWayMapDao;
import cn.rongcapital.mkt.dao.DataOptionMapDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.factory.GetFilterAudiencesStrategyFacade;
import cn.rongcapital.mkt.po.ContactWayMap;
import cn.rongcapital.mkt.po.DataOptionMap;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.service.DataGetFilterAudiencesService;
import cn.rongcapital.mkt.vo.in.CustomizeViewCheckboxIn;
import cn.rongcapital.mkt.vo.out.DataGetMainListOut;

@Service
public class DataGetFilterAudiencesServiceImpl implements DataGetFilterAudiencesService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ImportTemplateDao importTemplateDao;

	@Autowired
	private DataPartyDao dataPartyDao;

	@Autowired
	private DataOptionMapDao dataOptionMapDao;

	@Autowired
	private ContactWayMapDao contactWayMapDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T extends BaseQuery> Object getFilterAudiences(String method, String userToken, String ver, Integer index,
			Integer size, Integer dataType, List<Integer> dataTypeList, List<Integer> contactIds,
			List<CustomizeViewCheckboxIn> customizeViews, Integer timeCondition, Integer contactWayList) {
		// 返回的结果
		// 建议service返回的时候都返回VO对象,不然复用就很费事
		DataGetMainListOut result = new DataGetMainListOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

		List<Map<String, Object>> columnList = new ArrayList<>();
		List<ImportTemplate> importTemplateList = null;

		contactIds.add(contactWayList);

		// 对传入的条件进行检查, 并保存选择的条件.(用于传给前端, 分页的时候还要把条件传回来)
		updateConditions(dataTypeList, contactIds, timeCondition);

		// 保存自定义视图的选项状态
		if (customizeViews != null && !customizeViews.isEmpty()) {
			for (int i = 0; i < customizeViews.size(); i++) {
				ImportTemplate paramImportTemplate = new ImportTemplate(index, size);
				paramImportTemplate.setSelected(customizeViews.get(i).getIsSelected());
				paramImportTemplate.setFieldName(customizeViews.get(i).getColName());
				paramImportTemplate.setTemplType(dataType);
				importTemplateDao.updateSelectedByTemplType(paramImportTemplate);
			}
		}
		
		ImportTemplate paramImportTemplate = new ImportTemplate(index, size);
		paramImportTemplate.setSelected(Boolean.TRUE);
		paramImportTemplate.setTemplType(dataType);
		importTemplateList = importTemplateDao.selectSelectedTemplateList(paramImportTemplate);

		if (importTemplateList != null && !importTemplateList.isEmpty()) {
			for (ImportTemplate importTemplate : importTemplateList) {
				Map<String, Object> map = new HashMap<>();
				map.put("col_id", importTemplate.getId());
				map.put("col_name", importTemplate.getFieldName());
				map.put("col_code", importTemplate.getFieldCode());
				if (importTemplate.getFieldCode().equalsIgnoreCase("gender")) {
					map.put("col_code", "sex");
				}
				columnList.add(map);
				result.setMdType(importTemplate.getTemplType());
			}
		}
		
		DataParty paramObj = new DataParty(index, size);
		
		Map<String, Object> paramMap = new HashMap<>();
		contactIds = filterContactId(contactIds);
		if (CollectionUtils.isEmpty(dataTypeList)) {
			dataTypeList = null;
		}
		List<Integer> mdDataList = new ArrayList<Integer>();
		
		//逻辑写的有点复杂，待优化
		if(dataTypeList != null && dataTypeList.size() > 0){
			if (dataType != 0) {
				for (Integer mdType : dataTypeList) {
					if (mdType == dataType) {
						mdDataList.add(dataType);
					}
				}
			} else {
				for (Integer mdType : dataTypeList) {
					mdDataList.add(mdType);
				}
			}
		}

		paramMap.put("contactIdList", contactIds);
		paramMap.put("mdTypes", mdDataList);

		if (timeCondition == null) {
			timeCondition = 0;
		}

		Date timeConditionDate = TaskConditionEnum.getEnumByCode(timeCondition).getTime();
		paramMap.put("timeCondition", timeConditionDate);
		
		Map<String,Object> mapResult = GetFilterAudiencesStrategyFacade.doGetData(dataType, dataTypeList, contactIds, timeCondition, paramObj);
		
		Map<String,Integer> countRowsMap = getCounts(paramMap,dataTypeList);
		
		countRowsMap.put(ImportTemplTypeEnum.PARTY.getCountName(), Integer.valueOf(mapResult.get("partyCount").toString()));
		
		List<Map<String, Object>> countList = new ArrayList<>();
		List<ImportTemplate> importTemplateList2 = importTemplateDao.selectTemplTypePairs();
		for (ImportTemplate importTemplate : importTemplateList2) {
			Map<String, Object> countMap = new LinkedHashMap<>();
			String tagName = importTemplate.getTemplName();
			countMap.put("md_type", importTemplate.getTemplType());
			countMap.put("tag_name", tagName);
			
			Integer count = countRowsMap.get(ImportTemplTypeEnum.getCountNameByName(tagName));
			
			// 主数据不在此列中
			if (!CollectionUtils.isEmpty(dataTypeList)
					&& !importTemplate.getTemplType().equals(DataTypeEnum.PARTY.getCode())
					&& !isDataTypeListContainType(dataTypeList, importTemplate.getTemplType())) {
				count = 0;
			}
			countMap.put("count_rows", count);
			countList.add(countMap);
		}
		

		List<Integer> dataOptionMapList = new ArrayList<>();
		List<Integer> contactWayMapList = new ArrayList<>();
		String resultTimeCondition = null;

		List<DataOptionMap> dataOptionMaps = dataOptionMapDao.selectList(null);
		if (!CollectionUtils.isEmpty(dataOptionMaps)) {
			for (DataOptionMap dataOptionMap : dataOptionMaps) {
				if (dataOptionMap.getOptionStatus().compareTo(new Byte("1")) == 0) {
					dataOptionMapList.add(dataOptionMap.getTableId());
				}
			}
		}

		List<ContactWayMap> contactWayMaps = contactWayMapDao.selectList(null);
		if (!CollectionUtils.isEmpty(contactWayMaps)) {
			for (ContactWayMap contactWayMap : contactWayMaps) {
				if (contactWayMap.getStatus().compareTo(new Byte("1")) == 0) {
					contactWayMapList.add(contactWayMap.getContactWayId());
				}
			}
			resultTimeCondition = contactWayMaps.get(0).getTimeConditionAbbreviation();
		}

		
		result.setContactWayList(contactWayMapList);
		result.setDataTypeList(dataOptionMapList);
		result.setTimeCondition(Byte.valueOf(TaskConditionEnum.getEnumByAbbreviation(resultTimeCondition).getCode() + ""));
		
		result.getData().addAll((List<Map<String, Object>>)mapResult.get("resultList"));
		result.setTotalCount(Integer.valueOf(mapResult.get("totalCount").toString()));
		result.setTotal(Integer.valueOf(mapResult.get("total").toString()));
		result.setCountList(countList);
		result.getColNames().addAll(columnList);
		return Response.ok().entity(result).build();
	}

	/**
	 * 获取所有的数量
	 * @param paramMap
	 * @param dataTypeList
	 */
	private Map<String, Integer> getCounts(Map<String, Object> paramMap, List<Integer> dataTypeList) {

		Map<String, Integer> countRowsMap = new HashMap<String, Integer>();
		
		if (!CollectionUtils.isEmpty(dataTypeList)) {
			for (Integer dataType : dataTypeList) {
				if (dataType == DataTypeEnum.POPULATION.getCode()) {
					paramMap.put("mdType", dataType);
					countRowsMap.put(ImportTemplTypeEnum.POPULATION.getCountName(), 
							GetFilterAudiencesStrategyFacade.doGetAudiencesCount(paramMap)) ;
				} else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
					paramMap.put("mdType", dataType);
					countRowsMap.put(ImportTemplTypeEnum.CUSTOMER_TAGS.getCountName(), 
							GetFilterAudiencesStrategyFacade.doGetAudiencesCount(paramMap)) ;
				} else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
					paramMap.put("mdType", dataType);
					countRowsMap.put(ImportTemplTypeEnum.ARCH_POINT.getCountName(), 
							GetFilterAudiencesStrategyFacade.doGetAudiencesCount(paramMap)) ;
				} else if (dataType == DataTypeEnum.MEMBER.getCode()) {
					paramMap.put("mdType", dataType);
					countRowsMap.put(ImportTemplTypeEnum.MEMBER.getCountName(), 
							GetFilterAudiencesStrategyFacade.doGetAudiencesCount(paramMap)) ;
				} else if (dataType == DataTypeEnum.LOGIN.getCode()) {
					paramMap.put("mdType", dataType);
					countRowsMap.put(ImportTemplTypeEnum.LOGIN.getCountName(), 
							GetFilterAudiencesStrategyFacade.doGetAudiencesCount(paramMap)) ;
				} else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
					paramMap.put("mdType", dataType);
					countRowsMap.put(ImportTemplTypeEnum.PAYMENT.getCountName(), 
							GetFilterAudiencesStrategyFacade.doGetAudiencesCount(paramMap)) ;
				} else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
					paramMap.put("mdType", dataType);
					countRowsMap.put(ImportTemplTypeEnum.SHOPPING.getCountName(), 
							GetFilterAudiencesStrategyFacade.doGetAudiencesCount(paramMap)) ;
				} else {
					logger.warn("传入错误的data type : {}", dataType);
				}
			}
		}
		
		return countRowsMap;
		
	}

	private <D extends BaseDataFilterDao<?>> List<Integer> filterContactId(List<Integer> contactIdList) {
		List<Integer> resultList = new ArrayList<>();

		if (CollectionUtils.isEmpty(contactIdList)) {
			return resultList;
		}

		for (Integer contactId : contactIdList) {
			String columnName = ContactwayEnum.getColumnNameById(contactId);
			if (columnName == null) {
				continue;
			} else {
				List<String> columnNameList = dataPartyDao.selectColumns();
				for (String str : columnNameList) {
					if (columnName.equalsIgnoreCase(str)) {
						resultList.add(contactId);
						break;
					}
				}
			}
		}

		return resultList;
	}

	private void updateConditions(List<Integer> dataTypeList, List<Integer> contactIds, Integer timeCondition) {

//		if (CollectionUtils.isEmpty(dataTypeList)) {
//			for (int i = 1; i < 8; i++) {
//				dataTypeList.add(i);
//			}
//		}

		// 将数据类型的下拉列表里的状态保存起来
		if (!CollectionUtils.isEmpty(dataTypeList)) {
			dataOptionMapDao.updateSelectedStatusByIds(dataTypeList);
			dataOptionMapDao.updateUnSelectedStatusByIds(dataTypeList);
		}

		if (!CollectionUtils.isEmpty(contactIds)) {
			contactWayMapDao.updateSelectedStatusByIds(contactIds);
			contactWayMapDao.updateUnSelectedStatusByIds(contactIds);
		}

		if (timeCondition != null) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("timeCondition", TaskConditionEnum.getEnumByCode(timeCondition).getTime());
			paramMap.put("timeConditionAbbreviation", TaskConditionEnum.getEnumByCode(timeCondition).getAbbreviation());
			contactWayMapDao.updateTimeCondition(paramMap);
		}

	}

	private boolean isDataTypeListContainType(List<Integer> mdTypeList, Integer mdType) {
		if (CollectionUtils.isEmpty(mdTypeList)) {
			return false;
		} else {
			for (Integer code : mdTypeList) {
				if (mdType.equals(code)) {
					return true;
				}
			}
		}

		return false;
	}

}
