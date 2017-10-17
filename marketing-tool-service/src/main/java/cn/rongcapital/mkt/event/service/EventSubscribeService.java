/*************************************************
 * @功能及特点的描述简述: 事件订阅及取消业务处理接口
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-1-9
 * @date(最后修改日期)：2017-1-9
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventSubscribeService {

    /**
     * 事件订阅及取消订阅
     * @param eventId 事件ID
     * @param subscribe 是否订阅
     * @return
     * @author xie.xiaoliang
     * @since 2017-1-9
     */
    BaseOutput eventSubscribe(long eventId,boolean subscribe);
}
