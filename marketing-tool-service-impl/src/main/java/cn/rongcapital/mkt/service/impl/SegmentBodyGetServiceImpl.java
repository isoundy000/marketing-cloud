/*************************************************
 * @功能简述: 获取受众细分body信息
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.po.SegmentBodyWithName;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.SegmentBodyGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentBodyGetOut;
import cn.rongcapital.mkt.vo.out.SegmentBodyTagsOut;

@Service
public class SegmentBodyGetServiceImpl implements SegmentBodyGetService {

	@Autowired
	SegmentationBodyDao segmentationBodyDao;
	
	@Autowired
    private MongoTemplate mongoTemplate;

	@ReadWrite(type = ReadWriteType.READ)
	@Override
	public BaseOutput getSegmentBody(String userToken, String segmentHeadId) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		List<SegmentBodyWithName> bodyList = segmentationBodyDao.getSegBodyUseHeaderId(Integer.valueOf(segmentHeadId));
		List<Object> data = new ArrayList<Object>();
		Map<Integer, List<SegmentBodyTagsOut>> dataMap = new TreeMap<Integer, List<SegmentBodyTagsOut>>();
		SegmentBodyGetOut dataElement = null;
		if(bodyList!= null){
			for (SegmentBodyWithName body : bodyList) {
				SegmentBodyTagsOut tag = new  SegmentBodyTagsOut();
				tag.setTagId(body.getTagId());
				tag.setTagGroupId(body.getTagGroupId());
				
				// 从mongodb查找tag
				TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(body.getTagGroupId())), TagRecommend.class);
				if(findOne != null) {
				    tag.setTagName(findOne.getTagName());
				    String tagId = body.getTagId();
                    int tagListIndex = Integer.valueOf(tagId.substring(tagId.lastIndexOf('_') + 1));
				    if(findOne.getTagList() != null && findOne.getTagList().size() > tagListIndex) {
				        tag.setTagGroupName(findOne.getTagList().get(tagListIndex));
				    }
				}
				tag.setExclude(body.getExclude());
				if (dataMap.containsKey(body.getGroupIndex())){
					dataMap.get(body.getGroupIndex()).add(tag);
				}else{
					dataMap.put(body.getGroupIndex(), new ArrayList<SegmentBodyTagsOut>());
					dataMap.get(body.getGroupIndex()).add(tag);
				}
			}
		}
		for (Entry<Integer,List<SegmentBodyTagsOut>> mapElement : dataMap.entrySet()) {
			dataElement = new SegmentBodyGetOut();
			dataElement.setGroupIndex(mapElement.getKey());
			dataElement.setTagList(mapElement.getValue());
			data.add(dataElement);
		}
		baseOutput.setTotal(data.size());
		baseOutput.setData(data);
		return baseOutput;
	}

}
