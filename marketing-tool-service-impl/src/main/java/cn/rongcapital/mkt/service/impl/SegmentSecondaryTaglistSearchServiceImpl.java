package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.service.SegmentSecondaryTaglistSearchService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SystemValueIn;
import cn.rongcapital.mkt.vo.in.TagListSecondarySearchIn;
import cn.rongcapital.mkt.vo.out.SearchTagValueOut;
import cn.rongcapital.mkt.vo.out.SystemTagValueOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by byf on 11/9/16.
 */

@Service
public class SegmentSecondaryTaglistSearchServiceImpl implements SegmentSecondaryTaglistSearchService {

    @Autowired
    private TagValueCountDao tagValueCountDao;

    @Override
    public BaseOutput searchSegmentSecondaryTaglist(TagListSecondarySearchIn tagListSecondarySearchIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        if(tagListSecondarySearchIn == null){
            baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
            return baseOutput;
        }

        TagValueCount paramTagValueCount = new TagValueCount();
        paramTagValueCount.setTagId(tagListSecondarySearchIn.getTagId());
        paramTagValueCount.setTagValue(tagListSecondarySearchIn.getKeyWord());

        List<TagValueCount> tagValueCountList = tagValueCountDao.selectTagValueCountListByKeyWord(paramTagValueCount);
        if(CollectionUtils.isEmpty(tagValueCountList)){
            return baseOutput;
        }

        for(TagValueCount tagValueCount : tagValueCountList){
            if(tagValueCount.getIsTag() == null || tagValueCount.getIsTag().equals("1")) continue;
            boolean flag = isTagValueAlreadySelected(tagValueCount.getTagValue(),tagListSecondarySearchIn.getSelectTagValueList());
            if(!flag){
                SearchTagValueOut searchTagValueOut = new SearchTagValueOut();
                searchTagValueOut.setTagValue(tagValueCount.getTagValue());
                searchTagValueOut.setTagValueSeq(tagValueCount.getTagValueSeq());
                searchTagValueOut.setValueCount(tagValueCount.getValueCount() == null? 0 : tagValueCount.getValueCount().intValue());
                baseOutput.getData().add(searchTagValueOut);
            }
        }

        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }

    private boolean isTagValueAlreadySelected(String tagValue, List<SystemValueIn> selectTagValueList) {
        if(CollectionUtils.isEmpty(selectTagValueList)) return false;
        for(SystemValueIn systemValueIn : selectTagValueList){
            if(tagValue.equals(systemValueIn.getTagValue())) return true;
        }
        return false;
    }
}
