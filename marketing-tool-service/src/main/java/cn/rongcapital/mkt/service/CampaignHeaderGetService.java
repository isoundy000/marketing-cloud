package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.CampaignAnalysisOut;
import cn.rongcapital.mkt.vo.out.CampaignHeaderGetOut;
import cn.rongcapital.mkt.vo.out.CampaignProfileOut;

public interface CampaignHeaderGetService {

    CampaignHeaderGetOut campaignHeaderGet(String userToken, String ver, int campaignHeadId);

    CampaignProfileOut campaignProfileList(String userToken, String ver, Integer campaignHeadId);

    CampaignAnalysisOut campaignAnalysisList(String userToken, String ver, Integer campaignHeadId, Byte viewType);
}
