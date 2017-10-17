package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.CampaignNodeItemListOut;

public interface CampaignNodeItemListGetService {

	public CampaignNodeItemListOut campaignNodeItemListGet(String userToken, String ver);
}
