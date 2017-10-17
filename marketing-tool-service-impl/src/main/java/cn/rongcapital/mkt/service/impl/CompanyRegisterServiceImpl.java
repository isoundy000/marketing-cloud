package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.job.vo.in.H5MktPersonalListResponse;
import cn.rongcapital.mkt.service.CompanyRegisterService;
import cn.rongcapital.mkt.vo.in.H5CompanyRegResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-21.
 */
@Service
public class CompanyRegisterServiceImpl implements CompanyRegisterService{

    @Autowired
    private TenementDao tenementDao;

    @Override
    public void registerCompany() {
        Map<String,String> h5ParamMap = tenementDao.selectCompanyRegisterParam();
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,"ruixue.hfive.mkt.company.reg");
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null) {
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_company_reg_response");
                H5CompanyRegResponse h5CompanyRegResponse = JSON.parseObject(obj.toString(), H5CompanyRegResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
