package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CampaignHeadUpdateIn;

public interface CampaignHeaderUpdateService {

	public BaseOutput campaignHeaderUpdate(CampaignHeadUpdateIn body, SecurityContext securityContext);

	void decreaseSegmentReferCampaignCount(Integer campaignHeadId);
}
