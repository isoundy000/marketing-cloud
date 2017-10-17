/*************************************************
 * @功能及特点的描述简述: MaterialCouponCodeCheckServiceImpl测试类 该类被编译测试过
 * @see （与该类关联的类）：cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6 @date(创建、开发日期)：2016-12月-09 @date(最后修改日期)：2016-12月-09 @复审人：
 *************************************************/

package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeMaxTypeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeCheckService;
import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeMaxCountOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponCodeCheckServiceTest {
    
    private static final long MAX_COUNT = 1000000;

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    private MaterialCouponCodeCheckService checkService; //service实例

    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao; //mock

    @Mock
    private MaterialCouponDao materialCouponDao; //mock

    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2016年12月9日
     */
    @Before
    public void setUp() throws Exception {
        logger.info(
                "测试：MaterialCouponCodeCheckServiceTest 准备---------------------");
        checkService = new MaterialCouponCodeCheckServiceImpl();
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(checkService, "materialCouponCodeDao",
                materialCouponCodeDao);
        ReflectionTestUtils.setField(checkService, "materialCouponDao",
                materialCouponDao);
    }

    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2016年12月9日
     */
    @After
    public void tearDown() throws Exception {
        logger.info(
                "测试：MaterialCouponCodeCheckServiceTest 结束---------------------");
    }

    /**
     * Test method for
     * {@link cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl#materialCouponCodeCheck(java.lang.Long, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMaterialCouponCodeCheckSuccess() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, 1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");
        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(new Date()); // 设置当前日期
        startCalender.add(Calendar.DATE, -1); // 日期加1
        Date startDate = startCalender.getTime(); // 结果
        mockMaterialCoupon.setStartTime(startDate);
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);

        BaseOutput output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
    }
    
    
    @Test
    public void testMaterialCouponCodeCheckSizeIsZero() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, 1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");
        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(new Date()); // 设置当前日期
        startCalender.add(Calendar.DATE, -1); // 日期加1
        Date startDate = startCalender.getTime(); // 结果
        mockMaterialCoupon.setStartTime(startDate);
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(new ArrayList<>());

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);

        BaseOutput output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), output.getMsg());
        
        
        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
        .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(new ArrayList<>());
        
         output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), output.getMsg());
        
        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
        .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(null);
        
         output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), output.getMsg());
    }



    /**
     * Test method for
     * {@link cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl#materialCouponCodeCheck(java.lang.Long, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMaterialCouponCodeCheckNotFound() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, 1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");
        mockMaterialCoupon.setStartTime(new Date());
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(null);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);

        BaseOutput output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(
                ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                output.getCode());
    }

    /**
     * Test method for
     * {@link cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl#materialCouponCodeCheck(java.lang.Long, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMaterialCouponCodeCheckNotACTIVE() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, 1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");
        mockMaterialCoupon.setStartTime(new Date());
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode())); // 当前状态为已删除
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.DELETED.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);

        BaseOutput output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(
                ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_DELETE
                        .getCode(),
                output.getCode());
    }

    /**
     * Test method for
     * {@link cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl#materialCouponCodeCheck(java.lang.Long, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMaterialCouponCodeCheckNotRECEIVED() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, 1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");
        mockMaterialCoupon.setStartTime(new Date());
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode())); // 当前状态为已删除
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);

        BaseOutput output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(
                ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_RELEASE
                        .getCode(),
                output.getCode());
    }

    /**
     * Test method for
     * {@link cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl#materialCouponCodeCheck(java.lang.Long, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMaterialCouponCodeCheckNotUNVERIFY() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, 1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");
        mockMaterialCoupon.setStartTime(new Date());
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode())); // 当前状态为已删除
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);

        BaseOutput output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(
                ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_VERIFY
                        .getCode(),
                output.getCode());
    }

    /**
     * Test method for
     * {@link cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl#materialCouponCodeCheck(java.lang.Long, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMaterialCouponCodeCheckNotInPeriod() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, -1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");

        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(new Date()); // 设置当前日期
        startCalender.add(Calendar.DATE, -5); // 日期加1
        Date startDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setStartTime(startDate);
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode())); // 当前状态为已删除
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);

        BaseOutput output = this.checkService.materialCouponCodeCheck(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(
                ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_NOT_IN_PERIOD
                        .getCode(),
                output.getCode());
        
        //测试if分支中的判断条件，增加brunch覆盖率
        endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE,1); // 日期加1
        endDate = endCalender.getTime(); // 结果
        mockMaterialCoupon.setEndTime(endDate);
        startCalender = Calendar.getInstance();
        startCalender.setTime(new Date()); // 设置当前日期
        startCalender.add(Calendar.DATE, 1); // 日期加1
        startDate = endCalender.getTime(); // 结果
        mockMaterialCoupon.setStartTime(startDate);
        
        output = this.checkService.materialCouponCodeCheck(
            paramMaterialCouponCode.getId(),
            paramMaterialCouponCode.getCode(),
            paramMaterialCouponCode.getUser());
        Assert.assertEquals(
            ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_NOT_IN_PERIOD
                    .getCode(),
            output.getCode());
        
    }
    
    /**
     * Test method for
     * {@link cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl#materialCouponCodeCheck(java.lang.Long, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMaterialCouponCodeCheckVerify() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, 1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");
        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(new Date()); // 设置当前日期
        startCalender.add(Calendar.DATE, -1); // 日期加1
        Date startDate = startCalender.getTime(); // 结果
        mockMaterialCoupon.setStartTime(startDate);
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);
        Mockito.when(materialCouponCodeDao.updateById(Matchers.any()))
        .thenReturn(1);

        //校验正确的情况
        BaseOutput output = this.checkService.materialCouponCodeVerify(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        
        
        //校验update失败的情况
        Mockito.when(materialCouponCodeDao.updateById(Matchers.any()))
        .thenReturn(0);
        BaseOutput outputVerifyFaild = this.checkService.materialCouponCodeVerify(
            paramMaterialCouponCode.getId(),
            paramMaterialCouponCode.getCode(),
            paramMaterialCouponCode.getUser());
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_VERIFY_FAILD.getCode(), outputVerifyFaild.getCode());
        
    }
    /**
     * Test method for
     * {@link cn.rongcapital.mkt.service.impl.MaterialCouponCodeCheckServiceImpl#materialCouponCodeCheck(java.lang.Long, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMaterialCouponCodeCheckVerifyCheckFaild() {

        MaterialCoupon mockMaterialCoupon = null;
        MaterialCouponCode mockMaterialCouponCode = null;
        mockMaterialCoupon = new MaterialCoupon();
        mockMaterialCoupon.setId(1L);
        mockMaterialCoupon.setAmount(new BigDecimal(8));
        mockMaterialCoupon.setChannelCode("sms");
        mockMaterialCoupon.setCouponStatus("used");
        mockMaterialCoupon.setCreateTime(new Date());

        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(new Date()); // 设置当前日期
        endCalender.add(Calendar.DATE, -1); // 日期加1
        Date endDate = endCalender.getTime(); // 结果

        mockMaterialCoupon.setEndTime(endDate);
        mockMaterialCoupon.setSourceCode("common");
        mockMaterialCoupon.setRule("");
        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(new Date()); // 设置当前日期
        startCalender.add(Calendar.DATE, -2); // 日期加1
        Date startDate = startCalender.getTime(); // 结果
        mockMaterialCoupon.setStartTime(startDate);
        mockMaterialCoupon.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCoupon.setStockRest(10000);
        mockMaterialCoupon.setStockTotal(20000);
        mockMaterialCoupon.setTitle("贝贝熊短信引流优惠码活动");
        mockMaterialCoupon.setType("voucher");
        mockMaterialCoupon.setUpdateTime(new Date());

        mockMaterialCouponCode = new MaterialCouponCode();
        mockMaterialCouponCode.setId(1L);
        mockMaterialCouponCode.setCouponId(mockMaterialCoupon.getId());
        mockMaterialCouponCode.setCode("ABCDE");
        mockMaterialCouponCode.setUser("13888888888");
        mockMaterialCouponCode.setReleaseStatus(
                MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
        mockMaterialCouponCode.setVerifyStatus(
                MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        mockMaterialCouponCode.setStatus(
                NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        mockMaterialCouponCode.setCreateTime(new Date());

        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(mockMaterialCouponCode.getId());
        paramMaterialCouponCode.setCode(mockMaterialCouponCode.getCode());
        paramMaterialCouponCode.setUser(mockMaterialCouponCode.getUser());

        List<MaterialCouponCode> mockCodeResult = new ArrayList<>();
        mockCodeResult.add(mockMaterialCouponCode);
        List<MaterialCoupon> mockCouponResult = new ArrayList<>();
        mockCouponResult.add(mockMaterialCoupon);

        Mockito.when(materialCouponCodeDao.selectList(Matchers.any()))
                .thenReturn(mockCodeResult);

        Mockito.when(materialCouponDao.selectListByIdList(Matchers.any()))
                .thenReturn(mockCouponResult);
        Mockito.when(materialCouponCodeDao.updateById(Matchers.any()))
        .thenReturn(1);

        //校验正确的情况
        BaseOutput output = this.checkService.materialCouponCodeVerify(
                paramMaterialCouponCode.getId(),
                paramMaterialCouponCode.getCode(),
                paramMaterialCouponCode.getUser());
        Assert.assertNotEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
    }

    @Test
    public void testMaxCount(){
        System.out.println();
        //验证以字母形式，长度为5的最大长度
        CouponCodeMaxCountOut baseOutput = this.checkService.materialCouponCodeMaxCount(MaterialCouponCodeMaxTypeEnum.ALPHA.getCode(), 5);
        long maxCount = baseOutput.getItems().get(0).getMaxCount();
        long expectedCount= 26 * 26 * 26 * 26 * 26;
        if(expectedCount>=MAX_COUNT){
            expectedCount = MAX_COUNT;
        }
        Assert.assertEquals(expectedCount, maxCount);
        
        //验证以数字形式，长度为10的最大长度
        baseOutput = this.checkService.materialCouponCodeMaxCount(MaterialCouponCodeMaxTypeEnum.NUMBER.getCode(), 6);
        maxCount = baseOutput.getItems().get(0).getMaxCount();
        expectedCount = 10 * 10 * 10 * 10 * 10 * 10;
        if(expectedCount>=MAX_COUNT){
            expectedCount = MAX_COUNT;
        }
        Assert.assertEquals(expectedCount, maxCount);
        
        //验证是组合形式，长度为5的最大长度
        baseOutput = this.checkService.materialCouponCodeMaxCount(MaterialCouponCodeMaxTypeEnum.MIXED.getCode(), 5);
        maxCount = baseOutput.getItems().get(0).getMaxCount();
        expectedCount = 36 * 36 * 36 * 36 * 36;
        if(expectedCount>=MAX_COUNT){
            expectedCount = MAX_COUNT;
        }
        Assert.assertEquals(expectedCount, maxCount);
        
        //验证码组合的规则不正确
        baseOutput = this.checkService.materialCouponCodeMaxCount("ERROR", 5);
        maxCount = baseOutput.getItems().get(0).getMaxCount();
        expectedCount = 0;
        Assert.assertEquals(expectedCount, maxCount);
    }
}
