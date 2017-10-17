package cn.rongcapital.mkt.service;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.po.base.BaseQuery;

public interface DataGetFilterAudiencesStrategyService {

	public <T extends BaseQuery> Map<String,Object> getData(Integer mdType,
			List<Integer> mdTypeList, List<Integer> contactIdList, Integer timeCondition, T paramObj);
	
	/**
	 * 查询人口数量
	 * @param paramMap
	 * @return
	 */
	public Integer getAudiencesCount(Map<String, Object> paramMap );
	/**
	 * 查询人口对应主数据id(需要去重)
	 * @param paramMap
	 * @return
	 */
	public List<String> getAudiencesIds(Map<String, Object> paramMap);
}
