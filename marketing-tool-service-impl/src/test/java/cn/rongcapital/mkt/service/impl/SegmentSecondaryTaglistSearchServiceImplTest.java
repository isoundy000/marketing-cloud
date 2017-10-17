package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.service.SegmentSecondaryTaglistSearchService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagListSecondarySearchIn;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 11/21/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SegmentSecondaryTaglistSearchServiceImplTest {

    @Mock
    private TagValueCountDao tagValueCountDao;

    private SegmentSecondaryTaglistSearchServiceImpl segmentSecondaryTaglistSearchService = new SegmentSecondaryTaglistSearchServiceImpl();
    private TagListSecondarySearchIn tagListSecondarySearchIn = new TagListSecondarySearchIn();

    @Before
    public void setUp() throws Exception{
        ReflectionTestUtils.setField(segmentSecondaryTaglistSearchService,"tagValueCountDao",tagValueCountDao);

        tagListSecondarySearchIn.setTagId("搜索标签Id");
        tagListSecondarySearchIn.setTagName("搜索标签");
        tagListSecondarySearchIn.setKeyWord("搜索单测");
    }

    @Test
    public void searchSegmentSecondaryTaglist() throws Exception {
        BaseOutput baseOutput = segmentSecondaryTaglistSearchService.searchSegmentSecondaryTaglist(null);
        Assert.assertEquals(baseOutput.getCode(),ApiErrorCode.BIZ_ERROR.getCode());

        List<TagValueCount> tagValueCountList = new ArrayList<>();
        TagValueCount tagValueCount = new TagValueCount();
        tagValueCount.setTagId("XXX-01");
        tagValueCount.setTagName("搜索结果");
        tagValueCount.setTagValue("搜索结果-01");
        tagValueCount.setIsTag("0");
        tagValueCount.setValueCount(Long.valueOf(100));
        tagValueCountList.add(tagValueCount);

        Mockito.when(tagValueCountDao.selectTagValueCountListByKeyWord(Mockito.any())).thenReturn(tagValueCountList);
        baseOutput = segmentSecondaryTaglistSearchService.searchSegmentSecondaryTaglist(tagListSecondarySearchIn);
        Assert.assertEquals(baseOutput.getCode(), ApiErrorCode.SUCCESS.getCode());

    }

    @After
    public void tearDown() throws Exception{

    }
}