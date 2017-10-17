/*************************************************
 * @功能简述: 获取客体属性值列表
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.dao.event.EventObjectPropValueDao;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.po.EventObjectPropValue;
import cn.rongcapital.mkt.event.service.EventObjectPropsListService;
import cn.rongcapital.mkt.event.vo.in.EventObjecAttribure;
import cn.rongcapital.mkt.event.vo.out.EventObjectPropsOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class EventObjectPropsListServiceImpl implements EventObjectPropsListService {

    @Autowired
    private EventObjectDao eventObjectDao;

    @Autowired
    private EventObjectPropValueDao eventObjectPropValueDao;

    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput getEventObjProps(Long eventObjectId) {
        BaseOutput result =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
        EventObject condition = new EventObject();
        condition.setId(eventObjectId);
        condition.setStatus((byte) 0);
        final List<EventObjectPropsOut> propsList = new ArrayList<EventObjectPropsOut>();
        // 获取客体
        List<EventObject> eventObjList = eventObjectDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(eventObjList)) {
            EventObject eventObj = eventObjList.get(0);
            // 客体属性名称
            EventObjectPropsOut propInstance = new EventObjectPropsOut();
            propInstance.setName(eventObj.getInstanceNameProp());
            propInstance.setLabel(eventObj.getInstanceNameLabel());
            propsList.add(propInstance);
            // 其他属性
            if (StringUtils.isNotBlank(eventObj.getAttributes())) {
                List<EventObjecAttribure> arributes =
                        JSON.parseArray(eventObj.getAttributes(), EventObjecAttribure.class);
                arributes.forEach(item -> {
                    EventObjectPropsOut prop = new EventObjectPropsOut();
                    prop.setName(item.getName());
                    prop.setLabel(item.getLabel());
                    propsList.add(prop);
                });
            }
            // 获取客体属性值
            EventObjectPropValue propCondition = new EventObjectPropValue();
            propCondition.setObjectId(eventObjectId);
            List<EventObjectPropValue> propList = eventObjectPropValueDao.selectList(propCondition);
            if (CollectionUtils.isNotEmpty(propList)) {
                // 属性值分组
                Map<String, List<EventObjectPropValue>> groupProps =
                        propList.stream().collect(Collectors.groupingBy(EventObjectPropValue::getPropName));
                // 属性值设定
                propsList.forEach(item -> {
                    if (CollectionUtils.isNotEmpty(groupProps.get(item.getName()))) {
                        item.setValues(groupProps.get(item.getName()).stream().map(EventObjectPropValue::getPropValue)
                                .collect(Collectors.toList()));
                    }
                });
            }
            result.setTotal(propsList.size());
            result.setTotalCount(result.getTotal());
            result.getData().addAll(propsList);
        }
        return result;
    }

}
