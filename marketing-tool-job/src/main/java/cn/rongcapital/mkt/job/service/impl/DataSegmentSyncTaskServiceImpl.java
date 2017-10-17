package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.WriteResult;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SegmentationBody;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;

@Service
public class DataSegmentSyncTaskServiceImpl implements TaskService {

	// private Logger logger = LoggerFactory.getLogger(getClass());
	private Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SegmentationBodyDao segmentationBodyDao;
	@Autowired
	private SegmentationHeadDao segmentationHeadDao;
	private static final int pageSize = 100;

	@Override
	public void task(String jsonMessage) {
		JSONObject userJson = JSON.parseObject(jsonMessage);
		logger.info("message:" + jsonMessage);
		Integer jsonInt = userJson.getInteger("id");
		SegmentationHead segmentationHead = new SegmentationHead();
		segmentationHead.setId(jsonInt);
		this.DataSegmentSyncOne(segmentationHead);
	}

	@Override
	public void task(Integer taskId) {
		SegmentationHead segmentationHeadT = new SegmentationHead();
		segmentationHeadT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int totalRecord = segmentationHeadDao.selectListCount(segmentationHeadT);
		int totalPage = (totalRecord + pageSize - 1) / pageSize;
		for (int index = 1; index <= totalPage; index++) {
			segmentationHeadT = new SegmentationHead(index, pageSize);
			segmentationHeadT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			segmentationHeadT.setOrderField("update_time");
			segmentationHeadT.setOrderFieldType("desc");
			List<SegmentationHead> segmentationHeadList = segmentationHeadDao.selectList(segmentationHeadT);
			if (CollectionUtils.isEmpty(segmentationHeadList)) {
				break;
			}
			doTask(segmentationHeadList);
		}
	}

	private void doTask(List<SegmentationHead> segmentationHeadList) {
		if (CollectionUtils.isEmpty(segmentationHeadList)) {
			return;
		}
		for (SegmentationHead segmentationHead : segmentationHeadList) {
			logger.info("正在同步细分管理, id={} , name = {}", segmentationHead.getId(), segmentationHead.getName());

			// if(segmentationHead.getPublishStatus() ==
			// ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH) {
			// continue;//未发布的细分不进行同步
			// }

			// mongoTemplate.findAllAndRemove(new
			// Query(Criteria.where("segmentationHeadId").is(segmentationHead.getId())),Segment.class);
			// List<Segment> lastSegmentList = mongoTemplate.find(new
			// Query(Criteria.where("segmentationHeadId").is(segmentationHead.getId())),Segment.class);
			// Set<String> lastSegmentDataIdSet = new HashSet<String>();
			// if(CollectionUtils.isNotEmpty(lastSegmentList)) {
			// for(Segment s:lastSegmentList) {
			// lastSegmentDataIdSet.add(s.getDataId()+"");
			// }
			// }
			// SEGMENTATION_GROUP_MEMBER_MOST_COUNT:每个组最多的标签数

			// 清除Segment表中segmentation_head_id
			WriteResult SegmentRemoveByHeadId = mongoTemplate.remove(
					new Query(Criteria.where("segmentation_head_id").is(segmentationHead.getId())), Segment.class);
			logger.info("清除Segment表中segmentation_head_id = {} 完毕, 共删除：{} 个", segmentationHead.getId(),
					SegmentRemoveByHeadId.getN());

			for (int groupIndex = 0; groupIndex < ApiConstant.SEGMENTATION_GROUP_MEMBER_MOST_COUNT; groupIndex++) {
				SegmentationBody segmentationBodyT = new SegmentationBody();
				segmentationBodyT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				segmentationBodyT.setHeadId(segmentationHead.getId());
				segmentationBodyT.setGroupIndex(groupIndex);
				List<SegmentationBody> segmentationBodyList = segmentationBodyDao.selectList(segmentationBodyT);
				if (segmentationBodyList == null || CollectionUtils.isEmpty(segmentationBodyList)
						|| segmentationBodyList.size() == 0) {
					// logger.info("同步细分管理body失败，因为没有有效数据, id={} , name = {}",
					// segmentationHead.getId(), segmentationHead.getName());
					continue;
				}
				List<Criteria> criteriasList = new ArrayList<Criteria>();
				for (SegmentationBody segmentationBody : segmentationBodyList) {
					// logger.info("正在同步细分管理body, id={} , name = {}, tag_id =
					// {}", segmentationHead.getId(),
					// segmentationHead.getName(), segmentationBody.getTagId());
					String tagId = segmentationBody.getTagId();
					String tagGroupId = segmentationBody.getGroupId();
					Byte exclude = segmentationBody.getTagExclude().byteValue();
					Criteria oneCriteria = null;
					if (exclude == 0) {
						if ("0".equals(tagId)) {// 不限
							oneCriteria = Criteria.where("tagList").elemMatch(Criteria.where("tagId").is(tagGroupId));
						} else {

							String tagIndex = tagId.substring(tagId.indexOf("_") + 1);

							TagRecommend findOne = mongoTemplate
									.findOne(new Query(Criteria.where("tagId").is(tagGroupId)), TagRecommend.class);
							List<String> tagList = findOne.getTagList();
							String tagValue = tagList.get(Integer.valueOf(tagIndex));

							oneCriteria = Criteria.where("tagList")
									.elemMatch(Criteria.where("tagId").is(tagGroupId).and("tagValue").is(tagValue));

						}
					} else {
						if ("0".equals(tagId)) {// 不限
							oneCriteria = Criteria.where("tagList").elemMatch(Criteria.where("tagId").ne(tagGroupId));
						} else {

							String tagIndex = tagId.substring(tagId.indexOf("_") + 1);

							TagRecommend findOne = mongoTemplate
									.findOne(new Query(Criteria.where("tagId").is(tagGroupId)), TagRecommend.class);
							List<String> tagList = findOne.getTagList();
							String tagValue = tagList.get(Integer.valueOf(tagIndex));
							oneCriteria = Criteria.where("tagList")
									.elemMatch(Criteria.where("tagId").ne(tagGroupId).and("tagValue").ne(tagValue));
						}
					}
					criteriasList.add(oneCriteria);
				}

				Criteria criteriaAll = new Criteria()
						.andOperator(criteriasList.toArray(new Criteria[criteriasList.size()]));
				List<DataParty> dataPartyList = mongoTemplate.find(new Query().addCriteria(criteriaAll),
						DataParty.class);
				logger.info("同步细分管理body id={} , name = {},dataPartyList.size={} ", segmentationHead.getId(),
						segmentationHead.getName(), dataPartyList.size());
				if (CollectionUtils.isNotEmpty(dataPartyList)) {

					// 创建永远存储批量插入的List
					List<Segment> segmentLists = new ArrayList<Segment>();
					// 用来记录segmentLists中有多少条数据，当segmentLists中超过100000条时进行一次插入，防止数量过大被mongodb拦截
					int segmentListsCount = 0;

					for (DataParty dataParty : dataPartyList) {

						// mongoTemplate.remove(new
						// Query(Criteria.where("segmentationHeadId").is(segmentationHead.getId()).and("dataId").is(dataParty.getMid())),
						// Segment.class);
						// List<Segment> sListT = mongoTemplate.find(new
						// Query(Criteria.where("segmentationHeadId")
						// .is(segmentationHead.getId()).and("dataId").is(dataParty.getMid())),
						// Segment.class);

						// if (CollectionUtils.isEmpty(sListT)) {// 不存在，则插入
						// if(!lastSegmentDataIdSet.contains(dataParty.getMid()+""))
						// {//不存在，则插入
						Segment segment = new Segment();
						segment.setDataId(dataParty.getMid());
						segment.setSegmentationHeadId(segmentationHead.getId());
						if (dataParty.getMdType() == DataTypeEnum.WECHAT.getCode()) {// 如果是微信数据
							segment.setName(dataParty.getWxName());
						} else {
							segment.setName(dataParty.getName());
						}
						segment.setMdType(dataParty.getMdType());
						segment.setMappingKeyid(dataParty.getMappingKeyid());
						// mongoTemplate.insert(segment);

						segmentListsCount++;
						segmentLists.add(segment);
						if (segmentListsCount > 100000) {
							mongoTemplate.insert(segmentLists, Segment.class);
							// 重置list
							segmentListsCount = 0;
							segmentLists.clear();
						}
						// }
						// else {//存在,则删除之前list里的id
						// lastSegmentDataIdSet.remove(dataParty.getMid()+"");
						// }
					}

					// 批量插入
					mongoTemplate.insert(segmentLists, Segment.class);
				}
			}
            logger.info("已经完成导入到segment表中  共导入{}条", mongoTemplate.count(
                    new Query(Criteria.where("segmentation_head_id").is(segmentationHead.getId())), Segment.class));
            // if(CollectionUtils.isNotEmpty(lastSegmentDataIdSet)) {
			// for(String dataId:lastSegmentDataIdSet) {
			// mongoTemplate.remove(new
			// Query(Criteria.where("segmentationHeadId")
			// .is(segmentationHead.getId())
			// .and("dataId").is(Integer.parseInt(dataId))),
			// Segment.class);
			// }
			// }
		}
	}

	/**
	 * 根据传入的SegmentationHead只同步这一个SegmentationHead
	 *
	 * @param segmentationHead
	 * @Date 2016.10.17
	 */
	public void DataSegmentSyncOne(SegmentationHead segmentationHead) {

		logger.info("正在同步细分管理, id={}", segmentationHead.getId());

		// 清除Segment表中segmentation_head_id
		WriteResult SegmentRemoveByHeadId = mongoTemplate
				.remove(new Query(Criteria.where("segmentation_head_id").is(segmentationHead.getId())), Segment.class);
		logger.info("清除Segment表中segmentation_head_id = {} 完毕, 共删除：{} 个", segmentationHead.getId(),
				SegmentRemoveByHeadId.getN());

		for (int groupIndex = 0; groupIndex < ApiConstant.SEGMENTATION_GROUP_MEMBER_MOST_COUNT; groupIndex++) {
			SegmentationBody segmentationBodyT = new SegmentationBody();
			segmentationBodyT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			segmentationBodyT.setHeadId(segmentationHead.getId());
			segmentationBodyT.setGroupIndex(groupIndex);
			List<SegmentationBody> segmentationBodyList = segmentationBodyDao.selectList(segmentationBodyT);
			if (segmentationBodyList == null || CollectionUtils.isEmpty(segmentationBodyList)
					|| segmentationBodyList.size() == 0) {
				// logger.info("同步细分管理body失败，因为没有有效数据, id={} , name = {},
				// groupIndex = {}", segmentationHead.getId(),
				// segmentationHead.getName());
				continue;
			}
			List<Criteria> criteriasList = new ArrayList<Criteria>();
			for (SegmentationBody segmentationBody : segmentationBodyList) {
				// logger.info("正在同步细分管理body, id={} , name = {}, tag_id = {}",
				// segmentationHead.getId(),
				// segmentationHead.getName(), segmentationBody.getTagId());
				String tagId = segmentationBody.getTagId();
				String tagGroupId = segmentationBody.getGroupId();
				Byte exclude = segmentationBody.getTagExclude().byteValue();
				Criteria oneCriteria = null;
				if (exclude == 0) {
					if ("0".equals(tagId)) {// 不限
						oneCriteria = Criteria.where("tagList").elemMatch(Criteria.where("tagId").is(tagGroupId));
					} else {

						String tagIndex = tagId.substring(tagId.indexOf("_") + 1);

						TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(tagGroupId)),
								TagRecommend.class);
						List<String> tagList = findOne.getTagList();
						String tagValue = tagList.get(Integer.valueOf(tagIndex));

						oneCriteria = Criteria.where("tagList")
								.elemMatch(Criteria.where("tagId").is(tagGroupId).and("tagValue").is(tagValue));

					}
				} else {
					if ("0".equals(tagId)) {// 不限
						oneCriteria = Criteria.where("tagList").elemMatch(Criteria.where("tagId").ne(tagGroupId));
					} else {

						String tagIndex = tagId.substring(tagId.indexOf("_") + 1);

						TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(tagGroupId)),
								TagRecommend.class);
						List<String> tagList = findOne.getTagList();
						String tagValue = tagList.get(Integer.valueOf(tagIndex));
						oneCriteria = Criteria.where("tagList")
								.elemMatch(Criteria.where("tagId").ne(tagGroupId).and("tagValue").ne(tagValue));
					}
				}
				criteriasList.add(oneCriteria);
			}

			Criteria criteriaAll = new Criteria()
					.andOperator(criteriasList.toArray(new Criteria[criteriasList.size()]));
			List<DataParty> dataPartyList = mongoTemplate.find(new Query().addCriteria(criteriaAll), DataParty.class);
			logger.info("同步细分管理body id={},dataPartyList.size={} ", segmentationHead.getId(), dataPartyList.size());
			if (CollectionUtils.isNotEmpty(dataPartyList)) {

				// 创建永远存储批量插入的List
				List<Segment> segmentLists = new ArrayList<Segment>();
				// 用来记录segmentLists中有多少条数据，当segmentLists中超过100000条时进行一次插入，防止数量过大被mongodb拦截
				int segmentListsCount = 0;

				for (DataParty dataParty : dataPartyList) {

					Segment segment = new Segment();
					segment.setDataId(dataParty.getMid());
					segment.setSegmentationHeadId(segmentationHead.getId());
					if (dataParty.getMdType() == DataTypeEnum.WECHAT.getCode()) {// 如果是微信数据
						segment.setName(dataParty.getWxName());
					} else {
						segment.setName(dataParty.getName());
					}
					segment.setMdType(dataParty.getMdType());
					segment.setMappingKeyid(dataParty.getMappingKeyid());

					segmentListsCount++;
					segmentLists.add(segment);
					if (segmentListsCount > 100000) {
						mongoTemplate.insert(segmentLists, Segment.class);
						// 重置list
						segmentListsCount = 0;
						segmentLists.clear();
					}
				}
				// 批量插入
				mongoTemplate.insert(segmentLists, Segment.class);
			}
            logger.info("已经完成导入到segment表中  共导入{}条", mongoTemplate.count(
                    new Query(Criteria.where("segmentation_head_id").is(segmentationHead.getId())), Segment.class));
        }
	}

}
