package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.TagSystemTreeListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.TagSystemTreeOut;
import cn.rongcapital.mkt.vo.out.TagSystemTreeTagOut;

@Service
public class TagSystemTreeListGetServiceImpl implements TagSystemTreeListGetService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final Integer DATA_STATUS_VALID = 0;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 系统标签（树形）结构接口 接口：mkt.tag.system.tree.list.get
     * 
     * @return
     * @author shuiyangyang
     * @Date 2016-11-09
     */
    @Override
    public BaseOutput getTagSystemTreeList() {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        new ArrayList<Object>());
        // 获取所有一级标签分类
        List<TagTree> tagTreeFirstLists = mongoTemplate.find(
                        new Query(Criteria.where("level").is(1).and("status")
                                        .is(TagSystemTreeListGetServiceImpl.DATA_STATUS_VALID)),
                        TagTree.class);
        if (tagTreeFirstLists == null || tagTreeFirstLists.size() <= 0) {
            logger.debug("数据异常,查询到0个一级标签分类");
            return result;
        }
        result.setTotal(tagTreeFirstLists.size());

        for (TagTree tagTreeFirst : tagTreeFirstLists) {
            TagSystemTreeOut tagSystemTreeOut =
                            new TagSystemTreeOut(tagTreeFirst.getTagId(), tagTreeFirst.getTagName(),
                                            tagTreeFirst.getLevel(), new ArrayList<Object>());

            // 获取二级标签分类tag_id
            List<String> tagTreeIdSecondLists = tagTreeFirst.getChildren();

            // 如果孩子节点不存在或者为空跳过这次循环
            if (tagTreeIdSecondLists == null || tagTreeIdSecondLists.size() <= 0) {
                result.getData().add(tagSystemTreeOut);
                continue;
            }
            for (String tagTreeIdSecond : tagTreeIdSecondLists) {
                // 获取二级标签分类
                TagTree tagTreeSecond = mongoTemplate.findOne(
                                new Query(Criteria.where("tag_id").is(tagTreeIdSecond).and("status")
                                                .is(TagSystemTreeListGetServiceImpl.DATA_STATUS_VALID)),
                                TagTree.class);

                // 如果不存在则跳出本次循环
                if (tagTreeSecond == null) {
                    continue;
                }

                TagSystemTreeOut tagTreeSecondOut = new TagSystemTreeOut(tagTreeSecond.getTagId(),
                                tagTreeSecond.getTagName(), tagTreeSecond.getLevel(),
                                new ArrayList<Object>());

                // 获取tag的tag_id列表
                List<String> tagIdLists = tagTreeSecond.getChildren();

                // 如果无tag则跳出循环, 并把二级标签分类加入到一级标签分类下
                if (tagIdLists == null || tagIdLists.size() <= 0) {
                    tagSystemTreeOut.getChildren().add(tagTreeSecondOut);
                    continue;
                }

                for (String tagId : tagIdLists) {
                    // 根据tag_id获取tag
                    TagRecommend tag = mongoTemplate.findOne(
                                    new Query(Criteria.where("tag_id").is(tagId).and("status")
                                                    .is(TagSystemTreeListGetServiceImpl.DATA_STATUS_VALID)),
                                    TagRecommend.class);
                    if (tag != null) {
                        TagSystemTreeTagOut tagOut = new TagSystemTreeTagOut(tag.getTagId(),
                                        tag.getTagName(), tag.getFlag(), tag.getTagNameEng(),
                                        tag.getSearchMod(), tag.getTagList());
                        tagTreeSecondOut.getChildren().add(tagOut);
                    }

                }

                // 添加二级标签分类
                tagSystemTreeOut.getChildren().add(tagTreeSecondOut);

            }
            result.getData().add(tagSystemTreeOut);
        }
        return result;
    }

}
