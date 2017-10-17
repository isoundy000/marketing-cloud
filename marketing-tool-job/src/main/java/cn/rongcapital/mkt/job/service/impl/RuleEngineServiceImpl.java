package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.service.RuleEngineService;

@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class RuleEngineServiceImpl implements RuleEngineService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("secondaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;

	@Autowired
	Environment env;

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Override
	public Boolean requestRuleEngine(List<Integer> keyIds) {
		boolean requestStatus = false;
		try {
			for (Integer id : keyIds) {
				threadPoolTaskExecutor.execute(new WorkThread(id,env));
			}
		} catch (Exception e) {
			logger.error("请求规则引擎出现异常：---------------->" + e.getMessage(), e);
		}
		logger.info("----------requestRuleEngine----end----");
		return requestStatus;
	}
	
	

}
