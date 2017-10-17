package cn.rongcapital.mkt.job.service.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.KeyidMapBlockDao;
import cn.rongcapital.mkt.job.service.DataPartySyncService;
import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataShopping;
import cn.rongcapital.mkt.po.KeyidMapBlock;

/**
 * Created by ethan on 16/6/30.
 */
@PropertySource("classpath:${conf.dir}/application-api.properties")
public abstract class AbstractDataPartySyncService<T> implements DataPartySyncService<T> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	Environment env;
	
	//protected int BATCH_SIZE = Integer.valueOf(env.getProperty("data.to.party.batch.size"));

	protected static Integer MD_TYPE = Integer.valueOf(0);

	@Autowired
	protected DataPartyDao dataPartyDao;

	@Autowired
	private KeyidMapBlockDao keyidMapBlockDao;

	@Override
	public void doSync() {
		int totalCount = this.queryTotalCount();
		if (totalCount < 1) {
			return;
		}

		int BATCH_SIZE = Integer.valueOf(env.getProperty("data.to.party.batch.size"));
		
//		int totalPages = (totalCount + BATCH_SIZE - 1) / BATCH_SIZE;
		
		this.querySyncData(totalCount,BATCH_SIZE);
		
//		for (int i = 0; i < totalPages; i++) {
//			DataPartySyncVO<T> dataPartySyncVO = this.querySyncData(Integer.valueOf(i * BATCH_SIZE),
//					Integer.valueOf(BATCH_SIZE));
//			if (dataPartySyncVO == null) {
//				return;
//			}
//			List<DataParty> dataPartyList = dataPartySyncVO.getDataPartyList();
//			if (CollectionUtils.isEmpty(dataPartyList)) {
//				return;
//			}
//			dataPartyDao.batchInsert(dataPartyList);
//			this.doSyncAfter(dataPartySyncVO);
//		}

	}

	public List<String> getAvailableKeyid(String bitmap) {
		List<KeyidMapBlock> list = keyidMapBlockDao.selectKeyidMapBlockList();

		int length = bitmap.length();
		List<String> strlist = new ArrayList<String>();
		if (length == list.size()) {
			char[] stringArr = bitmap.toCharArray();

			for (int i = 0; i < stringArr.length; i++) {
				if (stringArr[i] == '1') {
					String field = list.get(i).getField();
					if (field.indexOf("_") > 0) {
						String head = field.substring(0, field.indexOf("_"));
						String upper = field.substring(field.indexOf("_") + 1, field.indexOf("_") + 2).toUpperCase();
						String tail = field.substring(field.indexOf("_") + 2);
						strlist.add(head + upper + tail);
					} else {
						strlist.add(field);
					}

				}
			}
		}
		return strlist;

	}

	public Object primaryKeyCopy(Object from, Object to, List<String> primaryKeyList) throws Exception {
		for (int i = 0; i < primaryKeyList.size(); i++) {
			String strField = primaryKeyList.get(i);

			PropertyDescriptor pd = new PropertyDescriptor(strField, from.getClass());

			Method m = pd.getReadMethod();

			Object value = (String) m.invoke(from);

			if (value != null) {
				PropertyDescriptor pdto = new PropertyDescriptor(strField, to.getClass());

				Method mto = pdto.getWriteMethod();
				mto.invoke(to, value);
			}
		}

		return to;
	}

	public Integer getDataParyPrimaryKey(Object dataObj, String bitmap) {
		DataParty dataParty = new DataParty();
		//dataParty.setMdType(DataTypeEnum.PARTY.getCode());
		dataParty.setStatus(StatusEnum.DELETED.getStatusCode().byteValue());
		// 获取keyid
		dataParty = this.getDataParyKey(dataParty, dataObj, bitmap);

		Integer id = dataPartyDao.getDataPartyIdByKey(dataParty);
		return id;
	}

	public DataParty getDataParyKey(DataParty dataParty, Object dataObj, String bitmap) {
		if (StringUtils.isNotBlank(bitmap)) {
			try {
				// 获取keyid
				List<String> strlist = this.getAvailableKeyid(bitmap);

				dataParty = (DataParty) this.primaryKeyCopy(dataObj, dataParty, strlist);
				dataParty.setBitmap(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataParty;
	}

	protected List<Map<String, Object>> checkData(String bitmap, Integer beginId){
		
		List<String> strlist = this.getColumnKeyid(bitmap);
		
		Map<String,Object> parmMap = new HashMap<String,Object>();
		
		String bitmapColumn = "";
		
		for(String column:strlist){
			
			bitmapColumn += column + ",";
		}
		
		bitmapColumn = bitmapColumn.substring(0,bitmapColumn.length()-1);
		
		
		//查询列
		parmMap.put("bitmapColumn", bitmapColumn);
		//新增起始ID
		parmMap.put("id", beginId);
		
		parmMap.put("bitmap", bitmap);
		
		List<Map<String, Object>> repeatDatas = dataPartyDao.getRepeatDataByBitmapKeys(parmMap);
	  
  		return repeatDatas;
	}
	
	protected List<Integer> getIdsByRepeatByBitmapKeys(Map<String,Object> paramMap,int keySize,String bitmap){
		
		DataParty dataParty = new DataParty();
		
		if(paramMap.size() <= keySize){
			
			return null;
		}
		
		for(String key :paramMap.keySet()){
			
			if("su".equals(key)){
				continue;
			}
			
			String field = key;
			
			if (field.indexOf("_") > 0) {
				String head = field.substring(0, field.indexOf("_"));
				String upper = field.substring(field.indexOf("_") + 1, field.indexOf("_") + 2).toUpperCase();
				String tail = field.substring(field.indexOf("_") + 2);
				
				field = head + upper + tail;
			} 
			
			PropertyDescriptor pd;
			try {
				pd = new PropertyDescriptor(field, dataParty.getClass());
				Method sm = pd.getWriteMethod();
				
				Object filedValue = paramMap.get(key);
				
				if(filedValue == null || "".equals(filedValue.toString())){
					
					return null;
				}
				
				sm.invoke(dataParty, paramMap.get(key));
			} catch (IntrospectionException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		dataParty.setBitmap(bitmap);
		return dataPartyDao.getIdsByRepeatByBitmapKeys(dataParty);
	}
	
	protected Integer distinctData(List<Integer> ids){
		
		if(ids == null || ids.size() == 0){
			
			return 0;
			
		}else if (ids.size() == 1){
			return ids.get(0);
		}else{
			
			for(int i = 1; i < ids.size(); i++){
				
				Integer id = ids.get(i);
				if(id != null){
					logger.info("======================delete repeat id :" + id +"===================");
					dataPartyDao.deleteDataById(id);
				}
				
			}
			return ids.get(0);
		}
	}
	
	
	public List<String> getColumnKeyid(String bitmap) {
		List<KeyidMapBlock> list = keyidMapBlockDao.selectKeyidMapBlockList();

		int length = bitmap.length();
		List<String> strlist = new ArrayList<String>();
		if (length == list.size()) {
			char[] stringArr = bitmap.toCharArray();

			for (int i = 0; i < stringArr.length; i++) {
				if (stringArr[i] == '1') {
					String field = list.get(i).getField();
					strlist.add(field);
				}
			}
		}
		return strlist;

	}
	
	/**
	 * 校验主键类值是否为空
	 * @param fields
	 * @param obj
	 * @return
	 */
	protected boolean checkBitKeyByType(List<String> fields,Object obj){
		
		if(fields == null || fields.size() == 0){
			return true;
		}
		
		for(String field : fields){
			
			PropertyDescriptor pd;
			try {
				pd = new PropertyDescriptor(field, obj.getClass());
				Method m = pd.getReadMethod();
				Object fieldValue = m.invoke(obj);
				
				if(fieldValue == null || "".equals((String)fieldValue)){
					return true;	
				}
				
			} catch (IntrospectionException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
		
	}
	
	protected int getKeySizeByBitmap(String bitmap){
		
		List<String> strlist = this.getColumnKeyid(bitmap);
		
		return strlist.size();
		
	}
	
	protected List<String> getBitmaps(Integer id){
		return dataPartyDao.getBitmaps(id);
	}
	
}
