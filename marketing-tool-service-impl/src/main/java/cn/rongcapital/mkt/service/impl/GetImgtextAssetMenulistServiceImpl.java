package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.service.GetImgtextAssetMenulistService;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-6.
 */
@Service
public class GetImgtextAssetMenulistServiceImpl implements GetImgtextAssetMenulistService{

    @Autowired
    private ImgTextAssetDao imgTextAssetDao;

    @Override
    public Object getImgTextAssetMenulist(BaseInput baseInput) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

        Map<String, Object> paramMap = constructParamMap();
        List<Map<String,Object>> menuList = imgTextAssetDao.selectMenuList(paramMap);
        baseOutput.getData().addAll(menuList);

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(baseOutput.getData().size());
        return Response.ok().entity(baseOutput).build();
    }

    private Map<String, Object> constructParamMap() {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("status",0);
        return paramMap;
    }
}
