package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.service.TagCustomTaxonomyRootListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class TagCustomTaxonomyRootListGetServiceTest {
    
    @Autowired
    TagCustomTaxonomyRootListGetService tagCustomTaxonomyRootListGetService;
    
    @Mock
    MongoTemplate mongoTemplate;
    
    List<SystemCustomTagTree> systemCustomTagTreeLists;

    @Before
    public void setUp() throws Exception {
        
        systemCustomTagTreeLists = new ArrayList<SystemCustomTagTree>();
        SystemCustomTagTree systemCustomTagTree = new SystemCustomTagTree();
        systemCustomTagTree.setTagTreeId("eULJTgsX1481794865892");
        systemCustomTagTree.setTagTreeName("自定义分类");
        systemCustomTagTree.setIsShow(true);
        systemCustomTagTreeLists.add(systemCustomTagTree);
        
        tagCustomTaxonomyRootListGetService = new TagCustomTaxonomyRootListGetServiceImpl();
        
        Mockito.when(mongoTemplate.find(any(), eq(SystemCustomTagTree.class))).thenReturn(systemCustomTagTreeLists);
        
        ReflectionTestUtils.setField(tagCustomTaxonomyRootListGetService, "mongoTemplate", mongoTemplate);
        
    }

    @Test
    public void testTagCustomTaxonomyRootListGet() {
        BaseOutput baseOutput = tagCustomTaxonomyRootListGetService.tagCustomTaxonomyRootListGet(true);
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(systemCustomTagTreeLists.size(), baseOutput.getTotal());
        
        Assert.assertEquals(systemCustomTagTreeLists.get(0).getTagTreeId(), ((Map)baseOutput.getData().get(0)).get("tag_tree_id"));
        Assert.assertEquals(systemCustomTagTreeLists.get(0).getTagTreeName(), ((Map)baseOutput.getData().get(0)).get("tag_tree_name"));
        Assert.assertEquals(systemCustomTagTreeLists.get(0).getIsShow(), ((Map)baseOutput.getData().get(0)).get("is_show"));
        
    }
    
    @After
    public void tearDown() throws Exception {}

}
