/*************************************************
 * @功能简述: 编辑某条主数据详细信息的业务类接口 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.DataMainBaseInfoUpdateIn;

public interface DataMainBasicInfoUpdateService {
	BaseOutput updateBaseInfoByContactId(DataMainBaseInfoUpdateIn body);
}
