package cn.rongcapital.mkt.biz.impl;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import com.qq.weixin.mp.aes.AesException;

import cn.rongcapital.mkt.biz.ProcessReceiveMessageOfWeiXinBiz;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeTicketDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.service.QrcodeFocusInsertService;
import cn.rongcapital.mkt.service.WechatAssetService;
import cn.rongcapital.mkt.service.WechatInterfaceLogService;
import cn.rongcapital.mkt.service.WeixinAnalysisQrcodeScanService;

@RunWith(MockitoJUnitRunner.class)
public class ProcessReceiveMessageOfWeiXinTest {

	 private ProcessReceiveMessageOfWeiXinBiz processReceiveMessageOfWeiXinBiz;
	
	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
    @Mock
	WechatInterfaceLogService wechatInterfaceLogService;
    @Mock
    private Environment env;	 
    @Mock
	private WechatMemberDao wechatMemberDao;	
    @Mock
	private WebchatAuthInfoDao webchatAuthInfoDao;	
    @Mock
	private QrcodeFocusInsertService qrcodeFocusInsertService;	
    @Mock
	private WechatAssetService wechatAssetService;	
    @Mock
	private WechatRegisterDao wechatRegisterDao;    
    @Mock
    private WechatQrcodeTicketDao wechatQrcodeTicketDao;   
    @Mock
    private WeixinAnalysisQrcodeScanService weixinAnalysisQrcodeScanService;   
    @Mock
    private WechatQrcodeDao wechatQrcodeDao;
    
    @Before
    public void setUp() throws Exception {
		 processReceiveMessageOfWeiXinBiz = new ProcessReceiveMessageOfWeiXinImpl();
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "wechatInterfaceLogService", wechatInterfaceLogService);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "env", env);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "wechatMemberDao", wechatMemberDao);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "webchatAuthInfoDao", webchatAuthInfoDao);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "qrcodeFocusInsertService", qrcodeFocusInsertService);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "wechatAssetService", wechatAssetService);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "wechatRegisterDao", wechatRegisterDao);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "wechatQrcodeTicketDao", wechatQrcodeTicketDao);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "weixinAnalysisQrcodeScanService", weixinAnalysisQrcodeScanService);
		 ReflectionTestUtils.setField(processReceiveMessageOfWeiXinBiz, "wechatQrcodeDao", wechatQrcodeDao);
    }
	 
	 @Test
	 public void testValidateMsgSendState(){
		 StringBuffer textXml= new StringBuffer();
		 textXml.append("<xml>");
		 textXml.append("<ToUserName><![CDATA[gh_3c884a361561]]></ToUserName>");
		 textXml.append("<Encrypt><![CDATA[plJT+Ispvve6iADUHVdWbNPktN/cf4uM9bAV/kh+G2cC0DXrafDGdPrc+GRESRZHM5lOV3hMzUB69HDHdgEqpfNvIKeT7txCpIoNaijRhf/4HMb7rfiCagSEdYdM+UILJB0dEtFn/Jf6zwylIYrHo2I+OEH+zalJdkxssCvoX6crtpwh3MctMb6kkHFjEkH13NXIeYCqQ6UnOpjd6h4szq6mS7tUaPthl/EjqiuxefIiEbJz5033Q+KIh4KnRyci2TkNku9z4fjUBn0CLkDJxNfoP9zelvw/BLQ0VFvAajfSkvepeF3Ys+RlX3KTWBOelmlGPvWz9Yd8LONHnmHKAJ2JJwtLFgD53bqAhXFNTNAEJ9co7mrrv4oTJ4bPGAgrlVw8ykvNC0z62A+QJn10aF8UNOnvoTkahSz9uKxfP8P+YwPwOczJg7dko8z9SJG0DjCClEqdWDKFkmgyE19a8HJJLlIcEL3p4Af2/kiyg38CFXq7+EqACU46iYN8etldtKQt5pN/KkycrMlW8QCY9IDkLXUzOAvPJAQE1iJA1WZMFcWuDxOeOmcaxr92/wDClApYy+qKsk2nU+psWvUC8A==]]></Encrypt>");
		 textXml.append("</xml>");
		 String msg_signature="894d68a03e96a133fc9aeb656d43704ece104d73";
		 String timestamp="1480910000";
		 String nonce="2095471832";
		 String signature="f0948a2bde3b4d8a5afd1b2e1dd70b548f637461";
		 String openid="ozy4qt0Rsc9YJzR5nEeVAaTHg9DQ";
		 String authAppId="wx570bc396a51b8ff8";
		 
/*		 StringBuffer textXml= new StringBuffer();
		 textXml.append("<xml>");
		 textXml.append("<ToUserName><![CDATA[gh_3c884a361561]]></ToUserName>");
		 textXml.append("<Encrypt><![CDATA[pyTZrlg6jzJdlx9Uq7391m7IF6VL9YG7tVZOzEnZhKry6sSKfih72Masb9207nXVP5cfWbb/A77woUtbMaLX51si5AbkIhkGpVpisx8sO1aETG0myr8jn/23W7MhVmKo+YYHQ01XHwNRE0j5MeAUwhasKOdynX2BPWS6NCNAllHl/ODJZfxgGUCWxz2Em3aewp0w8eMBQEGcSDXpDLQojyM9bOMZXXclSU0ny4Ty81hfY3tNEiH5Y5Vbiix9n3VVfnG/C+x2L+L9fm5OCfMcHmpfxK/6zhqmz3RSr0vBT1c2JHZL659qDsaPEPUkvkOlK3AdjpJyetsckn1jPgx/2E3q+qnjd6HSo+yBMwZ24rurYQ+b/BbbPOhv3AFJT3jZLf8Okpzglvq3p8pvDfcEVdJa+Uufye0DRA+9LFxvDgz5Nx2FPTS6rNp/KbfyD7HBg4Etixy6Wh58gi8gtx8dIw==]]></Encrypt>");
		 textXml.append("</xml>");
		 String msg_signature="msg_signature：52bc5f75b4cfc2bbffb085f63cc11ea3ebe761ca";
		 String timestamp="1480910009";
		 String nonce="245238553";
		 String signature="dea86dfb0a1ccc7cb852ab7174247e431e300ce6";
		 String openid="ozy4qt0Rsc9YJzR5nEeVAaTHg9DQ";
		 String authAppId="wx570bc396a51b8ff8";
		 
		 StringBuffer textXml= new StringBuffer();
		 textXml.append("<xml>");
		 textXml.append("<ToUserName><![CDATA[gh_3c884a361561]]></ToUserName>");
		 textXml.append("<Encrypt><![CDATA[L0NPN9JM8ZoWGYzaMarVLNUJAcW6pVOutnRwTBG0Rh2pTTEEa4X0IvO+5MwomGTz9eX58dZATZHitY9QKRkWoaM/v5kleJhTjq1QcoFN4AgIWZv8+jbWlHvuhc5LR5mBS3wngqYjD89CqXx33wGq1LjwKIxpsWm3J03XEd7NX9YetGGcMp6XiScrPKnoClYH85tr1pwz6SUG648SNC1/4of7RzDQ+Y6vyMp/G5l9X/FiXi6uZWt/p/QRdHsf/TQPsJclpdAYZit/9N6lvxSjzh760y1Asrt6AXiuMgEILRm2VTj9LJHJBLguc6nDUAv864PFF9UxES3Uy2lL0rGLiTp9zj0Cf3i7QU3BTGdQlcL/2RpD4LFkdRDTJyHOtanrEd3EKkVGZgFcpkSbrldPrYryC4rupnBkmmNek7wMuiNBJ1FmLamGEXcqxMqfVyolSaqaOueWRL5BQeIjFlCvT8wofru9MrdUnKLYX+9aVnd4vYrd0mMOCeBXij4/Ahx/]]></Encrypt>");
		 textXml.append("</xml>");
		 String msg_signature="ec018b0ccfbbe006e700af8c82540a4bc41e342a";
		 String timestamp="1480910017";
		 String nonce="1830052199";
		 String signature="c09fea3c0f7a16db13490bce0919c3aefb0554d2";
		 String openid="ozy4qt0Rsc9YJzR5nEeVAaTHg9DQ";
		 String authAppId="wx570bc396a51b8ff8";
*/		 
		 try {
			 boolean isSendSuccess = processReceiveMessageOfWeiXinBiz.validateMsgSendState(textXml.toString(), msg_signature, timestamp, nonce, signature, openid, authAppId);	
			 Assert.assertEquals(true, isSendSuccess);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (AesException e) {
			e.printStackTrace();
		}
	 }
	 
}
