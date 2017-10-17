/*************************************************
 * @功能简述: MaterialCouponCodeVerifyListService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.SqlConvertUtils;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeVerifyListService;
import cn.rongcapital.mkt.material.coupon.vo.out.MaterialCouponCodeVerifyListOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponCodeVerifyListServiceImpl implements MaterialCouponCodeVerifyListService {

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput listMaterialCouponCodeVerfy(Long id, String blurSearch, String receiveStatus,
                    String verifyStatus, String expireStatus, Integer index, Integer size) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("user", SqlConvertUtils.escapeSQLCharacter(blurSearch));
        paramMap.put("releaseStatus", receiveStatus);
        paramMap.put("verifyStatus", verifyStatus);
        if (StringUtils.isNotBlank(expireStatus)) {
            paramMap.put("expireStatus", expireStatus);
            paramMap.put("expireTime", new Date());
        }
        int proIndex = (index == null || index.intValue() == 0) ? 1 : index;
        int proSize = (size == null || size.intValue() == 0) ? 10 : size;
        paramMap.put("index", (proIndex - 1) * proSize);
        paramMap.put("size", proSize);

        BaseOutput result =
                        new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                                        ApiConstant.INT_ZERO, null);
        int totalCnt = materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap);
        result.setTotalCount(totalCnt);
        if (totalCnt > 0) {
            List<MaterialCouponCodeVerifyListOut> dataList = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);
            if (CollectionUtils.isNotEmpty(dataList)) {
                result.setTotal(dataList.size());
                result.getData().addAll(dataList);
            }
        }
        return result;
    }

}
