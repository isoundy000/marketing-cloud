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
import cn.rongcapital.mkt.service.TagCustomTaxonomyRootSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyRootSaveIn;

@Service
public class TagCustomTaxonomyRootSaveServiceImpl implements TagCustomTaxonomyRootSaveService {

    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final int DATA_VALID = 0;
    private static final int DATA_NOT_VALID = 1;
    private static final int TAG_CUSTOM_TAXONOMY_MAX_SHOW = ApiConstant.TAG_CUSTOM_TAXONOMY_MAX_SHOW;
    private static final Boolean IS_SHOW = true;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 功能描述：创建自定义分类跟分类
     * 
     * 接口：mkt.tag.custom.taxonomy.root.save
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.21
     * @author shuiyangyang
     */
    @Override
    public BaseOutput tagCustomTaxonomyRootSave(TagCustomTaxonomyRootSaveIn body, SecurityContext securityContext) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ONE, null);
        
        if(body == null) {
            result.setTotal(ApiConstant.INT_ZERO);
            return result;
        }
        
        String tagTreeId = body.getTagTreeId();
        String tagTreeName = body.getTagTreeName();

        SystemCustomTagTree systemCustomTagTree = mongoTemplate.findOne(
                new Query(new Criteria("tag_tree_id").is(tagTreeId).and("isDeleted").is(DATA_VALID)),
                SystemCustomTagTree.class);

        // 如果存在则更新,不存在则新增
        if (systemCustomTagTree != null) {
            mongoTemplate.updateFirst(
                    new Query(new Criteria("tag_tree_id").is(tagTreeId).and("isDeleted").is(DATA_VALID)),
                    new Update().set("tag_tree_name", tagTreeName).set("update_time", new Date()),
                    SystemCustomTagTree.class);
        } else {
            List<String> children = new ArrayList<String>();
            
            // 插入默认子标签
            if(CollectionUtils.isNotEmpty(body.getChildren())) {
                String tagTreeIdLevelTwo = generateTagTreeId();
                SystemCustomTagTree systemCustomTagTreeLevelTwo = new SystemCustomTagTree(null, tagTreeIdLevelTwo, body.getChildren().get(0).getTagTreeName(), LEVEL_TWO, DATA_VALID, tagTreeName,
                    new Date(), new Date(), new ArrayList<String>(), null, new ArrayList<String>());
                mongoTemplate.insert(systemCustomTagTreeLevelTwo);
                children.add(tagTreeIdLevelTwo);
            }
            
            systemCustomTagTree = new SystemCustomTagTree(null, generateTagTreeId(), tagTreeName, LEVEL_ONE, DATA_VALID, null,
                    new Date(), new Date(), children, isShow(), null);

            mongoTemplate.insert(systemCustomTagTree);

        }

        return result;
    }

    /**
     * 功能描述：如果已经显示的数量超过6个则默认不显示
     * 
     * @return
     * @Date 2016.12.21
     * @author shuiyangyang
     */
    private Boolean isShow() {
        long count = mongoTemplate.count(new Query(new Criteria("isDeleted").is(DATA_VALID).and("is_show").is(true)),
                SystemCustomTagTree.class);
        return count < TAG_CUSTOM_TAXONOMY_MAX_SHOW ? IS_SHOW : !IS_SHOW;
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

}
