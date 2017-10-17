package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpClientUtil;
import cn.rongcapital.mkt.common.util.HttpUrl;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.job.vo.in.BASToken;
import cn.rongcapital.mkt.po.Tenement;
import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-7-19.
 */
@Service
public class RegisterBasTokenServiceImpl {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TenementDao tenementDao;

    @PostConstruct
    public void registerBasTokenAndBasId(){
        //0验证是否已经存在BasToken如果已经存在则方法返回
        Tenement tenement = validateBasSessionToken();
        if (tenement == null) return;

        //1构造获取BasToken的请求参数
        Map<String, String> httpParams = ConstructRegisterBasRequestParam(tenement);
        if (httpParams == null) return;

        //2发送POST请求,请求成功，将获取到的BasToken和BasId存储到数据库中,请求失败打印log日志
        registerBasByHttpRequest(tenement, httpParams);
    }

    //验证是否已经存在BasToken如果已经存在则方法返回
    private Tenement validateBasSessionToken() {
        Tenement tenement = new Tenement();
        tenement.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        tenement.setRegisterType(ApiConstant.REGISTER_BAS_TYPE);
        List<Tenement> tenements = tenementDao.selectList(tenement);
        if(tenements != null && !CollectionUtils.isEmpty(tenements)){
            tenement = tenements.get(0);
            String sessionToken = tenement.getSessionToken();
            if(sessionToken != null && !sessionToken.isEmpty()){
                logger.info("BasToken 已经注册完毕");
                return null;
            }
        }else{
            logger.info("注册BaseToken所需相关信息获取失败，请检查数据库配置。");
            return null;
        }
        return tenement;
    }

    //构造获取BasToken的请求参数
    private Map<String, String> ConstructRegisterBasRequestParam(Tenement tenement) {
        String companyName = tenement.getName();
        String companySName = tenement.getShortname();
        if(companyName == null || companyName.isEmpty() || companySName == null || companySName.isEmpty()){
            logger.info("Bas注册参数填写不全，请在数据库中补全注册参数");
            return null;
        }

        Map<String,String> httpParams= new HashMap<>();
        httpParams.put("companyName",companyName);
        httpParams.put("companySName",companySName);
        return httpParams;
    }

    //发送POST请求,请求成功，将获取到的BasToken和BasId存储到数据库中,请求失败打印log日志
    private void registerBasByHttpRequest(Tenement tenement, Map<String, String> httpParams) {
        HttpUrl httpUrl = new HttpUrl();
        httpUrl.setHost(ApiConstant.BAS_HOST);
        httpUrl.setPath(ApiConstant.BAS_URL);
        httpUrl.setRequetsBody(JSON.toJSONString(httpParams));
        httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
        try {
            PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
            String postResStr = postResult.getResponseBodyAsString();
            BASToken basToken = JSON.parseObject(postResStr,BASToken.class);
            if(basToken != null && basToken.getSuccess()){
                if(basToken.getDataObject() != null){
                    tenement.setSessionToken(basToken.getDataObject().getSessionToken());
                    tenement.setBasId(basToken.getDataObject().getId());
                    tenementDao.updateById(tenement);
                }
            }else{
                logger.info("Bas注册Token获取失败，请检查");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}
