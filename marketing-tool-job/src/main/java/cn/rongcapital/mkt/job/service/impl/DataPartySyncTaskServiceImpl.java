package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.job.service.DataPartySyncService;
import cn.rongcapital.mkt.job.service.base.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DataPartySyncTaskServiceImpl implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    List<DataPartySyncService> syncServices;

	@Override
	public void task(Integer taskId) {
        for (DataPartySyncService dataPartySyncService : syncServices) {
            dataPartySyncService.doSync();
        }
	}

}
