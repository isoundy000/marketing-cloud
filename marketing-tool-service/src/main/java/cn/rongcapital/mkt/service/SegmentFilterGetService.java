/*************************************************
 * @功能简述: 接口mkt.segment.tag.get的service接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/7
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service;


import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.*;
import cn.rongcapital.mkt.vo.out.SegmentFilterOut;

import javax.ws.rs.core.SecurityContext;


public interface SegmentFilterGetService {
  
    BaseOutput getSegmentFilterCount(SegmentFilterCountIn body, SecurityContext securityContext);

    BaseOutput getSegmentFilterSum(SegmentFilterSumIn body);

    BaseOutput segmentGenderCountList(SegmentCountFilterIn input);

    BaseOutput segmentProvinceCountList(SegmentCountFilterIn input);

    BaseOutput segmentReceiveCountList(SegmentCountFilterIn input);

    SegmentFilterOut getSegmentFilterResult(TagGroupsListIn tagGroupsListIn);

}
