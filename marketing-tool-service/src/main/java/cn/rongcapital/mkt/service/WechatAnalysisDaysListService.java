package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
/**
 * @ClassName:  WechatAnalysisDaysListService   
 * @Description:微信公众号关注信息统计接口
 * @author: wangweiqiang
 * @date:   2016年9月1日 下午3:22:40   
 */
public interface WechatAnalysisDaysListService {

	/**
	 * @Title: analysisDaysList   
	 * @Description: 微信公众号关注信息统计   
	 * @param: @param startDate	开始日期
	 * @param: @param endDate	结束日期
	 * @param: @param daysType	时间范围
	 * @param: @param chCode	渠道编码
	 * @param: @param wxName	微信名称
	 * @param: @return      
	 * @return: BaseOutput      
	 * @throws
	 */
	BaseOutput analysisDaysList(String startDate,String endDate,String daysType,String chCode,String wxName,String qrcodeId);
	
	/**
	 * @Title: analysisHoursList   
	 * @Description: 统计时间点的关注信息  
	 * @param: @param date		查询日期
	 * @param: @param chCode	渠道编码
	 * @param: @param wxName	微信名称
	 * @param: @return      
	 * @return: BaseOutput      
	 * @throws
	 */
	BaseOutput analysisHoursList(String date,String chCode,String wxName,String qrcodeId);  
}
