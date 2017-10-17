/*************************************************
 * @功能及特点的描述简述: 事件订阅及取消业务处理实现类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-1-9
 * @date(最后修改日期)：2017-1-9
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.service.EventSubscribeService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class EventSubscribeServiceImpl implements EventSubscribeService {

    @Autowired
    private EventDao eventDao;

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.service.EventSubscribeService#eventSubscribe(long, boolean)
     */
    @Override
    public BaseOutput eventSubscribe(long eventId, boolean subscribe) {
        BaseOutput successOutput= new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        Event event = new Event();
        event.setId(eventId);
        List<Event> events = this.eventDao.selectList(event);
        if (events != null && events.size() > 0) {
            Event item = events.get(0);
            if (item.getUnsubscribable()) {//不可取消订阅则不能被修改订阅状态
                return new BaseOutput(ApiErrorCode.VALIDATE_ERROR_EVENT_UNSUBSCRIBABLE.getCode(), ApiErrorCode.VALIDATE_ERROR_EVENT_UNSUBSCRIBABLE.getMsg(), ApiConstant.INT_ZERO, null);
            }else{
                item.setSubscribed(subscribe);
                this.eventDao.updateById(item);
                return successOutput;
            }
        }
        return new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(), ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
    }

}
