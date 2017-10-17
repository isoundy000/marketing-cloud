/*************************************************
 * @功能简述: 接口mkt.segment.body.update的service接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service;
import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.in.SegmentBodyUpdateIn;

public interface SegmentBodyUpdateService {
  
    public Object segmentBodyUpdate(SegmentBodyUpdateIn body,SecurityContext securityContext);
    
}
