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

import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.ContactsCommitDatapartyDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class ContactsCommitDownloadServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private ContactsCommitDatapartyDownloadService contactsCommitDatapartyDownloadService;
    
    @Mock
    private ImportTemplateDao importTemplateDao;
    
    @Mock
    private DataPartyDao dataPartyDao;
    
    private List<DataParty> dataList = new ArrayList<DataParty>();
    
    private List<ImportTemplate> importTemplates = new ArrayList<ImportTemplate>();
    
    private File file ;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：contactsCommitDatapartyDownloadService 准备---------------------");
 
        DataParty dataParty = new DataParty();
    
        dataParty.setMobile("13800123654");
        dataParty.setName("zhaosi");
        dataParty.setGender((byte) 1);
        dataParty.setBirthday(DateUtil.getDateFromString("1990-10-10", "yyyy-MM-dd"));
        
        dataParty.setProvice("辽宁省");
        dataParty.setCity("沈阳市");
        dataParty.setJob("无");
        dataParty.setMonthlyIncome(new BigDecimal(88888));
        dataParty.setMemberLevel("1");
        dataParty.setMemberPoints("100000");
        dataParty.setSource("联系人表单");
        dataParty.setMonthlyConsume(new BigDecimal(66666));
        dataParty.setLastLogin(DateUtil.getDateFromString("1990-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        dataParty.setStatus((byte) 1);
        dataParty.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        dataParty.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        dataList.add(dataParty);
        
        Mockito.when(dataPartyDao.selectListByContactId(anyInt())).thenReturn(dataList);
        
        ImportTemplate importTemplate1 = new ImportTemplate(); 
        
        importTemplate1.setId(1);
        importTemplate1.setTemplType(0);
        importTemplate1.setTemplName("主数据");
        importTemplate1.setFieldName("手机号");
        importTemplate1.setFieldCode("mobile");
        importTemplate1.setSelected(true);
        importTemplate1.setStatus((byte) 0);
        importTemplate1.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate1.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate1);
        
        ImportTemplate importTemplate2 = new ImportTemplate(); 
        
        importTemplate2.setId(2);
        importTemplate2.setTemplType(0);
        importTemplate2.setTemplName("主数据");
        importTemplate2.setFieldName("姓名号");
        importTemplate2.setFieldCode("name");
        importTemplate2.setSelected(true);
        importTemplate2.setStatus((byte) 0);
        importTemplate2.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate2.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate2);
        
        ImportTemplate importTemplate3 = new ImportTemplate(); 
        
        importTemplate3.setId(3);
        importTemplate3.setTemplType(0);
        importTemplate3.setTemplName("主数据");
        importTemplate3.setFieldName("性别");
        importTemplate3.setFieldCode("gender");
        importTemplate3.setSelected(true);
        importTemplate3.setStatus((byte) 0);
        importTemplate3.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate3.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate3);
        
        ImportTemplate importTemplate4 = new ImportTemplate(); 
        
        importTemplate4.setId(4);
        importTemplate4.setTemplType(0);
        importTemplate4.setTemplName("主数据");
        importTemplate4.setFieldName("出生年月日");
        importTemplate4.setFieldCode("birthday");
        importTemplate4.setSelected(true);
        importTemplate4.setStatus((byte) 0);
        importTemplate4.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate4.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate4);
        
        ImportTemplate importTemplate5 = new ImportTemplate(); 
        
        importTemplate5.setId(5);
        importTemplate5.setTemplType(0);
        importTemplate5.setTemplName("主数据");
        importTemplate5.setFieldName("省");
        importTemplate5.setFieldCode("provice");
        importTemplate5.setSelected(true);
        importTemplate5.setStatus((byte) 0);
        importTemplate5.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate5.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate5);
        
        ImportTemplate importTemplate6 = new ImportTemplate(); 
        
        importTemplate6.setId(6);
        importTemplate6.setTemplType(0);
        importTemplate6.setTemplName("主数据");
        importTemplate6.setFieldName("市");
        importTemplate6.setFieldCode("city");
        importTemplate6.setSelected(true);
        importTemplate6.setStatus((byte) 0);
        importTemplate6.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate6.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate6);
        
        
        ImportTemplate importTemplate7 = new ImportTemplate(); 
        
        importTemplate7.setId(7);
        importTemplate7.setTemplType(0);
        importTemplate7.setTemplName("主数据");
        importTemplate7.setFieldName("职业");
        importTemplate7.setFieldCode("job");
        importTemplate7.setSelected(true);
        importTemplate7.setStatus((byte) 0);
        importTemplate7.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate7.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate7);
        
        ImportTemplate importTemplate8 = new ImportTemplate(); 
        
        importTemplate8.setId(8);
        importTemplate8.setTemplType(0);
        importTemplate8.setTemplName("主数据");
        importTemplate8.setFieldName("月收入");
        importTemplate8.setFieldCode("monthly_income");
        importTemplate8.setSelected(true);
        importTemplate8.setStatus((byte) 0);
        importTemplate8.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate8.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate8);
        
        ImportTemplate importTemplate9 = new ImportTemplate(); 
        
        importTemplate9.setId(9);
        importTemplate9.setTemplType(0);
        importTemplate9.setTemplName("主数据");
        importTemplate9.setFieldName("会员等级");
        importTemplate9.setFieldCode("member_level");
        importTemplate9.setSelected(true);
        importTemplate9.setStatus((byte) 0);
        importTemplate9.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate9.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate9);
        
        
        ImportTemplate importTemplate10 = new ImportTemplate(); 
        
        importTemplate10.setId(10);
        importTemplate10.setTemplType(0);
        importTemplate10.setTemplName("主数据");
        importTemplate10.setFieldName("会员积分");
        importTemplate10.setFieldCode("member_points");
        importTemplate10.setSelected(true);
        importTemplate10.setStatus((byte) 0);
        importTemplate10.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate10.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate10);
        
        
        ImportTemplate importTemplate11 = new ImportTemplate(); 
        
        importTemplate11.setId(11);
        importTemplate11.setTemplType(0);
        importTemplate11.setTemplName("主数据");
        importTemplate11.setFieldName("数据来源");
        importTemplate11.setFieldCode("source");
        importTemplate11.setSelected(true);
        importTemplate11.setStatus((byte) 0);
        importTemplate11.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate11.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate11);
        
        
        ImportTemplate importTemplate12 = new ImportTemplate(); 
        
        importTemplate12.setId(12);
        importTemplate12.setTemplType(0);
        importTemplate12.setTemplName("主数据");
        importTemplate12.setFieldName("月均消费");
        importTemplate12.setFieldCode("monthly_consume");
        importTemplate12.setSelected(true);
        importTemplate12.setStatus((byte) 0);
        importTemplate12.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate12.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate12);
        
        
        ImportTemplate importTemplate13 = new ImportTemplate(); 
        
        importTemplate13.setId(13);
        importTemplate13.setTemplType(0);
        importTemplate13.setTemplName("主数据");
        importTemplate13.setFieldName("最后登录时间");
        importTemplate13.setFieldCode("last_login");
        importTemplate13.setSelected(true);
        importTemplate13.setStatus((byte) 0);
        importTemplate13.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate13.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate13);
        
        ImportTemplate importTemplate14 = new ImportTemplate(); 
        
        importTemplate14.setId(14);
        importTemplate14.setTemplType(0);
        importTemplate14.setTemplName("主数据");
        importTemplate14.setFieldName("删除标记");
        importTemplate14.setFieldCode("status");
        importTemplate14.setSelected(true);
        importTemplate14.setStatus((byte) 0);
        importTemplate14.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate14.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate14);
        
        ImportTemplate importTemplate15 = new ImportTemplate(); 
        
        importTemplate15.setId(15);
        importTemplate15.setTemplType(0);
        importTemplate15.setTemplName("主数据");
        importTemplate15.setFieldName("产生时间");
        importTemplate15.setFieldCode("create_time");
        importTemplate15.setSelected(true);
        importTemplate15.setStatus((byte) 0);
        importTemplate15.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate15.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate15);
        
        ImportTemplate importTemplate16 = new ImportTemplate(); 
        
        importTemplate16.setId(16);
        importTemplate16.setTemplType(0);
        importTemplate16.setTemplName("主数据");
        importTemplate16.setFieldName("删除时间");
        importTemplate16.setFieldCode("update_time");
        importTemplate16.setSelected(true);
        importTemplate16.setStatus((byte) 0);
        importTemplate16.setCreateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        importTemplate16.setUpdateTime(DateUtil.getDateFromString("2016-10-10 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        
        importTemplates.add(importTemplate16);
        
        Mockito.when(importTemplateDao.selectList(any())).thenReturn(importTemplates);
        
        file = new File("data_party_123654.csv");
        
//        Mockito.when(FileUtil.generateFileforDownload(any(), dataList, "data_party_123654.csv")).thenReturn(file);
        
        contactsCommitDatapartyDownloadService = new ContactsCommitDatapartyDownloadServiceImpl();
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(contactsCommitDatapartyDownloadService, "importTemplateDao", importTemplateDao);
        ReflectionTestUtils.setField(contactsCommitDatapartyDownloadService, "dataPartyDao", dataPartyDao);
        
    }
    
    
    @Test
    public void testContactsCommitDatapartyDownload() {
        logger.info("测试：contactsCommitDatapartyDownload 开始---------------------");
    	
        BaseOutput result =  contactsCommitDatapartyDownloadService.contactsCommitDatapartyDownload(1);
        Map map = (Map)result.getData().get(0);
        String fileName = map.get("download_url").toString();
        int index = fileName.indexOf("data_party_");
        Assert.assertTrue(index == 0);
        logger.info("测试：contactsCommitDatapartyDownload 结束---------------------");
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：contactsCommitDatapartyDownloadService 结束---------------------");
    }

}


