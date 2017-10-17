package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataCustomerTagToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
    private static final int THREAD_POOL_FIX_SIZE = 100;
    
    private static final int BATCH_SIZE = 500;
    
	@Autowired
	private DataCustomerTagsDao dataCustomerTagsDao;
	
	private ExecutorService executor = null;

	@Override
	public int queryTotalCount() {
		DataCustomerTags dataCustomerTags = new DataCustomerTags();
		dataCustomerTags.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
		return dataCustomerTagsDao.selectListCount(dataCustomerTags);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {

		executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
		
    	logger.info("=====================同步customerTags到主数据开始");
    	long startTime = System.currentTimeMillis();
		
		List<DataCustomerTags> dataCustomerTagsLists = new ArrayList<DataCustomerTags>();
		
		DataCustomerTags dataCustomerTags = new DataCustomerTags();
		dataCustomerTags.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataCustomerTags.setPageSize(pageSize);
			dataCustomerTags.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataCustomerTags> dataCustomerTagsList = dataCustomerTagsDao.selectList(dataCustomerTags);
			
			dataCustomerTagsLists.addAll(dataCustomerTagsList);
			
		}

		logger.info("===================获取数据结束,开始同步.");
		
		//获取目前最大Id以便检验重复时使用
		Integer maxId = dataPartyDao.getMaxId();
		maxId = maxId == null ? 0 : maxId;
//		String bitmap = dataCustomerTagsLists.get(0).getBitmap();
//		int keySize = getKeySizeByBitmap(bitmap);
		
		Set<String> bitmapList = new HashSet<String>();
		
		List<List<DataCustomerTags>> dataCustomerTagssList = ListSplit.getListSplit(dataCustomerTagsLists, BATCH_SIZE);
	    
	    for(List<DataCustomerTags> dataCustomerTagss :dataCustomerTagssList){
	    	
			List<Integer> idList = new ArrayList<>(dataCustomerTagsLists.size());
			for (DataCustomerTags dataObj : dataCustomerTagss) {

	            executor.submit(new Callable<Void>() {

	    			@Override
	    			public Void call() throws Exception {
	    				
    					if(!checkBitKey(dataObj)){
    						bitmapList.add(dataObj.getBitmap());
    						createParty(dataObj);
    					}
	    				
	    				return null;
	    			}
	    			
	            });
				
				idList.add(dataObj.getId());
			}
	    	
			DataPartySyncVO<Integer> dataPartySyncVO = new DataPartySyncVO<>();
			dataPartySyncVO.setExtendDataList(idList);
			doSyncAfter(dataPartySyncVO);
	    }
	    
        executor.shutdown();
      try {
    	  //设置最大阻塞时间，所有线程任务执行完成再继续往下执行
    	  executor.awaitTermination(24, TimeUnit.HOURS);
    	  
    	  logger.info("======================校验重复数据==================== ");
    	  
      	Iterator<String> it = bitmapList.iterator();  
      	while (it.hasNext()) {  
      	 
      		String bitmap = it.next();  
      	  
      		int keySize = getKeySizeByBitmap(bitmap);
      		
      		List<Map<String, Object>> repeatDatas = checkData(bitmap, maxId);
    	  
      		logger.info("======================重复数据"+repeatDatas.size()+"组,开始处理重复数据==========");
    	  
      		if(repeatDatas != null && repeatDatas.size() > 0){
      			for(Map<String, Object> repeatData : repeatDatas){
      				List<Integer> repeatIds = getIdsByRepeatByBitmapKeys(repeatData, keySize,bitmap);
    			 
      				if(repeatIds == null){
      					continue;
      				}
     			
      				Integer id = distinctData(repeatIds);
    			 
      				for(Integer repeatId : repeatIds){
    				 
//    					logger.info("==================repeatId:"+repeatId);
    				 
      					Map<String,Object> paraMap = new HashMap<String,Object>();
      					paraMap.put("newkeyId",id);
      					paraMap.put("oldkeyId", repeatId);
      					dataCustomerTagsDao.updateDataCustomerTagsKeyid(paraMap);
      				}
      			}
    		 }
    	  }
    	  
    	  logger.info("==========处理重复数据结束====================");
    	  
    	  long endTime = System.currentTimeMillis();
    	  
    	  logger.info("=====================同步购物记录到主数据结束,用时"+ (endTime-startTime) + "毫秒" );
    	  
      } catch (InterruptedException e) {
    	  logger.info("======================同步购物记录到主数据超时" );
      }
	    
		
	}

	@Override
	public void doSyncAfter(DataPartySyncVO<Integer> dataPartySyncVO) {
		dataCustomerTagsDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(),
				StatusEnum.PROCESSED.getStatusCode());

	}
	
	public void updateKeyidByid(Integer keyid, Integer id) {
		DataCustomerTags keyidObj = new DataCustomerTags();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataCustomerTagsDao.updateById(keyidObj);
	}
	
	/**
	 * 校验主键是否为空
	 * @param dataObj
	 * @return
	 */
	private boolean checkBitKey(DataCustomerTags dataObj){
		String bitmap = dataObj.getBitmap();
		
		if (StringUtils.isNotBlank(bitmap)) {
			try {
				// 获取keyid
				List<String> strlist = this.getAvailableKeyid(bitmap);
				
				return checkBitKeyByType(strlist, dataObj);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	private void createParty(DataCustomerTags dataObj){
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {

			DataParty dataParty = new DataParty();
			dataParty.setMdType(DataTypeEnum.CUSTOMER_TAGS.getCode());
			dataParty.setSource(dataObj.getSource());
			dataParty.setBatchId(dataObj.getBatchId());
			dataParty.setMobile(dataObj.getMobile());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

		}
		
	}
}
