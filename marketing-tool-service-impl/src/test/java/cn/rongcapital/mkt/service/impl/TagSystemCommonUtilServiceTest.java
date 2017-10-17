package cn.rongcapital.mkt.service.impl;

import static org.junit.Assert.*;
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

import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
@RunWith(MockitoJUnitRunner.class)
public class TagSystemCommonUtilServiceTest {
    
    private TagSystemCommonUtilService tagSystemCommonUtilService;
    
    @Mock
    private MongoTemplate mongoTemplate;
    
    @Mock
    private TagValueCountDao tagValueCountDao;
    
    private List<TagValueCount> tagValueCountLists;
    private TagValueCount tagValueCount;
    private long allCount;

    @Before
    public void setUp() throws Exception {
        
        tagValueCountLists = new ArrayList<TagValueCount>();
        tagValueCount = new TagValueCount();
        tagValueCount.setValueCount((long)100);
        tagValueCountLists.add(tagValueCount);
        
        allCount = 1000;
        
        tagSystemCommonUtilService = new TagSystemCommonUtilServiceImpl();
        
        Mockito.when(mongoTemplate.count(any(), eq(DataParty.class))).thenReturn(allCount);
        Mockito.when(tagValueCountDao.selectList(any())).thenReturn(tagValueCountLists);
        
        ReflectionTestUtils.setField(tagSystemCommonUtilService, "mongoTemplate", mongoTemplate);
        ReflectionTestUtils.setField(tagSystemCommonUtilService, "tagValueCountDao", tagValueCountDao);
    }
    
    @Test
    public final void testGetTagCover() {
        String tagCover;
        
        // 测试传入null
        tagCover = tagSystemCommonUtilService.getTagCover(null);
        Assert.assertEquals("0%", tagCover);
        
        // 测试正常数据
        tagCover = tagSystemCommonUtilService.getTagCover("LBej3qLy");
        Assert.assertEquals("10%", tagCover);
        
        // 测试allCount = 0
        allCount = 0;
        Mockito.when(mongoTemplate.count(any(), eq(DataParty.class))).thenReturn(allCount);
        tagCover = tagSystemCommonUtilService.getTagCover("LBej3qLy");
        Assert.assertEquals("0%", tagCover);
        
        // 测试不能整除
        allCount = 202;
        Mockito.when(mongoTemplate.count(any(), eq(DataParty.class))).thenReturn(allCount);
        tagCover = tagSystemCommonUtilService.getTagCover("LBej3qLy");
        Assert.assertEquals("49%", tagCover);
        
    }

    @After
    public void tearDown() throws Exception {}

    

}
