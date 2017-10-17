package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagCustomTaxonomySaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomySaveChildrenIn;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomySaveChildrenTagIn;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomySaveIn;

@Service
public class TagCustomTaxonomySaveServiceImpl implements TagCustomTaxonomySaveService {

    private static final int isDeleted = 0;
    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final int DATA_VALID = 0;
    private static final int DATA_NOT_VALID = 1;

    @Autowired
    MongoTemplate mongoTemplate;


    /**
     * 功能描述：保存自定义分类子分类
     * 
     * 接口：mkt.tag.custom.taxonomy.save
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @Override
    public BaseOutput tagCustomTaxonomySave(TagCustomTaxonomySaveIn body, SecurityContext securityContext) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);
        SystemCustomTagTree systemCustomTagTree = findSystemCustomTagTreeById(body.getTagTreeId());
        if(systemCustomTagTree == null ) {
            return result;
        }
        result.setTotal(ApiConstant.INT_ONE);
        
        TagCustomTaxonomySaveChildrenIn tagCustomTaxonomySaveRoot = new TagCustomTaxonomySaveChildrenIn();
        tagCustomTaxonomySaveRoot.setTagTreeId(body.getTagTreeId());
        tagCustomTaxonomySaveRoot.setTagTreeName(systemCustomTagTree.getTagTreeName());
        tagCustomTaxonomySaveRoot.setChildren(body.getChildren());
        tagCustomTaxonomySaveRoot.setChildrenTag(body.getChildrenTag());
        
        // 保存自定义分类
        saveChildren(tagCustomTaxonomySaveRoot, null, LEVEL_ONE);
        
        return result;
    }

    /**
     * 功能描述：保存一级标签下的所有标签
     * 
     * @param children
     * @param parent
     * @param level
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private String saveChildren(TagCustomTaxonomySaveChildrenIn children, String parent, int level) {

        if (children == null) {
            return null;
        }

        String tagTreeId = children.getTagTreeId();
        String tagTreeName = children.getTagTreeName();

        List<TagCustomTaxonomySaveChildrenTagIn> tagCustomTaxonomySaveChildrenTagInLists = children.getChildrenTag();
        
        
        // 去除无效的tag_id
        List<String> childrenTag = checkTag(tagCustomTaxonomySaveChildrenTagInLists);

        List<String> childrenOld = null;
        SystemCustomTagTree systemCustomTagTreeOld = findSystemCustomTagTreeById(tagTreeId);
        if (systemCustomTagTreeOld != null) {
            childrenOld = systemCustomTagTreeOld.getChildren();
        }

        // 防止出现空指针异常
        if (childrenOld == null) {
            childrenOld = new ArrayList<String>();
        }

        // 新的children
        List<String> childrenNew = new ArrayList<String>();

        List<TagCustomTaxonomySaveChildrenIn> tagCustomTaxonomySaveChildrenInLists = children.getChildren();
        if (CollectionUtils.isNotEmpty(tagCustomTaxonomySaveChildrenInLists)) {
            for (TagCustomTaxonomySaveChildrenIn tagCustomTaxonomySaveChildrenInList : tagCustomTaxonomySaveChildrenInLists) {
                String childrenId = saveChildren(tagCustomTaxonomySaveChildrenInList, tagTreeName, level + 1);
                if (childrenId != null) {
                    childrenNew.add(childrenId);
                    childrenOld.remove(childrenId);
                }
            }

            // 删除没有用到的二级分类
            delByTagTreeIdList(childrenOld);
        }

        if (systemCustomTagTreeOld != null) {
            // 更新一级分类
            systemCustomTagTreeUpdateById(tagTreeId, tagTreeName, parent, childrenNew, level, childrenTag);
        } else {
            tagTreeId = generateTagTreeId();
            SystemCustomTagTree systemCustomTagTreeLevelOne = new SystemCustomTagTree(null, tagTreeId, tagTreeName,
                    level, isDeleted, parent, new Date(), new Date(), childrenNew, null, childrenTag);
            systemCustomTagTreeInsert(systemCustomTagTreeLevelOne);
        }


        return tagTreeId;
    }



    /**
     * 功能描述：向mongodb中插入自定义标签分类
     * 
     * @param systemCustomTagTree
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private void systemCustomTagTreeInsert(SystemCustomTagTree systemCustomTagTree) {
        mongoTemplate.insert(systemCustomTagTree);
    }

    /**
     * 功能描述：根据tag_tree_id 更新系统标签分类
     * 
     * @param tagTreeId
     * @param tagTreeName
     * @param parent
     * @param children
     * @param level
     * @param childrenTag
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private void systemCustomTagTreeUpdateById(String tagTreeId, String tagTreeName, String parent,
            List<String> children, int level, List<String> childrenTag) {

        Update update = new Update();
        update.set("tag_tree_name", tagTreeName);
        if (parent != null) {
            update.set("parent", parent);
        }
        update.set("children", children);
        update.set("level", level);
        update.set("childrenTag", childrenTag);
        update.set("update_time", new Date());


        mongoTemplate.updateFirst(new Query(new Criteria("tag_tree_id").is(tagTreeId).and("isDeleted").is(DATA_VALID)),
                update, SystemCustomTagTree.class);
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
                new Query(new Criteria("tag_tree_id").is(tagTreeId).and("isDeleted").is(DATA_VALID)),
                SystemCustomTagTree.class);
    }

    /**
     * 功能描述：生成tag_tree_id
     * 
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private String generateTagTreeId() {
        return GenerateUUid.generateShortUuid() + new Date().getTime();
    }


    /**
     * 功能描述：根据tag_tree_list逻辑删除标签分类
     * 
     * @param children
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private void delByTagTreeIdList(List<String> children) {
        if (CollectionUtils.isNotEmpty(children)) {
            for (String tagTreeId : children) {
                SystemCustomTagTree systemCustomTagTree = mongoTemplate.findOne(
                        new Query(new Criteria("tag_tree_id").is(tagTreeId).and("isDeleted").is(DATA_VALID)),
                        SystemCustomTagTree.class);
                if (systemCustomTagTree != null) {
                    delByTagTreeIdList(systemCustomTagTree.getChildren());
                }
                mongoTemplate.updateFirst(new Query(new Criteria("tag_tree_id").is(tagTreeId)),
                        new Update().set("isDeleted", DATA_NOT_VALID), SystemCustomTagTree.class);
            }
        }
    }

    /**
     * 功能描述：去除不合法的tag_id
     * 
     * @param tagList
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    private List<String> checkTag(List<TagCustomTaxonomySaveChildrenTagIn> tagCustomTaxonomySaveChildrenTagInLists) {
        List<String> tagListNew = new ArrayList<String>();

        if (CollectionUtils.isNotEmpty(tagCustomTaxonomySaveChildrenTagInLists)) {
            for (TagCustomTaxonomySaveChildrenTagIn tagCustomTaxonomySaveChildrenTagInList : tagCustomTaxonomySaveChildrenTagInLists) {
                String tagId = tagCustomTaxonomySaveChildrenTagInList.getTagId();
                TagRecommend tagRecommend = mongoTemplate.findOne(
                        new Query(new Criteria("tag_id").is(tagId).and("status").is(DATA_VALID)), TagRecommend.class);
                if (tagRecommend != null) {
                    // 去除重复
                    if (!tagListNew.contains(tagId)) {
                        tagListNew.add(tagId);
                    }
                }
            }
        }
        return tagListNew;
    }

}
