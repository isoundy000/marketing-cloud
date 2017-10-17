package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.QrcodePicDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 下载单个微信二维码图片文件
 * Created by zhaoguoying on 2016-08-12.
 */
@Service
public class QrcodePicDownloadServiceImpl implements QrcodePicDownloadService {

	@Autowired
	WechatQrcodeDao wechatQrcodeDao;

	@Override
	public BaseOutput getQrcodePicDownload(Integer qrcodeId) {

		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setId(qrcodeId);

		List<WechatQrcode> wechatQrcodes = wechatQrcodeDao.selectList(wechatQrcode);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (CollectionUtils.isNotEmpty(wechatQrcodes)) {
			result.setTotal(wechatQrcodes.size());
			
			for (WechatQrcode w : wechatQrcodes) {
				Map<String, Object> wechatQrcodetMap = new HashMap<String, Object>();
				wechatQrcodetMap.put("qrcode_pic1", ApiConstant.RETURN_IMG_PATH_LARGE + w.getQrcodePic());
				wechatQrcodetMap.put("qrcode_pic2", ApiConstant.RETURN_IMG_PATH_MIDDLE + w.getQrcodePic());
				wechatQrcodetMap.put("qrcode_pic3", ApiConstant.RETURN_IMG_PATH_SMALL + w.getQrcodePic());
				wechatQrcodetMap.put("qrcode_url", w.getQrcodeUrl());
				result.getData().add(wechatQrcodetMap);
			}
		}
		return result;
	}
}
