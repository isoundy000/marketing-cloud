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

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
import cn.rongcapital.mkt.service.TagSystemFuzzyListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.TagSystemFuzzyListGetOut;
@RunWith(MockitoJUnitRunner.class)
public class TagSystemFuzzyListGetServiceTest {

    private TagSystemFuzzyListGetService tagSystemFuzzyListGetService;

    @Mock
    private TagValueCountDao tagValueCountDao;
    
    @Mock
    private TagSystemCommonUtilService tagSystemCommonUtilService;
    
    @Mock
    private MongoTemplate mongoTemplate;

    int count = 10;

    List<TagValueCount> tagValueCountLists;
    
    String tagCover = "10%";

    @Before
    public void setUp() throws Exception {

        tagValueCountLists = new ArrayList<TagValueCount>();
        TagValueCount tagValueCountTag = new TagValueCount("09butpYr", "蛋糕慕斯系列", "蛋糕慕斯系列#",
                        Long.valueOf(0), null, "交易历史及偏好>交易历史及偏好>蛋糕慕斯系列>", "1", 0);
        TagValueCount tagValueCountValue = new TagValueCount("09butpYr", "蛋糕慕斯系列", "是",
                        Long.valueOf(0), null, "09butpYr_0", "交易历史及偏好>交易历史及偏好>蛋糕慕斯系列>", 0);
        tagValueCountLists.add(tagValueCountTag);
        tagValueCountLists.add(tagValueCountValue);


        tagSystemFuzzyListGetService = new TagSystemFuzzyListGetServiceImpl();

        Mockito.when(tagValueCountDao.selectFuzzyTagValue(any())).thenReturn(tagValueCountLists);
        Mockito.when(tagValueCountDao.selectFuzzyTagValueCount(any())).thenReturn(count);
        Mockito.when(tagSystemCommonUtilService.getTagCover(any())).thenReturn(tagCover);
        Mockito.when(mongoTemplate.findOne(any(), eq(TagRecommend.class))).thenReturn(null);

        ReflectionTestUtils.setField(tagSystemFuzzyListGetService, "tagValueCountDao",
                        tagValueCountDao);
        ReflectionTestUtils.setField(tagSystemFuzzyListGetService, "tagSystemCommonUtilService",
                tagSystemCommonUtilService);
        ReflectionTestUtils.setField(tagSystemFuzzyListGetService, "mongoTemplate", mongoTemplate);
    }

    @Test
    public void testGetTagSystemFuzzyList() {
        BaseOutput result = tagSystemFuzzyListGetService.getTagSystemFuzzyList("","1", 0, 0);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(tagValueCountLists.size(), result.getTotal());
        Assert.assertEquals(count, result.getTotalCount());

        List<TagSystemFuzzyListGetOut> actualData = new ArrayList<TagSystemFuzzyListGetOut>();
        
        for (TagValueCount tagValueCountList : tagValueCountLists) {
            TagSystemFuzzyListGetOut tagSystemFuzzyListGetOut = new TagSystemFuzzyListGetOut(
                            tagValueCountList.getTagId(), tagValueCountList.getTagName(),
                            tagValueCountList.getTagValue(), tagValueCountList.getTagPath(),
                            tagValueCountList.getIsTag(), tagValueCountList.getSearchMod(),
                            tagValueCountList.getTagValueSeq());
            tagSystemFuzzyListGetOut.setTagCover(tagCover);
            tagSystemFuzzyListGetOut.setFlag(false);
            actualData.add(tagSystemFuzzyListGetOut);
        }
        
        Assert.assertEquals(actualData, result.getData());

    }

    @After
    public void tearDown() throws Exception {}

}
