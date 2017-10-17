package cn.rongcapital.mkt.factory;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.ImportTemplClassEnum;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.service.DataGetFilterAudiencesStrategyService;

public class GetFilterAudiencesStrategyFacade {

	
	/**
     * 实际执行的策略
     * @param number
     * @return
     */
    public static <T extends BaseQuery>  Map<String, Object> doGetData(Integer mdType,
			List<Integer> mdTypeList, List<Integer> contactIdList, Integer timeCondition, T paramObj){
        
    	DataGetFilterAudiencesStrategyService strategy = GetFilterAudiencesStrategyFactory.getFilterAudiencesStrategy(getStrategyType(mdType));
    	return strategy.getData(mdType, mdTypeList, contactIdList, timeCondition, paramObj);
        
    }
    
	/**
	 * 查询人口数量
	 * @param paramMap
	 * @return
	 */
	public static Integer doGetAudiencesCount(Map<String, Object> paramMap ){
		
		DataGetFilterAudiencesStrategyService strategy 
			= GetFilterAudiencesStrategyFactory.getFilterAudiencesStrategy(getStrategyType(Integer.valueOf(paramMap.get("mdType").toString())));
		return strategy.getAudiencesCount(paramMap);
		
	}
	/**
	 * 查询人口对应主数据id(需要去重)
	 * @param paramMap
	 * @return
	 */
	public static List<String> doGetAudiencesIds(Map<String, Object> paramMap){
		
		DataGetFilterAudiencesStrategyService strategy 
			= GetFilterAudiencesStrategyFactory.getFilterAudiencesStrategy(getStrategyType(Integer.valueOf(paramMap.get("mdType").toString())));
		return strategy.getAudiencesIds(paramMap);
		
	}
    
    /**
     * 根据类型获取
     * @param mdType
     * @return
     */
    private static ImportTemplClassEnum getStrategyType(Integer mdType){
        
		if (mdType == DataTypeEnum.PARTY.getCode()) {
			return ImportTemplClassEnum.PARTY;
		} else if (mdType == DataTypeEnum.POPULATION.getCode()) {
			return ImportTemplClassEnum.POPULATION;
		} else if (mdType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
			return ImportTemplClassEnum.CUSTOMER_TAGS;
		} else if (mdType == DataTypeEnum.ARCH_POINT.getCode()) {
			return ImportTemplClassEnum.ARCH_POINT;
		} else if (mdType == DataTypeEnum.MEMBER.getCode()) {
			return ImportTemplClassEnum.MEMBER;
		} else if (mdType == DataTypeEnum.LOGIN.getCode()) {
			return ImportTemplClassEnum.LOGIN;
		} else if (mdType == DataTypeEnum.PAYMENT.getCode()) {
			return ImportTemplClassEnum.PAYMENT;
		} else if (mdType == DataTypeEnum.SHOPPING.getCode()) {
			return ImportTemplClassEnum.SHOPPING;
		} 
    	
    	return null;
    }
}
