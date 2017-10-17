/*************************************************
 * @功能简述: 获取细分标签漏斗计算
 * @see MktApi：
 * @author: xukun
 * @version: 1.0 @date：2016-06-28
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.service.SegmentCalcService;
import cn.rongcapital.mkt.vo.SegmentGroupRedisVO;
import cn.rongcapital.mkt.vo.SegmentGroupTagRedisVO;
import cn.rongcapital.mkt.vo.SegmentRedisVO;
import cn.rongcapital.mkt.vo.in.*;
import cn.rongcapital.mkt.vo.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.GenderEnum;
import cn.rongcapital.mkt.common.enums.SegmentProvinceMap;
import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.SegmentFilterGetService;
import cn.rongcapital.mkt.service.SegmentManageCalService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentFilterGetServiceImpl implements SegmentFilterGetService {

	private Logger logger = LoggerFactory.getLogger(getClass());

//	private String FRONT_PROVINCE_NAME = "北京,天津,上海,重庆,河北,河南,云南,辽宁,黑龙江,湖南,安徽,"
//			+ "山东,新疆,江苏,浙江,江西,湖北,广西,甘肃,山西,内蒙古,陕西,吉林,福建,贵州," + "广东,青海,西藏,四川,宁夏,海南,台湾,香港,澳门";

	private final static int selectBatchSize  = 10000;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private SegmentCalcService segmentCalcService;

	public static final  Integer POOL_INDEX = 2;
	
	public static final String CHINA_COUNT_KEY = "chinacountkey";
	
	public static final String TAG_COVER_ID_STR="tagcoverid:";
	
	@Autowired
	private TagValueCountDao tagValueCountDao;
	
	@Autowired
	private SegmentManageCalService segmentManageCalService;
	
    private ThreadLocal<String> uuid=new ThreadLocal<String>();    
    private ThreadLocal<ArrayList<String>> tempKeys=new ThreadLocal<ArrayList<String>>();
    
	@ReadWrite(type = ReadWriteType.READ)
	@Override
	public BaseOutput getSegmentFilterCount(SegmentFilterCountIn body, SecurityContext securityContext) {
		List<SegmentFilterCondition> conditions = body.getConditions();
		int count = conditions.size();
		BaseOutput result = newSuccessBaseOutput();
		List<Object> data = result.getData();
		result.setTotal(count);
		List<Criteria> criterialist = new ArrayList<>();
		for (SegmentFilterCondition condition : conditions) {
			//查询所需参数
			String tagId = condition.getTagId();
			String tagGroupId = condition.getTagGroupId();
			String tagName = condition.getTagName();
			String tagValue = tagName.substring(tagName.indexOf("-")+1, tagName.length());
			String exclude = condition.getExclude();
			Query query = new Query();
				// 根据getExclude()拼条件
				if ("1".equals(exclude)) {
					Criteria criteriaCond = null;
					if ("0".equals(tagId)) {// 不限
						criteriaCond = Criteria.where("tagList").elemMatch(Criteria.where("tagId").ne(tagGroupId));
					} else {
						criteriaCond = Criteria.where("tagList")
								.elemMatch(Criteria.where("tagId").ne(tagGroupId).and("tagValue").ne(tagValue));
					}
					criterialist.add(criteriaCond);
				} else {
					Criteria criteriaCond = null;
					if ("0".equals(tagId)) {// 不限
						criteriaCond = Criteria.where("tagList").elemMatch(Criteria.where("tagId").is(tagGroupId));
					} else {
						criteriaCond = Criteria.where("tagList")
								.elemMatch(Criteria.where("tagId").is(tagGroupId).and("tagValue").is(tagValue));
					}
					criterialist.add(criteriaCond);
				}
			//查询标签值的人数
			Criteria[] crieriaArray = criterialist.toArray(new Criteria[criterialist.size()]);
			query.addCriteria(new Criteria().andOperator(crieriaArray));
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					long tagCount = mongoTemplate.count(query, DataParty.class);
					//封装返回结果
					SystemTagFilterOut systemTagFilterOut = new SystemTagFilterOut(tagId, tagName, tagCount);
					data.add(systemTagFilterOut);
				}
			};
			threadPoolTaskExecutor.execute(runnable);
		}
		while(data.size() < count){
			try {
				Thread.sleep(1L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public BaseOutput getSegmentFilterSum(SegmentFilterSumIn body) {
		BaseOutput result = newSuccessBaseOutput();
		List<SegmentFilterSumCondition> orGroups = body.getGroups();
		if (CollectionUtils.isEmpty(orGroups)) {
			return result;
		}

		List<Criteria> orCriteriaList = new ArrayList<>();
		for (SegmentFilterSumCondition segmentFilterSumCondition : orGroups) {
			List<SegmentFilterCondition> conditions = segmentFilterSumCondition.getConditions();
			if (CollectionUtils.isEmpty(conditions)) {
				continue;
			}
			orCriteriaList.add(getConditionCriteria(conditions));
		}
		if (CollectionUtils.isEmpty(orCriteriaList)) {
			return result;
		}
		Criteria[] orCriteriaArray = new Criteria[orCriteriaList.size()];
		orCriteriaList.toArray(orCriteriaArray);
		Criteria allCriteria = new Criteria();
		allCriteria.orOperator(orCriteriaArray);
		long totalCount = mongoTemplate.count(new Query(allCriteria), DataParty.class);
		result.setTotal(Integer.parseInt(String.valueOf(totalCount)));
		return result;
	}

	private Criteria getConditionCriteria(List<SegmentFilterCondition> conditions) {
		Criteria criteria = new Criteria();
		if (CollectionUtils.isEmpty(conditions)) {
			return criteria;
		}
		List<Criteria> andCriterias = new ArrayList<>();
		for (SegmentFilterCondition tempSegmentFilterCondition : conditions) {
			String tagId = tempSegmentFilterCondition.getTagId();
			String tagGroupId = tempSegmentFilterCondition.getTagGroupId();
			String excule = tempSegmentFilterCondition.getExclude();
			Criteria oneCriteria = null;
			if ("1".equals(excule)) {
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
			} else {
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
			}

			if (andCriterias != null) {
				andCriterias.add(oneCriteria);
			}
		}

		if (!CollectionUtils.isEmpty(andCriterias)) {
			Criteria[] criteriaArray = new Criteria[andCriterias.size()];
			andCriterias.toArray(criteriaArray);
			criteria.andOperator(criteriaArray);
		}
		return criteria;
	}

	@Override
	public BaseOutput segmentGenderCountList(SegmentCountFilterIn input) {
		BaseOutput result = newSuccessBaseOutput();
		
		//获取所有headId并转换成数据格式
		String[] arrHeadIds = getHeadIds(input);
		
		//获取主数据id集合(并集)
		Long midCount = getMids(arrHeadIds);
		
		Map<String,Integer> genderCountMap = getGender();
		
		int maleCount = genderCountMap.get(ApiConstant.GENDER_TAG_KEY_MALE) == null ? 0 : genderCountMap.get(ApiConstant.GENDER_TAG_KEY_MALE);
		int femaleCount = genderCountMap.get(ApiConstant.GENDER_TAG_KEY_FEMALE) == null ? 0 : genderCountMap.get(ApiConstant.GENDER_TAG_KEY_FEMALE);
		int otherCount = Integer.valueOf(midCount+"") - maleCount - femaleCount;

		this.clearTempRedisKeys();
		
		List<SegmentDimensionCountOut> formatSegmentDimensionOut = new ArrayList<>();
		formatSegmentDimensionOut.add(new SegmentDimensionCountOut(GenderEnum.MALE.getDescription(), maleCount));
		formatSegmentDimensionOut.add(new SegmentDimensionCountOut(GenderEnum.FEMALE.getDescription(), femaleCount));
		formatSegmentDimensionOut.add(new SegmentDimensionCountOut(GenderEnum.UNSURE.getDescription(), otherCount));
		setBaseOut(result, formatSegmentDimensionOut);
		return result;
	}

	@Override
	public BaseOutput segmentProvinceCountList(SegmentCountFilterIn input) {
		
		logger.info("=============================细分管理区域查询开始================================");
		long start = System.currentTimeMillis();
		
		int chinaCount = 0;
		int foreignCount = 0;
		
		//获取所有headId并转换成数据格式
		String[] arrHeadIds = getHeadIds(input);
		
		//获取主数据id集合(并集)
		Long midCount = getMids(arrHeadIds);
		
		//获取各省人数
		Map<String,Integer> provinceCountMap = getProvinceByType(input.getType());
		
		chinaCount = getChinaCountByType(input.getType());
		
		this.clearTempRedisKeys();
		
		List<SegmentDimensionCountOut> formartedProvinceCountOutList = new ArrayList<>();
		
		for(String key:provinceCountMap.keySet()){
			
			SegmentDimensionCountOut validProvinceCountOut = new SegmentDimensionCountOut();
			validProvinceCountOut.setDimensionName(getProvinceName(key));
			validProvinceCountOut.setPopulationCount(provinceCountMap.get(key));
				//chinaCount += provinceCountMap.get(key);
			formartedProvinceCountOutList.add(validProvinceCountOut);
			
		}
		
        Collections.sort(formartedProvinceCountOutList, new Comparator<SegmentDimensionCountOut>() {
            @Override
            public int compare(SegmentDimensionCountOut m1, SegmentDimensionCountOut m2) {
            	
                int m1Count = m1.getPopulationCount();
                int m2Count = m2.getPopulationCount();
                
                if(m1Count > m2Count){
                    return -1;
                }else if(m1Count < m2Count){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
		
		BaseOutput result = newSuccessBaseOutput();

		foreignCount = Integer.valueOf(midCount+"") - chinaCount;
		
		SegmentAreaCountOut segmentAreaCountOut = new SegmentAreaCountOut();
		segmentAreaCountOut.setChinaPopulationCount(chinaCount);
		segmentAreaCountOut.setForeignPopulationCount(foreignCount);
		segmentAreaCountOut.setProvinceList(formartedProvinceCountOutList);
		result.setTotal(1);
		result.getData().add(segmentAreaCountOut);
		long end = System.currentTimeMillis();
		
		logger.info("=============细分管理地区查询结束,用时:"+(end-start)+"毫秒==================================");
		return result;
	}

	@Override
	public BaseOutput segmentReceiveCountList(SegmentCountFilterIn input) {
		BaseOutput result = newSuccessBaseOutput();
		Criteria midCriteria = getDataPartyCriteria(input.getSegmentHeadIds());
		if (midCriteria == null) {
			return result;
		}
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(midCriteria), Aggregation
				.group("receiveCount").count().as("populationCount").first("receiveCount").as("dimensionName"));
		AggregationResults<SegmentDimensionCountOut> aggregationResults = mongoTemplate.aggregate(aggregation,
				DataParty.class, SegmentDimensionCountOut.class);
		List<SegmentDimensionCountOut> receiveCountList = aggregationResults.getMappedResults();
		int[] countArray = new int[5];
		if (!CollectionUtils.isEmpty(receiveCountList)) {
			for (SegmentDimensionCountOut tempSegmentDimensionCountOut : receiveCountList) {
				Integer populationCount = tempSegmentDimensionCountOut.getPopulationCount();
				if (populationCount == null) {
					continue;
				}
				String receiveCountStr = tempSegmentDimensionCountOut.getDimensionName();
				if (StringUtils.hasText(receiveCountStr)) {
					int receiveCount = Integer.parseInt(receiveCountStr);
					if (receiveCount == 1) {
						countArray[0] += populationCount.intValue();
					} else if (receiveCount == 2) {
						countArray[1] += populationCount.intValue();
					} else if (receiveCount == 3) {
						countArray[2] += populationCount.intValue();
					} else if (receiveCount == 4) {
						countArray[3] += populationCount.intValue();
					} else if (receiveCount >= 5) {
						countArray[4] += populationCount.intValue();
					}
				}
			}
		}
		List<SegmentDimensionCountOut> formatedDimensionCountOut = new ArrayList<>();
		for (int i = 0; i < countArray.length; i++) {
			String timesStr = (i + 1) + "次";
			if (i == countArray.length - 1) {
				timesStr = (i + 1) + "+次";
			}
			formatedDimensionCountOut.add(new SegmentDimensionCountOut(timesStr, countArray[i]));
		}
		setBaseOut(result, formatedDimensionCountOut);
		return result;
	}

	private Criteria getDataPartyCriteria(List<Integer> segmentHeadIds) {
		if (CollectionUtils.isEmpty(segmentHeadIds)) {
			return null;
		}
		
		long start = System.currentTimeMillis();
		logger.info("=================================查询细分管理主数据开始================================");

		List<Segment> segmentLists = new ArrayList<Segment>();

		Query query = new Query(Criteria.where("segmentationHeadId").in(segmentHeadIds));

		int totalSize = (int) mongoTemplate.count(query, Segment.class);
		int pageSize = selectBatchSize;
		int totalPages = (totalSize + pageSize - 1) / pageSize;

		logger.info("=========分批查询细分管理主数据共" + totalSize + "条，分为" + totalPages+ "页===============");

		for (int i = 0; i < totalPages; i++) {

			Query queryChild = new Query(Criteria.where("segmentationHeadId").in(segmentHeadIds)).skip(i * pageSize).limit(pageSize);
			List<Segment> segmentList = mongoTemplate.find(queryChild, Segment.class);
			segmentLists.addAll(segmentList);

		}

		long end = System.currentTimeMillis();
		logger.info("=========查询细分管理主数据结束,查询数据总:" + segmentLists.size() + "条,用时:" + (end - start) + "毫秒=================");

		Criteria midCriteria = Criteria.where("mid");
		Set<Integer> dataPartyIdSet = new HashSet<>();
		for (Segment segment : segmentLists) {
			dataPartyIdSet.add(segment.getDataId());
		}
		midCriteria.in(dataPartyIdSet);
		return midCriteria;
	}

	private String getPeopleCount(int idx, List<SegmentFilterCondition> conditions) {
		Query query = new Query();
		Criteria[] criterialist = new Criteria[idx + 1];
		for (int i = 0; i < idx + 1; i++) {
			String tagId = conditions.get(i).getTagId();
			String tagGroupId = conditions.get(i).getTagGroupId();
			// 根据getExclude()拼条件
			if ("1".equals(conditions.get(i).getExclude())) {
				Criteria criteriaCond = null;
				if ("0".equals(tagId)) {// 不限
					criteriaCond = Criteria.where("tagList").elemMatch(Criteria.where("tagId").ne(tagGroupId));
				} else {
					String tagIndex = tagId.substring(tagId.indexOf("_") + 1);

					TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(tagGroupId)),
							TagRecommend.class);
					List<String> tagList = findOne.getTagList();
					String tagValue = tagList.get(Integer.valueOf(tagIndex));

					criteriaCond = Criteria.where("tagList")
							.elemMatch(Criteria.where("tagId").ne(tagGroupId).and("tagValue").ne(tagValue));
				}
				criterialist[i] = criteriaCond;
			} else {
				Criteria criteriaCond = null;
				if ("0".equals(tagId)) {// 不限
					criteriaCond = Criteria.where("tagList").elemMatch(Criteria.where("tagId").is(tagGroupId));
				} else {
					String tagIndex = tagId.substring(tagId.indexOf("_") + 1);
					TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(tagGroupId)),
							TagRecommend.class);
					List<String> tagList = findOne.getTagList();
					String tagValue = tagList.get(Integer.valueOf(tagIndex));

					criteriaCond = Criteria.where("tagList")
							.elemMatch(Criteria.where("tagId").is(tagGroupId).and("tagValue").is(tagValue));
				}
				criterialist[i] = criteriaCond;
			}
		}

		query.addCriteria(new Criteria().andOperator(criterialist));
		long tag_count = mongoTemplate.count(query, DataParty.class);
		return Long.toString(tag_count);
	}

	private BaseOutput newSuccessBaseOutput() {
		return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
	}

	private <O> void setBaseOut(BaseOutput out, List<O> dataList) {
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}

		out.setTotal(dataList.size());
		out.getData().addAll(dataList);
	}

	@Override
	public SegmentFilterOut getSegmentFilterResult(TagGroupsListIn tagGroupsListIn) {
		SegmentFilterOut segmentFilterOut = new SegmentFilterOut(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO);
		SegmentCreUpdateIn segmentCreUpdateIn = new SegmentCreUpdateIn();
		segmentCreUpdateIn.setFilterGroups(tagGroupsListIn.getTagGroupsInList());
		segmentCalcService.calcSegmentCover(segmentCreUpdateIn);
		SegmentRedisVO segmentRedisVO = segmentCalcService.getSegmentRedis();
		//将算出来的数据封装成对象
		segmentFilterOut.setSegmentTotal(segmentRedisVO.getSegmentCoverCount());
		//Todo:需要做验证空值的处理
		if(CollectionUtils.isEmpty(segmentRedisVO.getSegmentGroups())){
			segmentFilterOut.setCode(ApiErrorCode.BIZ_ERROR.getCode());
			segmentFilterOut.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
			return segmentFilterOut;
		}
		for(SegmentGroupRedisVO segmentGroupRedisVO : segmentRedisVO.getSegmentGroups()){
			TagGroupChartOut tagGroupChartOut = new TagGroupChartOut();
			tagGroupChartOut.setGroupId(segmentGroupRedisVO.getGroupId());
			tagGroupChartOut.setGroupName(segmentGroupRedisVO.getGroupName());
			tagGroupChartOut.setGroupChange(segmentGroupRedisVO.getGroupChange());
			tagGroupChartOut.setGroupIndex(segmentGroupRedisVO.getGroupIndex());
			if(CollectionUtils.isEmpty(segmentGroupRedisVO.getTagList())) continue;
			for(SegmentGroupTagRedisVO segmentGroupTagRedisVO : segmentGroupRedisVO.getTagList()){
				TagChartDatas tagChartDatas = new TagChartDatas();
				tagChartDatas.setTagId(segmentGroupTagRedisVO.getTagId());
				tagChartDatas.setTagName(segmentGroupTagRedisVO.getTagName());
				tagChartDatas.setTagCount(segmentGroupTagRedisVO.getFunnelCount().intValue());
				tagGroupChartOut.getChartData().add(tagChartDatas);
			}
			segmentFilterOut.getTagGroupChartOutList().add(tagGroupChartOut);
		}

		return segmentFilterOut;
	}
	private  Map<String, Integer> getProvinceByType(int type){
		
		Map<String,Integer> provinceCountMap = new HashMap<String,Integer>();
		
		List<String> provinceList = getProvinceKeysByType(type);
		
		for(String province : provinceList){
			
			segmentManageCalService.sinterstore(POOL_INDEX,province+"_"+uuid.get(),uuid.get(),province);
			
			tempKeys.get().add(province+"_"+uuid.get());
			Long count = segmentManageCalService.scard(POOL_INDEX, province+"_"+uuid.get());
			
			if(count > 0){
				provinceCountMap.put(province, Integer.valueOf(count+""));
				logger.info("province count==>"+"key="+province+",count="+count);
			}
			
		}

		return provinceCountMap;
		
	}

	/**
	 * 计算中国总人数
	 * @param type
	 * @return
	 */
	private Integer getChinaCountByType(int type){
		
		if(ApiConstant.DATA_PARTY_LOCATION_TYPE.equals(type+"")){
			//用户所在区域
			segmentManageCalService.sinterstore(POOL_INDEX,CHINA_COUNT_KEY+"_"+uuid.get(),TAG_COVER_ID_STR+ApiConstant.DATA_PARTY_LOCATION_TAG_ID,uuid.get());
		} else {
			//用户活动区域
			segmentManageCalService.sinterstore(POOL_INDEX,CHINA_COUNT_KEY+"_"+uuid.get(),TAG_COVER_ID_STR+ApiConstant.DATA_PARTY_ACTIVE_TAG_ID,uuid.get());
		}

		tempKeys.get().add(CHINA_COUNT_KEY+"_"+uuid.get());
		Long chinaCount = segmentManageCalService.scard(POOL_INDEX, CHINA_COUNT_KEY+"_"+uuid.get());
		
		return Integer.valueOf(chinaCount+"");
		
	}
	
	
	/**
	 * 获取性别阀盖人数
	 * @return
	 */
	private  Map<String, Integer> getGender(){
		
		Map<String,Integer> genderCountMap = new HashMap<String,Integer>();
		
		List<String> genderList = getGenderKeys();
		
		for(String genderKey : genderList){
			
			segmentManageCalService.sinterstore(POOL_INDEX,genderKey+"_"+uuid.get(),uuid.get(),genderKey);
			tempKeys.get().add(genderKey+"_"+uuid.get());
			
			Long count = segmentManageCalService.scard(POOL_INDEX, genderKey+"_"+uuid.get());
			if(count > 0){
				genderCountMap.put(genderKey, Integer.valueOf(count+""));
				logger.info("gender count==>"+"key="+genderKey+",count="+count);
			}
		}
		
		return genderCountMap;
		
	}
	
	
	/**
	 * 获取Mid集合(并集)
	 * @param arrHeadIds
	 * @return
	 */
	private Long getMids(String[] arrHeadIds ){
		
	    uuid.set(GenerateUUid.generateShortUuid());
	    tempKeys.set(new ArrayList<String>());
	    tempKeys.get().add(uuid.get());
		segmentManageCalService.sunionstore(POOL_INDEX,uuid.get(),arrHeadIds);
		return segmentManageCalService.scard(POOL_INDEX, uuid.get());
	}
	
	/**
	 * 转换headids为数组类型
	 * @param input
	 * @return
	 */
	private String[] getHeadIds(SegmentCountFilterIn input){
		
		List<String> headIds = new ArrayList<String>();
		
		for(Integer headid : input.getSegmentHeadIds()){
			
			headIds.add("segmentcoverid:"+headid);
		}
		
		return (String[]) headIds.toArray(new String[headIds.size()]);
		
	}
	
	/**
	 * 根据类型获取省份keys
	 * @param type
	 * @return
	 */
	private List<String> getProvinceKeysByType(int type){
		
		List<String> provinceKeysList = new ArrayList<String>();
		
		TagValueCount tagValueCount = new TagValueCount();
		
		if(ApiConstant.DATA_PARTY_LOCATION_TYPE.equals(type+"")){
			tagValueCount.setTagId(ApiConstant.DATA_PARTY_LOCATION_TAG_ID);
		} else {
			tagValueCount.setTagId(ApiConstant.DATA_PARTY_ACTIVE_TAG_ID);
		}
		
		tagValueCount.setIsTag("0");
		tagValueCount.setStartIndex(null);
		tagValueCount.setPageSize(null);
		List<TagValueCount> tagValueCountList = tagValueCountDao.selectList(tagValueCount);
		
		if(tagValueCountList != null && tagValueCountList.size() > 0){
			
			for(TagValueCount tagValue : tagValueCountList){
				provinceKeysList.add(TAG_COVER_ID_STR+tagValue.getTagValueSeq());
			}
			
		}
		
		return provinceKeysList;
	}
	
	/**
	 * 获取性别keys
	 * @return
	 */
	private List<String> getGenderKeys(){
		
		List<String> genderList = new ArrayList<String>();
		
		genderList.add(ApiConstant.GENDER_TAG_KEY_MALE);
		genderList.add(ApiConstant.GENDER_TAG_KEY_FEMALE);
		
		return genderList;
	}
	
	/**
	 * 根据redis省份key获取省份名称
	 * @param provinceKey
	 * @return
	 */
	private String getProvinceName(String provinceKey){
		
		int start = provinceKey.lastIndexOf(":") + 1;
		String tagValue = "";
		String tagValueSeq = provinceKey.substring(start);
		
		TagValueCount tagValueCount = new TagValueCount();
		
		tagValueCount.setTagValueSeq(tagValueSeq);
		
		List<TagValueCount> tagValueCountList = tagValueCountDao.selectList(tagValueCount);
		
		if(tagValueCountList != null && tagValueCountList.size() > 0){
			
			tagValue = tagValueCountList.get(0).getTagValue();
			
		}
		
		return getShortProvinceName(tagValue);
		
	}

    /**
     * 清除临时产生的redis数据
     */
    private void clearTempRedisKeys() {

    	if(tempKeys.get()!=null&&tempKeys.get().size()>0) {
			String[] keys=new String[tempKeys.get().size()];
		            keys=tempKeys.get().toArray(keys);
				segmentManageCalService.deleteTempKey(POOL_INDEX, keys);
				logger.info("calculate segment management over  ,delete temporary keys:");
				
				StringBuffer sb = new StringBuffer();
				
				for(String key :tempKeys.get()){
					sb.append(key+";");
				}
				
				logger.info("=========================>"+sb.toString()+"=====================");
				
		        tempKeys.get().clear();
		}

    }
    
    /**
     * 获取对应关系(细分管理页面显示用)
     * @param provinceName
     * @return
     */
    private String getShortProvinceName(String provinceName){
    	
    	if(SegmentProvinceMap.S_110000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_110000.getShortName();
    	}else if(SegmentProvinceMap.S_120000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_120000.getShortName();
    	}else if(SegmentProvinceMap.S_130000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_130000.getShortName();
    	}else if(SegmentProvinceMap.S_140000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_140000.getShortName();
    	}else if(SegmentProvinceMap.S_150000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_150000.getShortName();
    	}else if(SegmentProvinceMap.S_210000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_210000.getShortName();
    	}else if(SegmentProvinceMap.S_220000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_220000.getShortName();
    	}else if(SegmentProvinceMap.S_230000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_230000.getShortName();
    	}else if(SegmentProvinceMap.S_310000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_310000.getShortName();
    	}else if(SegmentProvinceMap.S_320000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_320000.getShortName();
    	}else if(SegmentProvinceMap.S_330000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_330000.getShortName();
    	}else if(SegmentProvinceMap.S_340000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_340000.getShortName();
    	}else if(SegmentProvinceMap.S_350000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_350000.getShortName();
    	}else if(SegmentProvinceMap.S_360000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_360000.getShortName();
    	}else if(SegmentProvinceMap.S_370000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_370000.getShortName();
    	}else if(SegmentProvinceMap.S_410000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_410000.getShortName();
    	}else if(SegmentProvinceMap.S_420000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_420000.getShortName();
    	}else if(SegmentProvinceMap.S_430000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_430000.getShortName();
    	}else if(SegmentProvinceMap.S_440000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_440000.getShortName();
    	}else if(SegmentProvinceMap.S_450000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_450000.getShortName();
    	}else if(SegmentProvinceMap.S_460000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_460000.getShortName();
    	}else if(SegmentProvinceMap.S_500000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_500000.getShortName();
    	}else if(SegmentProvinceMap.S_510000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_510000.getShortName();
    	}else if(SegmentProvinceMap.S_520000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_520000.getShortName();
    	}else if(SegmentProvinceMap.S_530000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_530000.getShortName();
    	}else if(SegmentProvinceMap.S_540000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_540000.getShortName();
    	}else if(SegmentProvinceMap.S_610000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_610000.getShortName();
    	}else if(SegmentProvinceMap.S_620000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_620000.getShortName();
    	}else if(SegmentProvinceMap.S_630000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_630000.getShortName();
    	}else if(SegmentProvinceMap.S_640000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_640000.getShortName();
    	}else if(SegmentProvinceMap.S_650000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_650000.getShortName();
    	}else if(SegmentProvinceMap.S_710000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_710000.getShortName();
    	}else if(SegmentProvinceMap.S_810000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_820000.getShortName();
    	}else if(SegmentProvinceMap.S_820000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_820000.getShortName();
    	}
    	
    	return null;
    }
}
