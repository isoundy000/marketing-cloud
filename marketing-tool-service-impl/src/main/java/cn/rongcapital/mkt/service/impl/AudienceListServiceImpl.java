/*************************************************
 * @功能简述: AudienceListService实现类
 * @see: MkyApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/6/6 
*************************************************/


package cn.rongcapital.mkt.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.AudienceColumnsDao;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.SmsTaskTargetAudienceCacheDao;
import cn.rongcapital.mkt.po.AudienceColumns;
import cn.rongcapital.mkt.po.AudienceCount;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.service.AudienceListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ColumnsOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceListServiceImpl implements AudienceListService {
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	AudienceListDao audienceListDao;
	
	@Autowired
	AudienceColumnsDao audienceColumnsDao;
	
	@Autowired
	SmsTaskTargetAudienceCacheDao smsTaskTargetAudienceCacheDao;
	
	@Autowired
	AudienceListPartyMapDao audienceListPartyMapDao;
	
	private static final String ORDER_BY_FIELD_NAME = "field_order";//排序的字段名
	
	private ExecutorService executor = null;

	private static final int THREAD_POOL_FIX_SIZE = 30;

	private static final int BATCH_SIZE = 50;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput audienceList(String userToken,Integer size,Integer index) {
		
		AudienceList param = new AudienceList();
		param.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int totalCount = audienceListDao.selectListCount(param);
		param.setPageSize(size);
		param.setStartIndex((index-1)*size);
		param.setOrderField("create_time");
		param.setOrderFieldType("desc");
		
		List<AudienceList> reList = audienceListDao.selectList(param);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if (CollectionUtils.isNotEmpty(reList)) {
			result.setTotal(reList.size());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (AudienceList s : reList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("audience_list_id", s.getId());
				map.put("audience_list_name", s.getAudienceName());
				map.put("audience_count", s.getAudienceRows());
				map.put("source_name", s.getSource());
				map.put("create_time", df.format(s.getCreateTime()));
				result.getData().add(map);
			}
		}
		
		//查询页面表格的列名
		AudienceColumns audienceColumns = new AudienceColumns();
		audienceColumns.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		audienceColumns.setOrderField(ORDER_BY_FIELD_NAME);
		List<AudienceColumns> audienceColumnsList = audienceColumnsDao.selectList(audienceColumns);
		List<Object> columnsOutList = new ArrayList<Object>(); 
		if(CollectionUtils.isNotEmpty(audienceColumnsList)) {
			for(AudienceColumns ac:audienceColumnsList) {
				ColumnsOut columnsOut = new ColumnsOut();
				columnsOut.setColCode(ac.getFieldCode());
				columnsOut.setColName(ac.getFieldName());
				columnsOutList.add(columnsOut);
			}
		}
		result.setColNames(columnsOutList);
		result.setTotal(result.getData().size());
		result.setTotalCount(totalCount);
		return result;
	}

    @Override
    public BaseOutput getAudienceByListId(String userToken, Integer audienceId, Integer index, Integer size) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        AudienceList audienceListParam = new AudienceList();
        audienceListParam.setId(audienceId);
        List<AudienceList> audienceLists = audienceListDao.selectList(audienceListParam);
        if (CollectionUtils.isEmpty(audienceLists)) {
            return result;
        }

        audienceListParam = audienceLists.get(0);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("audience_list_id", audienceListParam.getId());
        resultMap.put("audience_list_name", audienceListParam.getAudienceName());
        resultMap.put("audience_count", audienceListParam.getAudienceRows());
        resultMap.put("source_name", audienceListParam.getSource());
        resultMap.put("create_time", df.format(audienceListParam.getCreateTime()));

        result.getData().add(resultMap);
        return result;
    }

	@Override
	public BaseOutput audienceCount(String userToken) {
		AudienceList param = new AudienceList();
		param.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        AudienceCount audienceCount = audienceListDao.selectAudienceCount(param);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                                                  ApiConstant.INT_ZERO, null);

        result.getData().add(audienceCount);

		return result;
	}

    @Override
    public boolean saveAudienceByMobile(Long taskHeadId, List<String> mobileList,
                    String audienceName) {
        if(taskHeadId == null || CollectionUtils.isEmpty(mobileList) || StringUtils.isEmpty(audienceName)){
            logger.error("parameter is not valid!");
            return false;
        }
        executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
        List<Future<List<Long>>> resultList = new ArrayList<>();
        List<List<String>> mobileListBatch = ListSplit.getListSplit(mobileList, BATCH_SIZE);
        try {
            for (List<String> mobileListChild : mobileListBatch) {
                Future<List<Long>> dataIdFutureList = executor.submit(new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() throws Exception {
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("list", mobileListChild);
                        paramMap.put("taskHeadId", taskHeadId);
                        List<Long> dataIdList = smsTaskTargetAudienceCacheDao.selectListByMobile(paramMap);
                        return dataIdList;
                    }
                });
                resultList.add(dataIdFutureList);
            }
            executor.shutdown();
            // 设置最大阻塞时间，所有线程任务执行完成再继续往下执行
            executor.awaitTermination(24, TimeUnit.HOURS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        Map<String,Object> paramMap = null;
        List<Long> dataIdlistAll = new ArrayList<>();
       // 遍历任务的结果
        if(CollectionUtils.isNotEmpty(resultList)){
            for (Future<List<Long>> fs : resultList) {
                try {
                    // 打印各个线程（任务）执行的结果
                    List<Long> dataIdlist = fs.get();
                    dataIdlistAll.addAll(dataIdlist);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    return false;
                } catch (ExecutionException e) {
                    executor.shutdownNow();
                    logger.error(e.getMessage());
                    return false;
                }
            }
        }
        
        logger.info("dataIdlist size is {}", dataIdlistAll.size());
        //保存到人群
        AudienceList audience = new AudienceList();
        audience.setAudienceName(audienceName);
        audience.setAudienceRows(dataIdlistAll.size());
        audience.setSource("优惠码");
        audience.setCreateTime(new Date());
        audienceListDao.insert(audience);
        //保存到map
        List<Map<String,Object>> paramInsertLists = new ArrayList<>();
        for(Long Keyid : dataIdlistAll){
            if(Keyid != null){
                paramMap = new HashMap<>();
                paramMap.put("audience_list_id", audience.getId());
                paramMap.put("party_id", Keyid);
                paramMap.put("create_time", new Date());
                paramInsertLists.add(paramMap);
            }
        }
        int batchInsert = audienceListPartyMapDao.batchInsert(paramInsertLists);
        logger.info("success insert audiencelistpartymap count {}", batchInsert);
        return true;
    }

}
