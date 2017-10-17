package cn.rongcapital.mkt.service.impl;
import cn.rongcapital.mkt.common.jedis.JedisConnectionManager;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.service.SegmentCalcService;
import cn.rongcapital.mkt.service.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.vo.SegmentRedisVO;
import cn.rongcapital.mkt.vo.in.SegmentCreUpdateIn;
import cn.rongcapital.mkt.vo.in.SystemTagIn;
import cn.rongcapital.mkt.vo.in.SystemValueIn;
import cn.rongcapital.mkt.vo.in.TagGroupsIn;
import redis.clients.jedis.Jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class SegmentCalcServiceTest extends AbstractUnitTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
  
    private SegmentCalcService segmentCalcService;
    
    TagGroupsIn tagGroup=new TagGroupsIn();
    SegmentCreUpdateIn segment=new SegmentCreUpdateIn();
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: SegmentCalcServiceTest 开始---------------------");
       
        
        demoData3();
        segmentCalcService=new SegmentCalcServiceImpl();
    }
    
    @Test
    public void testCalcSegmentCoverByGroup()  {
        logger.info("测试方法: calcSegmentCoverByGroup ");  
       
        //segmentCalcService.calcSegmentCoverByGroup(tagGroup);           
        
    }
    
    @Test
    public void testCalcSegmentCover() throws Exception {
        logger.info("测试方法: calcSegmentCover");  
       
        segmentCalcService.calcSegmentCover(segment);  
        //segmentCalcService.saveSegmentCover() ;
        //mutiThreadTest();
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: SegmentCalcServiceTest 结束---------------------");
    }
    
    //测试一般情况单组
    private void demoData1()
    {
        tagGroup.setGroupId("000FFF");
        tagGroup.setGroupName("testGroup");
        tagGroup.setGroupIndex(0);
        SystemTagIn systag1=new SystemTagIn();
        SystemTagIn systag2=new SystemTagIn();
        List<SystemTagIn> tagList=new ArrayList<SystemTagIn>();
        tagList.add(systag1);
        tagList.add(systag2);
        tagGroup.setTagList(tagList);  
        
        List<SystemValueIn> values1=new ArrayList<SystemValueIn>();
        SystemValueIn val1=new SystemValueIn(); //男
        val1.setTagValueId("Td29zdq8_0");
        val1.setTagValue("男");
        SystemValueIn val2=new SystemValueIn(); //女
        val2.setTagValueId("Td29zdq8_1");
        val2.setTagValue("女");
        values1.add(val1);
        values1.add(val2);
        systag1.setTagId("Td29zdq8");
        systag1.setTagName("性别");
        //systag1.setTagExclude(tagExclude);
        systag1.setTagIndex(0);
        
        List<SystemValueIn> values2=new ArrayList<SystemValueIn>();
        SystemValueIn val3=new SystemValueIn(); 
        val3.setTagValueId("Zc2E7DO0_0");
        val3.setTagValue("是");
        SystemValueIn val4=new SystemValueIn(); 
        val4.setTagValueId("Zc2E7DO0_1");
        val4.setTagValue("否");
        values2.add(val3);
        values2.add(val4);
        systag2.setTagId("Zc2E7DO0");
        systag2.setTagName("30天内新注册会员");
        //systag1.setTagExclude(tagExclude);
        systag2.setTagIndex(1);
        
        
        systag1.setTagValueList(values1);
        systag2.setTagValueList(values2);
    }
    
    //测试包含排除单组
    private void demoData2()
    {
        tagGroup.setGroupId("000FFF");
        tagGroup.setGroupName("testGroup");
        tagGroup.setGroupIndex(0);
        SystemTagIn systag1=new SystemTagIn();
        SystemTagIn systag2=new SystemTagIn();
        List<SystemTagIn> tagList=new ArrayList<SystemTagIn>();
        tagList.add(systag1);
        tagList.add(systag2);
        tagGroup.setTagList(tagList);  
        
        List<SystemValueIn> values1=new ArrayList<SystemValueIn>();
        SystemValueIn val1=new SystemValueIn(); //男
        val1.setTagValueId("Td29zdq8_0");
        val1.setTagValue("男");
        SystemValueIn val2=new SystemValueIn(); //女
        val2.setTagValueId("Td29zdq8_1");
        val2.setTagValue("女");
        values1.add(val1);
        values1.add(val2);
        systag1.setTagId("Td29zdq8");
        systag1.setTagName("性别");
        //systag1.setTagExclude(tagExclude);
        systag1.setTagIndex(0);
        
        List<SystemValueIn> values2=new ArrayList<SystemValueIn>();
        SystemValueIn val3=new SystemValueIn(); 
        val3.setTagValueId("Zc2E7DO0_0");
        val3.setTagValue("是");
        
        values2.add(val3);        
        systag2.setTagId("Zc2E7DO0");
        systag2.setTagName("30天内新注册会员");
        systag2.setTagExclude(1); //排除
        systag2.setTagIndex(1);
        
        
        systag1.setTagValueList(values1);
        systag2.setTagValueList(values2);
    }
    
    //测试单Tag，无标签值 单组（实际不存在此种情况）
    private void demoData3()
    {
        List<TagGroupsIn> groups=new ArrayList<TagGroupsIn>();
        TagGroupsIn tagGroup1=new TagGroupsIn();
        TagGroupsIn tagGroup2=new TagGroupsIn();
        groups.add(tagGroup2);
        groups.add(tagGroup1);
        segment.setFilterGroups(groups);
        //segment.setSegmentHeadId(888L);
        tagGroup1.setGroupChange(0);
        tagGroup1.setGroupId("000FFF");
        tagGroup1.setGroupName("testGroup");
        tagGroup1.setGroupIndex(0);
        SystemTagIn systag1=new SystemTagIn();
        SystemTagIn systag2=new SystemTagIn();
        List<SystemTagIn> tagList=new ArrayList<SystemTagIn>();
        tagList.add(systag1);
        
        tagGroup1.setTagList(tagList);  
        
        List<SystemValueIn> values1=new ArrayList<SystemValueIn>();
        SystemValueIn val1=new SystemValueIn(); //男
        val1.setTagValueId("TestTagA_0");
        val1.setTagValue("男");      
        values1.add(val1);        
        systag1.setTagId("TestTagA");
        systag1.setTagName("性别");
        //systag1.setTagExclude(tagExclude);
        systag1.setTagIndex(0);
        
        List<SystemValueIn> values2=new ArrayList<SystemValueIn>();
        SystemValueIn val3=new SystemValueIn(); 
        val3.setTagValueId("TestTagB_0");
        val3.setTagValue("是");
       
        values2.add(val3);
        
        systag2.setTagId("TestTagB");
        systag2.setTagName("30天内新注册会员");
        //systag1.setTagExclude(tagExclude);
        systag2.setTagIndex(1);        
        
        systag1.setTagValueList(values1);
        systag2.setTagValueList(values2);
        
        tagGroup2.setGroupId("000XXX");
        tagGroup2.setGroupName("testGroup2");
        tagGroup2.setGroupIndex(1);
        tagGroup2.setGroupChange(1);
        SystemTagIn systag3=new SystemTagIn();
        List<SystemTagIn> tagList2=new ArrayList<SystemTagIn>();
        tagList2.add(systag3);
        tagGroup2.setTagList(tagList2);
        
        List<SystemValueIn> valuesX=new ArrayList<SystemValueIn>();
        SystemValueIn valX=new SystemValueIn(); //男
        valX.setTagValueId("TestTagC_0");
        valX.setTagValue("是");
        systag3.setTagValueList(valuesX);
        
        systag3.setTagId("TestTagC");
        systag3.setTagName("蛋糕芝士系列");
        //systag1.setTagExclude(tagExclude);
        systag3.setTagIndex(0);
    }
    
  //测试一般情况单组
    private void demoData4()
    {
        List<TagGroupsIn> groups=new ArrayList<TagGroupsIn>();
        TagGroupsIn tagGroup1=new TagGroupsIn();
        TagGroupsIn tagGroup2=new TagGroupsIn();
        groups.add(tagGroup2);
        groups.add(tagGroup1);
        segment.setFilterGroups(groups);
        //segment.setSegmentHeadId(888L);
        tagGroup1.setGroupChange(0);
        tagGroup1.setGroupId("000FFF");
        tagGroup1.setGroupName("testGroup");
        tagGroup1.setGroupIndex(0);
        SystemTagIn systag1=new SystemTagIn();
        SystemTagIn systag2=new SystemTagIn();
        List<SystemTagIn> tagList=new ArrayList<SystemTagIn>();
        tagList.add(systag1);
        tagList.add(systag2);
        tagGroup1.setTagList(tagList);  
        
        List<SystemValueIn> values1=new ArrayList<SystemValueIn>();
        SystemValueIn val1=new SystemValueIn(); //男
        val1.setTagValueId("TestTagA_0");
        val1.setTagValue("男");
        SystemValueIn val2=new SystemValueIn(); //女
        val2.setTagValueId("TestTagA_1");
        val2.setTagValue("女");
        values1.add(val1);
        values1.add(val2);
        systag1.setTagId("TestTagA");
        systag1.setTagName("性别");
        //systag1.setTagExclude(tagExclude);
        systag1.setTagIndex(0);
        
        List<SystemValueIn> values2=new ArrayList<SystemValueIn>();
        SystemValueIn val3=new SystemValueIn(); 
        val3.setTagValueId("TestTagB_0");
        val3.setTagValue("是");
        SystemValueIn val4=new SystemValueIn(); 
        val4.setTagValueId("TestTagB_1");
        val4.setTagValue("否");
        values2.add(val3);
        values2.add(val4);
        systag2.setTagId("TestTagB");
        systag2.setTagName("30天内新注册会员");
        //systag1.setTagExclude(tagExclude);
        systag2.setTagIndex(1);        
        
        systag1.setTagValueList(values1);
        systag2.setTagValueList(values2);
        
        tagGroup2.setGroupId("000XXX");
        tagGroup2.setGroupName("testGroup2");
        tagGroup2.setGroupIndex(1);
        tagGroup2.setGroupChange(1);
        SystemTagIn systag3=new SystemTagIn();
        List<SystemTagIn> tagList2=new ArrayList<SystemTagIn>();
        tagList2.add(systag3);
        tagGroup2.setTagList(tagList2);
        
        List<SystemValueIn> valuesX=new ArrayList<SystemValueIn>();
        SystemValueIn valX=new SystemValueIn(); //男
        valX.setTagValueId("TestTagC_0");
        valX.setTagValue("是");
        systag3.setTagValueList(valuesX);
        
        systag3.setTagId("TestTagC");
        systag3.setTagName("蛋糕芝士系列");
        //systag1.setTagExclude(tagExclude);
        systag3.setTagIndex(0);
    }
    
    private void multiThreadTest() {
        Thread t1 = new Thread(new Runnable(){
            public void run(){
                System.out.println("Mythread 线程t1");
                while(true){                    
                    SegmentRedisVO vo=new SegmentRedisVO();
                    vo.setSegmentName("00000000000000");
                    segmentCalcService.setSegmentRedis(vo);
                    SegmentRedisVO seg=  segmentCalcService.getSegmentRedis();                    
                    System.out.println("线程t1 >> " + seg.getSegmentName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
            }
        });
        
        Thread t2 = new Thread(new Runnable(){
            public void run(){
                System.out.println("Mythread 线程t2");
                while(true){                    
                    SegmentRedisVO vo=new SegmentRedisVO();
                    vo.setSegmentName("111111111111111");
                    segmentCalcService.setSegmentRedis(vo);
                    SegmentRedisVO seg=  segmentCalcService.getSegmentRedis();                    
                    System.out.println("线程t2>> " + seg.getSegmentName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
            }
        });
        t1.start();
        t2.start();
    }
}
