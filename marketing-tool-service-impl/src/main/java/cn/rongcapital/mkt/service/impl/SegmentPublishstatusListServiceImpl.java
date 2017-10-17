/*************************************************
 * @功能简述: SegmentHeaderCreateService实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.service.SegmentManageCalService;
import cn.rongcapital.mkt.service.SegmentPublishstatusListService;
import cn.rongcapital.mkt.service.SmsTaskHeadService;
import cn.rongcapital.mkt.vo.out.SegmentPublishstatusListDataOut;
import cn.rongcapital.mkt.vo.out.SegmentPublishstatusListOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentPublishstatusListServiceImpl implements SegmentPublishstatusListService {

	public static final  Integer POOL_INDEX = 2;
	
	public static final String SEGMENT_COVER_ID_STR="segmentcoverid:";
	
    @Autowired
    SegmentationHeadDao segmentationHeadDao;
    
    @Autowired
    SegmentationBodyDao segmentationBodyDao;
    
	@Autowired
	private SegmentManageCalService segmentManageCalService;
	
	@Autowired
	private CampaignAudienceTargetDao campaignAudienceTargetDao;

	@Autowired
	private SmsTaskHeadService smsTaskHeadService;

    /**
     * @功能简述: mkt.segment.publishstatus.list.get
     * @param: String method, String userToken, 
	 *		   Integer publishStatus, Integer index,
	 *		   Integer size, String ver
     * @return: Object
     */
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public SegmentPublishstatusListOut segmentPublishstatusList(String userToken, 
										   Integer publishStatus, Integer index,
										   Integer size, String ver,String keyword) {
		SegmentationHead t = new SegmentationHead();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		if(ApiConstant.SEGMENT_PUBLISH_STATUS_ALL == publishStatus.byteValue()){
			//t.setPublishStatus(publishStatus.byteValue());
			t.setOrderFieldType("desc");
			t.setOrderField("publish_status");
		}else if(ApiConstant.SEGMENT_PUBLISH_STATUS_PUBLISH == publishStatus.byteValue()){
			
			t.setPublishStatus(publishStatus.byteValue());
			t.setOrderFieldType("desc");
			t.setOrderField("update_time");
		}else if(ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH == publishStatus.byteValue()){
			t.setPublishStatus(publishStatus.byteValue());
			t.setOrderFieldType("desc");
			t.setOrderField("create_time");
		}
		t.setPageSize(size);
		t.setStartIndex((index-1)*size);
		t.getCustomMap().put("keyword", keyword);
//		t.setOrderFieldType("desc");
//		t.setOrderField("update_time");
		int totalCount = segmentationHeadDao.selectListCount(t);
		List<SegmentationHead> reList = segmentationHeadDao.selectListByKeyword(t);
		SegmentPublishstatusListOut rseult = new SegmentPublishstatusListOut(ApiErrorCode.SUCCESS.getCode(),
										   ApiErrorCode.SUCCESS.getMsg(),
										   ApiConstant.INT_ZERO);
		if(CollectionUtils.isNotEmpty(reList)) {
			for(SegmentationHead s:reList){
				SegmentPublishstatusListDataOut data = new SegmentPublishstatusListDataOut();
				data.setSegmentName(s.getName());
				data.setSegmentHeadId(s.getId());
				data.setPublishStatus(s.getPublishStatus());
				data.setReferCampaignCount(s.getReferCampaignCount());
				data.setTagNames(segmentationBodyDao.getContainTagsByHeadId(s.getId()));//获取包含标签
				
				// 判断活动是否在使用该细分
				CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
				campaignAudienceTarget.setSegmentationId(s.getId());
				int count = campaignAudienceTargetDao.selectPublishStatusCount(campaignAudienceTarget);

				if (count == 0) {
					// 判断短信是否在使用该细分
					SmsTaskBody smsTaskBody = new SmsTaskBody();
					smsTaskBody.setTargetId(new Long(s.getId()));
					smsTaskBody.setTargetType(0);
					int num = smsTaskHeadService.getTaskStatusCount(smsTaskBody);
					
					if (num == 0) {
						data.setCompileStatus(ApiConstant.SEGMENT_COMPILE_STATUS_YES);
					} else {
						data.setCompileStatus(ApiConstant.SEGMENT_COMPILE_STATUS_NO);
					}

				} else {
					data.setCompileStatus(ApiConstant.SEGMENT_COMPILE_STATUS_NO);
				}
				
				//redis 中获取覆盖人数
				Long coverCount = segmentManageCalService.scard(POOL_INDEX, SEGMENT_COVER_ID_STR+s.getId());

				data.setCoverCount(coverCount.intValue());
				
				rseult.getDataCustom().add(data);
			}
		}
		rseult.setTotal(rseult.getDataCustom().size());
		rseult.setTotalCount(totalCount);
		return rseult;
	}

}
