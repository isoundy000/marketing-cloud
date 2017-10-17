package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.OriginalDataPopulationDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.OriginalDataPopulation;
import cn.rongcapital.mkt.service.OriginalDataPopulationService;

@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class OriginalDataPopulationServiceImpl implements OriginalDataPopulationService,TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());

    private static final int THREAD_POOL_FIX_SIZE = 100;

	@Autowired
    private OriginalDataPopulationDao originalDataPopulationDao;

    @Autowired
    private DataPopulationDao dataPopulationDao;
    
	@Autowired
	Environment env;
	
	private ExecutorService executor = null;
	
    @Override
    public void cleanData() {
    	
    	executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
    	
    	logger.info("=====================上传人口属性开始");
    	long startTime = System.currentTimeMillis();
        int BATCH_NUM = Integer.valueOf(env.getProperty("orginal.to.data.batch.num"));

        // 1. 取出需要处理的数据
        OriginalDataPopulation paramOriginalDataPopulation = new OriginalDataPopulation();
        paramOriginalDataPopulation.setStatus(StatusEnum.ACTIVE.getStatusCode());
        
        paramOriginalDataPopulation.setStartIndex(null);
        paramOriginalDataPopulation.setPageSize(null);
        List<OriginalDataPopulation> originalDataPopulationsTotal =
                originalDataPopulationDao.selectList(paramOriginalDataPopulation);
        
        List<List<OriginalDataPopulation>> originalDataPopulationsList = new ArrayList<List<OriginalDataPopulation>>();
        
        originalDataPopulationsList = ListSplit.getListSplit(originalDataPopulationsTotal, BATCH_NUM);
        
        for(List<OriginalDataPopulation> originalDataPopulations : originalDataPopulationsList){
        	
            executor.submit(new Callable<Void>() {

    			@Override
    			public Void call() throws Exception {
    				
    				handleOriginalDataPopulation(originalDataPopulations);
    				
    				return null;
    			}
    			
            });
        }

        executor.shutdown();
      try {
    	  //设置最大阻塞时间，所有线程任务执行完成再继续往下执行
    	  executor.awaitTermination(24, TimeUnit.HOURS);
    	  long endTime = System.currentTimeMillis();
    	  
    	  logger.info("=====================上传人口属性结束,用时"+ (endTime-startTime) + "毫秒" );
    	  
      } catch (InterruptedException e) {
    	  logger.info("=====================上传人后属性超时" );
      }
    }

    // 处理OriginalDataPopulation的数据
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void handleOriginalDataPopulation(
                    List<OriginalDataPopulation> tmpOriginalDataPopulations) {
        if (tmpOriginalDataPopulations.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataPopulations.size();

        List<DataPopulation> dataPopulations = new ArrayList<>(batchCount);
        // 将OriginalDataPopulation的数据同步到DataPopulation
        for (int i = 0; i < batchCount; i++) {
            DataPopulation paramDataPopulation = new DataPopulation();
            OriginalDataPopulation tmpOriginalDataPopulation = tmpOriginalDataPopulations.get(i);
            BeanUtils.copyProperties(tmpOriginalDataPopulation, paramDataPopulation);

            // 因为在一个事务里 , 直接修改OriginalDataPopulation的状态
            tmpOriginalDataPopulation.setStatus(StatusEnum.PROCESSED.getStatusCode());
            originalDataPopulationDao.updateById(tmpOriginalDataPopulation);
            dataPopulations.add(paramDataPopulation);
        }

        Map<String, List<DataPopulation>> paramMap = new HashMap<>();
        paramMap.put("list", dataPopulations);

        dataPopulationDao.cleanAndUpdateByOriginal(paramMap);
    }

    @Override
    public void task(Integer taskId) {
        cleanData();
    }
}
