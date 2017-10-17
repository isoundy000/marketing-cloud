/*************************************************
 * @功能简述: 删除campaign mkt.campaign.delete
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
import cn.rongcapital.mkt.service.CampaignDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CampaignDeleteServiceImpl implements CampaignDeleteService {
	@Autowired
	private CampaignHeadDao campaignHeadDao;
	
	/**
	 * mkt.campaign.delete
	 * @param campaignId
	 * @return BaseOutput
	 */
	@Override
	public BaseOutput campaignDelete(Integer campaignId) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

		CampaignHead campaignHead = new CampaignHead();
		campaignHead.setId(campaignId);
		campaignHead.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
		
		int rowEffected = campaignHeadDao.updateById(campaignHead);

		if (rowEffected == 0) {
			result.setMsg("不存在id为" + campaignId + "的数据");
		}
		
		result.setTotal(result.getData().size());

		return result;
	}

}
