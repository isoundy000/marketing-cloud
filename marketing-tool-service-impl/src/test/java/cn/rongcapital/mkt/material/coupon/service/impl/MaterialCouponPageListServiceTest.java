/*************************************************
 * @功能及特点的描述简述: MaterialCouponPageListService测试类 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6 @date(创建、开发日期)：2016-12月-07 最后修改日期：2016-12月-07 @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponChannelCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponPageListService;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponListOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponPageListServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MaterialCouponPageListService materialCouponPageListService;

    @Mock
    private MaterialCouponDao meterialCouponDao;

    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2016年12月7日
     */
    @Before
    public void setUp() throws Exception {

        logger.info("测试：ContactKeyListGetService 准备---------------------");
        materialCouponPageListService = new MaterialCouponPageListServiceImpl();
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(materialCouponPageListService, "meterialCouponDao", meterialCouponDao);
    }

    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2016年12月7日
     */
    @After
    public void tearDown() throws Exception {
        logger.info("测试：ContactKeyListGetService 结束---------------------");
    }

    @Test
    public void getMaterialCouponListByKeywordChanelCodeNull() {
        logger.info("测试方法: getContactKeyList ");
        MaterialCoupon paramMaterialCoupon = new MaterialCoupon();
        MaterialCouponChannelCodeEnum chanelCode = MaterialCouponChannelCodeEnum.SMS;
        MaterialCouponStatusEnum couponStatus = MaterialCouponStatusEnum.UNUSED;
        String keyword = "贝贝熊";
        Integer index = 1;
        Integer size = 10;
        int totalCount = 20;


        paramMaterialCoupon.setTitle("贝贝熊");
        paramMaterialCoupon.setStartIndex(1);
        paramMaterialCoupon.setPageSize(10);

        List<MaterialCoupon> resultList = new ArrayList<MaterialCoupon>();
        resultList.add(paramMaterialCoupon);

        Mockito.when(meterialCouponDao.selectListByKeyword(Mockito
                .argThat(new MaterialCouponPageListServiceTest.MaterialCouponMatcherMockTwo(paramMaterialCoupon))))
                .thenReturn(resultList);
        Mockito.when(meterialCouponDao.selectListByKeywordCount(Mockito
                .argThat(new MaterialCouponPageListServiceTest.MaterialCouponMatcherMockTwo((paramMaterialCoupon)))))
                .thenReturn(totalCount);

        BaseOutput baseOutput = materialCouponPageListService.getMaterialCouponListByKeyword(null, null, keyword, index, size, Boolean.FALSE);
        
        BaseOutput expected = new BaseOutput(ApiErrorCode.PARAMETER_ERROR.getCode(), ApiErrorCode.VALIDATE_ERROR.getMsg(),
            ApiConstant.INT_ZERO, null);
        Assert.assertEquals(expected.getCode(), baseOutput.getCode());

        String channelCode = "ERROR";
        baseOutput = materialCouponPageListService.getMaterialCouponListByKeyword(channelCode, null, keyword, index, size, Boolean.FALSE);

        Assert.assertEquals(ApiErrorCode.PARAMETER_ERROR.getCode(), baseOutput.getCode());
    }
    
    
    @Test
    public void getMaterialCouponListByKeyword() {
        logger.info("测试方法: getContactKeyList ");
        MaterialCoupon paramMaterialCoupon = new MaterialCoupon();
        MaterialCouponChannelCodeEnum chanelCode = MaterialCouponChannelCodeEnum.SMS;
        MaterialCouponStatusEnum couponStatus = MaterialCouponStatusEnum.UNUSED;
        String keyword = "贝贝熊";
        Integer index = 1;
        Integer size = 10;
        int totalCount = 20;

        materialCouponPageListService.getMaterialCouponListByKeyword(chanelCode.getCode(), couponStatus.getCode(), keyword, index, size, Boolean.FALSE);

        paramMaterialCoupon.setTitle("贝贝熊");
        paramMaterialCoupon.setStartIndex(1);
        paramMaterialCoupon.setPageSize(10);
        paramMaterialCoupon.setChannelCode(chanelCode.getCode());
        paramMaterialCoupon.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        paramMaterialCoupon.setCreateTime(new Date());

        List<MaterialCoupon> resultList = new ArrayList<MaterialCoupon>();
        resultList.add(paramMaterialCoupon);

        Mockito.when(meterialCouponDao.selectListByKeyword(Mockito
                .argThat(new MaterialCouponPageListServiceTest.MaterialCouponMatcherMockTwo(paramMaterialCoupon))))
                .thenReturn(resultList);
        Mockito.when(meterialCouponDao.selectListByKeywordCount(Mockito
                .argThat(new MaterialCouponPageListServiceTest.MaterialCouponMatcherMockTwo((paramMaterialCoupon)))))
                .thenReturn(totalCount);

        MaterialCouponListOut baseOutput = materialCouponPageListService.getMaterialCouponListByKeyword(chanelCode.getCode(), couponStatus.getCode(),
                keyword, index, size, Boolean.FALSE);
        Assert.assertEquals(baseOutput.getCode(), ApiErrorCode.SUCCESS.getCode());
        Assert.assertEquals(totalCount, baseOutput.getTotalCount());
        Assert.assertEquals(1, baseOutput.getListItems().size());


         baseOutput = materialCouponPageListService.getMaterialCouponListByKeyword(chanelCode.getCode(), couponStatus.getCode(),
                keyword, index, size, Boolean.TRUE);
        Assert.assertEquals(baseOutput.getCode(), ApiErrorCode.SUCCESS.getCode());
        Assert.assertEquals(totalCount, baseOutput.getTotalCount());
        Assert.assertEquals(1, baseOutput.getListItems().size());
    }
    
    @Test
    public void getMaterialCouponListByKeywordErrorStatus() {
        logger.info("测试方法: getContactKeyList ");
        MaterialCoupon paramMaterialCoupon = new MaterialCoupon();
        MaterialCouponChannelCodeEnum chanelCode = MaterialCouponChannelCodeEnum.SMS;
        String keyword = "贝贝熊";
        Integer index = 1;
        Integer size = 10;
        int totalCount = 20;

        paramMaterialCoupon.setTitle("贝贝熊");
        paramMaterialCoupon.setStartIndex(1);
        paramMaterialCoupon.setPageSize(10);
        paramMaterialCoupon.setChannelCode(chanelCode.getCode());
        paramMaterialCoupon.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        paramMaterialCoupon.setCreateTime(new Date());

        List<MaterialCoupon> resultList = new ArrayList<MaterialCoupon>();
        resultList.add(paramMaterialCoupon);

        Mockito.when(meterialCouponDao.selectListByKeyword(Mockito
                .argThat(new MaterialCouponPageListServiceTest.MaterialCouponMatcherMockTwo(paramMaterialCoupon))))
                .thenReturn(resultList);
        Mockito.when(meterialCouponDao.selectListByKeywordCount(Mockito
                .argThat(new MaterialCouponPageListServiceTest.MaterialCouponMatcherMockTwo((paramMaterialCoupon)))))
                .thenReturn(totalCount);

        BaseOutput baseOutput = materialCouponPageListService.getMaterialCouponListByKeyword(chanelCode.getCode(), "error",
                keyword, index, size, Boolean.FALSE);
        BaseOutput expected = new BaseOutput(ApiErrorCode.PARAMETER_ERROR.getCode(), ApiErrorCode.VALIDATE_ERROR.getMsg(),
            ApiConstant.INT_ZERO, null);
        Assert.assertEquals(expected.getCode(), baseOutput.getCode());


         baseOutput = materialCouponPageListService.getMaterialCouponListByKeyword(chanelCode.getCode(),null,
                keyword, index, size, Boolean.FALSE);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());

    }
    
    @Test
    public void getMaterialCouponListByKeywordTestWithNoStatus() {
        logger.info("测试方法: getContactKeyList ");
        MaterialCoupon paramMaterialCoupon = new MaterialCoupon();
        MaterialCouponChannelCodeEnum chanelCode = MaterialCouponChannelCodeEnum.SMS;
        MaterialCouponStatusEnum couponStatus = MaterialCouponStatusEnum.UNUSED;
        String keyword = "贝贝熊";
        Integer index = 1;
        Integer size = 10;
        int totalCount = 20;

        materialCouponPageListService.getMaterialCouponListByKeyword(chanelCode.getCode(), couponStatus.getCode(), keyword, index, size, Boolean.FALSE);

        paramMaterialCoupon.setTitle("贝贝熊");
        paramMaterialCoupon.setStartIndex(1);
        paramMaterialCoupon.setPageSize(10);
        paramMaterialCoupon.setChannelCode(chanelCode.getCode());
        paramMaterialCoupon.setCreateTime(new Date());

        List<MaterialCoupon> resultList = new ArrayList<MaterialCoupon>();
        resultList.add(paramMaterialCoupon);

        Mockito.when(meterialCouponDao.selectListByKeyword(Mockito
                .argThat(new MaterialCouponPageListServiceTest.MaterialCouponMatcherMockTwo(paramMaterialCoupon))))
                .thenReturn(resultList);
        Mockito.when(meterialCouponDao.selectListByKeywordCount(Mockito
                .argThat(new MaterialCouponPageListServiceTest.MaterialCouponMatcherMockTwo((paramMaterialCoupon)))))
                .thenReturn(totalCount);

        MaterialCouponListOut baseOutput = materialCouponPageListService.getMaterialCouponListByKeyword(chanelCode.getCode(), couponStatus.getCode(),
                keyword, index, size, Boolean.FALSE);
        Assert.assertEquals(baseOutput.getCode(), ApiErrorCode.SUCCESS.getCode());
        Assert.assertEquals(totalCount, baseOutput.getTotalCount());
        Assert.assertEquals(1, baseOutput.getListItems().size());
    }

    class MaterialCouponMatcherMockTwo extends ArgumentMatcher<MaterialCoupon> {

        MaterialCoupon materialCoupon = null;

        public MaterialCouponMatcherMockTwo(MaterialCoupon materialCoupon) {
            this.materialCoupon = materialCoupon;
        }

        public boolean matches(Object obj) {
            String name = materialCoupon.getTitle();
            String objName = ((MaterialCoupon) obj).getTitle();
            if (name.equals(objName)) return true;
            return false;
        }
    }

}
