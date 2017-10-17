package cn.rongcapital.mkt.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.service.TagSystemValueListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
@RunWith(MockitoJUnitRunner.class)
public class TagSystemValueListGetServiceTest {
    
    private TagSystemValueListGetService tagSystemValueListGetService;
    
    @Mock
    private TagValueCountDao tagValueCountDao;
    
    private List<TagValueCount> tagValueCountLists;

    @Before
    public void setUp() throws Exception {
        
        tagValueCountLists = new ArrayList<TagValueCount>();
        TagValueCount tagValueCountTag = new TagValueCount("09butpYr", "蛋糕慕斯系列", "蛋糕慕斯系列#", Long.valueOf(0), null,
                        "交易历史及偏好>交易历史及偏好>蛋糕慕斯系列>", "1", 0);
        TagValueCount tagValueCountValue = new TagValueCount("09butpYr", "蛋糕慕斯系列", "是", Long.valueOf(0), null,
                        "09butpYr_0", "交易历史及偏好>交易历史及偏好>蛋糕慕斯系列>", 0);
        tagValueCountLists.add(tagValueCountTag);
        tagValueCountLists.add(tagValueCountValue);
        
        tagSystemValueListGetService = new TagSystemValueListGetServiceImpl();
        
        Mockito.when(tagValueCountDao.selectList(any())).thenReturn(tagValueCountLists);
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(tagSystemValueListGetService, "tagValueCountDao", tagValueCountDao);
    }
    
    @Test
    public void testGetTagSystemValueList() {
        
        BaseOutput result = tagSystemValueListGetService.getTagSystemValueList("09butpYr");
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(tagValueCountLists.size(), result.getTotal());
        
        List<Object> expectedData = new ArrayList<Object>();
        for(TagValueCount tagValueCountList : tagValueCountLists) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tag_value", tagValueCountList.getTagValue());
            map.put("tag_value_seq", tagValueCountList.getTagValueSeq());
            map.put("value_count", tagValueCountList.getValueCount());
            expectedData.add(map);
        }
        Assert.assertEquals(expectedData, result.getData());
        
        
    }

    @After
    public void tearDown() throws Exception {}

}
