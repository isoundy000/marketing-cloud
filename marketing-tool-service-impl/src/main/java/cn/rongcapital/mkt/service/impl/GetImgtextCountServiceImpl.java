package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.service.GetImgtextCountService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-7.
 */
@Service
public class GetImgtextCountServiceImpl implements GetImgtextCountService{

    @Autowired
    private ImgTextAssetDao imgTextAssetDao;

    @Override
    public Object getImgtextAssetCount() {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

        List<Map<String,Object>> results = imgTextAssetDao.selectImgtextAssetCountByType();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        for(Map<String,Object> map : results){
            if((Integer)map.get("type") == 0){
                resultMap.put("wechat_count",map.get("type_count"));
            }else{
                resultMap.put("h5_count",map.get("type_count"));
            }
        }
        baseOutput.getData().add(resultMap);
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(baseOutput.getData().size());
        return Response.ok().entity(baseOutput).build();
    }
}
