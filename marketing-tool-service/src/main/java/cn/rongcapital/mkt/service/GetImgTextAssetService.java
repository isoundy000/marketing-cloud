package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;

/**
 * Created by Yunfeng on 2016-5-27.
 */
public interface GetImgTextAssetService {
    Object getImgTextAssetService(ImgAsset imgAsset);
    
    BaseOutput getImgTextAssetByName(String pubId, String name);
}
