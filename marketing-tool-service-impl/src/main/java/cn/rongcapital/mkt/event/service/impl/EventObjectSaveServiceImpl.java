/*************************************************
 * @功能简述: 事件客体注册
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
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.service.EventObjectSaveService;
import cn.rongcapital.mkt.event.vo.in.EventObjecAttribure;
import cn.rongcapital.mkt.event.vo.in.EventObjectVo;
import cn.rongcapital.mkt.vo.BaseOutput;

import com.alibaba.fastjson.JSON;

@Service
public class EventObjectSaveServiceImpl implements EventObjectSaveService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EventObjectDao eventObjectDao;

    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput saveEventObj(EventObjectVo event) {
        BaseOutput result =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
        EventObject eventObjPo = new EventObject();
        eventObjPo.setCode(event.getCode());
        eventObjPo.setStatus((byte) 0);
        // 客体对象标识重复判断
        if (CollectionUtils.isNotEmpty(eventObjectDao.selectList(eventObjPo))) {
            logger.error("param is {}, event object code {} already exist.", event, event.getCode());
            return new BaseOutput(ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_CODE_ALREADY_EXIST.getCode(),
                    ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_CODE_ALREADY_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        }
        eventObjPo.setName(event.getName());
        eventObjPo.setInstanceNameLabel(event.getInstanceNameLabel());
        eventObjPo.setInstanceNameProp(event.getInstanceNameProp());

        List<EventObjecAttribure> attributes = event.getAttributes();
        if (CollectionUtils.isNotEmpty(attributes)) {
            // 客体对象属性重复判断
            Set<String> attriSet = attributes.stream().map(EventObjecAttribure::getName).collect(Collectors.toSet());
            if (attriSet.size() != attributes.size()) {
                logger.error("param is {}, event object attribute is duplicated.", event);
                return new BaseOutput(ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_ATTRIBUTE_DUPLICATED.getCode(),
                        ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_ATTRIBUTE_DUPLICATED.getMsg(), ApiConstant.INT_ZERO, null);
            }
            eventObjPo.setAttributes(JSON.toJSONString(attributes));
        }
        eventObjPo.setSystemObject(false);
        eventObjectDao.insert(eventObjPo);
        return result;
    }

}
