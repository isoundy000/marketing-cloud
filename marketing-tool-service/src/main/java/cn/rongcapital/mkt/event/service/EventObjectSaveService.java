/*************************************************
 * @功能简述: 事件客体注册
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/1/9
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.event.vo.in.EventObjectVo;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventObjectSaveService {

    /**
     * 事件客体注册
     * 
     * @param source 事件客体定义
     * @return BaseOutput
     * @author zhuxuelong
     * @date: 2017/1/9
     */
    public BaseOutput saveEventObj(EventObjectVo event);

}
