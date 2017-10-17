package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.mongodb.CommandResult;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagSystemFlagSetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagSystemFlagSetIn;

@RunWith(MockitoJUnitRunner.class)
public class TagSystemFlagSetServiceImplTest {

    private TagSystemFlagSetService tagSystemFlagSetService;

    @Mock
    private MongoTemplate mongoTemplate;

    private TagSystemFlagSetIn body;

    WriteResult writeResult;

    private int total = 1;

    @Before
    public void setUp() throws Exception {
        body = new TagSystemFlagSetIn();
        body.setTagId("68QkbhSa");
        body.setFlag(true);

        Mockito.when(mongoTemplate.updateMulti(any(), eq(null), eq(TagRecommend.class)))
                        .thenReturn(writeResult);

        ReflectionTestUtils.setField(tagSystemFlagSetService, "mongoTemplate", mongoTemplate);
    }

    /**
     * 忽略原因：WriteResult未提供公共的构造方法
     * 
     * @throws Exception
     */
    @Ignore
    @Test
    public void testUpdateFlag() throws Exception {
        BaseOutput result = tagSystemFlagSetService.updateFlag(body, null);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(total, result.getTotal());
    }

    @After
    public void tearDown() throws Exception {}

}
