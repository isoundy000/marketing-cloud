package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.DirectlyCityEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CityDicDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.ProvinceDicDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CityDic;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.ProvinceDic;
import cn.rongcapital.mkt.po.WechatMember;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-8-9.
 */

@Service
public class WechatMemberScheduleToPopulationServiceImpl implements TaskService{
    private static final Integer MD_TYPE = 8;
    private static final String WECHAT_PUBFANS_SOURCE = "公众号";
    private static final String WECHAT_PERSONS_SOURCE = "个人号";
    private static final Integer NOT_SYNC_TO_DATA_PARTY = 0;
    private static final Integer SYNCED_TO_DATA_PARTY = 1;
    private final Integer BATCH_SIZE = 500;
    
    private final String WECHAT_AREA = "其它";
    private final String WECHAT_CITIZENSHIP_EN = "China";
    private final String WECHAT_CITIZENSHIP = "中国";
    

    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private DataPopulationDao dataPopulationDao;
    
    @Autowired
    private ProvinceDicDao provinceDicDao;
    
    @Autowired
    private CityDicDao cityDicDao;
    
    private Map<String, ProvinceDic> provinceDicMap;
    
    private Map<String, CityDic> cityDicMap;
    
    private String format = "yyyy-MM-dd HH:mm:ss";

    //1选出没有被同步过的数据，根据selected为0的字段
    //2将数据同步到dataParty表中。
    //3将通不过的数据的selected字段置为1。


    @Override
    public void task(Integer taskId) {
        Integer totalCount = wechatMemberDao.selectedNotSyncCount();
        if(totalCount != null&&totalCount!=0){
        	provinceDicMap = getProvinceDicMap();
        	cityDicMap = getCityDicMap();
            for(int pageNum = 1; pageNum <= (totalCount + BATCH_SIZE -1) / BATCH_SIZE; pageNum ++ ){
                syncWechatMemberByBatchSize();
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void syncWechatMemberByBatchSize() {
        //1通过PO来选择未同步的WechatMember数据,如果没有未同步的数据则返回，如果有对这些数据进行同步
        WechatMember wechatMember = new WechatMember();
        wechatMember.setSelected(NOT_SYNC_TO_DATA_PARTY.byteValue());
        wechatMember.setPageSize(BATCH_SIZE);
        List<WechatMember> notSyncWechatMemberList = wechatMemberDao.selectList(wechatMember);
        if(notSyncWechatMemberList == null || CollectionUtils.isEmpty(notSyncWechatMemberList)) return;
        if(doSync(notSyncWechatMemberList)){
            List<Long> idList = new ArrayList<Long>();
            for(WechatMember notUpdateStatusWechatMember: notSyncWechatMemberList){
                idList.add(notUpdateStatusWechatMember.getId());
            }
            updateSyncWechatMemeberListStatus(idList);
        }
    }

    //在这里需要做两步，data_party里有数据的弄出key_id反插入member，否则
    private boolean doSync(List<WechatMember> notSyncWechatMemberList) {
        //将同步过程改为使用PO操作数据库而不是使用Map操作数据库
        for(WechatMember wechatMember : notSyncWechatMemberList){
            DataPopulation dataPopulation = new DataPopulation();
            if(wechatMember.getPubId() != null && !wechatMember.getPubId().isEmpty()){
                dataPopulation.setWxmpId(wechatMember.getPubId());
                dataPopulation.setWxCode(wechatMember.getWxCode());
                dataPopulation.setBitmap(wechatMember.getBitmap());
                dataPopulation.setProvice(wechatMember.getProvince());
                dataPopulation.setName(wechatMember.getWxName());
                if(wechatMember.getSex()!=null){
                	dataPopulation.setGender(wechatMember.getSex().byteValue());
                }                
                dataPopulation.setNickname(wechatMember.getNickname());
                dataPopulation.setCity(wechatMember.getCity());
                dataPopulation.setHeadImgUrl(wechatMember.getHeadImageUrl());
                dataPopulation.setCitizenship(wechatMember.getCountry());
                dataPopulation.setSource(WECHAT_PUBFANS_SOURCE);
                dataPopulation.setSubscribeTime(DateUtil.getDateFromString(wechatMember.getSubscribeTime(),format));
                dataPopulation.setRemark(wechatMember.getRemark());
                /**
                 * 清洗省市码表
                 */
                dataPopulation = cleanProvinceDicAndCityDic(dataPopulation);
                if (updateWechatMemberKeyidByBitmap(wechatMember)){
                	dataPopulationDao.updateDataPopulationByPubIdAndOpenId(dataPopulation);
                }else{
                    dataPopulationDao.insert(dataPopulation);
                    wechatMember.setKeyid(dataPopulation.getId());
                    updateKeyIdInWechatMember(wechatMember);
                } 
            }
        }
        return true;
    }

    private boolean updateWechatMemberKeyidByBitmap(WechatMember wechatMember) {
    	DataPopulation dataPopulation = new DataPopulation();
        dataPopulation.setWxmpId(wechatMember.getPubId());
        dataPopulation.setWxCode(wechatMember.getWxCode());
        if(isAlreadySyncedToDataPopulation(dataPopulation)){
            Integer keyId = retrieveKeyId(dataPopulation);
            if(keyId != null && keyId > 0){
                wechatMember.setKeyid(keyId);
                updateKeyIdInWechatMember(wechatMember);
                return true;
            }
        }
        return false;
    }

    private void updateKeyIdInWechatMember(WechatMember wechatMember) {
        wechatMemberDao.updateById(wechatMember);
    }

    private Integer retrieveKeyId(DataPopulation dataPopulation) {
        List<DataPopulation> dataPopulationList = dataPopulationDao.selectList(dataPopulation);
        if(!CollectionUtils.isEmpty(dataPopulationList)){
            DataPopulation idDataPopulation = dataPopulationList.get(0);
            return idDataPopulation.getId();
        }
        return null;
    }

    private boolean isAlreadySyncedToDataPopulation(DataPopulation dataPopulation) {
        Integer count = dataPopulationDao.selectListCount(dataPopulation);
        if(count != null && count > 0) return true;
        return false;
    }

    private void updateSyncWechatMemeberListStatus(List<Long> notSyncWechatMemberList) {
        wechatMemberDao.updateSyncDataMark(notSyncWechatMemberList);
    }
    
    /**
     * 获取省码表
     * @return
     */
    private Map<String, ProvinceDic> getProvinceDicMap(){
    	provinceDicMap = new HashMap<String, ProvinceDic>();
    	ProvinceDic provinceDicTemp = new ProvinceDic();
    	provinceDicTemp.setPageSize(null);
    	provinceDicTemp.setStartIndex(null);
    	List<ProvinceDic> provinceDics = provinceDicDao.selectList(provinceDicTemp);
    	if(!CollectionUtils.isEmpty(provinceDics)){
    		for(Iterator<ProvinceDic> iter = provinceDics.iterator();iter.hasNext();){
    			ProvinceDic provinceDic = iter.next();
    			if(!provinceDicMap.containsKey(provinceDic.getProvinceNamee())){
    				provinceDicMap.put(provinceDic.getProvinceNamee(), provinceDic);
    			}    			
    		}
    	}
		return provinceDicMap;
    }
    
    
    /**
     * 获取市码表
     * @return
     */
    private Map<String, CityDic> getCityDicMap(){
    	cityDicMap = new HashMap<String, CityDic>();
    	CityDic cityDicTemp = new CityDic();
    	cityDicTemp.setPageSize(null);
    	cityDicTemp.setStartIndex(null);
    	List<CityDic> cityDics = cityDicDao.selectList(cityDicTemp);
    	if(!CollectionUtils.isEmpty(cityDics)){
    		for(Iterator<CityDic> iter = cityDics.iterator();iter.hasNext();){
    			CityDic cityDic = iter.next();
    			if(!cityDicMap.containsKey(cityDic.getCityNamee())){
    				cityDicMap.put(cityDic.getCityNamee(), cityDic);
    			}    			
    		}
    	}
		return cityDicMap;
    }
    
    /**
     * 清洗省市，英文换成中文
     * @param paramDataPopulation
     * @return
     */
    private DataPopulation cleanProvinceDicAndCityDic(DataPopulation paramDataPopulation){
		if(paramDataPopulation!=null){
			String provice = paramDataPopulation.getProvice();
	    	if(StringUtils.isNotEmpty(provice)&&containCode(provice)){
	    		paramDataPopulation = cleanzhixiashi(paramDataPopulation);
	    	}else{
	    		paramDataPopulation = cleanProvinceDic(paramDataPopulation);
				paramDataPopulation = cleanCityDic(paramDataPopulation);
	    	}
		}
		return paramDataPopulation;   	
    }
    
    
    /**
     * 替换直辖市的省市中文
     * @param paramDataPopulation
     * @return
     */
    private DataPopulation cleanzhixiashi(DataPopulation paramDataPopulation){
    	String provice = paramDataPopulation.getProvice();
		paramDataPopulation.setProvice(DirectlyCityEnum.valueOf(provice).getDescription());
		paramDataPopulation.setCity(DirectlyCityEnum.valueOf(provice).getDescription());
		paramDataPopulation.setCitizenship(this.WECHAT_CITIZENSHIP);
		return paramDataPopulation;    	
    }
    
    /**
     * 验证省份是否是直辖市
     * @param code
     * @return
     */
    private boolean containCode(String code){
    	boolean hasCode = false;
    	switch(code){
	    	 case "Beijing" :{
	    		 hasCode = true;
	    		 break;
	    	 }
	    	 case "Shanghai" :{
	    		 hasCode = true;
	    		 break;
	    	 }
	    	 case "Tianjin" :{
	    		 hasCode = true;
	    		 break;
	    	 }
	    	 case "Chongqing" :{
	    		 hasCode = true;
	    		 break;
	    	 }
	    	 default :{
	    		 break; 
	    	 }
    	}
		return hasCode;
    }
    
    /**
     * 替换省中文
     * @param paramDataPopulation
     * @return
     */
    private DataPopulation cleanProvinceDic(DataPopulation paramDataPopulation){
    	String provice = paramDataPopulation.getProvice();		
    	if(provinceDicMap!=null&&provinceDicMap.size()>0){    			
			if(StringUtils.isNotEmpty(provice)&&provinceDicMap.containsKey(provice)){
				ProvinceDic provinceDic = provinceDicMap.get(provice);
				if(provinceDic!=null){
					String provinceNamec = provinceDic.getProvinceNamec();
					paramDataPopulation.setProvice(provinceNamec);
					paramDataPopulation.setCitizenship(this.WECHAT_CITIZENSHIP);
				}
			}else{
				paramDataPopulation.setProvice(this.WECHAT_AREA);
				String citizenship = paramDataPopulation.getCitizenship();
				if(StringUtils.isNotEmpty(citizenship)&&WECHAT_CITIZENSHIP_EN.equals(citizenship)){
					paramDataPopulation.setCitizenship(this.WECHAT_CITIZENSHIP);
				}else{
					paramDataPopulation.setCitizenship(this.WECHAT_AREA);
				}
				
			}    			    		
    	}
    	
		return paramDataPopulation;   
    }
    
    /**
     * 替换市中文
     * @param paramDataPopulation
     * @return
     */
    private DataPopulation cleanCityDic(DataPopulation paramDataPopulation){
    	String city = paramDataPopulation.getCity();
    	if(cityDicMap!=null&&cityDicMap.size()>0){
			if(StringUtils.isNotEmpty(city)&&cityDicMap.containsKey(city)){
				CityDic cityDic = cityDicMap.get(city);
				if(cityDic!=null){
					String cityNamec = cityDic.getCityNamec();
					paramDataPopulation.setCity(cityNamec);
					paramDataPopulation.setCitizenship(this.WECHAT_CITIZENSHIP);
				}
			}else{
				paramDataPopulation.setCity(this.WECHAT_AREA);
				String citizenship = paramDataPopulation.getCitizenship();
				if(StringUtils.isNotEmpty(citizenship)&&WECHAT_CITIZENSHIP_EN.equals(citizenship)){
					paramDataPopulation.setCitizenship(this.WECHAT_CITIZENSHIP);
				}else{
					paramDataPopulation.setCitizenship(this.WECHAT_AREA);
				}
			}    			
    	}
		return paramDataPopulation;
    }
}
