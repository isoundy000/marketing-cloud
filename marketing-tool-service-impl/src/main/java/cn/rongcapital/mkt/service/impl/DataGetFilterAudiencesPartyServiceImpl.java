package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.ContactwayEnum;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.TaskConditionEnum;
import cn.rongcapital.mkt.common.util.GenderUtils;
import cn.rongcapital.mkt.common.util.ReflectionUtil;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.factory.GetFilterAudiencesStrategyFacade;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.service.DataGetFilterAudiencesStrategyService;

@Service
public class DataGetFilterAudiencesPartyServiceImpl implements DataGetFilterAudiencesStrategyService{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ImportTemplateDao importTemplateDao;

	@Autowired
	private DataPartyDao dataPartyDao;

	public <T extends BaseQuery> Map<String,Object> getData(Integer mdType,
			List<Integer> mdTypeList, List<Integer> contactIdList, Integer timeCondition, T paramObj) {

		Map<String,Object> outMap = new HashMap<String,Object>(); 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramMap = new HashMap<>();
		// 这段mappingKeyIds的逻辑绝对是效率上的作大死.以后肯定会改的.
		List<String> mappingKeyIds = new ArrayList<>();
		contactIdList = filterContactId(contactIdList);
		if (CollectionUtils.isEmpty(mdTypeList)) {
			mdTypeList = null;
		}
		List<Integer> mdDataList = new ArrayList<Integer>();
		//逻辑写的有点复杂，待优化
		if(mdTypeList != null && mdTypeList.size() > 0){
			if (mdType != 0) {
				for (Integer dataType : mdTypeList) {
					if (mdType == dataType) {
						mdDataList.add(mdType);
					}
				}
			} else {
				for (Integer dataType : mdTypeList) {
					mdDataList.add(dataType);
				}
			}
		}
		paramMap.put("contactIdList", contactIdList);
		paramMap.put("mdTypes", mdDataList);
		
		if (timeCondition == null) {
			timeCondition = 0;
		}

		Date timeConditionDate = TaskConditionEnum.getEnumByCode(timeCondition).getTime();
		paramMap.put("timeCondition", timeConditionDate);
		
		if (!CollectionUtils.isEmpty(mdDataList)) {
			for (Integer dataType : mdDataList) {
				List<String> tmpList = new ArrayList<String>();
				if (dataType == DataTypeEnum.POPULATION.getCode()) {
					paramMap.put("mdType", dataType);
					tmpList = GetFilterAudiencesStrategyFacade.doGetAudiencesIds(paramMap);
					if (!CollectionUtils.isEmpty(tmpList))
						mappingKeyIds.addAll(tmpList);
				} else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
					paramMap.put("mdType", dataType);
					tmpList = GetFilterAudiencesStrategyFacade.doGetAudiencesIds(paramMap);
					if (!CollectionUtils.isEmpty(tmpList))
						mappingKeyIds.addAll(tmpList);
				} else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
					paramMap.put("mdType", dataType);
					tmpList = GetFilterAudiencesStrategyFacade.doGetAudiencesIds(paramMap);
					if (!CollectionUtils.isEmpty(tmpList))
						mappingKeyIds.addAll(tmpList);
				} else if (dataType == DataTypeEnum.MEMBER.getCode()) {
					paramMap.put("mdType", dataType);
					tmpList = GetFilterAudiencesStrategyFacade.doGetAudiencesIds(paramMap);
					if (!CollectionUtils.isEmpty(tmpList))
						mappingKeyIds.addAll(tmpList);
				} else if (dataType == DataTypeEnum.LOGIN.getCode()) {
					paramMap.put("mdType", dataType);
					tmpList = GetFilterAudiencesStrategyFacade.doGetAudiencesIds(paramMap);
					if (!CollectionUtils.isEmpty(tmpList))
						mappingKeyIds.addAll(tmpList);
				} else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
					paramMap.put("mdType", dataType);
					tmpList = GetFilterAudiencesStrategyFacade.doGetAudiencesIds(paramMap);
					if (!CollectionUtils.isEmpty(tmpList))
						mappingKeyIds.addAll(tmpList);
				} else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
					paramMap.put("mdType", dataType);
					tmpList = GetFilterAudiencesStrategyFacade.doGetAudiencesIds(paramMap);
					if (!CollectionUtils.isEmpty(tmpList))
						mappingKeyIds.addAll(tmpList);
				} else {
					logger.warn("传入错误的data type : {}", dataType);
				}
			}
		}
		
		mappingKeyIds = clearRepeat(mappingKeyIds);
		
		Integer data_party_rows = mappingKeyIds.size();
		
		if(data_party_rows <= paramObj.getStartIndex()){
			paramObj.setStartIndex(0);
		}
		
		List<String> selectIds = splitPage(mappingKeyIds,paramObj.getStartIndex(),paramObj.getPageSize());
		
		// 所有表中的mapping_key都查不到, 索性把mapping_key设为-1, 这样一定不会查到任何数据
		selectIds.add("-1");
		paramMap.put("mappingKeyIds", selectIds);

		List<DataParty> dataList = dataPartyDao.selectByBatchId(paramMap);
		logger.info("dataList 列表数据----" + dataList.toString());
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (dataList != null && !dataList.isEmpty()) {
			ImportTemplate paramImportTemplate = new ImportTemplate();
			paramImportTemplate.setSelected(Boolean.TRUE);
			paramImportTemplate.setTemplType(mdType);

			List<ImportTemplate> importTemplateList = importTemplateDao.selectSelectedTemplateList(paramImportTemplate);

			for (DataParty tempT : dataList) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", ReflectionUtil.getObjectPropertyByName(tempT, "id"));
				for (ImportTemplate importTemplate : importTemplateList) {
					Object value = ReflectionUtil.getObjectPropertyByName(tempT,
							ReflectionUtil.recoverFieldName(importTemplate.getFieldCode()));
					if (value != null && value.getClass().getSimpleName().equals(Date.class.getSimpleName())) {
						simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						value = simpleDateFormat.format((Date) value);
					}
					map.put(importTemplate.getFieldCode(), value);
				}

				if (mdType == 0 || mdType == 1) {
					Object sexByte = ReflectionUtil.getObjectPropertyByName(tempT, "gender");
					if (sexByte != null) {
						String sex = GenderUtils.byteToChar(Byte.valueOf(sexByte + ""));
						map.put("sex", sex);
					}
				}

				resultList.add(map);
			}
		}

		logger.info("keyIds 主数据----" + mappingKeyIds.size());
		
		outMap.put("partyKeyIds", mappingKeyIds);
		outMap.put("partyCount", data_party_rows);
		outMap.put("resultList", resultList);
		outMap.put("totalCount", data_party_rows);
		outMap.put("total", dataList.size());
		
		return outMap;
	}
	
	/**
	 * 手动分页（由于查询出的ID数据比较大,程序处理获取先事业ids）
	 * @param mappingKeyIds
	 * @param startIndex
	 * @param pageSize
	 */
	private List<String> splitPage(List<String> mappingKeyIds, Integer startIndex, Integer pageSize) {
		
		logger.info("程序分页:startIndex: "+ startIndex + ",pageSize=" +pageSize );
		
		List<String> pageList = new ArrayList<String>();
		
		for(int i = startIndex; i<(startIndex + pageSize) && i <  mappingKeyIds.size() ; i++ ){
			pageList.add(mappingKeyIds.get(i));
		}
	
		return pageList;
		
	}

	/**
	 * 
	 * @param contactIdList
	 * @return
	 */
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

	/**
	 * 去除重复项
	 * @param mappingKeyIds
	 */
	private List<String> clearRepeat(List<String> mappingKeyIds){
		
		HashSet<String> hs = new HashSet<String>(mappingKeyIds);
		List<String> list = new ArrayList<String>(hs);
		return list;
	}

	@Override
	public Integer getAudiencesCount(Map<String, Object> paramMap) {
		return null;
	}



	@Override
	public List<String> getAudiencesIds(Map<String, Object> paramMap) {
		return null;
	}
}
