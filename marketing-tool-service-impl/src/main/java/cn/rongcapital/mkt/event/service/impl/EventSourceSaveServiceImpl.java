/*************************************************
 * @功能简述: 事件来源注册
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventPlatformDao;
import cn.rongcapital.mkt.dao.event.EventSourceDao;
import cn.rongcapital.mkt.event.po.EventPlatform;
import cn.rongcapital.mkt.event.po.EventSource;
import cn.rongcapital.mkt.event.service.EventSourceSaveService;
import cn.rongcapital.mkt.event.vo.in.EventSourceVo;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class EventSourceSaveServiceImpl implements EventSourceSaveService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EventSourceDao eventSourceDao;

    @Autowired
    private EventPlatformDao eventPlatformDao;

    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput saveEventSource(EventSourceVo source) {
        BaseOutput result =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
        EventSource sourceDb = new EventSource();
        sourceDb.setStatus((byte) 0);
        sourceDb.setCode(source.getCode());
        // 校验事件来源标示唯一性
        List<EventSource> existSource = eventSourceDao.selectList(sourceDb);
        if (CollectionUtils.isNotEmpty(existSource)) {
            logger.error("param is {}, event source code {} already exist.", source, source.getCode());
            return new BaseOutput(ApiErrorCode.BIZ_ERROR_EVENT_SOURCE_CODE_ALREADY_EXIST.getCode(),
                    ApiErrorCode.BIZ_ERROR_EVENT_SOURCE_CODE_ALREADY_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        }
        sourceDb.setName(source.getName());
        // 获取平台
        EventPlatform platForm = new EventPlatform();
        platForm.setCode(source.getPlatformCode());
        platForm.setStatus((byte) 0);
        List<EventPlatform> platFormList = eventPlatformDao.selectList(platForm);
        // 设置平台
        if (CollectionUtils.isEmpty(platFormList)) {
            logger.error("param is {}, platform_code {} is not exist.", source, source.getPlatformCode());
            return new BaseOutput(ApiErrorCode.BIZ_ERROR_EVENT_SOURCE_PF_NOT_EXIST.getCode(),
                    ApiErrorCode.BIZ_ERROR_EVENT_SOURCE_PF_NOT_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        }
        sourceDb.setPlatformId(platFormList.get(0).getId());
        sourceDb.setSystemSource(false);
        eventSourceDao.insert(sourceDb);
        return result;
    }


}
