package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WechatQrcodeActivateService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class WechatQrcodeActivateServiceImpl implements WechatQrcodeActivateService {

	public final static Byte WECHAT_QRCODE_ACTIVITY= 1;
	
	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;
	
	@Override
	public BaseOutput weChatQrocdeActivate(int id) {
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE,null);		
		WechatQrcode wechatQrcode = new WechatQrcode();		
		wechatQrcode.setId(id);		
		wechatQrcode.setStatus(WECHAT_QRCODE_ACTIVITY);		
		int count = wechatQrcodeDao.updateStatusById(wechatQrcode);
		
		if(count<=0) {
			result.setTotal(0);
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		} else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("status", WECHAT_QRCODE_ACTIVITY);
			result.getData().add(map);
			result.setTotalCount(count);
		}
		
		return result;	
	}

		
	
	
}
