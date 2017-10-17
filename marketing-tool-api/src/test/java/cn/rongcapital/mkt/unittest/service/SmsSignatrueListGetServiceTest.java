package cn.rongcapital.mkt.unittest.service;

import cn.rongcapital.mkt.dao.SmsSignatureDao;
import cn.rongcapital.mkt.po.SmsSignature;
import cn.rongcapital.mkt.service.SmsSignatureListGetService;
import cn.rongcapital.mkt.service.impl.SmsSignatureListGetServiceImpl;
import cn.rongcapital.mkt.vo.out.SmsSignatureListOut;
import cn.rongcapital.mkt.vo.out.SmsSignatureOut;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 10/20/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsSignatrueListGetServiceTest {

    private SmsSignatureListGetService smsSignatureListGetService;

    @Mock
    private SmsSignatureDao smsSignatureDao; //mock dao

    @Before
    public void setUp() throws Exception {
        //定义dao的返回值
        SmsSignature resultSmsSignature = new SmsSignature();
        resultSmsSignature.setId(Long.valueOf(1));
        resultSmsSignature.setSmsSignatureName("incake测试数据");
        List<SmsSignature> resultList = new ArrayList<>();
        resultList.add(resultSmsSignature);

        //定义dao方法的参数
        SmsSignature paramSmsSignature = new SmsSignature();
        paramSmsSignature.setStatus(new Integer(0).byteValue());
        paramSmsSignature.setPageSize(Integer.MAX_VALUE);
        Mockito.when(smsSignatureDao.selectList(Mockito.argThat(new SmsSignatrueListGetServiceTest.SmsSignatureMatcher(paramSmsSignature)))).thenReturn(resultList);

        //把mock的dao set进入service
        smsSignatureListGetService = new SmsSignatureListGetServiceImpl();
        ReflectionTestUtils.setField(smsSignatureListGetService, "smsSignatureDao", smsSignatureDao);
    }

    @Test
    public void testSmsSignatrueListGetService()	{
        //执行待测的service方法getSmsSignatureList()
        SmsSignatureListOut smsSignatureListOut=smsSignatureListGetService.getSmsSignatureList();

        //断言判断结果正确
        SmsSignature expectedSmsSignature = new SmsSignature();
        expectedSmsSignature.setId(Long.valueOf(1));
        expectedSmsSignature.setSmsSignatureName("incake测试数据");
        SmsSignatureOut actualSmsSignatureOut = smsSignatureListOut.getSmsSignatureOutList().get(0);
        Assert.assertEquals(expectedSmsSignature.getId(),actualSmsSignatureOut.getId());
        Assert.assertEquals(expectedSmsSignature.getSmsSignatureName(),actualSmsSignatureOut.getSignatrueName());

    }


    class SmsSignatureMatcher extends ArgumentMatcher<SmsSignature> {

        SmsSignature smsSignature=null;

        public  SmsSignatureMatcher(SmsSignature smsSignature) {
            this.smsSignature = smsSignature;
        }

        public boolean matches(Object obj) {
            Byte signatureStatus = smsSignature.getStatus();
            Byte objSignatureStatus = ((SmsSignature)obj).getStatus();
            Integer pageSize = smsSignature.getPageSize();
            Integer objPageSize = ((SmsSignature)obj).getPageSize();
            if (signatureStatus.byteValue() == objSignatureStatus.byteValue() && pageSize.intValue() == objPageSize.intValue()) {
                return true;
            }
            return false;
        }
    }
}
