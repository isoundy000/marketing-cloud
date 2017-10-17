/*************************************************
 * @功能及特点的描述简述: MaterialCouponCodeCheckService 接口实现类 该类被编译测试过
 * @see （与该类关联的类）：cn.rongcapital.mkt.service.MaterialCouponCodeCheckService
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6 
 * @date(创建、开发日期)：2016-12月-09 
 * @date(最后修改日期)：2016-12月-09 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
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
import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeMaxCountOutItem;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class MaterialCouponCodeCheckServiceImpl
        implements
            MaterialCouponCodeCheckService {

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Autowired
    private MaterialCouponDao materialCouponDao;

    
    private static final long MAX_COUNT = 1000000;
    
    private static final char[] LETTERS = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final char[] NUMBERS = {0,1,2,3,4,5,6,7,8,9};
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.rongcapital.mkt.service.MaterialCouponCodeCheckService#materialCouponCodeCheck(java.lang.
     * Long, java.lang.String, java.lang.String)
     */
    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput materialCouponCodeCheck(Long id, String couponCode,
            String user) {
        
        MaterialCouponCode paramMaterialCouponCode = new MaterialCouponCode();
        paramMaterialCouponCode.setId(id);
        paramMaterialCouponCode.setCode(couponCode);
        paramMaterialCouponCode.setUser(user);

        return checkCode(paramMaterialCouponCode);
    }

    /**
     * @功能描述: message
     * @param paramMaterialCouponCode
     * @return BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月12日
     */
    private BaseOutput checkCode(MaterialCouponCode paramMaterialCouponCode) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        // 验证优惠码是否有效，有效的条件为：
        // 1.此优惠码status状态值为0
        // 2.优惠码发放状态为收到
        // 3.优惠券核销状态为未使用
        // 4.优惠码在有效期范围内
        List<MaterialCouponCode> resultList =
                materialCouponCodeDao.selectList(paramMaterialCouponCode);
        // 能够查询到相应的优惠码信息，方可进行一下验证
        if (resultList != null && resultList.size() > 0) {
            MaterialCouponCode item = resultList.get(0);
            // 判断当前优惠码状态
            if (NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()) != item
                    .getStatus().byteValue()) {
                return new BaseOutput(
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_DELETE
                                .getCode(),
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_DELETE
                                .getMsg(),
                        ApiConstant.INT_ZERO, null);
            }
            // 如果当前的发放状态未处于received(收到)状态，则提示，此用户未收到此优惠码
            if (!MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode()
                    .equals(item.getReleaseStatus())) {
                return new BaseOutput(
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_RELEASE
                                .getCode(),
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_RELEASE
                                .getMsg(),
                        ApiConstant.INT_ZERO, null);
            }
            // 如果优惠码的核销状态不是unverify(未核销)，则提示：当前优惠码已使用过
            if (!MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode()
                    .equals(item.getVerifyStatus())) {
                return new BaseOutput(
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_VERIFY
                                .getCode(),
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_VERIFY
                                .getMsg(),
                        ApiConstant.INT_ZERO, null);
            }

            // 验证优惠码的有效期
            List<Integer> idList = new ArrayList<>();
            idList.add(item.getCouponId().intValue());
            List<MaterialCoupon> couponList =
                    this.materialCouponDao.selectListByIdList(idList);
            if (couponList != null && couponList.size() > 0) {
                MaterialCoupon coupon = couponList.get(0);
                Date currentDate = new Date();
                if (currentDate.before(coupon.getEndTime())
                        && currentDate.after(coupon.getStartTime())) {
                    // 返回验证成功
                    return result;
                } else {
                    return new BaseOutput(
                            ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_NOT_IN_PERIOD
                                    .getCode(),
                            ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_NOT_IN_PERIOD
                                    .getMsg(),
                            ApiConstant.INT_ZERO, null);
                }
            }
        }
        return new BaseOutput(
                ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(),
                ApiConstant.INT_ZERO, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.rongcapital.mkt.service.MaterialCouponCodeCheckService#materialCouponCodeVerify(java.lang.
     * Long, java.lang.String, java.lang.String)
     */
    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseOutput materialCouponCodeVerify(Long id, String couponCode,
            String user) {
        BaseOutput successResult = new BaseOutput(
                ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        MaterialCouponCode item = new MaterialCouponCode();
        item.setId(id);
        item.setCode(couponCode);
        item.setUser(user);

        
        BaseOutput validateResult = this.checkCode(item);
        
        if(successResult.getCode() == validateResult.getCode()){
            // 进行核销操作
            item.setVerifyStatus(
                    MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode());// 将核销状态置为已使用
            item.setVerifyTime(new Date());// 将核销时间设置为当前服务器时间
            int updateRow = this.materialCouponCodeDao.updateById(item);
            // 受影响的行数如果为1，则说明核销操作成功
            if (updateRow == 1) {
                return successResult;
            } else {
                return new BaseOutput(
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_VERIFY_FAILD
                                .getCode(),
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_VERIFY_FAILD
                                .getMsg(),
                        ApiConstant.INT_ZERO, null);
            }
        }else{
            return validateResult;
        }
    }
    
    /*
     * @see
     * cn.rongcapital.mkt.service.MaterialCouponCodeCheckService#materialCouponCodeMaxCount(java.
     * lang.String, int)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
    public CouponCodeMaxCountOut materialCouponCodeMaxCount(String typeCode, int length) {
        MaterialCouponCodeMaxTypeEnum type = MaterialCouponCodeMaxTypeEnum.getByCode(typeCode);
        long maxCount = 1L;
        if (MaterialCouponCodeMaxTypeEnum.contains(typeCode)) {
            switch (type) {
                case ALPHA:
                    maxCount = getMaxCount(length, LETTERS.length);
                    break;
                case NUMBER:
                    maxCount = getMaxCount(length, NUMBERS.length);
                    break;
                default:
                    maxCount = getMaxCount(length, NUMBERS.length + LETTERS.length);
                    break;
            }
        } else {
            maxCount = 0;
        }

        // 如果组合计算出来的长度大于一百万，则只显示一百万
        if (maxCount >= MAX_COUNT) {
            maxCount = MAX_COUNT;
        }
        //构建结果集
        CouponCodeMaxCountOut resultOut = new CouponCodeMaxCountOut(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE);
        resultOut.getItems().add(new CouponCodeMaxCountOutItem(maxCount));
        resultOut.setTotal(resultOut.getItems().size());
        resultOut.setTotalCount(resultOut.getItems().size());
        return resultOut;
    }

    private long getMaxCount(int typeLength, int length) {
        long result = 1L;
        for (int i = 0; i < typeLength; i++) {
            result = result * length;
            if(result> MAX_COUNT){
                return result;
            }
        }
        return result;
    }
}
