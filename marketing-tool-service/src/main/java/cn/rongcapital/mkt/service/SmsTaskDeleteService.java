/**
 * 
 */
package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsTaskDeleteIn;

/**
 * @author shuiyangyang
 *
 */
public interface SmsTaskDeleteService {
    /**
     * 接口：mkt.sms.task.delete
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016.10.19
     */
    public BaseOutput smsTaskDelete(SmsTaskDeleteIn body, SecurityContext securityContext);

}
