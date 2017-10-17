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
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentAudienctAnalysisDataOut;
import cn.rongcapital.mkt.vo.out.SegmentPublishstatusListOut;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
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
public class SegmentAudienctAnalysisServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SegmentAudienctAnalysisService segmentAudienctAnalysisService;
    
    @Mock
	private TagValueCountDao tagValueCountDao;
	
    @Mock
	private SegmentManageCalService segmentManageCalService;
	
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：SegmentAudienctAnalysisService 准备---------------------");
        
    	Mockito.when(segmentManageCalService.scard(any(),any())).thenReturn(10l);

    	Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
    		
    	}).when(segmentManageCalService).sinterstore(any(), any(), any());
    	
    	Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
    		
    	}).when(segmentManageCalService).sunionstore(any(), any(), any());
    	
    	Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
    		
    	}).when(segmentManageCalService).deleteTempKey(any(), any());
        
    	TagValueCount tagValueCount = new TagValueCount();
    	
    	tagValueCount.setTagId("Ewg6v3z1");
    	tagValueCount.setTagName("");
    	tagValueCount.setTagValue("北京市");
    	tagValueCount.setTagValueSeq("Ewg6v3z1_0");
    	
    	TagValueCount tagValueCount2 = new TagValueCount();
    	
    	tagValueCount2.setTagId("Ewg6v3z1");
    	tagValueCount2.setTagName("");
    	tagValueCount2.setTagValue("河北省");
    	tagValueCount2.setTagValueSeq("Ewg6v3z1_2");
    	
    	
    	List<TagValueCount> tagValueCountList = new ArrayList<TagValueCount>();
    	
    	tagValueCountList.add(tagValueCount);
    	tagValueCountList.add(tagValueCount2);
    	
    	Mockito.when(tagValueCountDao.selectList(any())).thenReturn(tagValueCountList);
    	
    	segmentAudienctAnalysisService = new SegmentAudienctAnalysisServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(segmentAudienctAnalysisService, "tagValueCountDao", tagValueCountDao);
        ReflectionTestUtils.setField(segmentAudienctAnalysisService, "segmentManageCalService", segmentManageCalService);
    }
    
    @Test
    public void testGetSegmentAudienctAnalysis() {
        logger.info("测试方法: getSegmentAudienctAnalysis ");
        
        BaseOutput out = segmentAudienctAnalysisService.getSegmentAudienctAnalysis("Ewg6v3z1", 100);
     
        SegmentAudienctAnalysisDataOut segmentAudienctAnalysisDataOut =  (SegmentAudienctAnalysisDataOut)out.getData().get(0);
        
        Assert.assertEquals(2, segmentAudienctAnalysisDataOut.getPopulationCount().size());
        
        
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentPublishstatusListService 结束---------------------");
    }

}


