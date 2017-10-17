/*************************************************
 * @功能简述: 获取事件概要信息
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/1/9
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventGeneralGetService {

    /**
     * 获取事件概要信息
     * 
     * @param eventId 事件主键
     * @return BaseOutput
     * @author zhuxuelong
     * @date: 2017/1/9
     */
    public BaseOutput getEventGeneral(Long eventId);

}
