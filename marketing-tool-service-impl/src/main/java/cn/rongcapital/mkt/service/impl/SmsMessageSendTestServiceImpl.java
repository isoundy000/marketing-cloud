package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.SmsSendUtilByIncake;
import cn.rongcapital.mkt.service.SmsMessageSendTestService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsMessageSendTestIn;
@Service
public class SmsMessageSendTestServiceImpl implements SmsMessageSendTestService{
    
    private Logger logger = LoggerFactory.getLogger(getClass());

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
    @Override
    public BaseOutput messageSendTest(SmsMessageSendTestIn body,
                    SecurityContext securityContext) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        logger.info("发送手机号：{} || 信息内容：{}", body.getReceiveMobiles(), body.getSendMessage());
        String receiveMobiles = body.getReceiveMobiles();
        String[] receiveMobileArrays = receiveMobiles.split(",");
        String sendMessage = body.getSendMessage();
        
        Map<Long, String[]> SmsBatchMap = new LinkedHashMap<Long, String[]>();
        
        for(int i = 0; i < receiveMobileArrays.length; i++) {
            String[] sms = {receiveMobileArrays[i],sendMessage};
            SmsBatchMap.put(Long.valueOf(i), sms);
        }
        Map<Long, Integer> sendResult = SmsSendUtilByIncake.sendSms(SmsBatchMap);
        
        // 输出日志
        if(sendResult != null) {
            String sendResultString = "返回信息" + sendResult.size() +"条：[";
            for(int i = 0; i < receiveMobileArrays.length; i++) {
                sendResultString += "{手机号：" + receiveMobileArrays[i] + ",返回状态码：" + sendResult.get(Long.valueOf(i)) + "},";
            }
            if(receiveMobileArrays.length > 0) {
                sendResultString = sendResultString.substring(0, sendResultString.length()-1);
            }
            sendResultString += "]";
            logger.info(sendResultString);
        } else {
            logger.info("返回结果为：null");
        }
        
        // 判断测试是否通过
        boolean testPase = true;
        if(sendResult == null || sendResult.size() != receiveMobileArrays.length) { // 如果返回结果为空，或者返回数量和发送数量不一致返回false
            testPase = false;
        } else {
            for(int i = 0; i < receiveMobileArrays.length; i++) {
                if(sendResult.get(Long.valueOf(i)) <= 0) { // 如果一个失败则失败
                    testPase = false;
                    i = receiveMobileArrays.length;
                }
            }
        }
        
        return result;
    }

}
