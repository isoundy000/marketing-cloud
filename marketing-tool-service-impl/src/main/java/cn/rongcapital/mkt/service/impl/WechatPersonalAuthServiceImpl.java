package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskManager;
import cn.rongcapital.mkt.job.service.impl.GetPersonContactsList;
import cn.rongcapital.mkt.job.vo.in.H5PersonalContactlistResponse;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.service.WechatPersonalAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatPersonalAuthIn;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-22.
 */
@Service
public class WechatPersonalAuthServiceImpl implements WechatPersonalAuthService {

    private static final Integer PERSONAL_WECHAT_ASSET_TYPE = 1;

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private WechatPersonalUuidDao wechatPersonalUuidDao;
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private WechatGroupDao wechatGroupDao;
    @Autowired
    private WechatAssetDao wechatAssetDao;
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;
    @Autowired
    private WechatRegisterDao wechatRegisterDao;

    @Override
    public BaseOutput authPersonWechat(WechatPersonalAuthIn wechatPersonalAuthIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String uuid = wechatPersonalAuthIn.getUuid();
        if(uuid != null){
            paramMap.put("uuid",wechatPersonalAuthIn.getUuid());
            String uin = wechatPersonalAuthIn.getUin();
            if(uin != null){
                paramMap.put("uin",uin);
                wechatPersonalUuidDao.insertUuidAndUin(paramMap);
                registerWechatPersonInDatabase(uin);
                deleteRecordAccordUin(uin);
                taskManager.manualInitTask(1098,null);
                baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
                baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            }
        }
        return baseOutput;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void deleteRecordAccordUin(String uin) {
        //Todo:1.删除wechat_member相关数据
        wechatMemberDao.deleteRecordByUin(uin);
        //Todo:2.删除wechat_group相关数据
        wechatGroupDao.deleteRecordByUin(uin);
        //Todo:3.删除wechat_asset相关数据
        wechatAssetDao.deleteRecordByUin(uin);
        //Todo:4.删除wechat_asset_group相关数据
        wechatAssetGroupDao.deleteRecordByUin(uin);
    }

    private void registerWechatPersonInDatabase(String uin) {
        WechatRegister wechatRegister = new WechatRegister();
        wechatRegister.setWxAcct(uin);
        if(isAssetExistEver(wechatRegister)){
            wechatRegister.setCreateTime(new Date(System.currentTimeMillis()));
            wechatRegisterDao.updateConsignationTimeByWxacct(wechatRegister);
            return;
        }
        wechatRegister.setType(PERSONAL_WECHAT_ASSET_TYPE);
        wechatRegisterDao.insert(wechatRegister);
    }

    private boolean isAssetExistEver(WechatRegister wechatRegister) {
        Integer count = wechatRegisterDao.selectListCount(wechatRegister);
        if(count > 0) return true;
        return false;
    }
}
