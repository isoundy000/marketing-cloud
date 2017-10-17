package cn.rongcapital.mkt.job.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.biz.WechatQrcodeBiz;
import cn.rongcapital.mkt.job.service.base.TaskService;

@Service
public class WechatQrcodeScheduleImpl implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WechatQrcodeBiz wechatQrcodeBiz;
	
	@Override
	public void task(Integer taskId) {
		//给每个授权的账号每天批量生成二维码2000个
		try {
			wechatQrcodeBiz.getQrcodes(1, 100, "QR_LIMIT_SCENE");
		} catch (FileNotFoundException e) {			
			logger.info(e.getMessage());
		} catch (IOException e) {			
			logger.info(e.getMessage());
		}
	}

}
