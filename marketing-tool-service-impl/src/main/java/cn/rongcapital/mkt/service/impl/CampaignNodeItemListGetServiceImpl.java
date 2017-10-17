package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignNodeItemDao;
import cn.rongcapital.mkt.po.CampaignNodeItem;
import cn.rongcapital.mkt.service.CampaignNodeItemListGetService;
import cn.rongcapital.mkt.vo.out.CampaignItemListOut;
import cn.rongcapital.mkt.vo.out.CampaignNodeItemListOut;
import cn.rongcapital.mkt.vo.out.CampaignNodeListOut;

@Service
public class CampaignNodeItemListGetServiceImpl implements CampaignNodeItemListGetService {
	
	@Autowired
	CampaignNodeItemDao campaignNodeItemDao;
	
	public CampaignNodeItemListOut campaignNodeItemListGet(String userToken, String ver) {
		
		CampaignNodeItemListOut campaignNodeItemListOut = new CampaignNodeItemListOut(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),0,null);
		CampaignNodeItem t = new CampaignNodeItem();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setPtype((byte)-1);//先查找父类节点
		List<CampaignNodeItem> campaignNodeList = campaignNodeItemDao.selectList(t);
		if(CollectionUtils.isNotEmpty(campaignNodeList)){
			for(CampaignNodeItem node:campaignNodeList) {
				CampaignNodeListOut campaignNodeListOut = new CampaignNodeListOut();
				campaignNodeListOut.setColor(node.getColor());
				campaignNodeListOut.setIcon(node.getIcon());
				campaignNodeListOut.setIndex(node.getDisplayIndex());
				campaignNodeListOut.setName(node.getName());
				campaignNodeListOut.setCode(node.getCode());
				campaignNodeListOut.setType(node.getType());
				campaignNodeListOut.setUrl(node.getUrl());
				
				CampaignNodeItem t2 = new CampaignNodeItem();
				t2.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				t2.setPtype(node.getType());
				List<CampaignNodeItem> campaignItemList = campaignNodeItemDao.selectList(t2);
				if(CollectionUtils.isNotEmpty(campaignItemList)){
					for(CampaignNodeItem item:campaignItemList) {
						CampaignItemListOut campaignItemListOut = new CampaignItemListOut();
						campaignItemListOut.setColor(item.getColor());
						campaignItemListOut.setIcon(item.getIcon());
						campaignItemListOut.setIndex(item.getDisplayIndex());
						campaignItemListOut.setName(item.getName());
						campaignItemListOut.setCode(item.getCode());
						campaignItemListOut.setType(item.getType());
						campaignItemListOut.setUrl(item.getUrl());
						campaignNodeListOut.getChildren().add(campaignItemListOut);
					}
				}
				
				campaignNodeItemListOut.getData().add(campaignNodeListOut);
			}
			
		}
		campaignNodeItemListOut.setTotal(campaignNodeItemListOut.getData().size());
		return campaignNodeItemListOut;
	}

}
