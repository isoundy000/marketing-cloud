/*************************************************
 * @功能简述: 获取系统标签组列表 
 * @see MktApi
 * @author: zhangwei
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TaggroupSystemMenulistGetService {

	/**
	 * mkt.taggroup.system.menulist.get
	 * 
	 * @author zhangwei
	 * @功能简述 : 获取系统标签组列表
	 * @param method
	 * @param userToken
	 * @param index
	 * @param size
	 * @return
	 */
	public BaseOutput getTaggroupSystemMenulist(String method, String userToken, Integer index, Integer size);

	/**
	 * 从mongo中获取系统标签组列表
	 * 
	 * @author congshulin
	 * @功能简述 : 获取系统标签组列表
	 * @param method
	 * @param userToken
	 * @param index
	 * @param size
	 * @return BaseOutput
	 */
	public BaseOutput getMonggTagTreelist(String method, String userToken, Integer index, Integer size);

}
