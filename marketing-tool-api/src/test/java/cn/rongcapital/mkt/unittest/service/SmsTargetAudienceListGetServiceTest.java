package cn.rongcapital.mkt.unittest.service;

import cn.rongcapital.mkt.common.enums.SmsTargetAudienceTypeEnum;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.SmsSignature;
import cn.rongcapital.mkt.service.SmsTargetAudienceListGetService;
import cn.rongcapital.mkt.service.impl.SmsTargetAudienceListGetServiceImpl;
import cn.rongcapital.mkt.vo.out.SmsSignatureListOut;
import cn.rongcapital.mkt.vo.out.SmsSignatureOut;
import cn.rongcapital.mkt.vo.out.SmsTargetAudienceListOut;
import cn.rongcapital.mkt.vo.out.SmsTargetAudienceOut;
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
 * Created by byf on 10/20/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsTargetAudienceListGetServiceTest {

    private SmsTargetAudienceListGetService smsTargetAudienceListGetService;

    @Mock
    private SegmentationHeadDao segmentationHeadDao;

    @Mock
    private AudienceListDao audienceListDao;

    @Before
    public void setUp() throws Exception {
        //定义dao的返回值
        SegmentationHead resultSegmentationHead = new SegmentationHead();
        resultSegmentationHead.setName("单元测试细分人群组");
        resultSegmentationHead.setId(1);
        List<SegmentationHead> resultSegmentationList = new ArrayList<>();
        resultSegmentationList.add(resultSegmentationHead);

        AudienceList resultAudienceList = new AudienceList();
        resultAudienceList.setId(2);
        resultAudienceList.setAudienceName("单元测试固定人群组");
        List<AudienceList> resultAudienceListList = new ArrayList<>();
        resultAudienceListList.add(resultAudienceList);

        //定义dao方法的参数
        SegmentationHead paramSegmentationHead = new SegmentationHead();
        paramSegmentationHead.setStatus(new Integer(0).byteValue());
        paramSegmentationHead.setPageSize(Integer.MAX_VALUE);
        Mockito.when(segmentationHeadDao.selectList(Mockito.argThat(new SmsTargetAudienceListGetServiceTest.SegmentationHeadMatcher(paramSegmentationHead)))).thenReturn(resultSegmentationList);

        AudienceList paramAudienceList = new AudienceList();
        paramAudienceList.setStatus(new Integer(0).byteValue());
        paramAudienceList.setPageSize(Integer.MAX_VALUE);
        Mockito.when(audienceListDao.selectList(Mockito.argThat(new SmsTargetAudienceListGetServiceTest.AudienceListMatcher(paramAudienceList)))).thenReturn(resultAudienceListList);

        //把mock的dao set进入service
        smsTargetAudienceListGetService = new SmsTargetAudienceListGetServiceImpl();
        ReflectionTestUtils.setField(smsTargetAudienceListGetService, "segmentationHeadDao", segmentationHeadDao);
        ReflectionTestUtils.setField(smsTargetAudienceListGetService, "audienceListDao", audienceListDao);
    }

    @Test
    public void testSmsSignatrueListGetService()	{
        //执行待测的service方法getSmsSignatureList()
        SmsTargetAudienceListOut smsTargetAudienceListOut=smsTargetAudienceListGetService.getSmsTargetAudienceList();

        //断言判断结果正确
        //判断细分人群的结果
        SmsTargetAudienceOut expectedSegmentationHeadTypeOut = new SmsTargetAudienceOut();
        expectedSegmentationHeadTypeOut.setId(Long.valueOf(1));
        expectedSegmentationHeadTypeOut.setName("单元测试细分人群组");
        expectedSegmentationHeadTypeOut.setType(SmsTargetAudienceTypeEnum.SMS_TARGET_SEGMENTATION.getTypeCode());
        SmsTargetAudienceOut actualSegmentationHeadTypeOut = smsTargetAudienceListOut.getSmsTargetAudienceOutList().get(0);
        Assert.assertEquals(expectedSegmentationHeadTypeOut.getId(),actualSegmentationHeadTypeOut.getId());
        Assert.assertEquals(expectedSegmentationHeadTypeOut.getName(),actualSegmentationHeadTypeOut.getName());
        Assert.assertEquals(expectedSegmentationHeadTypeOut.getType(),actualSegmentationHeadTypeOut.getType());

        //判断固定人群的结果
        SmsTargetAudienceOut expectedAudienceListTypeOut = new SmsTargetAudienceOut();
        expectedAudienceListTypeOut.setId(Long.valueOf(2));
        expectedAudienceListTypeOut.setName("单元测试固定人群组");
        expectedAudienceListTypeOut.setType(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode());
        SmsTargetAudienceOut actualAudienceListTypeOut = smsTargetAudienceListOut.getSmsTargetAudienceOutList().get(1);
        Assert.assertEquals(expectedAudienceListTypeOut.getId(),actualAudienceListTypeOut.getId());
        Assert.assertEquals(expectedAudienceListTypeOut.getName(),actualAudienceListTypeOut.getName());
        Assert.assertEquals(expectedAudienceListTypeOut.getType(),actualAudienceListTypeOut.getType());
    }

    class SegmentationHeadMatcher extends ArgumentMatcher<SegmentationHead> {

        SegmentationHead segmentationHead=null;

        public  SegmentationHeadMatcher(SegmentationHead segmentationHead) {
            this.segmentationHead = segmentationHead;
        }

        public boolean matches(Object obj) {
            Byte segmentationHeadStatus = segmentationHead.getStatus();
            Byte objSignatureStatus = ((SegmentationHead)obj).getStatus();
            Integer pageSize = segmentationHead.getPageSize();
            Integer objPageSize = ((SegmentationHead)obj).getPageSize();
            if (segmentationHeadStatus.byteValue() == objSignatureStatus.byteValue() && pageSize.intValue() == objPageSize.intValue()) {
                return true;
            }
            return false;
        }
    }

    class AudienceListMatcher extends ArgumentMatcher<AudienceList> {

        AudienceList audienceList=null;

        public  AudienceListMatcher(AudienceList audienceList) {
            this.audienceList = audienceList;
        }

        public boolean matches(Object obj) {
            Byte audienceListStatus = audienceList.getStatus();
            Byte objSignatureStatus = ((AudienceList)obj).getStatus();
            Integer pageSize = audienceList.getPageSize();
            Integer objPageSize = ((AudienceList)obj).getPageSize();
            if (audienceListStatus.byteValue() == objSignatureStatus.byteValue() && pageSize.intValue() == objPageSize.intValue()) {
                return true;
            }
            return false;
        }
    }
}
