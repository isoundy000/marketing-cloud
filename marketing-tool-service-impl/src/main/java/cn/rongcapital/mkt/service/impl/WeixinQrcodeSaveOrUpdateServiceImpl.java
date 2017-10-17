package cn.rongcapital.mkt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeSaveOrUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeInData;

@Service
public class WeixinQrcodeSaveOrUpdateServiceImpl implements WeixinQrcodeSaveOrUpdateService {

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;
	
	@Autowired
	private WechatChannelDao wechatChannelDao;
	
	@Override
	public BaseOutput weixinSaveOrUpdate(WechatQrcodeInData body) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		String wxAcct = body.getWxAcct();
		String qrcodeName = body.getQrcodeName();
		WechatQrcode wechatQrcodeQuery = new WechatQrcode();
		wechatQrcodeQuery.setWxAcct(wxAcct);
		wechatQrcodeQuery.setQrcodeName(qrcodeName);
		
		//渠道表
		WechatChannel wechatChannel =  new WechatChannel();
		wechatChannel.setId(body.getChCode());
		wechatChannel.setChName(body.getChName());
		List<WechatChannel> wechatChannelList = wechatChannelDao.selectList(wechatChannel);
		
		
		List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcodeQuery);
		result.setTotal(1);
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setWxAcct(wxAcct);
		wechatQrcode.setWxName(body.getWxName() == null ?  "": body.getWxName());
		wechatQrcode.setChCode(body.getChCode() == null ?  0: body.getChCode());
		wechatQrcode.setQrcodeName(qrcodeName);
		String eTime = body.getExpirationTime();
		Date expirationTime = null;
		try {
			expirationTime = sdf.parse(eTime);
		} catch (ParseException e) {
			e.printStackTrace();
			result = new BaseOutput(ApiErrorCode.VALIDATE_ERROR.getCode(), "时间格式化错误",
					ApiConstant.INT_ZERO, null);
			return result;
			
		}
		wechatQrcode.setExpirationTime(expirationTime);
		wechatQrcode.setIsAudience(body.getIsAudience().byteValue());
		wechatQrcode.setAudienceName(body.getAudienceName());
		
		if(wechatQrcodeList != null && !wechatQrcodeList.isEmpty()){
			
			Integer id = wechatQrcodeList.get(0).getId();
			wechatQrcode.setId(id);
			wechatQrcodeDao.updateById(wechatQrcode);
			
		}else {
			wechatQrcodeDao.insert(wechatQrcode);
		}
		
		//更新或者插入channel表
		if(wechatChannelList != null && !wechatChannelList.isEmpty()){
			wechatChannelDao.updateById(wechatChannel);
		}else {
			wechatChannel.setType(1);
			wechatChannel.setIsRemoved(1);
			wechatChannel.setStatus("0");
			wechatChannelDao.insert(wechatChannel);
		}
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", wechatQrcode.getId());
		resultMap.put("wx_name", wechatQrcode.getWxName());
		resultMap.put("ch_name", body.getChName());
		resultMap.put("qrcode_name", wechatQrcode.getQrcodeName());
		resultMap.put("create_time", wechatQrcode.getCreateTime());
		resultMap.put("expiration_time", wechatQrcode.getExpirationTime());
		resultMap.put("audience_name", wechatQrcode.getAudienceName());
		resultMap.put("qrcode_pic", wechatQrcode.getQrcodePic());
		resultMap.put("qrcode_url", wechatQrcode.getQrcodeUrl());

		result.getData().add(resultMap);
		return result;
	}

}
