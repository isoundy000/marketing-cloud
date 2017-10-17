package cn.rongcapital.mkt.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.common.enums.SmsTaskAppEnum;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.SmsMaterial;

@RunWith(SpringJUnit4ClassRunner.class)
public class SmsMaterialDaoTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SmsMaterialDao smsMaterialDao;
	
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

    private SmsMaterial setSmsMaterial(){
    	SmsMaterial smsMaterialTest = new SmsMaterial();
    	smsMaterialTest.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
    	smsMaterialTest.setSmsType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
    	smsMaterialTest.setName("测试素材1");
    	smsMaterialTest.setSmsSignId(1);
    	smsMaterialTest.setSmsSignName("融数金服");
    	smsMaterialTest.setSmsTempletId(1);
    	smsMaterialTest.setSmsTempletContent("测试素材内容1");
    	smsMaterialTest.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
    	smsMaterialTest.setCreator("user1");
    	smsMaterialTest.setCreateTime(new Date());
    	smsMaterialTest.setUpdateUser("user1");
    	smsMaterialTest.setUpdateTime(new Date());
		return smsMaterialTest;    	
    }
	
    @Test
    public void insertSmsMaterial(){
    	SmsMaterial smsMaterialTest = this.setSmsMaterial();    	
    	smsMaterialDao.insert(smsMaterialTest);
    	smsMaterialTest = new SmsMaterial();
    	smsMaterialTest.setName("测试素材");
    	smsMaterialTest.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
    	smsMaterialTest.setSmsType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
    	List<SmsMaterial> smsMaterialLists = smsMaterialDao.selectList(smsMaterialTest);
    	Assert.assertEquals(4, smsMaterialLists.size());
    }

    @Test
    public void updateSmsMaterial(){
//    	SmsMaterial smsMaterialTest = this.setSmsMaterial(); 
    	SmsMaterial smsMaterialTest = new SmsMaterial(); 
    	smsMaterialTest.setId(8);
    	smsMaterialTest.setName("测试素材8");
    	smsMaterialDao.updateById(smsMaterialTest);
    	smsMaterialTest = new SmsMaterial();
    	smsMaterialTest.setName("测试素材");
    	smsMaterialTest.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
    	smsMaterialTest.setSmsType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
    	List<SmsMaterial> smsMaterialLists = smsMaterialDao.selectList(smsMaterialTest);
    	Assert.assertEquals(4, smsMaterialLists.size());
    }
    
    @Test
    public void deleteSmsMaterial(){
    	SmsMaterial smsMaterialTest = new SmsMaterial(); 
    	smsMaterialTest.setId(8);
    	smsMaterialTest.setStatus(NumUtil.int2OneByte(StatusEnum.DELETED.getStatusCode()));
    	smsMaterialDao.updateById(smsMaterialTest);
    	smsMaterialTest = new SmsMaterial();
    	smsMaterialTest.setName("测试素材");
    	smsMaterialTest.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
    	smsMaterialTest.setSmsType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
    	smsMaterialTest.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
    	List<SmsMaterial> smsMaterialLists = smsMaterialDao.selectList(smsMaterialTest);
    	Assert.assertEquals(3, smsMaterialLists.size());
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
