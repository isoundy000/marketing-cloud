/**
 * @author shuiyangyang
 * @Date 2016.10.18
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
public interface SmsMessageSendRecordGetService {
    /**
     * 返回当前任务的发送记录列表（可以按照手机号查询）
     * 
     * @param smsTaskHeadId
     * @param receiveMobile
     * @return
     * @author shuiyangyang
     * @Date 2016.10.18
     */
    public BaseOutput messageSendRecordGet(Long smsTaskHeadId, String receiveMobile, Integer index, Integer size);
}
