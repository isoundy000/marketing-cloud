package cn.rongcapital.mkt.unittest.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import cn.rongcapital.mkt.service.DataPartySyncMongoTaskService;
import cn.rongcapital.mkt.unittest.AbstractUnitTest;


public class ServiceUnitTestSyncMongo extends AbstractUnitTest{

    @Autowired
    private DataPartySyncMongoTaskService dataPartySyncMongoTaskService;

    @Test
	public void campaignDeleteTest(){
        dataPartySyncMongoTaskService.tesTask(1);
	}
}
