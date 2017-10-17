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
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.ListSplit;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;

/**
 * Created by ethan on 16/6/30.
 */
@Service
public class DataMemberToDataPartyImpl extends AbstractDataPartySyncService<Integer> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final int THREAD_POOL_FIX_SIZE = 100;
	
	private static final int BATCH_SIZE = 500;
	
	@Autowired
	private DataMemberDao dataMemberDao;
	
	private ExecutorService executor = null;

	@Override
	public int queryTotalCount() {
		DataMember dataMember = new DataMember();
		dataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());
		return dataMemberDao.selectListCount(dataMember);
	}

	@Override
	public void querySyncData(Integer totalSize, Integer pageSize) {

		executor = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);
		
    	logger.info("=====================同步member到主数据开始");
    	long startTime = System.currentTimeMillis();
		
		List<DataMember> dataMemberLists = new ArrayList<DataMember>();
		
		DataMember dataMember = new DataMember();
		dataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());
		
		int totalPages = (totalSize + pageSize - 1) / pageSize;
		
		for (int i = 0; i < totalPages; i++) {
			
			dataMember.setPageSize(pageSize);
			dataMember.setStartIndex(Integer.valueOf(i * pageSize));
			List<DataMember> dataMemberList = dataMemberDao.selectList(dataMember);
			
			dataMemberLists.addAll(dataMemberList);
			
		}

		logger.info("===================获取数据结束,开始同步.");
		
		//获取目前最大Id以便检验重复时使用
		Integer maxId = dataPartyDao.getMaxId();
		maxId = maxId == null ? 0 : maxId;
//		String bitmap = dataMemberLists.get(0).getBitmap();
//		int keySize = getKeySizeByBitmap(bitmap);
		
		Set<String> bitmapList = new HashSet<String>();
		
		List<List<DataMember>> dataMembersList = ListSplit.getListSplit(dataMemberLists, BATCH_SIZE);
	    
	    for(List<DataMember> dataMembers :dataMembersList){
	    	
			List<Integer> idList = new ArrayList<>(dataMemberLists.size());
			for (DataMember dataObj : dataMembers) {

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
    	  
        		logger.info("======================重复数据"+repeatDatas.size()+"组");
    	  
        		if(repeatDatas != null && repeatDatas.size() > 0){
        			for(Map<String, Object> repeatData : repeatDatas){
        				List<Integer> repeatIds = getIdsByRepeatByBitmapKeys(repeatData,keySize,bitmap);
    			 
        				if(repeatIds == null){
        					continue;
        				}
        			
        				Integer id = distinctData(repeatIds);
    			 
        				for(Integer repeatId : repeatIds){
    				 
//    						logger.info("==================repeatId:"+repeatId);
    				 
        					Map<String,Object> paraMap = new HashMap<String,Object>();
        					paraMap.put("newkeyId",id);
        					paraMap.put("oldkeyId", repeatId);
        					dataMemberDao.updateDataMemberKeyid(paraMap);
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
		dataMemberDao.updateStatusByIds(dataPartySyncVO.getExtendDataList(), StatusEnum.PROCESSED.getStatusCode());

	}
	
	public void updateKeyidByid(Integer keyid, Integer id) {
		DataMember keyidObj = new DataMember();
		keyidObj.setId(id);
		keyidObj.setKeyid(keyid);
		dataMemberDao.updateById(keyidObj);
	}
	
	/**
	 * 校验主键是否为空
	 * @param dataObj
	 * @return
	 */
	private boolean checkBitKey(DataMember dataObj){
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
	
	private void createParty(DataMember dataObj){
		
		String bitmap = dataObj.getBitmap();

		Integer keyid = super.getDataParyPrimaryKey(dataObj, bitmap);
		if (keyid != null) {
			this.updateKeyidByid(keyid, dataObj.getId());
		} else {
			DataParty dataParty = new DataParty();
			dataParty.setMemberLevel(dataObj.getMemberLevel());
			dataParty.setMemberPoints(dataObj.getMemberPoints());
			// dataParty.setMobile(dataObj.getMobile());
			// dataParty.setMappingKeyid(dataObj.getId().toString());
			// dataParty.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
			dataParty.setMdType(DataTypeEnum.MEMBER.getCode());
			dataParty.setSource(dataObj.getSource());
			dataParty.setBatchId(dataObj.getBatchId());

			dataParty = super.getDataParyKey(dataParty, dataObj, bitmap);

			dataPartyDao.insert(dataParty);
			this.updateKeyidByid(dataParty.getId(), dataObj.getId());

			// dataPartyList.add(dataParty);
		}
		
	}
}
