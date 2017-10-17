/*************************************************
 * @功能简述: 获取系统标签内容列表
 * @see MktApi
 * @author: zhangwei
 * @version: 1.0 @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.PagingUtil;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagSystemListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TagSystemListGetServiceImpl implements TagSystemListGetService {

	@Autowired
	TaggroupDao taggroupDao;

	@Autowired
	MongoOperations mongoOperations;

	@Override
	public BaseOutput getTagcount(String method, String userToken, Integer tagGroupId, Integer index, Integer size) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		PagingUtil.fixPagingParam(index, size);
		Taggroup paramTaggroup = new Taggroup();
		paramTaggroup.setParentGroupId(Long.valueOf(tagGroupId));
		paramTaggroup.setStartIndex(index);
		paramTaggroup.setPageSize(size);

		List<Taggroup> taggroups = taggroupDao.selectList(paramTaggroup);
		List<String> resultList = new ArrayList<>(taggroups.size());

		for (Taggroup taggroup : taggroups) {
			resultList.add(taggroup.getName());
		}

		baseOutput.getData().addAll(resultList);
		if (!CollectionUtils.isEmpty(resultList)) {
			baseOutput.setTotal(resultList.size());
		}

		return baseOutput;
	}

	/**
	 * 获取系统标签内容列表
	 * 
	 * @author congshulin
	 * @功能简述 : 获取系统标签内容列表
	 * @param method
	 * @param userToken
	 * @param tagGroupId
	 * @param index
	 * @param size
	 * @return
	 */
	@Override
	public BaseOutput getMongoTagList(String method, String userToken, String tagGroupId, Integer index, Integer size) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		TagRecommend tagRecommend = mongoOperations.findOne(new Query(Criteria.where("tag_id").is(tagGroupId)),
				TagRecommend.class);
		List<String> tagList = tagRecommend.getTagList();

		baseOutput.getData().addAll(tagList);

		baseOutput.setTotal(tagList.size());

		return baseOutput;
	}

}
