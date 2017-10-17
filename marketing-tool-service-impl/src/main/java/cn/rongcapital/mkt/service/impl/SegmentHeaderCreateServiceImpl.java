/*************************************************
 * @功能简述: SegmentHeaderCreateService实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.UserSessionUtil;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SegmentHeaderCreateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentHeadCreateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentHeaderCreateServiceImpl implements SegmentHeaderCreateService {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	SegmentationHeadDao segmentationHeadDao;

	@Autowired
	private MQTopicService mqTopicService;

	private static final String PARAM_NAME = "mq.synSegment";
	
	private static final String SEGMENT_SERVICE = "dataSegmentSyncTaskServiceImpl";

	/**
	 * @功能简述: 创建segment header
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext
	 * @return: Object
	 */
	@Override
	@ReadWrite(type = ReadWriteType.WRITE)
	public BaseOutput segmentHeaderCreate(SegmentHeadCreateIn body, SecurityContext securityContext) {
		SegmentationHead t = new SegmentationHead();
		t.setName(body.getSegmentName());
		t.setPublishStatus(body.getPublishStatus().byteValue());
		Date now = new Date();
		t.setCreateTime(now);
		BaseOutput ur = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(), ApiErrorCode.DB_ERROR.getMsg(),
				ApiConstant.INT_ZERO, null);
		int res = segmentationHeadDao.insert(t);
		if (res > ApiConstant.INT_ZERO) {
			ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
					null);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("oper", UserSessionUtil.getUserNameByUserToken());// TO
																		// DO:获取当前用户名
			map.put("updatetime", DateUtil.getStringFromDate(now, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
			map.put("id", t.getId());
			ur.getData().add(map);
			ur.setTotal(ur.getData().size());
		}

//		try {
//			String jsonString = JSONObject.toJSONString(t);
//			ActiveMqMessageVO activeMqMessageVO = new ActiveMqMessageVO();
//			activeMqMessageVO.setTaskName("同步受众人群到MongoDB");
//			activeMqMessageVO.setServiceName(SEGMENT_SERVICE);
//			activeMqMessageVO.setMessage(jsonString);
//			logger.info("同步受众人群到MongoDB，开始异步调取");
//			mqTopicService.senderMessage(PARAM_NAME, activeMqMessageVO);
//		} catch (JMSException e) {
//			e.printStackTrace();
//		}
		return ur;
	}

}
