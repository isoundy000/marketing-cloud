package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationBody;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 11/22/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SegmentDetailGetServiceImplTest {

    @Mock
    private SegmentationHeadDao segmentationHeadDao;

    @Mock
    private SegmentationBodyDao segmentationBodyDao;

    private SegmentDetailGetServiceImpl segmentDetailGetService = new SegmentDetailGetServiceImpl();
    private Long id = Long.valueOf(0);

    @Before
    public void setUp() throws Exception{
        ReflectionTestUtils.setField(segmentDetailGetService,"segmentationHeadDao",segmentationHeadDao);
        ReflectionTestUtils.setField(segmentDetailGetService,"segmentationBodyDao",segmentationBodyDao);
    }

    @Test
    public void getSegmentDetail() throws Exception {
        BaseOutput baseOutput = segmentDetailGetService.getSegmentDetail(id);
        Assert.assertEquals(baseOutput.getCode(), ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());

        SegmentationHead paramSegmentationHead = new SegmentationHead();
        paramSegmentationHead.setId(id.intValue());
        paramSegmentationHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SegmentationHead> resultList = new ArrayList<SegmentationHead>();
        resultList.add(paramSegmentationHead);
        Mockito.when(segmentationHeadDao.selectList(Mockito.argThat(new SegmentDetailGetServiceImplTest.SegmentationHeaderMatcher(paramSegmentationHead)))).thenReturn(resultList);
        baseOutput = segmentDetailGetService.getSegmentDetail(id);
        Assert.assertEquals(baseOutput.getCode(),ApiErrorCode.SUCCESS.getCode());

        SegmentationBody paramSegmentationBody = new SegmentationBody();
        paramSegmentationBody.setHeadId(id.intValue());
        paramSegmentationBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SegmentationBody> rsList = new ArrayList<>();
        SegmentationBody segmentationBody = new SegmentationBody();
        segmentationBody.setHeadId(id.intValue());
        segmentationBody.setGroupId("ujiafzoxvyebwwix");
        segmentationBody.setGroupName("分组");
        segmentationBody.setGroupIndex(0);
        segmentationBody.setTagId("LBej3qLy");
        segmentationBody.setTagName("性别");
        segmentationBody.setTagSeq(0);
        segmentationBody.setTagExclude(0);
        segmentationBody.setTagValueId("LBej3qLy_0");
        segmentationBody.setTagValueName("男");
        rsList.add(segmentationBody);
        segmentationBody = new SegmentationBody();
        segmentationBody.setHeadId(id.intValue());
        segmentationBody.setGroupId("qeteieyartynblzl");
        segmentationBody.setGroupName("分组");
        segmentationBody.setGroupIndex(1);
        segmentationBody.setTagId("ttM3RRWl");
        segmentationBody.setTagName("忠诚客户");
        segmentationBody.setTagSeq(0);
        segmentationBody.setTagExclude(0);
        segmentationBody.setTagValueId("ttM3RRWl_0");
        segmentationBody.setTagValueName("是");
        rsList.add(segmentationBody);
        Mockito.when(segmentationBodyDao.selectList(Mockito.argThat(new SegmentDetailGetServiceImplTest.SegmentationBodyMatcher(paramSegmentationBody)))).thenReturn(rsList);
        baseOutput = segmentDetailGetService.getSegmentDetail(id);
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
            Integer id = segmentationHead.getId();
            Integer paramId = ((SegmentationHead)obj).getId();
            if(id.intValue() == paramId.intValue()) return true;
            return false;
        }
    }

    class SegmentationBodyMatcher extends ArgumentMatcher<SegmentationBody> {

        SegmentationBody segmentationBody=null;

        public  SegmentationBodyMatcher(SegmentationBody segmentationBody) {
            this.segmentationBody = segmentationBody;
        }

        public boolean matches(Object obj) {
            Integer headId = segmentationBody.getHeadId();
            Integer paramHeadId = ((SegmentationBody)obj).getHeadId();
            if(headId.intValue() == paramHeadId.intValue()) return true;
            return false;
        }
    }
}