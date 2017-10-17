package cn.rongcapital.mkt.unittest.service;

import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WechatQrcodeActivateService;
import cn.rongcapital.mkt.service.impl.WechatQrcodeActivateServiceImpl;
import cn.rongcapital.mkt.vo.BaseOutput;


@RunWith(MockitoJUnitRunner.class)   

public class WechatQrcodeActivateServiceTest  {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	private WechatQrcodeActivateService wechatQrcodeActivateService; 
	
	@Mock	
	private WechatQrcodeDao wechatQrcodeDao ; //mock dao
	
	@Before  
	public void setUp() throws Exception {  
		
		int rows=18; //设定dao返回值
		//定义dao方法的参数
		WechatQrcode wechatQrcode = new WechatQrcode();		
		wechatQrcode.setId(3);		
		wechatQrcode.setStatus(WechatQrcodeActivateServiceImpl.WECHAT_QRCODE_ACTIVITY);	
		
		//mock dao以及其方法updateStatusById(),返回rows
		//wechatQrcodeDao = Mockito.mock(WechatQrcodeDao.class);	
		
		Mockito.when(wechatQrcodeDao.updateStatusById(Mockito.argThat(new WechatQrcodeMatcher(wechatQrcode)))).thenReturn(rows);
				
		//把mock的dao set进入service
		wechatQrcodeActivateService =new WechatQrcodeActivateServiceImpl();		
		//wechatQrcodeActivateService.setWechatQrcodeDao(wechatQrcodeDao);
		ReflectionTestUtils.setField(wechatQrcodeActivateService, "wechatQrcodeDao", wechatQrcodeDao);
				
	}  
	
	
	@Test
	public void testWeChatQrocdeActivate()	{		
		
				
		int testParam=3; //定义service方法参数		
		
		//执行待测的service方法weChatQrocdeActivate()
		//weChatQrocdeActivate方法中含mock的dao
		BaseOutput baseOutPut=wechatQrcodeActivateService.weChatQrocdeActivate(testParam);
		
		//获取service方法执行结果
		logger.info("TotalCount="+baseOutPut.getTotalCount()+"---------test passed");
		int succ=baseOutPut.getCode();
		  
		//断言
		Assert.assertEquals(succ, ApiErrorCode.SUCCESS.getCode());
		

	}
	
	@After
    public void tearDown() throws Exception {
    }

	
	class WechatQrcodeMatcher extends ArgumentMatcher<WechatQrcode> {  
		
		WechatQrcode wcq=null;
		
		public  WechatQrcodeMatcher(WechatQrcode wechatQrcode) {
			
			wcq=wechatQrcode;
		}
		
		
		public boolean matches(Object obj) {
			int a1=wcq.getId();
			int a2=((WechatQrcode)obj).getId();			
			if (a1==a2) {				
			return true;
			}
			
			return false;
		}  
	}  
	

   
}
