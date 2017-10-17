package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.service.TagCustomTaxonomyRootListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TagCustomTaxonomyRootListGetServiceImpl implements TagCustomTaxonomyRootListGetService {

    private static final int DATA_VALID = 0;// 数据未逻辑删除
    private static final Boolean IS_SHOW = true;
    private static final int LEVEL_ONE = 1;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 功能描述：获取自定义标签分类全部一级分类
     * 
     * 接口：mkt.tag.custom.taxonomy.root.list.get
     * 
     * @param nolyShow
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @Override
    public BaseOutput tagCustomTaxonomyRootListGet(Boolean nolyShow) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        Criteria criteria = new Criteria();
        // 查询正常数据
        criteria.and("is_deleted").is(DATA_VALID);
        criteria.and("level").is(LEVEL_ONE);
        // 判断是否只显示is_show为true的数据
        if (IS_SHOW.equals(nolyShow)) {
            criteria.and("is_show").is(IS_SHOW);
        }

        List<SystemCustomTagTree> systemCustomTagTreeLists =
                mongoTemplate.find(new Query(criteria), SystemCustomTagTree.class);

        if (CollectionUtils.isNotEmpty(systemCustomTagTreeLists)) {
            result.setTotal(systemCustomTagTreeLists.size());
            for (SystemCustomTagTree systemCustomTagTreeList : systemCustomTagTreeLists) {
                Map<String, Object> map = new HashMap<String, Object>();
                String tagTreeId = systemCustomTagTreeList.getTagTreeId();
                String tagTreeName = systemCustomTagTreeList.getTagTreeName();
                Boolean isShow = systemCustomTagTreeList.getIsShow();
                map.put("tag_tree_id", tagTreeId == null ? "" : tagTreeId);
                map.put("tag_tree_name", tagTreeName == null ? "" : tagTreeName);
                map.put("is_show", isShow == null ? false : isShow);
                result.getData().add(map);
            }
        }

        return result;
    }

}
