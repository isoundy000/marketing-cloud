/*************************************************
 * @功能简述: 获取不同状态下的campaign列表
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/7
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.CampaignColumnsDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignTriggerTimerDao;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.CampaignColumns;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CampaignTriggerTimer;
import cn.rongcapital.mkt.service.CampaignProgressStatusListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CampaignProgressStatusListOut;
import cn.rongcapital.mkt.vo.out.ColumnsOut;

@Service
public class CampaignProgressStatusListServiceImpl implements
		CampaignProgressStatusListService {
	
	@Autowired
	private CampaignHeadDao campaignHeadDao;
	@Autowired
	private CampaignTriggerTimerDao campaignTriggerTimerDao;
	@Autowired
    private CampaignAudienceTargetDao campaignAudienceTargetDao;
	@Autowired
	private CampaignColumnsDao campaignColumnsDao;
	
	private static final String ORDER_BY_FIELD_NAME = "field_order";//排序的字段名
	/**
	 * mkt.campaign.progressstatus.list.get
	 * @param publishStatus campaignName index size
	 * @return Object
	 */
	@Override
	public BaseOutput campaignProgressStatusList(Byte publishStatus,
			String campaignName, Integer index, Integer size) {
		CampaignHead t = new CampaignHead();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		if(publishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_ALL){
			t.setPublishStatus(publishStatus);
		}
		t.setPageSize(size);
		t.setStartIndex((index-1)*size);
		t.getCustomMap().put("keyword", campaignName);
		t.setOrderField("update_time");
		t.setOrderFieldType("desc");
		int totalCount = campaignHeadDao.selectCampaignProgressStatusListByPublishStatusCount(t);
		List<CampaignHead> reList = campaignHeadDao.selectCampaignProgressStatusListByPublishStatus(t);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if(CollectionUtils.isNotEmpty(reList)){
			for(CampaignHead c:reList){
				CampaignProgressStatusListOut campaignProgressStatusListOut = new CampaignProgressStatusListOut();
				campaignProgressStatusListOut.setCampaignId(c.getId());
				campaignProgressStatusListOut.setCampaignName(c.getName());
				campaignProgressStatusListOut.setCreateTime(DateUtil.getStringFromDate(c.getCreateTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
				campaignProgressStatusListOut.setPublishStatus(c.getPublishStatus());
				
				//查询活动的开始结束时间
				CampaignTriggerTimer ctt = new CampaignTriggerTimer();
				ctt.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				ctt.setCampaignHeadId(c.getId());
				List<CampaignTriggerTimer> cttList = campaignTriggerTimerDao.selectList(ctt);
				if(CollectionUtils.isNotEmpty(cttList)){
					String startTime = DateUtil.getStringFromDate(cttList.get(0).getStartTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
					String endTime = DateUtil.getStringFromDate(cttList.get(0).getEndTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss);
					campaignProgressStatusListOut.setStartTime(startTime);
					campaignProgressStatusListOut.setEndTime(endTime);
				}
				
				//查询活动涉及到的人群
				CampaignAudienceTarget cat = new CampaignAudienceTarget();
				cat.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				cat.setCampaignHeadId(c.getId());
				List<CampaignAudienceTarget> catList = campaignAudienceTargetDao.selectSegmentationName(cat);
				if(CollectionUtils.isNotEmpty(catList)){
					List<String> audienceNameList = new ArrayList<String>();
					for(CampaignAudienceTarget campaignAudienceTarget:catList) {
						if(campaignAudienceTarget == null){
							audienceNameList.add("");
							continue;
						}
						if(StringUtils.isNotBlank(campaignAudienceTarget.getSegmentationName())) {
							audienceNameList.add(campaignAudienceTarget.getSegmentationName());
						}
					}
					campaignProgressStatusListOut.setSegmentationName(StringUtils.join(audienceNameList, ";"));
				}
				
				result.getData().add(campaignProgressStatusListOut);
			}
		}
		//查询页面表格的列名
		CampaignColumns campaignColumns = new CampaignColumns();
		campaignColumns.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignColumns.setOrderField(ORDER_BY_FIELD_NAME);
		List<CampaignColumns> campaignColumnsList = campaignColumnsDao.selectList(campaignColumns);
		List<Object> columnsOutList = new ArrayList<Object>(); 
		if(CollectionUtils.isEmpty(campaignColumnsList)) {
			for(CampaignColumns cc:campaignColumnsList) {
				ColumnsOut columnsOut = new ColumnsOut();
				columnsOut.setColCode(cc.getFieldCode());
				columnsOut.setColName(cc.getFieldName());
				columnsOutList.add(columnsOut);
			}
		}
		result.setColNames(columnsOutList);
		result.setTotal(result.getData().size());
		result.setTotalCount(totalCount);
		return result;
	}
}
