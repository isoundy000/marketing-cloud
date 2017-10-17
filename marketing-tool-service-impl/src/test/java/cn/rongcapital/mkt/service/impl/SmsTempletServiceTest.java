package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTaskAppEnum;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.enums.SmsTempleteAuditStatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dao.SmsTempletMaterialMapDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsTaskDetailState;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsTempletService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;
import cn.rongcapital.mkt.vo.sms.out.SmsTempletCountVo;

@RunWith(MockitoJUnitRunner.class)
public class SmsTempletServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    SmsTempletService smsTempletService;
    
    @Mock
    private SmsTempletDao smsTempletDao;
    @Mock
	private SmsMaterialDao smsMaterialDao;
    @Mock
	private SmsTempletMaterialMapDao smsTempletMaterialMapDao;
    
    
    private int selectListCountResult = 10;
    
    private int insertResult = 1;
    
    private List<SmsTemplet> dataList;
    private List<SmsTemplet> dataList1;
    
    private List<SmsTempletCountVo> smsTempletCountVosByType;
    
    private List<SmsTempletCountVo> smsTempletCountVosByAuditStatus;
    
    private List<SmsMaterial> smsMaterials;
    
    
    @Before
    public void setUp() throws Exception {
        
        dataList = new ArrayList<SmsTemplet>();
        SmsTemplet smsTemplet = new SmsTemplet();
        smsTemplet.setAuditStatus(NumUtil.int2OneByte(SmsTempleteAuditStatusEnum.AUDIT_STATUS_PASS.getStatusCode()));
        dataList.add(smsTemplet);
        dataList.add(smsTemplet);

        smsTempletCountVosByType = new ArrayList<SmsTempletCountVo>();
        SmsTempletCountVo smsTempletCountVoType = new SmsTempletCountVo();
        smsTempletCountVosByType.add(smsTempletCountVoType);
        smsTempletCountVosByType.add(smsTempletCountVoType);
        
        smsTempletCountVosByAuditStatus = new ArrayList<SmsTempletCountVo>();
        SmsTempletCountVo smsTempletCountVoAuditStatus = new SmsTempletCountVo();
        smsTempletCountVosByAuditStatus.add(smsTempletCountVoAuditStatus);
        smsTempletCountVosByAuditStatus.add(smsTempletCountVoAuditStatus);
        
        smsMaterials = new ArrayList<SmsMaterial>();
        SmsMaterial smsMaterial = new SmsMaterial();
        smsMaterials.add(smsMaterial);
        smsMaterials.add(smsMaterial);
        
        Mockito.when(smsTempletDao.selectListCount(any())).thenReturn(selectListCountResult);
        Mockito.when(smsTempletDao.selectList(any())).thenReturn(dataList);
        
        Mockito.when(smsTempletDao.selectListCountGroupByType(any())).thenReturn(smsTempletCountVosByType);
        Mockito.when(smsTempletDao.selectListCountGroupByAuditStatus(any())).thenReturn(smsTempletCountVosByAuditStatus);
        
        Mockito.when(smsMaterialDao.selectList(any())).thenReturn(smsMaterials);
        
        Mockito.when(smsTempletDao.insert(any())).thenReturn(insertResult);
        
    	Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				return null;
			}
		}).when(smsTempletMaterialMapDao).deleteByTempletId(any());
        
    	Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				return null;
			}
		}).when(smsTempletMaterialMapDao).insert(any());
    	
        smsTempletService = new SmsTempletServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smsTempletService, "smsTempletDao", smsTempletDao);
        
        ReflectionTestUtils.setField(smsTempletService, "smsMaterialDao", smsMaterialDao);
        
        ReflectionTestUtils.setField(smsTempletService, "smsTempletMaterialMapDao", smsTempletMaterialMapDao);
    }

    @Test
    public void testSmsTempletList() {
        BaseOutput result = smsTempletService.smsTempletList("111", 0, 10, "0", "1", "测试","");
        
        // 断言
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(selectListCountResult, result.getTotalCount());
        Assert.assertEquals(dataList, result.getData());
        
    }
    
    @Test
    public void testSaveOrUpdateSmsTemplet() {
        SmsTempletIn smsTempletIn = new SmsTempletIn();
        /**
         * 插入参数
         */
        smsTempletIn.setId(1);
        smsTempletIn.setType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
        smsTempletIn.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
        smsTempletIn.setContent("测试模板");
        smsTempletIn.setCreator("user1");

        BaseOutput result = smsTempletService.saveOrUpdateSmsTemplet(smsTempletIn);
        
        // 断言
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        
        
        SmsTempletIn smsTempletIn1 = new SmsTempletIn();
        /**
         * 插入参数
         */
        smsTempletIn1.setId(2);
        smsTempletIn.setType(NumUtil.int2OneByte(SmsTempletTypeEnum.VARIABLE.getStatusCode()));
        smsTempletIn.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
        smsTempletIn.setContent("测试变量模板");
        smsTempletIn.setCreator("user2");

        BaseOutput result2 = smsTempletService.saveOrUpdateSmsTemplet(smsTempletIn);
        
        // 断言
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result2.getCode()); 
        
        
    }
    

}
