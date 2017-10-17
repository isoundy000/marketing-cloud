/*************************************************
 * @功能简述: 接口mkt.segment.tag.update的service接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/7
 * @复审人: 
 *************************************************/
package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentTagUpdateIn;

public interface SegmentTagUpdateService {

	public BaseOutput updateSegmentTag(SegmentTagUpdateIn body, SecurityContext securityContext);

}
