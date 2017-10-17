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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.TagSystemTreeListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.TagSystemTreeOut;
import cn.rongcapital.mkt.vo.out.TagSystemTreeTagOut;

/**
 * TagSystemTreeListGetServiceImpl测试
 * 
 * @return
 * @author shuiyangyang
 * @Date 2016-11-09
 */
@RunWith(MockitoJUnitRunner.class)
public class TagSystemTreeListGetServiceTest {
    
    private TagSystemTreeListGetService tagSystemTreeListGetService;
    
    @Mock
    private MongoTemplate mongoTemplate;
    
    private List<TagTree> tagTreeFirstLists;
    
    private TagTree tagTreeSecond;
    
    private TagRecommend tag;

    @Before
    public void setUp() throws Exception {
        
        List<String> stringList = new ArrayList<String>();
        stringList.add("111");
        
        // 设置一级分类
        tagTreeFirstLists = new ArrayList<TagTree>();
        TagTree tagTreeFirst = new TagTree(null, "uodxRyod", "用户属性", 1, null, 0, null, null, null, null, stringList);
        tagTreeFirstLists.add(tagTreeFirst);
        
        // 设置二级分类
        tagTreeSecond = new TagTree(null, "YWhNcSkt", "个人信息", 2, null, 0, null, null, null, null, stringList);
        
        tag = new TagRecommend(null, "68QkbhSa", "蛋糕乳脂系列", stringList, 0, false,
                        "用户购买该品类至少一次", null, null, "creamCakeSeries", "incake", 17, 1);
        
        
        tagSystemTreeListGetService = new TagSystemTreeListGetServiceImpl();
        
        Mockito.when(mongoTemplate.find(any(), eq(TagTree.class))).thenReturn(tagTreeFirstLists);
        Mockito.when(mongoTemplate.findOne(any(), eq(TagTree.class))).thenReturn(tagTreeSecond);
        Mockito.when(mongoTemplate.findOne(any(), eq(TagRecommend.class))).thenReturn(tag);
        
        ReflectionTestUtils.setField(tagSystemTreeListGetService, "mongoTemplate", mongoTemplate);
    }
    
    @Test
    public void testGetTagSystemTreeList() {
        BaseOutput resutl = tagSystemTreeListGetService.getTagSystemTreeList();
        System.out.println(resutl.toString());
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), resutl.getCode());
        Assert.assertEquals(tagTreeFirstLists.size(), resutl.getTotal());

        // 测试data是否正确
        List<TagSystemTreeOut> actualData = new ArrayList<TagSystemTreeOut>();
        TagSystemTreeTagOut TagSystemTreeTagOut =
                        new TagSystemTreeTagOut(tag.getTagId(), tag.getTagName(), tag.getFlag(),
                                        tag.getTagNameEng(), tag.getSearchMod(), tag.getTagList());
        TagSystemTreeOut tagTreeFirstOut = new TagSystemTreeOut(tagTreeFirstLists.get(0).getTagId(),
                        tagTreeFirstLists.get(0).getTagName(), tagTreeFirstLists.get(0).getLevel(),
                        new ArrayList<Object>());
        TagSystemTreeOut tagTreeSecondOut =
                        new TagSystemTreeOut(tagTreeSecond.getTagId(), tagTreeSecond.getTagName(),
                                        tagTreeSecond.getLevel(), new ArrayList<Object>());
        tagTreeSecondOut.getChildren().add(TagSystemTreeTagOut);
        tagTreeFirstOut.getChildren().add(tagTreeSecondOut);
        actualData.add(tagTreeFirstOut);

        Assert.assertEquals(actualData, resutl.getData());
    }

    @After
    public void tearDown() throws Exception {}

}
