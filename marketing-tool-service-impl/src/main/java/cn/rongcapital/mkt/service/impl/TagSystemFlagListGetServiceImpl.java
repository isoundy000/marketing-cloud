package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
import cn.rongcapital.mkt.service.TagSystemFlagListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.TagSystemFlagListGetOut;

@Service
public class TagSystemFlagListGetServiceImpl implements TagSystemFlagListGetService {

    private static final Integer DATA_STATUS_VALID = 0;

    @Autowired
    private MongoTemplate mongoTemplate;
    
	@Autowired
	private TagSystemCommonUtilService commonUtilService;

    /**
     * 获取推荐标签
     * 接口名称：  mkt.tag.system.flag.list.get
     * 
     * @return
     * @author shuiyangyang
     * @Date 2016-11-09
     */
    @Override
    public BaseOutput getTagSystemFlagList() {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        // 从mongodb tagRecommend中取flag为true的数据
        List<TagRecommend> flagTagList = mongoTemplate.find(
                        new Query(Criteria.where("flag").is(true).and("status")
                                        .is(TagSystemFlagListGetServiceImpl.DATA_STATUS_VALID)),
                        TagRecommend.class);
        // 判断返回值是否为null和空
        if (flagTagList != null && flagTagList.size() > 0) {
            for (TagRecommend flagTag : flagTagList) {
            	String tagId = flagTag.getTagId();
            	String tagCover = commonUtilService.getTagCover(tagId);
            	
                TagSystemFlagListGetOut tagSystemFlagListGetOut = new TagSystemFlagListGetOut(
                		tagId, flagTag.getTagName(), flagTag.getTagList(),
                                flagTag.getFlag(), flagTag.getTagDesc(), flagTag.getTagNameEng(),
                                flagTag.getSeq(), flagTag.getSearchMod());
                if(!"0%".equals(tagCover)){
                	result.getData().add(tagSystemFlagListGetOut);
                }
            }
            result.setTotal(result.getData().size());
        }
        return result;
    }
    

}
