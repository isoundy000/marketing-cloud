/*************************************************
 * @功能简述: 获取不同状态下的campaign数量
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.CampaignProgressStatusCountService;
import cn.rongcapital.mkt.vo.out.CampaignProgressStatusCountDataOut;
import cn.rongcapital.mkt.vo.out.CampaignProgressStatusCountOut;

@Service
public class CampaignProgressStatusCountServiceImpl implements
		CampaignProgressStatusCountService {
	@Autowired
	private CampaignHeadDao campaignHeadDao;

	/**
	 * mkt.campaign.progressstatus.count.get
	 * @param
	 * @return BaseOutput
	 */
	@Override
	public CampaignProgressStatusCountOut campaignProgressStatusCountGet() {
		CampaignProgressStatusCountOut baseOutput = new CampaignProgressStatusCountOut(ApiErrorCode.SUCCESS.getCode(),
																					   ApiErrorCode.SUCCESS.getMsg(), 
																					   ApiConstant.INT_ZERO);
		CampaignHead t = new CampaignHead();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH);
		int notPublishCount = campaignHeadDao.selectListCount(t);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH);
		int publishCount = campaignHeadDao.selectListCount(t);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS);
		int inprogressCount = campaignHeadDao.selectListCount(t);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH);
		int finishCount = campaignHeadDao.selectListCount(t);
		int allCount = notPublishCount + publishCount + inprogressCount + finishCount;
		
		CampaignProgressStatusCountDataOut cpscdo = new CampaignProgressStatusCountDataOut();
		cpscdo.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH);
		cpscdo.setCount(notPublishCount);
		baseOutput.getDataCustom().add(cpscdo);
		
		cpscdo = new CampaignProgressStatusCountDataOut();
		cpscdo.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH);
		cpscdo.setCount(publishCount);
		baseOutput.getDataCustom().add(cpscdo);
		
		cpscdo = new CampaignProgressStatusCountDataOut();
		cpscdo.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS);
		cpscdo.setCount(inprogressCount);
		baseOutput.getDataCustom().add(cpscdo);
		
		cpscdo = new CampaignProgressStatusCountDataOut();
		cpscdo.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH);
		cpscdo.setCount(finishCount);
		baseOutput.getDataCustom().add(cpscdo);
		
		cpscdo = new CampaignProgressStatusCountDataOut();
		cpscdo.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_ALL);
		cpscdo.setCount(allCount);
		baseOutput.getDataCustom().add(cpscdo);
		
		baseOutput.setTotal(baseOutput.getDataCustom().size());
		return baseOutput;
	}

}
