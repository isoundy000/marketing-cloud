package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.service.GetWxImgTextAssetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;

/**
 * Created by lijinkai on 2016-9-14.
 */
@Service
public class GetWxImgTextAssetServiceImpl implements GetWxImgTextAssetService {

    @Autowired
    ImgTextAssetDao imgTextAssetDao;

    @Override
    public Object getWxImgTextAssetService(ImgAsset imgAsset) {
        
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
    	Map<String, Object> paramMap = getParamMap(imgAsset);
        
        List<Map<String,Object>> listBySearchKeyLike = imgTextAssetDao.selectListBySearchKeyLike(paramMap);
        //重组数据格式
        List<Map<String,Object>> list = constructOutData(listBySearchKeyLike);
        
        int total = imgTextAssetDao.selectListBySearchKeyLikeCount(paramMap);
        baseOutput.getData().addAll(list);
        baseOutput.setTotalCount(total);
        baseOutput.setTotal(list.size());
        return Response.ok().entity(baseOutput).build();
    }

    private Map<String, Object> getParamMap(ImgAsset imgAsset) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("pubId",imgAsset.getPubId());
        paramMap.put("type",imgAsset.getAssetType());
        paramMap.put("wxType",imgAsset.getWxType());
        paramMap.put("searchKey",imgAsset.getSearchKey());
        paramMap.put("startIndex",imgAsset.getIndex());
        paramMap.put("pageSize",imgAsset.getSize());
        return paramMap;
    }
    /**
     * 根据接口要求重组数据格式
     * @param listBySearchKeyLike
     * @return
     */
    private List<Map<String,Object>> constructOutData(List<Map<String,Object>> listBySearchKeyLike){
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	
    	Map<String,List<Map<String,Object>>> items = new HashMap<String,List<Map<String,Object>>>();
    	
    	if(listBySearchKeyLike != null && listBySearchKeyLike.size() > 0){
    		
    		List<Map<String,Object>> newItems = new ArrayList<Map<String,Object>>();
    		
    		Map<String,Object> tempDataMap = new HashMap<String, Object>();
    		
    		for(Map<String,Object> map : listBySearchKeyLike){
    			
    			tempDataMap = constructMap(map);
    			
    			String materialId = tempDataMap.get("materialId")+"";
    			
    			if(items.containsKey(materialId)){
    				
    				newItems = items.get(materialId);
    				newItems.add(tempDataMap);
    				items.put(materialId, newItems);
    				
    			}else{
    				
    				newItems = new ArrayList<Map<String,Object>>();
    				newItems.add(tempDataMap);
    				items.put(materialId, newItems);
    				
    			}
    			
    		}
    	}
    	
    	Map<String,Object> datamap = null;
    	
    	for( String key :items.keySet() ){
    		
    		datamap = new HashMap<String,Object>();
    		
    		datamap.put("media_id", key);
    		String createTime = items.get(key).get(0).get("createTime") + "";
    		datamap.put("create_time",createTime );
    		datamap.put("content", items.get(key));
    		
    		list.add(datamap);
    		
    	}
    	
    	return list;
    }

    /**
     * 重新组装Map
     * @param map
     */
	private Map<String, Object> constructMap(Map<String, Object> map) {
		
		Map<String, Object> tempMap = new HashMap<String, Object>();
		
		tempMap.put("materialId", map.get("materialId"));
		String createTime = map.get("createTime")+"";
		tempMap.put("createTime",createTime.substring(0, 10));
		tempMap.put("name", map.get("name"));
		tempMap.put("ownerName", map.get("ownerName"));
		tempMap.put("imgfileUrl", map.get("imgfileUrl"));
		
		Object digest = map.get("digest");
		if(digest != null){
			tempMap.put("digest", digest);
		}else{
			tempMap.put("digest", "");
		}
		
		return tempMap;
	}
	

    /**
     * 获取公众号下的图文资产列表
     * 
     * @param imgTextAsset
     * @return
     * @author shuiyangyang
     * @Date 2016-11-17
     */
    @Override
    public BaseOutput getWxImgTextAsset(ImgTextAsset imgTextAsset) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        if (imgTextAsset == null) {
            return result;
        }

        // 获取material_id
        List<String> materialIdLists =
                        imgTextAssetDao.selectMaterialIdListBySearchKeyLike(imgTextAsset);
        // 获取总数
        int totalCount = imgTextAssetDao.selectMaterialIdListBySearchKeyLikeCount(imgTextAsset);
        result.setTotalCount(totalCount);

        // 如果 materialIdLists 为空则不返回数据
        if (materialIdLists == null || materialIdLists.size() <= 0) {
            return result;
        }
        result.setTotal(materialIdLists.size());

        // 显示设置不分页
        imgTextAsset.setStartIndex(null);
        imgTextAsset.setPageSize(null);

        for (String materialIdList : materialIdLists) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("media_id", materialIdList);

            imgTextAsset.setMaterialId(materialIdList);
            // 根据media_id获取数据
            List<Map<String, Object>> mapBymaterialIdLists =
                            imgTextAssetDao.selectListBySearchKey(imgTextAsset);
            if (mapBymaterialIdLists == null || mapBymaterialIdLists.size() <= 0) {
                map.put("create_time", "");
            } else {
                map.put("create_time", mapBymaterialIdLists.get(0).get("createTime"));
            }
            map.put("content", mapBymaterialIdLists);

            result.getData().add(map);
        }

        return result;
    }
}
