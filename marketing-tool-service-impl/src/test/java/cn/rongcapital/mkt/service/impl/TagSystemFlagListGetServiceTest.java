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
import cn.rongcapital.mkt.service.TagSystemFlagListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.TagSystemFlagListGetOut;

@RunWith(MockitoJUnitRunner.class)
public class TagSystemFlagListGetServiceTest {

    private TagSystemFlagListGetService tagSystemFlagListGetService;

    @Mock
    private MongoTemplate mongoTemplate;

    List<TagRecommend> flagTagList;

    @Before
    public void setUp() throws Exception {

        List<String> tagList = new ArrayList<String>();
        tagList.add("是");
        tagList.add("否");
        flagTagList = new ArrayList<TagRecommend>();
        flagTagList.add(new TagRecommend(null, "68QkbhSa", "蛋糕乳脂系列", tagList, 0, false,
                        "用户购买该品类至少一次", null, null, "creamCakeSeries", "incake", 17, 0));
        flagTagList.add(new TagRecommend(null, "3Swmnwgc", "购买过蛋糕品类", tagList, 0, false,
                        "用户购买该品类至少一次", null, null, "anyCakeSeries", "incake", 12, 0));

        tagSystemFlagListGetService = new TagSystemFlagListGetServiceImpl();

        Mockito.when(mongoTemplate.find(any(), eq(TagRecommend.class))).thenReturn(flagTagList);

        ReflectionTestUtils.setField(tagSystemFlagListGetService, "mongoTemplate", mongoTemplate);
    }

    @Test
    public void testGetTagSystemFlagList() {
        BaseOutput resutl = tagSystemFlagListGetService.getTagSystemFlagList();

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), resutl.getCode());
        Assert.assertEquals(flagTagList.size(), resutl.getTotal());

        TagRecommend flagTag = flagTagList.get(0);
        TagSystemFlagListGetOut tagSystemFlagListGetOut = new TagSystemFlagListGetOut(
                        flagTag.getTagId(), flagTag.getTagName(), flagTag.getTagList(),
                        flagTag.getFlag(), flagTag.getTagDesc(), flagTag.getTagNameEng(),
                        flagTag.getSeq(), flagTag.getSearchMod());
        Assert.assertEquals(tagSystemFlagListGetOut, resutl.getData().get(0));
    }

    @After
    public void tearDown() throws Exception {}
}
