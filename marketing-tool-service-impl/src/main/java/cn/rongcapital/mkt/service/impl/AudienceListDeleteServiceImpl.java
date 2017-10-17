/*************************************************
 * @功能简述: AudienceListDeleteService实现类
 * @see: MktApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/6/6
*************************************************/
package cn.rongcapital.mkt.service.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.service.AudienceListDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceListDeleteServiceImpl implements AudienceListDeleteService {
	
	@Autowired
	AudienceListDao audienceListDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.WRITE)
	public BaseOutput audienceListDel(Integer audienceListId, SecurityContext securityContext) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		AudienceList t = new AudienceList();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
		t.setId(audienceListId);
		audienceListDao.updateById(t);
    	return result;
	}
}
