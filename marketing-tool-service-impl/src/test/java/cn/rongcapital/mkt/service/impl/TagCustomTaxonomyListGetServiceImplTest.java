package cn.rongcapital.mkt.service.impl;

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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.util.ReflectionTestUtils;

import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagCustomTaxonomyListGetService;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.TagSystemCustomTreeOut;
import cn.rongcapital.mkt.vo.out.TagSystemTreeTagOut;

@RunWith(MockitoJUnitRunner.class)
public class TagCustomTaxonomyListGetServiceImplTest {

    private static final int DATA_VALID = 0;
    private static final int DATA_NOT_VALID = 1;

    private TagCustomTaxonomyListGetService tagCustomTaxonomyListGetService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private TagSystemCommonUtilService tagSystemCommonUtilService;

    // 标签
    private TagRecommend tagRecommendOne;
    private TagRecommend tagRecommendTwo;

    // 标签分类
    private SystemCustomTagTree systemCustomTagTreeLevelOne;
    private SystemCustomTagTree systemCustomTagTreeLevelTwo;
    private SystemCustomTagTree systemCustomTagTreeLevelTwoSecond;
    private SystemCustomTagTree systemCustomTagTreeLevelThree;

    // 标签分类的子标签分类tag_tree_id
    private List<String> childrenLevelOne = new ArrayList<String>();
    private List<String> childrenLevelTwo = new ArrayList<String>();

    // 标签分类的标签tag_id
    private List<String> childrenTag = new ArrayList<String>();

    private String percentTen = "10%";
    private String percentZero = "0%";

    @Before
    public void setUp() throws Exception {

        tagRecommendOne = new TagRecommend(null, "tag1", "标签一", null, 0, true, "来自火星的标签", null, null, "", null, 1, 0);
        tagRecommendTwo = new TagRecommend(null, "tag2", "标签二", null, 0, false, "来自地球的标签", null, null, "", null, 2, 0);

        childrenTag.add("tag1");
        childrenTag.add("tag2");
        childrenTag.add("无效tag_id");

        childrenLevelOne.add("level2");
        childrenLevelOne.add("level2-2");

        childrenLevelTwo.add("level3");

        systemCustomTagTreeLevelOne =
                new SystemCustomTagTree(null, "level1", "分类1级", 1, 0, null, null, null, childrenLevelOne, true, null);
        systemCustomTagTreeLevelTwo =
                new SystemCustomTagTree(null, "level2", "分类2级", 2, 0, null, null, null, childrenLevelTwo, true, null);
        systemCustomTagTreeLevelTwoSecond =
                new SystemCustomTagTree(null, "level2-2", "分类2级-2", 2, 0, null, null, null, null, true, childrenTag);
        systemCustomTagTreeLevelThree =
                new SystemCustomTagTree(null, "level3", "分类3级", 2, 0, null, null, null, null, true, null);


        // Mock标签分类
        Mockito.when(mongoTemplate.findOne(new Query(new Criteria("tag_tree_id")
                .is(systemCustomTagTreeLevelOne.getTagTreeId()).and("is_deleted").is(DATA_VALID)),
                SystemCustomTagTree.class)).thenReturn(systemCustomTagTreeLevelOne);
        Mockito.when(mongoTemplate.findOne(new Query(new Criteria("tag_tree_id")
                .is(systemCustomTagTreeLevelTwo.getTagTreeId()).and("is_deleted").is(DATA_VALID)),
                SystemCustomTagTree.class)).thenReturn(systemCustomTagTreeLevelTwo);
        Mockito.when(mongoTemplate.findOne(new Query(new Criteria("tag_tree_id")
                .is(systemCustomTagTreeLevelTwoSecond.getTagTreeId()).and("is_deleted").is(DATA_VALID)),
                SystemCustomTagTree.class)).thenReturn(systemCustomTagTreeLevelTwoSecond);
        Mockito.when(mongoTemplate.findOne(new Query(new Criteria("tag_tree_id")
                .is(systemCustomTagTreeLevelThree.getTagTreeId()).and("is_deleted").is(DATA_VALID)),
                SystemCustomTagTree.class)).thenReturn(systemCustomTagTreeLevelThree);

        // Mock标签
        Mockito.when(mongoTemplate.findOne(
                new Query(new Criteria("tag_id").is(tagRecommendOne.getTagId()).and("status").is(DATA_VALID)),
                TagRecommend.class)).thenReturn(tagRecommendOne);
        Mockito.when(mongoTemplate.findOne(
                new Query(new Criteria("tag_id").is(tagRecommendTwo.getTagId()).and("status").is(DATA_VALID)),
                TagRecommend.class)).thenReturn(tagRecommendTwo);

        // Mock标签覆盖率
        Mockito.when(tagSystemCommonUtilService.getTagCover("tag1")).thenReturn(percentTen);
        Mockito.when(tagSystemCommonUtilService.getTagCover("tag2")).thenReturn(percentZero);


        tagCustomTaxonomyListGetService = new TagCustomTaxonomyListGetServiceImpl();


        ReflectionTestUtils.setField(tagCustomTaxonomyListGetService, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(tagCustomTaxonomyListGetService, "tagSystemCommonUtilService",
                tagSystemCommonUtilService);
    }

    @Test
    public final void testTagCustomTaxonomyListGet() {
        BaseOutput baseOutput;
        TagSystemCustomTreeOut tagSystemCustomTreeOutTwo;
        TagSystemCustomTreeOut tagSystemCustomTreeOutTwoSecond;

        TagSystemTreeTagOut tagSystemTreeTagOutOne;
        TagSystemTreeTagOut tagSystemTreeTagOutTwo;

        // 测试不过滤覆盖率为0的标签
        baseOutput = tagCustomTaxonomyListGetService
                .tagCustomTaxonomyListGet(systemCustomTagTreeLevelOne.getTagTreeId(), null);
        // 判断是否成功
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(2, baseOutput.getTotal());
        
        // 设置返回值到对象
        tagSystemCustomTreeOutTwo = (TagSystemCustomTreeOut) baseOutput.getData().get(0);
        tagSystemCustomTreeOutTwoSecond = (TagSystemCustomTreeOut) baseOutput.getData().get(1);
        tagSystemTreeTagOutOne = (TagSystemTreeTagOut) tagSystemCustomTreeOutTwoSecond.getChildrenTag().get(0);
        tagSystemTreeTagOutTwo = (TagSystemTreeTagOut) tagSystemCustomTreeOutTwoSecond.getChildrenTag().get(1);

        Assert.assertEquals(systemCustomTagTreeLevelTwo.getTagTreeId(), tagSystemCustomTreeOutTwo.getTagTreeId());
        Assert.assertEquals(Integer.valueOf(2), tagSystemCustomTreeOutTwoSecond.getTagCount());
        Assert.assertEquals(systemCustomTagTreeLevelTwoSecond.getTagTreeId(),
                tagSystemCustomTreeOutTwoSecond.getTagTreeId());
        Assert.assertEquals(tagRecommendOne.getTagId(), tagSystemTreeTagOutOne.getTagId());
        Assert.assertEquals(tagRecommendTwo.getTagId(), tagSystemTreeTagOutTwo.getTagId());

        // 测试过滤覆盖率为0的标签
        baseOutput =
                tagCustomTaxonomyListGetService.tagCustomTaxonomyListGet(systemCustomTagTreeLevelOne.getTagTreeId(), 1);
        // 判断是否成功
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(2, baseOutput.getTotal());
        
        // 设置返回值到对象
        tagSystemCustomTreeOutTwo = (TagSystemCustomTreeOut) baseOutput.getData().get(0);
        tagSystemCustomTreeOutTwoSecond = (TagSystemCustomTreeOut) baseOutput.getData().get(1);
        tagSystemTreeTagOutOne = (TagSystemTreeTagOut) tagSystemCustomTreeOutTwoSecond.getChildrenTag().get(0);

        Assert.assertEquals(systemCustomTagTreeLevelTwo.getTagTreeId(), tagSystemCustomTreeOutTwo.getTagTreeId());
        Assert.assertEquals(Integer.valueOf(1), tagSystemCustomTreeOutTwoSecond.getTagCount());
        Assert.assertEquals(systemCustomTagTreeLevelTwoSecond.getTagTreeId(),
                tagSystemCustomTreeOutTwoSecond.getTagTreeId());
        Assert.assertEquals(tagRecommendOne.getTagId(), tagSystemTreeTagOutOne.getTagId());

    }

    @After
    public void tearDown() throws Exception {}



}
