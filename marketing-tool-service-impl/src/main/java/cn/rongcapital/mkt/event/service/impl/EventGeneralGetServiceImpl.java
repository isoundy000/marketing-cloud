/*************************************************
 * @功能简述: 获取事件概要信息
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/1/9
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.service.EventGeneralGetService;
import cn.rongcapital.mkt.event.vo.out.EventGeneralOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class EventGeneralGetServiceImpl implements EventGeneralGetService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventObjectDao eventObjectDao;

    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput getEventGeneral(Long eventId) {
        BaseOutput result =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
        EventGeneralOut output = new EventGeneralOut();
        // 查找事件
        Event event = new Event();
        event.setId(eventId);
        event.setStatus((byte) 0);
        List<Event> eventList = eventDao.selectList(event);
        if (CollectionUtils.isNotEmpty(eventList)) {
            Event resultEvent = eventList.get(0);
            output.setId(resultEvent.getId());
            output.setCode(resultEvent.getCode());
            output.setName(resultEvent.getName());
            output.setSourceId(resultEvent.getSourceId());
            output.setObjectId(resultEvent.getObjectId());
            // 查找事件客体对象
            if (resultEvent.getObjectId() != null) {
                EventObject objCondition = new EventObject();
                objCondition.setId(resultEvent.getObjectId());
                objCondition.setStatus((byte) 0);
                List<EventObject> eventObjList = eventObjectDao.selectList(objCondition);
                if (CollectionUtils.isNotEmpty(eventObjList)) {
                    output.setObjectName(eventObjList.get(0).getName());
                }
            }
            result.setTotal(ApiConstant.INT_ONE);
            result.setTotalCount(ApiConstant.INT_ONE);
            result.getData().add(output);
        }
        return result;
    }


}
