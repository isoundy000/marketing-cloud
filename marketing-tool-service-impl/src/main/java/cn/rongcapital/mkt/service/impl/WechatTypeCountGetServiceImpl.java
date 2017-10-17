package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.service.WechatTypeCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class WechatTypeCountGetServiceImpl implements WechatTypeCountGetService{

    @Autowired
    private WechatAssetDao wechatAssetDao;

    @Override
    public Object getWechatTypeCount() {
//        Map<String,Object> typeCounts = new HashMap<String,Object>();
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        Map<String,Object> paramMap = new HashMap<String,Object>();

        caculateWechatAssetCount(baseOutput, paramMap,ApiConstant.WECHAT_ASSET_SERVER_NUMBER);        //获取服务号及其总数
        caculateWechatAssetCount(baseOutput, paramMap,ApiConstant.WECHAT_ASSET_PERSONAL_NUMBER);        //获取个人号及其总数
        caculateWechatAssetCount(baseOutput, paramMap,ApiConstant.WECHAT_ASSET_SUBSCRIPTION_NUMBER);        //获取订阅号及其总数

        if(baseOutput.getData().size() > 0){
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            baseOutput.setTotal(baseOutput.getData().size());
        }
        return Response.ok().entity(baseOutput).build();
    }

    private void caculateWechatAssetCount(BaseOutput baseOutput, Map<String, Object> paramMap,int assetType) {
        paramMap.put("asset_type",assetType);
        List<Map<String,Object>> typeCountList = wechatAssetDao.getWechatCountByType(paramMap);
        if(typeCountList != null && typeCountList.size() > 0){
            baseOutput.getData().add(wechatCountByType(typeCountList,assetType));
        }else{
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("count",new Integer(0));
            resultMap.put("totalNumber",new Integer(0));
            resultMap.put("asset_type",assetType);
            baseOutput.getData().add(resultMap);
        }
    }

    private Map<String,Object> wechatCountByType(List<Map<String, Object>> typeCountList,int assetType) {
        if(typeCountList != null && typeCountList.size() > 0){
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("count",new Integer(0));
            resultMap.put("totalNumber",new Integer(0));
            for(Map<String,Object> map : typeCountList){
                resultMap.put("asset_type",assetType);
                resultMap.put("count",(Integer)resultMap.get("count") + 1);
                if(map.get("total_count") != null){
                    resultMap.put("totalNumber",(Integer)resultMap.get("totalNumber") + (Integer)map.get("total_count"));
                }
            }
            return resultMap;
        }
        return null;
    }
}
