package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.CityDicDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.ProvinceDicDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.CityDic;
import cn.rongcapital.mkt.po.ProvinceDic;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.service.DataPopulationService;
import cn.rongcapital.mkt.service.testbase.AbstractUnitTest;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataPopulationServiceTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
	private DataPopulationService dataPopulationService;
	
    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private DataPopulationDao dataPopulationDao;
    
    @Autowired
    private ProvinceDicDao provinceDicDao;
    
    @Autowired
    private CityDicDao cityDicDao;
	
//	@Mock
//    private WechatMemberDao wechatMemberDao;
//	@Mock
//    private DataPopulationDao dataPopulationDao;    
//	@Mock
//    private ProvinceDicDao provinceDicDao;    
//	@Mock
//    private CityDicDao cityDicDao;
	
	private List<WechatMember> wechatMembers;
	
	private String format = "yyyy-MM-dd HH:mm:ss";
	
    private Map<String, ProvinceDic> provinceDicMap = new HashMap<String, ProvinceDic>();
    
    private Map<String, CityDic> cityDicMap = new HashMap<String, CityDic>();
 
	
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：DataMainRadarInfoGetService 准备---------------------");
        
        wechatMembers = new ArrayList<WechatMember>();
        WechatMember wechatMember = new WechatMember();
        wechatMember.setActivity48hYn("N");
        wechatMember.setBitmap("00000011000000000");
        wechatMember.setCountry("China");
        wechatMember.setProvince(null);
        wechatMember.setCity(null);
        wechatMember.setCreateTime(DateUtil.getDateFromString("2016-12-16 14:32:57", format));
        wechatMember.getCustomMap();
        wechatMember.setFansJson("{\"city\":\"\",\"country\":\"China\",\"headImageUrl\":\"http://wx.qlogo.cn/mmopen/WXejyrfgAdPtxJD5GuicibVo3KEpFejicZOHEgdZJaA1w0LLPia2hR2BpOjiaupKkJNHsYnzOkY9K733DVBDRyGz0YiaIEeKEL0fec/0\",\"nickname\":\"大丛子\",\"province\":\"\",\"pubId\":\"gh_4685d4eef135\",\"remark\":\"\",\"sex\":1,\"subscribeTime\":\"2016-12-05 10:38:13\",\"wxCode\":\"o0gycwPRSkdEoP3dBLWMpe-JCKBQ\",\"wxGroupId\":\"\",\"wxName\":\"大丛子\"}");
        wechatMember.setHeadImageUrl("http://wx.qlogo.cn/mmopen/WXejyrfgAdPtxJD5GuicibVo3KEpFejicZOHEgdZJaA1w0LLPia2hR2BpOjiaupKkJNHsYnzOkY9K733DVBDRyGz0YiaIEeKEL0fec/0");
        wechatMember.setId(31L);
        wechatMember.setKeyid(555981);
        wechatMember.setNickname("大丛子");
        wechatMember.setPubId("gh_4685d4eef135");
        wechatMember.setSelected(NumUtil.int2OneByte(0));
        wechatMember.setSex(1);
        wechatMember.setStatus(NumUtil.int2OneByte(0));
        wechatMember.setSubscribeTime("2016-12-05 10:38:13");
        wechatMember.setSubscribeYn("Y");
        wechatMember.setUpdateTime(DateUtil.getDateFromString("2016-12-18 01:30:00", format));
        wechatMember.setWxCode("o0gycwPRSkdEoP3dBLWMpe-JCKBQ");
        wechatMember.setWxGroupId("999,");
        wechatMember.setWxName("大丛子"); 
        wechatMember.setUin(null);
        wechatMember.setSignature(null);
        wechatMember.setRemark(null);
        wechatMember.setCounty(null);
        wechatMember.setIsFriend(null);
        wechatMember.setActiveTime(null);
        wechatMember.setBirthday(null);
        wechatMembers.add(wechatMember);
                
    	/**
    	 * 注入省的编码
    	 */
        ProvinceDic provinceDic = new ProvinceDic();        
        provinceDic.setProvinceNamee("Beijing");
        provinceDic.setProvinceNamec("北京市");        
        provinceDicMap.put(provinceDic.getProvinceNamee(), provinceDic);
        provinceDic = new ProvinceDic();
        provinceDic.setProvinceNamee("Hunan");
        provinceDic.setProvinceNamec("湖南省");        
        provinceDicMap.put(provinceDic.getProvinceNamee(), provinceDic);
    	
    	/**
    	 * 
    	 */
        CityDic cityDic = new CityDic();
        cityDic.setCityNamee("");
        cityDic.setCityNamec("北京市");
        cityDicMap.put(cityDic.getCityNamee(), cityDic);
        cityDic = new CityDic();
        cityDic.setCityNamee("Changsha");
        cityDic.setCityNamec("长沙市");
        cityDicMap.put(cityDic.getCityNamee(), cityDic);
        
        dataPopulationService = new DataPopulationServiceImpl();
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(dataPopulationService, "wechatMemberDao", wechatMemberDao);
        ReflectionTestUtils.setField(dataPopulationService, "dataPopulationDao", dataPopulationDao);
        ReflectionTestUtils.setField(dataPopulationService, "provinceDicDao", provinceDicDao);
        ReflectionTestUtils.setField(dataPopulationService, "cityDicDao", cityDicDao);        
    }
    
    @Test
    public void synchronizeMemberToDataPopulationAndUpdateMemberTest(){    	
    	dataPopulationService.synchronizeMemberToDataPopulationAndUpdateMember(wechatMembers);
    }
    
    @Test
    public void synchronizeMemberToDataPopulationAndUpdateMember1Test(){
    	dataPopulationService.synchronizeMemberToDataPopulationAndUpdateMember(wechatMembers, provinceDicMap, cityDicMap);    	
    }
    
    @Test
    public void synchronizeMemberToDataPopulationTest(){
    	dataPopulationService.synchronizeMemberToDataPopulation(wechatMembers);    	
    }
    
    @Test
    public void synchronizeMemberToDataPopulation1Test(){
    	dataPopulationService.synchronizeMemberToDataPopulation(wechatMembers, provinceDicMap, cityDicMap);
    }
    
}
