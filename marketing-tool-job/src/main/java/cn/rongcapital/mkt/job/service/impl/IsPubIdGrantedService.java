package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.job.vo.in.H5GrantStatusResponse;
import cn.rongcapital.mkt.job.vo.in.H5PersonalIsonline;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-23.
 */
@Service
public class IsPubIdGrantedService {

    @Autowired
    private TenementDao tenementDao;

    public boolean isPubIdGranted(String pubId){
        Map<String,String> h5VerifyUuidParamMap = tenementDao.selectPid();
        h5VerifyUuidParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_IS_PUBLIC_GRANTED);
        h5VerifyUuidParamMap.put("pub_id", pubId);
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5VerifyUuidParamMap);
        if(httpResponse != null){
            JSONObject obj = null;
            try {
                obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_grant_status_response");
                if(obj != null){
                    H5GrantStatusResponse h5GrantStatusResponse = JSON.parseObject(obj.toString(),H5GrantStatusResponse.class);
                    if(h5GrantStatusResponse != null){
                        return h5GrantStatusResponse.getStatus();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
