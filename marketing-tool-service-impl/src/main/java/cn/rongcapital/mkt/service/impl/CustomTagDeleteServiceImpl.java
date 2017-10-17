package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.service.DeleteCustomTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.CustomTagDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagDeleteIn;

@Service
public class CustomTagDeleteServiceImpl implements CustomTagDeleteService {

    @Autowired
    private DeleteCustomTagService deleteCustomTagService;

    @Override
    public BaseOutput deleteCustomTag(CustomTagDeleteIn body) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        deleteCustomTagService.deleteCustomTagLeafByTagId(String.valueOf(body.getTagId()));
        return result;
    }
}
