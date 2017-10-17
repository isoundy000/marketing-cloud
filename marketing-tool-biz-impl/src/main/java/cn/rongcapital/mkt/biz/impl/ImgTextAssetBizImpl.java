package cn.rongcapital.mkt.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;

import cn.rongcapital.mkt.biz.ImgTextAssetBiz;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.vo.weixin.WXImgText;
import cn.rongcapital.mkt.vo.weixin.WXImgTextContent;
import cn.rongcapital.mkt.vo.weixin.WXNewsItem;

@Service
public class ImgTextAssetBizImpl extends BaseBiz implements ImgTextAssetBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.rongcapital.mkt.biz.ImgTextAssetBiz#getMaterialList(java.lang.String,java.lang.String, long, long) 
	 * 图文列表返回的信息列表  样式
 * 					{
					    "item": [
					        {
					            "media_id": "ZGmSwfoayacvqtasO_W58JMM4Szs9TuUR755HUw3hP8",
					            "content": {
					                "news_item": [
					                    {
					                        "title": "云峰",
					                        "author": "尹恒",
					                        "digest": "小凤仙",
					                        "content": "<p><img data-s=\"300,640\" data-type=\"png\" data-src=\"http://mmbiz.qpic.cn/mmbiz_png/u29ic1uhjXFEibOpNEe0iaqTvWAICDl6Sr23PT1EOZjG1vSjxB00ibEOXa4rM4meyQEdO83jR70vMloBZKmVopwBww/0?wx_fmt=png\" data-ratio=\"1.3511777301927195\" data-w=\"467\"  /><br  />沙发沙发上的发生大发</p>",
					                        "content_source_url": "https://www.baidu.com",
					                        "thumb_media_id": "o0LZ3DfEGwkefJH4UG4Qzob9rJB7vnX_An-F7H4lHLU",
					                        "show_cover_pic": 0,
					                        "url": "http://mp.weixin.qq.com/s?__biz=MzIxMjQ1NDYzMw==&mid=100000002&idx=1&sn=d61b0beef4d945eac34abad2a7f19b03#rd",
					                        "thumb_url": ""
					                    },					                  
					                ],
					                "create_time": 1472544480,
					                "update_time": 1472544516
					            },
					            "update_time": 1472544516
					        }
					    ],
					    "total_count": 1,
					    "item_count": 1
					}
	 */
	@Override
	public List<ImgTextAsset> getMaterialList(String authAppId,String authorizer_refresh_token, String type) {
		App app = this.getApp();
		app.setAuthAppId(authAppId);
		app.setAuthRefreshToken(authorizer_refresh_token);
		
		long materialCountAll = this.getMaterialCount(app);
		if(materialCountAll <= 0) {
			return null;
		}
		
		List<ImgTextAsset> imgTextAssetes = new ArrayList<ImgTextAsset>();
		
		long offset = 0;
		long materialCount = 0;
		while(offset  < materialCountAll) {
			materialCount = materialCountAll - offset;
			if(materialCount > 20) {
				materialCount = 20;
			}
			String materialListStr= WxComponentServerApi.getBaseWxSdk().getMaterialList(app,type,offset,materialCount);
			if(StringUtils.isNotEmpty(materialListStr)){
				/**
				 * 去掉特殊字符 例如表情符等等
				 */
				materialListStr = super.replaceAllUTF8mb4(materialListStr);
				/**
				 * 记入接口日志到数据库
				 */
				WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("ImgTextAssetBizImpl","getMaterialList",materialListStr,new Date());				
				wechatInterfaceLogService.insert(wechatInterfaceLog);
				/**
				 * 组装图文资产
				 */
				imgTextAssetes.addAll(this.getImgTextAssetes(materialListStr));
			}
			offset += materialCount;
		}		
		return imgTextAssetes;
	}
	
	/**
	 * @param materialListStr
	 * 图文列表返回的信息列表  样式
 * 					{
					    "item": [
					        {
					            "media_id": "ZGmSwfoayacvqtasO_W58JMM4Szs9TuUR755HUw3hP8",
					            "content": {
					                "news_item": [
					                    {
					                        "title": "云峰",
					                        "author": "尹恒",
					                        "digest": "小凤仙",
					                        "content": "<p><img data-s=\"300,640\" data-type=\"png\" data-src=\"http://mmbiz.qpic.cn/mmbiz_png/u29ic1uhjXFEibOpNEe0iaqTvWAICDl6Sr23PT1EOZjG1vSjxB00ibEOXa4rM4meyQEdO83jR70vMloBZKmVopwBww/0?wx_fmt=png\" data-ratio=\"1.3511777301927195\" data-w=\"467\"  /><br  />沙发沙发上的发生大发</p>",
					                        "content_source_url": "https://www.baidu.com",
					                        "thumb_media_id": "o0LZ3DfEGwkefJH4UG4Qzob9rJB7vnX_An-F7H4lHLU",
					                        "show_cover_pic": 0,
					                        "url": "http://mp.weixin.qq.com/s?__biz=MzIxMjQ1NDYzMw==&mid=100000002&idx=1&sn=d61b0beef4d945eac34abad2a7f19b03#rd",
					                        "thumb_url": ""
					                    }
					                ],
					                "create_time": 1472544480,
					                "update_time": 1472544516
					            },
					            "update_time": 1472544516
					        }
					    ],
					    "total_count": 1,
					    "item_count": 1
					}
	 * @return
	 */
	public List<ImgTextAsset> getImgTextAssetes(String materialListStr){
		List<ImgTextAsset> list = new ArrayList<ImgTextAsset>();
		JSONObject jsonObject = JSONObject.parseObject(materialListStr);
/*		long total_count = jsonObject.getLong("total_count");
		long item_count = jsonObject.getLong("item_count");
*/		JSONArray jsonArray = jsonObject.getJSONArray("item");
		List<WXImgText> wxImgTextes = JSONArray.parseArray(jsonArray.toJSONString(), WXImgText.class);
		if(wxImgTextes!=null&&wxImgTextes.size()>0){
			for(Iterator<WXImgText> iter = wxImgTextes.iterator();iter.hasNext();){
				WXImgText wxImgText = iter.next();
				if(wxImgText!=null){					
					List<ImgTextAsset> imgTextAssetes = getImgTextAssete(wxImgText);
					list.addAll(imgTextAssetes);
				}				
			}
		}
		return list;		
	}

	public List<ImgTextAsset> getImgTextAssete(WXImgText wxImgText){
		ImgTextAsset imgTextAsset = new ImgTextAsset();
		imgTextAsset.setMaterialId(wxImgText.getMedia_id());		
		imgTextAsset.setUpdateTime(new Date(wxImgText.getUpdate_time() * 1000));
		WXImgTextContent wxImgTextContent = wxImgText.getContent();
		List<ImgTextAsset> imgTextAssets = this.getImgTextAsseteFromWXImgTextContent(imgTextAsset, wxImgTextContent);
		return imgTextAssets;		
	}
	
	public List<ImgTextAsset> getImgTextAsseteFromWXImgTextContent(ImgTextAsset imgTextAsset,WXImgTextContent wxImgTextContent){
		List<ImgTextAsset> imgTextAssets = new ArrayList<ImgTextAsset>();
		List<WXNewsItem> wxNewsItems = wxImgTextContent.getNewsItem();
		imgTextAsset.setCreateTime(new Date(wxImgTextContent.getCreate_time() * 1000));
		if(wxNewsItems!=null&&wxNewsItems.size()>0){
			int i=0;
			for(Iterator<WXNewsItem> iter = wxNewsItems.iterator();iter.hasNext();){
				WXNewsItem wxNewsItem = iter.next();
				if(wxNewsItem!=null){					
					ImgTextAsset imgTextAssetTemp = new ImgTextAsset();
					imgTextAssetTemp.setName(wxNewsItem.getTitle());
					imgTextAssetTemp.setOwnerName(wxNewsItem.getAuthor());
					imgTextAssetTemp.setPcPreviewUrl(wxNewsItem.getUrl());
					imgTextAssetTemp.setMobilePreviewUrl(wxNewsItem.getUrl());
					imgTextAssetTemp.setImgfileUrl(wxNewsItem.getThumb_url());
					imgTextAssetTemp.setMaterialId(imgTextAsset.getMaterialId());
					imgTextAssetTemp.setCreateTime(imgTextAsset.getCreateTime());
					imgTextAssetTemp.setUpdateTime(imgTextAsset.getUpdateTime());
					int show_cover_pic = wxNewsItem.getShow_cover_pic();
					imgTextAssetTemp.setShowCoverPic(NumUtil.int2OneByte(show_cover_pic));					
					imgTextAssetTemp.setWechatStatus(NumUtil.int2OneByte(1));
					imgTextAssetTemp.setType(NumUtil.int2OneByte(0));
					imgTextAssetTemp.setStatus(NumUtil.int2OneByte(0));	
					imgTextAssetTemp.setDigest(wxNewsItem.getDigest());
					if(i==0){
						imgTextAssetTemp.setFirstAsset(NumUtil.int2OneByte(1));
					}else{
						imgTextAssetTemp.setFirstAsset(NumUtil.int2OneByte(0));
					}
					imgTextAssets.add(imgTextAssetTemp);
					i++;
				}				
			}
		}		
		return imgTextAssets;		
	}
	
	public Long getMaterialCount(App app) {
		String materialCountStr = WxComponentServerApi.getBaseWxSdk().getMaterialCount(app);
		/**
		 * 记入接口日志到数据库
		 */
		WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("ImgTextAssetBizImpl","getMaterialCount",materialCountStr,new Date());
		wechatInterfaceLogService.insert(wechatInterfaceLog);
		/**
		 * 组装图文资产总数
		 */

		JSONObject jsonObject = JSONObject.parseObject(materialCountStr);
		long materialCount = jsonObject.getLongValue("news_count");
		return materialCount;
	}
	
}
