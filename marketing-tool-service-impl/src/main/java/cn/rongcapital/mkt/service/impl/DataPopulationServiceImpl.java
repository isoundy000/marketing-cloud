package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.enums.DirectlyCityEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CityDicDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.ProvinceDicDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.CityDic;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.ProvinceDic;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.service.DataPopulationService;

@Service
public class DataPopulationServiceImpl implements DataPopulationService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private DataPopulationDao dataPopulationDao;
    
    @Autowired
    private ProvinceDicDao provinceDicDao;
    
    @Autowired
    private CityDicDao cityDicDao;
    
/*    private Map<String, ProvinceDic> provinceDicMap;
    
    private Map<String, CityDic> cityDicMap;
*/    
    private String format = "yyyy-MM-dd HH:mm:ss";
	
    private static final String WECHAT_PUBFANS_SOURCE = "公众号";
    
    private final String WECHAT_AREA = "其它";
    private final String WECHAT_CITIZENSHIP_EN = "China";
    private final String WECHAT_CITIZENSHIP = "中国";
    

    
    @Override
	public void synchronizeMemberToDataPopulationAndUpdateMember(List<WechatMember> wechatMembers) {
		 if(synchronizeMemberToDataPopulation(wechatMembers)){
	            List<Long> idList = new ArrayList<Long>();
	            for(WechatMember notUpdateStatusWechatMember: wechatMembers){
	                idList.add(notUpdateStatusWechatMember.getId());
	            }
	            updateSyncWechatMemeberListStatus(idList);
	        }		
	}

	@Override
	public void synchronizeMemberToDataPopulationAndUpdateMember(List<WechatMember> wechatMembers,
			Map<String, ProvinceDic> provinceDicMap, Map<String, CityDic> cityDicMap) {
		 if(synchronizeMemberToDataPopulation(wechatMembers,provinceDicMap,cityDicMap)){
	            List<Long> idList = new ArrayList<Long>();
	            for(WechatMember notUpdateStatusWechatMember: wechatMembers){
	                idList.add(notUpdateStatusWechatMember.getId());
	            }
	            updateSyncWechatMemeberListStatus(idList);
	        }		
	}

	@Override
	public Boolean synchronizeMemberToDataPopulation(List<WechatMember> wechatMembers) {
		Map<String, ProvinceDic> provinceDicMap = getProvinceDicMap();
		Map<String, CityDic> cityDicMap = getCityDicMap();   	
        //将同步过程改为使用PO操作数据库而不是使用Map操作数据库
        for(WechatMember wechatMember : wechatMembers){
            DataPopulation dataPopulation = new DataPopulation();
            if(wechatMember.getPubId() != null && !wechatMember.getPubId().isEmpty()){
            	dataPopulation = this.getDataPopulation(dataPopulation, wechatMember);
            	/**
                 * 清洗省市码表
                 */
                dataPopulation = cleanProvinceDicAndCityDic(dataPopulation,provinceDicMap,cityDicMap);
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

	@Override
	public Boolean synchronizeMemberToDataPopulation(List<WechatMember> wechatMembers,
			Map<String, ProvinceDic> provinceDicMap, Map<String, CityDic> cityDicMap) {
        for(WechatMember wechatMember : wechatMembers){
            DataPopulation dataPopulation = new DataPopulation();
            if(wechatMember.getPubId() != null && !wechatMember.getPubId().isEmpty()){
            	dataPopulation = this.getDataPopulation(dataPopulation, wechatMember);
            	/**
                 * 清洗省市码表
                 */
                dataPopulation = cleanProvinceDicAndCityDic(dataPopulation,provinceDicMap,cityDicMap);
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

	/**
	 * @param dataPopulation
	 * @param wechatMember
	 * @return
	 */
	private DataPopulation getDataPopulation( DataPopulation dataPopulation,WechatMember wechatMember){
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
		return dataPopulation;		
	}
	
    /**
     * 获取省码表
     * @return
     */
    public Map<String, ProvinceDic> getProvinceDicMap(){
    	Map<String, ProvinceDic> provinceDicMap = new HashMap<String, ProvinceDic>();
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
    public Map<String, CityDic> getCityDicMap(){
    	Map<String, CityDic> cityDicMap = new HashMap<String, CityDic>();
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
    private DataPopulation cleanProvinceDicAndCityDic(DataPopulation paramDataPopulation,Map<String, ProvinceDic> provinceDicMap,Map<String, CityDic> cityDicMap){
		if(paramDataPopulation!=null){
			String provice = paramDataPopulation.getProvice();
	    	if(StringUtils.isNotEmpty(provice)&&containCode(provice)){
	    		paramDataPopulation = cleanzhixiashi(paramDataPopulation);
	    	}else{
	    		paramDataPopulation = cleanProvinceDic(paramDataPopulation,provinceDicMap);
				paramDataPopulation = cleanCityDic(paramDataPopulation,cityDicMap);
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
    private DataPopulation cleanProvinceDic(DataPopulation paramDataPopulation,Map<String, ProvinceDic> provinceDicMap){
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
    private DataPopulation cleanCityDic(DataPopulation paramDataPopulation,Map<String, CityDic> cityDicMap){
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
				}
			}    			
    	}
		return paramDataPopulation;
    }

    /**
     * @param wechatMember
     * @return
     */
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

    /**
     * @param wechatMember
     */
    private void updateKeyIdInWechatMember(WechatMember wechatMember) {
        wechatMemberDao.updateById(wechatMember);
    }

    /**
     * @param dataPopulation
     * @return
     */
    private Integer retrieveKeyId(DataPopulation dataPopulation) {
        List<DataPopulation> dataPopulationList = dataPopulationDao.selectList(dataPopulation);
        if(!CollectionUtils.isEmpty(dataPopulationList)){
            DataPopulation idDataPopulation = dataPopulationList.get(0);
            return idDataPopulation.getId();
        }
        return null;
    }
    
    /**
     * @param dataPopulation
     * @return
     */
    private boolean isAlreadySyncedToDataPopulation(DataPopulation dataPopulation) {
        Integer count = dataPopulationDao.selectListCount(dataPopulation);
        if(count != null && count > 0) return true;
        return false;
    }

    /**
     * @param notSyncWechatMemberList
     */
    private void updateSyncWechatMemeberListStatus(List<Long> notSyncWechatMemberList) {
        wechatMemberDao.updateSyncDataMark(notSyncWechatMemberList);
    }
}
