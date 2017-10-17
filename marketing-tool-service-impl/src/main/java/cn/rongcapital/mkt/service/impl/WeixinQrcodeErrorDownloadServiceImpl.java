/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeLogDao;
import cn.rongcapital.mkt.po.WechatQrcodeLog;
import cn.rongcapital.mkt.service.WeixinQrcodeErrorDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinQrcodeErrorDownloadServiceImpl implements WeixinQrcodeErrorDownloadService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WechatQrcodeLogDao wechatQrcodeLogDao;
	
	/**
	 * 下载导入失败的数据
	 * 接口： mkt.weixin.qrcode.error.download
	 * @author shuiyangyang
	 * @Date 2016.08.26
	 */
	@Override
	public BaseOutput weixinQrcodeErrorDownload(String batchId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE,null);
		
		WechatQrcodeLog wechatQrcodeLog = new WechatQrcodeLog();
		wechatQrcodeLog.setBatchId(batchId);
		
		List<WechatQrcodeLog> wechatQrcodeLogLists = wechatQrcodeLogDao.selectList(wechatQrcodeLog);
		
		if(wechatQrcodeLogLists != null && !wechatQrcodeLogLists.isEmpty()) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("batch_error_file", "/downloads/" + batchId + "_error.xlsx");
			result.getData().add(map);
		} else {
			result.setTotal(0);
//			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
//			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
			logger.info("数据在数据库中不存在, batch_id = {}", batchId);
		}
		
		return result;
	}

}
