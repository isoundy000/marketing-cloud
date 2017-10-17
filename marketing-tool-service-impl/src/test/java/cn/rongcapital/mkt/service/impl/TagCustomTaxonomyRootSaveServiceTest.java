package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.List;

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

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.service.TagCustomTaxonomyRootSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyRootSaveIn;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomySaveChildrenIn;

@RunWith(MockitoJUnitRunner.class)
public class TagCustomTaxonomyRootSaveServiceTest {

    @Autowired
    TagCustomTaxonomyRootSaveService tagCustomTaxonomyRootSaveService;

    @Mock
    MongoTemplate mongoTemplate;
    
    private SystemCustomTagTree systemCustomTagTree;

    @Before
    public void setUp() throws Exception {
        
        tagCustomTaxonomyRootSaveService = new TagCustomTaxonomyRootSaveServiceImpl();
        
        Mockito.when(mongoTemplate.findOne(any(), eq(SystemCustomTagTree.class))).thenReturn(systemCustomTagTree);

        ReflectionTestUtils.setField(tagCustomTaxonomyRootSaveService, "mongoTemplate", mongoTemplate);
    }

    @Test
    public void testTagCustomTaxonomyRootSave() {
        BaseOutput baseOutput;
        TagCustomTaxonomyRootSaveIn body =null;
        
        // 测试输入为null
        baseOutput = tagCustomTaxonomyRootSaveService.tagCustomTaxonomyRootSave(body, null);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(ApiConstant.INT_ZERO, baseOutput.getTotal());
        
        // 新增根节点
        body = new TagCustomTaxonomyRootSaveIn();
        body.setTagTreeId("test");
        body.setTagTreeName("test");
        List<TagCustomTaxonomySaveChildrenIn> children = new ArrayList<TagCustomTaxonomySaveChildrenIn>();
        children.add(new TagCustomTaxonomySaveChildrenIn());
        body.setChildren(children);
        baseOutput = tagCustomTaxonomyRootSaveService.tagCustomTaxonomyRootSave(body, null);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(ApiConstant.INT_ONE, baseOutput.getTotal());
        
        // 测试修改根结点
        systemCustomTagTree = new SystemCustomTagTree();
        Mockito.when(mongoTemplate.findOne(any(), eq(SystemCustomTagTree.class))).thenReturn(systemCustomTagTree);
        body.setChildren(null);
        baseOutput = tagCustomTaxonomyRootSaveService.tagCustomTaxonomyRootSave(body, null);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(ApiConstant.INT_ONE, baseOutput.getTotal());
        
        // 
        
    }

    @After
    public void tearDown() throws Exception {}

}
