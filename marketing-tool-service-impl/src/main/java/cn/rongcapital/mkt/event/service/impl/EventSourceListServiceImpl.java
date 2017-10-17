/*************************************************
 * @功能及特点的描述简述: 事件库数量统计
 
 * @对应项目名称：营销云系统
 * @author: 单璟琦
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-01-10 
 * @date(最后修改日期)
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventSourceDao;
import cn.rongcapital.mkt.event.service.EventSourceListService;
import cn.rongcapital.mkt.event.vo.out.EventSourceListOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class EventSourceListServiceImpl implements EventSourceListService{

	@Autowired
    private EventSourceDao eventSourceDao;
	
	@Override
	public BaseOutput getEventSourceListByChannel(String channel) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);
		
	    Map<String, Object> paramMap = new HashMap();
        paramMap.put("channel", channel);
        List<EventSourceListOut> list = eventSourceDao.getEventSourceListByChannel(paramMap);
        if (!CollectionUtils.isEmpty(list)) {
        	result.getData().addAll(list);
        }
		return result;
	}

}
