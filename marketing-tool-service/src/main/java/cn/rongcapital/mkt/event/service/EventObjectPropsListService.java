/*************************************************
 * @功能简述: 获取客体属性值列表
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/1/9
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventObjectPropsListService {

    /**
     * 获取客体属性值列表
     * 
     * @param eventObjectId 事件客体主键
     * @return BaseOutput
     * @author zhuxuelong
     * @date: 2017/1/9
     */
    public BaseOutput getEventObjProps(Long eventObjectId);

}
