package cn.rongcapital.mkt.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.common.beantool.BeanTools;
import cn.rongcapital.mkt.common.enums.ImportTemplClassEnum;
import cn.rongcapital.mkt.service.DataGetFilterAudiencesStrategyService;
/**
 * 
 * @author lijinkai
 * 2016-09-29
 *
 */
public class GetFilterAudiencesStrategyFactory {

	private static Logger logger = LoggerFactory.getLogger("cn.rongcapital.mkt.factory.GetFilterAudiencesStrategyFactory");
	
	public static DataGetFilterAudiencesStrategyService getFilterAudiencesStrategy(ImportTemplClassEnum importTemplClassEnum){
	    
		DataGetFilterAudiencesStrategyService strategy = null;
        
        try {
        	
            strategy = (DataGetFilterAudiencesStrategyService) BeanTools.getBean(importTemplClassEnum.getValue());
        } catch (Exception e) {
            logger.info("生成查询主数据策略service出错。。" + importTemplClassEnum.getValue().toString());
        } 
        
        return strategy;
    }
}
