package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;

/**
 * Created by lijinkai on 2016-9-14.
 * 微信资产-公众号图文资产
 */
public interface GetWxImgTextAssetService {
    Object getWxImgTextAssetService(ImgAsset imgAsset);
    /**
     * 获取公众号下的图文资产列表
     * 
     * @param imgTextAsset
     * @return
     * @author shuiyangyang
     * @Date 2016-11-17
     */
    public BaseOutput getWxImgTextAsset(ImgTextAsset imgTextAsset);
}
