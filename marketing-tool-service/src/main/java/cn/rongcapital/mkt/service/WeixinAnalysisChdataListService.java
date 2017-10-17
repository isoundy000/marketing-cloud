/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
public interface WeixinAnalysisChdataListService {
	/**
	 * 按公众号和渠道,以及时间区间内获取关注数据(扫码、关注、新增...), 支持分页 
	 * 接口：mkt.weixin.analysis.chdata.list
	 * 
	 * @param wxName
	 * @param chCode
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author shuiyangyang
	 * @date 2016.09.01
	 */
	BaseOutput getAnalysisChdata(String wxName, String chCode, String qrcodeId, String startDate, String endDate);
}
