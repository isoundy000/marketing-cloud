/*************************************************
 * @功能简述: MaterialCouponStatusUpdateService实现类
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
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponStatusUpdateVO;

@Service
public class MaterialCouponStatusUpdateServiceImpl implements MaterialCouponStatusUpdateService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponDao materialCouponDao;

    /**
     * 回写优惠券状态
     * 
     * @param vo
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateMaterialCouponStatus(MaterialCouponStatusUpdateVO vo) {
        if (vo.getId() == null) {
            throw new IllegalArgumentException("param id can not be null");
        }
        MaterialCoupon couple4Search = new MaterialCoupon();
        couple4Search.setId(vo.getId());
        couple4Search.setStatus((byte) 0);
        List<MaterialCoupon> poList = materialCouponDao.selectList(couple4Search);
        if (poList.size() == 0) {
            throw new RuntimeException(String.format("coupon id=[%s] is not exist.", vo.getId()));
        }
        MaterialCoupon po = poList.get(0);
        if (po.getEndTime() != null && po.getEndTime().compareTo(new Date()) < 0) {
            throw new RuntimeException(String.format("coupon id=[%s] is expired.", vo.getId()));
        }
        MaterialCoupon couple4Update = new MaterialCoupon();
        couple4Update.setId(vo.getId());
        couple4Update.setTaskId(vo.getTaskId());
        couple4Update.setTaskName(StringUtils.isBlank(vo.getTaskName()) ? null : vo.getTaskName().trim());
        couple4Update.setCouponStatus(StringUtils.isBlank(vo.getStatus()) ? null : vo.getStatus().trim());
        materialCouponDao.updateByIdAndStatus(couple4Update);
        logger.debug("coupon id={} updated. param is [{}].", vo.getId(), vo);
    }

}
