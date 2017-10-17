package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationBody;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.service.CreupdateSegmentService;
import cn.rongcapital.mkt.service.SegmentCalcService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentCreUpdateIn;
import cn.rongcapital.mkt.vo.in.SystemTagIn;
import cn.rongcapital.mkt.vo.in.SystemValueIn;
import cn.rongcapital.mkt.vo.in.TagGroupsIn;
import cn.rongcapital.mkt.vo.out.SegmentCreupdateOut;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by byf on 11/4/16.
 */
@Service
public class CreupdateSegmentServiceImpl implements CreupdateSegmentService {

    private static Logger logger = LoggerFactory.getLogger(CreupdateSegmentServiceImpl.class);

    @Autowired
    private SegmentationHeadDao segmentationHeadDao;

    @Autowired
    private SegmentationBodyDao segmentationBodyDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SegmentCalcService segmentCalcService;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public BaseOutput creupdateSegment(@NotEmpty SegmentCreUpdateIn segmentCreUpdateIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
        SegmentCreupdateOut segmentCreupdateOut = new SegmentCreupdateOut();

        if(segmentCreUpdateIn.getSegmentHeadId() == null){
            //不包含了id,则为创建操作
            //1.首先更新segmentHead表,将相关的数据设置进去.
            SegmentationHead segmentationHead = new SegmentationHead();
            segmentationHead.setName(segmentCreUpdateIn.getSegmentName());
            segmentationHead.setPublishStatus(segmentCreUpdateIn.getPublishStatus().byteValue());
            segmentationHead.setCreateTime(new Date());
            segmentationHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            segmentationHeadDao.insert(segmentationHead);
            segmentCreUpdateIn.setSegmentHeadId(Long.valueOf(segmentationHead.getId()));

            //2.获取segmentHeadId之后,解析相应的数据,更新segmentBody表.
            createNewSegmentationBody(segmentCreUpdateIn, baseOutput, segmentCreupdateOut, segmentationHead);
        }else {
            //2.包含了id,则为更新操作
            //1检查现在的细分状态是否能够更新,不能够更新返回相应的提示信息
            SegmentationHead paramSegmentationHead = new SegmentationHead();
            paramSegmentationHead.setId(segmentCreUpdateIn.getSegmentHeadId().intValue());
            paramSegmentationHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<SegmentationHead> segmentationHeadList = segmentationHeadDao.selectList(paramSegmentationHead);
            if(CollectionUtils.isEmpty(segmentationHeadList)){
                baseOutput.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
                baseOutput.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
                return baseOutput;
            }
            SegmentationHead segmentationHead = segmentationHeadList.get(0);
            Integer refCampaignCount = segmentationHead.getReferCampaignCount();
            if(refCampaignCount != null && refCampaignCount > 0){
                baseOutput.setCode(ApiErrorCode.BIZ_ERROR_SEGMENTATION_IN_CAMPAIGN.getCode());
                baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_SEGMENTATION_IN_CAMPAIGN.getMsg());
                return baseOutput;
            }
            //2更新细分头状态
            SegmentationHead updateSegmentationHead = new SegmentationHead();
            updateSegmentationHead.setId(segmentCreUpdateIn.getSegmentHeadId().intValue());
            updateSegmentationHead.setName(segmentCreUpdateIn.getSegmentName());
            updateSegmentationHead.setPublishStatus(segmentCreUpdateIn.getPublishStatus().byteValue());
            updateSegmentationHead.setCreateTime(new Date());
            segmentationHeadDao.updateById(updateSegmentationHead);
            //3删除掉以前的细分body以及Mongo中对应的细分人群
            segmentationBodyDao.batchDeleteUseHeaderId(segmentCreUpdateIn.getSegmentHeadId().intValue());
            // mongoTemplate.remove(new Query(Criteria.where("segmentationHeadId").is(segmentCreUpdateIn.getSegmentHeadId().intValue())),Segment.class);
            //4重新创建细分body
            createNewSegmentationBody(segmentCreUpdateIn, baseOutput, segmentCreupdateOut, segmentationHead);
        }

        //重新更新细分的人群的详细信息
        segmentCalcService.calcSegmentCover(segmentCreUpdateIn);
        try {
            segmentCalcService.saveSegmentCover();
        } catch (JedisException e) {
            logger.equals("segment write to jedis exception :" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
            return baseOutput;
        }

        //生成返回值信息
        getResultValue(segmentCreUpdateIn, baseOutput, segmentCreupdateOut);
        return baseOutput;
    }

    private void getResultValue(@NotEmpty SegmentCreUpdateIn segmentCreUpdateIn, BaseOutput baseOutput, SegmentCreupdateOut segmentCreupdateOut) {
        segmentCreupdateOut.setId(segmentCreUpdateIn.getSegmentHeadId());
        segmentCreupdateOut.setUpdatetime(DateUtil.getStringFromDate(new Date(),"yyyy-MM-dd hh:mm:ss"));
        segmentCreupdateOut.setOper("Mock 奥巴马");
        baseOutput.getData().add(segmentCreupdateOut);
        baseOutput.setTotal(baseOutput.getData().size());
    }

    private void createNewSegmentationBody(@NotEmpty SegmentCreUpdateIn segmentCreUpdateIn, BaseOutput baseOutput, SegmentCreupdateOut segmentCreupdateOut, SegmentationHead segmentationHead) {
        List<TagGroupsIn> filterGroups = segmentCreUpdateIn.getFilterGroups();
        if(CollectionUtils.isEmpty(filterGroups)){
            getResultValue(segmentCreUpdateIn, baseOutput, segmentCreupdateOut);
        }
        for(TagGroupsIn tagGroupsIn : filterGroups){
            List<SystemTagIn> tagInList = tagGroupsIn.getTagList();
            if(CollectionUtils.isEmpty(tagInList)) continue;
            for(SystemTagIn systemTagIn : tagInList){
                List<SystemValueIn> systemValueIns = systemTagIn.getTagValueList();
                if(CollectionUtils.isEmpty(systemValueIns)) continue;
                for(SystemValueIn systemValueIn : systemValueIns){
                    SegmentationBody segmentationBody = new SegmentationBody();
                    segmentationBody.setHeadId(segmentationHead.getId());
                    segmentationBody.setGroupId(tagGroupsIn.getGroupId());
                    segmentationBody.setGroupName(tagGroupsIn.getGroupName());
                    segmentationBody.setGroupIndex(tagGroupsIn.getGroupIndex());
                    segmentationBody.setTagId(systemTagIn.getTagId());
                    segmentationBody.setTagName(systemTagIn.getTagName());
                    segmentationBody.setTagSeq(systemTagIn.getTagIndex());
                    segmentationBody.setTagExclude(systemTagIn.getTagExclude());
                    segmentationBody.setTagValueId(systemValueIn.getTagValueId());
                    segmentationBody.setTagValueName(systemValueIn.getTagValue());
                    segmentationBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
                    segmentationBodyDao.insert(segmentationBody);
                }
            }
        }
    }

}
