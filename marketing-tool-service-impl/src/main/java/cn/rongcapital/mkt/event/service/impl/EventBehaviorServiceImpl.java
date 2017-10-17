/*************************************************
 * @功能及特点的描述简述: 事件行为Service Implement
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-07 
 * @date(最后修改日期)：2017-01-07 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.event.service.EventBehaviorService;
import cn.rongcapital.mkt.mongodb.event.EventBehaviorRepository;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;
@Service
public class EventBehaviorServiceImpl implements EventBehaviorService {
    @Autowired
    private EventBehaviorRepository eventBehaviorRepository;
    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.service.EventBehaviorService#selectList()
     */
    
    @Override
    public List<EventBehavior> selectList() {
         
        return this.eventBehaviorRepository.findAll();
    }

}
