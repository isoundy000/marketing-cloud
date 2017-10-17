package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.service.WechatChanellUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatChanellUpdateIn;

/**
 * Created by zhaoguoying on 2016-08-11.
 */
@Service
public class WechatChanellUpdateServiceImpl implements WechatChanellUpdateService {

	@Autowired
	WechatChannelDao wechatChannelDao;

	/**
	 * 设置渠道
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput wechatChannelUpdate(WechatChanellUpdateIn wechatChanellUpdateIn,
			SecurityContext securityContext) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		//删除未使用渠道
		WechatChannel wechatChannel = new WechatChannel();
		wechatChannel.setIsRemoved(ApiConstant.TABLE_DATA_REMOVED_DEL);
		wechatChannel.setType(ApiConstant.WECHAT_CHANNEL_TYPE_CUSTOM);
		wechatChannelDao.delete(wechatChannel);
		
		//获取渠道名称数组
		String[] chaNames = wechatChanellUpdateIn.getChaNames();
		//重新插入渠道
		if(chaNames != null && chaNames.length != 0){
			for (String chaName : chaNames) {
				
				wechatChannel = new WechatChannel();
				wechatChannel.setChName(chaName);
				//如果存在不插入
				int count = wechatChannelDao.selectListCount(wechatChannel);
				if(count == 0){
					wechatChannel.setType(ApiConstant.WECHAT_CHANNEL_TYPE_CUSTOM);
					wechatChannel.setIsRemoved(ApiConstant.TABLE_DATA_REMOVED_DEL);
					wechatChannelDao.insert(wechatChannel);
				}
			}
		}

		wechatChannel = new WechatChannel();
		wechatChannel.setStatus(Byte.toString(ApiConstant.TABLE_DATA_STATUS_VALID));
		List<WechatChannel> wechatChannels = wechatChannelDao.selectList(wechatChannel);
		
		if (CollectionUtils.isNotEmpty(wechatChannels)) {
			result.setTotal(wechatChannels.size());
			for (WechatChannel w : wechatChannels) {
				Map<String, Object> channelMap = new HashMap<String, Object>();
				channelMap.put("channel_id", w.getId());
				channelMap.put("channel_name", w.getChName());
				channelMap.put("channel_type", w.getType());
				channelMap.put("channel_removed", w.getIsRemoved());
				result.getData().add(channelMap);
			}
		}
		
		return result;
	}
}
