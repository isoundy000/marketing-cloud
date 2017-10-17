/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeScanDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeScan;
import cn.rongcapital.mkt.service.WeixinAnalysisQrcodeScanService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeScanIn;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinAnalysisQrcodeScanServiceImpl implements WeixinAnalysisQrcodeScanService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WechatQrcodeScanDao wechatQrcodeScanDao;

	@Autowired
	WechatQrcodeDao wechatQrcodeDao;
	
	/**
	 * 保存扫描微信二维码次数和人数 接口：mkt.weixin.analysis.qrcode.scan
	 * 
	 * @author shuiyangyang
	 * @date 2016.09.01
	 */
	@Override
	public BaseOutput instertToWechatQrcodeScan(WechatQrcodeScanIn body) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		WechatQrcodeScan wechatQrcodeScan = new WechatQrcodeScan();
		Integer qrcodeId = Integer.valueOf(body.getQrcodeId());
		
		//获取渠道,公众号信息
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setId(qrcodeId);
		List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcode);
		if(wechatQrcodeList != null && wechatQrcodeList.size() > 0 && wechatQrcodeList.get(0) != null){
			
			wechatQrcode = wechatQrcodeList.get(0);
			wechatQrcodeScan.setChCode(wechatQrcode.getChCode());
			wechatQrcodeScan.setWxName(wechatQrcode.getWxName());
			wechatQrcodeScan.setWxAcct(wechatQrcode.getWxAcct());
		}
		
		wechatQrcodeScan.setUserId(body.getUserId());
		wechatQrcodeScan.setUserHost(body.getUserHost());
		wechatQrcodeScan.setQrcodeId(qrcodeId);
		wechatQrcodeScan.setCreateTime(new Date());

		int num = wechatQrcodeScanDao.insert(wechatQrcodeScan);

		if (num != 1) {
			//result.setCode(ApiErrorCode.DB_ERROR.getCode());
			result.setMsg("插入数据失败");
			logger.info("插入数据失败userId = {}, userHost = {}, qrcodeId = {}", body.getUserId(), body.getUserHost(), body.getQrcodeId());
		}
		return result;
	}

    @Override
    public void instertToWechatQrcodeScan(String openId, WechatQrcode wechatQrcode) {
        WechatQrcodeScan wechatQrcodeScan = new WechatQrcodeScan();
        //获取渠道,公众号信息
        wechatQrcodeScan.setChCode(wechatQrcode.getChCode());
        wechatQrcodeScan.setWxName(wechatQrcode.getWxName());
        wechatQrcodeScan.setWxAcct(wechatQrcode.getWxAcct());        
        wechatQrcodeScan.setUserId(openId);
        wechatQrcodeScan.setQrcodeId(wechatQrcode.getId());
        wechatQrcodeScan.setCreateTime(new Date());

        int num = wechatQrcodeScanDao.insert(wechatQrcodeScan);

        if (num != 1) {
            //result.setCode(ApiErrorCode.DB_ERROR.getCode());
            logger.info("插入数据失败userId = {}, qrcodeId = {}", openId,  wechatQrcode.getId());
        }

        
    }

	
	
}
