/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeDelService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeInId;

/**
 * 删除二维码接口 （逻辑删除，状态改为2）
 * 
 * @author shuiyangyang
 * @Date 2016.08.25
 */
@Service
public class WeixinQrcodeDelServiceImpl implements WeixinQrcodeDelService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;
	
	@Autowired
	private WechatChannelDao wechatChannelDao;
	
	
	@Override
	public BaseOutput weixinQrocdeDel(WechatQrcodeInId body) {
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE,null);
		
		WechatQrcode wechatQrcode = new WechatQrcode();
		
		wechatQrcode.setId(body.getId());
		
		List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcode);
		
		if(wechatQrcodeList != null){
			wechatQrcode = wechatQrcodeList.get(0);
		}
		
		wechatQrcode.setStatus((byte)2);
		
		int count = wechatQrcodeDao.updateById(wechatQrcode);
		
		if(count<=0) {
			result.setTotal(0);
//			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
//			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
			logger.debug("数据不存在, id={}", body.getId());
		} else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", body.getId());
			map.put("status", 2);
			
			handleWechatChannel(wechatQrcode.getChCode());
			
			result.getData().add(map);
		}
		
		return result;
	}
	
	/**
	 * 删除二维码接口 （微信记录物理删除）
	 * 接口：mkt.weixin.qrcode.records.del
	 * 
	 * @param id
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	@Override
	public BaseOutput weixinQrcodeRecordsDel(WechatQrcodeInId body) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE,null);
		
		WechatQrcode wechatQrcode = new WechatQrcode();
		
		wechatQrcode.setId(body.getId());
		
		List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcode);
		
		if(wechatQrcodeList != null){
			wechatQrcode = wechatQrcodeList.get(0);
		}
		
		int count = wechatQrcodeDao.deleteById(wechatQrcode);
		if(count<=0) {
			result.setTotal(0);
//			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
//			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
			logger.debug("数据不存在, id={}", body.getId());
		} else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", body.getId());
			
			handleWechatChannel(wechatQrcode.getChCode());
			
			result.getData().add(map);
		}
		return result;
	}

	/**
	 * 处理未使用自定义渠道状态
	 * @param chCode
	 */
	private void handleWechatChannel(Integer chCode) {

		WechatChannel wechatChannel = new WechatChannel();
		
		if(chCode != null){
			
			wechatChannel.setId(chCode);
			List<WechatChannel> wechatChannelList = wechatChannelDao.selectList(wechatChannel);

			if(wechatChannelList != null && wechatChannelList.size() > 0 && wechatChannelList.get(0) != null){
				wechatChannel = wechatChannelList.get(0);

				Integer type = wechatChannel.getType();
				//系统渠道不处理
				if(type == ApiConstant.WECHAT_CHANNEL_TYPE_CUSTOM){
						
					int count = wechatQrcodeDao.selectUsedChannelCountBychCode(chCode);
					//没有二维码使用的渠道 设置为删除状态
					if(count == 0){
						wechatChannel.setId(chCode);
						//这个地方要改成可以删除 2016-9-30 by lihaiguang
						//wechatChannel.setIsRemoved(ApiConstant.TABLE_DATA_REMOVED_NOTDEL);
						wechatChannel.setIsRemoved(ApiConstant.TABLE_DATA_REMOVED_DEL);
						wechatChannelDao.updateById(wechatChannel);
					}
				}
			}
		}
	}
}
