/*************************************************
 * @功能简述: MaterialCouponAudienceCreateService 实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 2016/12/13
 * @复审人:
 * 
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponAudienceCreateService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCreateAudienceVO;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponAudienceCreateServiceImpl implements MaterialCouponAudienceCreateService {

    @Autowired
    private MQTopicService mqTopicService;

    private static final String MQ_CREATE_TARGET_AUDIENCE_SERVICE = "createTargetAudienceGroupTask";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput createTargetAudienceGroup(MaterialCouponCreateAudienceVO mcca) throws JMSException {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        String jsonString = JSONObject.toJSONString(mcca);
        
        ActiveMqMessageVO message = new ActiveMqMessageVO();
        message.setTaskName("优惠券新建固定人群任务");
        message.setServiceName(MQ_CREATE_TARGET_AUDIENCE_SERVICE);
        message.setMessage(jsonString);
        mqTopicService.senderMessage(MQ_CREATE_TARGET_AUDIENCE_SERVICE, message);
        
        return result;
    }
}
