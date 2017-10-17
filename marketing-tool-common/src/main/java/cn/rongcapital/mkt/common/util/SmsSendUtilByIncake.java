package cn.rongcapital.mkt.common.util;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.vo.sms.in.SmsRequestVo;
import cn.rongcapital.mkt.vo.sms.out.SmsResponseVo;


public class SmsSendUtilByIncake {
    private static final String APPLICATION_JSON = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    private static final int HTTP_STATUS_OK = 200;
    private static final String HTTP_ERROR = "-99";
    private static final Integer HTTP_ERROR_INTEGER = -99;
//    private static final String MESSAGE_SEND_URL = "http://43.254.53.81:8040/YunXiangSMS/SendCRMSms";
//    private static final String MESSAGE_SEND_URL = "http://gk.incake.net/YiMa/YiMaOrder";
    private static final String MESSAGE_SEND_URL = "http://gk.incake.net/YunXiangSMS/SendCRMSms";
    private static final String DEFAULT_PARTNER_NAME = "云像";
    private static final String DEFAULT_PARTNER_NO = "001";
//    private static final String INCAKE_NUM = "6415DAE359507AE62A875533B90A80B6";
    private static final String INCAKE_NUM = "ERROR_INCAKE_NUM";
    private static final String[] BATCH_RETURN_ERROR_CODE_LIST = {"-1001", "-1004", "-0000"};
    
    /* 
        -1001 数据获取失败
        -1002 电话号码错误
        -1003 短信内容错误
        -1004 签名错误
        -0000 系统错误
    */
    
    private static Logger logger = LoggerFactory.getLogger(SmsSendUtilByIncake.class);
    
    public static String send(String url, String jsonStr) {

        // 设置返回结果
        String result = null;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        try {
            // 设置post请求body
            StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(stringEntity);
            // 发送请求
            CloseableHttpResponse response = httpclient.execute(httpPost);
            // 获取状态码
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            logger.info("==============================={}",statusCode);
            if(statusCode == HTTP_STATUS_OK) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
                
                logger.info("-------------------------------------------->");
                logger.info(result);
                logger.info("-------------------------------------------->");
                
                response.close();
            } else {
                result = HTTP_ERROR;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
        return result;
    }
    
    public static String stringMD5(String input) {
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();

            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);

            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();

            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;

        for (byte b : byteArray) {

            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

            resultCharArray[index++] = hexDigits[b & 0xf];

        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
    
    public Map<String, String> sendSms(List<String> phones, String message) {
        if(phones == null || phones.size() < 1) {
            logger.error("phones illegal!");
            return null;
        }
        if(message == null){
            logger.error("message illegal!");
            return null;
        }
        SmsRequestVo smsVo  =  null;
        List<SmsRequestVo> smsVoList = new ArrayList<>();
        for(String phone : phones){
            smsVo = new SmsRequestVo();
            smsVo.setPartner_Name(DEFAULT_PARTNER_NAME);
            smsVo.setPartner_No(DEFAULT_PARTNER_NO);
            smsVo.setPartner_Phone(phone);
            smsVo.setPartner_Msg(message);
            smsVo.setPartner_Time(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
            smsVoList.add(smsVo);
        }
        String jsonString = JSONObject.toJSONString(smsVoList);
        String md5String =  stringMD5(jsonString + INCAKE_NUM);
        
        String requestBody = "order="+ jsonString +"&sagin="+ md5String;
        
        String result = send(MESSAGE_SEND_URL, requestBody);
        
        List<SmsResponseVo> outVoList = JSONArray.parseArray(result, SmsResponseVo.class);
        
        Map<String, String> resultMap = new HashMap<>();
        for(SmsResponseVo out : outVoList){
            logger.info("msg is {}" , out.get_Msg());
            resultMap.put(out.get_Phone(), out.get_Code());
        }
        return resultMap;
    }
    
    
    public static Map<Long, Integer> sendSms(Map<Long, String[]> SmsBatchMap) {
        Map<Long, Integer> resultMap = new HashMap<>();
        
        // 处理返回空map的情况
        if(SmsBatchMap ==null || SmsBatchMap.isEmpty()) {
            return resultMap;
        }
        
        List<Long> idLists = new ArrayList<Long>();

        SmsRequestVo smsVo  =  null;
        List<SmsRequestVo> smsVoList = new ArrayList<>();
        for(Entry<Long, String[]> entry : SmsBatchMap.entrySet()){
            idLists.add(entry.getKey());
            
            smsVo = new SmsRequestVo();
            smsVo.setPartner_Name(DEFAULT_PARTNER_NAME);
            smsVo.setPartner_No(DEFAULT_PARTNER_NO);
            smsVo.setPartner_Phone(entry.getValue()[0]);
            smsVo.setPartner_Msg(entry.getValue()[1]);
            smsVo.setPartner_Time(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
            smsVoList.add(smsVo);
        }
        String jsonString = JSONObject.toJSONString(smsVoList);
        String md5String =  stringMD5(jsonString + INCAKE_NUM);
        
        String requestBody = "order="+ jsonString +"&sagin="+ md5String;
        
        String result = send(MESSAGE_SEND_URL, requestBody);
        // 结果返回为null或者空或者为网络错误则判断为网络错误
        if(StringUtils.isNotBlank(result) && !result.equals(HTTP_ERROR)) {
            List<SmsResponseVo> outVoList = JSONArray.parseArray(result, SmsResponseVo.class);
            
            if(outVoList == null) {
                logger.info("返回结果解析错误,短信id:{}, 返回信息 ：{}", idLists.toString(), result);
            } else {
                if(outVoList.size() == 1 && isBatchReturnErrorCode(outVoList.get(0).get_Code())) {
                    resultMap = batchSetError(idLists, Integer.valueOf(outVoList.get(0).get_Code()));
                } else {
                    // 检测接口是否正常
                    if (outVoList.size() != idLists.size()) {
                        logger.info("短信接口异常,发送{}条短信，返回{}条状态。短信id：{}, 返回信息：{}", idLists.size(),
                                        outVoList.size(), idLists.toString(), result);
                    }
                    int idListsCount = 0;
                    for(SmsResponseVo out : outVoList){
                        resultMap.put(idLists.get(idListsCount++), Integer.valueOf(out.get_Code()));
                    }
                }
            }
        } else {
            resultMap = batchSetError(idLists, HTTP_ERROR_INTEGER);
        }
        return resultMap;
    }
    
    private static Map<Long, Integer> batchSetError(List<Long> idLists, Integer error) {
        Map<Long, Integer> resultMap = new HashMap<>();
        for(Long idList : idLists) {
            resultMap.put(idList, error);
        }
        return resultMap;
    }
    
    private static boolean isBatchReturnErrorCode(String code) {
        for(String errorCode : BATCH_RETURN_ERROR_CODE_LIST) {
            if(errorCode.equals(code)) {
                return true;
            }
        }
        return false;
    }
    
    
    public static void main(String[] args) {
//        test1();
        test2();
    }
    
    private static void test1() {
        SmsSendUtilByIncake sms = new SmsSendUtilByIncake();
        List<String> phones = new ArrayList<String>();
        phones.add("135521347181");
        Map<String, String> sendSms = sms.sendSms(phones, "【INCAKE】测试短信");
        
        for(Entry<String, String> entry : sendSms.entrySet()){
           System.out.println("phone is " + entry.getKey() + "code is " + entry.getValue()); 
        }
    }
    
    private static void test2() {
        
        Map<Long, String[]> SmsBatchMap = new LinkedHashMap<>();
        String[] sms1 = new String[2];
        sms1[0] = "150019861281";
        sms1[1] = "【INCAK】测试短信，大伟1";
        SmsBatchMap.put((long) 1, sms1);
        String[] sms2 = new String[2];
        sms2[0] = "15001986128";
        sms2[1] = "【INCAKE】测试短信，大伟2";
        SmsBatchMap.put((long) 2, sms2);
        
        Map<Long, Integer> sendSms = sendSms(SmsBatchMap);
        
        for(Entry<Long, Integer> entry : sendSms.entrySet()){
           System.out.println("id is " + entry.getKey() + "  code is " + entry.getValue()); 
        }
    }
    

}
