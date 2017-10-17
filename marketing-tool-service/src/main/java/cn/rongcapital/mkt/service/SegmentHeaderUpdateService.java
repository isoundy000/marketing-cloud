package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentHeadDeleteIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadUpdateIn;

public interface SegmentHeaderUpdateService {
	
	public BaseOutput segmentHeaderUpdate(SegmentHeadUpdateIn body,SecurityContext securityContext);

	BaseOutput deleteSegmentHeader(SegmentHeadDeleteIn body, SecurityContext securityContext);
}
