/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.service.SmsMessageSendRecordGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.MessageSendRecordGetOut;

/**
 * @author shuiyangyang
 *
 */
@Service
public class SmsMessageSendRecordGetServiceImpl implements SmsMessageSendRecordGetService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsTaskDetailDao smsTaskDetailDao;

    /**
     * 返回当前任务的发送记录列表（可以按照手机号查询）
     * 
     * @param smsTaskHeadId
     * @param receiveMobile
     * @return
     * @author shuiyangyang
     * @Date 2016.10.18
     */
    @Override
    public BaseOutput messageSendRecordGet(Long smsTaskHeadId, String receiveMobile, Integer index, Integer size) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        SmsTaskDetail smsTaskDetail = new SmsTaskDetail();
        smsTaskDetail.setSmsTaskHeadId(smsTaskHeadId);
        if (StringUtils.isNotEmpty(receiveMobile)) {
            smsTaskDetail.setReceiveMobile(receiveMobile);
        }
        smsTaskDetail.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        smsTaskDetail.setStartIndex((index-1)*size);
        smsTaskDetail.setPageSize(size);
        
        // 设置总数
        result.setTotalCount(smsTaskDetailDao.selectListCount(smsTaskDetail));

        List<MessageSendRecordGetOut> messageSendRecordGetOutLists =
                smsTaskDetailDao.messageSendRecordGet(smsTaskDetail);

        if (messageSendRecordGetOutLists != null && messageSendRecordGetOutLists.size() > 0) {
            result.setTotal(messageSendRecordGetOutLists.size());
            result.getData().addAll(messageSendRecordGetOutLists);
        }

        return result;
    }


}
