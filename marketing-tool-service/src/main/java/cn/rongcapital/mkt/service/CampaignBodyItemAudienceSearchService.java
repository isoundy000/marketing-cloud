package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.CampaignBodyItemAudienceSearchOut;

public interface CampaignBodyItemAudienceSearchService {

	public CampaignBodyItemAudienceSearchOut campaignBodyItemAudienceSearch(String name,Integer campaignHeadId,String itemId);
}
