package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.enums.CustomTagShownEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.vo.out.CustomTagColumnShown;
import cn.rongcapital.mkt.vo.out.CustomTagOutput;
import cn.rongcapital.mkt.vo.out.CustomTagShown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.CustomTagGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CustomTagGetServiceImpl implements CustomTagGetService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FindCustomTagInfoService findCustomTagInfoService;

    private final String TAG_TYPE = "tag_type";
    private final String CUSTOM_TAG_LIST = "custom_tag_list";
    private final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    private final String CREATE_TIME = "create_time";

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public BaseOutput getCustomTagList(String method, String userToken, Integer index, Integer size) {
        CustomTagOutput customTagOutput = new CustomTagOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO);
        getShownColumnsInfo(customTagOutput);

        Query countQuery = new Query(Criteria.where(TAG_TYPE).is(ApiConstant.CUSTOM_TAG_LEAF_TYPE));
        countQuery.with(new Sort(Sort.Direction.DESC, CREATE_TIME));
        Long totalTagCount = mongoTemplate.count(countQuery,BaseTag.class);
        customTagOutput.setTotalCount(totalTagCount.intValue());

        Query tagQuery = new Query(Criteria.where(TAG_TYPE).is(ApiConstant.CUSTOM_TAG_LEAF_TYPE)).with(new Sort(Sort.Direction.DESC, CREATE_TIME)).skip((index -1 ) * size).limit(size);
        List<BaseTag> customTagLeafs = mongoTemplate.find(tagQuery,BaseTag.class);
        if(!CollectionUtils.isEmpty(customTagLeafs)){
            for(BaseTag baseTag : customTagLeafs){
                CustomTagShown customTagShown = new CustomTagShown();
                customTagShown.setTagId(baseTag.getTagId());
                customTagShown.setTagName(baseTag.getTagName());
                customTagShown.setTagSource(baseTag.getSource());
                customTagShown.setCreateTime(DateUtil.getStringFromDate(baseTag.getCreateTime(),FORMAT_STRING));
                Query query = new Query(Criteria.where(CUSTOM_TAG_LIST).is(baseTag.getTagId()));
                Long audienceCoverCount = mongoTemplate.count(query, DataParty.class);
                customTagShown.setCoverAudienceCount(audienceCoverCount.intValue());
                customTagOutput.getCustomTagShownList().add(customTagShown);
            }
        }

        customTagOutput.setTotal(customTagLeafs.size());
        return customTagOutput;
    }

    private void getShownColumnsInfo(CustomTagOutput customTagOutput) {
        for(CustomTagShownEnum customTagShownEnum : CustomTagShownEnum.values()){
            CustomTagColumnShown customTagColumnShown = new CustomTagColumnShown();
            customTagColumnShown.setColId(customTagShownEnum.getColumnId());
            customTagColumnShown.setColCode(customTagShownEnum.getColumnCode());
            customTagColumnShown.setColName(customTagShownEnum.getColumnName());
            customTagOutput.getCustomTagColumnShownList().add(customTagColumnShown);
        }
    }
}
