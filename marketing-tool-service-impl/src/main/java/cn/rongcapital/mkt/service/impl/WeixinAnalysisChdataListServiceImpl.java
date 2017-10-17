/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.dao.WechatQrcodeScanDao;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.service.WeixinAnalysisChdataListService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinAnalysisChdataListServiceImpl implements WeixinAnalysisChdataListService {

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
	WechatAssetDao wechatAssetDao;

	/**
	 * 按公众号和渠道,以及时间区间内获取关注数据(扫码、关注、新增...), 支持分页
	 * 接口：mkt.weixin.analysis.chdata.list
	 * 
	 * @author shuiyangyang
	 * @date 2016.09.01
	 */
	@Override
	public BaseOutput getAnalysisChdata(String wxName, String chCode, String qrcodeId, String startDate,
			String endDate) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		logger.info("二维码分析列表数据获取:" + "wxName=" + wxName + "chCode=" + chCode + "qrcodeId=" + qrcodeId + "startDate="
				+ startDate + "endDate=" + endDate);
		Map<String, Object> paraMap = new HashMap<String, Object>();

		// 渠道Id
		if (!"0".equals(chCode)) {
			paraMap.put("chCode", chCode);
		}
		List<String> list = new ArrayList<String>();
		// 公众号，这变量传的name，code不分....
		if (!"0".equals(wxName)) {
			list.add(wxName);
			paraMap.put("wxAcctlist", list);
		}
		if ("0".equals(wxName)) {
			WechatAsset wechatAsset = new WechatAsset();
			wechatAsset.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			List<WechatAsset> selectList = wechatAssetDao.selectList(wechatAsset);
			if (CollectionUtils.isNotEmpty(selectList)) {

				for (WechatAsset wAsset : selectList) {
					list.add(wAsset.getWxAcct());
				}
				paraMap.put("wxAcctlist", list);
			}

		}
		// 微信二维码id
		if (!"".equals(qrcodeId)) {
			paraMap.put("qrcodeId", qrcodeId);
		}
		// 起始时间
		startDate = startDate + ApiConstant.DATE_FORMAT_CONSTANT_BEGIN;
		endDate = endDate + ApiConstant.DATE_FORMAT_CONSTANT_END;
		paraMap.put("startTime", DateUtil.getDateFromString(startDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		paraMap.put("endTime", DateUtil.getDateFromString(endDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));

		List<Map<String, Object>> allFocusDataLists = wechatQrcodeFocusDao.getAllFocusData(paraMap);

		if (allFocusDataLists != null && allFocusDataLists.size() > 0) {
			int total = allFocusDataLists.size();

			for (Map<String, Object> allFocusDataList : allFocusDataLists) {
				logger.info(allFocusDataList.toString());
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("wx_name", allFocusDataList.get("wxName"));
				map.put("ch_name", allFocusDataList.get("chName"));
				map.put("ch_code", allFocusDataList.get("chCode"));
				// 总扫码次数
				map.put("total_scan", allFocusDataList.get("totalScan"));
				// 总扫码人数
				map.put("total_scan_user", allFocusDataList.get("totalScanUser"));
				// 总关注数
				map.put("total_focus", allFocusDataList.get("totalFocus"));
				// 新增关注数
				Integer newFocus = allFocusDataList.get("newFocus") == null ? 0
						: Integer.valueOf(allFocusDataList.get("newFocus").toString());
				map.put("new_focus", newFocus);
				// 流失关注数
				Integer lostFocus = allFocusDataList.get("lostFocus") == null ? 0
						: Integer.valueOf(allFocusDataList.get("lostFocus").toString());
				map.put("lost_focus", lostFocus);
				// 净增关注数
				map.put("add_focus", newFocus - lostFocus);

				result.getData().add(map);
			}
			result.setTotal(total);
		}
		list.clear();
		logger.info("二维码分析列表数据获取成功");
		return result;
	}

}
