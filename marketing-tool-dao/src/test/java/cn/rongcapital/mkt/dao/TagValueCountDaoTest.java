package cn.rongcapital.mkt.dao;

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

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.TagValueCount;

@RunWith(SpringJUnit4ClassRunner.class)
public class TagValueCountDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TagValueCountDao tagValueCountDao;

    TagValueCount tagValueCountTag;
    TagValueCount tagValueCountValue;
    TagValueCount tagValueCountValueTwo;

    @Before
    public void setUp() throws Exception {
        tagValueCountTag = new TagValueCount("09butpYr", "蛋糕慕斯系列", "蛋糕慕斯系列#", Long.valueOf(0), null,
                        "交易历史及偏好>交易历史及偏好>蛋糕慕斯系列>", "1", 0);
        tagValueCountValue = new TagValueCount("09butpYr", "蛋糕慕斯系列", "是", Long.valueOf(0), null,
                        "09butpYr_0", "交易历史及偏好>交易历史及偏好>蛋糕慕斯系列>", 0);
        tagValueCountValueTwo = new TagValueCount("09butpYr", "蛋糕慕斯系列", "否#", Long.valueOf(0), null,
                        "09butpYr_0", "交易历史及偏好>交易历史及偏好>蛋糕慕斯系列>", 0);
        
        tagValueCountDao.insert(tagValueCountTag);
        tagValueCountDao.insert(tagValueCountValue);
        tagValueCountDao.insert(tagValueCountValueTwo);
    }
    
    @Test
    public void testClearStockData() {
        
        int count = tagValueCountDao.clearStockData();
        Assert.assertEquals(3,count);
        
    }
    
    @Test
    public void testSelectTagValueId() {
        String tagValueSeq = tagValueCountDao.selectTagValueId("09butpYr","是");
        Assert.assertEquals(tagValueCountValue.getTagValueSeq(),tagValueSeq);
    }
    
    @Test
    public void testSelectTagValueCountListByKeyWord() {
        TagValueCount tagValueCountSelect = new TagValueCount();
        tagValueCountSelect.setTagId("09butpYr");
        
        // 测试模糊查询
        tagValueCountSelect.setTagValue("#");
        List<TagValueCount> tagValueCountListsFuzzy = tagValueCountDao.selectTagValueCountListByKeyWord(tagValueCountSelect);
        
        Assert.assertEquals(2,tagValueCountListsFuzzy.size());
        Assert.assertEquals(tagValueCountTag,tagValueCountListsFuzzy.get(0));
        Assert.assertEquals(tagValueCountValueTwo,tagValueCountListsFuzzy.get(1));
        
        // 测试精确查询
        tagValueCountSelect.setTagValue("是");
        List<TagValueCount> tagValueCountListsAll = tagValueCountDao.selectTagValueCountListByKeyWord(tagValueCountSelect);
        
        Assert.assertEquals(1,tagValueCountListsAll.size());
        Assert.assertEquals(tagValueCountValue,tagValueCountListsAll.get(0));
    }
    
    @Test
    public void testSelectFuzzyTagValue() {
        TagValueCount tagValueCountSelect = new TagValueCount();
        tagValueCountSelect.setTagValue("#");
        List<TagValueCount> tagValueCountLists = tagValueCountDao.selectFuzzyTagValue(tagValueCountSelect);
        
        Assert.assertEquals(2,tagValueCountLists.size());
        Assert.assertEquals(tagValueCountTag,tagValueCountLists.get(0));
        Assert.assertEquals(tagValueCountValueTwo,tagValueCountLists.get(1));
    }
    
    @Test
    public void testSelectFuzzyTagValueCount() {
        int count;
        
        TagValueCount tagValueCountSelect = new TagValueCount();
        
        // 测试全部
        count = tagValueCountDao.selectFuzzyTagValueCount(tagValueCountSelect);
        Assert.assertEquals(3,count);
        
        // 测试正常情况
        tagValueCountSelect.setTagValue("#");
        count = tagValueCountDao.selectFuzzyTagValueCount(tagValueCountSelect);
        Assert.assertEquals(2,count);
        
        // 测试分页
        tagValueCountSelect.setTagValue("#");
        tagValueCountSelect.setStartIndex(1);
        tagValueCountSelect.setPageSize(1);
        count = tagValueCountDao.selectFuzzyTagValueCount(tagValueCountSelect);
        Assert.assertEquals(2,count);
        
    }

    @After
    public void tearDown() throws Exception {
        tagValueCountDao.clearStockData();
    }

}
