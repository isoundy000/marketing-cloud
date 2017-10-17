package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatPersonalUuidDao;
import cn.rongcapital.mkt.job.service.impl.IsPubIdGrantedService;
import cn.rongcapital.mkt.job.service.impl.IsUuidEffectiveService;
import cn.rongcapital.mkt.service.ReauthWechatAccountService;
import cn.rongcapital.mkt.service.WechatPersonalAuthService;
import cn.rongcapital.mkt.service.WechatPublicAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ReauthWechatAccountIn;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-23.
 */
@Service
public class ReauthWechatAccountServiceImpl implements ReauthWechatAccountService{

    @Autowired
    private WechatPublicAuthService wechatPublicAuthService;
    @Autowired
    private WechatPersonalAuthService wechatPersonalAuthService;
    @Autowired
    private WechatAssetDao wechatAssetDao;
    @Autowired
    private WechatPersonalUuidDao wechatPersonalUuidDao;
    @Autowired
    private IsUuidEffectiveService isUuidEffectiveService;
    @Autowired
    private IsPubIdGrantedService isPubIdGrantedService;
    @Autowired
    private TenementDao tenementDao;

    @Override
    public Map<String,Object> reauthWechatAccount(String assetId) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        Boolean isAlreadyLogin = false;
        String redirectUrl = null;
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("asset_id",assetId);
        Map<String,Object> resultMap = wechatAssetDao.selectAssetTypeAndWxacct(paramMap);
        if(resultMap != null){
            if((Integer)resultMap.get("asset_type") == 1){
                Map<String,Object> uinMap = new HashMap<String,Object>();
                uinMap.put("uin",resultMap.get("wx_acct"));
                Map<String,Object> uuidMap = wechatPersonalUuidDao.selectUuidByUin(uinMap);
                if(uuidMap != null){
                    isAlreadyLogin = isUuidEffectiveService.isUuidEffective((String) uuidMap.get("uuid"));
                }
            }else{
                String pubId = (String) resultMap.get("wx_acct");
                isAlreadyLogin = isPubIdGrantedService.isPubIdGranted(pubId);
                if(!isAlreadyLogin){
                    Map<String,String> h5ParamRequestMap = tenementDao.selectPidAndShortname();
                    h5ParamRequestMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PUB_GRANT_API);
                    h5ParamRequestMap.put(ApiConstant.DL_API_PARAM_PUB_GRANT_CALLBACK_KEY,ApiConstant.DL_API_PARAM_PUB_GRANT_CALLBACK_VALUE);
                    h5ParamRequestMap.put("user_token","6200819d9366af1383023a19907ZZf9048e4c14fd56333b263685215");
                    h5ParamRequestMap.put("ver","1.2.1");
                    HttpResponse response = HttpUtils.requestH5Interface(h5ParamRequestMap);
                    if(response != null){
                        try {
                            String result = EntityUtils.toString(response.getEntity());
                            JSONObject obj = new JSONObject(result);
                            redirectUrl = (String) ((JSONObject)obj.get("hfive_mkt_pub_grant_response")).get("url");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap.put("flag",isAlreadyLogin);
        responseMap.put("url",redirectUrl);
        return responseMap;
    }
}
