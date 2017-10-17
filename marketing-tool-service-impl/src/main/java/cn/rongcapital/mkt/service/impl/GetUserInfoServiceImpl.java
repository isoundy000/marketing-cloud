package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.UserInfoDao;
import cn.rongcapital.mkt.po.UserInfo;
import cn.rongcapital.mkt.service.GetUserInfoService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class GetUserInfoServiceImpl implements GetUserInfoService{

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public BaseOutput getUserInfo(String userId) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        //返回结果集
        Map<String, Object> resultMap = new HashMap<>();
        UserInfo userInfo = userInfoDao.getUserInfo(userId);
        if(null != userInfo){
            resultMap.put("comp_id", userInfo.getCompId());
            resultMap.put("comp_name", userInfo.getCompName());
        }
        baseOutput.getData().add(resultMap);
        return baseOutput;
    }

    
    
}
