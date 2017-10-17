package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CampaignHeadCreateIn;

public interface CampaignHeaderCreateService {

	public BaseOutput campaignHeaderCreate(CampaignHeadCreateIn body, SecurityContext securityContext);

}
