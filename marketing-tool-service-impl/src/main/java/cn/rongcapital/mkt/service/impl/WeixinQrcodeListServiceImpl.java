package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.service.WeixinQrcodeListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class WeixinQrcodeListServiceImpl implements WeixinQrcodeListService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;

	@Autowired
	private WechatChannelDao wechatChannelDao;

	@Autowired
	private WechatQrcodeFocusDao wechatQrcodeFocusDao;
	
	@Autowired
	private TagDao tagDao;

	/**
	 * 根据公众号名称、失效时间、状态、二维码名称查询二维码列表
	 * 接口：mkt.weixin.qrcode.list 
	 * @author shuiyangyang
	 * @Data 2016.08.19
	 */
	@Override
	public BaseOutput getWeixinQrcodeList(String wxmpName, Integer expirationTime, Byte qrcodeStatus, int index, int size) {
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		if(wxmpName != null && !wxmpName.isEmpty() && !wxmpName.equals("0")) {
			paramMap.put("wxAcct", wxmpName);
		}
		
		if(expirationTime != null) {
			paramMap.put("expirationTimeStatus", expirationTime);//查询状态
			paramMap.put("expirationTime", getExpirationTime(expirationTime));//失效时间
			Calendar calendar = Calendar.getInstance();
			Date date = new Date();
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			calendar.setTime(date);
			calendar.add(Calendar.DATE,-1);
			paramMap.put("expirationTimeNow", calendar.getTime());
		}
		
		/*
		 * 如果qrcodeStatus==0查询除删除以外的数据
		 */
		if(qrcodeStatus != null) {
			paramMap.put("status", qrcodeStatus);
		} else {
			paramMap.put("status", Byte.valueOf("0"));
		}
		paramMap.put("pageSize", size);
		paramMap.put("startIndex", (index-1)*size);

		paramMap.put(ApiConstant.SORT_ORDER_FIELD, "createTime");
		paramMap.put(ApiConstant.SORT_ORDER_FIELD_TYPE, ApiConstant.SORT_DESC);
		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.selectListExpirationTime(paramMap);// 如果修改表结构需要修改对应的mapper文件
		//查询总条数用
		paramMap.put("pageSize", null);
		paramMap.put("startIndex", null);
		List<WechatQrcode> countList = wechatQrcodeDao.selectListExpirationTime(paramMap);
		
		result.setTotal(wechatQrcodeLists.size());
		if (wechatQrcodeLists != null && !wechatQrcodeLists.isEmpty()) {
			result = addData(result, wechatQrcodeLists);
		}
		result.setTotalCount(countList.size());
		return result;
	}
	
	/**
	 * 根据输入的二维码名称模糊查询表wechat_qrcode
	 * 接口：mkt.weixin.qrcode.list.qrname
	 * @author shuiyangyang
	 * @Data 2016.08.19
	 */
	@Override
	public BaseOutput getWeixinQrcodeListQrname(String qrcodeName, int index, int size) {
		
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setQrcodeName(qrcodeName);
		wechatQrcode.setPageSize(size);
		wechatQrcode.setStartIndex((index-1)*size);


		
		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.fuzzySearchQrcodeName(wechatQrcode);
		result.setTotal(wechatQrcodeLists.size());
		wechatQrcode = new WechatQrcode();
		wechatQrcode.setQrcodeName(qrcodeName);
		wechatQrcode.setStartIndex(null);
		wechatQrcode.setPageSize(null);
		List<WechatQrcode> wqList = wechatQrcodeDao.fuzzySearchQrcodeName(wechatQrcode);
		if (wechatQrcodeLists != null && !wechatQrcodeLists.isEmpty()) {
			result = addData(result, wechatQrcodeLists);
		} else {
			logger.debug("根据微信号名：{}查不到信息",qrcodeName);
		}
		result.setTotalCount(wqList.size());
		
		return result;
	}
	
	/**
	 * 把查询出来的结果按格式放到 result
	 * @param result
	 * @param wechatQrcodeLists
	 * @return
	 * @author shuiyangyang
	 * @Data 2016.08.19
	 */
	private BaseOutput addData(BaseOutput result, List<WechatQrcode> wechatQrcodeLists) {
		
			//result.setTotal(wechatQrcodeLists.size());
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			for (WechatQrcode wechatQrcodeList : wechatQrcodeLists) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("id", wechatQrcodeList.getId());
				
				map.put("qrcode_pic", ApiConstant.RETURN_IMG_PATH_SMALL + wechatQrcodeList.getQrcodePic());// 返回二维码图片文件url 
																		
				map.put("qrcode_name", wechatQrcodeList.getQrcodeName());
				
				map.put("wx_name", wechatQrcodeList.getWxName());

				// 获取 渠道名
				WechatChannel wechatChannel = new WechatChannel();
				wechatChannel.setId(wechatQrcodeList.getChCode());
				List<WechatChannel> wechatChannelLists = wechatChannelDao.selectList(wechatChannel);
				String chName = "";
				if(wechatChannelLists != null && !wechatChannelLists.isEmpty()) {
					chName = wechatChannelLists.get(0).getChName();
				}
				
				map.put("ch_name", chName);
				if(wechatQrcodeList.getExpirationTime() != null) {
					map.put("expiration_time", format.format(wechatQrcodeList.getExpirationTime()));
				} else {
					map.put("expiration_time", wechatQrcodeList.getExpirationTime());
				}
				
				map.put("qrcode_status", statusToString(wechatQrcodeList.getStatus()));

				if(wechatQrcodeList.getRelatedTags() != null && wechatQrcodeList.getRelatedTags().length() > 0) {
					map.put("qrcode_tag", "有");
				} else {
					map.put("qrcode_tag", "无");
				}
				

				// 获取 关注者数
				WechatQrcodeFocus wechatQrcodeFocus = new WechatQrcodeFocus();
				wechatQrcodeFocus.setQrcodeId(wechatQrcodeList.getId().toString());
				int focusCount = wechatQrcodeFocusDao.selectListCount(wechatQrcodeFocus);
				
				map.put("focus_count", focusCount);

				result.getData().add(map);
			}
		
		
		return result;
	}
	
	private Date getExpirationTime(Integer expirationTimeInteger) {
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch(expirationTimeInteger.intValue()) {
			case 0 : return null;
			case 1 : calendar.add(Calendar.DATE, 3); break;
			case 2 : calendar.add(Calendar.DATE, 7); break;
			case 3 : calendar.add(Calendar.MONTH, 1); break;
			case 4 : calendar.add(Calendar.MONTH, 3); break;
			case 5 : calendar.add(Calendar.MONTH, 6); break;
			case 6 : calendar.add(Calendar.YEAR, 1); break;
			case 7 : calendar.add(Calendar.YEAR, 3); break;
			case 8 : return null;
		}
		return calendar.getTime();
	}
	
	// 根据状态获取对应的文字
	private String statusToString(Byte status) {
		if(status != null){
            switch (status){
                case 1:
                    return "使用中";
                case 2:
                    return "已删除";
                case 3:
                    return "已失效";
            }
        }
        return "删除";
	}
	
	/**
	 * 定时更新失效时间状态wechat_qrcode
	 *
	 * @param nowDate(格式
	 *            yyyy-MM-dd HH:mm:ss)
	 * @return
	 * @author congshulin
	 */
	public void updateStatusByExpirationTime(Date nowDate) {
		WechatQrcode wechatQrcode = new WechatQrcode();
		if (nowDate != null) {
			wechatQrcode.setExpirationTime(nowDate);
		} else {
			wechatQrcode.setExpirationTime(new Date());
		}

		wechatQrcode.setStatus(ApiConstant.TABLE_DATA_REMOVED_FAIL);

		int count = wechatQrcodeDao.updateStatusByExpirationTime(wechatQrcode);
		
		logger.debug("根据当前时间{}更新状态为失效条数",new Date(),count);
		
	}
}
