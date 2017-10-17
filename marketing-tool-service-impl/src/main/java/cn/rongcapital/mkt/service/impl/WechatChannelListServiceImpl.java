package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.service.WechatChannelListService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by zhaoguoying on 2016-08-11.
 */
@Service
public class WechatChannelListServiceImpl implements WechatChannelListService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	WechatChannelDao wechatChannelDao;

	@Override
	public BaseOutput channelList() {

		WechatChannel wechatChannel = new WechatChannel();
		wechatChannel.setStatus(Byte.toString(ApiConstant.TABLE_DATA_STATUS_VALID));
		wechatChannel.setStartIndex(null);
		wechatChannel.setPageSize(null);
		List<WechatChannel> wechatChannels = wechatChannelDao.selectList(wechatChannel);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		//增加默认六项系统默认渠道
		List<String> sysChannel = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(wechatChannels)) {

			result.setTotal(wechatChannels.size() + sysChannel.size());
			for (WechatChannel w : wechatChannels) {
				Map<String, Object> channelMap = new HashMap<String, Object>();
				channelMap.put("channel_id", w.getId());
				channelMap.put("channel_name", w.getChName());
				channelMap.put("channel_type", w.getType());
				channelMap.put("channel_removed", w.getIsRemoved());
				result.getData().add(channelMap);
			}	
		}
		
		logger.info(result.getData().toString());
		return result;
	}

	@Override
	public BaseOutput chanelExitLike(WechatChannel param) {

		int exit_count = wechatChannelDao.selectListCount_Like(param);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Map<String, Object> exit_map = new HashMap<String, Object>();
		if (exit_count > 0) {
			exit_map.put("is_exist", 1);
			result.setTotal(exit_count);
		}
		else
		{
			exit_map.put("is_exist", 0);
			result.setTotal(exit_count);
		}
		result.getData().add(exit_map);
		return result;
	}
}
