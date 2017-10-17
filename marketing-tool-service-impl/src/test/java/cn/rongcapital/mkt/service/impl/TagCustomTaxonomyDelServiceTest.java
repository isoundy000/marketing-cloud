package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

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
import cn.rongcapital.mkt.service.TagCustomTaxonomyDelService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyDelIn;

@RunWith(MockitoJUnitRunner.class)
public class TagCustomTaxonomyDelServiceTest {
    
    @Autowired
    private TagCustomTaxonomyDelService tagCustomTaxonomyDelService;

    @Mock
    MongoTemplate mongoTemplate;
    
    @Before
    public void setUp() throws Exception {
        tagCustomTaxonomyDelService = new TagCustomTaxonomyDelServiceImpl();
        
        Mockito.when(mongoTemplate.updateFirst(any(), any(), eq(SystemCustomTagTree.class))).thenReturn(null);
        Mockito.when(mongoTemplate.findOne(any(), eq(SystemCustomTagTree.class))).thenReturn(null);
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(tagCustomTaxonomyDelService, "mongoTemplate", mongoTemplate);
    }
    
    @Test
    public void testTagCustomTaxonomyDel() {
        
        TagCustomTaxonomyDelIn body = new TagCustomTaxonomyDelIn();
        body.setTagTreeId("2222");
        
        BaseOutput baseOutput = tagCustomTaxonomyDelService.tagCustomTaxonomyDel(body, null);
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        
        
    }

    @After
    public void tearDown() throws Exception {}


}
