package cn.rongcapital.mkt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.service.WechatAnalysisDaysListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class WechatAnalysisDaysListServiceImpl implements WechatAnalysisDaysListService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 关注时间数据库字段
	private static final String FOCUS_FIELDNAME = "focus_datetime";
	// 取消关注时间数据库字段
	private static final String UNFOCUS_FIELDNAME = "unfocus_datetime";
	// 按天查询标识
	private static final Integer DAY_FLAG = 1;
	// 按小时查询标识
	private static final Integer HOUR_FLAG = 2;
	// 计算天数
	private static final Long CALCULATE_VARI = 86400000L;
	
	private static final String WX_NAME_ALL = "全部";
	
	private static final String CH_CODE_ALL = "0";

	private static final String SELECT_LAST7 = "last7";
	
	private static final String SELECT_LAST30 = "last30";
	
	@Autowired
	private WechatQrcodeFocusDao wechatQrcodeFocusDao;
	
	/**
	 * 按天统计
	 */
	@Override
	public BaseOutput analysisDaysList(String startDate, String endDate, String daysType, String chCode,
			String wxName,String qrcodeId) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 返回结果集
			Map<String, Object> resultMap = new HashMap<>();
			
			//选择
			if(SELECT_LAST7.equals(daysType)){
				Date eTempDate = new Date();
				Date sTempDate = DateUtil.addDays(eTempDate, -6);
				startDate = DateUtil.getStringFromDate(sTempDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd);
				endDate = DateUtil.getStringFromDate(eTempDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd);
			}else if(SELECT_LAST30.equals(daysType)){
				Date eTempDate = new Date();
				Date sTempDate = DateUtil.addDays(eTempDate, -29);
				startDate = DateUtil.getStringFromDate(sTempDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd);
				endDate = DateUtil.getStringFromDate(eTempDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd);
			}
			
			startDate = startDate+ApiConstant.DATE_FORMAT_CONSTANT_BEGIN;
			endDate = endDate+ ApiConstant.DATE_FORMAT_CONSTANT_END;
			
			// 日期格式化
			Date sDate = DateUtil.getDateFromString(startDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
			Date eDate = DateUtil.getDateFromString(endDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
			if(sDate.compareTo(eDate) == 1){
				baseOutput.setCode(1003);
				baseOutput.setMsg("日期参数有误！");
				return baseOutput;
			}
			// 计算数组长度
			long s = sDate.getTime() / CALCULATE_VARI;
			long e = eDate.getTime() / CALCULATE_VARI;
			// 数组长度
			int arrayLenth = (int) (e - s) + 1;
			// 参数数组定义
			String[] dateArray = new String[arrayLenth]; // 查询日期
			Integer[] focusCountArray = new Integer[arrayLenth]; // 关注数量
			Integer[] unFocusCountArray = new Integer[arrayLenth]; // 取消关注数量
			Integer[] creFocusCountArray = new Integer[arrayLenth]; // 净增关注数量
			// 临时日期变量
			Date tempDate = sDate;
			// 数量记录
			int i = 0;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(tempDate);
			while (tempDate.compareTo(eDate) <= 0) {
				Integer foucusCount = getCunt(tempDate, FOCUS_FIELDNAME, chCode, wxName,qrcodeId, DAY_FLAG); // 关注数量
				Integer creFocusCount = getCunt(tempDate, UNFOCUS_FIELDNAME, chCode, wxName,qrcodeId, DAY_FLAG);// 净关注数量				
				Integer unFocusCount = foucusCount - creFocusCount; // 取消关注数量				
				focusCountArray[i] = foucusCount;
				unFocusCountArray[i] = unFocusCount;
				creFocusCountArray[i] = creFocusCount;
				dateArray[i] = sdf.format(tempDate);
				// 向后加一天
				calendar.add(Calendar.DATE, 1);
				tempDate = calendar.getTime();
				i++;
			}
			List<Map<String, Object>> paramList = new ArrayList<>();
			paramList.add(getMap("新增关注", ArrayUtils.subarray(focusCountArray, 0, i)));
			paramList.add(getMap("流失关注", ArrayUtils.subarray(unFocusCountArray, 0, i)));
			paramList.add(getMap("净增关注", ArrayUtils.subarray(creFocusCountArray, 0, i)));
			resultMap.put("series", paramList);
			resultMap.put("date", ArrayUtils.subarray(dateArray, 0, i));
			resultMap.put("days_type", daysType);
			resultMap.put("wx_name", wxName);
			resultMap.put("ch_code", chCode);
			resultMap.put("qrcode_id", qrcodeId);
			baseOutput.getData().add(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseOutput;
	}

	
	/**
	 * 按小时统计
	 */
	@Override
	public BaseOutput analysisHoursList(String date, String chCode, String wxName,String qrcodeId) {
		Calendar calendar = Calendar.getInstance();
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			
			// 参数数组定义
			String[] dateArray = new String[24]; // 查询日期
			Integer[] focusCountArray = new Integer[24]; // 关注数量
			Integer[] unFocusCountArray = new Integer[24]; // 取消关注数量
			Integer[] creFocusCountArray = new Integer[24]; // 净增关注数量
			// 返回结果集
			Map<String, Object> resultMap = new HashMap<>();

			Map<String, Date> dateMap = getDate(date);
			// 日期参数（yMdH)
			Date hourDate = dateMap.get("hourDate");
			// 日期参数（yMd)
			Date dayDate = dateMap.get("dayDate");
			// 当前日期（yMd)
			Date nowDayDate = dateMap.get("nowDayDate");
			// 当前日期（yMdH)
			Date nowhourDate = dateMap.get("nowhourDate");
			//超出日期范围返回
			calendar.setTime(nowDayDate);
			calendar.add(Calendar.DATE, -1);
			Date yesterday  = calendar.getTime();
			if(dayDate.compareTo(yesterday) == -1 || dayDate.compareTo(nowDayDate) == 1){
				baseOutput.setCode(1003);
				baseOutput.setMsg("日期超出范围");
				return baseOutput;
			}
			// 今天0点(ymd 00)
			Date todayZero = dateMap.get("todayZero");
			calendar.setTime(todayZero);
			calendar.add(Calendar.HOUR_OF_DAY, -1);
			todayZero = calendar.getTime();

			// 判断今天昨天,值为-1为昨天，否则为今天
			int compareValue = dayDate.compareTo(nowDayDate);
			Date tempDate = hourDate;
			int i = 0;
			Date maxDate = compareValue == -1 ? todayZero : nowhourDate;
			while (tempDate.compareTo(maxDate) <= 0) {
				Integer foucusCount = getCunt(tempDate, FOCUS_FIELDNAME, chCode, wxName,qrcodeId, HOUR_FLAG); // 关注数量
				Integer creFocusCount = getCunt(tempDate, UNFOCUS_FIELDNAME, chCode, wxName,qrcodeId, HOUR_FLAG);// 净关注数量				
				Integer unFocusCount = foucusCount - creFocusCount; // 取消关注数量
				focusCountArray[i] = foucusCount;
				unFocusCountArray[i] = unFocusCount;
				creFocusCountArray[i] = creFocusCount;
				dateArray[i] = sdf.format(tempDate);
				calendar.setTime(tempDate);
				calendar.add(Calendar.HOUR_OF_DAY, 1);
				tempDate = calendar.getTime();
				i++;
			}

			List<Map<String, Object>> paramList = new ArrayList<>();
			paramList.add(getMap("新增关注", ArrayUtils.subarray(focusCountArray, 0, i)));
			paramList.add(getMap("流失关注", ArrayUtils.subarray(unFocusCountArray, 0, i)));
			paramList.add(getMap("净增关注", ArrayUtils.subarray(creFocusCountArray, 0, i)));
			resultMap.put("series", paramList);
			resultMap.put("date", ArrayUtils.subarray(dateArray, 0, i));
			resultMap.put("wx_name", wxName);
			resultMap.put("ch_code", chCode);
			resultMap.put("qrcode_id", qrcodeId);
			baseOutput.getData().add(resultMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseOutput;
	}

	/**
	 * 获取不同格式的日期
	 */
	private Map<String, Date> getDate(String strDate) {
		Map<String, Date> dateMap = new HashMap<>();
		try {
			SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH");
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dayDate = dayFormat.parse(strDate);
			dateMap.put("dayDate", dayDate);
			dateMap.put("hourDate", hourFormat.parse(hourFormat.format(dayDate)));
			Date nowDayDate = dayFormat.parse(dayFormat.format(new Date()));
			dateMap.put("nowDayDate", nowDayDate);
			dateMap.put("nowhourDate", hourFormat.parse(hourFormat.format(new Date())));
			dateMap.put("todayZero", hourFormat.parse(hourFormat.format(nowDayDate)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateMap;
	}

	/**
	 * 获取相应数量
	 */
	private Integer getCunt(Date searchDate, String fieldName, String chCode, String wxName,String qrcodeId, Integer flag) {
		String sqlField = flag == DAY_FLAG ? "searchDate" : "searchHours";
		// 参数集合
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(sqlField, searchDate);
		paramMap.put("fieldName", fieldName);
		
		if(!CH_CODE_ALL.equals(chCode) && ! "".equals(chCode)){
			paramMap.put("chCode", chCode);
		}
		
		if(!WX_NAME_ALL.equals(wxName) && ! "".equals(wxName)){
			paramMap.put("wxName", wxName);
		}
		
		if(!"".equals(qrcodeId)){
			paramMap.put("qrcodeId", qrcodeId);
		}
		logger.info(paramMap.toString());
		if(FOCUS_FIELDNAME.equals(fieldName)) {
		    return wechatQrcodeFocusDao.getFocusCount(paramMap);
		} 	
		else if(UNFOCUS_FIELDNAME.equals(fieldName)) {   //当输入取消关注时间时，先计算净关注数，再通过关注-净关注=取消关注
		    return wechatQrcodeFocusDao.getNetFocusCount(paramMap);   
		}
		return 0;
	}

	/**
	 * 封装参数集合 
	 */
	private Map<String, Object> getMap(String value1, Object value2) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", value1);
		map.put("data", value2);
		return map;
	}

}
