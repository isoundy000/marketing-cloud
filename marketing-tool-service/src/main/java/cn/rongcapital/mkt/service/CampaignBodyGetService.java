package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.CampaignBodyGetOut;

public interface CampaignBodyGetService {

	public CampaignBodyGetOut campaignBodyGet(String userToken, String ver, int campaignHeadId);
}
