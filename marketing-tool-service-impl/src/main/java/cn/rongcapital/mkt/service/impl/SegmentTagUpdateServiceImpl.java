/*************************************************
 * @功能简述: 增加或修改受众细分关联的标签
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.service.InsertCustomTagService;
import cn.rongcapital.mkt.service.SegmentTagUpdateService;
import cn.rongcapital.mkt.service.TagCustomTagToDataPartyService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentTagUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
@Transactional
public class SegmentTagUpdateServiceImpl implements SegmentTagUpdateService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String REDIS_IDS_KEY_PREFIX = "segmentcoverid:";

	private ConcurrentHashMap<String, Field[]> filedMap = new ConcurrentHashMap<>();

	@Autowired
	SegmentationHeadDao segmentationHeadDao;

	@Autowired
	CustomTagMapDao customTagMapDao;

	@Autowired
	CustomTagDao customTagDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private InsertCustomTagService insertCustomTagServiceImpl;

	@Autowired
	private TagCustomTagToDataPartyService tagCustomTagToDataPartyServiceImpl;

	@ReadWrite(type = ReadWriteType.WRITE)
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public BaseOutput updateSegmentTag(SegmentTagUpdateIn body, SecurityContext securityContext) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Integer headerId;
		Set<String> smembers = null;
		try {
			headerId = Integer.valueOf(body.getSegmentHeadId());
			smembers = JedisClient.smembers(REDIS_IDS_KEY_PREFIX + headerId, 2);
			logger.info("redis key {} get value {}.", REDIS_IDS_KEY_PREFIX + headerId, smembers);
		} catch (Exception e) {
			logger.error(e.getMessage());
			baseOutput.setCode(9001);
			baseOutput.setMsg("细分人群编号不能为空");
			return baseOutput;
		}

		List<String> tagNames = body.getTagNames();

		List<Integer> personIdList = new ArrayList<Integer>();

		if (CollectionUtils.isNotEmpty(smembers)) {
			for (String segment : smembers) {
				personIdList.add(new Integer(segment));
			}
		}
		List<String> tagIdList = new ArrayList<String>();
		// 标签名保存至自定义标签表
		BaseTag baseTag = null;
		String tagSource = TagSourceEnum.SEGMENTATION_SOURCE_ACCESS.getTagSourceName();
		if (tagNames != null) {
			for (String tagName : tagNames) {
				baseTag = new CustomTagLeaf();
				baseTag.setTagName(tagName);
				baseTag.setStatus((int) ApiConstant.TABLE_DATA_STATUS_VALID);
				baseTag.setSource(TagSourceEnum.SEGMENTATION_SOURCE_ACCESS.getTagSourceName());

				Query query = new Query(Criteria.where("tag_name").is(baseTag.getTagName()).and("status")
						.is(baseTag.getStatus()).and("source").is(tagSource));
				List<BaseTag> customTagLeafs = mongoTemplate.find(query, BaseTag.class);

				if (CollectionUtils.isEmpty(customTagLeafs)) {
					BaseTag baseTagResult = insertCustomTagServiceImpl.insertCustomTagLeafFromSystemIn(tagName,
							tagSource);
					tagIdList.add(baseTagResult.getTagId());

				} else {
					Update update = buildBaseUpdate(baseTag);
					mongoTemplate.updateFirst(query, update, baseTag.getClass());
					tagIdList.add(customTagLeafs.get(0).getTagId());
				}
			}
		}
		// 删除标签与细分对应关系
		customTagMapDao.batchDeleteUseHeadId(String.valueOf(headerId));

		// 建立标签与细分对应关系
		for (String customTag : tagIdList) {

			String tagId = customTag;
			CustomTagMap tagMap = new CustomTagMap();
			tagMap.setTagId(String.valueOf(tagId));
			tagMap.setTagSource(TagSourceEnum.SEGMENTATION_SOURCE_ACCESS.getTagSourceId());
			tagMap.setMapId(String.valueOf(headerId));
			customTagMapDao.insert(tagMap);

			// 删除标签与人群对应关系
			customTagMapDao.batchDeleteUseTagId(tagId);

			// 建立细分与人群对应关系
			/*
			 * for (Integer personId : personIdList) { CustomTagMap pensonTagMap
			 * = new CustomTagMap();
			 * pensonTagMap.setTagId(String.valueOf(tagId));
			 * tagMap.setTagSource(TagSourceEnum.SEGMENTATION_SOURCE_ACCESS.
			 * getTagSourceId());
			 * pensonTagMap.setMapId(String.valueOf(personId));
			 * customTagMapDao.insert(pensonTagMap); }
			 */
			// 给人打上标签
			logger.info("tagid is {}========================" + tagId);
			for (Integer personId : personIdList) {
				tagCustomTagToDataPartyServiceImpl.tagCustomTagToDataPartyById(String.valueOf(tagId), personId);
//				logger.info("======================tagid is " + tagId + " personId is " + personId);
			}
		}

		// 标签ID保存至segmentation_head
		SegmentationHead headUpdate = new SegmentationHead();
		headUpdate.setId(headerId);
		headUpdate.setTagIds(StringUtils.join(tagIdList, ","));
		// headUpdate.setUpdateTime(now);
		segmentationHeadDao.updateById(headUpdate);

		return baseOutput;
	}

	private <T> Update buildBaseUpdate(T t) {
		Update update = new Update();
		String className = t.getClass().getName();
		Field[] fields = filedMap.get(className);
		if (fields == null) {
			fields = t.getClass().getDeclaredFields();
			filedMap.putIfAbsent(className, fields);
		}
		for (Field field : fields) {
			if (field.getName().equals("serialVersionUID"))
				continue;
			field.setAccessible(true);
			try {
				Object value = field.get(t);
				if (value != null) {
					update.set(field.getName(), value);
				}
			} catch (Exception e) {
				logger.error("buildBaseUpdate failed", e);
			}
		}
		return update;
	}

}