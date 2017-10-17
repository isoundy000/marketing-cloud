package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyCreateOut;

public interface CampaignBodyCreateService {

	public CampaignBodyCreateOut campaignBodyCreate(CampaignBodyCreateIn body, SecurityContext securityContext);
}
