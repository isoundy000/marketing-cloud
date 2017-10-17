package cn.rongcapital.mkt.service.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.service.CampaignBodyItemAudienceSearchService;
import cn.rongcapital.mkt.vo.out.CampaignBodyItemAudienceSearchData;
import cn.rongcapital.mkt.vo.out.CampaignBodyItemAudienceSearchOut;

@Service
public class CampaignBodyItemAudienceSearchServiceImpl implements CampaignBodyItemAudienceSearchService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public CampaignBodyItemAudienceSearchOut campaignBodyItemAudienceSearch(String name,Integer campaignHeadId,String itemId) {
		CampaignBodyItemAudienceSearchOut CampaignBodyItemAudienceSearchOut =  new CampaignBodyItemAudienceSearchOut(
				 															    ApiConstant.INT_ZERO, 
					                    		                                ApiErrorCode.SUCCESS.getMsg(), 
					                    		                                ApiConstant.INT_ZERO);
		//模糊匹配
		if(StringUtils.isBlank(name)) {
			name = "";
		}
		Pattern pattern = Pattern.compile("^.*"+name+".*$", Pattern.CASE_INSENSITIVE);
		Query query = Query.query(Criteria.where("name").regex(pattern).and("status").is(0)
								  .and("campaignHeadId").is(campaignHeadId).and("itemId").is(itemId));  
	    List<NodeAudience> nodeAudienceList = mongoTemplate.find(query, NodeAudience.class);
	    if(CollectionUtils.isNotEmpty(nodeAudienceList)) {
	    	for(NodeAudience nodeAudience:nodeAudienceList) {
	    		CampaignBodyItemAudienceSearchData dataCustom = new CampaignBodyItemAudienceSearchData();
	    		dataCustom.setDataId(nodeAudience.getDataId());
	    		dataCustom.setName(nodeAudience.getName());
	    		CampaignBodyItemAudienceSearchOut.getDataCustom().add(dataCustom);
	    	}
	    	CampaignBodyItemAudienceSearchOut.setTotal(CampaignBodyItemAudienceSearchOut.getDataCustom().size());
	    }
		return CampaignBodyItemAudienceSearchOut;
	}

}
