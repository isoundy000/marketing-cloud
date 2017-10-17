package cn.rongcapital.mkt.job.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.service.WeixinQrcodeListService;
/*************************************************
 * @功能及特点的描述简述: 定时任务，更新失效状态
 * 
 * @see （与该类关联的类): WechatInfoSendBiz
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.2
 * @date(创建、开发日期): 2016-09-19
 * 最后修改日期: 2016-09-19
 * @复审人: 丛树林
 *************************************************/
@Service
public class WechatQrcodeExpirationTimeImpl implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WeixinQrcodeListService weixinQrcodeListService;
	
	@Override
	public void task(Integer taskId) {
		logger.info("更新失效时间任务");
		weixinQrcodeListService.updateStatusByExpirationTime(null);
	}

}
