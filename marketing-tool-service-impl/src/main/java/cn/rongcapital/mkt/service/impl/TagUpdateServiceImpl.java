package cn.rongcapital.mkt.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.SecurityContext;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.po.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.rongcapital.mkt.service.TagUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagBodyUpdateIn;

@Service
public class TagUpdateServiceImpl implements TagUpdateService{

    @Autowired
    private TagDao tagDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput tagInfoUpdate(TagBodyUpdateIn body) 
	{
		Tag tag = new Tag();
		tag.setId(body.getTag_id());
		tag.setName(body.getTag_name());
		tag.setTagGroupId(body.getTag_group_id());
		tag.setUpdateTime(new Date());
		tagDao.updateById(tag);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", body.getTag_id());
    	map.put("updatetime", DateUtil.getStringFromDate(new Date(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
    	result.getData().add(map);
    	result.setTotal(result.getData().size());
    	
    	return result;
	}
}
