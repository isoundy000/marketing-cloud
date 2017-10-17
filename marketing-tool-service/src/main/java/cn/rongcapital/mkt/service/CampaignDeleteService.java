/*************************************************
 * @功能简述: 删除campaign
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface CampaignDeleteService {
	/**
	 * mkt.campaign.delete
	 * @param campaignId
	 * @return 
	 */
	public BaseOutput campaignDelete(Integer campaignId);
}
