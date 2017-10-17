package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
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
 * Created by byf on 11/17/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsMaterialGetServiceImplTest {

    private SmsMaterialGetServiceImpl smsMaterialGetService;

    @Mock
    private SmsMaterialDao smsMaterialDao;

    @Mock
    private SmsTempletDao smsTempletDao;

    @Mock
    private SmsMaterialService smsMaterialService;

    @Before
    public void setUp() throws Exception{
        smsMaterialGetService = new SmsMaterialGetServiceImpl();
        ReflectionTestUtils.setField(smsMaterialGetService,"smsMaterialDao",smsMaterialDao);
        ReflectionTestUtils.setField(smsMaterialGetService,"smsTempletDao",smsTempletDao);
        ReflectionTestUtils.setField(smsMaterialGetService,"smsMaterialService",smsMaterialService);
    }

    @Test
    public void getSmsMaterialById() throws Exception {
        //1.测试返回值为空的分支
        Long id = Long.valueOf(1);
        List<SmsMaterial> resultList = new ArrayList<>();
        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setId(id.intValue());
        Mockito.when(smsMaterialDao.selectList(Mockito.argThat(new SmsMaterialGetServiceImplTest.SmsMaterialMatcherMockOne(paramSmsMaterial)))).thenReturn(resultList);
        BaseOutput baseOutput = smsMaterialGetService.getSmsMaterialById(id);
        Assert.assertEquals(baseOutput.getCode(), ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());

        //2.测试返回值不为空的分支
        resultList.add(new SmsMaterial());
        baseOutput = smsMaterialGetService.getSmsMaterialById(id);
        Assert.assertEquals(baseOutput.getCode(),ApiErrorCode.SUCCESS.getCode());
    }

    @Test
    public void getSmsMaterialListByKeyword() throws Exception {
        SmsMaterial paramSmsMaterial = new SmsMaterial();
        String paramSearchWord = "测试";
        Integer paramIndex = 1;
        Integer paramSmsType = 0;
        Integer paramPageSize = 10;
        Integer paramChannelType = 0;
        int totalCount = 20;
        paramSmsMaterial.setName(paramSearchWord);
        paramSmsMaterial.setStartIndex(paramIndex);
        paramSmsMaterial.setPageSize(paramPageSize);
        List<SmsMaterial> resultList = new ArrayList<>();
        resultList.add(paramSmsMaterial);
        Mockito.when(smsMaterialDao.selectListByKeyword(Mockito.argThat(new SmsMaterialGetServiceImplTest.SmsMaterialMatcherMockTwo(paramSmsMaterial)))).thenReturn(resultList);
        Mockito.when(smsMaterialDao.selectListByKeywordCount(Mockito.argThat(new SmsMaterialGetServiceImplTest.SmsMaterialMatcherMockTwo(paramSmsMaterial)))).thenReturn(totalCount);
        
        BaseOutput baseOutput = smsMaterialGetService.getSmsMaterialListByKeyword(paramSearchWord,paramChannelType,paramSmsType,paramIndex,paramPageSize);
        Assert.assertEquals(baseOutput.getCode(),ApiErrorCode.SUCCESS.getCode());
        Assert.assertEquals(totalCount, baseOutput.getTotalCount());

    }


    class SmsMaterialMatcherMockOne extends ArgumentMatcher<SmsMaterial> {

        SmsMaterial smsMaterial=null;

        public  SmsMaterialMatcherMockOne(SmsMaterial smsMaterial) {
            this.smsMaterial = smsMaterial;
        }

        public boolean matches(Object obj) {

            Long id = Long.valueOf(smsMaterial.getId());
            Long objId =Long.valueOf(((SmsMaterial)obj).getId());
            if(id.longValue() == objId.longValue()) return true;
            return false;
        }
    }

    class SmsMaterialMatcherMockTwo extends ArgumentMatcher<SmsMaterial> {

        SmsMaterial smsMaterial=null;

        public  SmsMaterialMatcherMockTwo(SmsMaterial smsMaterial) {
            this.smsMaterial = smsMaterial;
        }

        public boolean matches(Object obj) {
            String name = smsMaterial.getName();
            String objName = ((SmsMaterial)obj).getName();
            if(name.equals(objName)) return true;
            return false;
        }
    }
}