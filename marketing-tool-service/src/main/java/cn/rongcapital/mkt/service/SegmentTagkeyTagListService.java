/*************************************************
 * @功能简述: 根据关键字查询系统推荐标签下拉列表的业务类接口 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface SegmentTagkeyTagListService {
	BaseOutput getLastTagByKey(String tagGroupName);
	
	/**
	 * 模糊查询从mongodb中获取推荐标签列表
	 * 
	 * @author congshulin
	 * @功能简述 : 模糊查询从mongodb中获取推荐标签列表
	 * @param tagName
	 * @return BaseOutput
	 */
	BaseOutput getMongoTagRecommendByLike(String tagName);
}
