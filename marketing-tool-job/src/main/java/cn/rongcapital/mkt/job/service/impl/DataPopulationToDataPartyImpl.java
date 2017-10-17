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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.DataShopping;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataPopulationToDataPartyImpl extends AbstractDataPartySyncService<Integer> {
	
    private static final int THREAD_POOL_FIX_SIZE = 100;
    
    private static final int BATCH_SIZE = 500;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataPopulationDao dataPopulationDao;

	private ExecutorService executor = null;
	
	@Override
	public int queryTotalCount() {
		DataPopulation dataPopulation = new DataPopulation();
		dataPopulation.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataPopulationDao.selectListCount(dataPopulation);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {

		executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
		
    	logger.info("=====================同步人口属性到主数据开始");
    	long startTime = System.currentTimeMillis();
    	
		List<DataPopulation> dataPopulationLists = new ArrayList<DataPopulation>();
		
		DataPopulation dataPopulation = new DataPopulation();
		dataPopulation.setStatus(StatusEnum.ACTIVE.getStatusCode());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		//防止数据量过大，分页查询出全部待同步数据
		for (int i = 0; i < totalPages; i++) {
			
			dataPopulation.setPageSize(pageSize);
			dataPopulation.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataPopulation> dataPopulationList = dataPopulationDao.selectList(dataPopulation);
			
			dataPopulationLists.addAll(dataPopulationList);
			
		}
		
		logger.info("===================获取数据结束,开始同步.");
		
		//获取目前最大Id以便检验重复时使用
		Integer maxId = dataPartyDao.getMaxId();
		maxId = maxId == null ? 0 : maxId;
		//String bitmap = dataPopulationLists.get(0).getBitmap();
		//int keySize = getKeySizeByBitmap(bitmap);
		
		Set<String> bitmapList = new HashSet<String>();
		
		//拆分总总数据，分批同步
		List<List<DataPopulation>> dataPopulationsList = ListSplit.getListSplit(dataPopulationLists, BATCH_SIZE);
	    
	    for(List<DataPopulation> dataPopulations : dataPopulationsList){
	    	
            executor.submit(new Callable<Void>() {

    			@Override
    			public Void call() throws Exception {
    				
    				List<Integer> idList = new ArrayList<>(dataPopulationLists.size());
    				
    				for (DataPopulation dataObj : dataPopulations) {
	    				
    					if(!checkBitKey(dataObj)){
    						
    						bitmapList.add(dataObj.getBitmap());
    						
    						createParty(dataObj);
    					}
	    				
	    				idList.add(dataObj.getId());
	    				
    				}
    				
    				DataPartySyncVO<Integer> dataPartySyncVO = new DataPartySyncVO<>();
    				dataPartySyncVO.setExtendDataList(idList);
    				doSyncAfter(dataPartySyncVO);
    				
    				return null;
    			}
    			
            });
	    	
	    }
		
        executor.shutdown();
        try {
        	//设置最大阻塞时间，所有线程任务执行完成再继续往下执行
        	executor.awaitTermination(24, TimeUnit.HOURS);
    	  
        	logger.info("======================校验重复数据====================校验开始id= "+maxId);
    	  
        	Iterator<String> it = bitmapList.iterator();  
        	while (it.hasNext()) {  
        	 
        		String bitmap = it.next();  
        	  
        		int keySize = getKeySizeByBitmap(bitmap);
        	  
             	List<Map<String, Object>> repeatDatas = checkData(bitmap, maxId);
          	  
            	logger.info("======================重复数据"+repeatDatas.size()+"组");
        	  
            	if(repeatDatas != null && repeatDatas.size() > 0){
            		for(Map<String, Object> repeatData : repeatDatas){
            			List<Integer> repeatIds = getIdsByRepeatByBitmapKeys(repeatData,keySize,bitmap);
        			 
            			if(repeatIds == null){
            				continue;
            			}
            			
            			Integer id = distinctData(repeatIds);
        			 
            			for(Integer repeatId : repeatIds){
        				 
        					logger.info("==================repeatId:"+repeatId);
        				 
            				Map<String,Object> paraMap = new HashMap<String,Object>();
        					paraMap.put("newkeyId",id);
        					paraMap.put("oldkeyId", repeatId);
            				dataPopulationDao.updateDataPopulationKeyid(paraMap);
            			}
            			
            		}
            	}
        	  
        	} 
    	  
        logger.info("==========处理重复数据结束====================");
    	long endTime = System.currentTimeMillis();
    	logger.info("=====================同步人口属性到主数据结束,用时"+ (endTime-startTime) + "毫秒" );
    	  
        } catch (InterruptedException e) {
        	logger.info("======================同步人口属性到主数据超时" );
        }
	}

	@Override
	public void doSyncAfter(DataPartySyncVO<Integer> dataPartySyncVO) {
		dataPopulationDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());
	}

	public void updateKeyidByid(Integer keyid, Integer id) {
		DataPopulation keyidObj = new DataPopulation();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataPopulationDao.updateById(keyidObj);
	}
	
	/**
	 * 校验主键是否为空
	 * @param dataObj
	 * @return
	 */
	private boolean checkBitKey(DataPopulation dataObj){
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
	
	private void createParty(DataPopulation dataObj){
		
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			DataParty dataParty = new DataParty();
			BeanUtils.copyProperties(dataObj, dataParty);
			dataParty.setMdType(DataTypeEnum.POPULATION.getCode());
			dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
//			dataParty.setMobile(dataObj.getMobile());
//			dataParty.setName(dataObj.getName());
//			dataParty.setGender(dataObj.getGender());
//			dataParty.setBirthday(dataObj.getBirthday());
//			dataParty.setCitizenship(dataObj.getCitizenship());
//			dataParty.setProvice(dataObj.getProvice());
//			dataParty.setCity(dataObj.getCity());
//			dataParty.setJob(dataObj.getJob());
//			dataParty.setMonthlyIncome(dataObj.getMonthlyIncome());
//			dataParty.setMonthlyConsume(dataObj.getMonthlyConsume());
//			dataParty.setSource(dataObj.getSource());
//			dataParty.setBatchId(dataObj.getBatchId());
			dataParty.setId(keyid);
			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);
			dataPartyDao.updateById(dataParty);
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {

			DataParty dataParty = new DataParty();
			BeanUtils.copyProperties(dataObj, dataParty);
			dataParty.setMappingKeyid(dataObj.getId().toString());
			dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
			dataParty.setMdType(DataTypeEnum.POPULATION.getCode());
//			dataParty.setMobile(dataObj.getMobile());
//			dataParty.setName(dataObj.getName());
//			dataParty.setGender(dataObj.getGender());
//			dataParty.setBirthday(dataObj.getBirthday());
//			dataParty.setCitizenship(dataObj.getCitizenship());
//			dataParty.setProvice(dataObj.getProvice());
//			dataParty.setCity(dataObj.getCity());
//			dataParty.setJob(dataObj.getJob());
//			dataParty.setMonthlyIncome(dataObj.getMonthlyIncome());
//			dataParty.setMonthlyConsume(dataObj.getMonthlyConsume());
//			dataParty.setSource(dataObj.getSource());
//			dataParty.setBatchId(dataObj.getBatchId());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

			// dataPartyList.add(dataParty);
		}
		
	}
}
