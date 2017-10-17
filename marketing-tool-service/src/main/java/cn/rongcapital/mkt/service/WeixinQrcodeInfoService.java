package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface WeixinQrcodeInfoService {
	BaseOutput getWeiXinQrocdeInfo(String qrcodeId);
}
