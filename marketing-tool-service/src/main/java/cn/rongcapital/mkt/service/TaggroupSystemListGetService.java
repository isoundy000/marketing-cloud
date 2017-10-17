/*************************************************
 * @功能简述: 获取系统标签组列表 
 * @see MktApi
 * @author: zhangwei
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TaggroupSystemListGetService {

    /**
     * mkt.tag.system.list.get
     * 
     * @author zhangwei
     * @功能简述 : 获取系统标签组列表
     * @param method
     * @param userToken
     * @param tagGroupId
     * @param index
     * @param size
     * @return
     */
    public BaseOutput getTagGroupByParentGroupId(String method, 
    		String userToken, Integer tagGroupId,
    		Integer index, Integer size);
    
	/**
	 * 根据标签树的id从mongodb中获取推荐标签值
	 * 
	 * @author congshulin
	 * @功能简述 : 获取系统标签组列表
	 * @param method
	 * @param userToken
	 * @param tagGroupId
	 * @param index
	 * @param size
	 * @return BaseOutput
	 */
	public BaseOutput getMongoTagRecommendByTagTreeId(String method, String userToken, String tagGroupId,
			Integer index, Integer size);
}
