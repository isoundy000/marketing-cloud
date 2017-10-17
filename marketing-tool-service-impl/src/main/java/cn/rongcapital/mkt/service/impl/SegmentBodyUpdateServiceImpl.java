/*************************************************
 * @功能简述: 编辑受众细分body
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationBody;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SegmentBodyUpdateService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentBodyFilterGroupIn;
import cn.rongcapital.mkt.vo.in.SegmentBodyTagsIn;
import cn.rongcapital.mkt.vo.in.SegmentBodyUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
@Transactional
public class SegmentBodyUpdateServiceImpl implements SegmentBodyUpdateService {
	
    private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	SegmentationBodyDao segmentationBodyDao;
	@Autowired
	SegmentationHeadDao segmentationHeadDao;
	
	private static final String PARAM_NAME = "mq.synSegment";
	
	private static final String SEGMENT_SERVICE = "dataSegmentSyncTaskServiceImpl";
	
	@Autowired
	private MQTopicService mqTopicService;
	
	@Override
	@ReadWrite(type = ReadWriteType.WRITE)
	@Transactional(propagation = Propagation.REQUIRED)
	public Object segmentBodyUpdate(SegmentBodyUpdateIn body,
			SecurityContext securityContext) {
		
		Integer headerId = body.getSegmentHeadId();
		BaseOutput ur = checkPublishStatus(headerId);
		if(null != ur) {
			return ur;
		}
		
		// 删除既有body数据
		segmentationBodyDao.batchDeleteUseHeaderId(headerId);

		// 插入新的Body数据
		List<SegmentBodyFilterGroupIn> filterGroups = body.getFilterGroups();
		List<SegmentBodyTagsIn> tags = null;
		if (filterGroups != null) {
			Date now = new Date();
			for (SegmentBodyFilterGroupIn filterGroup : filterGroups) {
				Integer groupIndex = filterGroup.getGroupIndex();
				tags = filterGroup.getTagList();
				if (tags != null) {
					for (SegmentBodyTagsIn tag : tags) {
						SegmentationBody insertBody = new SegmentationBody();
						insertBody.setHeadId(headerId);
//						insertBody.setTagGroupId(tag.getTagGroupId());
						insertBody.setTagId(tag.getTagId());
//						insertBody.setExclude(tag.getExclude().byteValue());
						insertBody.setCreateTime(now);
						insertBody.setGroupIndex(groupIndex);
						insertBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//						insertBody.setGroupSeq(tag.getGroupSeq());
						segmentationBodyDao.insert(insertBody);
					}
				}
			}
		}
		SegmentationHead t = new SegmentationHead();
		t.setId(headerId);
		try {
			String jsonString = JSONObject.toJSONString(t);
			ActiveMqMessageVO activeMqMessageVO = new ActiveMqMessageVO();
			activeMqMessageVO.setTaskName("同步受众人群到MongoDB");
			activeMqMessageVO.setServiceName(SEGMENT_SERVICE);
			activeMqMessageVO.setMessage(jsonString);
			logger.info("同步受众人群到MongoDB，开始异步调取");
			mqTopicService.senderMessage(PARAM_NAME, activeMqMessageVO);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		return ur;
	}
	private BaseOutput checkPublishStatus(int id) {
		 BaseOutput ur = null;
		 SegmentationHead t = new SegmentationHead();  
		 t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		 t.setId(id);
		 List<SegmentationHead> segList = segmentationHeadDao.selectList(t);
		 if(CollectionUtils.isNotEmpty(segList)) {
			SegmentationHead sht = segList.get(0);
			 Integer referCampaignCount = sht.getReferCampaignCount();
			if(referCampaignCount != null && referCampaignCount.intValue() > 0) {
				ur = new BaseOutput(ApiErrorCode.BIZ_ERROR_SEGMENTATION_IN_CAMPAIGN.getCode(),
									ApiErrorCode.BIZ_ERROR_SEGMENTATION_IN_CAMPAIGN.getMsg(),
									ApiConstant.INT_ZERO,null);
			}
		 } else {
			ur = new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
								ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(),
								ApiConstant.INT_ZERO,null);
		 }
		 return ur;
	 }
}
