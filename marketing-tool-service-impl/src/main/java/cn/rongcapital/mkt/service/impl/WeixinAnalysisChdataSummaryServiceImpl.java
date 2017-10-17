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
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.dao.WechatQrcodeScanDao;
import cn.rongcapital.mkt.service.WeixinAnalysisChdataListService;
import cn.rongcapital.mkt.service.WeixinAnalysisChdataSummaryService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinAnalysisChdataSummaryServiceImpl implements WeixinAnalysisChdataSummaryService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WechatQrcodeFocusDao wechatQrcodeFocusDao;

	@Autowired
	WechatChannelDao wechatChannelDao;

	@Autowired
	WechatQrcodeDao wechatQrcodeDao;

	@Autowired
	WechatQrcodeScanDao wechatQrcodeScanDao;
	
	@Autowired
	WeixinAnalysisChdataListService weixinAnalysisChdataListService;

	/**
	 * 获取平均、汇总、历史最高关注数据(扫码、关注、新增...) 
	 * 接口：mkt.weixin.analysis.chdata.summary
	 * @author shuiyangyang
	 */
	@Override
	public BaseOutput getAnalysisChdataSummary(String wxName, String chCode,String qrcodeId, String startDate, String endDate) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ONE, null);
		
		logger.info("二维码分析列获取平均、汇总、历史最高关注数据:"+ "wxName=" + wxName 
				+ "chCode="+chCode + "qrcodeId="+qrcodeId 
				+ "startDate="+startDate + "endDate=" + endDate);
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		//渠道Id
		if(!"0".equals(chCode)) {
			paraMap.put("chCode", chCode);
		}
		//公众号，这变量传的name，code不分....
		if(!"0".equals(wxName)) {
			paraMap.put("wxAcct", wxName);
		}
		//微信二维码id
		if(!"".equals(qrcodeId)) {
			paraMap.put("qrcodeId", qrcodeId);
		}
		
		//起始时间
		startDate = startDate+ApiConstant.DATE_FORMAT_CONSTANT_BEGIN;
		endDate = endDate+ ApiConstant.DATE_FORMAT_CONSTANT_END;
		paraMap.put("startTime", DateUtil.getDateFromString(startDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		paraMap.put("endTime", DateUtil.getDateFromString(endDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		
		List<Map<String, Object>> analysisChdataMap = wechatQrcodeFocusDao.getAllFocusData(paraMap);
		
		logger.info(analysisChdataMap.toString());
		int analysisChdataMapSize = analysisChdataMap.size();
		logger.info(analysisChdataMapSize+"");
		
		long[] analysisChdataInt = { 0, 0, 0, 0, 0 };
		if(analysisChdataMapSize == 0) {
			analysisChdataMapSize = 1; 
		} else {
			for (int i = 0; i < analysisChdataMapSize; i++) {
				Map<String, Object> map = (Map<String, Object>) analysisChdataMap.get(i);
				analysisChdataInt[0] += Integer.valueOf(map.get("totalScan").toString());
				analysisChdataInt[1] += Integer.valueOf(map.get("totalScanUser").toString());
				analysisChdataInt[2] += Integer.valueOf(map.get("totalFocus").toString());
				analysisChdataInt[3] += Integer.valueOf(map.get("newFocus").toString());
				analysisChdataInt[4] += Integer.valueOf(map.get("lostFocus").toString());
				
			}
		}
		
		// 获取总扫码次数最大值
		Map<String, Object> amountScanMax = wechatQrcodeScanDao.getAmountScanMax(paraMap);
		// 获取总扫码人数最大值 
		Map<String, Object> amountScanUserMax = wechatQrcodeScanDao.getamountScanUserMax(paraMap);
		// 获取最大总关注数
		Map<String, Object> amountFocusMax = wechatQrcodeFocusDao.getAmountFocusMax(paraMap);
		//获取最大新关注的信息
		Map<String, Object> newFocusMax = wechatQrcodeFocusDao.getNewFocusMax(paraMap);
		// 获取最大净增关注信息
		Map<String, Object> AddFocusMax = wechatQrcodeFocusDao.getAddFocusMax(paraMap);
		// 获取最大流失关注的信息
		Map<String, Object> lostFocusMax = wechatQrcodeFocusDao.getLostFocusMax(paraMap);
		
		Map<String, Object> Alldata = new HashMap<String, Object>(); 
		//计算平均值
		Map<String, Object> averageData = new HashMap<String, Object>();
		averageData.put("amount_scan", (analysisChdataInt[0] + analysisChdataMapSize/2) / analysisChdataMapSize);
		averageData.put("amount_scan_user", (analysisChdataInt[1] + analysisChdataMapSize/2) / analysisChdataMapSize);
		averageData.put("amount_focus", (analysisChdataInt[2] + analysisChdataMapSize/2) / analysisChdataMapSize);
		averageData.put("new_focus", (analysisChdataInt[3] + analysisChdataMapSize/2) / analysisChdataMapSize);
		averageData.put("add_focus", (analysisChdataInt[3] - analysisChdataInt[4] + analysisChdataMapSize/2) / analysisChdataMapSize);
		averageData.put("lost_focus", (analysisChdataInt[4] + analysisChdataMapSize/2) / analysisChdataMapSize);
		Alldata.put("average", averageData);
		
		logger.info("平均数:"+averageData.toString());
		
		//汇总值
		Map<String, Object> sumData = new HashMap<String, Object>();
		sumData.put("amount_scan", analysisChdataInt[0]);
		sumData.put("amount_scan_user", analysisChdataInt[1]);
		sumData.put("amount_focus", analysisChdataInt[2]);
		sumData.put("new_focus", analysisChdataInt[3]);
		sumData.put("add_focus", analysisChdataInt[3] - analysisChdataInt[4]);
		sumData.put("lost_focus", analysisChdataInt[4]);
		Alldata.put("sum", sumData);
		
		logger.info("汇总数:"+sumData.toString());
		
		Map<String, Object> maxData = new HashMap<String, Object>();
		if(amountScanMax != null) {
			maxData.put("amount_scan", amountScanMax.get("value"));
			maxData.put("amount_scan_date", amountScanMax.get("time"));
		} else {
			maxData.put("amount_scan", "");
			maxData.put("amount_scan_date", "");
		}
		
		if(amountScanUserMax != null) {
			maxData.put("amount_scan_user", amountScanUserMax.get("value"));
			maxData.put("amount_scan_user_date", amountScanUserMax.get("time"));
		} else {
			maxData.put("amount_scan_user", "");
			maxData.put("amount_scan_user_date", "");
		}
		
		if(amountFocusMax != null) {
			maxData.put("amount_focus", amountFocusMax.get("value"));
			maxData.put("amount_focus_date", amountFocusMax.get("time"));
		} else {
			maxData.put("amount_focus", "");
			maxData.put("amount_focus_date", "");
		}
		
		if(newFocusMax != null) {
			maxData.put("new_focus", newFocusMax.get("value"));
			maxData.put("new_focus_date", newFocusMax.get("time"));
		} else {
			maxData.put("new_focus", "");
			maxData.put("new_focus_date", "");
		}
		
		if(AddFocusMax != null) {
			maxData.put("add_focus", AddFocusMax.get("value"));
			maxData.put("add_focus_date", AddFocusMax.get("time"));
		} else {
			maxData.put("add_focus", "");
			maxData.put("add_focus_date", "");
		}
		
		if(lostFocusMax != null) {
			maxData.put("lost_focus", lostFocusMax.get("value"));
			maxData.put("lost_focus_date", lostFocusMax.get("time"));
		} else {
			maxData.put("lost_focus", "");
			maxData.put("lost_focus_date", "");
		}
		
		Alldata.put("max", maxData);
		logger.info("最大数:"+maxData.toString());
		
		result.getData().add(Alldata);
		
		return result;
	}
	
	
 
}
