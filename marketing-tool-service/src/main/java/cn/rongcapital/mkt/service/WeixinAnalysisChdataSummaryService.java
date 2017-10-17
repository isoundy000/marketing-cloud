/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
public interface WeixinAnalysisChdataSummaryService {
	/**
	 * 获取平均、汇总、历史最高关注数据(扫码、关注、新增...) 
	 * 接口：mkt.weixin.analysis.chdata.summary
	 * @param wxName
	 * @param chCode
	 * @param qrcodeId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author shuiyangyang
	 */
	BaseOutput getAnalysisChdataSummary(String wxName, String chCode,String qrcodeId, String startDate, String endDate);
}
