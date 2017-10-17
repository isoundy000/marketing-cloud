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

public interface SegmentTagGetService {
  
    public BaseOutput getSegmentTag(String userToken, String segmentHeadId);
    
}
