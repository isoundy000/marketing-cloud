package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.UserSessionUtil;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.CampaignHeaderCreateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CampaignHeadCreateIn;

@Service
public class CampaignHeaderCreateServiceImpl implements CampaignHeaderCreateService {
	
	@Autowired
	private CampaignHeadDao campaignHeadDao;

	@Override
	public BaseOutput campaignHeaderCreate(CampaignHeadCreateIn body, SecurityContext securityContext) {
		CampaignHead t = new CampaignHead();
		t.setName(body.getCampaignName());
		t.setPublishStatus(body.getPublishStatus());
		Date now = new Date();
    	t.setCreateTime(now);
    	BaseOutput ur = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(),ApiConstant.INT_ZERO,null);
    	int res = campaignHeadDao.insert(t);
    	if(res > ApiConstant.INT_ZERO) {
    		ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("oper", UserSessionUtil.getUserNameByUserToken());//TO DO:获取当前用户名
    		map.put("updatetime", DateUtil.getStringFromDate(now, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
    		map.put("id", t.getId());
    		ur.getData().add(map);
    		ur.setTotal(ur.getData().size());
    	}
		return ur;
	}

}
