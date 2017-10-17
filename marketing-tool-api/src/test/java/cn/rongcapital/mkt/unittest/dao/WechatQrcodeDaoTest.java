package cn.rongcapital.mkt.unittest.dao;

import org.junit.Assert;
import org.junit.Before;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.unittest.AbstractUnitTest;

@RunWith(SpringJUnit4ClassRunner.class)

public class WechatQrcodeDaoTest extends AbstractUnitTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;
	
	WechatQrcode testWechatQrcode=new WechatQrcode(); 
	
	@Before  
	public void setUp() throws Exception {

		testWechatQrcode.setWxAcct("zqUT1_wxAcct");
		testWechatQrcode.setWxName("zqUT1_wxName");
		testWechatQrcode.setChCode(1);
		testWechatQrcode.setIsAudience(NumUtil.int2OneByte(1));
		testWechatQrcode.setAudienceName("zqUT1_audienceName");
		testWechatQrcode.setRelatedTags("6184;6185;6186;6187");
		testWechatQrcode.setComments("zqUT1_comments");
		testWechatQrcode.setExpirationTime(DateUtil.getDateFromString("2017-08-01 18:44:44","yyyy-MM-dd hh:mm:ss"));
		testWechatQrcode.setStatus(NumUtil.int2OneByte(1));
		testWechatQrcode.setQrcodePic("zqUT1_qrcodePic");
		testWechatQrcode.setQrcodeUrl("zqUT1_qrcodeUrl");
		testWechatQrcode.setBatchId("20160914112430");
		testWechatQrcode.setQrcodeName("zqUT1_qrcodeName");
		testWechatQrcode.setTicket("2055");
		testInsert();
	}
	
	
	
	@Test
	public void test()
	{
		
		testUpdateStatusById();
		
	}
	
	private void testInsert() {
	
		int rows=wechatQrcodeDao.insert(testWechatQrcode);	
		logger.info("...inserted rows:"+rows);		
	}
	
	
	
	
	private void testUpdateStatusById()	{
		
		Byte succ= 5;
		logger.info("-------------test 1:wechatQrcodeDao.updateStatusById-----------");		
				
		List<WechatQrcode> mylist=wechatQrcodeDao.selectList(testWechatQrcode);
		WechatQrcode oldwq=mylist.get(0);
		logger.info("....oldstauts="+oldwq.getStatus());	
		
		testWechatQrcode.setStatus(succ);
		wechatQrcodeDao.updateStatusById(testWechatQrcode);
		
		List<WechatQrcode> mylist2=wechatQrcodeDao.selectList(testWechatQrcode);
		WechatQrcode newwq=mylist2.get(0);
		logger.info("....newstauts="+newwq.getStatus());					
		Assert.assertEquals(succ,newwq.getStatus() );
		logger.info("-------------test 1:wechatQrcodeDao.updateStatusById-----------passed");	
	}
	
	private void delAllTestData(){
		List<WechatQrcode> selectList = wechatQrcodeDao.selectList(testWechatQrcode);
		if(selectList != null && selectList.size() > 0){				
			for(int i=0;i<selectList.size() ;i++) {
				WechatQrcode dels=selectList.get(i);
				int rows=wechatQrcodeDao.deleteById(dels);
				logger.info("...deleted rows:"+rows);
			}			
		}		
	}
	
	@After
	public void tearDown() throws Exception {
		delAllTestData();
	}

}
