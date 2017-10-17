package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTargetAudienceTypeEnum;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.AudienceListPartyMap;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SmsTargetAudienceListGetService;
import cn.rongcapital.mkt.vo.out.SmsTargetAudienceListOut;
import cn.rongcapital.mkt.vo.out.SmsTargetAudienceOut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by byf on 10/18/16.
 */
@Service
public class SmsTargetAudienceListGetServiceImpl implements SmsTargetAudienceListGetService{

    private static Logger logger = LoggerFactory.getLogger(SmsTargetAudienceListOut.class);
    @Autowired
    private SegmentationHeadDao segmentationHeadDao;

    @Autowired
    private AudienceListDao audienceListDao;
    
    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;

    @Override
    public SmsTargetAudienceListOut getSmsTargetAudienceList() {
        SmsTargetAudienceListOut smsTargetAudienceListOut = new SmsTargetAudienceListOut(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);

        SegmentationHead paramSegmentationHead = new SegmentationHead();
        paramSegmentationHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSegmentationHead.setPageSize(Integer.MAX_VALUE);

        List<SegmentationHead> segmentationHeadList = segmentationHeadDao.selectList(paramSegmentationHead);
        if(!CollectionUtils.isEmpty(segmentationHeadList)){
            for(SegmentationHead segmentationHead : segmentationHeadList){
                SmsTargetAudienceOut smsTargetAudienceOut = new SmsTargetAudienceOut();
                smsTargetAudienceOut.setId(Long.valueOf(segmentationHead.getId()));
                smsTargetAudienceOut.setType(SmsTargetAudienceTypeEnum.SMS_TARGET_SEGMENTATION.getTypeCode());
                smsTargetAudienceOut.setName(segmentationHead.getName());
                String segmentKey = SegmentCalcServiceImpl.KEY_PREFIX_SEGMENTCOVERID + segmentationHead.getId();
                Long segmentCount = 0L;
                try {
                    segmentCount = JedisClient.scard(SegmentCalcServiceImpl.REDISDB_INDEX, segmentKey);
                } catch (JedisException e) {
                    logger.error("get segmentCount error {}" , e);
                    e.printStackTrace();
                }
                logger.info("segmentCount is " + segmentCount);
                smsTargetAudienceOut.setCount(segmentCount);
                smsTargetAudienceListOut.getSmsTargetAudienceOutList().add(smsTargetAudienceOut);
            }
        }

        AudienceList paramAudienceList = new AudienceList();
        paramAudienceList.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramAudienceList.setPageSize(Integer.MAX_VALUE);
        List<AudienceList> audienceListList = audienceListDao.selectList(paramAudienceList);

        if(!CollectionUtils.isEmpty(audienceListList)){
            for(AudienceList audienceList : audienceListList){
                SmsTargetAudienceOut smsTargetAudienceOut = new SmsTargetAudienceOut();
                smsTargetAudienceOut.setId(Long.valueOf(audienceList.getId()));
                //audience count 
                AudienceListPartyMap audienceListPartyMap = new AudienceListPartyMap();
                audienceListPartyMap.setAudienceListId(audienceList.getId());
                Integer audienceListPartyCount = audienceListPartyMapDao.selectListCount(audienceListPartyMap);
                logger.info("audienceListPartyCount is " + audienceListPartyCount);
                smsTargetAudienceOut.setCount(audienceListPartyCount.longValue());
                smsTargetAudienceOut.setType(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode());
                smsTargetAudienceOut.setName(audienceList.getAudienceName());
                smsTargetAudienceListOut.getSmsTargetAudienceOutList().add(smsTargetAudienceOut);
            }
        }

        smsTargetAudienceListOut.setTotal(smsTargetAudienceListOut.getSmsTargetAudienceOutList().size());
        smsTargetAudienceListOut.setTotalCount(smsTargetAudienceListOut.getSmsTargetAudienceOutList().size());
        return smsTargetAudienceListOut;
    }

}
