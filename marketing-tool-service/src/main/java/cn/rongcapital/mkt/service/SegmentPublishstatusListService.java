package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.SegmentPublishstatusListOut;

public interface SegmentPublishstatusListService {

	public SegmentPublishstatusListOut segmentPublishstatusList(String userToken,
			    						   Integer publishStatus,Integer index,
			    						   Integer size,String ver,String keyword);
	
}
