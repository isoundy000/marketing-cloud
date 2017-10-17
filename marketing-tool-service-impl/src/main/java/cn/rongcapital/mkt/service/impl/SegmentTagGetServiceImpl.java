/*************************************************
 * @功能简述: 获取细分关联的标签
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.service.SegmentTagGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentTagGetOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentTagGetServiceImpl implements SegmentTagGetService {

	@Autowired
	CustomTagMapDao customTagMapDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@ReadWrite(type = ReadWriteType.READ)
	@Override
	public BaseOutput getSegmentTag(String userToken, String segmentHeadId) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		CustomTagMap customTagMap =  new CustomTagMap();
		customTagMap.setMapId(segmentHeadId);
		customTagMap.setStatus((byte)0);//未删除的数据
		customTagMap.setTagSource(TagSourceEnum.SEGMENTATION_SOURCE_ACCESS.getTagSourceId());//细分的标签
		customTagMap.setStartIndex(null);
		customTagMap.setPageSize(null);
		List<CustomTagMap> resultCustomTagMap = customTagMapDao.selectList(customTagMap);
		
		String source = TagSourceEnum.SEGMENTATION_SOURCE_ACCESS.getTagSourceName();
		List<String> tagIdList = new ArrayList<>();
		List<BaseTag> data = new ArrayList<>();
		if(resultCustomTagMap != null && !resultCustomTagMap.isEmpty()){
			for(CustomTagMap ctm : resultCustomTagMap){
				tagIdList.add(ctm.getTagId());
				//根据tagid 和状态（数据正常）查询一条
				Query query = new Query(Criteria.where("tag_id").is(ctm.getTagId()).and("status").is(0).and("source").is(source));
				BaseTag tag = mongoTemplate.findOne(query, BaseTag.class);
				data.add(tag);
			}
		}
		
		/*List<CustomTagWithName> data = customTagMapDao.getTagUseHeadId(Long
				.valueOf(segmentHeadId));*/
		if (data != null && data.size() > 0) {
			List<Object> result = new ArrayList<Object>();
			SegmentTagGetOut out = null;
			for (BaseTag tags : data) {
				out = new SegmentTagGetOut();
				out.setTagId(tags.getTagId());
				out.setTagName(tags.getTagName());
				result.add(out);
			}
			baseOutput.setData(result);
			baseOutput.setTotal(result.size());
			
		}
		return baseOutput;
	}

}
