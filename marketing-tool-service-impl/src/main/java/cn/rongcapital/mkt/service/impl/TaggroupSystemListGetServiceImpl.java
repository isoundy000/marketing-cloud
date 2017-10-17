package cn.rongcapital.mkt.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.TaggroupSystemListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaggroupSystemListGetServiceImpl implements TaggroupSystemListGetService {

	@Autowired
	TaggroupDao taggroupDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public BaseOutput getTagGroupByParentGroupId(String method, String userToken, Integer tagGroupId, Integer index,
			Integer size) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		Taggroup paramTaggroup = new Taggroup(index, size);

		paramTaggroup.setParentGroupId(Long.valueOf(tagGroupId));
		paramTaggroup.setStatus((byte) 0); // 如果状态为0则显示，如果状态为1则不显示

		int total = taggroupDao.selectListCount(paramTaggroup);
		baseOutput.setTotalCount(total);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<Taggroup> groupList = taggroupDao.selectList(paramTaggroup);
		if (CollectionUtils.isNotEmpty(groupList)) {
			baseOutput.setTotal(groupList.size());
			for (Taggroup tagGroup : groupList) {
				Taggroup paramNodeTaggroup = new Taggroup();
				paramNodeTaggroup.setParentGroupId(Long.valueOf(tagGroup.getId()));
				int count = taggroupDao.selectListCount(paramNodeTaggroup);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tag_group_id", tagGroup.getId());

				// map.put("tag_group_name", tagGroup.getName());
				// 截取原字符串倒数第一个 '-' 后面的字符串
				map.put("tag_group_name", tagGroup.getName().substring(tagGroup.getName().lastIndexOf('-') + 1));
				map.put("tag_group_creat_time", sdf.format(tagGroup.getCreateTime()));
				map.put("tag_count", count);
				baseOutput.getData().add(map);
			}
		}

		baseOutput.setColNames(generateColumnNameList());

		return baseOutput;
	}

	private List<Object> generateColumnNameList() {
		List<Object> columnList = new ArrayList<>();
		Map<String, Object> groupNameColumnMap = new HashMap<>();
		Map<String, Object> groupCreateTimeColumnMap = new HashMap<>();
		Map<String, Object> countColumnMap = new HashMap<>();
		groupNameColumnMap.put("col_code", "tag_group_name");
		groupNameColumnMap.put("col_name", "标签名称");
		groupCreateTimeColumnMap.put("col_code", "tag_group_creat_time");
		groupCreateTimeColumnMap.put("col_name", "添加时间");
		countColumnMap.put("col_code", "tag_count");
		countColumnMap.put("col_name", "覆盖人群");
		columnList.add(groupNameColumnMap);
		columnList.add(groupCreateTimeColumnMap);
		columnList.add(countColumnMap);

		return columnList;
	}

	/**
	 * 根据标签树的id从mongodb中获取推荐标签值
	 * 
	 * @author congshulin
	 * @功能简述 : 获取系统标签组列表
	 * @param method
	 * @param userToken
	 * @param tagGroupId
	 * @param index
	 * @param size
	 * @return BaseOutput
	 */
	public BaseOutput getMongoTagRecommendByTagTreeId(String method, String userToken, String tagGroupId, Integer index,
			Integer size) {

		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		List<Map<String, Object>> resultList = new ArrayList<>();

		Query query = new Query();
		Criteria criteria = Criteria.where("tag_id").is(tagGroupId);
		query.addCriteria(criteria);

		TagTree findOne = mongoTemplate.findOne(query, TagTree.class);

		if (findOne == null) {
			return baseOutput;
		}

		List<String> childrenList = findOne.getChildren();


		int indexStart = (index - 1) * size;
		indexStart = indexStart>childrenList.size()?childrenList.size():indexStart;
		int indexEnd = indexStart + size;
		indexEnd = indexEnd>childrenList.size()?childrenList.size():indexEnd;


		long count = mongoTemplate.count(null, DataParty.class);

		for (int i = indexStart; i < indexEnd; i++) {
			String tagRecommendId = childrenList.get(i);

            TagRecommend tagRecommend = mongoTemplate.findOne(
                            new Query(Criteria.where("tag_id").is(tagRecommendId).and("status")
                                            .is(ApiConstant.TABLE_DATA_STATUS_VALID)),
                            TagRecommend.class);
			
			// 如果查询不到标签，则跳过这次循环
			if(tagRecommend == null) {
			    continue;
			}

			long tagCount = mongoTemplate.count(new Query(Criteria.where("tagList.tagId").is(tagRecommendId)),
					DataParty.class);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tag_group_id", tagRecommend.getTagId());

			// map.put("tag_group_name", tagGroup.getName());
			// 截取原字符串倒数第一个 '-' 后面的字符串
			map.put("tag_group_name", tagRecommend.getTagName());
			map.put("tag_group_creat_time", DateUtil.getStringFromDate(tagRecommend.getUpdateTime(),
					ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
			map.put("tag_count", tagRecommend.getTagList().size());
			map.put("tag_desc", tagRecommend.getTagDesc());
			if (count != 0) {
				map.put("tag_cover", new DecimalFormat("#.##%").format(tagCount / (double)count));
			} else {
				map.put("tag_cover", new DecimalFormat("#.##%").format(0));
			}
			
			// 增加
			map.put("tag_list", tagRecommend.getTagList());
			
			// 增加 推荐标记
			map.put("flag", tagRecommend.getFlag());

			resultList.add(map);
		}

		baseOutput.getData().addAll(resultList);
		baseOutput.setTotal(resultList.size());
		baseOutput.setTotalCount(childrenList.size());

		return baseOutput;
	}

}