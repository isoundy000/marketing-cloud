package cn.rongcapital.mkt.dao;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.SmsTaskAppEnum;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.SmsMaterial;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


/**
 * Created by byf on 11/18/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SmsMaterialSelectDaoTest extends AbstractUnitTest{

    @Autowired
    private SmsMaterialDao smsMaterialDao;

    SmsMaterial paramSmsMaterial;

    @Before
    public void setUp() throws Exception{
        paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setChannelType(SmsTaskAppEnum.ADVERT_SMS.getStatus());
        paramSmsMaterial.setSmsType(NumUtil.int2OneByte(SmsTempletTypeEnum.FIXED.getStatusCode()));
        paramSmsMaterial.setName("模糊搜索素材1");
        paramSmsMaterial.setSmsSignId(1);
        paramSmsMaterial.setSmsSignName("融数金服");
        paramSmsMaterial.setSmsTempletId(1);
        paramSmsMaterial.setSmsTempletContent("测试素材内容1");
        paramSmsMaterial.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        paramSmsMaterial.setCreator("user1");
        paramSmsMaterial.setCreateTime(new Date());
        paramSmsMaterial.setUpdateUser("user1");
        paramSmsMaterial.setUpdateTime(new Date());
        smsMaterialDao.insert(paramSmsMaterial);
    }

    @Test
    public void selectListByKeyword() throws Exception {
        SmsMaterial querySmsMaterial = new SmsMaterial();
        querySmsMaterial.setName("模糊");
        querySmsMaterial.setStartIndex(0);
        querySmsMaterial.setPageSize(10);
        querySmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterial> resultList = smsMaterialDao.selectListByKeyword(querySmsMaterial);
        if(CollectionUtils.isEmpty(resultList)) throw new Exception("数据选取失败,单元测试不通过");
        for(SmsMaterial smsMaterial : resultList){
            if(smsMaterial.getName().contains("模糊")){
                Assert.assertEquals(paramSmsMaterial.getName().equals(smsMaterial.getName()),true);
            }
        }
    }

    @After
    public void tearDown() throws Exception{
        paramSmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
        smsMaterialDao.updateById(paramSmsMaterial);
    }
}