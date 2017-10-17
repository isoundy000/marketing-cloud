package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.service.SegmentSearchDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentSearchDownloadOut;

@Service
public class SegmentSearchDownloadServiceImpl implements SegmentSearchDownloadService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	DataPopulationDao segmentSearchDownloadServiceDao;

    public static final Integer POOL_INDEX = 2;
    
	@Override
	public BaseOutput getSegmentSearchDownload(Integer head_id) {

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
			List<SegmentSearchDownloadOut> searchDownloads = segmentSearchDownloadServiceDao
					.getSegmentSearchDownload(head_ids);

			if (CollectionUtils.isNotEmpty(searchDownloads)) {
				Map<String, String> resultMap = new HashMap<>();
				List<Map<String, String>> columnsMapList = new ArrayList<>();
				String[][] excelTitles = { { "name", "姓名" }, { "mobile", "手机号" }, { "gender", "性别" },
						{ "birthday", "出生年月日" }, { "provice", "省" }, { "city", "市" }, { "email", "邮箱" },
						{ "identifyNo", "身份证号" }, { "drivingLicense", "驾驶证号" }, { "wxCode", "微信号" }, { "qq", "qq号" } };
				for (String[] a : excelTitles) {
					Map<String, String> map = new HashMap<>();
					map.put(a[0], a[1]);
					columnsMapList.add(map);
				}
				File file = FileUtil.generateFileforDownload(columnsMapList, searchDownloads,
						FileNameEnum.CUSTOM_AUDIENCE.getDetailName());
				resultMap.put("download_url", file.getName());
				result.getData().add(resultMap);
			}
		}
		return result;
	}
}