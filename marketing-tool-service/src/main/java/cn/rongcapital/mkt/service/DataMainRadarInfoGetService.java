/*************************************************
 * @功能简述: 查询某联系人雷达图数据 的业务类接口 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface DataMainRadarInfoGetService {
	BaseOutput getRadarInfoByContactId(String contactId);
}
