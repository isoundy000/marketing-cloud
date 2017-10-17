/*************************************************
 * @功能简述: 获取某联系人行为信息(微信、web、活动)的业务类实现 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.PartyBehaviorDao;
import cn.rongcapital.mkt.po.PartyBehavior;
import cn.rongcapital.mkt.service.MainActionInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MainActionInfoGetServiceImpl implements MainActionInfoGetService{
	
	@Autowired
	PartyBehaviorDao partyBehaviorDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput getMainActionInfo(String contactId,String behaviorType) {
		PartyBehavior param = new PartyBehavior();
		param.setContactId(contactId);
		param.setBehaviorType(behaviorType);
		List<PartyBehavior> resList = partyBehaviorDao.selectList(param);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if(resList != null && !resList.isEmpty()){
			result.setTotal(resList.size());
			for(PartyBehavior po : resList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("behavior_type", po.getBehaviorType());
				map.put("behavior_name", po.getBehaviorName());
				map.put("wechat_type", po.getWechatType());
				map.put("wechat_name", po.getWechatName());
				map.put("message", po.getMessage());
				map.put("createtime", po.getCreatetime());
				result.getData().add(map);
			}
		}
		return result;
	}
	
	@Override
    @ReadWrite(type=ReadWriteType.READ)
    public BaseOutput getPartyBehaviorCountById(String contactId) {
        
	    BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
	                    ApiErrorCode.SUCCESS.getMsg(),
	                    ApiConstant.INT_ZERO,null);
	    
	    PartyBehavior param = new PartyBehavior();
        param.setContactId(contactId);
        
        //行为类型
        //0-微信、1-web、2-营销活动
        
        Map<String,Object> map1 = new HashMap<String,Object>();        
        param.setBehaviorType("0");
        int type1_count=partyBehaviorDao.selectListCount(param);                
        map1.put("behavior_type", "0");
        map1.put("behavior_count", type1_count+""); 
        result.getData().add(map1);
                
        Map<String,Object> map2 = new HashMap<String,Object>();
        param.setBehaviorType("1");
        int type2_count=partyBehaviorDao.selectListCount(param);
        map2.put("behavior_type", "1");
        map2.put("behavior_count", type2_count+"");
        result.getData().add(map2);
        
        Map<String,Object> map3 = new HashMap<String,Object>();
        param.setBehaviorType("2");
        int type3_count=partyBehaviorDao.selectListCount(param);
        map3.put("behavior_type", "2");
        map3.put("behavior_count", type3_count+"");
        result.getData().add(map3);
        
        result.setTotal(3);        
        return result;
    }

}
