package cn.rongcapital.mkt.biz;

import java.util.List;

import cn.rongcapital.mkt.po.ImgTextAsset;

public interface ImgTextAssetBiz {

	/**
	 * 获取公众号的图文信息
	 * @param authAppId
	 * @param authorizer_refresh_token
	 * @param type
	 * @return
	 */
	public List<ImgTextAsset> getMaterialList(String authAppId,String authorizer_refresh_token,String type);
	
}
