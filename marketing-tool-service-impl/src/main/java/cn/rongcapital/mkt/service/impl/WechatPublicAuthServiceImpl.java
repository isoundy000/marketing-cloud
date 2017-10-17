package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.filter.ApiRequestRouter;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.service.WechatPublicAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.PublicAuthOut;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-16.
 */
@Service
public class WechatPublicAuthServiceImpl implements WechatPublicAuthService{

    @Autowired
    private TenementDao tenementDao;
    @Override
    public BaseOutput authWechatPublicAccount() {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        PublicAuthOut publicAuthOut = new PublicAuthOut();
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
                publicAuthOut.setUrl((String) ((JSONObject)obj.get("hfive_mkt_pub_grant_response")).get("url"));
                baseOutput.getData().add(publicAuthOut);
                baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
                baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return baseOutput;
    }
}
