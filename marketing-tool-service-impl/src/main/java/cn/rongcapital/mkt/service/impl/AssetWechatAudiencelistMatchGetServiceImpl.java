/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.AssetWechatAudiencelistMatchGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class AssetWechatAudiencelistMatchGetServiceImpl implements AssetWechatAudiencelistMatchGetService{

	
	@Autowired
	private AudienceListDao audienceListDao;
	
	/**
	 * 精确查询固定人群是否存在
	 * 
	 * @author shuiyangyang
	 * @Data 2016.08.25
	 */
	@Override
	public BaseOutput assetWechatAudiencelistMatchGet(String audienceName) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE,null);
		
		AudienceList audienceList = new AudienceList();
		audienceList.setAudienceName(audienceName);
		
		
		List<AudienceList> audienceListLists = audienceListDao.selectList(audienceList);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(audienceListLists == null || audienceListLists.isEmpty()) {
			map.put("is_match", 0);
			map.put("id", "");
		} else {
			map.put("is_match", 1);
			map.put("id", audienceListLists.get(0).getId());
		}
		
		result.getData().add(map);
		
		return result;
	}

}
