/*************************************************
 * @功能简述: 获取某条主数据详细信息
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0 @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MainBasicInfoGetService {
	Integer getKeyIdFromContactInfo(Integer contactId, Integer dataType, String userToken);
    
    BaseOutput getMainBasicInfo(Integer contactId, Integer dataType, String userToken);
}
