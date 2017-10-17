package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SegmentProvinceMap;
import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.service.SegmentAudienctAnalysisService;
import cn.rongcapital.mkt.service.SegmentManageCalService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentAnalysisCountData;
import cn.rongcapital.mkt.vo.out.SegmentAudienctAnalysisDataOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


/**
 * Created by ljk on 2016-12-05.
 */
@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class SegmentAudienctAnalysisServiceImpl implements SegmentAudienctAnalysisService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TagValueCountDao tagValueCountDao;
	
	@Autowired
	private SegmentManageCalService segmentManageCalService;

	@Autowired
	Environment env;
	
	public static final  Integer POOL_INDEX = 2;
	
	public static final String TAG_COVER_ID_STR="tagcoverid:";
	
	public static final String SEGMENT_COVER_ID_STR="segmentcoverid:";
	
	public static final String ACTIVE_OTHER = "4HAcjGd5_34";//所在区域其他tagValueId
	public static final String LOCATION_OTHER = "8HARIs7F_34";//订单区域其他tagValueId
	
    private ThreadLocal<String> uuid=new ThreadLocal<String>();    
	
	@Override
	public BaseOutput getSegmentAudienctAnalysis(String tagId, Integer headId) {
		
		String showMapTagIds = env.getProperty("segment.analysis.show.map");
		
		String[] arrTagIds = showMapTagIds.split("-");
		
		logger.info("===================================segment audienct analysis start ,tagId:"+tagId +",segmentHeadId:"+headId);
		
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
		
    	//中国人口数量
        Integer chinaPopulationCount = 0;
        //其他及未知区域
        Integer foreignPopulationCount = 0;
        
		SegmentAudienctAnalysisDataOut segmentAudienctAnalysisDataOut = new SegmentAudienctAnalysisDataOut();
		//设置redis 临时key
	    uuid.set(GenerateUUid.generateShortUuid());
		
		//1.判断是否显示地图 1:饼图 2:地图
		String showType = ApiConstant.SEGMENT_SHOW_PIE;
	    
		for(String showMapTagId : arrTagIds){
			
			if(showMapTagId.equals(tagId)){
				showType = ApiConstant.SEGMENT_SHOW_MAP;
				break;
			}
		}
		
//		if(ApiConstant.DATA_PARTY_LOCATION_TAG_ID.equals(tagId) || ApiConstant.DATA_PARTY_ACTIVE_TAG_ID.equals(tagId)){
//			
//			showType = ApiConstant.SEGMENT_SHOW_MAP;
//		}
		logger.info("===================================segment audienct analysis ,show type" + showType);
		segmentAudienctAnalysisDataOut.setShowType(showType);
		
		//2.根据tagId获取所有tagValueId
		TagValueCount tagValueCount = new TagValueCount();
		tagValueCount.setTagId(tagId);
		tagValueCount.setIsTag(ApiConstant.IS_TAG_VALUE);
		tagValueCount.setStartIndex(null);
		tagValueCount.setPageSize(null);
		List<TagValueCount> tagValueCountList = tagValueCountDao.selectList(tagValueCount);
		
		//3.计算redis中tagcoverid 与 segmentcoverId 交集获取标签人数
		List<SegmentAnalysisCountData> SegmentAnalysisCountDataList = new ArrayList<SegmentAnalysisCountData>();
		
		SegmentAnalysisCountData segmentAnalysisCountData;
		
		//显示地图时,计算中国人口用
		List<String> sinterIds = new ArrayList<String>();
		
		//4.循环计算标签值覆盖人数
		for(TagValueCount tagvalueCount : tagValueCountList){
			
			segmentManageCalService.sinterstore(POOL_INDEX, uuid.get() ,TAG_COVER_ID_STR+tagvalueCount.getTagValueSeq(), SEGMENT_COVER_ID_STR+headId);
			Long count = segmentManageCalService.scard(POOL_INDEX, uuid.get());
			
			if(ACTIVE_OTHER.equals(tagvalueCount.getTagValueSeq()) || LOCATION_OTHER.equals(tagvalueCount.getTagValueSeq())){
				foreignPopulationCount = count.intValue();
				continue;
			}
				
			segmentAnalysisCountData = new SegmentAnalysisCountData();
			segmentAnalysisCountData.setTagvalueId(tagvalueCount.getTagValueSeq());
				
			if(ApiConstant.SEGMENT_SHOW_MAP.equals(showType)){
				
				//保存省份标签值Id
				sinterIds.add(TAG_COVER_ID_STR+tagvalueCount.getTagValueSeq());
				
				//获取省份简称保存
				segmentAnalysisCountData.setTagvalueName(getShortProvinceName(tagvalueCount.getTagValue()));
			}else{
				segmentAnalysisCountData.setTagvalueName(tagvalueCount.getTagValue());
			}
			
			//标签值覆盖人数
			segmentAnalysisCountData.setTagvalueCount(count.intValue());
			SegmentAnalysisCountDataList.add(segmentAnalysisCountData);


		}
		
		//5.排序
        Collections.sort(SegmentAnalysisCountDataList, new Comparator<SegmentAnalysisCountData>() {
            @Override
            public int compare(SegmentAnalysisCountData m1, SegmentAnalysisCountData m2) {
            	
                int m1Count = m1.getTagvalueCount();
                int m2Count = m2.getTagvalueCount();
                
                if(m1Count > m2Count){
                    return -1;
                }else if(m1Count < m2Count){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
		
        //6.保存覆盖人数数据
        segmentAudienctAnalysisDataOut.setPopulationCount(SegmentAnalysisCountDataList);
        
        //显示地图时计算中国人数
		if(ApiConstant.SEGMENT_SHOW_MAP.equals(showType)){
			
			segmentManageCalService.sunionstore(POOL_INDEX, uuid.get(),(String[]) sinterIds.toArray(new String[sinterIds.size()]));
			segmentManageCalService.sinterstore(POOL_INDEX, uuid.get(),uuid.get(),SEGMENT_COVER_ID_STR+headId);
			chinaPopulationCount = segmentManageCalService.scard(POOL_INDEX, uuid.get()).intValue();
		}
		
		segmentAudienctAnalysisDataOut.setChinaPopulationCount(chinaPopulationCount);
		segmentAudienctAnalysisDataOut.setForeignPopulationCount(foreignPopulationCount);
		
		baseOutput.getData().add(segmentAudienctAnalysisDataOut);
		
		//清楚redis 临时key
		segmentManageCalService.deleteTempKey(POOL_INDEX, uuid.get());
			
		logger.info("===================================segment audienct analysis end ,tagId:"+tagId +",segmentHeadId:"+headId);
		
		return baseOutput;
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
    		return SegmentProvinceMap.S_810000.getShortName();
    	}else if(SegmentProvinceMap.S_820000.getProvinceName().equals(provinceName)){
    		return SegmentProvinceMap.S_820000.getShortName();
    	}else{
    		return provinceName;
    	}
    	
    }
	
}
