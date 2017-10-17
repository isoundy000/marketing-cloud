package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialDeleteIn;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialIn;

@RunWith(MockitoJUnitRunner.class)
public class SmsMaterialServiceTest {

	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	 SmsMaterialService smsMaterialService;
	
	 @Mock
	 private SmsMaterialDao smsMaterialDao;
	 @Mock
	 private SmsTempletDao smsTempletDao;
	 @Mock
	 private SmsTaskHeadDao smsTaskHeadDao;
	 
	 private int selectListCountResult = 10;
	    
	 private int insertResult = 1;
	 
	 private List<SmsMaterial> smsMaterials;
	 
     @Before
     public void setUp() throws Exception {
    
    	 smsMaterialService = new SmsMaterialServiceImpl();
         ReflectionTestUtils.setField(smsMaterialService, "smsMaterialDao", smsMaterialDao);
         ReflectionTestUtils.setField(smsMaterialService, "smsTempletDao", smsTempletDao);
         ReflectionTestUtils.setField(smsMaterialService, "smsTaskHeadDao", smsTaskHeadDao);

     }
     
     @Test
     public void testSaveOrUpdateSmsMaterial() {
    	 SmsMaterialIn smsMaterialIn = new SmsMaterialIn();
//    	 smsMaterialIn.setId(1);
    	 smsMaterialIn.setName("测试素材");
    	 smsMaterialIn.setSmsSignId(1);
    	 smsMaterialIn.setSmsSignName("融数金服");
    	 smsMaterialIn.setSmsTempletContent("融数金服");
    	 smsMaterialIn.setSmsTempletId(1);
    	 smsMaterialIn.setSmsType(0);
    	 smsMaterialIn.setCreator("user1");
    	 smsMaterialIn.setUpdateUser("user1");
    	 BaseOutput output = smsMaterialService.insertOrUpdateSmsMaterial(smsMaterialIn);
    	 
         // 断言
         Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());    	 
     }
     
     @Test
     public void testDeleteSmsMaterial() {
    	 SmsMaterialDeleteIn smsMaterialDeleteIn = new SmsMaterialDeleteIn();
    	 smsMaterialDeleteIn.setId(1);
    	 BaseOutput output = smsMaterialService.deleteSmsMaterial(smsMaterialDeleteIn);
    	// 断言
    	 Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());    	

     }

    	 
}
