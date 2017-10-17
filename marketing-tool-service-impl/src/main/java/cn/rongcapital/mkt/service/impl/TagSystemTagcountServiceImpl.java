package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.TagSystemTagcountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TagSystemTagcountServiceImpl implements TagSystemTagcountService {

	@Autowired
	private TaggroupDao taggroupDao;
	@Autowired
	MongoOperations mongoOperations;

	@Override
	public BaseOutput getTagcount(String method, String userToken) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Taggroup paramTaggroup = new Taggroup(0, 1);
		paramTaggroup.setOrderField("update_time");
		paramTaggroup.setOrderFieldType("DESC");
		taggroupDao.selectList(paramTaggroup);
		int tagCount = taggroupDao.selectSystemTagCount();
		List<Taggroup> taggroupResult = taggroupDao.selectList(paramTaggroup);
		Date resultDate = new Date();
		if (!CollectionUtils.isEmpty(taggroupResult)) {
			Date updateTime = taggroupResult.get(0).getUpdateTime();
			if (updateTime != null) {
				resultDate = updateTime;
			}
		}

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ONE, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tag_count", tagCount);
		map.put("sync_time", format.format(resultDate));
		result.getData().add(map);
		return result;
	}

	/**
	 * 从mongo中获取系统标签组数量
	 * 
	 * @author congshulin
	 * @功能简述 : 获取系统标签组列表
	 * @param method
	 * @param userToken
	 * @param index
	 * @param size
	 * @return BaseOutput
	 */
	@Override
	public BaseOutput getMonggTagcount(String method, String userToken) {

		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Query query = new Query();
		Criteria criteria = Criteria.where("level").is(ApiConstant.TAG_LEVEL).and("status")
				.is((int) ApiConstant.TABLE_DATA_STATUS_VALID);
		query.addCriteria(criteria);
		query.with(new Sort(Direction.DESC, "update_time"));
		// 获取一级节点的所有子节点
		List<TagTree> tagTreeList = mongoOperations.find(query, TagTree.class);
		
		if (CollectionUtils.isEmpty(tagTreeList)) {
			return baseOutput;
		}
		Date updateTime = tagTreeList.get(0).getUpdateTime();
		int tagCount = 0;

		for (TagTree tagTree : tagTreeList) {
			List<String> childrenList = tagTree.getChildren();
			for (String children : childrenList) {
				List<TagRecommend> find = mongoOperations.find(new Query(new Criteria("tag_id").is(children)),
						TagRecommend.class);
				if (CollectionUtils.isNotEmpty(find)) {
					tagCount += find.get(0).getTagList().size();
				}
			}

		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("tag_count", String.valueOf(tagCount));
		map.put("sync_time", DateUtil.getStringFromDate(updateTime, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));

		baseOutput.getData().add(map);
		baseOutput.setTotal(ApiConstant.INT_ONE);
		return baseOutput;
	}

}
