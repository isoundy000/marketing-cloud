/*************************************************
 * @功能及特点的描述简述: 事件相关Service接口类
 * 该类被编译测试过
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-07 
 * @date(最后修改日期)：2017-01-07 
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.event.vo.out.EventListOut;

public interface EventService {
    
    EventListOut selectList();

}
