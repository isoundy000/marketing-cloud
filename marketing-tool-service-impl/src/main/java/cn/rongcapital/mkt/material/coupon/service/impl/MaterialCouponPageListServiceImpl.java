/*************************************************
 * @功能及特点的描述简述: MaterialCouponPageListService 实现类 该类被编译测试过
 * @see （与该类关联的类）：cn.rongcapital.mkt.service.MaterialCouponPageListService
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6 @date(创建、开发日期)：2016-12月-07 最后修改日期：2016-12月-07 @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.Date;
import java.util.List;

import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.SqlConvertUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponChannelCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponPageListService;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponListItemOut;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponListOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;
@Service
public class MaterialCouponPageListServiceImpl implements MaterialCouponPageListService {

    @Autowired
    private MaterialCouponDao meterialCouponDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.rongcapital.mkt.service.MaterialCouponPageListService#getMaterialCouponListByKeyword(cn.
     * rongcapital.mkt.common.enums.MaterialCouponChannelCodeEnum,
     * cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum, java.lang.String,
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public MaterialCouponListOut getMaterialCouponListByKeyword(String channelCode,
                                                                String couponStatus, String keyword, Integer index, Integer size, boolean filterOverdue) {
        MaterialCouponListOut baseOutput = new MaterialCouponListOut(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO);

        MaterialCoupon paramMaterialCoupon = new MaterialCoupon();
        
        if(StringUtils.isEmpty(channelCode) || !MaterialCouponChannelCodeEnum.contains(channelCode)){
            return new MaterialCouponListOut(ApiErrorCode.PARAMETER_ERROR.getCode(), ApiErrorCode.VALIDATE_ERROR.getMsg(),
                ApiConstant.INT_ZERO); 
        }
        if(!StringUtils.isEmpty(couponStatus) && !MaterialCouponStatusEnum.contains(couponStatus)){
            return new MaterialCouponListOut(ApiErrorCode.PARAMETER_ERROR.getCode(), ApiErrorCode.VALIDATE_ERROR.getMsg(),
                ApiConstant.INT_ZERO);
        }
        
        paramMaterialCoupon.setChannelCode(channelCode);
        String paramCouponoStatus = StringUtils.isEmpty(couponStatus) ? null: couponStatus;
        paramMaterialCoupon.setCouponStatus(paramCouponoStatus);
        if(filterOverdue ){
            paramMaterialCoupon.setEndTime(new Date());
        }else{
            paramMaterialCoupon.setEndTime(null);
        }
        paramMaterialCoupon.setTitle(SqlConvertUtils.escapeSQLCharacter(keyword));
        paramMaterialCoupon.setStartIndex((index - 1) * size);
        paramMaterialCoupon.setPageSize(size);

        List<MaterialCoupon> meterialCouponList = meterialCouponDao.selectListByKeyword(paramMaterialCoupon);
        int totalCount = meterialCouponDao.selectListByKeywordCount(paramMaterialCoupon);
        // 如果查询的列表数据未空，则直接返回空列表给前段
        if (CollectionUtils.isEmpty(meterialCouponList)) {
            return baseOutput;
        }
        MaterialCouponListItemOut materialCouponListOut = null;
        //遍历查询的结果集
        for (MaterialCoupon item : meterialCouponList) {
            materialCouponListOut = new MaterialCouponListItemOut();
            //按照相同字段进行复制
            BeanUtils.copyProperties(item,materialCouponListOut);
            String createTimeStamp = String.valueOf( item.getCreateTime().getTime()/1000);
            materialCouponListOut.setCreateTime(createTimeStamp);
            //保存在结果对象中
            baseOutput.getListItems().add(materialCouponListOut);
        }
         baseOutput.setTotal(baseOutput.getListItems().size());
         baseOutput.setTotalCount(totalCount);
        return baseOutput;
    }

}
