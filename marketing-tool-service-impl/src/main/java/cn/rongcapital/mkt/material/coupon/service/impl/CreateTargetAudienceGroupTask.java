/*************************************************
 * @功能简述: 新建固定人群任务类
 * @项目名称: marketing cloud
 * @see:
 * @author: 单璟琦
 * @version: 0.0.1
 * @date: 2016/12/16
 * @复审人:
 * 
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCreateAudienceVO;
import cn.rongcapital.mkt.service.AudienceListService;
import com.alibaba.fastjson.JSONObject;

@Service
public class CreateTargetAudienceGroupTask implements TaskService {

    @Autowired
    MaterialCouponDao materialCouponDao;

    @Autowired
    MaterialCouponCodeDao materialCouponCodeDao;

    @Autowired
    AudienceListService audienceListService;

    @Override
    public void task(Integer taskId) {

    }

    @Override
    public void task(String pram) {

        MaterialCouponCreateAudienceVO parmVO = JSONObject.parseObject(pram,MaterialCouponCreateAudienceVO.class);
        
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id", parmVO.getId());
        paramMap.put("user", parmVO.getBlurSearch());
        paramMap.put("releaseStatus", parmVO.getReleaseStatus());
        paramMap.put("verifyStatus", parmVO.getVerifyStatus());
        if (StringUtils.isNotBlank(parmVO.getExpireStatus())) {
            paramMap.put("expireStatus", parmVO.getExpireStatus());
            paramMap.put("expireTime", new Date());
        }
        
        List<String> mobileList = materialCouponCodeDao.getCouponCodeVerifyUserInfoList(paramMap);

        List<Integer> idList = new ArrayList<>();
        idList.add(Integer.valueOf(String.valueOf(parmVO.getId())));
        List<MaterialCoupon> mc = materialCouponDao.selectListByIdList(idList);
        Long taskHeadId =
                (!CollectionUtils.isEmpty(mc) && mc.get(0).getTaskId() != null) ? mc.get(0).getTaskId() : null;
        if (taskHeadId != null) {
            // 目前只针对短信渠道 2016-12-14
            boolean flag = audienceListService.saveAudienceByMobile(taskHeadId, mobileList, parmVO.getName());
            if (flag) {
                // 成功
            } else {
                // 新建固定人群失败
            }
        } else {
             //优惠券没有关联任务ID
        }

    }

}
