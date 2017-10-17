package cn.rongcapital.mkt.common.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-16.
 */
public class HttpUtils {

    public static final String baseUrl = "http://test.h5plus.net/auth-template/api/?";

    public static HttpResponse requestH5Interface(Map<String, String> paramMap){
        HttpClient httpClient = new DefaultHttpClient();
        String url = baseUrl;
        for(String key : paramMap.keySet()){
            url = url + key + "=" + paramMap.get(key) + "&";
        }
        url = url.replace(" ","");
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
