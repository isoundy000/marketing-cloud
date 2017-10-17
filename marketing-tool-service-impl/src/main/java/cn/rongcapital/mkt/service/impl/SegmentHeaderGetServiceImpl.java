/*************************************************
 * @功能简述: 接口mkt.segment.header.get的service实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentHeaderGetService;
import cn.rongcapital.mkt.service.SegmentManageCalService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SegmentHeaderGetServiceImpl implements SegmentHeaderGetService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final  Integer POOL_INDEX = 2;
	
	public static final String SEGMENT_COVER_ID_STR="segmentcoverid:";
	
	@Autowired
    SegmentationHeadDao segmentationHeadDao;
	
	@Autowired
	private SegmentManageCalService segmentManageCalService;
	
	@Override
	public BaseOutput segmentHeaderGet(String userToken, String ver, String segmentId) {
		SegmentationHead t = new SegmentationHead();
		
		BaseOutput out = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		try {
			t.setId(Integer.parseInt(segmentId));  
			t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			List<SegmentationHead> list = segmentationHeadDao.selectList(t);
			
			Map<String,Object> map = new HashMap<String,Object>();
			if(CollectionUtils.isNotEmpty(list)){
				SegmentationHead s = list.get(0);
				map.put("segment_name", s.getName());
				map.put("publish_status", s.getPublishStatus());
				
				//redis 中获取覆盖人数
				Long coverCount = segmentManageCalService.scard(POOL_INDEX, SEGMENT_COVER_ID_STR+s.getId());
				map.put("cover_count", coverCount);
				
				map.put("oper", "奥巴马");//TO DO:MOCK
				map.put("id", t.getId());
				map.put("updatetime", "2016-06-01 14:26:01");
				out.getData().add(map);
			}
			out.setTotal(out.getData().size());
		} catch (Exception e) {
			logger.error(e.getMessage());
			out.setCode(9001);
			out.setMsg("细分人群编号不能为空");
//			e.printStackTrace();
		}
		return out;
//		return Response.ok().entity(out).build();
	}

}
