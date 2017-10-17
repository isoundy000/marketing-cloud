/*************************************************
 * @功能简述: 事件来源注册
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/1/9
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.event.vo.in.EventSourceVo;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventSourceSaveService {

    /**
     * 事件来源注册
     * 
     * @param source 事件来源定义
     * @return BaseOutput
     * @author zhuxuelong
     * @date: 2017/1/9
     */
    public BaseOutput saveEventSource(EventSourceVo source);

}
