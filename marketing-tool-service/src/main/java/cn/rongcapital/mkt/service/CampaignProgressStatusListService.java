/*************************************************
 * @功能简述: 获取不同状态下的campaign列表
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/7
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface CampaignProgressStatusListService {
	/**
	 * mkt.campaign.progressstatus.list.get
	 * @param publishStatus campaignName index size
	 * @return 
	 */
	BaseOutput campaignProgressStatusList(Byte publishStatus,
			String campaignName, Integer index, Integer size);
}
