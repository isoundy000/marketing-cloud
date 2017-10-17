/*************************************************
 * @功能简述: 根据关键字查询系统推荐标签下拉列表的业务类实现
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagRecommendDao;
import cn.rongcapital.mkt.mongodb.TagRecommendRepository;
import cn.rongcapital.mkt.po.TagRecommend;
import cn.rongcapital.mkt.service.SegmentTagkeyTagListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SegmentTagkeyTagListServiceImpl implements SegmentTagkeyTagListService {

	// @Autowired
	// TaggroupDao taggroupDao;
	// @Autowired
	// private TagGroupMapDao tagGroupMapDao;

	@Autowired
	private TagRecommendDao tagRecommendDao;

	@Autowired
	private TagRecommendRepository tagRecommendRepository;

	@Override
	public BaseOutput getLastTagByKey(String tagGroupName) {
		// //根据标签组名模糊查询标签组
		// Taggroup param = new Taggroup();
		// if(StringUtils.isNotBlank(tagGroupName)) {
		// param.setName(tagGroupName);
		// }
		// param.setName(tagGroupName);
		// param.setLevel(ApiConstant.INT_ZERO);
		// List<Taggroup> resList = taggroupDao.selectByNameFuzzy(param);
		//
		// BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
		// ApiErrorCode.SUCCESS.getMsg(),
		// ApiConstant.INT_ZERO,null);
		// if(CollectionUtils.isNotEmpty(resList)){
		// result.setTotal(resList.size());
		// for(Taggroup po : resList){
		// TagGroupMap tagGroupMap = new TagGroupMap();
		// tagGroupMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		// tagGroupMap.setGroupId(po.getId());
		// List<TagGroupMap> tagGroupMapList =
		// tagGroupMapDao.selectList(tagGroupMap);
		// if(CollectionUtils.isNotEmpty(tagGroupMapList)) {
		// Map<String,Object> map = new HashMap<String,Object>();
		// map.put("tag_group_id", po.getId());
		// map.put("tag_group_name", po.getName());
		// result.getData().add(map);
		// }
		// }
		// }

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		TagRecommend tagRecommend = new TagRecommend();
		if (tagGroupName != null && tagGroupName.length() > 0) {
			tagRecommend.setTagGroupName(tagGroupName);
		}

		List<TagRecommend> tagRecommendLists = tagRecommendDao.fuzzySearch(tagRecommend);

		if (!tagRecommendLists.isEmpty()) {
			result.setTotal(tagRecommendLists.size());
			for (TagRecommend tagRecommendList : tagRecommendLists) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tag_group_id", tagRecommendList.getTagGroupId());
				map.put("tag_group_name", getTagRecommendLastName(tagRecommendList.getTagGroupName()));
				result.getData().add(map);
			}
		}

		return result;
	}

	/**
	 * 获取系统推荐标签的最后一个关键词
	 * 
	 * @param tagRecommend
	 * @return
	 */
	private String getTagRecommendLastName(String tagRecommend) {
		if (tagRecommend == null || tagRecommend.length() == 0) {
			return "";
		} else {
			return tagRecommend.substring(tagRecommend.lastIndexOf('-') + 1);
		}
	}

	/**
	 * 模糊查询从mongodb中获取推荐标签列表
	 * 
	 * @author congshulin
	 * @功能简述 : 模糊查询从mongodb中获取推荐标签列表
	 * @param tagName
	 * @return BaseOutput
	 */
	public BaseOutput getMongoTagRecommendByLike(String tagName) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		List<cn.rongcapital.mkt.po.mongodb.TagRecommend> tagNameLikeList = tagRecommendRepository
				.findByTagNameLike(tagName);

		if (CollectionUtils.isNotEmpty(tagNameLikeList)) {

			for (cn.rongcapital.mkt.po.mongodb.TagRecommend tagRecommend : tagNameLikeList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tag_group_id", tagRecommend.getTagId());
				map.put("tag_group_name", tagRecommend.getTagName());
				result.getData().add(map);
			}
			result.setTotal(tagNameLikeList.size());
		}

		return result;
	}
}
