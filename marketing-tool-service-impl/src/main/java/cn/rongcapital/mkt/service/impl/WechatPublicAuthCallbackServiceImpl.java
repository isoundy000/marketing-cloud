package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.service.WechatPublicAuthCallbackService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatPublicAuthCallbackIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-17.
 */
@Service
public class WechatPublicAuthCallbackServiceImpl implements WechatPublicAuthCallbackService {

    private static final Integer UNKNOWN_WECHAT_ASSET_TYPE = -1;

    private Logger logger = LoggerFactory.getLogger(WechatPublicAuthCallbackServiceImpl.class);


    @Autowired
    private WechatRegisterDao wechatRegisterDao;

    //如果表里已经存在weixinId则
    //这里还应该判断是否是二次注册，如果是二次注册需要跟新操作时间

    @Override
    public BaseOutput authWechatPublicCallback(WechatPublicAuthCallbackIn wechatPublicAuthCallbackIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        WechatRegister wechatRegister = new WechatRegister();
        wechatRegister.setWxAcct(wechatPublicAuthCallbackIn.getWeixinId());
        logger.info("已经进入回调接口");
        if("success".equals(wechatPublicAuthCallbackIn.getStatus())){
            wechatRegister.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            if(isAssetExistEver(wechatRegister)){
                wechatRegister.setCreateTime(new Date(System.currentTimeMillis()));
                wechatRegisterDao.updateConsignationTimeByWxacct(wechatRegister);
                baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
                baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
                return baseOutput;
            }
            wechatRegister.setName(wechatPublicAuthCallbackIn.getName());
            wechatRegister.setType(UNKNOWN_WECHAT_ASSET_TYPE);
            wechatRegisterDao.insert(wechatRegister);
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        }else{
            logger.info("回调接口返回值");
        }
        return baseOutput;
    }

    private boolean isAssetExistEver(WechatRegister wechatRegister) {
        Integer count = wechatRegisterDao.selectListCount(wechatRegister);
        if(count > 0) return true;
        return false;
    }
}
