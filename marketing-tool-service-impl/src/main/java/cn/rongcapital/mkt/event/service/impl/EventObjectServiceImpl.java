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
package cn.rongcapital.mkt.event.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.service.EventObjectService;
@Service
public class EventObjectServiceImpl implements EventObjectService {

    @Autowired
    private EventObjectDao eventObjectDao;
    
    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.service.EventObjectService#selectById(long)
     */
//    @Cacheable(value = "EventObjectById",keyGenerator = "wiselyKeyGenerator")
    @Override
    public EventObject selectById(Integer eventObjectId) {
        List<Integer> idList= new ArrayList<>();
        idList.add(eventObjectId);
        return this.eventObjectDao.selectListByIdList(idList).get(0);
    }

}
