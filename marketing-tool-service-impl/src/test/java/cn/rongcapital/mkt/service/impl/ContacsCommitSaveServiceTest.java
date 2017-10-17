/*************************************************
 * @功能简述: Service实现测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.ContactList;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseOutput;

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

import static org.mockito.Matchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class ContacsCommitSaveServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private ContacsCommitSaveService contacsCommitSaveService;
    
    @Mock
    private ContactListDao contactListDao;
    
    @Mock
    private ContactTemplateDao contactTemplateDao;
    
    @Mock
    private DefaultContactTemplateDao defaultContactTemplateDao;
    
    private List<ContactTemplate> contactTemplateList;
    
    private List<ContactList> contactList;
    
	private List<Object> data = new ArrayList<Object>();
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：ContacsCommitSaveService 准备---------------------");
        contactTemplateList = new ArrayList<ContactTemplate>();
        ContactTemplate contactTemplate = new ContactTemplate();
        contactTemplate.setFieldIndex(0);
        contactTemplate.setFieldName("姓名");
        contactTemplate.setFieldCode("name");
        contactTemplateList.add(contactTemplate);
        contactTemplate = new ContactTemplate();
        contactTemplate.setFieldIndex(1);
        contactTemplate.setFieldName("性别");
        contactTemplate.setFieldCode("gender");
        contactTemplateList.add(contactTemplate);	
        	
        Mockito.when(contactTemplateDao.selectListAll(any())).thenReturn(contactTemplateList);

        contactList = new ArrayList<ContactList>();
        ContactList  contact  = new ContactList();
        contact.setId(1);
        contact.setMobile("13800001111");
        contact.setName("张三");
        contact.setGender((byte) 1);
        contact.setBirthday(DateUtil.getDateFromString("1990-10-10", "yyyy-MM-dd"));
        contact.setProvice("河北省");
        contact.setCity("邯郸市");
        contact.setJob(null);
        contact.setMonthlyIncome(new BigDecimal(10000));
        contact.setMonthlyConsume(new BigDecimal(9999));
        contact.setMaritalStatus("1");
        contact.setEducation("2");
        contact.setEmployment(null);
        contact.setNationality("汉");
        contact.setBloodType("O型");
        contact.setCitizenship("中国");
        contact.setIq(180);
        contact.setIdentifyNo("13022419901010123x");
        contact.setDrivingLicense("驾照");
        contact.setEmail("zhangsan@126.com");
        contact.setTel("010-98563274");
        contact.setQq("3543210");
        contact.setAcctNo("0123456");
        contact.setAcctType("weixin");
        contact.setIdfa("sadfafasf");
        contact.setImei("987654");
        contact.setUdid("qwertyuiopasdf");
        contact.setPhoneMac("123456789");
        contact.setStatus(2);
        contact.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:30:35", "yyyy-MM-dd HH:mm:ss"));
        contact.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:30:35", "yyyy-MM-dd  HH:mm:ss"));
        contact.setSource("微信");
        contact.setBatchId("1");
        contact.setBitmap("10000000000000000");
        contact.setKeyid(516593);
        contact.setWxmpId("123456");
        contact.setWxCode("zhangsan");
        contact.setNickname("张三李四");
        contact.setWxHeaderUrl("http://www.baidu.com");
        contact.setSubscribeTime(DateUtil.getDateFromString("2016-10-10 10:30:35", "yyyy-MM-dd HH:mm:ss"));
        contact.setUnionid("1");
        contact.setLanguage("汉语");
        contact.setRemark("测试用例");
        Date commitDate= DateUtil.getDateFromString("2016-10-15 10:30:35", "yyyy-MM-dd HH:mm:ss");
        contact.setCommitTime(commitDate);
        contactList.add(contact);
        
        Mockito.when(contactListDao.selectListByContactIdAndCommitTime(any())).thenReturn(contactList);
        
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "张三");
        map.put("gender", "男");
        map.put("commit_time", "2016-10-15 10:30:35");
        map.put("commit_id", 1);
        data.add(map);
        
        contacsCommitSaveService = new ContactsCommitSaveServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(contacsCommitSaveService, "contactDao", contactListDao);
        ReflectionTestUtils.setField(contacsCommitSaveService, "contactTemplateDao", contactTemplateDao);
        ReflectionTestUtils.setField(contacsCommitSaveService, "defaultContactTemplateDao", defaultContactTemplateDao);
        
    }
    
    @Test
    public void testContactsCommitDel() {
        logger.info("测试方法: contactsCommitDel ");
    }
    
    @Test
    public void testContactsCommitDownload() {
        logger.info("测试方法: contactsCommitDownload ");
    }
    
    @Test
    public void testContactsCommitSave() {
        logger.info("测试方法: contactsCommitSave ");
    }
    
    @Test
    public void testTransferNameListtoMap() {
        logger.info("测试方法: transferNameListtoMap ");
    }
    
    @Test
    public void testContactsCommitGet() {
        logger.info("测试方法: testContactsCommitGet 开始 ");
        
        BaseOutput result0 = contacsCommitSaveService.contactsCommitGet(1, 0, 0, 10);
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result0.getCode());
        Map map0 = (Map)data.get(0);
        Map resultmap0 = (Map)result0.getData().get(0);
        Assert.assertEquals(map0.get("name"),resultmap0.get("name"));
        Assert.assertEquals(map0.get("gender"),resultmap0.get("gender"));
        Assert.assertEquals(map0.get("commit_time"),resultmap0.get("commit_time"));
        Assert.assertEquals(map0.get("commit_id"),resultmap0.get("commit_id"));
        Assert.assertEquals(result0.getData().size(),1);
        
        BaseOutput result1 = contacsCommitSaveService.contactsCommitGet(1, 1, 0, 10);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result1.getCode());
        Map map1 = (Map)data.get(0);
        Map resultmap1 = (Map)result0.getData().get(0);
        Assert.assertEquals(map1.get("name"),resultmap1.get("name"));
        Assert.assertEquals(map1.get("gender"),resultmap1.get("gender"));
        Assert.assertEquals(map1.get("commit_time"),resultmap1.get("commit_time"));
        Assert.assertEquals(map1.get("commit_id"),resultmap1.get("commit_id"));
        Assert.assertEquals(result1.getData().size(),1);
        
        
        BaseOutput result2 = contacsCommitSaveService.contactsCommitGet(1, 2, 0, 10);
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result2.getCode());
        Map map2 = (Map)data.get(0);
        Map resultmap2 = (Map)result0.getData().get(0);
        Assert.assertEquals(map2.get("name"),resultmap2.get("name"));
        Assert.assertEquals(map2.get("gender"),resultmap2.get("gender"));
        Assert.assertEquals(map2.get("commit_time"),resultmap2.get("commit_time"));
        Assert.assertEquals(map2.get("commit_id"),resultmap2.get("commit_id"));
        Assert.assertEquals(result2.getData().size(),1);
        
        BaseOutput result3 = contacsCommitSaveService.contactsCommitGet(1, 3, 0, 10);
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result3.getCode());
        Map map3 = (Map)data.get(0);
        Map resultmap3 = (Map)result0.getData().get(0);
        Assert.assertEquals(map3.get("name"),resultmap3.get("name"));
        Assert.assertEquals(map3.get("gender"),resultmap3.get("gender"));
        Assert.assertEquals(map3.get("commit_time"),resultmap3.get("commit_time"));
        Assert.assertEquals(map3.get("commit_id"),resultmap3.get("commit_id"));
        Assert.assertEquals(result3.getData().size(),1);
        
        BaseOutput result4= contacsCommitSaveService.contactsCommitGet(1, 4, 0, 10);
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result4.getCode());
        Map map4 = (Map)data.get(0);
        Map resultmap4 = (Map)result0.getData().get(0);
        Assert.assertEquals(map4.get("name"),resultmap4.get("name"));
        Assert.assertEquals(map4.get("gender"),resultmap4.get("gender"));
        Assert.assertEquals(map4.get("commit_time"),resultmap4.get("commit_time"));
        Assert.assertEquals(map4.get("commit_id"),resultmap4.get("commit_id"));
        Assert.assertEquals(result4.getData().size(),1);
        
        logger.info("测试方法: testContactsCommitGet 结束");
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：ContacsCommitSaveService 结束---------------------");
    }

}


