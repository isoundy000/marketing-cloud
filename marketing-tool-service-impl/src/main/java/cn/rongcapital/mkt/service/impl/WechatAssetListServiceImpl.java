package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.service.WechatAssetListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-31.
 */
@Service
public class WechatAssetListServiceImpl implements WechatAssetListService{

    @Autowired
    private WechatAssetDao wechatAssetDao;

    @Override
    public Object getWechatAssetListByType(Integer assetType, Integer index, Integer size) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        if(assetType == null){
            baseOutput.setMsg("请求参数错误");
            return Response.ok().entity(baseOutput).build();
        }

        Map<String,Object> paramMap = constructParamMap(assetType, index, size);
        constructRightResult(baseOutput, paramMap);
        return Response.ok().entity(baseOutput).build();
    }

    /**
     * 获取公众号资产列表
     */
    @Override
    public Object getWechatAssetList(Integer index, Integer size) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        
        WechatAsset wechatAsset = new WechatAsset();
        
        if(index != null){
        	//wechatAsset.setStartIndex(index);
        	wechatAsset.setStartIndex((index-1)*size);
        }
        if(size != null){
        	wechatAsset.setPageSize(size);
        }
        wechatAsset.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAsset);
        
        int tatal = wechatAssetDao.selectListCount(null);
        baseOutput.getData().addAll(wechatAssetList);

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(wechatAssetList.size());
        baseOutput.setTotalCount(tatal);
        return Response.ok().entity(baseOutput).build();
    }    
    
    private void constructRightResult(BaseOutput baseOutput, Map<String, Object> paramMap) {
        List<Map<String,Object>> resultList = null;
        if(paramMap.get("asset_type") instanceof Integer){
            if((Integer)paramMap.get("asset_type") != 3){
                resultList = wechatAssetDao.selectAssetListByType(paramMap);
            }else if((Integer)paramMap.get("asset_type") == 3){
                resultList = wechatAssetDao.selectServerAndBookList(paramMap);
            }
            for(Map<String,Object> map : resultList){
                if(map.get("total_count") == null){
                    map.put("follower_count",0);
                }else{
                    map.put("follower_count", map.remove("total_count"));
                }
                baseOutput.getData().add(map);
            }
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            baseOutput.setTotal(baseOutput.getData().size());
        }
    }

    private Map<String,Object> constructParamMap(Integer assetType, Integer index, Integer size) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("asset_type",assetType);
        if(index != null){
            paramMap.put("index",(index-1)*size);
        }else{
            paramMap.put("index", ApiConstant.PAGE_START_INDEX_DEFAULT);
        }
        if(size != null){
            paramMap.put("size",size);
        }else{
            paramMap.put("size",ApiConstant.PAGE_PAGE_SIZE_DEFAULT);
        }
        return paramMap;
    }
}
