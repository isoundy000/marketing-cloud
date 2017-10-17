package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsSmstempletDelIn;

public interface SmsSmstempletDelService {
    /**
     * 短信模板删除
     * 
     * 接口：mkt.sms.smstemplet.del
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016-11-14
     */
    public BaseOutput delSmsTemple(SmsSmstempletDelIn body, SecurityContext securityContext);
}
