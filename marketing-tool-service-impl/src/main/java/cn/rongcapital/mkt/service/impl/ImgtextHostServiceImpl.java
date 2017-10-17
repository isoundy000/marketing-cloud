package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.service.ImgtextHostService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgtextHostIn;
import cn.rongcapital.mkt.vo.in.H5MktWtuwenConvertResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class ImgtextHostServiceImpl implements ImgtextHostService {

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private ImgTextAssetDao imgTextAssetDao;

    @Override
    public Object hostImgtextAsset(ImgtextHostIn imgtextHostIn, SecurityContext securityContext) {
        BaseOutput ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        Map<String, String> h5RequestParamMap = tenementDao.selectPid();
        h5RequestParamMap.put("request_url", imgtextHostIn.getAssetUrl());
        h5RequestParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_WUWEN_HOST);
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5RequestParamMap);
        if(httpResponse != null){
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_convert_response");
                if(obj != null){
                    H5MktWtuwenConvertResponse h5MktWtuwenConvertResponse = JSON.parseObject(obj.toString(),  H5MktWtuwenConvertResponse.class);
                    if(h5MktWtuwenConvertResponse != null){
                        Map<String,Object> paramMap = new HashMap<String,Object>();
                        paramMap.put("name",h5MktWtuwenConvertResponse.getTitle());
                        paramMap.put("pc_preview_url",h5MktWtuwenConvertResponse.getPcUrl());
                        paramMap.put("mobile_url",h5MktWtuwenConvertResponse.getMobileUrl());
                        paramMap.put("imgfile_url",h5MktWtuwenConvertResponse.getScreenshopUrl());
                        paramMap.put("create_time",h5MktWtuwenConvertResponse.getCreateTime());
                        paramMap.put("material_id",h5MktWtuwenConvertResponse.getMaterialId());
                        paramMap.put("type",1);
                        switch (h5MktWtuwenConvertResponse.getChannelType()){
                            case 2:
                                paramMap.put("owner_name","易企秀");
                                break;
                            case 3:
                                paramMap.put("owner_name","兔展");
                                break;
                            case 4:
                                paramMap.put("owner_name","MAKA");
                                break;
                        }
                        imgTextAssetDao.insertHostImg(paramMap);
                        ur.setCode(ApiErrorCode.SUCCESS.getCode());
                        ur.setMsg(ApiErrorCode.SUCCESS.getMsg());
                    }else{
                        ur.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                        ur.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
                    }
                }else{
                    ur.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                    ur.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Response.ok().entity(ur).build();
    }
}
