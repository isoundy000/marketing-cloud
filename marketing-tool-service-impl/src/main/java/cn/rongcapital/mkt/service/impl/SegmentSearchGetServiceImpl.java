/*************************************************
 * @功能简述:
 * @see: MkyApi
 * @author: chengjincheng
 * @version: 1.0
 * @date: 2016/9/13
 *************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.common.util.GenderUtils;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.service.SegmentSearchGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentSearchIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentSearchGetServiceImpl implements SegmentSearchGetService {

	@Autowired
	DataPartyDao dataPartyDao;

    public static final Integer POOL_INDEX = 2;
	
	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput SegmentSearch(Integer head_id, String query_name) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

//		Query query = new Query(Criteria.where("segmentation_head_id").is(head_id));
//		List<Segment> segmentList = mongoTemplate.find(query, Segment.class);
		List<Integer> head_ids = new ArrayList<>();
//		if (CollectionUtils.isNotEmpty(segmentList)) {
//			for (Segment segment : segmentList) {
//				head_ids.add(segment.getDataId());
//			}
//		}

		Set<String> mids = new HashSet<String>();
		
		try {
			mids = JedisClient.smembers("segmentcoverid:"+head_id, POOL_INDEX);
		} catch (JedisException e) {
			e.printStackTrace();
		}
		
		for(String mid : mids){
			head_ids.add(Integer.valueOf(mid));
		}
		
		if (CollectionUtils.isNotEmpty(head_ids)) {
			SegmentSearchIn searchIn = new SegmentSearchIn();
			searchIn.setHeadidList(head_ids);
			searchIn.setQueryName(query_name);

//			List<DataParty> dataList = dataPartyDao.segmentSearch(searchIn);
//			List<Map<String,Object>> dataList = dataPartyDao.segmentSearch(searchIn);
//			if (CollectionUtils.isNotEmpty(dataList)) {
//				result.setTotal(dataList.size());
//				for (DataParty data : dataList) {
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("name", data.getName());
//					result.getData().add(map);
//				}
//			}
			
			List<Map<String,Object>> dataList = dataPartyDao.segmentSearch(searchIn);
			
			if(dataList != null && dataList.size() > 0){
				for(Map<String,Object> map : dataList){
					if(map.get("gender") != null){
	                    Integer gender = (Integer) map.get("gender");
						map.put("gender", GenderUtils.byteToChar(gender.byteValue()));
					}
				}
			}
			
			result.getData().addAll(dataList);
		}
		
		return result;
	}
}