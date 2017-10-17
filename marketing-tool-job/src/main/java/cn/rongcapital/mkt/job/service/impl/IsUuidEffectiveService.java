package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatPersonalUuidDao;
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
 * Created by Yunfeng on 2016-6-22.
 */
@Service
public class IsUuidEffectiveService {

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private WechatPersonalUuidDao wechatPersonalUuidDao;

    public boolean isUuidEffective(String uuid){
        Map<String,String> h5VerifyUuidParamMap = new HashMap<String,String>();
        h5VerifyUuidParamMap.put("pid",tenementDao.selectPid().get("pid"));
        h5VerifyUuidParamMap.put("uuid",uuid);
        h5VerifyUuidParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PERSONAL_ISONLINE);
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5VerifyUuidParamMap);
        if(httpResponse != null){
            JSONObject obj = null;
            try {
                String entityString = EntityUtils.toString(httpResponse.getEntity());
                obj = JSON.parseObject(entityString).getJSONObject("hfive_mkt_personal_isonline_response");
                if(obj != null){
                    H5PersonalIsonline h5PersonalIsonline = JSON.parseObject(obj.toString(),H5PersonalIsonline.class);
                    if(h5PersonalIsonline != null){
                        if(h5PersonalIsonline.getStatus() == 0){
                            return true;
                        }else{
                            Map<String,Object> paramMap = new HashMap<String,Object>();
                            paramMap.put("uuid",uuid);
                            wechatPersonalUuidDao.updateStatus(paramMap);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
