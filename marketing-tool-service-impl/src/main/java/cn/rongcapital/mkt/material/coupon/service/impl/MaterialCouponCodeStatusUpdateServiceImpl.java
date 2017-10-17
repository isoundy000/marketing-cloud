/*************************************************
 * @功能简述: MaterialCouponCodeStatusUpdateService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;

@Service
public class MaterialCouponCodeStatusUpdateServiceImpl implements MaterialCouponCodeStatusUpdateService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 批量处理最大数量
     */
    public static final int BATCH_PROCESS_CNT = 1000;

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Autowired
    private MaterialCouponDao materialCouponDao;

    /**
     * 批量回写优惠码状态
     * 
     * @param voList
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    @Override
    public void updateMaterialCouponCodeStatus(List<MaterialCouponCodeStatusUpdateVO> voList) {
        // 数据校验
        checkParam(voList);
        // 数据拆分
        List<List<MaterialCouponCodeStatusUpdateVO>> batchList = splitList(voList);
        AtomicInteger processPart = new AtomicInteger(0);
        AtomicInteger processCnt = new AtomicInteger(0);
        // 数据处理
        if (CollectionUtils.isNotEmpty(batchList)) {
            // 根据优惠码获取优惠券
            final Long couponid = getCouponIdByCodeId(voList.get(0).getId());
            if (couponid == null) {
                logger.error("coupon data not exist(couponcode = {}).", voList.get(0).getId());
                throw new RuntimeException("coupon data not exist.");
            }
            // 处理优惠码
//            batchList.parallelStream().forEach(
            batchList.forEach(
                    batch -> {
                        processMaterialCouponCode(batch);
                        processCnt.addAndGet(batch.size());
                        logger.info(">>>>>>>coupon:{} part [{}/{}]({})processed >>>>>>>", couponid,
                                processPart.incrementAndGet(), batchList.size(), batch.size());
                    });
            // 回写优惠码剩余量
            materialCouponDao.updateCouponStockRest(couponid, processCnt.get());
        }
    }

    /**
     * 数据校验
     * 
     * @param voList
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    private void checkParam(List<MaterialCouponCodeStatusUpdateVO> voList) {
        List<MaterialCouponCodeStatusUpdateVO> illegalData = null;
        if (CollectionUtils.isNotEmpty(voList)) {
            illegalData =
                    voList.stream()
                            .filter(vo -> {
                                return (vo.getId() == null || StringUtils.isBlank(vo.getUser()) || StringUtils
                                        .isBlank(vo.getStatus()));
                            }).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(illegalData)) {
            logger.error("param {} is illegal.", illegalData);
            throw new IllegalArgumentException("param is illegal");
        }
    }

    /**
     * 根据优惠码ID获取优惠券ID
     * 
     * @param couponCodeId
     * @return Long
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    private Long getCouponIdByCodeId(Long couponCodeId) {
        Long couponId = null;
        if (couponCodeId != null) {
            MaterialCouponCode code = new MaterialCouponCode();
            code.setId(couponCodeId);
            List<MaterialCouponCode> resultList = materialCouponCodeDao.selectList(code);
            if (CollectionUtils.isNotEmpty(resultList)) {
                couponId = resultList.get(0).getCouponId();
            }
        }
        return couponId;
    }


    /**
     * 数据持久化处理
     * 
     * @param voList
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    private void processMaterialCouponCode(List<MaterialCouponCodeStatusUpdateVO> voList) {
        // 数据更新
        if (CollectionUtils.isNotEmpty(voList)) {
            List<MaterialCouponCode> poList = new ArrayList<MaterialCouponCode>();
            MaterialCouponCode po = null;
            for (MaterialCouponCodeStatusUpdateVO vo : voList) {
                po = new MaterialCouponCode();
                po.setId(vo.getId());
                po.setUser(vo.getUser());
                po.setReleaseStatus(vo.getStatus());
                poList.add(po);
            }
            materialCouponCodeDao.batchUpdateByIdAndStatus(poList);
        }
    }

    /**
     * 数据拆分
     * 
     * @param voList
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    private List<List<MaterialCouponCodeStatusUpdateVO>> splitList(List<MaterialCouponCodeStatusUpdateVO> voList) {
        List<List<MaterialCouponCodeStatusUpdateVO>> list = null;
        if (CollectionUtils.isNotEmpty(voList)) {
            list = new ArrayList<List<MaterialCouponCodeStatusUpdateVO>>();
            int listSize = voList.size();
            int pageSize = (int) Math.ceil((float) listSize / BATCH_PROCESS_CNT);
            for (int i = 0; i < pageSize; i++) {
                int begin = (i) * BATCH_PROCESS_CNT;
                int end = (i + 1) * BATCH_PROCESS_CNT;
                end = end > listSize ? listSize : end;
                list.add(voList.subList(begin, end));
            }
        }
        return list;
    }
}
