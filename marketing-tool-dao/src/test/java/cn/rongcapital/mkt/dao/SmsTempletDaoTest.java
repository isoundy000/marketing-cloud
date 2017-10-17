package cn.rongcapital.mkt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.SmsTaskAppEnum;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.enums.SmsTempleteAuditStatusEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.SmsTemplet;

@RunWith(SpringJUnit4ClassRunner.class)
public class SmsTempletDaoTest extends AbstractUnitTest {

    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsTempletDao smsTempletDao;
    
    int total = 3;
    SmsTemplet[] smsTemplet = new SmsTemplet[total];
    
    @Before
    public void setUp() throws Exception {
        
/*        Byte[] channelType = {0,1,2};
        Byte[] type = {0, 1, 0};
        Byte[] auditStatus = {0, 0, 0};
        String[] content = {"模板内容测试12", "模板内容测试13", "模板内容测试4"} ;
        
        for(int i =0 ; i < total; i++) {
            smsTemplet[i] = new SmsTemplet(channelType[i], type[i],auditStatus[i], content[i]);
            smsTempletDao.insert(smsTemplet[i]);
        }*/
        
    }
    
    @Test
    public void testSelectList() {
        
        List<SmsTemplet> smsTempletLists;
        SmsTemplet smsTempletTest = new SmsTemplet();
        smsTempletTest.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        smsTempletTest.setOrderField("create_time");
        smsTempletTest.setOrderFieldType("DESC");
        smsTempletTest.setStartIndex(0);
        smsTempletTest.setPageSize(10);
        
        /**
         *  测试查询所有
         */
/*        smsTempletLists = smsTempletDao.selectList(smsTempletTest);
        Assert.assertEquals(4, smsTempletLists.size());
*/        
        /**
         *  测试模糊查询1
         */
/*        smsTempletTest.setContent("模板内容测试");
        smsTempletLists = smsTempletDao.selectList(smsTempletTest);
        Assert.assertEquals(3, smsTempletLists.size());
        Assert.assertEquals(smsTemplet[0].getContent(), smsTempletLists.get(0).getContent());
*/        
        /**
         *  测试模糊查询2
         */
/*        smsTempletTest.setContent("4");
        smsTempletLists = smsTempletDao.selectList(smsTempletTest);
        Assert.assertEquals(1, smsTempletLists.size());
        Assert.assertEquals(smsTemplet[2].getContent(), smsTempletLists.get(0).getContent());
*/
        /**
         *  测试模糊查询3   
         */
        /**
         * 0:营销短信模板,1:服务通知模板,2：短信验证码模板;
         */
//        smsTempletTest.setChannelType(NumUtil.int2OneByte(SmsTaskAppEnum.ADVERT_SMS.getStatus()));
//        smsTempletTest.setChannelType(NumUtil.int2OneByte(SmsTaskAppEnum.SERVICE_SMS.getStatus()));
//        smsTempletTest.setChannelType(NumUtil.int2OneByte(SmsTaskAppEnum.IDENTIFY_CODE_SMS.getStatus()));        
        /**
         * 模板类型：0:固定模板,1:变量模板
         */
//        smsTempletTest.setType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
//        smsTempletTest.setType(NumUtil.int2OneByte(SmsTempletTypeEnum.VARIABLE.getStatusCode()));
        /**
         * 模板内容
         */
        smsTempletTest.setContent("测试");
        smsTempletLists = smsTempletDao.selectList(smsTempletTest);
        Assert.assertEquals(1, smsTempletLists.size());               
    }
    
    private SmsTemplet setSmsTemplet(){
    	SmsTemplet smsTempletTest = new SmsTemplet();
    	smsTempletTest.setName("测试模板");
    	smsTempletTest.setAuditor("user1");
//    	smsTempletTest.setAuditStatus(NumUtil.int2OneByte(SmsTempleteAuditStatusEnum.AUDIT_STATUS_PASS.getStatusCode()));
//    	smsTempletTest.setAuditStatus(NumUtil.int2OneByte(SmsTempleteAuditStatusEnum.AUDIT_STATUS_NO_CHECK.getStatusCode()));
    	smsTempletTest.setAuditStatus(NumUtil.int2OneByte(SmsTempleteAuditStatusEnum.AUDIT_STATUS_NO_PASS.getStatusCode()));
    	smsTempletTest.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
    	smsTempletTest.setCode("abcdefg123456789");
    	smsTempletTest.setContent("测试模板");
    	smsTempletTest.setCreateTime(new Date());
    	smsTempletTest.setCreator("user1");
    	smsTempletTest.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
    	smsTempletTest.setUpdateTime(new Date());
    	smsTempletTest.setUpdateUser("user1");
    	smsTempletTest.setType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
		return smsTempletTest;    	
    }
    
    @Test
    public void insertSmsTemplet(){
    	SmsTemplet smsTempletTest = this.setSmsTemplet();    	
    	smsTempletDao.insert(smsTempletTest);
    	smsTempletTest = new SmsTemplet();
    	smsTempletTest.setContent("测试模板");
    	smsTempletTest.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
    	smsTempletTest.setType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
    	List<SmsTemplet> smsTempletLists = smsTempletDao.selectList(smsTempletTest);
    	Assert.assertEquals(1, smsTempletLists.size());
    }
    
    @Test
    public void getTempletCountByType(){
    	List<Map<String, Object>> templetCountByType = smsTempletDao.getTempletCountByType("1");
    	
    //	Assert.assertEquals(2, templetCountByType.size()); 
    	
    }
    
    @After
    public void tearDown() throws Exception {
/*        // 逻辑删除数据
        for(int i =0 ; i < total; i++) {
            smsTemplet[i].setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            smsTempletDao.updateById(smsTemplet[i]);
        }
*/        
    }
}
