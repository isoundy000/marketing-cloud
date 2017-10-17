/*************************************************
 * @功能及特点的描述简述: 事件客体Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-08 
 * @date(最后修改日期)：2017-01-08 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.event.po.EventObject;

public interface EventObjectService {
    
    EventObject selectById(Integer eventObjectId);

}
