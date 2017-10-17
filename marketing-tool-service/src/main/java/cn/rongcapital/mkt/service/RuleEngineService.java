package cn.rongcapital.mkt.service;

import java.util.List;
/**
 * @author wangweiqiang
 *
 */
public interface RuleEngineService {

    /**
     * 请求引擎服务器
     * @param keyIds 
     * @return
     */
    public Boolean requestRuleEngine(List<Integer> keyIds);
    
}
