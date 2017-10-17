package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagCustomTaxonomyListGetService;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.TagSystemCustomTreeOut;
import cn.rongcapital.mkt.vo.out.TagSystemTreeTagOut;

@Service
public class TagCustomTaxonomyListGetServiceImpl implements TagCustomTaxonomyListGetService {

    private static final int DATA_VALID = 0;
    private static final int DATA_NOT_VALID = 1;
    private static final Integer SEGMENT_PAGE_SOURCE = 1;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    TagSystemCommonUtilService tagSystemCommonUtilService;


    /**
     * 功能描述：获得自定义分类子分类
     * 
     * 接口：mkt.tag.custom.taxonomy.list.get
     * 
     * @param tagTreeId
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @Override
    public BaseOutput tagCustomTaxonomyListGet(String tagTreeId,Integer pageSourceType) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        // 查询根标签分类
        SystemCustomTagTree systemCustomTagTree = mongoTemplate.findOne(
                new Query(new Criteria("tag_tree_id").is(tagTreeId).and("is_deleted").is(DATA_VALID)),
                SystemCustomTagTree.class);

        if (systemCustomTagTree != null) {
            // 获取根标签分类的子标签分类
            List<TagSystemCustomTreeOut> TagSystemCustomTreeOutLists =
                    getChildrenList(systemCustomTagTree.getChildren(),pageSourceType);
            if (TagSystemCustomTreeOutLists != null) {
                result.getData().addAll(TagSystemCustomTreeOutLists);
                result.setTotal(TagSystemCustomTreeOutLists.size());
            }
        }
        return result;
    }

    /**
     * 功能描述：根据childrens获取标签分类信息
     * 
     * @param childrens
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    public List<TagSystemCustomTreeOut> getChildrenList(List<String> childrens,Integer pageSourceType) {

        if (CollectionUtils.isEmpty(childrens)) {
            return null;
        }

        List<TagSystemCustomTreeOut> tagSystemCustomTreeOutLists = new ArrayList<TagSystemCustomTreeOut>();



        for (String children : childrens) {
            SystemCustomTagTree systemCustomTagTree = findSystemCustomTagTreeById(children);
            if (systemCustomTagTree != null) {

                TagSystemCustomTreeOut tagSystemCustomTreeOut =
                        new TagSystemCustomTreeOut(systemCustomTagTree.getTagTreeId(),
                                systemCustomTagTree.getTagTreeName(), systemCustomTagTree.getLevel(), null, null, null);
                // 设置标签输出
                List<TagSystemTreeTagOut> childrenTagOutLists =
                        generateTagOutByTagId(systemCustomTagTree.getChildrenTag(),pageSourceType);
                tagSystemCustomTreeOut.getChildrenTag().addAll(childrenTagOutLists);
                Integer tagCount = childrenTagOutLists.size();
                // 设置标签分类输出
                List<TagSystemCustomTreeOut> outChildrenLists = getChildrenList(systemCustomTagTree.getChildren(),pageSourceType);
                if (outChildrenLists != null) {
                    tagSystemCustomTreeOut.getChildren().addAll(outChildrenLists);

                    for (TagSystemCustomTreeOut outChildren : outChildrenLists) {
                        tagCount += outChildren.getChildrenTag().size();
                    }
                }
                // 设置标签分类的有多少标签
                tagSystemCustomTreeOut.setTagCount(tagCount);

                tagSystemCustomTreeOutLists.add(tagSystemCustomTreeOut);
            }
        }

        return tagSystemCustomTreeOutLists;
    }

    /**
     * 功能描述：根据tag_tree_id 获取标签分类对象
     * 
     * @param tagTreeId
     * @param level
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private SystemCustomTagTree findSystemCustomTagTreeById(String tagTreeId) {

        return mongoTemplate.findOne(
                new Query(new Criteria("tag_tree_id").is(tagTreeId).and("is_deleted").is(DATA_VALID)),
                SystemCustomTagTree.class);
    }

    /**
     * 功能描述：根据tag_id 获取系统标签
     * 
     * @param tagId
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private TagRecommend findTagRecommendById(String tagId) {
        return mongoTemplate.findOne(new Query(new Criteria("tag_id").is(tagId).and("status").is(DATA_VALID)),
                TagRecommend.class);
    }

    /**
     * 功能描述：根据系统标签id List 获取标签输出List
     * 
     * @param tagIdLists
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private List<TagSystemTreeTagOut> generateTagOutByTagId(List<String> tagIdLists,Integer pageSourceType) {

        List<TagSystemTreeTagOut> tagOutLists = new ArrayList<TagSystemTreeTagOut>();

        if (CollectionUtils.isNotEmpty(tagIdLists)) {
            for (String tagId : tagIdLists) {
                TagRecommend tagRecommend = findTagRecommendById(tagId);
                if (tagRecommend != null) {
                    String tagCover = tagSystemCommonUtilService.getTagCover(tagId);
                    TagSystemTreeTagOut tagSystemTreeTagOut = new TagSystemTreeTagOut(tagRecommend.getTagId(),
                            tagRecommend.getTagName(), tagRecommend.getFlag(), tagRecommend.getTagNameEng(),
                            tagRecommend.getSearchMod(), tagCover, tagRecommend.getTagDesc());
                    if(pageSourceType!=null && pageSourceType.equals(SEGMENT_PAGE_SOURCE) && !"0%".equals(tagCover)){
                        tagOutLists.add(tagSystemTreeTagOut);
                    }else if(pageSourceType == null || !pageSourceType.equals(1)){
                        tagOutLists.add(tagSystemTreeTagOut);
                    }
                }
            }
        }

        return tagOutLists;
    }

}
