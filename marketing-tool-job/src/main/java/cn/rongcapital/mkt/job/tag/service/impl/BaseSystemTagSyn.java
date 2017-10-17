package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import com.mongodb.WriteResult;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.dao.SystemTagResultDao;
import cn.rongcapital.mkt.dao.TagDefinitionDao;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.SysTagView;
import cn.rongcapital.mkt.po.SystemTagResult;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;

/*************************************************
 * @功能简述: 系统标签同步父类
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/10/18
 * @复审人:
 *************************************************/
public class BaseSystemTagSyn {
	
	private static final String GENERAL_TAG = "0";
	
	private static final String VARIABLE_TAG = "1";

	private static final String REDIS_IDS_KEY_PREFIX = "tagcoverid:";

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final Integer REDIS_DB_INDEX = 2;
	
	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	protected SystemTagResultDao systemTagResultDao;

	@Autowired
	protected ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private TagValueCountDao tagValueCountDao;
	
	@Autowired
	private TagDefinitionDao tagDefinitionDao;

	/**
	 * 获取标签视图映射集合
	 * 
	 * @param targetDao
	 *            目标表Dao
	 * @return
	 */
	protected void getTagViewList(BaseDao<SysTagView> targetDao) {
		try {
			SysTagView sysTagView = new SysTagView();
			sysTagView.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			sysTagView.setPageSize(null);
			List<SysTagView> tagViewList = targetDao.selectList(sysTagView);
			// 所有人员Id
			Set<String> midSet = new HashSet<>();
			for (SysTagView sys : tagViewList) {
				this.getSysTagResult(sys, midSet);
			}
			// 将所有人员Id保存到Redis中
			String[] idsArray = (String[]) midSet.toArray(new String[midSet.size()]);
			JedisClient.sadd(REDIS_DB_INDEX, RedisKey.ALL_DATAPARY_MID, idsArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void getSysTagResult(SysTagView sys, Set<String> midSet) {
		try {
			String viewName = sys.getViewName(); // 视图名称
			String tagName = sys.getTagName(); // 标签名称

			if (StringUtils.isEmpty(viewName) || StringUtils.isEmpty(tagName)) {
				return;
			}
			// 获取结果
			List<SystemTagResult> resultList = new ArrayList<>();
			switch (sys.getField2()) {
			case GENERAL_TAG:
				resultList = systemTagResultDao.selectListByMap(viewName);
				break;
			case VARIABLE_TAG:
				String sql = tagDefinitionDao.selectDefinitionSqlByTagName(tagName);
				resultList = tagDefinitionDao.executeSql(sql);
				break;
			default:
				break;
			}
			
			logger.info("开始同步" + sys.getViewDesc() + "标签，-------->" + tagName);
			if(CollectionUtils.isEmpty(resultList)){
				logger.info("此标签无匹配结果,不进行后续处理---------->"+ tagName);
				return;
			}

			// 查询推荐标签
			Criteria criteriaAll = Criteria.where("tag_name_eng").is(tagName);

			TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
					cn.rongcapital.mkt.po.mongodb.TagRecommend.class);

			if (tagRecommend == null) {
				return;
			}

			// 存放List
			Map<String, Vector<String>> paramMap = new HashMap<>();
			// 标签Id
			String tagId = tagRecommend.getTagId();
			for (SystemTagResult systemTagResult : resultList) {
				if(systemTagResult == null)continue;	
				// 标签值
				String tagValue = systemTagResult.getTag_value();
				if(StringUtils.isEmpty(tagValue))continue;
				// keyId
				Integer keyId = systemTagResult.getKeyId();
				// 封装Tag属性
				Tag tag = new Tag(tagId, tagRecommend.getTagName(), tagRecommend.getTagNameEng(), tagValue, 1);

				Runnable thread = new Runnable() {
					@Override
					public void run() {
						// 获取redis Key
						String key = getKey(tagId, tagValue);
						Vector<String> vector = paramMap.get(key);
						if (null == vector) {
							vector = getVector();
							paramMap.put(key, vector);
						}
						if (keyId != null) {
							String mid = String.valueOf(keyId);
							vector.add(mid);
							midSet.add(mid);
							startSynchTag(keyId, tag);
						}
					}
				};
				threadPoolTaskExecutor.execute(thread);
			}
			while (true) {
				// 获取线程池中活动线程数
				int threadActiveCount = threadPoolTaskExecutor.getActiveCount();
				if (threadActiveCount == 0) {
					// 存入Redis
					saveDataToReids(paramMap, tagId);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取视图结果数据出现异常----------------->" + e.getMessage(), e);
		}
	}

	/**
	 * 同步标签
	 * 
	 * @param keyId
	 *            人员ID
	 * @param tagValue
	 *            标签名称
	 * @param tagRecommend
	 */
	protected void startSynchTag(Integer keyId, Tag tag) {
		try {
			Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));
			// 查询Mongo是否存在此数据
			DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);
			if (dataParty == null) {
				return;
			}
			Query query = Query.query(Criteria.where("mid").is(keyId));
			WriteResult updateFirst = mongoTemplate.updateFirst(query, new Update().push("tag_list", tag),
					DataParty.class);
			// 为NULL标签该人不存在
			if (updateFirst == null) {
				logger.info("MID不存在---------------->" + keyId);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("同步Mongo中tag_list字段出现异常-------------->" + e.getMessage(), e);
		}
	}

	/**
	 * 初始化Mongo数据，将tag_list字段删除
	 */
	protected void initMongoTagList() {
		logger.info("初始化Mongo tag_list字段方法开始执行---------->");
		try {
			Query query = Query.query(Criteria.where("mid").gt(-1));
			Update update = new Update().unset("tag_list");
			mongoTemplate.updateMulti(query, update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("初始化Mongo tag_list字段出现异常---------->" + e.getMessage(), e);
		}
		logger.info("初始化Mongo tag_list字段方法执行结束---------->");
	}

	/**
	 * 拼接Redis key
	 * 
	 * @param tagID
	 * @param tagValue
	 * @return
	 */
	private String getKey(String tagID, String tagValue) {
		String tagValueId = tagValueCountDao.selectTagValueId(tagID, tagValue);
		return REDIS_IDS_KEY_PREFIX + tagValueId;
	}

	/**
	 * 获取集合
	 * 
	 * @return
	 */
	private Vector<String> getVector() {
		return new Vector<>();
	}

	/**
	 * 保存数据到Redis
	 * 
	 * @param dataMap
	 */
	private void saveDataToReids(Map<String, Vector<String>> dataMap, String tagId) {
		try {
			// 标签总Id
			Set<String> result = new HashSet<>();
			Set<String> keySet = dataMap.keySet();
			for (String key : keySet) {
				boolean delete = JedisClient.delete(REDIS_DB_INDEX, key);
				logger.info("删除redis数据方法执行结束，key为------>" + key, ",是否成功标识----->" + delete);
				Vector<String> vector = dataMap.get(key);
				result.addAll(vector);
				String[] idArray = (String[]) vector.toArray(new String[vector.size()]);
				JedisClient.sadd(REDIS_DB_INDEX, key, idArray);
			}
			if(!CollectionUtils.isEmpty(result)){
				String[] allIds = (String[]) result.toArray(new String[result.size()]);
				JedisClient.sadd(REDIS_DB_INDEX, REDIS_IDS_KEY_PREFIX + tagId, allIds);
			}
		} catch (Exception e) {
			logger.error("保存数据到Redis方法出现异常---------->" + e.getMessage(), e);
		}
	}
	
	

}
