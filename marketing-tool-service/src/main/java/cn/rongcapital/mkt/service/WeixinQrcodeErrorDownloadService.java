/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
public interface WeixinQrcodeErrorDownloadService {
	/**
	 * 下载导入失败的数据
	 * 接口： mkt.weixin.qrcode.error.download
	 * @param batchId
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.26
	 */
	BaseOutput weixinQrcodeErrorDownload(String batchId);
}
