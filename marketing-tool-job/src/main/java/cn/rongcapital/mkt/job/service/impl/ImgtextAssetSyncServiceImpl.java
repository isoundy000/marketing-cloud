package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.biz.ImgTextAssetBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.H5ImgtextType;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5TuwenSyncResponse;
import cn.rongcapital.mkt.job.vo.in.MaterialContent;
import cn.rongcapital.mkt.job.vo.in.WTuwen;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WechatRegister;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-7-8.
 */
@Service
public class ImgtextAssetSyncServiceImpl implements TaskService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ImgTextAssetDao imgTextAssetDao;
    @Autowired
    TenementDao tenementDao;
    @Autowired
    WechatRegisterDao wechatRegisterDao;
    @Autowired
    WebchatAuthInfoDao webchatAuthInfoDao;
    @Autowired
    ImgTextAssetBiz imgTextAssetBiz;
    
    

    @Override
    public void task(Integer taskId) {
    	this.syncImgtextAsset();        
    }
    
    public void syncImgtextAsset(){
    	List<WebchatAuthInfo> selectListByIdList = webchatAuthInfoDao.selectList(new WebchatAuthInfo());
    	if (!CollectionUtils.isEmpty(selectListByIdList)) {
    		for (WebchatAuthInfo info : selectListByIdList) {
    			WechatRegister wechatRegister = new WechatRegister();
    			wechatRegister.setAppId(info.getAuthorizerAppid());
    			List<WechatRegister> wechatRegisterLists = wechatRegisterDao.selectList(wechatRegister);
    			
    			if(wechatRegisterLists != null && wechatRegisterLists.size() > 0) {
    				wechatRegister = wechatRegisterLists.get(0);
    			} else {
    				logger.debug("在wechat_register表中根据app_id查不到信息，app_id = {}", info.getAuthorizerAppid());
    				//continue;
    			}
    			
    			List<ImgTextAsset> imgTextAssetLists = imgTextAssetBiz.getMaterialList(info.getAuthorizerAppid(),
    					info.getAuthorizerRefreshToken(),"news");
    			
    			if(!CollectionUtils.isEmpty(imgTextAssetLists)) {
    				/**
					 * 更新微信公众号下图文信息为删除状态
					 */
    				imgTextAssetDao.batchDeleteWechatStatusByPubId(wechatRegister.getWxAcct());
    				for(ImgTextAsset imgTextAsset : imgTextAssetLists) {   					
    					// 设置pub_id,pub_name,下载状态
    					imgTextAsset.setPubId(wechatRegister.getWxAcct());
    					imgTextAsset.setPubName(wechatRegister.getName());
    					imgTextAsset.setThumbReady((byte)0);
    					/**
    					 * 下载微信公众号的图文信息的缩略图到本地，页面显示的时候直接引用本地图文
    					 */
    					try {
							imgTextAsset = getFileByImgTextAsset(imgTextAsset);
						} catch (Exception e) {
							logger.debug("下载图文素材的缩略图, imgTextAsset。imgfileUrl = {}",
									imgTextAsset.getImgfileUrl());							
						}
    					/**
    					 * 插入、更新数据
    					 */
						imgTextAssetDao.insertWithDate(imgTextAsset);
    				}
    			} else {
    				logger.debug("查不到图文信息, AuthorizerAppid = {}, AuthorizerRefreshToken = {}",
    						info.getAuthorizerAppid(), info.getAuthorizerRefreshToken());
    			}
    		}
		}
    }   
    
    private ImgTextAsset getFileByImgTextAsset(ImgTextAsset imgTextAsset) throws Exception{
    	if(imgTextAsset!=null&&StringUtils.isNotEmpty(imgTextAsset.getImgfileUrl())){
    		String imgfileUrl = imgTextAsset.getImgfileUrl();
    		if(StringUtils.isNotEmpty(imgfileUrl)){
        		String[] imgfileUrls = imgfileUrl.split(File.separator);
        		String imgfileName = imgfileUrls[imgfileUrls.length-2];
        		String[] imgfileUrl1s = imgfileUrl.split("=");
        		String imgfileType = imgfileUrl1s[1];
        		imgfileName = imgfileName+"."+imgfileType;
        		imgTextAsset.setImgfileName(ApiConstant.WEIXIN_MATERIAL_IMG_PATH_TO_SHOW+imgfileName); 
            	FileUtil.download(imgTextAsset.getImgfileUrl(),ApiConstant.WEIXIN_MATERIAL_IMG_PATH,imgfileName);
    		}
    	}
		return imgTextAsset;
    }
    
    
    public void callH5PlusMethod(Integer taskId) {
    	
    	Map<String,String> h5ParamMap = new HashMap<String,String>();
        H5TuwenSyncResponse h5TuwenSyncResponse = null;
        Map<String,String> pidMap = tenementDao.selectPid();
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_WUWEN_SYNC);
        h5ParamMap.put("pid",pidMap.get("pid"));
        h5ParamMap.put("page_size",ApiConstant.IMGTEXT_SYNC_SIZE + "");
        h5ParamMap.put("page_num","1");
        h5ParamMap.put("sync_mp","true");
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_sync_response");
                if(obj != null){
                    h5TuwenSyncResponse = JSON.parseObject(obj.toString(),H5TuwenSyncResponse.class);
                    if(h5TuwenSyncResponse != null){
                        Integer totalNumber = h5TuwenSyncResponse.getTotal();
                        if(totalNumber != null){
                            syncImgtextByPageNum(h5ParamMap, totalNumber);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    	
    }

    private void syncImgtextByPageNum(Map<String, String> h5ParamMap, Integer totalNumber) {
        HttpResponse httpResponse;
        JSONObject obj;
        H5TuwenSyncResponse h5TuwenSyncResponse;
        for(int pageNumber = totalNumber/ApiConstant.IMGTEXT_SYNC_SIZE +1; pageNumber > 0; pageNumber --){
            h5ParamMap.put("page_num",pageNumber + "");
            httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
            if(httpResponse != null){
                try {
                    obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_sync_response");
                    if(obj != null){
                        h5TuwenSyncResponse = JSON.parseObject(obj.toString(),H5TuwenSyncResponse.class);
                        batchSyncImgtext(h5TuwenSyncResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void batchSyncImgtext(H5TuwenSyncResponse h5TuwenSyncResponse) {
        List<ImgTextAsset> imgTextAssetInsertList = new ArrayList<ImgTextAsset>();
        List<ImgTextAsset> imgTextAssetUpdateList = new ArrayList<ImgTextAsset>();
        for(WTuwen wtuwen : h5TuwenSyncResponse.getWtuwens().getWtuwen()){
            if(wtuwen.getPubId() == null || wtuwen.getMaterialId() == null || !checkChannelType(wtuwen.getChannelType()) || wtuwen.getMaterialContents() == null ||CollectionUtils.isEmpty(wtuwen.getMaterialContents().getMaterialContent())) continue;
            if(isIllegalImgtext(wtuwen.getPubId())) continue;
            MaterialContent materialContent = wtuwen.getMaterialContents().getMaterialContent().get(0);
            ImgTextAsset imgTextAsset = new ImgTextAsset();
            if(isAlreadySync(wtuwen.getMaterialId())){
                //已经导入的图文进行图文更新，通过Id
                constructLegalImagetextAsset(wtuwen, materialContent, imgTextAsset);
                Integer updateId = imgTextAssetDao.selectImgtextIdByMaterialId(Integer.toString(wtuwen.getMaterialId()));
                imgTextAsset.setId(updateId);
                imgTextAssetUpdateList.add(imgTextAsset);
            }else{
                //没有导入的图文进行图文插入，通过materialId做唯一标识
                constructLegalImagetextAsset(wtuwen, materialContent, imgTextAsset);
                imgTextAssetInsertList.add(imgTextAsset);
            }
        }

        if(!CollectionUtils.isEmpty(imgTextAssetInsertList)){
            for(ImgTextAsset imgTextAsset : imgTextAssetInsertList){
                imgTextAssetDao.insert(imgTextAsset);
            }
        }

        if(!CollectionUtils.isEmpty(imgTextAssetUpdateList)){
            for(ImgTextAsset imgTextAsset : imgTextAssetUpdateList){
                imgTextAssetDao.updateById(imgTextAsset);
            }
        }
    }

    private boolean isIllegalImgtext(String pubId) {
        WechatRegister wechatRegister = new WechatRegister();
        wechatRegister.setWxAcct(pubId);
        Integer count = wechatRegisterDao.selectListCount(wechatRegister);
        if(count == null || count == 0) return true;
        return false;
    }

    private void constructLegalImagetextAsset(WTuwen wtuwen, MaterialContent materialContent, ImgTextAsset imgTextAsset) {
        imgTextAsset.setWechatStatus(wtuwen.getStatus().byteValue());
        if(wtuwen.getStatus() == 1){
            imgTextAsset.setStatus(new Integer(0).byteValue());
        }else if(wtuwen.getStatus() == 0){
            imgTextAsset.setStatus(new Integer(1).byteValue());
        }
        if(materialContent.getTitle() != null){
            imgTextAsset.setName(materialContent.getTitle().replaceAll("[^\\u0000-\\uFFFF]", ""));
        }
        imgTextAsset.setImgfileUrl(materialContent.getScreenshotUrl());
        imgTextAsset.setPubId(wtuwen.getPubId());
        if(wtuwen.getPubName() != null){
            imgTextAsset.setPubName(wtuwen.getPubName().replaceAll("[^\\u0000-\\uFFFF]", ""));
        }
        imgTextAsset.setMaterialId(Integer.toString(wtuwen.getMaterialId()));
        imgTextAsset.setCreateTime(DateUtil.getDateFromString(wtuwen.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        imgTextAsset.setMobilePreviewUrl(materialContent.getMobileUrl());
        imgTextAsset.setShowCoverPic(materialContent.getShowCoverPic().byteValue());
        imgTextAsset.setPcPreviewUrl(materialContent.getPcUrl());
        imgTextAsset.setThumbReady(materialContent.getThumbReady().byteValue());
        if(wtuwen.getChannelType() != null){
//            switch (wtuwen.getChannelType()){
//                case 1:
                    imgTextAsset.setType(ApiConstant.WECHAT_IMGTEXT_TYPE);
                    imgTextAsset.setOwnerName(wtuwen.getPubName());
//                    break;
//                case 2:
//                    imgTextAsset.setType(H5ImgtextType.YI_QI_XIU.getType().byteValue());
//                    imgTextAsset.setOwnerName(H5ImgtextType.YI_QI_XIU.getOwnerName());
//                    break;
//                case 3:
//                    imgTextAsset.setType(H5ImgtextType.TU_ZHAN.getType().byteValue());
//                    imgTextAsset.setOwnerName(H5ImgtextType.TU_ZHAN.getOwnerName());
//                    break;
//                case 4:
//                    imgTextAsset.setType(H5ImgtextType.MAKA.getType().byteValue());
//                    imgTextAsset.setOwnerName(H5ImgtextType.MAKA.getOwnerName());
//                    break;
//            }
        }
    }

    private boolean checkChannelType(Integer channelType) {
        if(channelType != null && channelType >= 1 && channelType <= 4) return true;
        return false;
    }

    private boolean isAlreadySync(Integer materialId) {
        Integer id = imgTextAssetDao.selectImgtextIdByMaterialId(Integer.toString(materialId));
        return id != null;
    }

	@Override
	public void task() {
		this.syncImgtextAsset();
	}
    
}
