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

import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentHeadUpdateIn;

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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class SegmentHeaderUpdateServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SegmentHeaderUpdateService segmentHeaderUpdateService;
    
    
    @Mock
    private SegmentationHeadDao segmentationHeadDao;
    
    @Mock
    private SegmentationBodyDao segmentationBodyDao;
    
    private SegmentHeadUpdateIn segmentHeadUpdateIn0;
    
    private List<SegmentationHead> segList1 = new ArrayList<SegmentationHead>();
    
    private SecurityContext securityContext;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：SegmentHeaderUpdateService 准备---------------------");
        
        segmentHeaderUpdateService = new SegmentHeaderUpdateImpl();
        
        segmentHeadUpdateIn0 = new SegmentHeadUpdateIn();
        
        segmentHeadUpdateIn0.setSegmentId(1);
        segmentHeadUpdateIn0.setPublishStatus(0);
        
//        segmentHeadUpdateIn1 = new SegmentHeadUpdateIn();
//        
//        segmentHeadUpdateIn1.setSegmentId(2);
//        segmentHeadUpdateIn1.setPublishStatus(1);
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(segmentHeaderUpdateService, "segmentationHeadDao", segmentationHeadDao);
        ReflectionTestUtils.setField(segmentHeaderUpdateService, "segmentationBodyDao", segmentationBodyDao);
        
    }
    
    @Test
    public void testDeleteSegmentHeader() {
        logger.info("测试方法: deleteSegmentHeader ");
    }
    
    @Test
    public void testSegmentHeaderUpdate() {
        logger.info("测试方法: segmentHeaderUpdate ");
        
//        //1.被活动使用使用
//		SegmentationHead t = new SegmentationHead(); 
//        
//		t.setId(1);
//		t.setName("细分名称");
//		t.setPublishStatus((byte) 1);
//		t.setOper("oba");
//		t.setTagIds("123");
//		t.setReferCampaignCount(2);
//		t.setStatus((byte) 0);
//		segList1.add(t);
//		 
//        Mockito.when(segmentationHeadDao.selectList(any())).thenReturn(segList1);
//        BaseOutput ur0 =segmentHeaderUpdateService.segmentHeaderUpdate(segmentHeadUpdateIn0, securityContext);
//        
//        Assert.assertEquals(0, ur0.getData().size());         
        
        
//        //2.更新
//		SegmentationHead t = new SegmentationHead(); 
//        
//		t.setId(1);
//		t.setName("细分名称");
//		t.setPublishStatus((byte) 1);
//		t.setOper("oba");
//		t.setTagIds("123");
//		t.setReferCampaignCount(0);
//		t.setStatus((byte) 0);
//		segList1.add(t);
//		 
//        Mockito.when(segmentationHeadDao.selectList(any())).thenReturn(segList1);
//        BaseOutput ur0 =segmentHeaderUpdateService.segmentHeaderUpdate(segmentHeadUpdateIn0, securityContext);
//        
//        Assert.assertEquals(1, ur0.getData().size());  
        
        //3.没有数据
		 
        Mockito.when(segmentationHeadDao.selectList(any())).thenReturn(segList1);
        BaseOutput ur0 =segmentHeaderUpdateService.segmentHeaderUpdate(segmentHeadUpdateIn0, securityContext);
        
        Assert.assertEquals(0, ur0.getData().size()); 
        
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentHeaderUpdateService 结束---------------------");
    }

}


