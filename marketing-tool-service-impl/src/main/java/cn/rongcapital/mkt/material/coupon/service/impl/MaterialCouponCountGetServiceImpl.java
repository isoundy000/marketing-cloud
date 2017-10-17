/*************************************************
 * @功能简述: MaterialCouponCountGetService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/6
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.util.SqlConvertUtils;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponCountGetServiceImpl implements MaterialCouponCountGetService {

    @Autowired
    private MaterialCouponDao materialCouponDao;

    /**
     * 获取指定条件的优惠券的数量
     * 
     * @param chanelCode
     * @param keyword
     * @return BaseOutput
     * @author zhuxuelong
     * @Date 2016-12-06
     */
    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput getMaterialCouponCount(String chanelCode, String keyword) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("chanelCode", chanelCode);
        paramMap.put("title", SqlConvertUtils.escapeSQLCharacter(keyword));

        // 未投放数量
        paramMap.put("couponStatus", MaterialCouponStatusEnum.UNUSED.getCode());
        long unreleaseCount = materialCouponDao.getMaterialCouponCount(paramMap);

        // 已占用数量
        paramMap.put("couponStatus", MaterialCouponStatusEnum.USED.getCode());
        long occupyCount = materialCouponDao.getMaterialCouponCount(paramMap);

        // 投放中数量
        paramMap.put("couponStatus", MaterialCouponStatusEnum.RELEASING.getCode());
        long releasingCount = materialCouponDao.getMaterialCouponCount(paramMap);

        // 已投放数量
        paramMap.put("couponStatus", MaterialCouponStatusEnum.RELEASED.getCode());
        long releasedCount = materialCouponDao.getMaterialCouponCount(paramMap);

        // 全部数量
        long total = unreleaseCount + occupyCount + releasingCount + releasedCount;
        // 返回结果集合
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("unrelease_count", unreleaseCount);
        resultMap.put("occupy_count", occupyCount);
        resultMap.put("releasing_count", releasingCount);
        resultMap.put("released_count", releasedCount);
        resultMap.put("total", total);
        result.getData().add(resultMap);
        result.setTotal(1);
        result.setTotalCount(1);
        return result;
    }

}
