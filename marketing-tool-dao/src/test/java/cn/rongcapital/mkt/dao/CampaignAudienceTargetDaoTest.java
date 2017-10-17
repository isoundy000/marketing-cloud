/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignAudienceTargetDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CampaignAudienceTargetDao campaignAudienceTargetDao;
    
	@Autowired
	private DataPartyDao dataPartyDao;

	private static final String REDIS_IDS_KEY_PREFIX = "segmentcoverid:";

	private ExecutorService executor = null;

	private static final int THREAD_POOL_FIX_SIZE = 100;

	private static final int BATCH_SIZE = 50;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: CampaignAudienceTargetDao 开始---------------------");
        

    }
    
	@Test
	public void testSelectItemIdList() {
	    logger.info("测试方法: selectItemIdList ");    
		long startTime = System.currentTimeMillis();
		executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
		
		List<Future<List<Segment>>> resultList = new ArrayList<Future<List<Segment>>>();
        
		try {
			Set<String> smembers = JedisClient.smembers(REDIS_IDS_KEY_PREFIX + 68, 2);
			logger.info("redis key {} get value {}.", REDIS_IDS_KEY_PREFIX + 68, smembers);
			logger.info("smembers size {}", smembers.size());
			if (CollectionUtils.isNotEmpty(smembers)) {

				List<List<String>> setList = ListSplit.getSetSplit(smembers, BATCH_SIZE);
				logger.info("setList size {}", setList.size());
				for (List<String> segmentIdList : setList) {

					Future<List<Segment>> segmentFutureList = executor.submit(new Callable<List<Segment>>() {

						@Override
						public List<Segment> call() throws Exception {
							List<Segment> selectSegmentByIdList = dataPartyDao.selectSegmentByIdList(segmentIdList);
							return selectSegmentByIdList;
						}

					});
					resultList.add(segmentFutureList);
				}

			}
			executor.shutdown();
			// 设置最大阻塞时间，所有线程任务执行完成再继续往下执行
			executor.awaitTermination(24, TimeUnit.HOURS);
			long endTime = System.currentTimeMillis();

			logger.info("=====================从dataParty同步segment的name,用时" + (endTime - startTime) + "毫秒");

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		List<Segment> segmentList = new ArrayList<Segment>();
		// 遍历任务的结果
		for (Future<List<Segment>> fs : resultList) {
			try {
				List<Segment> list = fs.get(); // 打印各个线程（任务）执行的结果
				for (Segment segment : list) {
					segmentList.add(segment);
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			} catch (ExecutionException e) {
				executor.shutdownNow();
				logger.error(e.getMessage());
			}
		}
	}
	
	@Test
	public void testSelectSegmentationName() {
	    logger.info("测试方法: selectSegmentationName ");    
	}
	
	@Test
	public void testDeleteByCampaignHeadId() {
	    logger.info("测试方法: deleteByCampaignHeadId ");    
	}
	
	@Test
	public void testInsert() {
	    logger.info("测试方法: insert ");    
	}
	
	@Test
	public void testSelectListCount() {
	    logger.info("测试方法: selectListCount ");    
	}
	
	@Test
	public void testUpdateById() {
	    logger.info("测试方法: updateById ");    
	}
	
	@Test
	public void testSelectList() {
	    logger.info("测试方法: selectList ");    
	}
	
	@Test
	public void testSelectListByIdList() {
	    logger.info("测试方法: selectListByIdList ");    
	}
	
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: CampaignAudienceTargetDao 结束---------------------");
    }
}