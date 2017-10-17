package cn.rongcapital.mkt.job.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.tutils.okhttp.OkHttpUtil;
import com.tagsin.tutils.okhttp.OkHttpUtil.RequestMediaType;

import okhttp3.Response;

public class WorkThread implements Runnable{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String BIZ_TYPE_CODE = "biz_type_code";

	private static final String BIZ_CODE = "biz_code";

	private static final String DATAS = "datas";

	private static final String KEY_ID = "keyid";
	
	private Integer keyId;
	
	private Environment env;

	public WorkThread() {
	}

	public WorkThread(Integer keyId) {
		this.keyId = keyId;
	}

	public WorkThread(Integer keyId, Environment env) {
		super();
		this.keyId = keyId;
		this.env = env;
	}

	@Override
	public void run() {
		try {
			// 请求参数集合
			Map<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put(BIZ_TYPE_CODE, "maketing-system-tag");
			requestMap.put(BIZ_CODE, keyId);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put(KEY_ID, keyId);
			requestMap.put(DATAS, dataMap);
			String url = env.getProperty("rule.engine.url");
			Response response = OkHttpUtil.requestByPost(url, RequestMediaType.JSON, JsonUtils.toJson(requestMap));
			// 返回码
			int code = response.code();
			if(!(code >= 200 && code <= 206)){
				logger.info("请求规则引擎失败---------------->返回码："+code);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求规则引擎服务出现异常：------------------->"+e.getMessage());
		}
	}

}
