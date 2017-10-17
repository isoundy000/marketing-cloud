/*************************************************
 * @功能及特点的描述简述: 获取接入属性的test
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016年12月13日 
 * @date(最后修改日期)：2016年12月13日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.material.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.MaterialAccessPropertyDao;
import cn.rongcapital.mkt.material.po.MaterialAccessProperty;
import cn.rongcapital.mkt.material.service.MaterialCouponPropertiesService;
import cn.rongcapital.mkt.material.service.impl.MaterialCouponPropertiesServiceImpl;
import cn.rongcapital.mkt.vo.BaseOutput;
import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponPropertiesServiceTest {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Mock
    private MaterialAccessPropertyDao materialAccessPropertyDao;
    
    private MaterialCouponPropertiesService materialCouponPropertiesService;
    
    BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
    
    @Before
    public void set()
    {
        materialCouponPropertiesService =new MaterialCouponPropertiesServiceImpl();
        ReflectionTestUtils.setField(materialCouponPropertiesService, "materialAccessPropertyDao", materialAccessPropertyDao);
        
    }
    @Test
    public void test()
    {
         //没有物料接入属性时
        Mockito.when(materialAccessPropertyDao.selectList(any())).thenReturn(null);
        BaseOutput actual =materialCouponPropertiesService.getProperties(any());
        logger.info(JSON.toJSONString(actual));
        Assert.assertEquals(JSON.toJSONString(result), JSON.toJSONString(actual));
        
        List<MaterialAccessProperty> list =new ArrayList<MaterialAccessProperty>();
        //有物料数据时
        MaterialAccessProperty mp= new MaterialAccessProperty();
        mp.setId(1l);
        mp.setMaterialTypeId(1l);
        mp.setName("有效期");
        mp.setCode("start_time,end_time"); 
        list.add(mp);
        MaterialAccessProperty mp1= new MaterialAccessProperty();
        mp1.setId(2l);
        mp1.setMaterialTypeId(1l);
        mp1.setCode("amount");
        mp1.setName("价值");
        list.add(mp1);
        MaterialAccessProperty mp2= new MaterialAccessProperty();
        mp2.setId(3l);
        mp2.setName("渠道");
        mp2.setMaterialTypeId(1l);
        mp2.setCode("channel_code");
        list.add(mp2);
        Mockito.when(materialAccessPropertyDao.selectList(any())).thenReturn(list);
        actual=materialCouponPropertiesService.getProperties(any());
        logger.info(JSON.toJSONString(actual));
        Assert.assertEquals(3,actual.getTotal());
        
        
        
        
        
    }
}
