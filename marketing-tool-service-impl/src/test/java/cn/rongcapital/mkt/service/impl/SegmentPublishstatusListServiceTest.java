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
import cn.rongcapital.mkt.po.SegmentationBody;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.out.SegmentPublishstatusListOut;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class SegmentPublishstatusListServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SegmentPublishstatusListService segmentPublishstatusListService;
    
    @Mock
    private SegmentationHeadDao segmentationHeadDao;
    
    @Mock
    private SegmentationBodyDao segmentationBodyDao;
    
    @Mock
    private SegmentManageCalService segmentManageCalService;
    
	private List<SegmentationHead> segmentationHeadList;
	
	private List<String> tagNameList;
	
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：SegmentPublishstatusListService 准备---------------------");
        
        segmentationHeadList = new ArrayList<SegmentationHead>();
        SegmentationHead segmentationHead2 = new SegmentationHead();
        segmentationHead2.setId(5);
        segmentationHead2.setName("ceshi1");
        
        segmentationHeadList.add(segmentationHead2);
        SegmentationHead segmentationHead3 = new SegmentationHead();
        segmentationHead3.setId(4);
        segmentationHead3.setName("ceshi3");
        segmentationHeadList.add(segmentationHead3);
        
        
        tagNameList = new ArrayList<String>();
        tagNameList.add("性别");
        tagNameList.add("是否蛋糕系列");
        
        Mockito.when(segmentationHeadDao.selectListByKeyword(any())).thenReturn(segmentationHeadList);
        Mockito.when(segmentationHeadDao.selectListCount(any())).thenReturn(100);
        Mockito.when(segmentationBodyDao.getContainTagsByHeadId(any())).thenReturn(tagNameList);
        Mockito.when(segmentManageCalService.scard(any(), any())).thenReturn(100l);
        
        segmentPublishstatusListService = new SegmentPublishstatusListServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(segmentPublishstatusListService, "segmentationHeadDao", segmentationHeadDao);
        ReflectionTestUtils.setField(segmentPublishstatusListService, "segmentationBodyDao", segmentationBodyDao);
        ReflectionTestUtils.setField(segmentPublishstatusListService, "segmentManageCalService", segmentManageCalService);
    }
    
    @Test
    public void testSegmentPublishstatusList() {
        logger.info("测试方法: segmentPublishstatusList ");
        
        SegmentPublishstatusListOut segmentPublishstatusListOut1 = segmentPublishstatusListService.segmentPublishstatusList("", 0, 1, 7, "106", "");
        
        Assert.assertEquals(2, segmentPublishstatusListOut1.getDataCustom().size());
        
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentPublishstatusListService 结束---------------------");
    }

}


