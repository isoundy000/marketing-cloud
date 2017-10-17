/*************************************************
 * @功能简述: AudienceSearchService实现类
 * @see: MkyApi
 * @author: xukun
 * @version: 1.0
 * @date: 2016/6/23 
*************************************************/


package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.GenderUtils;
import cn.rongcapital.mkt.dao.AudienceColumnsDao;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.AudienceSearchService;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class AudienceSearchServiceImpl implements AudienceSearchService {
	
    @Autowired
	AudienceListDao audienceListDao;
	@Autowired
	AudienceColumnsDao audienceColumnsDao;
	
	@Autowired
	AudienceListPartyMapDao audienceListPartyMapDao;
	
	@Autowired
	FindCustomTagInfoService findCustomTagInfoServiceImpl;
	
	@Autowired
	CustomTagMapDao  customTagMapDao;
	
	@Autowired
	DataPartyDao dataPartyDao;
	
	private static final String ORDER_BY_FIELD_NAME = "field_order";//排序的字段名
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput audienceByName(String userToken,String audience_type,String audience_id,String audience_name,Integer size,Integer index) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO,null);

		if(StringUtils.isEmpty(audience_name)) return result;

		AudienceList param = new AudienceList();
		param.setPageSize(size);
		param.setStartIndex((index-1)*size);
		
		List<Integer> idList=new ArrayList<Integer>();

		List<String> partyIdList=new ArrayList<>();

		List<Map<String,Object>> resultList = null;
				
		if(audience_type.equals("0")){		    
		    //全局模糊查询所有联系人		    		    
		    resultList=dataPartyDao.selectListByKeyName(audience_name);
		}else if(audience_type.equals("1")){
		    
		    //人群管理:在人群中查找
		    //1.根据人群ID查出联系人ID列表
			partyIdList = audienceListPartyMapDao.selectPartyIdLIistByAudienceId(audience_id);
		    //2.在联系人中查找名字匹配的
			if(partyIdList != null && partyIdList.size() > 0){
				resultList = SearchAudienceByName(audience_name, partyIdList, resultList);
			}
		}else if(audience_type.equals("2")){
            //自定义标签:在人群中查找
            //1.根据自定义标签ID查出人群ID列表
			//partyIdList = customTagMapDao.selectTagIdList(audience_id);

            //2.在人群(自定义标签)中查找名字匹配的
			//resultList = SearchAudienceByName(audience_name, partyIdList, resultList);
			
			//根据tag_id,从mongo获得关联的人群
			List<DataParty> audiences=findCustomTagInfoServiceImpl.findMDataByTagId(audience_id,null,null);
			List<String> partyIds=new ArrayList<String>();
			if(!CollectionUtils.isEmpty(audiences)){
				for(DataParty myDataParty:audiences){
					if(myDataParty!=null)
					{
					partyIds.add(String.valueOf(myDataParty.getMid()));
					}
				}
				
			}
			resultList = SearchAudienceByName(audience_name, partyIds, resultList);
		}

		if(resultList != null && resultList.size() > 0){
			for(Map<String,Object> map : resultList){
				if(map.get("gender") != null){
                    Integer gender = (Integer) map.get("gender");
					map.put("gender", GenderUtils.byteToChar(gender.byteValue()));
				}
			}
		}

		if(resultList != null && resultList.size() > 0){
			result.getData().addAll(resultList);
		}
		result.setTotal(result.getData().size());
		
		return result;
	}

	private List<Map<String, Object>> SearchAudienceByName(String audience_name, List<String> partyIdList, List<Map<String, Object>> resultList) {
		if(partyIdList.size() != 0){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("partyIdList",partyIdList);
            paramMap.put("key_word",audience_name);
            resultList=dataPartyDao.selectListByNameInList(paramMap);
        }
		return resultList;
	}

}
