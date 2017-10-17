package cn.rongcapital.mkt.unittest.dao;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.unittest.AbstractUnitTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 10/20/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)

public class DataPartyDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPartyDao dataPartyDao;

    //Todo:需要测试的方法: selectDistinctMobileListByIdList
    List<Long> dataPartyIdList = new ArrayList<>();
    DataParty dataParty = new DataParty();

    @Before
    public void setUp() throws Exception{
        dataParty.setMobile("1019090909X");
        dataPartyDao.insert(dataParty);
        dataPartyIdList.add(Long.valueOf(dataParty.getId()));
    }

    @Test
    public void test(){
        List<String> distinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList);
        if(!CollectionUtils.isEmpty(distinctMobileList)){
            Assert.assertEquals(distinctMobileList.contains(dataParty.getMobile()),true);
        }
    }

    @After
    public void tearDown(){
        dataParty.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
        dataPartyDao.updateById(dataParty);
    }
}
