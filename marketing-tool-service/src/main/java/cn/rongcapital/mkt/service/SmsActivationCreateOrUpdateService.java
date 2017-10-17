package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;

import javax.jms.JMSException;

/**
 * Created by byf on 10/18/16.
 */
public interface SmsActivationCreateOrUpdateService {
    BaseOutput createOrUpdateSmsActivation(SmsActivationCreateIn smsActivationCreateIn) throws JMSException;
}
