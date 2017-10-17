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
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.SegmentRedisVO;
import cn.rongcapital.mkt.vo.in.*;
import cn.rongcapital.mkt.vo.out.SegmentAreaCountOut;
import cn.rongcapital.mkt.vo.out.SegmentDimensionCountOut;

import cn.rongcapital.mkt.vo.out.SegmentFilterOut;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class SegmentFilterGetServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private SegmentFilterGetService segmentFilterGetService;
    
	@Mock
	private SegmentCalcService segmentCalcService;

	@Mock
	private TagValueCountDao tagValueCountDao;
	
	@Mock
	private SegmentManageCalService segmentManageCalService;
    
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：SegmentFilterGetService 准备---------------------");
        
        
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
    	
        segmentFilterGetService = new SegmentFilterGetServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(segmentFilterGetService, "segmentCalcService", segmentCalcService);
        ReflectionTestUtils.setField(segmentFilterGetService, "tagValueCountDao", tagValueCountDao);
        ReflectionTestUtils.setField(segmentFilterGetService, "segmentManageCalService", segmentManageCalService);
        
    }
    
    @Test
    public void testSegmentProvinceCountList() {
        logger.info("测试方法: segmentProvinceCountList ");
        
        SegmentCountFilterIn input = new SegmentCountFilterIn();
        
        List<Integer>  mids = new ArrayList<Integer>();
        
        mids.add(53);
        mids.add(75);
        
        input.setSegmentHeadIds(mids);
        
        input.setType(1);
        
        BaseOutput out = segmentFilterGetService.segmentProvinceCountList(input);
        
        List<Object> dataList = out.getData();
        
        SegmentAreaCountOut segmentAreaCountOut = (SegmentAreaCountOut)dataList.get(0);
        
        int china_population_count = segmentAreaCountOut.getChinaPopulationCount();
        
        Assert.assertEquals(20,china_population_count);
        
    }
    
    @Test
    public void testGetSegmentFilterSum() {
        logger.info("测试方法: getSegmentFilterSum ");
    }
    
    @Test
    public void testSegmentGenderCountList() {
        logger.info("测试方法: segmentGenderCountList ");
        
        SegmentCountFilterIn input = new SegmentCountFilterIn();
        
        List<Integer>  mids = new ArrayList<Integer>();
        
        mids.add(53);
        mids.add(75);
        
        input.setSegmentHeadIds(mids);
        BaseOutput out = segmentFilterGetService.segmentGenderCountList(input);
        List<Object> dataList = out.getData();
        
        SegmentDimensionCountOut segmentDimensionCountOut = (SegmentDimensionCountOut)dataList.get(0);
        
        int malecount = segmentDimensionCountOut.getPopulationCount();
        Assert.assertEquals(10,malecount);
        
    }
    
    @Test
    public void testGetSegmentFilterCount() {
        logger.info("测试方法: getSegmentFilterCount ");
    }
    
    @Test
    public void testSegmentReceiveCountList() {
        logger.info("测试方法: segmentReceiveCountList ");
    }
    
    @Test
    public void testGetSegmentFilterResult() throws Exception{
        //1.创建Mock数据
        TagGroupsListIn paramTagGroupsListIn = new TagGroupsListIn();
        List<TagGroupsIn> tagGroupsInList = new ArrayList<>();
        paramTagGroupsListIn.setTagGroupsInList(tagGroupsInList);
        TagGroupsIn tagGroupsIn = new TagGroupsIn();
        tagGroupsIn.setGroupId("nfycekdboydpzmt1");
        tagGroupsIn.setGroupName("分组");
        tagGroupsIn.setGroupIndex(0);
        tagGroupsIn.setGroupChange(1);
        List<SystemTagIn> systemTagInList = new ArrayList<>();
        tagGroupsIn.setTagList(systemTagInList);
        SystemTagIn systemTagIn = new SystemTagIn();
        systemTagIn.setTagId("X4yylWlQ");
        systemTagIn.setTagName("市");
        systemTagIn.setTagIndex(0);
        systemTagIn.setTagExclude(1);
        List<SystemValueIn> systemValueInList = new ArrayList<>();
        systemTagIn.setTagValueList(systemValueInList);
        SystemValueIn systemValueIn = new SystemValueIn();
        systemValueIn.setTagValueId("X4yylWlQ_29");
        systemValueIn.setTagValue("北京市");
        systemValueInList.add(systemValueIn);
        systemValueIn = new SystemValueIn();
        systemValueIn.setTagValueId("X4yylWlQ_118");
        systemValueIn.setTagValue("福州市");
        systemValueInList.add(systemValueIn);
        systemTagInList.add(systemTagIn);
        tagGroupsInList.add(tagGroupsIn);

        SegmentRedisVO resultSegmentRedisVO = new SegmentRedisVO();
        resultSegmentRedisVO.setSegmentCoverCount(Long.valueOf(100));
        Mockito.when(segmentCalcService.getSegmentRedis()).thenReturn(resultSegmentRedisVO);

        SegmentFilterOut segmentFilterOut = segmentFilterGetService.getSegmentFilterResult(paramTagGroupsListIn);
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentFilterGetService 结束---------------------");
    }

}


