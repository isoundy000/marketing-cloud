package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagCustomTaxonomySaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomySaveIn;

@RunWith(MockitoJUnitRunner.class)
public class TagCustomTaxonomySaveServiceTest {

    private static final int DATA_VALID = 0;
    private static final int DATA_NOT_VALID = 1;


    @Autowired
    TagCustomTaxonomySaveService tagCustomTaxonomySaveService;

    @Mock
    MongoTemplate mongoTemplate;

    private SystemCustomTagTree systemCustomTagTreeLevelOne = new SystemCustomTagTree();
    private SystemCustomTagTree systemCustomTagTreeLevelTwo = new SystemCustomTagTree();

    private TagCustomTaxonomySaveIn body;

    private List<String> childrenLevelOne = new ArrayList<String>();

    @Before
    public void setUp() throws Exception {



        String bodyStr =
                "{\"ver\":\"1.6.1\",\"user_id\":\"user4\",\"user_token\":\"vwFNCcBG9ucb3KRvJ9MgPyfjeQQMFkDD\",\"tag_tree_id\":\"level-1\",\"children\":[null,{\"tag_tree_id\":\"level-2-1\",\"tag_tree_name\":\"二级分类-1\",\"children\":[],\"children_tag\":[]},{\"tag_tree_id\":\"\",\"tag_tree_name\":\"二级分类-2\",\"children\":[],\"children_tag\":[{\"tag_id\":\"LBej3qLy\"},{\"tag_id\":\"X4yylWlQ\"},{\"tag_id\":\"X4yylWlQ\"}]},{\"tag_tree_id\":\"\",\"tag_tree_name\":\"二级分类-3\",\"children\":[{\"tag_tree_id\":\"\",\"tag_tree_name\":\"三级分类-1\",\"children\":[],\"children_tag\":[]}],\"children_tag\":[]}]}";
        ObjectMapper objectMapper = new ObjectMapper();
        body = objectMapper.readValue(bodyStr, TagCustomTaxonomySaveIn.class);

        childrenLevelOne.add("level-2-1");
        childrenLevelOne.add("无效的tag_tree_id");
        childrenLevelOne.add("无效的tag_tree_id-2");
        childrenLevelOne.add(null);
        systemCustomTagTreeLevelOne.setTagTreeId("level-1");
        systemCustomTagTreeLevelOne.setTagTreeName("一级分类-1");
        systemCustomTagTreeLevelOne.setChildren(childrenLevelOne);
        systemCustomTagTreeLevelTwo.setTagTreeId("level-2-1");
        systemCustomTagTreeLevelTwo.setTagTreeName("二级分类-1");


        tagCustomTaxonomySaveService = new TagCustomTaxonomySaveServiceImpl();

        Mockito.when(mongoTemplate.findOne(
                new Query(new Criteria("tag_tree_id").is("level-1").and("isDeleted").is(DATA_VALID)),
                SystemCustomTagTree.class)).thenReturn(systemCustomTagTreeLevelOne);
        Mockito.when(mongoTemplate.findOne(
                new Query(new Criteria("tag_tree_id").is("level-2-1").and("isDeleted").is(DATA_VALID)),
                SystemCustomTagTree.class)).thenReturn(systemCustomTagTreeLevelTwo);
        Mockito.when(mongoTemplate.findOne(
                new Query(new Criteria("tag_tree_id").is("无效的tag_tree_id").and("isDeleted").is(DATA_VALID)),
                SystemCustomTagTree.class)).thenReturn(new SystemCustomTagTree());
        Mockito.when(mongoTemplate.findOne(new Query(new Criteria("tag_id").is("X4yylWlQ").and("status").is(DATA_VALID)),
                TagRecommend.class)).thenReturn(new TagRecommend());

        ReflectionTestUtils.setField(tagCustomTaxonomySaveService, "mongoTemplate", mongoTemplate);
    }

    @Test
    public void testTagCustomTaxonomySave() {
        BaseOutput baseOutput;

        // 测试逻辑正常
        baseOutput = tagCustomTaxonomySaveService.tagCustomTaxonomySave(body, null);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(ApiConstant.INT_ONE, baseOutput.getTotal());
        
        
        // 测试根据tag_tree_id查找不到数据
        Mockito.when(mongoTemplate.findOne(
            new Query(new Criteria("tag_tree_id").is("level-1").and("isDeleted").is(DATA_VALID)),
            SystemCustomTagTree.class)).thenReturn(null);
        baseOutput = tagCustomTaxonomySaveService.tagCustomTaxonomySave(body, null);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(ApiConstant.INT_ZERO, baseOutput.getTotal());

    }

    @After
    public void tearDown() throws Exception {}



}
