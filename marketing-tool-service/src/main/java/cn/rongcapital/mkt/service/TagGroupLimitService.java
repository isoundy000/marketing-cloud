
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 细分的组和组内标签个数限制接口
 *
 * @see （与该类关联的类): TagGroupLimitServiceImpl
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.5
 * @date(创建、开发日期): 2016-11-07
 * 最后修改日期: 2016-11-07
 * @复审人: 丛树林
  *************************************************/
public interface TagGroupLimitService {
	
	/**
     * @功能简述: 获取细分的标签组和组内标签个数限制接口
     *      
     *
     * @param: source
     *          来源
     * @return:
     *      BaseOutput
     */
	public BaseOutput getTagGroupLimit(String source);
}
