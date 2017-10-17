/*************************************************
 * @功能简述: 接口mkt.materiel.coupon.code.list的service接口实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: guozhenchao
 * @version: 0.0.1
 * @date: 2016/12/7
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CouponCodeListServiceImpl implements CouponCodeListService {

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Override
    public BaseOutput couponCodeList(Long id, Integer index, Integer size) {
        MaterialCouponCode code = new MaterialCouponCode();
        code.setCouponId(id);
        int proIndex = (index == null || index.intValue() == 0) ? 1 : index;
        int proSize = (size == null || size.intValue() == 0) ? 10 : size;
        code.setStartIndex((proIndex - 1) * proSize);
        code.setPageSize(proSize);
        int totle = materialCouponCodeDao.getTotleListCount(code);
        List<MaterialCouponCode> codeList = materialCouponCodeDao.selectList(code);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        for(MaterialCouponCode materialCouponCode : codeList){
            result.getData().add(materialCouponCode);
        }
        result.setTotal(result.getData().size());
        result.setTotalCount(totle);
        return result;
    }

    @Override
    public BaseOutput couponIssuedCodeList(Long id, Integer index, Integer size) {
        MaterialCouponCode code = new MaterialCouponCode();
        code.setCouponId(id);
        int proIndex = (index == null || index.intValue() == 0) ? 1 : index;
        int proSize = (size == null || size.intValue() == 0) ? 10 : size;
        code.setStartIndex((proIndex - 1) * proSize);
        code.setPageSize(proSize);
        int totle = materialCouponCodeDao.getTotleIssuedListCount(code);
        List<MaterialCouponCode> codeList = materialCouponCodeDao.selectIssuedList(code);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        for(MaterialCouponCode materialCouponCode : codeList){
            result.getData().add(materialCouponCode);
        }
        result.setTotal(result.getData().size());
        result.setTotalCount(totle);
        return result;
    }
}
