/*************************************************
 * @功能及特点的描述简述: 事件查询Service实现类
 * 该类被编译测试过
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-07 
 * @date(最后修改日期)：2017-01-07 
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.service.EventService;
import cn.rongcapital.mkt.event.vo.out.EventListItemOut;
import cn.rongcapital.mkt.event.vo.out.EventListOut;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    /*
     * (non-Javadoc)
     * 
     * @see cn.rongcapital.mkt.event.service.EventService#selectList()
     */
    @Override
    public EventListOut selectList() {
        EventListOut baseOutput =
                new EventListOut(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
        List<Event> events = this.eventDao.selectList(null);
        EventListItemOut item = null;
        for (Event event : events) {
            item = new EventListItemOut();
            BeanUtils.copyProperties(event, item);
            baseOutput.getListItems().add(item);
        }
        baseOutput.setTotal(baseOutput.getListItems().size());
        baseOutput.setTotalCount(baseOutput.getTotal());
        return baseOutput;
    }

}
