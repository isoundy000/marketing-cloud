package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
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
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.service.TagCustomTaxonomyDelService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyDelIn;

@Service
public class TagCustomTaxonomyDelServiceImpl implements TagCustomTaxonomyDelService {

    private static final int DATA_VALID = 0;
    private static final int DATA_NOT_VALID = 1;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 功能描述：删除自定义分类（逻辑删除）
     * 
     * 接口： mkt.tag.custom.taxonomy.del
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @Override
    public BaseOutput tagCustomTaxonomyDel(TagCustomTaxonomyDelIn body, SecurityContext securityContext) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ONE, null);
        // 删除父节点的数据
        mongoTemplate.updateFirst(
                new Query(new Criteria("children").is(body.getTagTreeId()).and("is_deleted").is(DATA_VALID)),
                new Update().pull("children", body.getTagTreeId()), SystemCustomTagTree.class);

        // 删除数据及子节点
        List<String> children = new ArrayList<String>();
        children.add(body.getTagTreeId());
        delByTagTreeIdList(children);

        return result;
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
                        new Query(new Criteria("tag_tree_id").is(tagTreeId).and("is_deleted").is(DATA_VALID)),
                        SystemCustomTagTree.class);
                if (systemCustomTagTree != null) {
                    delByTagTreeIdList(systemCustomTagTree.getChildren());
                }
                mongoTemplate.updateFirst(new Query(new Criteria("tag_tree_id").is(tagTreeId)),
                        new Update().set("is_deleted", DATA_NOT_VALID), SystemCustomTagTree.class);
            }
        }
    }

}
