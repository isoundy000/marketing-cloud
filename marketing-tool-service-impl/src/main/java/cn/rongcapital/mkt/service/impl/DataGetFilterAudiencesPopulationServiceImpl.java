package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.ContactwayEnum;
import cn.rongcapital.mkt.common.enums.TaskConditionEnum;
import cn.rongcapital.mkt.common.util.GenderUtils;
import cn.rongcapital.mkt.common.util.ReflectionUtil;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.service.DataGetFilterAudiencesStrategyService;

@Service
public class DataGetFilterAudiencesPopulationServiceImpl implements DataGetFilterAudiencesStrategyService{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ImportTemplateDao importTemplateDao;

	@Autowired
	private DataPopulationDao dataPopulationDao;

	public <T extends BaseQuery> Map<String,Object> getData(Integer mdType,
			List<Integer> mdTypeList, List<Integer> contactIdList, Integer timeCondition, T paramObj) {
		Map<String,Object> outMap = new HashMap<String,Object>(); 

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramMap = new HashMap<>();

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
		paramMap.put("mdType", mdType);

		if (timeCondition == null) {
			timeCondition = 0;
		}

		Date timeConditionDate = TaskConditionEnum.getEnumByCode(timeCondition).getTime();
		paramMap.put("timeCondition", timeConditionDate);
	
		paramMap.put("startIndex", paramObj.getStartIndex());
		paramMap.put("pageSize", paramObj.getPageSize());

		List<DataPopulation> dataList = new ArrayList<DataPopulation>();
		List<String> keyIds = new ArrayList<String>();
		
		Integer totalCount = 0;
		if (mdDataList != null && mdDataList.size() > 0) {
			
			dataList = dataPopulationDao.selectByBatchId(paramMap);
			totalCount = dataPopulationDao.selectCountByBatchId(paramMap);
			keyIds = getAudiencesIds(paramMap);
		}
		logger.info("dataList 列表数据----" + dataList.toString());
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (dataList != null && !dataList.isEmpty()) {
			ImportTemplate paramImportTemplate = new ImportTemplate();
			paramImportTemplate.setSelected(Boolean.TRUE);
			paramImportTemplate.setTemplType(mdType);

			List<ImportTemplate> importTemplateList = importTemplateDao.selectSelectedTemplateList(paramImportTemplate);

			for (DataPopulation tempT : dataList) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", ReflectionUtil.getObjectPropertyByName(tempT, "keyid"));
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

		logger.info("keyIds 主数据----" + keyIds.size());
		
		outMap.put("populationKeyIds", keyIds);
		outMap.put("partyCount", keyIds.size());
		outMap.put("resultList", resultList);
		outMap.put("totalCount", totalCount);
		outMap.put("total", dataList.size());
		return outMap;
	}
	
	/**
	 * 查询人口数量
	 * @param paramMap
	 * @return
	 */
	public Integer getAudiencesCount(Map<String, Object> paramMap ){
		
		return dataPopulationDao.getAudiencesCount(paramMap);
		
	}
	/**
	 * 查询人口对应主数据id(需要去重)
	 * @param paramMap
	 * @return
	 */
	public List<String> getAudiencesIds(Map<String, Object> paramMap){
			
		return dataPopulationDao.selectMappingKeyIds(paramMap);	
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
				List<String> columnNameList = dataPopulationDao.selectColumns();
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

}
