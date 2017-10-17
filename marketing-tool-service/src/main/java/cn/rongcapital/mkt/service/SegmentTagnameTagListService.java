/*************************************************
 * @功能简述: 查询系统推荐标签列表的业务类接口 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface SegmentTagnameTagListService {
	BaseOutput getSysRecommendedTagList();

	/**
	 * 根据标签树的id从mongodb中获取推荐标签列表
	 * 
	 * @author congshulin
	 * @功能简述 : 获取系统标签组列表
	 * @param method
	 * @param userToken
	 * @return BaseOutput
	 */
	BaseOutput getMongoTagRecommendList(String method, String userToken);
}
