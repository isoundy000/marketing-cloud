/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class WechatQrcodeFocusDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;
    
    Map<String, Object> paraMap = new HashMap<String, Object>();
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: WechatQrcodeFocusDao 开始---------------------");
               
        paraMap.put("chCode", "1");
        paraMap.put("wxAcct", "gh_4685d4eef135");
    
        //起始时间
        String startDate = "2016-10-26 00:00:00";
        String endDate = "2016-10-27 00:00:00";
        paraMap.put("startTime", DateUtil.getDateFromString(startDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
        paraMap.put("endTime", DateUtil.getDateFromString(endDate, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
    }
    
	@Test
	public void testGetLostFocusMax() {
	    logger.info("测试方法: getLostFocusMax ");    
	    Map<String, Object> lostFocusMax = wechatQrcodeFocusDao.getLostFocusMax(paraMap);
	    logger.info(lostFocusMax.toString());
	}
	
	@Test
	public void testGetNewFocusMax() {
	    logger.info("测试方法: getNewFocusMax ");
	    Map<String, Object> newFocusMax = wechatQrcodeFocusDao.getNewFocusMax(paraMap);
	    logger.info(newFocusMax.toString());
	}
	
	@Test
	public void testGetQrcodeIdList() {
	    logger.info("测试方法: getQrcodeIdList ");    
	}
	
	@Test
	public void testGetAddFocusMax() {
	    logger.info("测试方法: getAddFocusMax ");    
	    Map<String, Object> AddFocusMax = wechatQrcodeFocusDao.getAddFocusMax(paraMap);
	    logger.info(AddFocusMax.toString());
	}
	
	@Test
	public void testGetAllFocusData() {
	    
	       
        List<Map<String, Object>> analysisChdataMap= wechatQrcodeFocusDao.getAllFocusData(paraMap);        
        logger.info(analysisChdataMap.toString());
	    logger.info("测试方法: getAllFocusData ");    
	}
	
	@Test
	public void testSelectTheEarliestFocus() {
	    logger.info("测试方法: selectTheEarliestFocus ");    
	}
	
	@Test
	public void testGetAmountFocusMax() {
	    logger.info("测试方法: getAmountFocusMax ");   
	    
	    Map<String, Object> amountFocusMax=wechatQrcodeFocusDao.getAmountFocusMax(paraMap);
	    logger.info(amountFocusMax.toString());
	    
	}
	
	@Test
	public void testSelectTheEarliestFocusByQrcodeId() {
	    logger.info("测试方法: selectTheEarliestFocusByQrcodeId ");    
	}
	
	@Test
	public void testGetFocusCount() {
	    logger.info("测试方法: getFocusCount ");   
	    Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("searchDate", "2016-10-26");
        paramMap.put("fieldName", "focus_datetime");
        paramMap.put("chCode", "1");
        paramMap.put("wxName", "瑞雪营销云");
        logger.info("Day FocusCount="+wechatQrcodeFocusDao.getFocusCount(paramMap));
               
	}
	
	@Test
	public void testGetNetFocusCount() {
        logger.info("测试方法: getNetFocusCount ");  
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("searchDate", "2016-10-22");
        paramMap.put("fieldName", "unfocus_datetime");
        paramMap.put("chCode", "1");
        paramMap.put("wxName", "瑞雪营销云");
        Integer count=wechatQrcodeFocusDao.getNetFocusCount(paramMap);
        logger.info("Day NetFocusCount="+count);
    }
	
	@Test
	public void testInsert() {
	    logger.info("测试方法: insert ");    
	}
	
	@Test
	public void testSelectListCount() {
	    logger.info("测试方法: selectListCount ");    
	}
	
	@Test
	public void testUpdateById() {
	    logger.info("测试方法: updateById ");    
	}
	
	@Test
	public void testSelectList() {
	    logger.info("测试方法: selectList ");    
	}
	
	@Test
	public void testSelectListByIdList() {
	    logger.info("测试方法: selectListByIdList ");    
	}
	
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: WechatQrcodeFocusDao 结束---------------------");
    }
}