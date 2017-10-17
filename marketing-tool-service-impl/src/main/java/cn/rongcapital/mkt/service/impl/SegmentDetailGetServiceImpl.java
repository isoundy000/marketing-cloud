package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationBody;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentDetailGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentDetialOut;
import cn.rongcapital.mkt.vo.out.SystemTagOut;
import cn.rongcapital.mkt.vo.out.SystemTagValueOut;
import cn.rongcapital.mkt.vo.out.TagGroupsOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by byf on 11/8/16.
 */
@Service
public class SegmentDetailGetServiceImpl implements SegmentDetailGetService{

    @Autowired
    private SegmentationHeadDao segmentationHeadDao;

    @Autowired
    private SegmentationBodyDao segmentationBodyDao;

    @Override
    public BaseOutput getSegmentDetail(@NotNull Long id) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
        SegmentDetialOut segmentDetialOut = new SegmentDetialOut();

        //Todo:1首先通过细分id选出segemnt_head的信息
        SegmentationHead paramSegmentationHead = new SegmentationHead();
        paramSegmentationHead.setId(id.intValue());
        paramSegmentationHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SegmentationHead> segmentationHeadList = segmentationHeadDao.selectList(paramSegmentationHead);
        if(CollectionUtils.isEmpty(segmentationHeadList)){
            baseOutput.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
            baseOutput.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
            return baseOutput;
        }
        SegmentationHead segmentationHead= segmentationHeadList.get(0);
        segmentDetialOut.setSegmentHeadId(id);
        segmentDetialOut.setSegmentName(segmentationHead.getName());
        segmentDetialOut.setPublishStatus(segmentationHead.getPublishStatus()==null?ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH:Integer.valueOf(segmentationHead.getPublishStatus()));
        //Todo:2其次根据细分id选出segment_body然后将其组装进入VO对象(需要思考一下)
        SegmentationBody paramSegmentationBody = new SegmentationBody();
        paramSegmentationBody.setHeadId(id.intValue());
        paramSegmentationBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSegmentationBody.setPageSize(Integer.MAX_VALUE);
        List<SegmentationBody> segmentationBodyList = segmentationBodyDao.selectList(paramSegmentationBody);
        if(CollectionUtils.isEmpty(segmentationBodyList)){
            baseOutput.getData().add(segmentDetialOut);
            return baseOutput;
        }

        //Todo:2.1现在已经将细分按照组序号划分为了不同的细分
        Map<Integer,List<SegmentationBody>> segmentBodyListByGroupIndex = new HashMap<>();
        for(SegmentationBody segmentationBody : segmentationBodyList){
            if(segmentBodyListByGroupIndex.keySet() == null || !segmentBodyListByGroupIndex.containsKey(segmentationBody.getGroupIndex())){
                List<SegmentationBody> subSegmentationBodyList = new ArrayList<>();
                subSegmentationBodyList.add(segmentationBody);
                segmentBodyListByGroupIndex.put(segmentationBody.getGroupIndex(),subSegmentationBodyList);
            }else{
                segmentBodyListByGroupIndex.get(segmentationBody.getGroupIndex()).add(segmentationBody);
            }
        }

        //Todo:2.2遍历Map,生成TagGroup的实体类
        for(Integer key : segmentBodyListByGroupIndex.keySet()){
            List<SegmentationBody> segmentationBodies = segmentBodyListByGroupIndex.get(key);
            TagGroupsOut tagGroupsOut = new TagGroupsOut();
            tagGroupsOut.setGroupId(segmentationBodies.get(0).getGroupId());
            tagGroupsOut.setGroupIndex(key);
            tagGroupsOut.setGroupName(segmentationBodies.get(0).getGroupName());
            Set<String> tagIds = new HashSet<>();
            for(SegmentationBody segmentationBody : segmentationBodies){
                tagIds.add(segmentationBody.getTagId());
            }
            for(String tagId : tagIds){
                SystemTagOut systemTagOut = new SystemTagOut();
                for(SegmentationBody segmentationBody : segmentationBodies){
                    if(tagId != null && tagId.equals(segmentationBody.getTagId())){
                        systemTagOut.setTagId(tagId);
                        systemTagOut.setTagIndex(segmentationBody.getTagSeq());
                        systemTagOut.setTagName(segmentationBody.getTagName());
                        systemTagOut.setTagExclude(segmentationBody.getTagExclude());
                        SystemTagValueOut systemTagValueOut = new SystemTagValueOut();
                        systemTagValueOut.setTagValueId(segmentationBody.getTagValueId());
                        systemTagValueOut.setTagValue(segmentationBody.getTagValueName());
                        systemTagOut.getTagValueList().add(systemTagValueOut);
                    }
                }
                tagGroupsOut.getTagList().add(systemTagOut);
            }
            segmentDetialOut.getFilterGroups().add(tagGroupsOut);
        }


        baseOutput.getData().add(segmentDetialOut);
        return baseOutput;
    }

}
