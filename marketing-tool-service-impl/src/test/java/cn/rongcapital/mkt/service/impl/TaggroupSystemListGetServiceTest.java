/*************************************************
 * @功能简述: Service实现测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;


import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseOutput;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class TaggroupSystemListGetServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private TaggroupSystemListGetService taggroupSystemListGetService;
    
    
    @Mock
    private TaggroupDao taggroupDao;
    
    @Mock
    private MongoTemplate mongoTemplate;
    
    private static long count = 1;
    
    private TagTree tagTree ;
    private TagRecommend tagRecommend ;
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：TaggroupSystemListGetService 准备---------------------");
        
        List<String> children = new ArrayList<String>();
        children.add("mPRCxacM");
        tagTree = new TagTree(null, "YWhNcSkt", "个人信息", 2, null, 0, null, null, null, null, children);
        
        List<String> tagList = new ArrayList<String>();
        tagList.add("是");
        tagList.add("否");
        tagRecommend = new TagRecommend(null, "68QkbhSa", "蛋糕乳脂系列", tagList, 0, false,
                        "用户购买该品类至少一次", null, null, "creamCakeSeries", "incake", 17, null);

        Mockito.when(mongoTemplate.findOne(any(),eq(TagTree.class))).thenReturn(tagTree);
        Mockito.when(mongoTemplate.count(any(),eq(DataParty.class))).thenReturn(count);
        Mockito.when(mongoTemplate.findOne(any(),eq(TagRecommend.class))).thenReturn(tagRecommend);
        
        
        taggroupSystemListGetService = new TaggroupSystemListGetServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(taggroupSystemListGetService, "taggroupDao", taggroupDao);
        ReflectionTestUtils.setField(taggroupSystemListGetService, "mongoTemplate", mongoTemplate);
        
    }
    
    @Test
    public void testGetMongoTagRecommendByTagTreeId() {
        logger.info("测试方法: getMongoTagRecommendByTagTreeId ");
        
        BaseOutput baseOutput = taggroupSystemListGetService.getMongoTagRecommendByTagTreeId(null,
                        null, null, 1, 10);
 
        // 判断返回状态
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        Assert.assertEquals(tagTree.getChildren().size(), baseOutput.getTotal());
        
        // 判断返回结果主题
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("tag_group_id", tagRecommend.getTagId());
        mapResult.put("tag_group_name", tagRecommend.getTagName());           
        mapResult.put("tag_group_creat_time", "");
        mapResult.put("tag_count", tagRecommend.getTagList().size());
        mapResult.put("tag_desc", tagRecommend.getTagDesc());
        mapResult.put("tag_cover", new DecimalFormat("#.##%").format(count / (double)count));
        mapResult.put("flag", tagRecommend.getFlag());
        Assert.assertEquals(mapResult, baseOutput.getData().get(0));
    }
    
    @Test
    public void testGetTagGroupByParentGroupId() {
        logger.info("测试方法: getTagGroupByParentGroupId ");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：TaggroupSystemListGetService 结束---------------------");
    }

}


