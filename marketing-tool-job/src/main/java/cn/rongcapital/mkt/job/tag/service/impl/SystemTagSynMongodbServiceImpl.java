package cn.rongcapital.mkt.job.tag.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.SysTagViewDao;
import cn.rongcapital.mkt.job.service.SystemTagSynchService;
import cn.rongcapital.mkt.job.service.base.TaskService;

/*************************************************
 * @功能简述: 同步INCAKE系统标签（查询视图方式）
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/10/18
 * @复审人:
 *************************************************/
@Service
public class SystemTagSynMongodbServiceImpl extends BaseSystemTagSyn implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysTagViewDao sysTagViewDao;
	
	@Autowired
	private SystemTagSynchService systemTagSynchService;
	

	@Override
	public void task(Integer taskId) {
		try {
			initMysqlTagInformation();
			logger.info("同步系统标签任务开始执行------------------>");
			initMongoTagList();
			getTagViewList(sysTagViewDao);
			logger.info("同步系统标签任务执行结束------------------>");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("同步系统标签任务出现异常--------------->" + e.getMessage(), e);
		}
	}
	
	private void initMysqlTagInformation(){
		logger.info("同步系统标签前置初始化MySQL方法开始执行------>");
		systemTagSynchService.initTagValueCount(null);
		logger.info("同步系统标签前置初始化MySQL方法开始结束------>");
	}
	
	

}
