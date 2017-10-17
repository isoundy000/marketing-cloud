/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.service.InsertCustomTagService;
import cn.rongcapital.mkt.service.WeixinQrcodeBatchSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeBatchSaveIn;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinQrcodeBatchSaveServiceImpl implements WeixinQrcodeBatchSaveService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;
	
	@Autowired
    private InsertCustomTagService insertCustomTagService;

	/**
	 * 根据batch_id，在表wechat_qrcode 中查找到对应二维码记录，更新：失效时间、标签、备注信息、状态(status=0)
	 * 接口：mkt.weixin.qrcode.batch.save
	 * 
	 * @author shuiyangyang
	 * @Date 2016.08.26
	 */
	@Override
	public BaseOutput weixinQrcodeBatchSave(String batchId, String expirationTime, String qrcodeTagIds,
			Integer qrcodeStatus) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setBatchId(batchId);

		if (expirationTime != null && !expirationTime.isEmpty()) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar expirationTimeCalendar = Calendar.getInstance();
				expirationTimeCalendar.setTime(simpleDateFormat.parse(expirationTime));
				wechatQrcode.setExpirationTime(expirationTimeCalendar.getTime());
			} catch (ParseException e) {
				logger.error("转换expirationTime到日期出错,expirationTime = {} ", expirationTime);
			}
		}

		wechatQrcode.setRelatedTags(qrcodeTagIds);
		wechatQrcode.setStatus((byte) qrcodeStatus.intValue());

		int count = wechatQrcodeDao.updataByBatchId(wechatQrcode);
		
		
		logger.debug("更新数据id:{}",wechatQrcode.getId());

		return result;
	}

	@Override
	public BaseOutput weixinQrcodeBatchSave(WechatQrcodeBatchSaveIn body) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setBatchId(body.getBatchId());
		wechatQrcode.setStatus((byte) 1);//保存的时候给置为已用
		String expirationTime = body.getExpirationTime();
		
		if (expirationTime != null && !expirationTime.isEmpty()) {
			try {
				Calendar expirationTimeCalendar = Calendar.getInstance();
				expirationTimeCalendar.setTime(simpleDateFormat.parse(expirationTime));
				wechatQrcode.setExpirationTime(expirationTimeCalendar.getTime());
			} catch (ParseException e) {
				logger.error("转换expirationTime到日期出错,expirationTime = {} ", expirationTime);
			}
		}else {
			Calendar expirationTimeCalendar = Calendar.getInstance();
			expirationTimeCalendar.setTime(new Date());
			expirationTimeCalendar.add(Calendar.YEAR, 100);
			wechatQrcode.setExpirationTime(expirationTimeCalendar.getTime());
		}
		
		wechatQrcode.setComments(body.getComment());
		//wangweiqiang update 2016-09-19 
		List<String> tagNames = body.getTagNames();
		if(!CollectionUtils.isEmpty(tagNames)){
			//id拼接
			StringBuilder ids = new StringBuilder();
			for (String tagName : tagNames) {
				//判断是否存在
				if(StringUtils.isNotEmpty(tagName)){
				    BaseTag tag = insertCustomTagService.insertCustomTagLeafFromSystemIn(tagName,
                                    TagSourceEnum.WECHAT_QRCODE_SOURCE_ACCESS.getTagSourceName());
				    String tagId = tag == null ? "":tag.getTagId();
                    if (StringUtils.isNotEmpty(tagId)){
                        ids.append(tagId + ";");
                    }
				}
			}
			if(!StringUtils.isEmpty(ids)){
				String relatedTags = ids.toString().substring(0,ids.length()-1);
				wechatQrcode.setRelatedTags(relatedTags);
			}
		}else {
			wechatQrcode.setRelatedTags("");
		}
		
		wechatQrcodeDao.updataByBatchId(wechatQrcode);
		
		return result;
	}
	
	
	
}
