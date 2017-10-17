package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentCalcService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentCreUpdateIn;
import cn.rongcapital.mkt.vo.in.SystemTagIn;
import cn.rongcapital.mkt.vo.in.SystemValueIn;
import cn.rongcapital.mkt.vo.in.TagGroupsIn;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 11/21/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreupdateSegmentServiceImplTest {

    @Mock
    private SegmentationHeadDao segmentationHeadDao;

    @Mock
    private SegmentationBodyDao segmentationBodyDao;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private SegmentCalcService segmentCalcService;

    private CreupdateSegmentServiceImpl segmentService = new CreupdateSegmentServiceImpl();
    private SegmentCreUpdateIn notContainFilterGroupIn  = new SegmentCreUpdateIn();
    private SegmentCreUpdateIn notContainTagListIn = new SegmentCreUpdateIn();
    private SegmentCreUpdateIn notContainTagValueIn = new SegmentCreUpdateIn();

    @Before
    public void setUp() throws Exception{
        //1.MockServiceImpl
        ReflectionTestUtils.setField(segmentService,"segmentationHeadDao",segmentationHeadDao);
        ReflectionTestUtils.setField(segmentService,"segmentationBodyDao",segmentationBodyDao);
        ReflectionTestUtils.setField(segmentService,"mongoTemplate",mongoTemplate);
        ReflectionTestUtils.setField(segmentService,"segmentCalcService",segmentCalcService);

        //2.Mock不含标签组类型的传入参数
        notContainFilterGroupIn.setSegmentName("细分创建测试-不含标签组");
        notContainFilterGroupIn.setPublishStatus(Integer.valueOf(ApiConstant.SEGMENT_PUBLISH_STATUS_PUBLISH));

        //3.Mock包含空标签列表的传入参数
        notContainTagListIn.setSegmentName("细分创建测试-标签组中包含空标签列表");
        notContainTagListIn.setPublishStatus(Integer.valueOf(ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH));

        TagGroupsIn tagGroupsIn = new TagGroupsIn();
        tagGroupsIn.setGroupId("group1");
        tagGroupsIn.setGroupChange(1);
        tagGroupsIn.setGroupName("标签组-1");
        tagGroupsIn.setGroupIndex(1);

        notContainTagListIn.getFilterGroups().add(tagGroupsIn);

        //4.Mock包含标签列表值为空的传入参数
        notContainTagValueIn.setSegmentName("细分创建测试-标签组中包含空标签值");
        notContainTagValueIn.setPublishStatus(Integer.valueOf(ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH));

        tagGroupsIn = new TagGroupsIn();
        tagGroupsIn.setGroupId("group1");
        tagGroupsIn.setGroupChange(1);
        tagGroupsIn.setGroupName("标签组-1");
        tagGroupsIn.setGroupIndex(1);

        SystemTagIn systemTagIn = new SystemTagIn();
        systemTagIn.setTagId("标签ID-01");
        systemTagIn.setTagName("标签1");
        systemTagIn.setTagIndex(1);
        systemTagIn.setTagExclude(1);

        SystemValueIn systemValueIn = new SystemValueIn();
        systemValueIn.setTagValueId("标签ID-01-值01");
        systemValueIn.setTagValue("是");
        systemTagIn.getTagValueList().add(systemValueIn);
        tagGroupsIn.getTagList().add(systemTagIn);

        systemTagIn = new SystemTagIn();
        systemTagIn.setTagId("标签ID-02");
        systemTagIn.setTagName("");
        systemTagIn.setTagExclude(0);
        systemTagIn.setTagIndex(2);
        tagGroupsIn.getTagList().add(systemTagIn);

        notContainTagValueIn.getFilterGroups().add(tagGroupsIn);
    }

    @Test
    public void creupdateSegment() throws Exception {
        //Mock测试方法的返回值
        Mockito.when(segmentationHeadDao.insert(Mockito.any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SegmentationHead segmentationHead = invocationOnMock.getArgumentAt(0,SegmentationHead.class);
                segmentationHead.setId(1);
                return null;
            }
        });
        Mockito.when(segmentationBodyDao.insert(Mockito.any())).thenReturn(1);

        //1.测试不包含标签组的细分创建
        BaseOutput baseOutput = segmentService.creupdateSegment(notContainFilterGroupIn);
        Assert.assertEquals(baseOutput.getCode(), ApiErrorCode.SUCCESS.getCode());

        //2.测试包含空标签的标签测试
        baseOutput = segmentService.creupdateSegment(notContainTagListIn);
        Assert.assertEquals(baseOutput.getCode(),ApiErrorCode.SUCCESS.getCode());

        //3.测试包含空标签值的细分创建
        baseOutput = segmentService.creupdateSegment(notContainTagValueIn);
        Assert.assertEquals(baseOutput.getCode(),ApiErrorCode.SUCCESS.getCode());

        //4.测试细分更新
        baseOutput = segmentService.creupdateSegment(notContainTagValueIn);
        Assert.assertEquals(baseOutput.getCode(),ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
        List<SegmentationHead> segmentationHeadList = new ArrayList<>();
        segmentationHeadList.add(new SegmentationHead());
        Mockito.when(segmentationHeadDao.selectList(Mockito.any())).thenReturn(segmentationHeadList);
        baseOutput = segmentService.creupdateSegment(notContainTagValueIn);
        Assert.assertEquals(baseOutput.getCode(),ApiErrorCode.SUCCESS.getCode());
    }

    @After
    public void tearDown() throws Exception{

    }

    class SegmentationHeaderMatcher extends ArgumentMatcher<SegmentationHead> {

        SegmentationHead segmentationHead=null;

        public  SegmentationHeaderMatcher(SegmentationHead segmentationHead) {
            this.segmentationHead = segmentationHead;
        }

        public boolean matches(Object obj) {
            return true;
        }
    }
}