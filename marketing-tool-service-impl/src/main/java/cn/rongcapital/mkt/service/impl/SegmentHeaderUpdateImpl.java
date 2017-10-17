package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.vo.in.SegmentHeadDeleteIn;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.UserSessionUtil;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentHeaderUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentHeadUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentHeaderUpdateImpl implements SegmentHeaderUpdateService {
	
	 @Autowired
	 SegmentationHeadDao segmentationHeadDao;

    @Autowired
    SegmentationBodyDao segmentationBodyDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public static final Integer POOL_INDEX = 2;
    
    /**
     * @功能简述: 编辑segment header
     * @param: SegmentHeadIn body, SecurityContext securityContext 
     * @return: Object
     */
	 @Override
	 @ReadWrite(type=ReadWriteType.WRITE)
	 public BaseOutput segmentHeaderUpdate(SegmentHeadUpdateIn body,SecurityContext securityContext) {
		BaseOutput ur = checkPublishStatus(body.getSegmentId());
		if(null != ur) {
			return ur;
		}
		
		SegmentationHead t = new SegmentationHead();  
		t.setId(body.getSegmentId());
		t.setName(body.getSegmentName());
		t.setPublishStatus(body.getPublishStatus().byteValue());
		Date now = new Date();
		t.setCreateTime(now);
		segmentationHeadDao.updateById(t);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oper", UserSessionUtil.getUserNameByUserToken());//TO DO:获取当前用户名
		map.put("updatetime", DateUtil.getStringFromDate(now, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		map.put("id", t.getId());
		
		ur = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		ur.getData().add(map);
		ur.setTotal(ur.getData().size());
		return ur;
	 }

    @Override
    public BaseOutput deleteSegmentHeader(SegmentHeadDeleteIn body, SecurityContext securityContext) {
        Integer segmentHeadId = body.getSegmentId();
        BaseOutput ur = checkPublishStatus(segmentHeadId);
        if(null != ur) {
            return ur;
        }
        SegmentationHead segmentationHead = new SegmentationHead();
        segmentationHead.setId(segmentHeadId);
        segmentationHead.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);

        segmentationHeadDao.updateById(segmentationHead);
        segmentationBodyDao.batchDeleteUseHeaderId(segmentHeadId);
        //mongoTemplate.remove(new Query(Criteria.where("segmentationHeadId").is(segmentHeadId)),Segment.class);
        try {
			JedisClient.delete(POOL_INDEX, "segmentcover:"+segmentHeadId);
			JedisClient.delete(POOL_INDEX, "segmentcoverid:"+segmentHeadId);
		} catch (JedisException e) {
			e.printStackTrace();
		}
        
        return new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
    }

    private BaseOutput checkPublishStatus(Integer segmentHeadId) {
		 BaseOutput ur = null;
		 SegmentationHead t = new SegmentationHead(); 
		 t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		 t.setId(segmentHeadId);
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
