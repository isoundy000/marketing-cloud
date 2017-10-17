/**
 * 描述：描述：返回渠道名，渠道大类，和市的tag
 * 
 * @author shuiyangyang
 * @date 2016.09.28
 */
package cn.rongcapital.mkt.service;

import java.util.Map;

public interface SynchroMongodbCityService {
	
	/**
	 * 描述：返回渠道名，渠道大类，和市的tag
	 * 
	 * @param mid 
	 * @author shuiyangyang
	 * @return Map<String, Object>
	 * @date 2016.09.28
	 */
    public Map<String, Object> synchroMongodbCity(Integer keyId);
}
