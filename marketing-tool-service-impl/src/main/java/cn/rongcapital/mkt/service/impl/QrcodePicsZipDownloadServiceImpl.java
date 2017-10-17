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
import cn.rongcapital.mkt.service.QrcodePicsZipDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 下载批量生成的二维码图片文件(zip)
 * Created by zhaoguoying on 2016-08-12.
 */
@Service
public class QrcodePicsZipDownloadServiceImpl implements QrcodePicsZipDownloadService {

	@Autowired
	WechatQrcodeDao wechatQrcodeDao;
	
	@Override
	public BaseOutput getQrcodePicsZipDownload(String batchId) {
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setBatchId(batchId);
		
		List<WechatQrcode> wechatQrcodes = wechatQrcodeDao.selectList(wechatQrcode);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		if (CollectionUtils.isNotEmpty(wechatQrcodes)) {
			
			
			Map<String, Object> wechatQrcodetMap = new HashMap<String, Object>();
			wechatQrcodetMap.put("qrcode_pic1_zip", ApiConstant.RETURN_IMG_PATH_LARGE + batchId + ".zip");
			wechatQrcodetMap.put("qrcode_pic2_zip", ApiConstant.RETURN_IMG_PATH_MIDDLE + batchId + ".zip");
			wechatQrcodetMap.put("qrcode_pic3_zip", ApiConstant.RETURN_IMG_PATH_SMALL + batchId + ".zip");
			result.getData().add(wechatQrcodetMap);
			result.setTotal(1);
			
		} else {
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		}
		
		
		return result;
	}

}
