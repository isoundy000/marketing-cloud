package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.mongodb.Segment;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * 初始化mongo中segment表数据到redis，此Job只需执行一次
 * Created by lijinkai on 2016-11-09.
 */
@Service
public class Segment2RedisInitServiceImpl implements TaskService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final  Integer POOL_INDEX = 2;
	
    @Autowired
    private SegmentationHeadDao segmentationHeadDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
    @Override
    public void task(Integer taskId) {

    	logger.info("=================================初始化segment数据到Redis开始================================");
    	
		long start = System.currentTimeMillis();
    	//1.从mysql segmenBody表中查询出所有细分segmentList
    	
    	SegmentationHead segmentationHead = new SegmentationHead();
    	
    	segmentationHead.setStartIndex(null);
    	segmentationHead.setPageSize(null);
    	
    	List<SegmentationHead> segmentList = segmentationHeadDao.selectList(segmentationHead);
    	
    	if(segmentList != null && segmentList.size() > 0){
    		
    		for(SegmentationHead segment:segmentList){
    			
    			copyMongo2RedisByHeadId(segment);
    			
    		}
    	}
		long end = System.currentTimeMillis();
    	logger.info("=================================初始化segment数据到Redis结束，用时:" + (end - start) + "毫秒=================================");
    }

    
    private void copyMongo2RedisByHeadId(SegmentationHead segmentationHead){
    	
    	Integer headId = segmentationHead.getId();
    	String segmentName = segmentationHead.getName();
    
    	logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@插入redis主开始@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		long redisInsertStart = System.currentTimeMillis();
    	
    	try {
    		
    		JedisClient.delete(POOL_INDEX,"segmentcover:"+headId);
	    	JedisClient.hset(POOL_INDEX,"segmentcover:"+headId, "segmentheadid", headId+"");
			JedisClient.hset(POOL_INDEX,"segmentcover:"+headId, "segmentheadname", segmentName);
	    	JedisClient.hset(POOL_INDEX,"segmentcover:"+headId, "segmentcoverid", "segmentcoverid:"+headId);

		} catch (JedisException e) {
			
			logger.info("=============================同步segment到redis出错,headid:"+headId+"==================");
			e.printStackTrace();
		}

    	logger.info("=================================查询mongo细分管理数据开始================================");
		long selectMongoStart = System.currentTimeMillis();
		
		List<Segment> segmentLists = new ArrayList<Segment>();

    	Query query = new Query(Criteria.where("segmentationHeadId").is(headId));

		int totalSize = (int) mongoTemplate.count(query, Segment.class);
		int pageSize = 10000;
		int totalPages = (totalSize + pageSize - 1) / pageSize;

		logger.info("=========分批查询细分管理主数据共" + totalSize + "条，分为" + totalPages+ "页===============");

		for (int i = 0; i < totalPages; i++) {

			Query queryChild = new Query(Criteria.where("segmentationHeadId").is(headId)).skip(i * pageSize).limit(pageSize);
			List<Segment> segmentList = mongoTemplate.find(queryChild, Segment.class);
			segmentLists.addAll(segmentList);

		}
		
		long selectMongoEnd = System.currentTimeMillis();
		logger.info("=========查询mongo细分管理数据结束,查询数据总:" + segmentLists.size() + "条,用时:" + (selectMongoEnd - selectMongoStart) + "毫秒=================");
    	
		try {
			
	    	logger.info("=================================插入redisIds开始================================");
			long insetIdsStart = System.currentTimeMillis();
			for(Segment segment:segmentLists){
				JedisClient.sadd(POOL_INDEX,"segmentcoverid:"+headId, segment.getDataId()+"");
			}
			
			Long count = JedisClient.scard(POOL_INDEX, "segmentcoverid:"+headId);
			JedisClient.hset(POOL_INDEX,"segmentcover:"+headId, "segmentcovercount", count+"");
			
			long insetIdsEnd= System.currentTimeMillis();
			logger.info("=========插入redisIds结束,插入数据总:" + segmentLists.size() + "条,用时:" + (insetIdsEnd - insetIdsStart) + "毫秒=================");
		} catch (JedisException e) {
			e.printStackTrace();
		}
		
		long redisInsertend = System.currentTimeMillis();
		
		logger.info("@@@@@@@@@@@@@@@@@@@@@插入redis主结束,用时:" + (redisInsertend - redisInsertStart) + "毫秒@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    
}
