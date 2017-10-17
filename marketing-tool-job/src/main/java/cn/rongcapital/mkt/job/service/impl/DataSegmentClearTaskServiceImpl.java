package cn.rongcapital.mkt.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class DataSegmentClearTaskServiceImpl implements TaskService {
	@Autowired
	private MongoTemplate mongoTemplate; 
	@Override
	public void task(Integer taskId) {
		mongoTemplate.dropCollection(Segment.class);
	}
}
