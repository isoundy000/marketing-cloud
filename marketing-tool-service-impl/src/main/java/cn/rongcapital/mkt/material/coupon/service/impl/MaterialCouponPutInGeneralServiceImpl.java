/*************************************************
 * @功能及特点的描述简述: 返回投放优惠券统计数量 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC
 * @author: liuhaizhan
 * @version: 版本
 * @date(创建、开发日期)：2016年12月7日 最后修改日期：2016年12月7日 @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponPutInGeneralService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CouponPutInGeneral;

@Service
public class MaterialCouponPutInGeneralServiceImpl implements MaterialCouponPutInGeneralService {

    /*
     * (non-Javadoc)
     * 
     * @see cn.rongcapital.mkt.service.MaterialCouponPutInGeneralService#PutInGeneral(long)
     */

    @Autowired
    private MaterialCouponDao materialCouponDao;



    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;


    /**
     * @author liuhaizhan
     * @功能简述 投放统计实体
     * @param：优惠券Id @return：
     */

    @Override
    public BaseOutput putInGeneral(long id) {

        CouponPutInGeneral cpi = new CouponPutInGeneral();
        BaseOutput outPut = new BaseOutput(0, "success", ApiConstant.INT_ONE, null);
        MaterialCoupon mc = materialCouponDao.selectOneCoupon(id);
        if (mc == null || !"0".equals(mc.getStatus().toString())) {
            outPut.setCode(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VERIFYDATE_ERROR.getCode());
            outPut.setMsg(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_VERIFYDATE_ERROR.getMsg());
            outPut.setTotal(ApiConstant.INT_ZERO);
        } else {
            cpi.setRestCount(mc.getStockRest());
            double amout = mc.getAmount().doubleValue();
            List<Map> putIncnt = materialCouponCodeDao.getCouponPutInCount(id);// 统计优惠券投放成功，失败数量
            List<Map> couponVerifyCnt = materialCouponCodeDao.getCouponVerifyCount(id);// 统计优惠券已核销,未核销金额
            if (CollectionUtils.isNotEmpty(putIncnt)) {
                for (Map map : putIncnt) {
                    doPutIndata(cpi, map, amout);
                }
            }
            if (CollectionUtils.isNotEmpty(couponVerifyCnt)) {
                for (Map map : couponVerifyCnt) {
                    doPutIndata(cpi, map, amout);
                }
            }
            List outCpI = new ArrayList();
            outCpI.add(cpi);
            outPut.setData(outCpI);
        }


        return outPut;
    }


    /**
     * @author liuhaizhan
     * @功能简述 返回投放统计实体
     * @param：param1 message1
     * @param：param2 message2
     * @return：message2
     */
    private void doPutIndata(CouponPutInGeneral cpi, Map map, double account) {
        if (MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode().equals(map.get("status").toString())) {
            cpi.setReleasesuccessCount(Integer.valueOf(map.get("cnt").toString()));
        } else if (MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode().equals(map.get("status").toString())) {
            cpi.setReleaseFailCount(Integer.valueOf(map.get("cnt").toString()));
        } else if (MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode().equals(map.get("status"))) {
            cpi.setUnverifyAmount(Integer.valueOf(map.get("cnt").toString()) * account);
        } else {
            cpi.setVerifyAmount(Integer.valueOf(map.get("cnt").toString()) * account);
        }

    }

}
