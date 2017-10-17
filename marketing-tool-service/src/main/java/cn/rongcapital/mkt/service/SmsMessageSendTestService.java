package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsMessageSendTestIn;

public interface SmsMessageSendTestService {
    /**
     * 接口：mkt.sms.message.send.test 
     * 根据传入的手机号和短信内容进行测试发送
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016.10.20
     */
    public BaseOutput messageSendTest(SmsMessageSendTestIn body, SecurityContext securityContext);

}
