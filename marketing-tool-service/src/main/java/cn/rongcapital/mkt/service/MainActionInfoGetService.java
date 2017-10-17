/*************************************************
 * @功能简述: 获取某联系人行为信息(微信、web、活动)的业务类接口 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MainActionInfoGetService {
	BaseOutput getMainActionInfo(String contactId,String behaviorType);
	
	BaseOutput getPartyBehaviorCountById(String contactId);
}
