package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.service.GetImgTextAssetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;
import cn.rongcapital.mkt.vo.weixin.ImgTextAssetVo;

/**
 * Created by Yunfeng on 2016-5-27.
 */
@Service
public class GetImgTextAssetServiceImpl implements GetImgTextAssetService {

    @Autowired
    ImgTextAssetDao imgTextAssetDao;

    @Override
    public Object getImgTextAssetService(ImgAsset imgAsset) {
        Map<String, Object> paramMap = getParamMap(imgAsset);
        List<Map<String, Object>> imgTextAssets = getImgTextAssets(imgAsset, paramMap);
        BaseOutput baseOutput = constructBaseOutput(imgTextAssets);
        return Response.ok().entity(baseOutput).build();
    }

    private Map<String, Object> getParamMap(ImgAsset imgAsset) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("owner_name",imgAsset.getOwnerName());
        paramMap.put("type",imgAsset.getAssetType());
        paramMap.put("index",(imgAsset.getIndex()-1)*imgAsset.getSize());
        paramMap.put("size",imgAsset.getSize());
        return paramMap;
    }

    private List<Map<String, Object>> getImgTextAssets(ImgAsset imgAsset, Map<String, Object> paramMap) {
        List<Map<String,Object>> imgTextAssets = null;
        if(imgAsset.getAssetType() == 2){
            if(imgAsset.getOwnerName() == null || "".equals(imgAsset.getOwnerName())){
                imgTextAssets = imgTextAssetDao.selectListAll(paramMap);
            }else{
                imgTextAssets = imgTextAssetDao.selectListByName(paramMap);
            }
        }else{
            if(imgAsset.getOwnerName() == null || "".equals(imgAsset.getOwnerName())){
                imgTextAssets = imgTextAssetDao.selectListByType(paramMap);
            }else{
                imgTextAssets = imgTextAssetDao.selectListByNameAndType(paramMap);
            }
        }
        return imgTextAssets;
    }


    //Todo:如果要修改在这里做一个验证修改就可以
    private BaseOutput constructBaseOutput(List<Map<String,Object>> imgTextAssets) {
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(0);
        baseOutput.setMsg("success");
        baseOutput.setTotal(imgTextAssets.size());
        for(Map<String,Object> map: imgTextAssets){
            map.put("imgtext_id",map.remove("id"));
            map.put("imgtext_name",map.remove("name"));
            map.put("imgtext_type",map.remove("type"));
            baseOutput.getData().add(map);
        }
        return baseOutput;
    }

	@Override
	public BaseOutput getImgTextAssetByName(String pubId, String name) {
		BaseOutput output = this.newSuccessBaseOutput();
		ImgTextAsset imgTextAsset = new ImgTextAsset();
		imgTextAsset.setPubId(pubId);
		if(StringUtils.isNotEmpty(name)){
			imgTextAsset.setName(name);
		}				
		imgTextAsset.setFirstAsset(NumUtil.int2OneByte(1));
		List<ImgTextAsset> imgTextAssets = imgTextAssetDao.selectList(imgTextAsset);
		List<ImgTextAssetVo> imgTextAssetVos = this.getImgTextAssetVos(imgTextAssets);
		this.setBaseOut(output, imgTextAssetVos);
		return output;
	}

    private List<ImgTextAssetVo> getImgTextAssetVos(List<ImgTextAsset> imgTextAssets){
    	List<ImgTextAssetVo> imgTextAssetVos = new ArrayList<ImgTextAssetVo>();
    	if(CollectionUtils.isNotEmpty(imgTextAssets)){
    		for(ImgTextAsset imgTextAsset:imgTextAssets){
    			ImgTextAssetVo imgTextAssetVo = this.getImgTextAssetVo(imgTextAsset);
    			imgTextAssetVos.add(imgTextAssetVo);
    		}    		
    	}
		return imgTextAssetVos;
    	
    }
    
    private ImgTextAssetVo getImgTextAssetVo(ImgTextAsset imgTextAsset){
    	if(imgTextAsset!=null){
    		ImgTextAssetVo imgTextAssetVo = new ImgTextAssetVo();
    		if(imgTextAsset.getCreateTime()!=null){
    			imgTextAssetVo.setCreate_time(imgTextAsset.getCreateTime().getTime());
    		}    		
    		imgTextAssetVo.setImgfile_name(imgTextAsset.getImgfileName());
    		imgTextAssetVo.setImgfile_url(imgTextAsset.getImgfileUrl());
    		imgTextAssetVo.setImgtext_id(imgTextAsset.getId());
    		imgTextAssetVo.setImgtext_name(imgTextAsset.getName());
    		imgTextAssetVo.setImgtext_type(imgTextAsset.getType());
    		imgTextAssetVo.setMobile_preview_url(imgTextAsset.getMobilePreviewUrl());
    		imgTextAssetVo.setOwner_name(imgTextAsset.getOwnerName());
    		imgTextAssetVo.setPc_preview_url(imgTextAsset.getPcPreviewUrl());
    		imgTextAssetVo.setThumb_ready(imgTextAsset.getThumbReady());
    		return imgTextAssetVo;
    	}
		return null;
    	
    }
    
	private BaseOutput newSuccessBaseOutput() {
		return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
	}
	
	private <O> void setBaseOut(BaseOutput out, List<O> dataList) {
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}
		out.setTotal(dataList.size());
		out.getData().addAll(dataList);
	}
}

